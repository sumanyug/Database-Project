<a name="top"/>

GraphAware Recommendations Meetup
=================================

[![Build Status](https://travis-ci.org/graphaware/recommendations-meetup.png)](https://travis-ci.org/graphaware/recommendations-meetup)

Note to Sumanyu

1. [Download GraphAware](https://neo4j-plugins-public.s3.eu-west-1.amazonaws.com/graphaware-server-all-3.5.14.55.jar) and place into `plugins` of Neo4j
2. Enter the current working directory and run `mvn clean install -Dmaven.javadoc.skip=true`.
3. take the produced .jar **with dependencies** from `target` and place into `plugins` as well
4. Add the following lines to `conf.neo4j`
    - `dbms.jvm.additional=-Dunsupported.dbms.udc.source=tarball`
    - `dbms.unmanaged_extension_classes=com.graphaware.server=/graphaware`
    - `com.graphaware.runtime.enabled=true`
5. (re)start Neo4j
6. hit one of the URLs you defined in your controllers, e.g. [http://localhost:7474/graphaware/home/2] where 2 is the id of the user.
7. Look at [~/src/test/java/com/graphaware/meetup/MyRecommendationEngineEnd2EndTest.java] to understand how to use the recommendation engine from the spring MVC controller.
