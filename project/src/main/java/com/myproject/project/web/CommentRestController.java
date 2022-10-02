package com.myproject.project.web;

import com.myproject.project.model.dto.CommentViewModel;
import com.myproject.project.model.dto.NewCommentDto;
import com.myproject.project.model.validation.ApiError;
import com.myproject.project.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping("/api/{routeId}/comments")
    public ResponseEntity<CommentViewModel> newComment(@PathVariable Long routeId,
                                                       @AuthenticationPrincipal UserDetails principal,
                                                       @RequestBody @Valid NewCommentDto newCommentDto){

        newCommentDto
                .setRouteId(routeId)
                .setCreator(principal.getUsername());

        CommentViewModel newComment = this.commentService.createComment(newCommentDto);
        URI locationOfNewComment = URI.create(String.format("/api/%s/comments/%s", routeId, newComment.getCommentId()));

        return ResponseEntity
                .created(locationOfNewComment)
                .body(newComment);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exc){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exc.getFieldErrors()
                .forEach(fe -> apiError.addFieldWithError(fe.getField()));

        return ResponseEntity
                .badRequest()
                .body(apiError);
    }
}
