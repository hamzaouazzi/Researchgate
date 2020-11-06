package ma.ensa.researchgate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperResponse {
    private String title;
    private String author;
    private boolean publish;
    private String status;
}
