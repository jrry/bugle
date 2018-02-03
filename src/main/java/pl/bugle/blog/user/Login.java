package pl.bugle.blog.user;

import pl.bugle.blog.dao.UsersDao;
import pl.bugle.blog.entity.Users;
import java.io.IOException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

@ManagedBean
@RequestScoped
public class Login {
    
    @NotNull(message = "Wpisz nazwę użytkownika")
    @Size(min = 3, max = 15, message = "Niepoprawna długość")
    private String username;

    @NotNull(message = "Wpisz hasło")
    private String password;
    
    private boolean rememberMe;

    private final UsersDao dao;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
    
    public Login() {
        dao = new UsersDao();
    }
    
    public void loginTry() throws IOException {
        try {
            Users user = dao.getUserByUsername(username);
            if (user == null)
                throw new UnknownAccountException();
            SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUser_id().toString(), password, rememberMe));
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : "home.xhtml");
        } catch (UnknownAccountException | IncorrectCredentialsException uaie) {
            Messages.addGlobalError("Niepoprawna nazwa użytkownika lub hasło");
        } catch (AuthenticationException ae) {
            Messages.addGlobalFatal("Wystąpił błąd logowania. Spróbuj ponownie");
        }
    }
    
    public void logoutTry() throws IOException {
        SecurityUtils.getSubject().logout();
        Faces.invalidateSession();
        Faces.redirect("home.xhtml");
    }
    
    public String checkAccess() {
        if (SecurityUtils.getSubject().isAuthenticated())
            return "pretty:home";
        return null;
    }
    
}
