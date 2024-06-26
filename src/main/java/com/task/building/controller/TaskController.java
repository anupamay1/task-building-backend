package com.task.building.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.task.building.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.building.repository.TaskRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TaskController {

  @Autowired
  TaskRepository taskRepository;

  @GetMapping("/tasks")
  public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) String title) {
    try {
      List<Task> tasks = new ArrayList<Task>();

      if (title == null)
        taskRepository.findAll().forEach(tasks::add);
      else
        taskRepository.findByTitleContainingIgnoreCase(title).forEach(tasks::add);

      if (tasks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tasks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/tasks/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) {
    Optional<Task> taskData = taskRepository.findById(id);

    if (taskData.isPresent()) {
      return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/tasks")
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
    try {
      Task _task = taskRepository.save(new Task(task.getTitle(), task.getDescription(), false));
      return new ResponseEntity<>(_task, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/tasks/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
    Optional<Task> taskData = taskRepository.findById(id);

    if (taskData.isPresent()) {
      Task _task = taskData.get();
      _task.setTitle(task.getTitle());
      _task.setDescription(task.getDescription());
      _task.setPublished(task.isPublished());
      return new ResponseEntity<>(taskRepository.save(_task), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
    try {
      taskRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/tasks")
  public ResponseEntity<HttpStatus> deleteAllTasks() {
    try {
      taskRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/tasks/published")
  public ResponseEntity<List<Task>> findByPublished() {
    try {
      List<Task> tasks = taskRepository.findByPublished(true);

      if (tasks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tasks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
