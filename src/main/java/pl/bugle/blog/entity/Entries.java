package pl.bugle.blog.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "entries")
@NamedQueries({
    @NamedQuery(name = "Entries.findAll", query = "SELECT b FROM Entries b ORDER BY b.entry_date DESC"),
    @NamedQuery(name = "Entries.countAll", query = "SELECT COUNT(b.entry_id) FROM Entries b"),
    @NamedQuery(name = "Entries.findByEntry_id", query = "SELECT b FROM Entries b WHERE b.entry_id = :entry_id"),
    @NamedQuery(name = "Entries.findByEntry_title", query = "SELECT b FROM Entries b WHERE b.entry_title = :entry_title"),
    @NamedQuery(name = "Entries.findByEntry_seotitle", query = "SELECT b FROM Entries b WHERE b.entry_seotitle = :entry_seotitle"),
    @NamedQuery(name = "Entries.findByEntry_seodesc", query = "SELECT b FROM Entries b WHERE b.entry_seodesc = :entry_seodesc"),
    @NamedQuery(name = "Entries.findByEntry_content", query = "SELECT b FROM Entries b WHERE b.entry_content = :entry_content"),
    @NamedQuery(name = "Entries.findByEntry_date", query = "SELECT b FROM Entries b WHERE b.entry_date = :entry_date"),
    @NamedQuery(name = "Entries.findByEntry_category", query = "SELECT b FROM Entries b INNER JOIN b.entry_categories c WHERE c.category_seoname IN :category_seoname ORDER BY b.entry_date DESC")})
public class Entries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "entry_id")
    private Integer entry_id;

    @NotNull
    @ManyToOne(optional = false)
    private Users entry_author;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "entry_title")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String entry_title;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "entry_seotitle", unique = true)
    private String entry_seotitle;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "entry_seodesc")
    private String entry_seodesc;

    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Images entry_image;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "entry_content")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String entry_content;

    @Basic(optional = false)
    @NotNull
    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Field(index=Index.NO, analyze=Analyze.NO, store=Store.NO)
    @SortableField
    @DateBridge(resolution = Resolution.MINUTE)
    private Date entry_date;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "entry_category", joinColumns = { 
                @JoinColumn(name = "entry_id", nullable = false) }, inverseJoinColumns = { 
                @JoinColumn(name = "category_id", nullable = false) })
    private List<Categories> entry_categories;
    
    public Entries() {
    }

    public Entries(Integer entry_id) {
        this.entry_id = entry_id;
    }

    public Entries(Users entry_author, String entry_title, String entry_seotitle, String entry_seodesc, List<Categories> entry_categories, Images entry_image, String entry_content, Date entry_date) {
        this.entry_author = entry_author;
        this.entry_title = entry_title;
        this.entry_seotitle = entry_seotitle;
        this.entry_seodesc = entry_seodesc;
        this.entry_categories = entry_categories;
        this.entry_image = entry_image;
        this.entry_content = entry_content;
        this.entry_date = entry_date;
    }

    public Integer getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(Integer entry_id) {
        this.entry_id = entry_id;
    }

    public Users getEntry_author() {
        return entry_author;
    }

    public void setEntry_author(Users entry_author) {
        this.entry_author = entry_author;
    }

    public String getEntry_title() {
        return entry_title;
    }

    public void setEntry_title(String entry_title) {
        this.entry_title = entry_title;
    }

    public String getEntry_seotitle() {
        return entry_seotitle;
    }

    public void setEntry_seotitle(String entry_seotitle) {
        this.entry_seotitle = entry_seotitle;
    }

    public String getEntry_seodesc() {
        return entry_seodesc;
    }

    public void setEntry_seodesc(String entry_seodesc) {
        this.entry_seodesc = entry_seodesc;
    }

    public Images getEntry_image() {
        return entry_image;
    }

    public void setEntry_image(Images entry_image) {
        this.entry_image = entry_image;
    }
    
    public String getEntry_content() {
        return entry_content;
    }

    public void setEntry_content(String entry_content) {
        this.entry_content = entry_content;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public List<Categories> getEntry_categories() {
        return entry_categories;
    }

    public void setEntry_categories(List<Categories> entry_categories) {
        this.entry_categories = entry_categories;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entry_id != null ? entry_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entries)) {
            return false;
        }
        Entries other = (Entries) object;
        if ((this.entry_id == null && other.entry_id != null) || (this.entry_id != null && !this.entry_id.equals(other.entry_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Entries[ entry_id=" + entry_id + " ]";
    }
    
}
