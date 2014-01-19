import java.util.*;
import java.util.Arrays;

public class Johnson {
    public static ArrayList<Integer> getShortestPaths(Matrice graph) {
        if(graph.getNbColumns() == 2){
            return getShortestPathsDefault(graph);
        }
        else if(graph.getNbColumns() > 2){
            return getShortestPathsGeneralized(graph);
        }
        else{
            return null;
        }
    }
    
    private static ArrayList<Integer> getShortestPathsDefault(Matrice graph) {
        ArrayList<Integer> S = new ArrayList<Integer>();
        ArrayList<Integer> T = new ArrayList<Integer>();

        double minCol1 = graph.getValueAt(0, 0), 
                minCol2 = graph.getValueAt(0, 1);
        
        int indMinCol1 = 0,
                indMinCol2 = 0;

        for(int y=0;y<graph.getNbLines();y++){
            for(int i=0;i<graph.getNbLines();i++){
                if(!S.contains(i+1) && !T.contains(i+1)){
                    minCol1 = graph.getValueAt(i, 0);
                    minCol2 = graph.getValueAt(i, 1);
                    indMinCol1 = i;
                    indMinCol2 = i;
                    break;
                }
            }
            for(int i=0;i<graph.getNbLines();i++){
                if(graph.getValueAt(i, 0)<minCol1 && !S.contains(i+1) && !T.contains(i+1)){
                    minCol1 = graph.getValueAt(i, 0);
                    indMinCol1 = i;
                }
            }
            for(int i=0;i<graph.getNbLines();i++){
                if(graph.getValueAt(i, 1)<minCol2 && !S.contains(i+1) && !T.contains(i+1)){
                    minCol2 = graph.getValueAt(i, 1);
                    indMinCol2 = i;
                }
            }
            if(minCol1 < minCol2){
                S.add(indMinCol1+1);
                graph.setValueAt(indMinCol1, 0, Double.POSITIVE_INFINITY);
                graph.setValueAt(indMinCol1, 1, Double.POSITIVE_INFINITY);
            }
            else{
                T.add(indMinCol2+1);
                graph.setValueAt(indMinCol2, 0, Double.POSITIVE_INFINITY);
                graph.setValueAt(indMinCol2, 1, Double.POSITIVE_INFINITY);
            }
        }
        S.addAll(T);
        return S;
    }
    
    private static ArrayList<Integer> getShortestPathsGeneralized(Matrice graph) {
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
    
    public static void main (String[] args){
        double data[][] = {{5,7},{6,4},{9,8},{7,5},{8,10},{6,6},{4,3},{5,6}};
        Matrice test = new Matrice(data);
        System.out.println(Johnson.getShortestPaths(test));
                
        double data2[][] = {{2,6,2},{2,6,6},{8,2,6},{6,2,8},{6,6,2},{6,3,0}};
        Matrice test2 = new Matrice(data2);
        System.out.println(Johnson.getShortestPaths(test2));
    }
}