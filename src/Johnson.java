import java.util.*;
import java.util.Arrays;

public class Johnson {
    public static ArrayList<Integer> getShortestPaths(Matrice graph) {
        double[][] k = new double[graph.getNbLines()][2];
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for(int i=0; i<graph.getNbLines(); i++){
            double x = 0, 
                   y = 0;
            for(int z=0;z<graph.getNbColumns()-1; z++){
                x += graph.getValueAt(i, z);
            }
            for(int z=graph.getNbColumns()-1;z>0; z--){
                y += graph.getValueAt(i, z);
            }
            k[i][0] = i+1;
            k[i][1] = x/y;
        }
        Arrays.sort(k, new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                return Double.compare(o2[1], o1[1]);
            }
        });
        
        for(int i=(k.length-1);i>=0;i--){
            result.add((int) k[i][0]);
        }

        return result;
    }
}