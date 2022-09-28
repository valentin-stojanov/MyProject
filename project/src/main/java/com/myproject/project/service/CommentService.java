package com.myproject.project.service;

import com.myproject.project.model.dto.CommentViewModel;
import com.myproject.project.model.dto.NewCommentDto;
import com.myproject.project.model.entity.CommentEntity;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.repository.CommentRepository;
import com.myproject.project.repository.RouteRepository;
import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final RouteRepository routeRepository;

    public CommentService(CommentRepository commentRepository, RouteRepository routeRepository) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
    }

    @Transactional
    public List<CommentViewModel> getCommentsForRoute(Long routeId) {
        Optional<RouteEntity> routeOpt = this.routeRepository.findById(routeId);

        if (routeOpt.isEmpty()) {
            throw new ObjectNotFoundException("Route with id " + routeId + "was not found!");
        }

        return routeOpt
                .get()
                .getComments()
                .stream()
                .map(this::mapAsComment)
                .toList();
    }

    private CommentViewModel mapAsComment(CommentEntity commentEntity) {
        CommentViewModel commentViewModel = new CommentViewModel();
        commentViewModel
                .setCommentId(commentEntity.getId())
                .setCanApprove(true)
                .setCanDelete(true)
                .setCreated(commentEntity.getCreated())
                .setMessage(commentEntity.getText())
                .setUser(commentEntity.getAuthor().getFirstName() + " " + commentEntity.getAuthor().getLastName());

        return commentViewModel;
    }

    public CommentViewModel createComment(NewCommentDto newCommentDto) {
// TODO: create and save ne comment.
    }
}
