import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Somtochukwu Nwagbata
 * @version 1.0
 * @userid snwagbata3
 * @GTID 903685352
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex does not exist in graph");
        }

        HashSet<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        visited.add(start);
        queue.add(start);

        // adjacency list creates a list where each element is a list of
        // neighbors it is connected to
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        //get all neighbors of start from adjacency list
        List<Vertex<T>> adjacentVertices = new LinkedList<>();
        updateAdjacentVerticesList(start, adjacentVertices, adjList);


        // while queue is not empty
        while (!queue.isEmpty()) {
            // get the first element in the queue
            Vertex<T> current = queue.remove();
            // update adjacent vertices
            updateAdjacentVerticesList(current, adjacentVertices, adjList);
            for (Vertex<T> neighbor : adjacentVertices) {
                // if the neighbor has not been visited
                if (!visited.contains(neighbor)) {
                    // add it to the visited set
                    visited.add(neighbor);
                    // add it to the queue
                    queue.add(neighbor);
                }
            }
        }

        return new LinkedList<>(visited);
    }

    /**
     * Updates the list of vertices adjacent to the current vertex
     *
     * @param current the current vertex
     * @param adjacentVertices the list of vertices adjacent to the current vertex
     * @param adjList the adjacency list of the graph
     * @param <T> the generic type of the data
     */
    private static <T> void updateAdjacentVerticesList(Vertex<T> current, List<Vertex<T>> adjacentVertices,
                                                       Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        List<VertexDistance<T>> startDdjListNeighbors = adjList.get(current);
        for (VertexDistance<T> neighbor : startDdjListNeighbors) {
            adjacentVertices.add(neighbor.getVertex());
        }
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex does not exist in graph");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        return dfsHelper(start, graph, visited);

    }

    /**
     * Helper method for dfs
     *
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param visited the set of visited vertices
     * @param <T> the generic type of the data
     * @return list of vertices in visited order
     */
    private static <T> List<Vertex<T>> dfsHelper(Vertex<T> start, Graph<T> graph, Set<Vertex<T>> visited) {
        List<Vertex<T>> result = new LinkedList<>();
        result.add(start);
        visited.add(start);

        // adjacency list creates a list where each element is a list of
        // neighbors it is connected to
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        //get all neighbors of start from adjacency list
        List<Vertex<T>> adjacentVertices = new LinkedList<>();
        List<VertexDistance<T>> startDdjListNeighbors = adjList.get(start);
        for (VertexDistance<T> neighbor : startDdjListNeighbors) {
            adjacentVertices.add(neighbor.getVertex());
        }

        // for each neighbor, if it has not been visited, call dfs on it
        for (Vertex<T> adjVertex : adjacentVertices) {
            if (!visited.contains(adjVertex) && !result.contains(adjVertex)) {
                result.addAll(dfsHelper(adjVertex, graph, visited));
            }
        }
        return result;
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex does not exist in graph");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();

        // initialize distances to infinity
        for (Vertex<T> vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        // initialize source distance to 0
        pq.add(new VertexDistance<>(start, 0));

        // while there are still vertices to visit
        while (!pq.isEmpty() && !visited.containsAll(graph.getVertices())) {
            VertexDistance<T> current = pq.remove();
            if (!visited.contains(current.getVertex())) {
                visited.add(current.getVertex());
                distances.put(current.getVertex(), current.getDistance());

                /// get adjacent vertices
                Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

                //get all neighbors of start from adjacency list
                List<VertexDistance<T>> adjacentVertices =
                        adjList.get(current.getVertex());

                // for each neighbor
                for (VertexDistance<T> neighbor : adjacentVertices) {
                    // if neighbor is not visited
                    if (!visited.contains(neighbor.getVertex())) {
                        pq.add(new VertexDistance<>(neighbor.getVertex(),
                                current.getDistance() + neighbor.getDistance()));
                    }
                }


            }
        }


        return distances;

    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * You should NOT allow self-loops or parallel edges in the MST.
     * <p>
     * You may import/use PriorityQueue, java.util.Set, and any class that
     * implements the aforementioned interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex does not exist in graph");
        }

        Set<Edge<T>> mst = new HashSet<>();
        Set<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();

        // enqueue all edges from start vertex
        for (Edge<T> edge : graph.getEdges()) {
            if (edge.getU().equals(start)) {
                pq.add(edge);
            }
        }

        // add start vertex to visited
        visited.add(start);

        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            Edge<T> current = pq.remove();
            if (!visited.contains(current.getV())) {
                visited.add(current.getV());
                mst.add(current);
                for (Edge<T> edge : graph.getEdges()) {
                    if (edge.getU().equals(current.getV()) && !visited.contains(edge.getV())) {
                        pq.add(edge);
                    }
                }

            }
        }

        return mst;
    }
}