package pl.bugle.blog.view;

import pl.bugle.blog.dao.CategoriesDao;
import pl.bugle.blog.entity.Categories;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CategoryView {
    
    private final CategoriesDao cdao;

    public CategoryView() {
        this.cdao = new CategoriesDao();
    }
    
    public Categories getCategory(String seoname) {
        return cdao.getCategoryBySeoname(seoname);
    }
    
    public List<Categories> getAllCategories() {
        return cdao.getCategories();
    }
}
