package pl.bugle.blog.dao;

import pl.bugle.blog.entity.Users;
import pl.bugle.blog.config.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

public class UsersDao {
    
    private Session session;
    private Transaction tr;

    public List<Users> getUsers(int offset, int limit, String sortField, SortOrder sortOrder) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Criteria crit = session.createCriteria(Users.class);
        crit.setFirstResult(offset).setMaxResults(limit);
        crit.createAlias("user_entries", "ue", JoinType.LEFT_OUTER_JOIN);
        crit.setProjection(Projections.projectionList()
        .add(Projections.groupProperty("user_id"), "user_id")
        .add(Projections.property("user_name"), "user_name")
        .add(Projections.property("user_role"), "user_role")
        .add(Projections.property("user_email"), "user_email")
        .add(Projections.property("user_password"), "user_password")
        .add(Projections.count("ue.entry_id"),"user_entries_count"));
        
        if (sortField != null && !sortField.isEmpty()) {
            if (sortOrder.equals(SortOrder.ASCENDING)) {
                crit.addOrder(Order.asc(sortField));
            } else {
                crit.addOrder(Order.desc(sortField));
            }
        }
        
        crit.setResultTransformer(Transformers.aliasToBean(Users.class));
        List<Users> luser = crit.list();
        tr.commit();
        HibernateUtil.closeSession();
        return luser;
    }
    
    public int countUsers() {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        int c = ((Long) session.getNamedQuery("Users.countAll").uniqueResult()).intValue();
        tr.commit();
        HibernateUtil.closeSession();
        return c;
    }
    
    public Users getUserById(Integer user_id) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Users user = (Users) session.getNamedQuery("Users.findByUser_id").setParameter("user_id", user_id).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return user;
    }
    
    public Users getUserByUsername(String user_name) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Users user = (Users) session.getNamedQuery("Users.findByUser_name").setParameter("user_name", user_name).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return user;
    }
    
    public Users getUserByEmail(String user_email) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Users user = (Users) session.getNamedQuery("Users.findByUser_email").setParameter("user_email", user_email).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return user;
    }
    
    public void createUser(Users u) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.persist(u);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void updateUser(Users u) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.merge(u);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void deleteUser(Users u) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.delete(u);
        tr.commit();
        HibernateUtil.closeSession();
    }
}
