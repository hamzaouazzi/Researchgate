package ma.ensa.researchgate.dao;

import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PaperRepository  extends JpaRepository<Paper, Long> {

    @Query
    public Set<Paper> findByAuthorAndPublishTrueAndApprovedFalse(User author);

    public Set<Paper> findByAuthor(User author);

    @Query
    public Set<Paper> findByApprovedTrue();

    @Query
    public Set<Paper> findByApprovedFalse();
}
