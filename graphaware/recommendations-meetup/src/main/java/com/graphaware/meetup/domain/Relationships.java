package com.graphaware.meetup.domain;

import org.neo4j.graphdb.RelationshipType;

public enum Relationships implements RelationshipType {
    RATED,
    FRIENDS_WITH
}