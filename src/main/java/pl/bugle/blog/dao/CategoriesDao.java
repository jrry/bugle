package pl.bugle.blog.dao;

import pl.bugle.blog.entity.Categories;
import pl.bugle.blog.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

public class CategoriesDao {
    private Session session;
    private Transaction tr;
    
    public List<Categories> getCategories() {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        List<Categories> lcat = session.getNamedQuery("Categories.findAll").list();
        tr.commit();
        HibernateUtil.closeSession();
        return lcat;
    }
    
    public List<Categories> getCategories(int offset, int limit, String sortField, SortOrder sortOrder) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Criteria crit = session.createCriteria(Categories.class);
        crit.setFirstResult(offset).setMaxResults(limit);
        crit.createAlias("category_entries", "ce", JoinType.LEFT_OUTER_JOIN);
        crit.setProjection(Projections.projectionList()
        .add(Projections.groupProperty("category_id"), "category_id")
        .add(Projections.property("category_name"), "category_name")
        .add(Projections.property("category_seoname"), "category_seoname")
        .add(Projections.count("ce.entry_id"), "category_entries_count"));
        
        if (sortField != null && !sortField.isEmpty()) {
            if (sortOrder.equals(SortOrder.ASCENDING)) {
                crit.addOrder(Order.asc(sortField));
            } else {
                crit.addOrder(Order.desc(sortField));
            }
        }
        
        crit.setResultTransformer(Transformers.aliasToBean(Categories.class));
        List<Categories> lcat = crit.list();
        tr.commit();
        HibernateUtil.closeSession();
        return lcat;
    }
    
    public int countCategories() {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        int c = ((Long) session.getNamedQuery("Categories.countAll").uniqueResult()).intValue();
        tr.commit();
        HibernateUtil.closeSession();
        return c;
    }
    
    public Categories getCategoryById(Integer id) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Categories cat = (Categories) session.getNamedQuery("Categories.findByCategory_Id").setInteger("category_id", id).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return cat;
    }
    
    public Categories getCategoryBySeoname(String seoname) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        Categories cat = (Categories) session.getNamedQuery("Categories.findByCategory_Seoname").setString("category_seoname", seoname).uniqueResult();
        tr.commit();
        HibernateUtil.closeSession();
        return cat;
    }
        
    public void createCategory(Categories cat) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.persist(cat);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void updateCategory(Categories cat) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.merge(cat);
        tr.commit();
        HibernateUtil.closeSession();
    }
    
    public void deleteCategory(Categories cat) {
        session = HibernateUtil.getSession();
        tr = session.beginTransaction();
        session.delete(cat);
        tr.commit();
        HibernateUtil.closeSession();
    }   
}
