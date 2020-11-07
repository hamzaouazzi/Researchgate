package ma.ensa.researchgate.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.researchgate.dao.PaperRepository;
import ma.ensa.researchgate.dto.*;
import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.entities.User;
import ma.ensa.researchgate.service.serviceimpl.Paperserviceimpl;
import ma.ensa.researchgate.service.serviceimpl.Userserviceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/author")
@AllArgsConstructor
@Slf4j
public class AuthorController {

    private final PaperRepository paperdao;
    private final Paperserviceimpl paperserviceimpl;
    private final Userserviceimpl userserviceimpl;

    @PostMapping("/newpaper") //AUTHOR
    public ResponseEntity<PaperResponse> newPaper(@RequestBody NewPaperRequest newPaperRequest) {
        User author = userserviceimpl.getCurrentUser();
        Paper paper = paperserviceimpl.newPaper(newPaperRequest,author);
        PaperResponse response = new PaperResponse();
        response.setAuthor(author.getEmail());
        response.setPublish(paper.isPublish());
        response.setTitle(paper.getTitle());
        response.setStatus(paper.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/editpaper/{id}") //AUTHOR
    public ResponseEntity<PaperResponse> editPaper(@PathVariable("id") long id, @RequestBody EditPaperRequest editPaperRequest) {
        User author = userserviceimpl.getCurrentUser();
        Paper paperToEdit = paperdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        if(author.getPapers().contains(paperToEdit)){
            Paper paper = paperserviceimpl.editPaper(paperToEdit,editPaperRequest);
            PaperResponse response = new PaperResponse();
            response.setAuthor(author.getEmail());
            response.setPublish(paper.isPublish());
            response.setTitle(paper.getTitle());
            response.setStatus(paper.getStatus());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else return null;
    }

    @DeleteMapping("/deletepaper/{id}") //AUTHOR
    public ResponseEntity<String> deletePaper(@PathVariable("id") long id) {
        User author = userserviceimpl.getCurrentUser();
        Paper paperToDelete = paperdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        if(author.getPapers().contains(paperToDelete)){
            paperserviceimpl.deletePaper(paperToDelete);
            return new ResponseEntity<>("Paper successfully deleted!", HttpStatus.OK);
        }return new ResponseEntity<>("Something went wrong!", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/papers") //AUTHOR
    public ResponseEntity<Set<DetailedPaperResponse>> getAuthorPapers() {
        User currentUser = userserviceimpl.getCurrentUser();
        Set<Paper> papers = paperserviceimpl.getUserPapers(currentUser);
        Set<DetailedPaperResponse> detailedPaperResponse = new HashSet<>();
        DetailedPaperResponse publicPaper = new DetailedPaperResponse();
        for(Paper paper : papers){
            publicPaper.setAffiliations(paper.getAffiliations());
            publicPaper.setAuthorFirstName(paper.getAuthor().getFirstname());
            publicPaper.setAuthorFirstName(paper.getAuthor().getFirstname());
            publicPaper.setTitle(paper.getTitle());
            publicPaper.setCo_authors(paper.getCo_authors());
            publicPaper.setKeywords(paper.getKeywords());
            publicPaper.setPaperabstract(paper.getPaperabstract());
            publicPaper.setContent(paper.getContent());
            detailedPaperResponse.add(publicPaper);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(detailedPaperResponse);
    }

    @GetMapping("/pendings") //AUTHOR
    public ResponseEntity<Set<PendingPaperResponse>> getAuthorPendingPapers() {
        User currentUser = userserviceimpl.getCurrentUser();
        Set<Paper> papers = paperserviceimpl.getUserPendingPapers(currentUser);
        Set<PendingPaperResponse> detailedPaperResponse = new HashSet<>();
        PendingPaperResponse publicPaper = new PendingPaperResponse();
        for(Paper paper : papers){
            publicPaper.setAffiliations(paper.getAffiliations());
            publicPaper.setAuthorFirstName(paper.getAuthor().getFirstname());
            publicPaper.setAuthorFirstName(paper.getAuthor().getFirstname());
            publicPaper.setTitle(paper.getTitle());
            publicPaper.setCo_authors(paper.getCo_authors());
            publicPaper.setKeywords(paper.getKeywords());
            publicPaper.setPaperabstract(paper.getPaperabstract());
            publicPaper.setContent(paper.getContent());
            publicPaper.setApproved(paper.isApproved());
            publicPaper.setStatus(paper.getStatus());
            publicPaper.setAssignments(paper.getAssignments());
            publicPaper.setJudgment(paper.getJudgment());
            detailedPaperResponse.add(publicPaper);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(detailedPaperResponse);
    }

}
