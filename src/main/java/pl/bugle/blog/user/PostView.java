package pl.bugle.blog.user;

import pl.bugle.blog.dao.EntriesDao;
import pl.bugle.blog.entity.Entries;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class PostView {

    private String page;
    private String searchQuery;
    private String categoryParam;
    private int ipage;
    private List<Entries> be;
    private boolean nextpage;
    private boolean prevpage;
    
    private final EntriesDao bdao;

    public PostView() {
        bdao = new EntriesDao();
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getCategoryParam() {
        return categoryParam;
    }

    public void setCategoryParam(String categoryParam) {
        this.categoryParam = categoryParam;
    }

    public boolean isNextpage() {
        return nextpage;
    }

    public boolean isPrevpage() {
        return prevpage;
    }

    public String pathNext() {
        if (page == null) {
            if (categoryParam != null)
                return "kategoria/" + categoryParam + "/strona/2/";
            if (searchQuery != null)
                return "strona/2/?s=" + searchQuery.replaceAll("\\s","+");
            return "strona/2/";
        }
        if (categoryParam != null)
            return "kategoria/" + categoryParam + "/strona/" + (Integer.parseInt(page) + 1) + "/";
        if (searchQuery != null)
            return "strona/" + (Integer.parseInt(page) + 1) + "/?s=" + searchQuery.replaceAll("\\s","+");
        return "strona/" + (Integer.parseInt(page) + 1) + "/";
    }
    
    public String pathPrev() {
        int p = Integer.parseInt(page) - 1;
        if (p < 2) {
            if (categoryParam != null)
                return "kategoria/" + categoryParam + "/";
            if (searchQuery != null)
                return "home.xhtml?s=" + searchQuery.replaceAll("\\s","+");
            return null;
        }
        if (categoryParam != null)
            return "kategoria/" + categoryParam + "/strona/" + p + "/";
        if (searchQuery != null)
            return "strona/" + p + "/?s=" + searchQuery.replaceAll("\\s","+");
        return "strona/" + p + "/";
    }
    
    public String loadPage() {
        nextpage = false;
        prevpage = false;

        if (page == null)
            ipage = 0;
        else {
            try {
                ipage = Integer.parseInt(page);
            } catch (NumberFormatException nf) {
                return "pretty:home";
            }
            if (ipage < 1)
                return "pretty:home";
            ipage = (10 * ipage) - 10;
        }

        if (searchQuery != null) {
            if(searchQuery.length() > 100) 
                searchQuery = searchQuery.substring(0, 100);
            if (!searchQuery.matches("[a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]+[ a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]*"))
                return "pretty:home";
            be = bdao.searchEntries(ipage, 11, searchQuery);
        } else if (categoryParam != null) {
            if (categoryParam.length() > 64)
                categoryParam = categoryParam.substring(0, 64);
            be = bdao.getEntryByCategorySeotitle(ipage, 11, categoryParam);
        } else {
            be = bdao.getEntries(ipage, 11);
        }

        if (be.isEmpty()) {
            if (ipage == 0 && categoryParam == null)
                return null;
            return "pretty:home";
        }

        if (be.size() > 10) {
            nextpage = true;
            be.remove(ipage + 10);
        }

        if (ipage > 1)
            prevpage = true;

        return null;
    }
    
    public String search() {
        return "home.xhtml?faces-redirect=true&s=" + searchQuery;
    }
    
    public String cutString(String text) {
        return text.substring(0, text.indexOf("</p>") + 4);
    }

    public List<Entries> getEntries() {
        return be;
    }

}
