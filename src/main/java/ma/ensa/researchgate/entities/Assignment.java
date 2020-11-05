package ma.ensa.researchgate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "assignment")
public class Assignment implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "assignmentid", nullable = false)
    private long assignmentid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "paperid")
    private Paper paper;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User referee;

    @Column(name = "review")
    private String review;

    @Column(name = "rate")
    private double rate;

    @Column(name = "reviewed")
    private boolean reviewed;

    @ManyToOne
    @JoinColumn(name = "judgmentid")
    private Judgment judgment;

    @Column(name = "created_at")
    private Date created_at;

}
