package com.geko.graphql.Controller;

import com.geko.graphql.Client.UserClient;
import com.geko.graphql.Entity.Priority;
import com.geko.graphql.Entity.ToDoItem;
import com.geko.graphql.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRESTController {
    private final UserClient userClient;

    @Autowired
    public UserRESTController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll()
    {
        List<User> users = userClient.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam Integer userId)
    {
        User user = userClient.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUserTasks")
    public ResponseEntity<List<ToDoItem>> getTasks(@RequestParam Integer userId)
    {
        List<ToDoItem> tasks = userClient.getTasksOfUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/getTasksByTheirPriority")
    public ResponseEntity<List<ToDoItem>> getTasksByTheirPriority(@RequestParam Integer userId, @RequestParam Priority priority)
    {
        List<ToDoItem> list = userClient.getTasksByTheirPriority(userId, priority);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestParam String username,
                                             @RequestParam String email,
                                             @RequestParam String password)
    {
        String res = userClient.createUser(username, email, password);
        return ResponseEntity.ok().body(res);
    }
}
