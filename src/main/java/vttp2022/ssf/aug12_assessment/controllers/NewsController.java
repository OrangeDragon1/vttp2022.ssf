package vttp2022.ssf.aug12_assessment.controllers;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.ssf.aug12_assessment.models.NewsArticle;
import vttp2022.ssf.aug12_assessment.services.NewsService;

@Controller
@RequestMapping(path = "/", produces = "text/html")
public class NewsController {

    @Autowired
    private NewsService newsSvc;
    private List<NewsArticle> articlesList = new LinkedList<>();
    private List<NewsArticle> savedList = new LinkedList<>();
    private List<String> savedRow = new LinkedList<>();

    @GetMapping
    public String getNews(Model model) {

        articlesList = newsSvc.getArticles();
        for (int i = 0; i < articlesList.size(); i++) {
            articlesList.get(i).setRow(("row" + i).toString());
        }
        System.out.println(articlesList.get(0).getRow());

        model.addAttribute("articlesList", articlesList);

        return "index";
    }

    @PostMapping("/articles")
    public String postNews(
            @RequestBody MultiValueMap<String, String> form,
            Model model) {

        for (int i = 0; i < articlesList.size(); i++) {
            String articles = form.getFirst(("row" + i).toString());
            if (articles != null) {
                savedRow.add(articles);
            }
        }

        for (String s : savedRow) {
            JsonObject JO = createJsonObject(s);
            savedList.add(new NewsArticle(JO));
        }

        newsSvc.saveArticles(savedList);
        // System.out.println(savedRow.toString());
        // String savedArticles = form.getFirst("check");
        // System.out.println(savedArticles);
        // JsonObject savedJO = createJsonObject(savedArticles);
        // savedList.add(new NewsArticle(savedJO));
        // newsSvc.saveArticles(savedList);

        model.addAttribute("articlesList", articlesList);

        return "index";
    }

    public JsonObject createJsonObject(String jsonString) {
        Reader reader = new StringReader(jsonString);
        return Json
                .createReader(reader)
                .readObject();
    }
}
