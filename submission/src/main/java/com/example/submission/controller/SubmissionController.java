package com.example.submission.controller;

import com.example.submission.model.Submission;
import com.example.submission.model.UserDto;
import com.example.submission.service.SubmissionService;
import com.example.submission.service.TaskService;
import com.example.submission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<Submission> submitTask(
            @RequestParam Long task_id,
            @RequestParam String github_link,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        UserDto user = userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(task_id, github_link, user.getId(),jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission>getSubmissionById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllSubmissions(){
        List<Submission> submissions = submissionService.getAllTaskSubmissions();
        return new ResponseEntity<>(submissions,HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getAllSubmissionsForTask(@PathVariable Long taskId, @RequestHeader("Authorization") String jwt) throws Exception{
        List<Submission> submissions = submissionService.getTaskSubmissionsById(taskId);
        return new ResponseEntity<>(submissions,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(@PathVariable Long id, @RequestParam("status") String status, @RequestHeader("Authorization") String jwt) throws Exception {
        Submission submission = submissionService.acceptDeclineSubmission(id, status);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
}
