package ru.learn.learnSpring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.learn.learnSpring.model.Post;
import ru.learn.learnSpring.model.Tags;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class TagRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void addTag(Tags tag) {
        getCurrentSession().save(tag);
    }

    public List<Tags> getAllTag() {
        return getCurrentSession().createQuery("from Tag t").list();
    }

    public Tags getSearchTag(String tag) {
        String query = "from Tag t where tag = '" + tag + "'";
        List<Tags> list = getCurrentSession().createQuery(query).list();
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<Post> getPostsByTag(String tag) {
        String query = String.format("select t.posts Tag t where t.tag=%s", tag);
        Query q = getCurrentSession().createQuery(query);
        return q.list();
    }

    public Tags getTagById(int id) {
        return getCurrentSession().get(Tags.class, id);
    }

    public void deleteTag(Tags tag) {
        getCurrentSession().delete(tag);
    }


    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}

