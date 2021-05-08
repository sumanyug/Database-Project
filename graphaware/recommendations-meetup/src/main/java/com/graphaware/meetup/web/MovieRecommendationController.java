package com.graphaware.meetup.web;

import com.graphaware.meetup.MyRecommendationEngine;
import com.graphaware.meetup.MovieRecommendationEngine;
import com.graphaware.meetup.HomeRecommendationEngine;
import com.graphaware.meetup.TrendingRecommendationEngine;
import com.graphaware.reco.generic.config.SimpleConfig;
import com.graphaware.reco.generic.engine.TopLevelRecommendationEngine;
import com.graphaware.reco.generic.result.Recommendation;
import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.graphaware.common.util.IterableUtils.getSingle;

@Controller
public class MovieRecommendationController {

    private final GraphDatabaseService database;

    private TopLevelRecommendationEngine<Node, Node> engine = new MovieRecommendationEngine();
    private TopLevelRecommendationEngine<Node, Node> home_engine = new HomeRecommendationEngine();
    private TopLevelRecommendationEngine<Node, Node> trending_engine = new TrendingRecommendationEngine();


    @Autowired
    public MovieRecommendationController(GraphDatabaseService database) {
        System.out.println("database");
        System.out.println(database);
        this.database = database;
    }

    @RequestMapping("/home/{username}")
    @ResponseBody
    public List<RecommendationVO> recommend(@PathVariable String username, @RequestParam(defaultValue = "10") int limit) {
        try (Transaction tx = database.beginTx()) {
            return convert(home_engine.recommend(findByUsername(username), new SimpleConfig(limit)));
        }
    }
    @RequestMapping("/home/{username}/trending")
    @ResponseBody
    public List<RecommendationVO> recommend_trending(@PathVariable String username, @RequestParam(defaultValue = "10") int limit) {
        try (Transaction tx = database.beginTx()) {
            return convert(trending_engine.recommend(findByUsername(username), new SimpleConfig(limit)));
        }
    }


    @RequestMapping("/home/{username}/movie/{movie_id}")
    @ResponseBody
    public List<RecommendationVO> recommend_movie(@PathVariable String username,@PathVariable String movie_id, @RequestParam(defaultValue = "10") int limit) {
        try (Transaction tx = database.beginTx()) {
            return convert(engine.recommend( findByUsername(username), new SimpleConfig(limit)));
        }
    }

    private Node findMovieByID(String id) {
        System.out.println(id);
        int int_id = Integer.parseInt(id);
        return getSingle(database.findNodes(Label.label("Movie"), "id", int_id), "Movie with id " + id + " does not exist.");
    }

    private Node findByUsername(String username) {
        return getSingle(database.findNodes(Label.label("User"), "username", username), "Person with username " + username + " does not exist.");
    }

    private List<RecommendationVO> convert(List<Recommendation<Node>> recommendations) {
        List<RecommendationVO> result = new LinkedList<>();

        for (Recommendation<Node> recommendation : recommendations) {
            result.add(new RecommendationVO(recommendation.getItem().getProperty("movieid", "unknown").toString(), recommendation.getItem().getProperty("name", "unknown").toString(), recommendation.getScore()));
        }

        return result;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String translateException(NotFoundException e) {
        return e.getMessage();
    }
}
