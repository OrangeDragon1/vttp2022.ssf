package vttp2022.ssf.aug12_assessment.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.ssf.aug12_assessment.models.NewsArticle;
import vttp2022.ssf.aug12_assessment.services.NewsService;

@RestController
@RequestMapping(path = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsRESTController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping("{id}")
    public ResponseEntity<String> getNews(
            @PathVariable String id) {
        
        Optional<NewsArticle> opt = newsSvc.getNewsArticle(id);
        
        if (opt.isEmpty()) {
            JsonObject errorResponse = Json
                    .createObjectBuilder()
                    .add("error", "Cannot find news article %s".formatted(id))
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorResponse.toString());
        }

        NewsArticle newsArticle = opt.get();
        JsonObject jsonArticle = newsArticle.toJson();
        String payload = jsonArticle.toString();

        return ResponseEntity.ok(payload);
    }
}
