package pl.bugle.blog.admin;

import pl.bugle.blog.dao.UsersDao;
import pl.bugle.blog.entity.Users;
import pl.bugle.blog.lazy.UserLazy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;

@Named
@RequestScoped
public class UserEdit {
    
    private final UsersDao udao;
    
    private Users selectedUser;
    private LazyDataModel<Users> lazyUsers;
    
    private String password;

    public UserEdit() {
        this.udao = new UsersDao();
        lazyUsers = new UserLazy(udao);
        selectedUser = new Users();
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public LazyDataModel<Users> getLazyUsers() {
        return lazyUsers;
    }

    public void setLazyUsers(LazyDataModel<Users> lazyUsers) {
        this.lazyUsers = lazyUsers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void save() {
        Users user = udao.getUserById(selectedUser.getUser_id());
        user.setUser_name(selectedUser.getUser_name());
        user.setUser_email(selectedUser.getUser_email());
        user.setUser_role(selectedUser.getUser_role());
        if (password != null)
            user.setUser_password(password);
        udao.updateUser(user);
    }
    
    public void delete() {
        udao.deleteUser(selectedUser);
    }
}
