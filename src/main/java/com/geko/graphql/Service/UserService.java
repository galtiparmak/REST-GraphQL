package com.geko.graphql.Service;

import com.geko.graphql.Entity.Priority;
import com.geko.graphql.Entity.Status;
import com.geko.graphql.Entity.ToDoItem;
import com.geko.graphql.Entity.User;
import com.geko.graphql.Repository.ToDoItemRepository;
import com.geko.graphql.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ToDoItemRepository toDoItemRepository;

    @Autowired
    public UserService(UserRepository userRepository, ToDoItemRepository toDoItemRepository)
    {
        this.userRepository = userRepository;
        this.toDoItemRepository = toDoItemRepository;
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUser(Integer id)
    {
        return userRepository.findById(id).orElse(null);
    }

    public boolean createUser(String username, String email, String password) {
        try
        {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setToDoList(new ArrayList<>());
            userRepository.save(user);

            return true;

        } catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(Integer userId)
    {
        try
        {
            User user = getUserById(userId);

            userRepository.delete(user);
            return true;
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean createTask(Integer userId, String title, String description, Priority priority, long due)
    {
        try
        {
            User user = getUserById(userId);

            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setUser(user);
            toDoItem.setTitle(title);
            toDoItem.setDescription(description);
            toDoItem.setPriority(priority);
            toDoItem.setStatus(Status.IN_PROCESS);
            toDoItem.setCreatedDate(new Date(System.currentTimeMillis()));
            toDoItem.setDueDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * due));

            user.getToDoList().add(toDoItem); // because cascaded type is ALL, when the user saved, it will save the according todo-item.
            userRepository.save(user);

            return true;

        } catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean removeTask(Integer taskId)
    {
        try
        {
            Optional<ToDoItem> optionalToDoItem = toDoItemRepository.findById(taskId);
            if (optionalToDoItem.isEmpty())
            {
                return false;
            }
            ToDoItem toDoItem = optionalToDoItem.get();

            User user = toDoItem.getUser();
            user.getToDoList().remove(toDoItem); // because cascade type is ALL and orphan removal is true
            userRepository.save(user);           // when removing item from user's todo-list it will also delete the item from db

            return true;

        } catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            return false;
        }
    }

    public List<ToDoItem> getTasksOfUser(Integer userId)
    {
        User user = getUserById(userId);

        return user.getToDoList();
    }

    public List<ToDoItem> getTasksByPriority(Integer userId, Priority priority)
    {
        User user = getUserById(userId);
        List<ToDoItem> list = user.getToDoList();

        list.removeIf(
                item -> item.getPriority() != priority);

        return list;
    }

    private User getUserById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
        {
            throw new RuntimeException("User Not Found!");
        }
        return optionalUser.get();
    }
}
