package ma.ensa.researchgate.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.researchgate.dao.AssignmentRepository;
import ma.ensa.researchgate.dao.PaperRepository;
import ma.ensa.researchgate.dao.UserRepository;
import ma.ensa.researchgate.dto.*;
import ma.ensa.researchgate.entities.Assignment;
import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.entities.User;
import ma.ensa.researchgate.exceptions.UserAlreadyExistAuthenticationException;
import ma.ensa.researchgate.service.serviceimpl.Assignmentserviceimpl;
import ma.ensa.researchgate.service.serviceimpl.Paperserviceimpl;
import ma.ensa.researchgate.service.serviceimpl.Userserviceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/committee")
@AllArgsConstructor
@Slf4j
public class CommitteeController {

    private final Userserviceimpl authService;
    private final PaperRepository paperdao;
    private final Paperserviceimpl paperserviceimpl;
    private final UserRepository userdao;
    private final AssignmentRepository assignmentdao;
    private final Assignmentserviceimpl assignmentserviceimpl;

    @PostMapping("/newassignment") //COMMITTEE
    public ResponseEntity<Assignment> newAssignment(@RequestBody CreateAssignmentRequest createAssignmentRequest) {
        Paper paperToAssign = paperdao.findById(createAssignmentRequest.getPaperid())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + createAssignmentRequest.getPaperid()));
        User refereeToAssign = userdao.findById(createAssignmentRequest.getRefereeid())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + createAssignmentRequest.getRefereeid()));
        CreateAssignment createAssignment = new CreateAssignment();
        createAssignment.setPaper(paperToAssign);
        createAssignment.setReferee(refereeToAssign);
        Assignment assignment = assignmentserviceimpl.createAssignment(createAssignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    @PutMapping("/qualifypaper/{id}") //COMMITTEE
    public ResponseEntity<Paper> qualifyPaper(@PathVariable("id") long id, @RequestBody QualifyPaperRequest qualifyPaperRequest) {
        Paper paperToQualify = paperdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        Paper paper = paperserviceimpl.qualifyPaper(paperToQualify,qualifyPaperRequest);
        return ResponseEntity.status(HttpStatus.OK).body(paper);
    }

    @DeleteMapping("/deleteassignment/{id}") //COMMITTEE
    public ResponseEntity<String> deletePaper(@PathVariable("id") long id) {
        Assignment assignmentToDelete = assignmentdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        if(!assignmentToDelete.isReviewed()){
            assignmentserviceimpl.deleteAssignment(assignmentToDelete);
            return new ResponseEntity<>("Paper successfully deleted!", HttpStatus.OK);
        }return new ResponseEntity<>("Assignment can't be deleted!", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/newuser") //COMMITTEE
    public ResponseEntity<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException {
        User user = authService.adduser(registerRequest);
        RegisterResponse response = new RegisterResponse();
        response.setEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
