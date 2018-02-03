package pl.bugle.blog.user;

import com.github.jrry.pvl.PVL_Email;
import pl.bugle.blog.dao.UsersDao;
import pl.bugle.blog.entity.Users;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.omnifaces.util.Messages;

@ManagedBean
@RequestScoped
public class Register {

    private Users user;
    private final UsersDao dao;
    
    @NotNull(message = "Wpisz nazwę użytkownika")
    @Size(min = 5, max = 25, message = "Niepoprawna długość (min 5, max 25 znaków)")
    private String username;

    @Size(min = 6, max = 100, message = "Niepoprawna długość")
    @PVL_Email(message = "Niepoprawny adres email")
    private String email;
    
    @NotNull(message = "Wpisz hasło")
    @Size(min = 8, max = 30, message = "Niepoprawna długość (min 8, max 30 znaków)")
    @Pattern.List({
        @Pattern(regexp = "(^.*[0-9].*$)", message = "Hasło powinno zawierać conajmniej 1 liczbę"),
        @Pattern(regexp = "(^.*[a-z].*$)", message = "Hasło powinno zawierać conajmniej 1 małą literę"),
        @Pattern(regexp = "(^.*[A-Z].*$)", message = "Hasło powinno zawierać conajmniej 1 dużą literę")
    })
    private String password;

    public Register() {
        dao = new UsersDao();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String registerTry() {
        user = dao.getUserByUsername(username);

        if(user != null) {
            Messages.addGlobalError("Nazwa użytkownika jest zajęta");
            return null;
        } else {
            user = dao.getUserByEmail(email);
            if(user != null) {
               Messages.addGlobalError("Adres email jest zajęty");
               return null;
            } else {
                user = new Users(username, "user", email.toLowerCase(), password);
                dao.createUser(user);
                try {
                    SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUser_id().toString(), password));
                } catch (AuthenticationException e) {}
            }
        }

        return "pretty:home";
    }
}
