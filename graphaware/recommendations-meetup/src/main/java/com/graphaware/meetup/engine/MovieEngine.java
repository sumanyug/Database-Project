package com.graphaware.meetup.engine;

import com.graphaware.reco.generic.config.KeyValueConfig;
import com.graphaware.reco.generic.context.Context;
import com.graphaware.reco.generic.engine.SingleScoreRecommendationEngine;
import com.graphaware.reco.generic.result.PartialScore;

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
import static org.neo4j.graphdb.RelationshipType.withName;

/**
 * A very stupid engine, just recommends the fist X nodes, where X is the requested limit.
 */
public class MovieEngine extends SingleScoreRecommendationEngine<Node, Node> {

    @Override
    protected Map<Node, PartialScore> doRecommendSingle(Node id, Context<Node, Node> context) {
        Map<Node, PartialScore> result = new HashMap<>();

        for (Relationship r : id.getRelationships(withName("RATED"),OUTGOING)) {
            Node node = r.getEndNode();
            addToResult(result, node, score(r));
        }

        return result;
    }

    @Override
    public String name() {
        return "scores of rated movies";
    }

    private PartialScore score(Relationship r) {
        Long rating_obj = (Long)r.getProperty("rating");
        Map<String, Object> reasons = new HashMap<>();
        reasons.put("rating", rating_obj);
        float rating = rating_obj.floatValue();
        // float rating = (float)1.0;

        return new PartialScore(rating, reasons);
    }

    // private ResourceIterable<Node> getAllNodes(Node input) {
    //     return input.getGraphDatabase().getAllNodes();
    // }
}
