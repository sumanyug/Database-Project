package com.graphaware.meetup.engine;

import com.graphaware.reco.generic.config.KeyValueConfig;
import com.graphaware.reco.generic.context.Context;
import com.graphaware.reco.generic.engine.SingleScoreRecommendationEngine;
import com.graphaware.reco.generic.result.PartialScore;
import com.graphaware.reco.neo4j.engine.CollaborativeEngine;
import org.neo4j.graphdb.Label;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.BOTH;
import com.graphaware.meetup.domain.Relationships;
import org.neo4j.graphdb.RelationshipType;

import static com.graphaware.common.util.EntityUtils.getInt;


/**
 * A very stupid engine, just recommends the fist X nodes, where X is the requested limit.
 */
public class HomeEngine extends CollaborativeEngine {


    private static final String RATING = "rating";
    private final Label USER = Label.label("User");
    private final Label MOVIE = Label.label("Movie");

    @Override
    public String name() {
        return "user-user collaborative filtering";
    }

    @Override
    protected RelationshipType getType() {
        return Relationships.RATED;
    }

    @Override
    protected Direction getDirection() {
        return Direction.OUTGOING;
    }

    @Override
    protected boolean acceptableThroughNode(Node node) {
        return node.hasLabel(MOVIE);
    }

    @Override
    protected boolean acceptableSimilarNode(Node node) {
        return node.hasLabel(USER);
    }

    @Override
    protected int scoreNode(Node recommendation, Node throughNode, Node similarNode, Relationship r1, Relationship r2, Relationship r3) {
        int rat1 = getInt(r1, RATING, 2);
        int rat2 = getInt(r2, RATING, 2);
        int rat3 = getInt(r3, RATING, 2);
        
        return (rat1 - 2)*(rat2 -2) * rat3 ;
    }

    @Override
    protected Map<String, Object> details(Node throughNode, Node similarNode, Relationship r1, Relationship r2, Relationship r3) {

        Map<String, Object> result = new HashMap<>();
        // result.put("Movie", throughNode.getProperty("name"));
        result.put("User", similarNode.getProperty("username"));
        result.put("rating",r3.getProperty("rating"));
        return result;
    }
}
