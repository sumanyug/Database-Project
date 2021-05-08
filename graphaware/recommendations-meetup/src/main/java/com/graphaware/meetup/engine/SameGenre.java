package com.graphaware.meetup.engine;

import com.graphaware.reco.generic.transform.ParetoScoreTransformer;
import com.graphaware.reco.generic.transform.ScoreTransformer;
import com.graphaware.meetup.domain.Relationships;
import com.graphaware.reco.neo4j.engine.SomethingInCommon;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import java.util.Collections;
import java.util.Map;

import static org.neo4j.graphdb.Direction.OUTGOING;;

public class SameGenre extends SomethingInCommon{
    private ScoreTransformer<Node> scoreTransformer =  ParetoScoreTransformer.create(4, 8);
    @Override
    public String name() {
        return "genre-in-Common";
    }

    @Override
    protected ScoreTransformer<Node> scoreTransformer() {
        return scoreTransformer;
    }

    @Override
    protected RelationshipType getType() {
        return Relationships.HAS_GENRE;
    }

    @Override
    protected Direction getDirection() {
        return OUTGOING;
    }

    @Override
    protected Map<String, Object> details(Node thingInCommon, Relationship withInput, Relationship withOutput) {
        return Collections.singletonMap("name", thingInCommon.getProperty("name"));
    }
}
