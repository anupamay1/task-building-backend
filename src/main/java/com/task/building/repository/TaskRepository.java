package com.task.building.repository;

import java.util.List;

import com.task.building.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByPublished(boolean published);

  List<Task> findByTitleContainingIgnoreCase(String title);
}
