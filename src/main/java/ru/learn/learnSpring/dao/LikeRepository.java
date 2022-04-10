package ru.learn.learnSpring.dao;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.learn.learnSpring.model.PostComments;
import ru.learn.learnSpring.model.PostVotes;

import java.util.List;

public class LikeRepository {
    @Autowired
    SessionFactory sessionFactory;


    public List<PostVotes> getPostLikesListByPostId(int id){

        String query = String.format("from PostLike likes where likes.post=%d", id);
        return (List<PostVotes>) getCurrentSession().createQuery(query).list();
    }

    public PostVotes getLikedPost(int userId, int postId){

        String query = String.format("from PostLike likes where likes.post=%d and likes.person=%d", postId,userId);

        try {
            return (PostVotes) getCurrentSession().createQuery(query).uniqueResult();
        } catch(NonUniqueResultException e){
            return (PostVotes) getCurrentSession().createQuery(query).list().get(0);
        }
    }


    public void addPostLike(PostVotes postLike) {
        getCurrentSession().save(postLike);
    }

    public void updatePostLike(PostVotes postLike){
        getCurrentSession().update(postLike);
    }

    public void deletePostLike(PostVotes postLike) {
        getCurrentSession().delete(postLike);
    }

    public List<PostComments> getCommentLikesListByCommentId(int id){

        String query = String.format("from CommentLike likes where likes.postComment=%d", id);
        return (List<PostComments>) getCurrentSession().createQuery(query).list();
    }

    public PostComments getLikedComment(int userId, int postCommentId){

        String query = String.format("from CommentLike likes where likes.postComment=%d and likes.person=%d", postCommentId,userId);

        try {
            return (PostComments) getCurrentSession().createQuery(query).uniqueResult();
        } catch(NonUniqueResultException e){
            return (PostComments) getCurrentSession().createQuery(query).list().get(0);
        }
    }


    public void addCommentLike(PostComments postComment) {
        getCurrentSession().save(postComment);
    }

    public void updateCommentLike(PostComments postComment){
        getCurrentSession().update(postComment);
    }

    public void deleteCommentLike(PostComments postComment) {
        getCurrentSession().delete(postComment);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
