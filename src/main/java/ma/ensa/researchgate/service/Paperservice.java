package ma.ensa.researchgate.service;

import ma.ensa.researchgate.dto.EditPaperRequest;
import ma.ensa.researchgate.dto.NewPaperRequest;
import ma.ensa.researchgate.dto.QualifyPaperRequest;
import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.entities.User;

import java.util.Set;

public interface Paperservice {
    public Paper newPaper(NewPaperRequest newPaperRequest, User author);
    public Paper editPaper(Paper paperToEdit, EditPaperRequest editPaperRequest);
    public void deletePaper(Paper paperToDelete);
    public Set<Paper> getUserPapers(User author);
    public Paper qualifyPaper(Paper paperToQualify, QualifyPaperRequest qualifyPaperRequest);
    public Set<Paper> getApprovedPapers(boolean approved);
    public Set<Paper> getUserPendingPapers(User author);
}
