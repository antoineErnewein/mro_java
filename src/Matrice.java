import java.lang.Math;
import java.util.LinkedList;
import java.util.Queue;

public class Matrice {
	private int L;
	private int[][] data;
	
	public Matrice(int L){
		this.L = L;
		data = new int[L][L];
	}
	
	public void Matrix(int[][] data){
		L = data.length;
		this.data = new int[L][L];
		for (int i = 0; i<L; i++){
			for(int j = 0; j < L; j++){
				this.data[i][j] = data[i][j];
			}
		}
	}
	
	public static Matrice random(int L) {
        Matrice A = new Matrice(L);
        for (int i = 0; i < L; i++)
            for (int j = 0; j < L; j++)
                A.data[i][j] = (int) Math.random();
        return A;
    }

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public Matrice getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	/* Calcule le chemin le plus court entre deux points grâce à la méthode de Floyd Warshall */
	public Matrice floydWarshall(){
		Matrice A = new Matrice(this.getL());
		for(int k=0; k < this.getL(); k++){
		  for(int i=0; i< this.getL(); i++){
			for(int j=0; j< this.getL(); j++){
			  A[i][j]=Math.min(this.getData()[i][j],this.getData()[i][k]+this.getData()[k][j]);
			}
		  }
		}
		return A;
	  }

  public boolean bfs(int source, int goal){
    boolean pathFound = false;
    int numberOfVertices = this.getL();
    int destination, element;
    Queue<Integer> queue;
    int[] parent = new int[numberOfVertices + 1];
    boolean[] visited = new boolean[numberOfVertices + 1];
    
    for(int vertex = 1; vertex <= numberOfVertices; vertex++){
      parent[vertex] = -1;
      visited[vertex] = false;
    }

    queue.add(source);
    parent[source] = -1;
    visited[source] = true;

    while(!queue.isEmpty()){
    
      element = queue.remove();
      destination = 1;
      while (destination <= numberOfVertices)
            {
                if (this.getData()[element][destination] > 0 &&  !visited[destination])
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

