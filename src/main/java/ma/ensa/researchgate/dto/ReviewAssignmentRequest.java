package ma.ensa.researchgate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAssignmentRequest {

    private String review;
    private double rate;
    private boolean reviewed;
    private long judgmentid;
}
