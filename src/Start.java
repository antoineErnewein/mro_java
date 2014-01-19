
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Antoine
 */
public class Start 
{

    public static void main (String[] args)
   {
       branchBound();
       
        try {
            simplexe();
        } catch (MatrixException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    
    private static void simplexe() throws MatrixException 
    {
        double dataA[][] = {{1,0,0,2,3},{0,2,0,4,0},{2,1,2,0,2}};
        double datab[][] = {{6},{8},{18}};
        double dataX[][] = {{6,4,1,0,0}};
        double contraintes[][] = {{3,1,4,2,-2}};
        
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
        }
        catch(Exception e) { System.out.println(e);}
    }
    
}
