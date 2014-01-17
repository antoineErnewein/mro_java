
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
       
       //Test determinant
        try {
                System.out.println("Determinant matrice : " + test.getDeterminant());
            }
        
        catch (MatrixException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
    
}
