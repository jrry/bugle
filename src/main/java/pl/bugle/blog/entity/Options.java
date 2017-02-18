package pl.bugle.blog.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "options")
@NamedQueries({
    @NamedQuery(name = "Options.findAll", query = "SELECT o FROM Options o"),
    @NamedQuery(name = "Options.findById", query = "SELECT o FROM Options o WHERE o.option_id = :option_id"),
    @NamedQuery(name = "Options.findByName", query = "SELECT o FROM Options o WHERE o.option_name = :option_name"),
    @NamedQuery(name = "Options.findByValue", query = "SELECT o FROM Options o WHERE o.option_value = :option_value")})
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "option_id")
    private Integer option_id;

    @Basic(optional = false)
    @NotNull
    @Size(max = 32)
    @Column(name = "option_name")
    private String option_name;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "option_value")
    private String option_value;

    public Options() {
    }

    public Options(Integer option_id, String option_name, String option_value) {
        this.option_id = option_id;
        this.option_name = option_name;
        this.option_value = option_value;
    }

    public Integer getOption_id() {
        return option_id;
    }

    public void setOption_id(Integer option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_value() {
        return option_value;
    }

    public void setOption_value(String option_value) {
        this.option_value = option_value;
    }   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (option_id != null ? option_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Options)) {
            return false;
        }
        Options other = (Options) object;
        if ((this.option_id == null && other.option_id != null) || (this.option_id != null && !this.option_id.equals(other.option_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Options[ option_id=" + option_id + " ]";
    }
    
}
