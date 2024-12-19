package edu.upc.project;

import edu.upc.project.models.ElementType;
import edu.upc.project.models.FAQ;
import edu.upc.project.models.Item;
import edu.upc.project.models.User;

import java.util.List;

public interface GameManager {

    public FAQ addFAQ(FAQ question);
    public FAQ createFAQ(String date, String question, String answer, String sender);
    public List<FAQ> getFAQS();
    public int sizeQuestionsFAQs();

    public User createUser(Integer id, String name, String password, String email, Integer money,Integer puntos);
    public User addUser(User user);
    public User getUser(Integer id);

    public List<User> listUsers();
    public Integer addItemInventory(Integer userID, Integer itemID);

    public int sizeUsers();
    public int sizeItemsStore();

    public Item addItem(Item item);
    public Item createItem(Integer id, ElementType type, Integer value);
    public Item getItem(Integer id);
    public List<Item> getItems();
    public List<Item> listItembyType(ElementType type);

    public void clear();
}
