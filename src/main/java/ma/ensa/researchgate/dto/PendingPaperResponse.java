package ma.ensa.researchgate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.researchgate.entities.Assignment;
import ma.ensa.researchgate.entities.Judgment;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingPaperResponse {

    private String title;

    private String authorLastName;

    private String authorFirstName;

    private String paperabstract;

    private String affiliations;

    private String co_authors;

    private String keywords;

    private String content;

    private Set<Assignment> assignments;

    private String status;

    private boolean approved;

    private Judgment judgment;
}
