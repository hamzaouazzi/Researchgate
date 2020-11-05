package ma.ensa.researchgate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "judgments")
public class Judgment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "judgmentid", nullable = false)
    private long judgmentid;

    @Column(name = "judgment")
    private String judgment;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "judgment")
    private Set<Assignment> assignments;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "judgment")
    private Set<Paper> papers;
}
