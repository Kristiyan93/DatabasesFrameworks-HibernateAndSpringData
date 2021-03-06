package apps;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence
                .createEntityManagerFactory("soft_uni");
        EntityManager manager = factory.createEntityManager();

        Runnable engine = new Engine(manager);
        engine.run();
    }
}
