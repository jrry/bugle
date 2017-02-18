package pl.bugle.blog.lazy;

import pl.bugle.blog.dao.CategoriesDao;
import pl.bugle.blog.entity.Categories;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class CategoryLazy extends LazyDataModel<Categories> {

    private final CategoriesDao cdao;
    private final int count;
    
    public CategoryLazy(CategoriesDao cdao) {
        this.cdao = cdao;
        count = cdao.countCategories();
    }
    
    @Override
    public List<Categories> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return cdao.getCategories(first, first+pageSize, sortField, sortOrder);
    }

    @Override
    public int getRowCount() {
        return count;
    }

    @Override
    public Object getRowKey(Categories object) {
        return object.getCategory_id();
    }

    @Override
    public Categories getRowData(String rowKey) {
        return cdao.getCategoryById(Integer.parseInt(rowKey));
    }

}
