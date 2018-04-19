package pl.bugle.blog.dao;

import pl.bugle.blog.entity.Ratings;
import pl.bugle.blog.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RatingsDao {
    private Session session;
    private Transaction tr;
    
    public Ratings getRatingById(Integer id) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Ratings rt = (Ratings) session.getNamedQuery("Ratings.findById").setParameter("rating_id", id).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return rt;
    }

    public int countEntryRank(String entry_seotitle) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        int c = ((Long) session.getNamedQuery("Ratings.countEntry_Rank_By_Entry_Seotitle").setParameter("entry_seotitle", entry_seotitle).uniqueResult()).intValue();
        tr.commit();
        HibernateUtil.closeSession();
        return c;
    }

    public Double getEntryRank(String entry_seotitle) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Double c = ((Double) session.getNamedQuery("Ratings.avgEntry_Rank_By_Entry_Seotitle").setParameter("entry_seotitle", entry_seotitle).uniqueResult());
        tr.commit();
        HibernateUtil.closeSession();
        return c;
    }
    
    public Ratings getRatingByUserIdAndEntrySeotitle(Integer user_id, String entry_seotitle) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Ratings rt = (Ratings) session.getNamedQuery("Ratings.findByRating_Author_And_Entry_Seotitle").setParameter("user_id", user_id).setParameter("entry_seotitle", entry_seotitle).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return rt;
    }
    
    public void createRating(Ratings rt) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.persist(rt);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void updateRating(Ratings rt) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.merge(rt);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void deleteRating(Ratings rt) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.delete(rt);
        tr.commit();
        HibernateUtil.closeSession();
    }
}
