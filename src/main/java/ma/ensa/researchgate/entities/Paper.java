package ma.ensa.researchgate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "papers")
public class Paper implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "paperid", nullable = false)
    private long paperid;

    @Column(name = "title")
    private String title;

    @Column(name = "paperabstract", columnDefinition="TEXT")
    private String paperabstract;

    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @Column(name = "affiliations")
    private String affiliations;

    @Column(name = "co_authors")
    private String co_authors;

    @Column(name = "keywords", columnDefinition="TEXT")
    private String keywords;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "status")
    private String status;

    @Column(name = "publish")
    private boolean publish;

    @Column(name = "approved")
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "judgmentid")
    private Judgment judgment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "authorid")
    private User author;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "paper")
    private Set<Assignment> assignments;
}
