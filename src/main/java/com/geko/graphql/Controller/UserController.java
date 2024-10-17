package com.geko.graphql.Controller;

import com.geko.graphql.Entity.Priority;
import com.geko.graphql.Entity.ToDoItem;
import com.geko.graphql.Entity.User;
import com.geko.graphql.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * -------------QUERY MAPPING-------------
     */

    @QueryMapping
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @QueryMapping
    public User getUser(@Argument Integer userId)
    {
        return userService.getUser(userId);
    }

    @QueryMapping
    public List<ToDoItem> getTasksOfUser(@Argument Integer userId)
    {
        return userService.getTasksOfUser(userId);
    }

    @QueryMapping
    public List<ToDoItem> getTasksByTheirPriority(@Argument Integer userId, @Argument Priority priority)
    {
        return userService.getTasksByPriority(userId, priority);
    }



     /**
      * -------------MUTATION MAPPING-------------
    */

    @MutationMapping
    public String createUser(@Argument String username, @Argument String email, @Argument String password)
    {
        if (userService.createUser(username, email, password))
        {
            return "User Created Successfully";
        }
        return "Error At User Creation";
    }

    @MutationMapping
    public String deleteUser(@Argument Integer userId)
    {
        if (userService.deleteUser(userId))
        {
            return "User Deleted Successfully";
        }
        return "Error At User Deletion";
    }

    @MutationMapping
    public String createTask(@Argument Integer userId,
                             @Argument String title,
                             @Argument String description,
                             @Argument Priority priority,
                             @Argument long due)
    {
        if (userService.createTask(userId, title, description, priority, due))
        {
            return "Task Created Successfully";
        }
        return "Error At Task Creation";
    }

    @MutationMapping
    public String removeTask(@Argument Integer taskId)
    {
        if (userService.removeTask(taskId))
        {
            return "Task Removed Successfully";
        }
        return "Error At Task Remove";
    }

}
