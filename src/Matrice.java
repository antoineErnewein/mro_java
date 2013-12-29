import java.lang.Math;

public class Matrice {
	private int M;
	private int N;
	private int[][] data;
	
	public Matrice(int M, int N){
		this.M = M;
		this.N = N;
		data = new int[M][N];
	}
	
	public void Matrix(int[][] data){
		M = data.length;
		N = data.length;
		this.data = new int[M][N];
		for (int i = 0; i<M; i++){
			for(int j = 0; j < N; j++){
				this.data[i][j] = data[i][j];
			}
		}
	}
	
	public static Matrice random(int M, int N) {
        Matrice A = new Matrice(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = (int) Math.random();
        return A;
    }

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}

}

