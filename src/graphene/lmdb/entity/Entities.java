package entity;

import java.util.Date;
import java.util.List;
import org.deephacks.graphene.Entity;
import org.deephacks.graphene.Key;

public class Entities {
    @Entity
    public interface Order {
        @Key
        String getUniqueId();
        Client getClient();
        List<Line> getLines();
    }

    @Entity
    public interface Line {
        @Key
        String getId();
        String getPartId();
        Long getSeqNo();
        Date getTimeAdded();
        Long getCost();
    }

    @Entity
    public interface Client {
        @Key
        String getUniqueId();
        String getName();
        String getEmail();
        Long getAge();
    }
}
