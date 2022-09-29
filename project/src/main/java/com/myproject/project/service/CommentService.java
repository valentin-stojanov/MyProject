package com.myproject.project.service;

import com.myproject.project.model.dto.CommentViewModel;
import com.myproject.project.model.dto.NewCommentDto;
import com.myproject.project.model.entity.CommentEntity;
import com.myproject.project.model.entity.RouteEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.repository.CommentRepository;
import com.myproject.project.repository.RouteRepository;
import com.myproject.project.repository.UserRepository;
import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          RouteRepository routeRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
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
        RouteEntity routeEntity = this.routeRepository
                .findById(newCommentDto.getRouteId())
                .orElseThrow(() -> new ObjectNotFoundException("Route with id " + newCommentDto.getRouteId() + " not found!!!"));

        UserEntity author = this.userRepository
                .findByEmail(newCommentDto.getCreator())
                .orElseThrow(() -> new ObjectNotFoundException("User with email " + newCommentDto.getCreator() + " not found!!!"));

        CommentEntity newComment = new CommentEntity();
        newComment
                .setText(newCommentDto.getMessage())
                .setCreated(LocalDateTime.now())
                .setRoute(routeEntity)
                .setAuthor(author);

        CommentEntity savedComment = this.commentRepository.save(newComment);

        return mapAsComment(savedComment);
    }
}
