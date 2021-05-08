package com.graphaware.meetup;

import com.graphaware.meetup.engine.DummyEngine;
import com.graphaware.meetup.engine.MovieEngine;
import com.graphaware.meetup.engine.HomeEngine;
import com.graphaware.reco.neo4j.engine.CypherEngine;
import com.graphaware.meetup.post.RewardFriendsSuggestions;
import com.graphaware.reco.generic.engine.RecommendationEngine;
import com.graphaware.reco.generic.filter.BlacklistBuilder;
import com.graphaware.reco.generic.filter.Filter;
import com.graphaware.reco.generic.log.Logger;
import com.graphaware.reco.neo4j.filter.ExistingRelationshipBlacklistBuilder;
import com.graphaware.reco.generic.post.PostProcessor;
import com.graphaware.reco.neo4j.engine.Neo4jTopLevelDelegatingRecommendationEngine;
import org.neo4j.graphdb.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.neo4j.graphdb.Direction.BOTH;
import static com.graphaware.meetup.domain.Relationships.RATED;


public class HomeRecommendationEngine extends Neo4jTopLevelDelegatingRecommendationEngine{

    @Override
    protected List<RecommendationEngine<Node, Node>> engines() {
        return Arrays.<RecommendationEngine<Node, Node>>asList(
                new CypherEngine("home reco",
                "MATCH (u:User)-[r:RATED]->(m:Movie)-[s:SIMILARITY]-(reco:Movie) WHERE id(u)={id} WITH 5*avg(r.rating*s.coeff) AS score, reco return score, reco order by score desc;"),
                new CypherEngine("Trending Movies",
                "MATCH (n:User)-[r:RATED]->(reco:Movie) WHERE r.timestamp > 886286637 RETURN reco,avg(r.rating) as avg, count(*) as s, (count(*)*avg(r.rating)*avg(r.rating)/1000) as score order by score desc;")
                ); //todo remove this engine and add your own
    }

    @Override
    protected List<PostProcessor<Node, Node>> postProcessors() {
        return Arrays.<PostProcessor<Node, Node>>asList(
                new RewardFriendsSuggestions()
        );        
    }

    @Override
    protected List<BlacklistBuilder<Node, Node>> blacklistBuilders() {
        return Arrays.<BlacklistBuilder<Node, Node>>asList(
                new ExistingRelationshipBlacklistBuilder(RATED, BOTH)
        );        
    }

    @Override
    protected List<Filter<Node, Node>> filters() {
        return Collections.emptyList(); //todo add your own filters instead
    }

    @Override
    protected List<Logger<Node, Node>> loggers() {
        return Collections.emptyList(); //todo optionally add your own logger
    }
}
