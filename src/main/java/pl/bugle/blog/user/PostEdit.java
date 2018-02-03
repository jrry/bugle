package pl.bugle.blog.user;

import pl.bugle.blog.dao.EntriesDao;
import pl.bugle.blog.dao.UsersDao;
import pl.bugle.blog.entity.Categories;
import pl.bugle.blog.entity.Entries;
import pl.bugle.blog.entity.Images;
import pl.bugle.blog.entity.Users;
import pl.bugle.blog.util.UploadUtil;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.shiro.SecurityUtils;
import org.omnifaces.util.Messages;
import org.primefaces.model.UploadedFile;

@ManagedBean
@RequestScoped
public class PostEdit {

    @NotNull(message = "Brak tytułu")
    @Size(min = 10, max = 100, message = "Niepoprawna długość (min 10, max 100 znaków)")
    private String title;

    @NotNull(message = "Brak adresu SEO")
    @Size(min = 10, max = 100, message = "Niepoprawna długość (min 10, max 100 znaków)")
    @Pattern(regexp = "[a-z0-9-]+", message = "Adres SEO może zawierać małe litery, cyfry oraz znak -")
    private String seotitle;

    @NotNull(message = "Brak opisu")
    @Size(min = 10, max = 155, message = "Niepoprawna długość (min 10, max 155 znaków)")
    private String seodesc;

    private List<Categories> categories;

    @NotNull(message = "Brak opisu zdjęcia")
    @Size(min = 10, max = 100, message = "Niepoprawna długość (min 10, max 100 znaków)")
    private String alt;

    @NotNull(message = "Brak treści artykułu")
    @Size(min = 500, max = 50000, message = "Artykuł powinien zawierać co najmniej 500 i maksymalnie 50000 znaków")
    private String content;

    private UploadedFile file;
    private String coords;

    private final EntriesDao edao;
    private final UsersDao udao;
    
    public PostEdit() {
        edao = new EntriesDao();
        udao = new UsersDao();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeotitle() {
        return seotitle;
    }

    public void setSeotitle(String seotitle) {
        this.seotitle = seotitle;
    }

    public String getSeodesc() {
        return seodesc;
    }

    public void setSeodesc(String seodesc) {
        this.seodesc = seodesc;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String save() {

        if (edao.getEntryBySeotitle(seotitle, false) != null) {
            Messages.addError("form:form-seotitle", "Artykuł o podanym tytule-seo już istnieje");
            return null;
        }

        String name = file.getFileName().substring(0, file.getFileName().lastIndexOf('.'));

        if(name.matches("[^A-Za-z0-9-_]")){
            Messages.addError("form:upload", "Niedozwolone znaki w nazwie pliku");
            return null;
        }
        
        int c[] = Arrays.stream(coords.split("_")).mapToInt(Integer::parseInt).toArray();

        if (c[2] < 750 || c[3] < 300) {
            Messages.addError("form:upload", "Zaznaczona ramka jest mniesza niż wymagane 750x300");
            return null;
        }

        try {
            Users user = udao.getUserById(Integer.parseInt((String) SecurityUtils.getSubject().getPrincipal()));
            Date date = new Date();
            Images image = UploadUtil.createImage(file, name, alt, c, date);
            Entries entry = new Entries(user, title, seotitle, seodesc, categories, image, content, date);
            edao.createEntry(entry);
            return "pretty:home";
        } catch (FileAlreadyExistsException fae) {
            Messages.addError("form:upload", "Plik o podanej nazwie już istnieje");
        } catch (IOException ioe) {
            Messages.addError("form:upload", "Wystąpił błąd odczytu pliku");
        }

        return null;
    }
}
