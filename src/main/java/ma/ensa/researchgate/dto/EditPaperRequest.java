package ma.ensa.researchgate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditPaperRequest {

    private String title;

    private String paperabstract;

    private String content;

    private String affiliations;

    private String co_authors;

    private String keywords;

    private boolean publish;
}
