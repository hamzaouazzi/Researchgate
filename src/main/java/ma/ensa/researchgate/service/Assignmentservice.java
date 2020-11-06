package ma.ensa.researchgate.service;

import ma.ensa.researchgate.dto.CreateAssignment;
import ma.ensa.researchgate.dto.ReviewAssignmentRequest;
import ma.ensa.researchgate.entities.Assignment;
import ma.ensa.researchgate.entities.Paper;

import java.util.Set;

public interface Assignmentservice {
    public Set<Assignment> getPaperAssignments(Paper paper);
    public Assignment createAssignment(CreateAssignment createAssignmentRequest);
    public void deleteAssignment(Assignment assignment);
    public Assignment ReviewAssignment(Assignment assignment, ReviewAssignmentRequest reviewAssignmentRequest);
}
