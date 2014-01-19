public class FloydWarshall {
    public static Matrice getShortestPaths(Matrice mat){
        Matrice A = new Matrice(mat.getNbLines(),mat.getNbColumns());
        for(int k=0; k < mat.getNbLines(); k++){
            for(int i=0; i< mat.getNbLines(); i++){
                for(int j=0; j< mat.getNbColumns(); j++){
                  A.getData()[i][j]=Math.min(mat.getData()[i][j],(mat.getData()[i][k]+mat.getData()[k][j]));
                }
            }
        }
        return A;
    }
}
