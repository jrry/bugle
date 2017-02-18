package pl.bugle.blog.admin;

import pl.bugle.blog.dao.CategoriesDao;
import pl.bugle.blog.dao.EntriesDao;
import pl.bugle.blog.entity.Categories;
import pl.bugle.blog.entity.Entries;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import cz.jiripinkas.jsitemapgenerator.WebPageBuilder;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import org.omnifaces.util.Faces;

@Named
@RequestScoped
public class SitemapBuilder {
    
    private final EntriesDao bdao;
    private final CategoriesDao cdao;
    private static final String SITEMAP_PATH = Faces.getServletContext().getInitParameter("sitemap.PATH");

    public SitemapBuilder() {
        bdao = new EntriesDao();
        cdao = new CategoriesDao();
    }
    
    public void buildSitemap() throws IOException {
        SitemapGenerator sg = new SitemapGenerator("http://bugle.pl");
        sg.addPage(new WebPageBuilder().name("/").priorityMax().changeFreqDaily().lastModNow().build());
        sg.addPage(new WebPageBuilder().name("/profil/logowanie/").priority(0.6).changeFreqDaily().lastModNow().build());
        sg.addPage(new WebPageBuilder().name("/profil/rejestracja/").priority(0.6).changeFreqDaily().lastModNow().build());
        List<Entries> lbg = bdao.getEntries();
        List<Categories> lcg = cdao.getCategories();
        for (Entries ent : lbg) {
            sg.addPage(new WebPageBuilder().name("/" + ent.getEntry_seotitle() + "/").priority(0.8).changeFreqWeekly().lastMod(ent.getEntry_date()).build());
        }
        for (Categories cat : lcg) {
            sg.addPage(new WebPageBuilder().name("/kategoria/" + cat.getCategory_seoname() + "/").priority(0.4).changeFreqWeekly().lastModNow().build());
        }
        File file = new File(SITEMAP_PATH + "/sitemap.xml");
        sg.constructAndSaveSitemap(file);
        sg.pingBing("http://bugle.pl/sitemap.xml");
        sg.pingGoogle("http://bugle.pl/sitemap.xml");
        Faces.redirect("sitemap.xml");
    }
}
