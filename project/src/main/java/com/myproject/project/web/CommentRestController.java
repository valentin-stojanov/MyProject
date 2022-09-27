package com.myproject.project.web;

import com.myproject.project.model.dto.CommentViewModel;
import com.myproject.project.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController()
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(@PathVariable Long routeId,
                                                              Principal principal){

        return ResponseEntity.ok(this.commentService.getCommentsForRoute(routeId));
    }
}
