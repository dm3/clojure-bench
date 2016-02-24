(ns entity-access
  (:import [entity Entities Entities$Client Entities$Order Entities$Line Build]
           [org.deephacks.graphene Graphene EntityRepository
            TransactionManager TransactionManager$Transactional]))

(defn- uuid []
  (str (java.util.UUID/randomUUID)))

(defn- inst []
  (java.util.Date.))

(defn- generate-clients [n]
  (for [n (range n)]
    (Build/createClient (str n) (str "Client #" n) (str "client@" n ".example") n)))

(defn- generate-lines [order-id n]
  (for [n (range n)]
    (Build/createLine (str order-id "-" n) (str n), n, (inst), n)))

(defn- order-id [client-id order-seq]
  (str client-id "-" order-seq))

(defn- generate-orders-and-lines [clients orders-per-client lines-per-order]
  (for [client clients, n (range orders-per-client)]
    (let [oid (order-id (.getUniqueId client) (str n))
          lines (vec (generate-lines oid lines-per-order))]
      (conj lines (Build/createOrder oid client lines)))))

(defn generate-data []
  (let [clients (generate-clients 100)
        orders (flatten (generate-orders-and-lines clients 100 20))]
    (concat clients orders)))

(defn with-tx [f]
  (TransactionManager/withTx
    (reify TransactionManager$Transactional
      (execute [_ tx]
        (f tx)))))

(defn put! [^EntityRepository repo e]
  (.put repo e))

(def ^EntityRepository repository nil)

(defn load-all! []
  (with-tx
    (fn [_]
      (doseq [e (generate-data)]
        (put! repository e)))))

(defn setup []
  (Graphene/create)
  (alter-var-root #'repository (constantly (EntityRepository.)))
  (load-all!))

(defn delete-dir! [fname]
  (let [func (fn [func f]
               (when (.isDirectory f)
                 (doseq [f2 (.listFiles f)]
                   (func func f2)))
               (clojure.java.io/delete-file f))]
    (func func (clojure.java.io/file fname))))

(defn teardown []
  (-> (Graphene/get)
      (.get)
      (.closeAndDelete))
  (delete-dir! Graphene/DEFAULT_ENV_FILE)
  (alter-var-root #'repository (constantly nil)))

(defn get-by-id [id tp]
  (let [^java.util.Optional result (.get repository id tp)]
    (.orElse result nil)))

(defn sum-get-entity []
  (let [^Entities$Order o (get-by-id (order-id 50 50) Entities$Order)]
    (->> (.getLines o)
         (map (fn [^Entities$Line l] (.getCost l)))
         (reduce +))))

(defn sum-query []
  (->> (.query repository "filter id startsWith '50-50' ordered id" Entities$Line)
       (map (fn [^Entities$Line l] (.getCost l)))
       (reduce +)))
