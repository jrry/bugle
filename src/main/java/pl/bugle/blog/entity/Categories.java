package pl.bugle.blog.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
@NamedQueries({
    @NamedQuery(name = "Categories.findAll", query = "SELECT c FROM Categories c ORDER BY c.category_name"),
    @NamedQuery(name = "Categories.countAll", query = "SELECT COUNT(c.category_id) FROM Categories c"),
    @NamedQuery(name = "Categories.findByCategory_Id", query = "SELECT c FROM Categories c WHERE c.category_id = :category_id"),
    @NamedQuery(name = "Categories.findByCategory_Name", query = "SELECT c FROM Categories c WHERE c.category_name = :category_name"),
    @NamedQuery(name = "Categories.findByCategory_Seoname", query = "SELECT c FROM Categories c WHERE c.category_seoname = :category_seoname")
})
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "category_id")
    private Long category_id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "category_name")
    private String category_name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "category_seoname")
    private String category_seoname;

    @Transient
    private Long category_entries_count;
    
    @ManyToMany(mappedBy = "entry_categories", fetch = FetchType.LAZY)
    private List<Entries> category_entries;
    
    public Categories() {
    }
    
    public Categories(String category_name) {
        this.category_name = category_name;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_seoname() {
        return category_seoname;
    }

    public void setCategory_seoname(String category_seoname) {
        this.category_seoname = category_seoname;
    }

    public Long getCategory_entries_count() {
        return category_entries_count;
    }

    public void setCategory_entries_count(Long category_entries_count) {
        this.category_entries_count = category_entries_count;
    }
    
    public List<Entries> getCategory_entries() {
        return category_entries;
    }

    public void setCategory_entries(List<Entries> category_entries) {
        this.category_entries = category_entries;
    }
    
    public void addEntry(Entries entry) {
        if(!this.category_entries.contains(entry))
            this.category_entries.add(entry);
    }
    
    public void removeEntry(Entries entry) {
        if(this.category_entries.contains(entry))
            this.category_entries.remove(entry);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (category_id != null ? category_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categories)) {
            return false;
        }
        Categories other = (Categories) object;
        if ((this.category_id == null && other.category_id != null) || (this.category_id != null && !this.category_id.equals(other.category_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Categories[ id=" + category_id + " ]";
    }
    
}
