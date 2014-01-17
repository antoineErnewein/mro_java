
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
       double data[][] = {{1,0,0},{0,2,0},{2,1,2}};
       Matrice test = new Matrice(data);
       
       System.out.println("Matrice A : \n");
       test.printMatrice();
       
       //Test determinant + comatrice + inverse
        try {
                System.out.println("\nDeterminant matrice A : " + test.getDeterminant()+"\n");
                Matrice com = Matrice.getComatrice(test);
                System.out.println("Comatrice A : \n");
                com.printMatrice();
                Matrice inv = Matrice.getInverse(test);
                System.out.println("\nInverse A : \n");
                inv.printMatrice();
                Matrice verif = Matrice.multiply(test, inv);
                System.out.println("\nVerification A*A-1 : \n");
                verif.printMatrice();
                
            }
        
        catch (MatrixException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        
   }
    
}
