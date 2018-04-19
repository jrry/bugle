package pl.bugle.blog.dao;

import pl.bugle.blog.entity.Categories;
import pl.bugle.blog.entity.Entries;
import pl.bugle.blog.entity.Ratings;
import pl.bugle.blog.config.HibernateUtil;
import java.util.List;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

public class EntriesDao {
    private Session session;
    private Transaction tr;
    
    public List<Entries> getEntries() {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Criteria crit = session.createCriteria(Entries.class)
                .setProjection(Projections.projectionList()
                .add(Projections.property("entry_seotitle"), "entry_seotitle")
                .add(Projections.property("entry_date"), "entry_date"))
                .setResultTransformer(Transformers.aliasToBean(Entries.class))
                .addOrder(Order.desc("entry_date"));
        List<Entries> lbg = crit.list();
        tr.commit();
        HibernateUtil.closeSession();
        return lbg;
    }
    
    public List<Entries> getEntries(int offset, int limit) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        List<Entries> lbg = session.getNamedQuery("Entries.findAll").setFirstResult(offset).setMaxResults(limit).list();
        tr.commit();
        HibernateUtil.closeSession();
        return lbg;
    }
    
    public List<Entries> getEntries(int offset, int limit, String sortField, SortOrder sortOrder) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Criteria crit = session.createCriteria(Entries.class);
        crit.setFirstResult(offset).setMaxResults(limit);
        
        if (sortField != null && !sortField.isEmpty()) {
            if (sortOrder.equals(SortOrder.ASCENDING)) {
                crit.addOrder(Order.asc(sortField));
            } else {
                crit.addOrder(Order.desc(sortField));
            }
        }
        
        List<Entries> lbg = crit.list();
        tr.commit();
        HibernateUtil.closeSession();
        return lbg;
    }
   
    public int countEntries() {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        int c = ((Long) session.getNamedQuery("Entries.countAll").uniqueResult()).intValue();
        tr.commit();
        HibernateUtil.closeSession();
        return c;
    }
    
    public List<Entries> searchEntries(int offset, int limit, String query) {
        session = HibernateUtil.getSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        tr = session.beginTransaction();
        try {
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException ex) {}
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Entries.class).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("entry_title", "entry_content").matching(query).createQuery();
        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Entries.class);
        Sort sort = new Sort(new SortField("entry_date", SortField.Type.LONG, true));
        List<Entries> lbg = fullTextQuery.setSort(sort).setFirstResult(offset).setMaxResults(limit).list();
        tr.commit();
        HibernateUtil.closeSession();
        return lbg;
    }
    
    public Entries getEntryById(Integer id) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Entries bg = (Entries) session.getNamedQuery("Entries.findByEntry_id").setInteger("entry_id", id).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return bg;
    }
    
    public Entries getEntryBySeotitle(String seotitle, boolean init_categories) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Entries bg = (Entries) session.getNamedQuery("Entries.findByEntry_seotitle").setString("entry_seotitle", seotitle).uniqueResult();
        if (init_categories && bg != null)
            Hibernate.initialize(bg.getEntry_categories());
        tr.commit();
        HibernateUtil.closeSession();
        return bg;
    }
    
    public List<Entries> getEntryByCategorySeotitle(int offset, int limit, String categoryseotitle) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        List<Entries> lbg = session.getNamedQuery("Entries.findByEntry_category").setString("category_seoname", categoryseotitle).setFirstResult(offset).setMaxResults(limit).list();
        tr.commit();
        HibernateUtil.closeSession();
        return lbg;
    }
    
    public void createEntry(Entries entry) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        for(Categories cat: entry.getEntry_categories()) {
            session.refresh(cat);
            cat.addEntry(entry);
        }
        session.persist(entry);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void updateEntry(Entries entry) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();      
        session.merge(entry);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void deleteEntry(Entries entry) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.refresh(entry);
        entry.getEntry_author().removeEntry(entry);
        for(Categories cat: entry.getEntry_categories()) {
            cat.removeEntry(entry);
        }
        entry.getEntry_categories().clear();
        List<Ratings> lrt = session.getNamedQuery("Ratings.findByRating_Entry").setInteger("rating_entry", entry.getEntry_id()).list();
        for(Ratings rat: lrt) {
            session.delete(rat);
        }
        session.delete(entry);
        tr.commit();
        HibernateUtil.closeSession();
    }
}
