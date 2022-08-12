package vttp2022.ssf.aug12_assessment.repositories;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.ssf.aug12_assessment.models.NewsArticle;

@Repository
public class NewsRepository {

    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void saveArticles(List<NewsArticle> articlesList) {

        ListOperations<String, String> listOps = redisTemplate.opsForList();
        for (NewsArticle na : articlesList) {
            listOps.leftPush(na.getId(), na.toJson().toString());
        }
    }

    public Optional<NewsArticle> getArticles(String id) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String jsonString = listOps.leftPop(id);
        if (jsonString == null) {
            return Optional.empty();
        }
        JsonObject jsonObject = createJsonObject(jsonString);
        NewsArticle newsArticle = new NewsArticle(jsonObject);
        return Optional.of(newsArticle);
    }

    public JsonObject createJsonObject(String jsonString) {
        Reader reader = new StringReader(jsonString);
        return Json
                .createReader(reader)
                .readObject();
    }
}
