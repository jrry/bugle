package pl.bugle.blog.admin;

import pl.bugle.blog.dao.OptionsDao;
import pl.bugle.blog.entity.Options;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@RequestScoped
public class OptionsEdit {

    @NotNull(message = "Wpisz nazwę bloga")
    @Size(min = 5, max = 50, message = "Niepoprawna długość (min 5, max 50 znaków)")
    private String domain_name;

    @NotNull(message = "Wpisz tytuł")
    @Size(min = 5, max = 25, message = "Niepoprawna długość (min 5, max 25 znaków)")
    private String site_title;

    @NotNull(message = "Wpisz opis (meta description)")
    @Size(min = 5, max = 155, message = "Niepoprawna długość (min 5, max 155 znaków)")
    private String site_desc;
    
    private final OptionsDao dao;

    public OptionsEdit() {
        dao = new OptionsDao();
    }

    public String getDomain_name() {
        return dao.getOptionById(1).getOption_value();
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public String getSite_title() {
        return dao.getOptionById(2).getOption_value();
    }

    public void setSite_title(String site_title) {
        this.site_title = site_title;
    }

    public String getSite_desc() {
        return dao.getOptionById(3).getOption_value();
    }

    public void setSite_desc(String site_desc) {
        this.site_desc = site_desc;
    }
    
    public void save() {
        Options o = new Options(1, "domain_name", domain_name);
        dao.setOption(o);
        o = new Options(2, "site_title", site_title);
        dao.setOption(o);
        o = new Options(3, "site_desc", site_desc);
        dao.setOption(o);
    }
}
