
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
        double dataA[][] = {{1, 0, 0, 2, 3}, {0, 2, 0, 4, 0}, {2, 1, 2, 0, 2}};
        double datab[][] = {{6}, {8}, {18}};
        double dataX[][] = {{6, 4, 1, 0, 0}};
        double contraintes[][] = {{3, 1, 4, 2, -2}};

        //EXAM 2008-2009
        /*double dataA[][] = {{1,0,0,2,3},{0,2,0,4,0},{2,1,2,0,2}};
         double datab[][] = {{6},{6},{27}};
         double dataX[][] = {{6,3,6,0,0}};
         double contraintes[][] = {{3,1,4,4,-1}};*/
        Matrice A = new Matrice(dataA);
        Matrice b = new Matrice(datab);
        Matrice X = new Matrice(dataX);
        Matrice c = new Matrice(contraintes);

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
