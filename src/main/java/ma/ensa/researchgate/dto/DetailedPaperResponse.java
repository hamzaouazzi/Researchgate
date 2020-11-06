package ma.ensa.researchgate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedPaperResponse {

    private String title;

    private String authorLastName;

    private String authorFirstName;

    private String paperabstract;

    private String affiliations;

    private String co_authors;

    private String keywords;

    private String content;

}
