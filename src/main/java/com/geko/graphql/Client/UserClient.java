package com.geko.graphql.Client;

import com.geko.graphql.Entity.Priority;
import com.geko.graphql.Entity.ToDoItem;
import com.geko.graphql.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserClient {
    private final HttpGraphQlClient httpGraphQlClient;

    @Autowired
    public UserClient(HttpGraphQlClient httpGraphQlClient) {
        this.httpGraphQlClient = httpGraphQlClient;
    }

    public List<User> getAllUsers()
    {
        String query = "query GetAllUsers {\n" +
                "    getAllUsers {\n" +
                "        username\n" +
                "        email\n" +
                "        toDoList {\n" +
                "            title\n" +
                "            priority\n" +
                "            status\n" +
                "        }\n" +
                "    }\n" +
                "}";

        return httpGraphQlClient
                .document(query)
                .retrieve("getAllUsers")
                .toEntityList(User.class).block();
    }

    public User getUser(Integer userId)
    {
        String query = String.format("query GetUser {\n" +
                "    getUser(userId: \"%d\") {\n" +
                "        id\n" +
                "        username\n" +
                "        email\n" +
                "    }\n" +
                "}", userId);

        return httpGraphQlClient
                .document(query)
                .retrieve("getUser")
                .toEntity(User.class).block();
    }


    public List<ToDoItem> getTasksOfUser(Integer userId)
    {
        String query = String.format("query GetUser {\n" +
                "    getTasksOfUser(userId: \"%d\") {\n" +
                "        title\n" +
                "        description\n" +
                "        priority\n" +
                "        status\n" +
                "        createdDate\n" +
                "        dueDate\n" +
                "    }\n" +
                "}", userId);

        return httpGraphQlClient
                .document(query)
                .retrieve("getTasksOfUser")
                .toEntityList(ToDoItem.class).block();
    }

    public String createUser(String username, String email, String password)
    {
        String query = String.format("mutation CreateUser {\n" +
                "    createUser(username: \"%s\", email: \"%s\", password: \"%s\")\n" +
                "}", username, email, password);

        return httpGraphQlClient
                    .document(query)
                    .retrieve("createUser")
                    .toEntity(String.class)
                    .block();
    }

    public boolean deleteUser(Integer userId)
    {
        String query = String.format("mutation DeleteUser {\n" +
                "    deleteUser(userId: \"%d\")\n" +
                "}", userId);

        return Boolean.TRUE.equals(
                httpGraphQlClient
                        .document(query)
                        .retrieve("deleteUser")
                        .toEntity(Boolean.class)
                        .block()
        );
    }

    public boolean createTask(Integer userId, String title, Priority priority, Float due)
    {
        String query = String.format("mutation DeleteUser {\n" +
                "    createTask(userId: \"%d\", title: \"%s\", priority: %s, due: %f)\n" +
                "}", userId, title, priority, due);

        return Boolean.TRUE.equals(
                httpGraphQlClient
                        .document(query)
                        .retrieve("createTask")
                        .toEntity(Boolean.class)
                        .block()
        );
    }

    public boolean removeTask(Integer taskId)
    {
        String query = String.format("mutation RemoveTask2 {\n" +
                "    removeTask(taskId: \"%d\")\n" +
                "}", taskId);

        return Boolean.TRUE.equals(
                httpGraphQlClient
                        .document(query)
                        .retrieve("removeTask")
                        .toEntity(Boolean.class)
                        .block()
        );
    }
}
