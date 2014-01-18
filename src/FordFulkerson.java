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
}


