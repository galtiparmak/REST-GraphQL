package com.geko.graphql.Repository;

import com.geko.graphql.Entity.Priority;
import com.geko.graphql.Entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Integer> {
    List<ToDoItem> findAllByPriority(Priority priority);
}
