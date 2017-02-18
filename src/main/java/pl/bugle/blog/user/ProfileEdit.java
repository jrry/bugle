package pl.bugle.blog.user;

import pl.bugle.blog.dao.UsersDao;
import pl.bugle.blog.entity.Users;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.omnifaces.util.Messages;

@Named
@RequestScoped
public class ProfileEdit {
    
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Niepoprawny adres email")
    private String email;

    @NotNull(message = "Podaj swoje hasło")
    private String activePassword;

    @Size(min = 8, message = "Hasło powinno zawierać conajmniej 8 znaków")
    @Pattern.List({
        @Pattern(regexp = "(^.*[0-9].*$)", message = "Hasło powinno zawierać conajmniej 1 liczbę"),
        @Pattern(regexp = "(^.*[a-z].*$)", message = "Hasło powinno zawierać conajmniej 1 małą literę"),
        @Pattern(regexp = "(^.*[A-Z].*$)", message = "Hasło powinno zawierać conajmniej 1 dużą literę")
    })
    private String password;

    private final Users user;
    private final UsersDao dao;

    public String getUsername() {
        return user.getUser_name();
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserEmail() {
        return user.getUser_email();
    }
    
    public String getActivePassword() {
        return activePassword;
    }

    public void setActivePassword(String activePassword) {
        this.activePassword = activePassword;
    }
    
    public ProfileEdit() {
        dao = new UsersDao();
        user = dao.getUserById(Integer.parseInt((String) SecurityUtils.getSubject().getPrincipal()));
    }
    
    public void save() {
        boolean change = false;
        activePassword = new Sha256Hash(activePassword).toBase64();

        if (activePassword.equals(user.getUser_password())) {
            if (email != null) {
                Users tmp = dao.getUserByEmail(email);
                if (tmp != null) {
                    Messages.addGlobalError("Podano taki sam lub zajęty adres email");
                    return;
                }
                user.setUser_email(email.toLowerCase());
                change = true;
            }

            if (password != null) {
                user.setUser_password(password);
                change = true;
            }

            if (change) {
                dao.updateUser(user);
                Messages.addGlobalInfo("Zmiany zaakceptowane");
                return;
            }

            Messages.addGlobalWarn("Nie dokonano żadnych zmian");
        } else {
            Messages.addError("form:form-active-password", "Podane hasło jest nieprawidłowe");
        }
    }

}
