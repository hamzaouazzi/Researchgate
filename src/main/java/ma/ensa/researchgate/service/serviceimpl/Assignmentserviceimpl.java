package ma.ensa.researchgate.service.serviceimpl;

import lombok.AllArgsConstructor;
import ma.ensa.researchgate.dao.AssignmentRepository;
import ma.ensa.researchgate.dao.JudgmentRepository;
import ma.ensa.researchgate.dao.PaperRepository;
import ma.ensa.researchgate.dao.UserRepository;
import ma.ensa.researchgate.dto.CreateAssignment;
import ma.ensa.researchgate.dto.ReviewAssignmentRequest;
import ma.ensa.researchgate.entities.Assignment;
import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.entities.User;
import ma.ensa.researchgate.service.Assignmentservice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class Assignmentserviceimpl implements Assignmentservice {

    private final PaperRepository paperdao;
    private final AssignmentRepository assignmentdao;
    private final UserRepository userdao;
    private final JudgmentRepository judgmentdao;

    @Override
    public Set<Assignment> getPaperAssignments(Paper paper) {
        if(paperdao.findById(paper.getPaperid()).isPresent()) return paperdao.findById(paper.getPaperid()).get().getAssignments();
        else return null;
    }

    @Override
    public Assignment createAssignment(CreateAssignment createAssignmentRequest) {
        if (!createAssignmentRequest.getPaper().isApproved() && createAssignmentRequest.getReferee().getRole().getRole().equals("COMITTEE") && createAssignmentRequest.getPaper().isPublish()){
            Paper paper = createAssignmentRequest.getPaper();
            User referee = createAssignmentRequest.getReferee();
            Set<Assignment> assignments = paper.getAssignments();
            if (assignments.size()<4){
                Assignment assignment = new Assignment();
                assignment.setReferee(referee);
                assignment.setPaper(paper);
                assignment.setReviewed(false);
                paper.setStatus("Referee assignments : "+assignments.size()+1+"/3");
                assignmentdao.save(assignment);
                paperdao.save(paper);
                return assignment;
            }else return null;
        }else return null;
    }

    @Override
    public void deleteAssignment(Assignment assignment) {
        assignmentdao.delete(assignment);
    }

    @Override
    public Assignment ReviewAssignment(Assignment assignmentToReview, ReviewAssignmentRequest reviewAssignmentRequest) {
        if(!assignmentToReview.getPaper().isApproved() && assignmentToReview.getReferee().getRole().getRole().equals("REFEREE") && judgmentdao.findById(reviewAssignmentRequest.getJudgmentid()).isPresent()){
            Assignment assignment = assignmentdao.findById(assignmentToReview.getAssignmentid()).get();
            assignment.setRate(reviewAssignmentRequest.getRate());
            assignment.setReview(reviewAssignmentRequest.getReview());
            assignment.setReviewed(reviewAssignmentRequest.isReviewed());
            assignment.setJudgment(judgmentdao.findById(reviewAssignmentRequest.getJudgmentid()).get());
            assignmentdao.save(assignment);
            return assignment;
        }else return null;
    }

}
