package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CodeFirstApplication {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("code_first");
        EntityManager manager = factory.createEntityManager();

        Runnable runnable = new Engine(manager);

        runnable.run();
    }
}
