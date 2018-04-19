package pl.bugle.blog.admin;

import pl.bugle.blog.dao.EntriesDao;
import pl.bugle.blog.entity.Entries;
import pl.bugle.blog.lazy.EntryLazy;
import pl.bugle.blog.uploads.UploadUtil;
import org.primefaces.model.LazyDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class EntryEdit {
    
    private final EntriesDao edao;
    
    private Entries selectedEntry;
    private LazyDataModel<Entries> lazyEntries;
    
    public EntryEdit() {
        edao = new EntriesDao();
        lazyEntries = new EntryLazy(edao);
    }

    public Entries getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(Entries selectedEntry) {
        this.selectedEntry = selectedEntry;
    }
    
    public LazyDataModel<Entries> getLazyEntries() {
        return lazyEntries;
    }

    public void setLazyEntries(LazyDataModel<Entries> lazyEntries) {
        this.lazyEntries = lazyEntries;
    }
    
    public void delete() {
        edao.deleteEntry(selectedEntry);
        try {
            UploadUtil.deleteImage(selectedEntry.getEntry_image().getImage_name(), selectedEntry.getEntry_date());
        } catch (Exception e) {
            // TODO message for not deleted
        }
        
    }
}