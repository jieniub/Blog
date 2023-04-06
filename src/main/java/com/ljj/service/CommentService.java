package com.ljj.service;

import com.ljj.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> ListCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
