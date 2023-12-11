/**
 * CSDS 233 Assignment 6
 * AirportSystem.java
 * 
 * AUTHOR: Kaleb Kim
 * DATE MODIFIED: 12/9/23
 * DESCRIPTION: Airport System class with two private subclasses.
 * 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

class AirportSystem {
    private class Edge {
        private String source; // starting location of the edge
        private String destination; // ending location of the edge
        private int distance; // distance (weight) between start and destination
        
        Edge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.distance = weight;
        }
       
        // String representation of the edge in the format of “[start, destination]”.
        @Override
        public String toString() {
            // return "[" + source + ", " + destination + ", " + distance + "]";
            return "[" + source + ", " + destination + "]";
        }

        // getters/setters don't matter with the fields of nested classes since they can always be accessed in AirportSystem
    }

    private class Vertex {
        private String id; // city name
        private List<Edge> edges; // the cities that are connected to this city by the airport system.

        Vertex(String id) {
            this.id = id;
            this.edges = new ArrayList<Edge>();
        }

        @Override
        public String toString() { // default string is id of vertex
            return id;
        }

        /**
         * Get weight of the edge from the current vertex to a given destination (assuming the edge exists).
         * @param destination Destination
         * @return Weight, 0 if source = destination, -1 if not found
         */
        public int getWeight(String destination) {
            for(Edge e: edges) {
                if(e.destination.equals(destination))
                    return e.distance;
                else if(e.source.equals(destination))
                    return 0;
            }
            return -1;
        }
        // getters/setters don't matter with the fields of nested classes since they can always be accessed in AirportSystem
    }

    /**
     * the adjacency list of the cities. Each node is a city, and each connecting line indicates a flight
     * between two cities. The inner List<Edge> represents a vertex.
     */
    private List<Vertex> connections;

    // constructor (self-explanatory)
    AirportSystem() {
        this.connections = new ArrayList<Vertex>();
    }

    /**
     * Adds a new (undirected) flight to the connections list. Flights must be unique and weight must be non-negative.
     * @param source Name of the city to depart from
     * @param destination Name of the city to arrive at
     * @param weight Non-negative value representing distance
     * @return false if the edge already exists or the weight is negative
     */
    public boolean addEdge(String source, String destination, int weight) {
        if(weight < 0) return false; // weight must be non-negative!
        int s = getCityIndex(source); // get index of source city in connections
        int d = getCityIndex(destination); // get index of destination city in connections

        // create vertices if they don't exist (when index is still -1)
        if(s == -1) {
            connections.add(new Vertex(source));
            s = connections.size() - 1; // update s index
        }
        if(d == -1) {
            connections.add(new Vertex(destination));
            d = connections.size() - 1; // update d index
        }
        boolean out = addEdgeToVertex(s, destination, weight);
        out = addEdgeToVertex(d, source, weight); // for undirected graph
        return out; // if both edges were added successfully, it'll return true
    }

    /**
     * Helper method. Adds an edge to a vertex if the edge doesn't already exist. If it does, it updates the weight.
     * @param i Index of vertex in connections to add to
     * @param destination Name of destination (id of another vertex)
     * @param weight Non-negative value
     * @return true if added, false if updated or already exists
     */
    private boolean addEdgeToVertex(int i, String destination, int weight) {
        Vertex v = connections.get(i);
        for(Edge e: v.edges) {
            if(e.source.equals(v.id) && e.destination.equals(destination)) {
                e.distance = weight;
                return false;
            } else if(e.source.equals(destination) && e.destination.equals(v.id)) {
                e.distance = weight;
                return false;
            }
        }
        v.edges.add(new Edge(v.id, destination, weight));
        return true;
    }

    /**
     * Returns the shortest distance between city A and city B using Dijkstra’s algorithm
     * @param cityA Starting city (source vertex)
     * @param cityB Ending city (destination vertex)
     * @return Shortest distance between both cities, -1 if not possible
     */
    public int shortestDistance(String cityA, String cityB) { // for this implementation, using indexes to correspond to cities
        int V = connections.size(); // amount of vertices (alias)
        String src = cityA; // source vertex (alias)

        int[] D = new int[V]; // minimum distances from src to vertex i
        boolean[] N = new boolean[V]; // marks if in set N (finalized set)
        for(int i = 0; i < V; i++) { // initialize minimum distances from src
            if(adjacent(src, connections.get(i).toString())) // if neighbors
                D[i] = connections.get(i).getWeight(src); // weight from source to neighbor
            else // if not neighbors
                D[i] = Integer.MAX_VALUE; // to infinity and beyond
        }
 
        D[getCityIndex(src)] = 0; // distance from source is 0

        for(int v = 0; v < V; v++) {
            // start by updating set N by finding smallest distance
            int min = Integer.MAX_VALUE, minIndex = -1;
            for(int i = 0; i < V; i++) {
                if(!N[i] && D[i] < min) { // if not finalized, extract the smallest distance we got
                    min = D[i];
                    minIndex = i;
                }
            }
            if(minIndex == -1) break; // no more distances to be found, done with iterating through vertices
            N[minIndex] = true; // shortest path found so it's finalized

            // now that we got another boi in our finalized list, update paths
            // iterating through edges that come from the newly added element in finalized set
            int d; // neighbor vertex index
            for(Edge e: connections.get(minIndex).edges) {
                d = getCityIndex(e.destination);
                if(!N[d] && (D[minIndex] + e.distance < D[d])) // if the neighbor isn't finalized and new short distance + neighbor distance is smaller
                    D[d] = D[minIndex] + e.distance;
            }
        }

        return D[getCityIndex(cityB)] != Integer.MAX_VALUE ? D[getCityIndex(cityB)] : -1; // return -1 if no connection is possible
    }
    
    /**
     * Uses Prim’s algorithm to create a minimum spanning tree about the starting point.
     * @return A tree. Minimum Spanning Tree (MST).
     */
    public List<Edge> minimumSpanningTree() { // similar to DIJKSTRA!
        List<Edge> tree = new ArrayList<Edge>(); // tree

        int V = connections.size(); // amount of vertices (alias)

        int[] D = new int[V]; // minimum distances from set A to vertex i
        boolean[] A = new boolean[V]; // marks which indexes are part of set A
        int[] P = new int[V]; // predecessor indexes for shortest paths to set A
        
        for(int i = 0; i < V; i++) { // initialize minimum distances from src
            if(adjacent(connections.get(0).id, connections.get(i).toString())) { // if neighbors
                D[i] = connections.get(0).getWeight(connections.get(i).id); // weight from source to neighbor
                P[i] = 0;
            } else { // if not neighbors
                D[i] = Integer.MAX_VALUE; // to infinity and beyond
                P[i] = -1;
            }
        }


        A[0] = true; // <---- I NEEDED THIS STUPID LINE OF CODE AND I DEBUGGED FOR AN HOUR TO FIX THIS I LOST MY MIND

        
        for(int v = 0; v < V-1; v++) {
            // add to set A by finding smallest distance
            int min = Integer.MAX_VALUE, minIndex = -1; // minIndex represents index that we're bout to add to set A
            for(int i = 0; i < V; i++) {
                if(!A[i] && D[i] < min) { // if not finalized, extract the smallest distance we got
                    min = D[i];
                    minIndex = i;
                }
            }
            if(minIndex == -1) break; // no more tree. disconnected.
            
            A[minIndex] = true; // part of set A now!
            tree.add(new Edge(connections.get(P[minIndex]).id, connections.get(minIndex).id, D[minIndex])); // add shortest edge to tree, briding from predecessor to current

            // now that we got another boi in our finalized list, update paths about 
            // iterating through edges that come from the newly added element in finalized set
            int d; // neighbor vertex index
            for(Edge e: connections.get(minIndex).edges) {
                d = getCityIndex(e.destination);
                if(!A[d] && e.distance < D[d]) { // if the neighbor isn't finalized and a neighbor distance is smaller
                    D[d] = e.distance; // update
                    P[d] = getCityIndex(e.source);
                }
            }
        }
        
        return tree;
    }
    
    /**
     * Returns a list of all the cities from the start using BFS, assuming the start vertex exists.
     * @param start Starting vertex.
     * @return Visited cities in order of BFS.
     */
    public List<String> breadthFirstSearch(String start) {
        if(getCityIndex(start) == -1) return null; // just in case there's a bozo lol

        List<String> q = new ArrayList<String>(connections.size()); // queue of vertices to visit
        List<String> out = new ArrayList<String>(connections.size()); // output

        q.add(start);
        while(!q.isEmpty()) { // continue until we dump the queue
            for(Edge e: connections.get(getCityIndex(q.get(0))).edges) { // iterating through edges of q's first vertex
                if(!q.contains(e.destination) && !out.contains(e.destination)) { // if not in q or out yet, add to q
                    q.add(e.destination);
                }
            }
            // move the front of queue to the output list
            out.add(q.get(0));
            q.remove(0);
        }

        return out;
    }

    /**
     * Prints the graph in a clear, readable format.
     */
    public void printGraph() {
        for(Vertex v: connections) { // iterate through vertices
            System.out.printf("V: %s | E: ", v);
            for(Edge e: v.edges) { // in each vertex, iterate through edges
                System.out.printf("%s ", e);
            }
            System.out.print("\n"); // can't forget that new line!
        }
    }

    /**
     * Helper method. Returns the index of the city in the connections array.
     * @return -1 if not found.
     */
    private int getCityIndex(String id) {
        int size = connections.size(); // reduces time complexity if stpred as constant
        for(int i = 0; i < size; i++) {
            if(connections.get(i).toString().equals(id))
                return i;
        }
        return -1;
    }

    /**
     * Helper method. Checks if two vertices are adjacent neighbors. Assumes the cities exist.
     * @param a Vertex a
     * @param b Vertex b
     * @return true if vertices, false if not
     */
    private boolean adjacent(String a, String b) {
        for(Vertex v: connections) {
            if(v.id.equals(a)) { // get source
                for(Edge e: v.edges) {
                    if(e.source.equals(a) && e.destination.equals(b)) // source and destination match
                        return true;
                }
                return false; // well if it's one way there's something wrong?
            }
        }
        return false;
    }
}