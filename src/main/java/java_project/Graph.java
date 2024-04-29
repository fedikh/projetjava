package java_project;

import java.util.ArrayList;

abstract class Graph {
    public ArrayList<Integer> vertices ;
    public ArrayList<ArrayList> edges ;
    Graph(ArrayList vertices , ArrayList edges){
        this.vertices = vertices ;
        this.edges = edges ;       
    }
    
    public void addVertice(Integer vertice){
        this.vertices.add(vertice);
    }
    public void addEdge(ArrayList edge){
        this.edges.add(edge);
    }
    public void removeVertice(Integer vertice){
        this.vertices.remove(vertice);
    }
    public void removeEdge(ArrayList edge){
        this.edges.remove(edge);
    }
    public void showEdges(){
        if(!edges.isEmpty()){
            System.out.println("this is all of the edges : "+edges);
        }
        else {
            System.out.println("your graph is empty !");
        }
    }
    public abstract void breadthFirstSearch(int startVertice) ;
    public abstract void depthFirstSearch(int startVertice) ;
    
    
    
    
    
    
    
    
    
}
