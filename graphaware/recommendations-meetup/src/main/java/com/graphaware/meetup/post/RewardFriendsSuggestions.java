/*
 * Copyright (c) 2013-2020 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.meetup.post;

import com.graphaware.reco.generic.context.Context;
import com.graphaware.reco.generic.post.BasePostProcessor;
import com.graphaware.reco.generic.result.Recommendation;
import com.graphaware.reco.generic.result.Recommendations;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.graphaware.common.util.EntityUtils.getInt;
import static org.neo4j.graphdb.Direction.BOTH;
import static org.neo4j.graphdb.Direction.OUTGOING;;
import static org.neo4j.graphdb.RelationshipType.withName;

/**
 * Rewards people who live in the same country as the company by 10 (hardcoded) points.
 */
public class RewardFriendsSuggestions extends BasePostProcessor<Node, Node> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String name() {
        return "friend-recommendations";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPostProcess(Recommendations<Node> recommendations, Node user, Context<Node, Node> context) {
        System.out.println("Enter Post Process");
        for (Recommendation<Node> reco : recommendations.get()) {
            float num = 0;
            float total_score = 0;

            for (Relationship f : user.getRelationships(withName("FRIENDS_WITH"),BOTH)){
                Node friend = f.getOtherNode(user);

                for (Relationship r: friend.getRelationships(withName("RATED"),OUTGOING))
                {
                    if (reco.getItem().equals(r.getEndNode())) {
                        int rating = getInt(r,"rating",0);
                        if(rating != 0)
                        {
                            total_score = total_score + rating - 2;
                            num = num + 1;
                        }
                    }
                }
            }
            if(num > 3)
            {
                Map<String, Object> reasons = new HashMap<>();
                reasons.put("num friends", num);
                float scoreValue = total_score/num;
                reco.add(name(), scoreValue, reasons);
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float maxPositiveScore(Node input, Context<Node, Node> context) {
        return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float maxNegativeScore(Node input, Context<Node, Node> context) {
        return 0;
    }
}

