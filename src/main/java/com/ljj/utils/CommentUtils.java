package com.ljj.utils;

import com.ljj.pojo.Comment;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentUtils {
    private static List<Comment> tempReplys = new ArrayList<>();

    public static List<Comment> eachComment(List<Comment> comments){
        List<Comment> temp = new ArrayList<>();
        for (Comment comment: comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            temp.add(c);
        }
        combineChildren(temp);
        return temp;
    }

    public static void combineChildren(List<Comment> comments){
        for (Comment comment : comments){
            List<Comment> replys = comment.getReplyComment();
            for (Comment reply : replys){
                recursively(reply);
            }
            comment.setReplyComment(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    public static void recursively(Comment comment){
        tempReplys.add(comment);
        if (comment.getReplyComment().size()>0){
            List<Comment> replys = comment.getReplyComment();
            for (Comment reply : replys){
                tempReplys.add(reply);
                if (reply.getReplyComment().size() > 0){
                    recursively(reply);
                }
            }
        }
    }
}
