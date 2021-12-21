package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.util.List;
import java.util.function.Function;

public class HbnStore implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private static final Logger LOG = LoggerFactory.getLogger(HbnStore.class.getName());

    private HbnStore() {
    }

    private static final class Lazy {
        private static final Store INST = new HbnStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception on HbnStore", e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Item add(Item item) {
        return this.tx(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public List<Item> findAllItems() {
        return this.tx(
                session -> session.createQuery("from ru.job4j.model.Item").list()
        );
    }

    @Override
    public List<Item> findNotDoneItems() {
        return this.tx(
                session -> session.createQuery("from ru.job4j.model.Item where isDone = false").list()
        );
    }

    @Override
    public Item replace(int id) {
        return this.tx(session -> {
            session.createQuery("update ru.job4j.model.Item set isDone = true where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return session.get(Item.class, id);
        });
    }

    @Override
    public Item findById(int id) {
        return this.tx(session -> session.get(Item.class, id));
    }

    @Override
    public User findUserByUsername(String username) {
        User result;
        result = (User) this.tx(
                session -> session.createQuery("from ru.job4j.model.User where username = :username")
                        .setParameter("username", username)
                        .list().get(0));
        return result;
    }

    @Override
    public User findUserByEmail(String email) {
        User result;
        result = (User) this.tx(
                session -> session.createQuery("from ru.job4j.model.User where email = :email")
                        .setParameter("email", email)
                        .list().get(0));
        return result;
    }

    @Override
    public User add(User user) {
        return this.tx(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public List<Item> findAllItemsByUser(User user) {
        return this.tx(session -> session.createQuery("from ru.job4j.model.Item where user = :user")
                .setParameter("user", user)
                .list());
    }

    @Override
    public List<Item> findNotDoneItemsByUser(User user) {
        return this.tx(
                session -> session.createQuery("from ru.job4j.model.Item where isDone = false AND user = :user")
                .setParameter("user", user)
                .list());
    }
}
