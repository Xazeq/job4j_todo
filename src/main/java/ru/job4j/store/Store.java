package ru.job4j.store;

import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.util.List;

public interface Store {
    Item add(Item item);
    Item replace(int id);
    List<Item> findAllItems();
    List<Item> findNotDoneItems();
    Item findById(int id);
    User findUserByUsername(String username);
    User add(User user);
    User findUserByEmail(String email);
    List<Item> findAllItemsByUser(User user);
    List<Item> findNotDoneItemsByUser(User user);
}
