package java_project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GraphManaging implements IGraphManaging {
    private Connection connection;

    public GraphManaging(Connection connection) {
        this.connection = connection;
    }

   @Override
   public void createGraph(String name) {
        String sql = "CREATE TABLE IF NOT EXISTS " + name + " (vertex INT PRIMARY KEY, connected_vertices VARCHAR(255))";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("Table '" + name + "' created successfully");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    @Override
    public void updateGraph(String name, Graph graph) {
        try {
            clearGraphData(name);

            List<Integer> vertices = graph.vertices;
            for (int vertex : vertices) {
                addVertex(name, vertex);
            }

            ArrayList<ArrayList> edges = graph.edges;
            for (List<Integer> edge : edges) {
                addEdge(name, edge.get(0), edge.get(1));
            }
            System.out.println("Graph '" + name + "' updated successfully");
        } catch (SQLException e) {
            System.err.println("Error updating graph: " + e.getMessage());
        }
    }

    private void addVertex(String tableName, int vertex) throws SQLException {
        // Initialize the connected vertices string as empty initially
        String sql = "INSERT INTO " + tableName + "(vertex, connected_vertices) VALUES (?, '')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vertex);
            pstmt.executeUpdate();
        }
    }

    private void addEdge(String tableName, int u, int v) throws SQLException {
    // Get the existing connected vertices for vertex u
    String existingConnectedVerticesU = getConnectedVertices(tableName, u);
    // Get the existing connected vertices for vertex v
    String existingConnectedVerticesV = getConnectedVertices(tableName, v);

    // Append the new vertex v to the existing connected vertices of u
    String updatedConnectedVerticesU = existingConnectedVerticesU.isEmpty() ? String.valueOf(v) : existingConnectedVerticesU + "," + v;
    // Append the new vertex u to the existing connected vertices of v
    String updatedConnectedVerticesV = existingConnectedVerticesV.isEmpty() ? String.valueOf(u) : existingConnectedVerticesV + "," + u;

    // Update the connected vertices string for vertex u
    String sqlU = "UPDATE " + tableName + " SET connected_vertices = ? WHERE vertex = ?";
    try (PreparedStatement pstmtU = connection.prepareStatement(sqlU)) {
        pstmtU.setString(1, updatedConnectedVerticesU);
        pstmtU.setInt(2, u);
        pstmtU.executeUpdate();
    }

    // Update the connected vertices string for vertex v
    String sqlV = "UPDATE " + tableName + " SET connected_vertices = ? WHERE vertex = ?";
    try (PreparedStatement pstmtV = connection.prepareStatement(sqlV)) {
        pstmtV.setString(1, updatedConnectedVerticesV);
        pstmtV.setInt(2, v);
        pstmtV.executeUpdate();
    }
}


    private String getConnectedVertices(String tableName, int vertex) throws SQLException {
        String sql = "SELECT connected_vertices FROM " + tableName + " WHERE vertex = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vertex);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("connected_vertices");
                }
            }
        }
        return "";
    }

    private void clearGraphData(String tableName) throws SQLException {
        String sql = "DELETE FROM " + tableName;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteGraph(String name) {
        String sql = "DROP TABLE IF EXISTS " + name;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("Table '" + name + "' deleted successfully");
        } catch (SQLException e) {
            System.err.println("Error deleting table: " + e.getMessage());
        }
    }
}
