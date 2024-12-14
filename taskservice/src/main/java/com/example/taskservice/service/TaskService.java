package com.example.taskservice.service;

import com.example.taskservice.model.Task;
import com.example.taskservice.model.TaskStatus;
import com.example.taskservice.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repo;

    public Task createTask(Task task, String requesterRole) throws Exception{
        if(!requesterRole.equals(("ROLE_ADMIN"))){
            throw new Exception("only admin can create task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return repo.save(task);
    }

    public Task getTaskById(Long id) throws Exception{
        return repo.findById(id).orElseThrow(()->new Exception("task not found with id " + id));
    }

    public List<Task> getAllTasks(TaskStatus status){
        List<Task> tasks = repo.findAll();

        List<Task> filteredTasks = tasks.stream().filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());

        return filteredTasks;
    }

    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception{
        Task existingTask = getTaskById(id);

        if(updatedTask.getTitle()!=null){
            existingTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getImage()!=null){
            existingTask.setImage(updatedTask.getImage());
        }
        if(updatedTask.getDescription()!=null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getStatus()!=null){
            existingTask.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDeadline()!=null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }
        return repo.save(existingTask);
    }

    public void deleteTask(Long id) throws Exception{
        getTaskById(id);
        repo.deleteById(id);
    }

    public Task assignedToUser(Long userId, Long taskId) throws Exception{
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return repo.save(task);
    }

    public List<Task> assignedUsersTask(Long userId, TaskStatus status){
        List<Task> tasks = repo.findByAssignedUserId(userId);

        List<Task> filteredTasks = tasks.stream().filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());

        return filteredTasks;
    }

    public Task completeTask(Long taskId) throws Exception{
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return repo.save(task);
    }
}
