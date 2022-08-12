package vttp2022.ssf.aug12_assessment.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.embedded.NettyWebServerFactoryCustomizer;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.aug12_assessment.models.NewsArticle;
import vttp2022.ssf.aug12_assessment.repositories.NewsRepository;

@Service
public class NewsService {

    @Value("${API_KEY}")
    private String apiKey;

    @Autowired
    private NewsRepository newsRepo;

    private static final String NEWS_URL = "https://min-api.cryptocompare.com/data/v2/news/";

    public List<NewsArticle> getArticles() {

        String payload;
        try {
            String url = UriComponentsBuilder.fromUriString(NEWS_URL)
                    .queryParam("lang", "EN")
                    .queryParam("api_key", apiKey)
                    .toUriString();

            RequestEntity<Void> requestEntity = RequestEntity.get(url).build();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            payload = responseEntity.getBody();

        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return Collections.emptyList();
        }

        Reader stringReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(stringReader);
        JsonObject newsObject = jsonReader.readObject();
        JsonArray dataArray = newsObject.getJsonArray("Data");

        List<JsonObject> dataList = new LinkedList<>();
        for (int i = 0; i < dataArray.size(); i++) {
            dataList.add(dataArray.getJsonObject(i));
        }

        List<NewsArticle> newsList = new LinkedList<>();
        for (JsonObject jo : dataList) {
            newsList.add(new NewsArticle(jo));
        }

        return newsList;
    }

    public void saveArticles(List<NewsArticle> articlesList) {
        newsRepo.saveArticles(articlesList);
    }

    public Optional<NewsArticle> getNewsArticle(String id) {
        Optional<NewsArticle> opt = newsRepo.getArticles(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        NewsArticle newsArticle = opt.get();
        return Optional.of(newsArticle);
    }
}
