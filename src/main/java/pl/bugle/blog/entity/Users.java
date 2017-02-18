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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.shiro.crypto.hash.Sha256Hash;

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.countAll", query = "SELECT COUNT(u.user_id) FROM Users u"),
    @NamedQuery(name = "Users.findByUser_id", query = "SELECT u FROM Users u WHERE u.user_id = :user_id"),
    @NamedQuery(name = "Users.findByUser_name", query = "SELECT u FROM Users u WHERE u.user_name = :user_name"),
    @NamedQuery(name = "Users.findByUser_role", query = "SELECT u FROM Users u WHERE u.user_role = :user_role"),
    @NamedQuery(name = "Users.findByUser_email", query = "SELECT u FROM Users u WHERE u.user_email = :user_email")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Integer user_id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "user_name")
    private String user_name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "user_role")
    private String user_role;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "user_email")
    private String user_email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "user_password")
    private String user_password;

    @Transient
    private Long user_entries_count;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry_author")
    private List<Entries> user_entries;
    
    public Users() {
    }

    public Users(Integer user_id) {
        this.user_id = user_id;
    }

    public Users(String user_name, String user_role, String user_email, String user_password) {
        this.user_name = user_name;
        this.user_role = user_role;
        this.user_email = user_email;
        this.user_password = new Sha256Hash(user_password).toBase64();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer id) {
        this.user_id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = new Sha256Hash(user_password).toBase64();
    }

    public Long getUser_entries_count() {
        return user_entries_count;
    }

    public void setUser_entries_count(Long user_entries_count) {
        this.user_entries_count = user_entries_count;
    }

    public List<Entries> getUser_entries() {
        return user_entries;
    }

    public void setUser_entries(List<Entries> user_entries) {
        this.user_entries = user_entries;
    }
    
    public void addEntry(Entries entry) {
        if(!this.user_entries.contains(entry))
            this.user_entries.add(entry);
    }
    
    public void removeEntry(Entries entry) {
        if(this.user_entries.contains(entry))
            this.user_entries.remove(entry);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (user_id != null ? user_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.user_id == null && other.user_id != null) || (this.user_id != null && !this.user_id.equals(other.user_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Users[ user_id=" + user_id + " ]";
    }
    
}
