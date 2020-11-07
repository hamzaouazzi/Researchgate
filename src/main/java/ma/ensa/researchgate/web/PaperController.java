package ma.ensa.researchgate.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.researchgate.dao.PaperRepository;
import ma.ensa.researchgate.dto.DetailedPaperResponse;
import ma.ensa.researchgate.dto.SlimPaperResponse;
import ma.ensa.researchgate.entities.Paper;
import ma.ensa.researchgate.service.serviceimpl.Paperserviceimpl;
import ma.ensa.researchgate.service.serviceimpl.Userserviceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/papers")
@AllArgsConstructor
@Slf4j
public class PaperController {

    private final PaperRepository paperdao;
    private final Paperserviceimpl paperserviceimpl;
    private final Userserviceimpl userserviceimpl;


    @GetMapping("/highlights") //USER
    public ResponseEntity<Set<SlimPaperResponse>> getApprovedPapers() {
        Set<Paper> papers = paperserviceimpl.getApprovedPapers(true);
        Set<SlimPaperResponse> slimPaperResponse = new HashSet<>();
        SlimPaperResponse publicPaper = new SlimPaperResponse();
        for(Paper paper : papers){
            publicPaper.setAuthorFirstName(paper.getAuthor().getFirstname());
            publicPaper.setAuthorFirstName(paper.getAuthor().getFirstname());
            publicPaper.setTitle(paper.getTitle());
            publicPaper.setCo_authors(paper.getCo_authors());
            publicPaper.setKeywords(paper.getKeywords());
            publicPaper.setPaperabstract(paper.getPaperabstract());
            slimPaperResponse.add(publicPaper);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(slimPaperResponse);
    }

    @GetMapping("/details") //Authenticated USER
    public ResponseEntity<Set<DetailedPaperResponse>> getApprovedPapersContent() {
        Set<Paper> papers = paperserviceimpl.getApprovedPapers(true);
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

}
