package pl.bugle.blog.admin;

import pl.bugle.blog.dao.CategoriesDao;
import pl.bugle.blog.entity.Categories;
import pl.bugle.blog.lazy.CategoryLazy;
import org.primefaces.model.LazyDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CategoryEdit {

    private final CategoriesDao cdao;
    
    private Categories selectedCategory;
    private LazyDataModel<Categories> lazyCategories;

    public CategoryEdit() {
        cdao = new CategoriesDao();
        lazyCategories = new CategoryLazy(cdao);
        selectedCategory = new Categories();
    }

    public Categories getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Categories selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public LazyDataModel<Categories> getLazyCategories() {
        return lazyCategories;
    }

    public void setLazyCategories(LazyDataModel<Categories> lazyCategories) {
        this.lazyCategories = lazyCategories;
    }

    public void save() {
        cdao.updateCategory(selectedCategory);
    }
    
    public void delete() {
        cdao.deleteCategory(selectedCategory);
    }
}
