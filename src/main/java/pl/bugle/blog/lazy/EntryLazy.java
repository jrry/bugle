package pl.bugle.blog.lazy;

import pl.bugle.blog.dao.EntriesDao;
import pl.bugle.blog.entity.Entries;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class EntryLazy extends LazyDataModel<Entries> {
    
    private final EntriesDao edao;
    private final int count;
    
    public EntryLazy(EntriesDao edao) {
        this.edao = edao;
        count = edao.countEntries();
    }

    @Override
    public List<Entries> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return edao.getEntries(first, first, sortField, sortOrder);
    }

    @Override
    public int getRowCount() {
        return count;
    }

    @Override
    public Object getRowKey(Entries object) {
        return object.getEntry_id();
    }

    @Override
    public Entries getRowData(String rowKey) {
        return edao.getEntryById(Integer.parseInt(rowKey));
    }
}
