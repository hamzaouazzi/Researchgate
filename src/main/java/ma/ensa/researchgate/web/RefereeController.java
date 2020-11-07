package ma.ensa.researchgate.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.researchgate.dao.AssignmentRepository;
import ma.ensa.researchgate.dto.ReviewAssignmentRequest;
import ma.ensa.researchgate.entities.Assignment;
import ma.ensa.researchgate.service.serviceimpl.Assignmentserviceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/referee")
@AllArgsConstructor
@Slf4j
public class RefereeController {

    private final AssignmentRepository assignmentdao;
    private final Assignmentserviceimpl assignmentserviceimpl;



    @PutMapping("/reviewpaper/{id}") //REFEREE
    public ResponseEntity<Assignment> reviewAssignment(@PathVariable("id") long id, @RequestBody ReviewAssignmentRequest reviewAssignmentRequest) {
        Assignment assignmentToReview = assignmentdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(assignmentserviceimpl.ReviewAssignment(assignmentToReview,reviewAssignmentRequest));
    }

}
