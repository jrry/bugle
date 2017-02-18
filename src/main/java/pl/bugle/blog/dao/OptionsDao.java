package pl.bugle.blog.dao;

import pl.bugle.blog.entity.Options;
import pl.bugle.blog.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OptionsDao {
    private Session session;
    private Transaction tr;

    public Options getOptionById(Integer option_id) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Options option = (Options) session.getNamedQuery("Options.findById").setParameter("option_id", option_id).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return option == null ? new Options() : option;
    }

    public void setOption(Options o) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.saveOrUpdate(o);
        tr.commit();
        HibernateUtil.closeSession();
    }
}
