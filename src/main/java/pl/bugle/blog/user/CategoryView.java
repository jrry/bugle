package pl.bugle.blog.user;

import pl.bugle.blog.dao.CategoriesDao;
import pl.bugle.blog.entity.Categories;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
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
