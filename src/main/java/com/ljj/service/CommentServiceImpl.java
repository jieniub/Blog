package com.ljj.service;

import com.ljj.dao.CommentRepository;
import com.ljj.pojo.Comment;
import com.ljj.utils.CommentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository repository;

    @Override
    public List<Comment> ListCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");


        return CommentUtils.eachComment(repository.findByBlogIdAndParentCommentNull(blogId,sort));
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
//        if (comment.getParentComment() == null){
//            comment.setParentComment(null);
//            return repository.save(comment);
//        }
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(repository.getById(parentCommentId));
        }
        else{
             comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return repository.save(comment);
    }
}
