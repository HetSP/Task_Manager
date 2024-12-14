package com.example.submission.service;

import com.example.submission.model.Submission;
import com.example.submission.model.TaskDto;
import com.example.submission.model.UserDto;
import com.example.submission.repo.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository repo;

    @Autowired
    private TaskService taskService;

    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception{
        TaskDto task = taskService.getTaskById(taskId,jwt);
        if(task != null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return repo.save(submission);
        }
        throw new Exception("Task not found with id: "+taskId);
    }

    public Submission getTaskSubmissionById(Long submissionId) throws Exception{
        return repo.findById(submissionId).orElseThrow(()->new Exception("Task Submission not found with id: "+submissionId));
    }

    public List<Submission> getAllTaskSubmissions(){
        return repo.findAll();
    }

    public List<Submission> getTaskSubmissionsById(Long taskId){
        return repo.findByTaskId(taskId);
    }

    public Submission acceptDeclineSubmission(Long id, String status) throws Exception{
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equals("ACCEPT")){
            taskService.completeTask(submission.getTaskId());
        }
        return repo.save(submission);
    }
}
