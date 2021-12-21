package ru.job4j.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private final LocalDateTime created = LocalDateTime.now();
    private boolean isDone;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Item() {
    }

    public Item(String description) {
        this.description = description;
    }

    public Item(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public Item(int id, String description, boolean isDone) {
        this.id = id;
        this.description = description;
        this.isDone = isDone;
    }

    public Item(String description, boolean isDone, User user) {
        this.description = description;
        this.isDone = isDone;
        this.user = user;
    }

    public Item(int id, String description, boolean isDone, User user) {
        this.id = id;
        this.description = description;
        this.isDone = isDone;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id && isDone == item.isDone
                && Objects.equals(description, item.description)
                && Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, isDone);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", isDone=" + isDone
                + ", user=" + user
                + '}';
    }
}
