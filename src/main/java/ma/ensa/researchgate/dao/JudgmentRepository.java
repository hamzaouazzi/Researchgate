package ma.ensa.researchgate.dao;

import ma.ensa.researchgate.entities.Judgment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JudgmentRepository  extends JpaRepository<Judgment, Long> {

}
