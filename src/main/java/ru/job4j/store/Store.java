package ru.job4j.store;

import ru.job4j.model.Item;

import java.util.List;

public interface Store {
    Item add(Item item);
    Item replace(int id);
    List<Item> findAllItems();
    List<Item> findNotDoneItems();
    Item findById(int id);
}
