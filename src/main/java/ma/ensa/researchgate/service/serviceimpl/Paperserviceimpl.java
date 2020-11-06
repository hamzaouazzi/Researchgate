package ma.ensa.researchgate.service.serviceimpl;

import lombok.AllArgsConstructor;
import ma.ensa.researchgate.dao.JudgmentRepository;
import ma.ensa.researchgate.dao.PaperRepository;
import ma.ensa.researchgate.dto.EditPaperRequest;
import ma.ensa.researchgate.dto.NewPaperRequest;
import ma.ensa.researchgate.dto.QualifyPaperRequest;
import ma.ensa.researchgate.entities.Judgment;
import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.entities.User;
import ma.ensa.researchgate.service.Paperservice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class Paperserviceimpl implements Paperservice {
    private final PaperRepository paperdao;
    private final JudgmentRepository judgmentdao;

    @Override
    public Paper newPaper(NewPaperRequest newPaperRequest, User author) {
        Paper paper = new Paper();
        paper.setAuthor(author);
        paper.setTitle(newPaperRequest.getTitle());
        paper.setAffiliations(newPaperRequest.getAffiliations());
        paper.setContent(newPaperRequest.getContent());
        paper.setCo_authors(newPaperRequest.getCo_authors());
        paper.setKeywords(newPaperRequest.getKeywords());
        paper.setPaperabstract(newPaperRequest.getPaperabstract());
        paper.setPublish(newPaperRequest.isPublish());
        paper.setApproved(false);
        if(newPaperRequest.isPublish()){
            paper.setStatus("Locked for peer-reviewing! Pending...");
        }else paper.setStatus("You still can edit or delete your paper.");
        paperdao.save(paper);
        return paper;
    }

    @Override
    public Paper editPaper(Paper paperToEdit, EditPaperRequest editPaperRequest) {
        if(!paperToEdit.isPublish() && paperdao.findById(paperToEdit.getPaperid()).isPresent()){
            Paper paper = paperdao.findById(paperToEdit.getPaperid()).get();
            paper.setTitle(editPaperRequest.getTitle());
            paper.setAffiliations(editPaperRequest.getAffiliations());
            paper.setContent(editPaperRequest.getContent());
            paper.setCo_authors(editPaperRequest.getCo_authors());
            paper.setKeywords(editPaperRequest.getKeywords());
            paper.setPaperabstract(editPaperRequest.getPaperabstract());
            paper.setPublish(editPaperRequest.isPublish());
            if(editPaperRequest.isPublish()){
                paper.setStatus("Locked for peer-reviewing! Pending...");
            }else paper.setStatus("You still can edit or delete your paper.");
            paperdao.save(paper);
            return paper;
        }else return null;
    }

    @Override
    public void deletePaper(Paper paperToDelete) {
        if(!paperToDelete.isPublish()){
            paperdao.delete(paperToDelete);
        }
    }

    @Override
    public Set<Paper> getUserPapers(User author) {
        return paperdao.findByAuthor(author);
    }

    @Override
    public Paper qualifyPaper(Paper paperToQualify, QualifyPaperRequest qualifyPaperRequest) {
        if(!paperToQualify.isApproved() && paperToQualify.isPublish() && judgmentdao.findById(qualifyPaperRequest.getJudgmentid()).isPresent()){
            Paper paper = paperdao.findById(paperToQualify.getPaperid()).get();
            Judgment judgment = judgmentdao.findById(qualifyPaperRequest.getJudgmentid()).get();
            paper.setJudgment(judgment);
            paper.setApproved(qualifyPaperRequest.isApproved());
            if(qualifyPaperRequest.isApproved()) paper.setStatus("Closed : Paper approved!");
            else paper.setStatus(qualifyPaperRequest.getStatus());
            paperdao.save(paper);
            return paper;
        }else return null;
    }

    @Override
    public Set<Paper> getApprovedPapers(boolean approved) {
        if(approved) return paperdao.findByApprovedTrue();
        else return paperdao.findByApprovedFalse();
    }

    @Override
    public Set<Paper> getUserPendingPapers(User author) {
        return paperdao.findByAuthorAndPublishTrueAndApprovedFalse(author);
    }

}
