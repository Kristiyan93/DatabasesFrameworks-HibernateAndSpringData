package app;

import javax.persistence.EntityManager;

public class Engine implements Runnable {
    private final EntityManager manager;

    public Engine(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        this.manager.getTransaction().begin();

        this.manager.getTransaction().commit();
    }
}
