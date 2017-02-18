package pl.bugle.blog.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "images")
@NamedQueries({
    @NamedQuery(name = "Images.findAll", query = "SELECT i FROM Images i"),
    @NamedQuery(name = "Images.findById", query = "SELECT i FROM Images i WHERE i.image_id = :image_id"),
    @NamedQuery(name = "Images.findByName", query = "SELECT i FROM Images i WHERE i.image_name = :image_name"),
    @NamedQuery(name = "Images.findByAlt", query = "SELECT i FROM Images i WHERE i.image_alt = :image_alt")
})
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "image_id")
    private Long image_id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "image_name")
    private String image_name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "image_alt")
    private String image_alt;

    public Images() {
    }

    public Images(String image_name, String image_alt) {
        this.image_name = image_name;
        this.image_alt = image_alt;
    }

    public Long getImage_id() {
        return image_id;
    }

    public void setImage_id(Long image_id) {
        this.image_id = image_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_alt() {
        return image_alt;
    }

    public void setImage_alt(String image_alt) {
        this.image_alt = image_alt;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (image_id != null ? image_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Images)) {
            return false;
        }
        Images other = (Images) object;
        if ((this.image_id == null && other.image_id != null) || (this.image_id != null && !this.image_id.equals(other.image_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Images[ image_id=" + image_id + " ]";
    }
    
}
