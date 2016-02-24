package entity;

import java.util.List;
import java.util.Date;
import java.util.UUID;
import entity.Entities.Client;
import entity.Entities.Line;
import entity.Entities.Order;

public class Build {

    public static Client createClient(String id, String name, String email, Long age) {
        return new ClientBuilder().withUniqueId(id).withName(name).withEmail(email).withAge(age).build();
    }

    public static Line createLine(String id, String partId, Long seqNo, Date date, Long cost) {
        return new LineBuilder().withId(id)
            .withPartId(partId).withSeqNo(seqNo).withTimeAdded(date).withCost(cost).build();
    }

    public static Order createOrder(String id, Client client, List<Line> lines) {
        return new OrderBuilder().withUniqueId(id).withClient(client).withLines(lines).build();
    }
}
