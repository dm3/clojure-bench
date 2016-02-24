(ns entity-access
  (:require [datomic.api :as datomic]
            [datomic-schema.schema :as s]))

(set! *warn-on-reflection* true)

(defn- schema []
  [(s/schema order
     (s/fields
       [id :string :unique :indexed]
       [client :ref]
       [lines :ref :many :component]))

   (s/schema line
     (s/fields
       [part-id :string :unique]
       [seq-no :long]
       [time-added :instant]
       [cost :long]))

   (s/schema client
     (s/fields
       [id :string :unique]
       [name :string]
       [email :string :unique]
       [age :long]))])

(defn- generate-clients [n]
  (for [n (range n)]
    {:db/id (datomic/tempid :db.part/user)
     :client/id (str n)
     :client/name (str "Client #" n)
     :client/email (str "client@" n ".example")
     :client/age n}))

(defn- uuid []
  (str (java.util.UUID/randomUUID)))

(defn- inst []
  (java.util.Date.))

(defn- generate-lines [n]
  (for [n (range n)]
    {:line/part-id (uuid)
     :line/seq-no n
     :line/time-added (inst)
     :line/cost n}))

(defn- order-id [client-id order-seq]
  (str client-id "-" order-seq))

(defn- generate-orders [clients orders-per-client lines-per-order]
  (for [client clients, n (range orders-per-client)]
    {:db/id (datomic/tempid :db.part/user)
     :order/id (order-id (:client/id client) (str n))
     :order/client (:db/id client)
     :order/lines (generate-lines lines-per-order)}))

(defn- generate-data []
  (let [clients (generate-clients 100)
        orders (generate-orders clients 100 20)]
    (concat clients orders)))

(defn- setup-datomic []
  (datomic/create-database "datomic:mem://test")
  (let [conn (datomic/connect "datomic:mem://test")]
    @(datomic/transact conn (s/generate-schema (schema)))
    @(datomic/transact conn (generate-data))
    conn))

(defn- total-price-datoms [db order-id]
  (->> (datomic/datoms db :avet :order/id order-id)
       (first)
       :e
       (datomic/entity db)
       (:order/lines)
       (map :line/cost)
       (reduce +)))

(defn- total-price-part-query [db order-id]
  (->> (datomic/q '[:find [?lines ...]
                    :in $ ?order-id
                    :where
                    [?e :order/id ?order-id]
                    [?e :order/lines ?lines]]
                  db order-id)
       (map (partial datomic/entity db))
       (map :line/cost)
       (reduce +)))

(defn- total-price-query [db order-id]
  (->> (datomic/q '[:find (sum ?cost)
                    :in $ ?order-id
                    :where
                    [?e :order/id ?order-id]
                    [?e :order/lines ?line]
                    [?line :line/cost ?cost]]
                  db order-id)
       (ffirst)))

(def db nil)

(defn setup []
  (alter-var-root #'db (constantly (datomic/db (setup-datomic)))))

(defn datoms-index []
  (total-price-datoms db (order-id 50 50)))

(defn sum-query-code []
  (total-price-part-query db (order-id 50 50)))

(defn sum-query []
  (total-price-query db (order-id 50 50)))
