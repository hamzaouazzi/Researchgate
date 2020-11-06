package ma.ensa.researchgate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssignment {
    private Paper paper;
    private User referee;
}
