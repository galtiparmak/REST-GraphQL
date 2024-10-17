package com.geko.graphql.Service;

import com.geko.graphql.Entity.Priority;
import com.geko.graphql.Entity.ToDoItem;
import com.geko.graphql.Repository.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoItemService {
    private final ToDoItemRepository toDoItemRepository;

    @Autowired
    public ToDoItemService(ToDoItemRepository toDoItemRepository)
    {
        this.toDoItemRepository = toDoItemRepository;
    }

    public List<ToDoItem> getAllToDoItems()
    {
        return toDoItemRepository.findAll();
    }

    public ToDoItem getToDoItem(Integer id)
    {
        return toDoItemRepository.findById(id).orElse(null);
    }
}
