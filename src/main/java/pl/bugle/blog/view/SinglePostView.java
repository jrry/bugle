package pl.bugle.blog.view;

import pl.bugle.blog.dao.EntriesDao;
import pl.bugle.blog.dao.RatingsDao;
import pl.bugle.blog.dao.UsersDao;
import pl.bugle.blog.entity.Entries;
import pl.bugle.blog.entity.Ratings;
import pl.bugle.blog.entity.Users;
import org.apache.shiro.SecurityUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class SinglePostView {
    private final UsersDao udao;
    private final RatingsDao rdao;
    private final EntriesDao bdao;
    private String content;
    private Entries entry;

    private int rate;
    
    public SinglePostView() {
        udao = new UsersDao();
        rdao = new RatingsDao();
        bdao = new EntriesDao();
        entry = new Entries();
    }

    public int getRate() {
        if(entry.getEntry_seotitle() != null) {
            Users user = udao.getUserById(Integer.parseInt((String) SecurityUtils.getSubject().getPrincipal()));
            Ratings rt = rdao.getRatingByUserIdAndEntrySeotitle(user.getUser_id(), entry.getEntry_seotitle());
            if(rt != null)
                return rt.getRating_stars();
            return rate;
        }
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getContent() {
        return entry.getEntry_content();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Entries getEntry() {
        return entry;
    }

    public void setEntry(Entries entry) {
        this.entry = entry;
    }
    
    public String loadPost() {
        entry = bdao.getEntryBySeotitle(entry.getEntry_seotitle(), true);
        if (entry == null)
            return "pretty:home";
        return null;
    }
    
    public void onRate() {
        Users user = udao.getUserById(Integer.parseInt((String) SecurityUtils.getSubject().getPrincipal()));
        Ratings rt = rdao.getRatingByUserIdAndEntrySeotitle(user.getUser_id(), entry.getEntry_seotitle());
        if(rt != null){
            rt.setRating_stars(rate);
            rdao.updateRating(rt);
        } else {
            rt = new Ratings(rate, user, bdao.getEntryBySeotitle(entry.getEntry_seotitle(), false));
            rdao.createRating(rt);
        }
    }
    
    public void onCancel() {
        Users user = udao.getUserById(Integer.parseInt((String) SecurityUtils.getSubject().getPrincipal()));
        Ratings rt = rdao.getRatingByUserIdAndEntrySeotitle(user.getUser_id(), getEntry().getEntry_seotitle());
        if(rt != null) rdao.deleteRating(rt);
    }
    
    public String getRank() {
        Double format = rdao.getEntryRank(entry.getEntry_seotitle());
        if (format == null)
            format = 0.0;
        return String.format("%.2f", format);
    }

    public String getText() {
        int v = rdao.countEntryRank(entry.getEntry_seotitle());
        return v > 0 ? String.format("<strong>%s<strong> głosów, ocena: <strong>%s<strong>", v, getRank()) : "(brak oceny)";
    }
    
    public void update() {
        entry = bdao.getEntryBySeotitle(entry.getEntry_seotitle(), true);
        entry.setEntry_content(content);
        bdao.updateEntry(entry);
    }
}
