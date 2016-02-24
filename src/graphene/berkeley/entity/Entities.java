package entity;

import java.util.Date;
import java.util.List;
import org.deephacks.graphene.Entity;
import org.deephacks.graphene.Id;
import org.deephacks.graphene.Embedded;
import org.deephacks.vals.VirtualValue;

public class Entities {
    @Entity @VirtualValue
    public interface Order {
        @Id
        String getUniqueId();
        Client getClient();
        List<Line> getLines();
    }

    @Entity @VirtualValue
    public interface Line {
        @Id
        String getId();
        String getPartId();
        Long getSeqNo();
        Date getTimeAdded();
        Long getCost();
    }

    @Entity @VirtualValue
    public interface Client {
        @Id
        String getUniqueId();
        String getName();
        String getEmail();
        Long getAge();
    }
}
