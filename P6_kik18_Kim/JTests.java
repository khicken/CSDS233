/**
 * CSDS 233 Assignment 6
 * JTests.java
 * 
 * AUTHOR: Kaleb Kim
 * DATE MODIFIED: 12/9/23
 * DESCRIPTION: JUnit tests for at least 3 respective cases.
 * 
 */

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JTests {
    void addConnection(String cityA, String cityB, int distance, AirportSystem s) {
        s.addEdge(cityA, cityB, distance);
    }

    @Test
    public void assertMethods() {
        // case one
        AirportSystem airportOne = new AirportSystem();

        addConnection("Buffalo","Cleveland",191, airportOne);
        addConnection("Buffalo","Pittsburgh",216, airportOne);
        addConnection("Cleveland","Pittsburgh",135, airportOne);
        addConnection("Cleveland","Columbus",143, airportOne);
        addConnection("Cleveland","Toledo",117, airportOne);
        addConnection("Pittsburgh","Columbus",185, airportOne);
        addConnection("Columbus","Cincinnati",101, airportOne);
        addConnection("Cincinnati","Toledo",198, airportOne);
        addConnection("Cincinnati","Indianapolis",110, airportOne);
        addConnection("Toledo","Detroit",60, airportOne);
        addConnection("Toledo","Chicago",244, airportOne);
        addConnection("Detroit","Chicago",281, airportOne);
        addConnection("Chicago","Indianapolis",181, airportOne);
        addConnection("Miami", "Dallas", 110, airportOne);

        List<String> startCities = new ArrayList<>();
        startCities.add("Toledo");
        startCities.add("Buffalo");
        startCities.add("Chicago");

        List<String> endCities = new ArrayList<>();
        endCities.add("Miami");
        endCities.add("Indianapolis");
        endCities.add("Pittsburgh");

        int[] correctMinimumDistances = {-1, 545, 496};

        airportOne.printGraph();
        String[] BFSCases = new String[]{ "Cincinnati", "Columbus", "Toledo", "Indianapolis", "Cleveland", "Pittsburgh", "Detroit", "Chicago", "Buffalo" };
        List<String> validBFSOut = Arrays.stream(BFSCases).toList();
        assertEquals(airportOne.breadthFirstSearch("Cincinnati"), validBFSOut);
        for (int i = 0; i < 3; i++) {
            assertEquals(correctMinimumDistances[i], airportOne.shortestDistance(startCities.get(i), endCities.get(i)));
            System.out.println("Shortest Distance from " + startCities.get(i) + " to " + endCities.get(i) + ": " + airportOne.shortestDistance(startCities.get(i), endCities.get(i)));
        }

        AirportSystem airportTwo = new AirportSystem();
        addConnection("a","b",2, airportTwo);
        addConnection("a","c",3, airportTwo);
        addConnection("a","d",4, airportTwo);

        AirportSystem airportThree = new AirportSystem();
        addConnection("a","e",3, airportThree);
        addConnection("a","f",100, airportThree);
        addConnection("f","g",4, airportThree);

        List<String> MSTCases = new LinkedList<>();
        MSTCases.add("[[Buffalo, Cleveland], [Cleveland, Toledo], [Toledo, Detroit], [Cleveland, Pittsburgh], [Cleveland, Columbus], [Columbus, Cincinnati], [Cincinnati, Indianapolis], [Indianapolis, Chicago]]");
        MSTCases.add("[[a, b], [a, c], [a, d]]");
        MSTCases.add("[[a, e], [a, f], [f, g]]");

        assertEquals(MSTCases.get(0), airportOne.minimumSpanningTree().toString());
        assertEquals(MSTCases.get(1), airportTwo.minimumSpanningTree().toString());
        assertEquals(MSTCases.get(2), airportThree.minimumSpanningTree().toString());
    }
}
