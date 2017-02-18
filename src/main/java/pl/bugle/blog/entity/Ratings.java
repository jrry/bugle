package pl.bugle.blog.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings")
@NamedQueries({
    @NamedQuery(name = "Ratings.findAll", query = "SELECT r FROM Ratings r"),
    @NamedQuery(name = "Ratings.findByRating_Id", query = "SELECT r FROM Ratings r WHERE r.rating_id = :rating_id"),
    @NamedQuery(name = "Ratings.findByRating_Stars", query = "SELECT r FROM Ratings r WHERE r.rating_stars = :rating_stars"),
    @NamedQuery(name = "Ratings.findByRating_Entry", query = "SELECT r FROM Ratings r WHERE r.rating_entry = :rating_entry"),
    @NamedQuery(name = "Ratings.findByRating_Author_And_Entry_Seotitle", query = "SELECT r FROM Ratings r WHERE r.rating_author.user_id = :user_id AND r.rating_entry.entry_seotitle = :entry_seotitle"),
    @NamedQuery(name = "Ratings.countEntry_Rank_By_Entry_Seotitle", query = "SELECT COUNT(r.rating_id) FROM Ratings r WHERE r.rating_entry.entry_seotitle = :entry_seotitle"),
    @NamedQuery(name = "Ratings.avgEntry_Rank_By_Entry_Seotitle", query = "SELECT AVG(r.rating_stars) FROM Ratings r WHERE r.rating_entry.entry_seotitle = :entry_seotitle")
})
public class Ratings implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rating_id")
    private Long rating_id;
        
    @Basic(optional = false)
    @Column(name = "rating_stars")
    private int rating_stars;
    
    @NotNull
    @ManyToOne(optional = false)
    private Users rating_author;
    
    @NotNull
    @ManyToOne(optional = false)
    private Entries rating_entry;

    public Ratings() {
    }

    public Ratings(Integer rating_stars, Users rating_author, Entries rating_entry) {
        this.rating_stars = rating_stars;
        this.rating_author = rating_author;
        this.rating_entry = rating_entry;
    }
    
    public Long getRating_id() {
        return rating_id;
    }

    public void setRating_id(Long rating_id) {
        this.rating_id = rating_id;
    }

    public int getRating_stars() {
        return rating_stars;
    }

    public void setRating_stars(int rating_stars) {
        this.rating_stars = rating_stars;
    }
    
    public Users getRating_author() {
        return rating_author;
    }

    public void setRating_author(Users rating_author) {
        this.rating_author = rating_author;
    }

    public Entries getRating_entry() {
        return rating_entry;
    }

    public void setRating_entry(Entries rating_entry) {
        this.rating_entry = rating_entry;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rating_id != null ? rating_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ratings)) {
            return false;
        }
        Ratings other = (Ratings) object;
        if ((this.rating_id == null && other.rating_id != null) || (this.rating_id != null && !this.rating_id.equals(other.rating_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ratings[ rating_id=" + rating_id + " ]";
    }
    
}
