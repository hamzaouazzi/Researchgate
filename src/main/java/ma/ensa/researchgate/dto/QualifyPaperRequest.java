package ma.ensa.researchgate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualifyPaperRequest {

    private long judgmentid;
    private boolean approved;
    private String status;

}
