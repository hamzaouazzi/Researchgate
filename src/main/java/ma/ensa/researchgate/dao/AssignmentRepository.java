package ma.ensa.researchgate.dao;

import ma.ensa.researchgate.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
