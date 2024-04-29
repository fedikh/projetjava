package java_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/graph", "postgres", "fedikh09")) {
            // Création d'une instance de GraphManaging
            GraphManaging graphManager = new GraphManaging(connection);

            // Création d'un premier graph vide
            graphManager.createGraph("emptyGraph");

            // Création d'un deuxième graph avec des valeurs
            ArrayList<Integer> verticesWithValues = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
            ArrayList<ArrayList<Integer>> edgesWithValues = new ArrayList<>();
            edgesWithValues.add(new ArrayList<>(Arrays.asList(1, 2)));
            edgesWithValues.add(new ArrayList<>(Arrays.asList(2, 3)));
            edgesWithValues.add(new ArrayList<>(Arrays.asList(2, 4)));
            edgesWithValues.add(new ArrayList<>(Arrays.asList(3, 5)));
            Graph graphWithValues = new UndirectedGraph(verticesWithValues, edgesWithValues);
            graphManager.createGraph("graphWithValues");

            // Mise à jour du deuxième graph avec des valeurs
            graphManager.updateGraph("graphWithValues", graphWithValues);

            // Création d'un troisième graph avec des valeurs différentes
            ArrayList<Integer> otherVertices = new ArrayList<>(Arrays.asList(10, 20, 30));
            ArrayList<ArrayList<Integer>> otherEdges = new ArrayList<>();
            otherEdges.add(new ArrayList<>(Arrays.asList(10, 20)));
            otherEdges.add(new ArrayList<>(Arrays.asList(20, 30)));
            Graph otherGraph = new UndirectedGraph(otherVertices, otherEdges);

            // Mise à jour du troisième graph avec des valeurs
            graphManager.updateGraph("otherGraph", otherGraph);

            // Suppression du troisième graph
            graphManager.deleteGraph("otherGraph");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}