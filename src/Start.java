
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Antoine
 */
public class Start {

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while(true) {
            System.out.println("** Menu **");
            System.out.println("1- Programation Dynamique");
            System.out.println("2- Floyd-Warshall");
            System.out.println("3- Méthode des potentiels");
            System.out.println("4- Johnson");
            System.out.println("5- Ford-Fulkerson");
            System.out.println("6- Branch and Bound");
            System.out.println("7- Simplexe");
            System.out.println("\n0- Sortie");
            System.out.print("\nChoix : ");
            choice = Integer.parseInt(sc.nextLine());
            System.out.println("\n");
            
            switch (choice) {

                case 5:
                  Scanner scanner = new Scanner(System.in);
                  int[][] matrix;
                  int nbNoeuds;
                  int source;
                  int puits;
                   
                  System.out.println("Entrez le nombre de noeuds du graphe: ");
                  nbNoeuds = scanner.nextInt();
                  matrix = new int[nbNoeuds + 1][nbNoeuds + 1];
 
                 System.out.println("Entrez la matrice représentative du graphe:");
                 for(int i = 1; i <= nbNoeuds; i++){
                    for(int j = 1; j <= nbNoeuds; j++){
                        matrix[i][j] = scanner.nextInt();
                    }
                }
                System.out.println("Entrez le puit du graphe:");
                puits = scanner.nextInt();
                System.out.println("Entrez la source du graphe:");
                source= scanner.nextInt();

                int maxFlow;

                FordFulkerson fordFulkerson = new FordFulkerson(nbNoeuds);
                maxFlow = fordFulkerson.findMaxFlow(matrix, source, puits);
                System.out.println("Le flot maximal du graphe est: " + maxFlow);
                    break;
                case 4:
                    Johnson.main(new String[0]);
                    break;
                case 6:
                    branchBound();
                    break;
                case 7:
                    try {
                        simplexe();
                    } catch (MatrixException ex) {
                        Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    System.exit(0);
            }
            
            System.out.println("Tapez sur une touche pour continuer ...");
            sc.nextLine();
        }

    }

    private static void simplexe() throws MatrixException {
        
        //Exercice préparation exam
        double dataA1[][] = {{1, 0, 0, 2, 3}, {0, 2, 0, 4, 0}, {2, 1, 2, 0, 2}};
        double datab1[][] = {{6}, {8}, {18}};
        double dataX1[][] = {{6, 4, 1, 0, 0}};
        double contraintes1[][] = {{3, 1, 4, 2, -2}};
        
        //Exemple 12 page 47
        double dataA2[][] = {{1, 0, 0, 2, 3}, {0, 2, 0, 4, 0}, {2, 1, 2, 0, 2}};
        double datab2[][] = {{2}, {6}, {9}};
        double dataX2[][] = {{2, 3, 1, 0, 0}};
        double contraintes2[][] = {{3, 1, 4, 2, -2}};
        
        //EXAM 2008-2009
        double dataA3[][] = {{1,0,0,2,3},{0,2,0,4,0},{2,1,2,0,2}};
        double datab3[][] = {{6},{6},{27}};
        double dataX3[][] = {{6,3,6,0,0}};
        double contraintes3[][] = {{3,1,4,4,-1}};
        
        //EXAM 2013-2014
        double dataA4[][] = {{1,0,0,2,3},{0,2,0,4,0},{2,1,2,0,2}};
        double datab4[][] = {{6},{6},{20}};
        double dataX4[][] = {{6,3,2.5,0,0}};
        double contraintes4[][] = {{3,1,4,4,-1}};
        
        Matrice A = null;
        Matrice b = null;
        Matrice X = null;
        Matrice c = null;
        
        Scanner sc = new Scanner(System.in);
        int choice;
        
        System.out.println("Exemples d'execution du simplexe :");
        System.out.println("1- Exercice préparation exam");
        System.out.println("2- Exemple 12 page 47");
        System.out.println("3- Exam 2008-2009");
        System.out.println("4- Exam 2013-2014");
        System.out.print("\nChoix : ");
        choice = Integer.parseInt(sc.nextLine());
        System.out.println("\n");
        
        
        switch(choice)
        {
            case 1 :
                 A = new Matrice(dataA1);
                 b = new Matrice(datab1);
                 X = new Matrice(dataX1);
                 c = new Matrice(contraintes1);
                break;
            
            case 2 :
                 A = new Matrice(dataA2);
                 b = new Matrice(datab2);
                 X = new Matrice(dataX2);
                 c = new Matrice(contraintes2);
                break;
                
           case 3 :
                 A = new Matrice(dataA3);
                 b = new Matrice(datab3);
                 X = new Matrice(dataX3);
                 c = new Matrice(contraintes3);
                break;
               
           case 4 :
                 A = new Matrice(dataA4);
                 b = new Matrice(datab4);
                 X = new Matrice(dataX4);
                 c = new Matrice(contraintes4);
                break;
           
           default:
                System.exit(0);    
        }
 
        Simplexe simp = new Simplexe(c, X, A, b);
        simp.runSimplexe();
    }

    private static void branchBound() {
        try {
            String filename = "";

            Scanner sc = new Scanner(System.in);

            System.out.println("** Branch and Bound **");
            System.out.println("Nom du fichier : ");
            filename = sc.nextLine();

            String arg[] = {filename};
            BranchBound.main(arg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
