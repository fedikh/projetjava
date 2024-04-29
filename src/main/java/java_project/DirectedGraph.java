package java_project;

import java.util.*;

public class DirectedGraph extends Graph {
    public ArrayList<ArrayList<Integer>> directedEdges;

    public DirectedGraph(ArrayList<Integer> vertices, ArrayList<ArrayList<Integer>> edges) {
        super(vertices, edges);
        this.directedEdges = new ArrayList<>();
    }

    public void addDirectedEdge(ArrayList<Integer> directedEdge) {
        this.directedEdges.add(directedEdge);
    }

    public void removeDirectedEdge(ArrayList<Integer> directedEdge) {
        this.directedEdges.remove(directedEdge);
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
            for (int i = 0; i < neighbors.size(); i++) {
                int neighbor = neighbors.get(i);
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
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
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

            for (int i = 0; i < edges.get(vertex).size(); i++) {
                int neighbor = (int) edges.get(vertex).get(i);
                int newDist = weight + 1; // Assuming unit weights for directed edges
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
    
  public ArrayList<ArrayList<Integer>> convertToMatrix() {
        ArrayList<ArrayList<Integer>> adjacencyMatrix = new ArrayList<>();
        int size = vertices.size();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            adjacencyMatrix.add(row);
        }

    // Fill in the adjacency matrix based on the directed edges
        for (ArrayList<Integer> edge : directedEdges) {
            int from = edge.get(0);
            int to = edge.get(1);
            adjacencyMatrix.get(from).set(to, 1);
        }

        return adjacencyMatrix;
    }
    
     public static DirectedGraph convertFromMatrix(int[][] matrix) {
        ArrayList<Integer> vertices = new ArrayList<>();
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        ArrayList<ArrayList<Integer>> directedEdges= new ArrayList<>();
                int n = matrix.length; // Number of vertices (size of the matrix)
        for (int i = 0; i < n; i++) {
            vertices.add(i);
        }

        // Construct edges based on the adjacency matrix
        for (int i = 0; i < n; i++) {
           
            for (int j = 0; j < n; j++) {
                ArrayList<Integer> connect = new ArrayList<>();
                connect.add(i);
                if (matrix[i][j] == 1) {
                    connect.add(j);
                    edges.add(connect);
                    directedEdges.add(connect);
                }
            }   
        }
        DirectedGraph D = new DirectedGraph(vertices, edges);
        D.directedEdges= directedEdges;
        return D;
        
    }  
}
