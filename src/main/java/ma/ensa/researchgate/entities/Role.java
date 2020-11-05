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
@Table(name = "roles")
public class Role implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "roleid", nullable = false)
    private long roleid;

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
