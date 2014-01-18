import java.lang.Math;
import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson{


    private int[] parent;
    private Queue<Integer> queue;
    private int numberOfSommet;
    private boolean[] visited;

    public FordFulkerson(int numberOfSommet){
        this.numberOfSommet = numberOfSommet;
        parent = new int[numberOfSommet + 1];
        visited = new boolean[numberOfSommet+ 1];
        this.queue = new LinkedList<Integer>();
    }

    //Méthode d'exploration de graph en profondeur
    public boolean exploredepth(int source, int goal, int matrix[][]){
      boolean pathFound = false;
      int destination, element;

    for(int vertex = 1; vertex <= numberOfSommet; vertex++){
      parent[vertex] = -1;
      visited[vertex] = false;
    }

    queue.add(source);
    parent[source] = -1;
    visited[source] = true;

    while(!queue.isEmpty()){
      element = queue.remove();
      destination = 1;
      while (destination <= numberOfSommet)
            {
                if (matrix[element][destination] > 0 &&  !visited[destination])
                {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
    }
    if(visited[goal])
        {
            pathFound = true;
        }
        return pathFound;
  }
  //Calcul du flot maximal dans un graphe donné
  public int findMaxFlow(int matrix[][], int source, int destination){

    int a,d;
    int flow = 0;
    int path;
    int[][] resteMatrix = new int[numberOfSommet+1][numberOfSommet+1];

    for(int initSommet = 1; initSommet<=numberOfSommet; initSommet++){
      for(int finalSommet = 1; finalSommet<=numberOfSommet; finalSommet++){
        resteMatrix[initSommet][finalSommet] = matrix[initSommet][finalSommet];
      }
    }

    while(exploredepth(source, destination, resteMatrix)){
      path = Integer.MAX_VALUE;  // infinie pour le chemin de base

      for(d = destination; d!=source; d=parent[d]){
        a = parent[d];
        path = Math.min(path, resteMatrix[a][d]);
      }
      for(d = destination; d!=source; d=parent[d]){
        a = parent[d];
        resteMatrix[a][d] -= path;
        resteMatrix[d][a] += path;
      }
      flow += path;
    }

    return flow;
  }
}


