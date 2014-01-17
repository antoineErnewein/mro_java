import java.lang.Math;
import java.util.LinkedList;
import java.util.Queue;

public class Matrice {
	private int lines;
        private int columns;
	private double[][] data;
	
	public Matrice(int lines, int columns)
        {
		this.lines = lines;
                this.columns = columns;
		data = new double[lines][columns];
	}
	
	public Matrice(double[][] data)
        {
                this.lines = data.length;
                this.columns = data[0].length;
		this.data = data;
	}
	
	public static Matrice random(int lines, int columns) 
        {
            Matrice A = new Matrice(lines, columns);

            for (int i = 0; i < lines; i++)
            {
                for (int j = 0; j < columns; j++)
                    A.data[i][j] = (int) Math.random();
            }

            return A;
        }

	public int getNbLines() {
		return this.lines;
	}

	public int getNbColumns() {
		return this.columns;
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}
        
        public double getValueAt(int i, int j)
        {
            return this.data[i][j];
        }
        
        public void setValueAt(int i, int j, double value)
        {
            this.data[i][j] = value;
        }
        
        public double changeSign(double value)
        {
            if(value%2 == 0)
            {
                return 1;
            }
            
            else
            {
                return -1;
            }
        }
        
        public static Matrice createSubMatrix(Matrice matrix, int excluding_row, int excluding_col) 
        {
            Matrice mat = new Matrice(matrix.getNbLines()-1, matrix.getNbColumns()-1);
            int r = -1;
            
            for (int i=0;i<matrix.getNbLines();i++) 
            {
                if (i==excluding_row)
                    continue;
                    r++;
                    int c = -1;
                for (int j=0;j<matrix.getNbColumns();j++) {
                    if (j==excluding_col)
                        continue;
                    mat.setValueAt(r, ++c, matrix.getValueAt(i, j));
                }
            }
            return mat;
        } 
        
        public double getDeterminant() throws MatrixException
        {
            Matrice temp = null;
            
            if(this.lines != this.columns)
            {
                throw new MatrixException("La matrice n'est pas carrée !");
            }
            
            if (this.lines == 2) 
            {
                return (this.getValueAt(0, 0) * this.getValueAt(1, 1)) - ( this.getValueAt(0, 1) * this.getValueAt(1, 0));
            }
    
            double sum = 0.0;
    
            for (int i=0; i<this.lines; i++) 
            {
                temp = createSubMatrix(this, 0, i);
                sum += changeSign(i) * this.getValueAt(0, i) * temp.getDeterminant();
            }
    
            return sum;
        }

	/* Calcule le chemin le plus court entre deux points grâce à la méthode de Floyd Warshall */
	public Matrice floydWarshall(){
		Matrice A = new Matrice(this.lines,this.columns);
		for(int k=0; k < this.lines; k++){
		  for(int i=0; i< this.lines; i++){
			for(int j=0; j< this.columns; j++){
			  A.getData()[i][j]=Math.min(this.getData()[i][j],(this.getData()[i][k]+this.getData()[k][j]));
			}
		  }
		}
		return A;
	  }

  public boolean bfs(int source, int goal){
    boolean pathFound = false;
    int numberOfVertices = this.lines;
    int destination, element;
    Queue<Integer> queue = null;
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

