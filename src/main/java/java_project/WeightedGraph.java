 package java_project;

import java.util.*;

public class WeightedGraph extends Graph {
    public Map<String, Integer> weights;

    public WeightedGraph(ArrayList<Integer> vertices, ArrayList<ArrayList<Integer>> edges,HashMap weights) {
        super(vertices, edges);
        this.weights = weights;
    }

    public void addWeight(String edge, Integer weight) {
        this.weights.put(edge, weight);
    }

    public void removeWeight(String edge) {
        this.weights.remove(edge);
    }

    public int getWeight(int from, int to) {
        String edge = from + "-" + to;
        return weights.getOrDefault(edge, Integer.MAX_VALUE);
    }

    @Override
    public void breadthFirstSearch(int startVertex) {
        boolean[] visited = new boolean[vertices.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startVertex);
        visited[startVertex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(currentVertex + " ");

            ArrayList<Integer> neighbors = edges.get(currentVertex);
            for (int neighbor : neighbors) {
                if (!visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                }
            }
        }
    }

    @Override
    public void depthFirstSearch(int startVertex) {
        boolean[] visited = new boolean[vertices.size()];
        dfsHelper(startVertex, visited);
    }

    private void dfsHelper(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        ArrayList<Integer> neighbors = edges.get(vertex);
        for (int neighbor : neighbors) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public void dijkstraAlgorithm(int startVertex) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{startVertex, 0});

        int[] dist = new int[vertices.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startVertex] = 0;

        while (!pq.isEmpty()) {
            int[] pair = pq.poll();
            int vertex = pair[0];
            int weight = pair[1];

            if (weight > dist[vertex]) continue;

            ArrayList<Integer> neighbors = edges.get(vertex);
            for (int neighbor : neighbors) {
                int newDist = weight + getWeight(vertex, neighbor);
                if (newDist < dist[neighbor]) {
                    dist[neighbor] = newDist;
                    pq.offer(new int[]{neighbor, newDist});
                }
            }
        }

        for (int i = 0; i < vertices.size(); i++) {
            System.out.println("Shortest distance from vertex " + startVertex + " to vertex " + i + ": " + dist[i]);
        }
        
    }
     public static int[][] convertToMatrix(WeightedGraph G) {
        int size = G.vertices.size();
        int[][] T = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                T[i][j] = 0;
            }
        }
        for (ArrayList<Integer> edge : G.edges) {
            int v1 = edge.get(0);
            int v2 = edge.get(1);
            T[v1][v2] = 1;
            T[v2][v1] = 1;
        }
        return T;
    }
     public static WeightedGraph convertFromMatrix(int[][] matrix) {
        ArrayList<Integer> vertices = new ArrayList<>();
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        Map<String, Integer> weights =new HashMap<>();
        int n = matrix.length; // Number of vertices (size of the matrix)

        // Add vertices to the list
        for (int i = 0; i < n; i++) {
            vertices.add(i);
        }

        // Construct edges based on the adjacency matrix
        for (int i = 0; i < n; i++) {
            
            for (int j = 0; j < n; j++) {
                ArrayList<Integer> neighbors = new ArrayList<>();
                neighbors.add(i);
                if (matrix[i][j] == 1) {
                    neighbors.add(j);
                    String s = neighbors.get(0)+"-"+neighbors.get(1);
                    int r = neighbors.get(0)+neighbors.get(1);
                    weights.put(s,r) ;
                    edges.add(neighbors);
                     
    
                }
            }
            
        }
        WeightedGraph W = new WeightedGraph(vertices, edges , (HashMap) weights);
        
        
        // Create and return the UndirectedGraph object
        return W ;
    }
       
    
    
    
    
    
    
    
    
    
    
}
