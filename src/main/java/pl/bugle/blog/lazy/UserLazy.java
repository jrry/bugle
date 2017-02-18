package pl.bugle.blog.lazy;

import pl.bugle.blog.dao.UsersDao;
import pl.bugle.blog.entity.Users;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class UserLazy extends LazyDataModel<Users> {

    private final UsersDao udao;
    private final int count;
    
    public UserLazy(UsersDao udao) {
        this.udao = udao;
        count = udao.countUsers();
    }

    @Override
    public List<Users> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return udao.getUsers(first, first, sortField, sortOrder);
    }
    
    @Override
    public int getRowCount() {
        return count;
    }

    @Override
    public Object getRowKey(Users object) {
        return object.getUser_id();
    }

    @Override
    public Users getRowData(String rowKey) {
        return udao.getUserById(Integer.parseInt(rowKey));
    }
}
