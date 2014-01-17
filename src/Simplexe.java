

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Antoine
 */
public class Simplexe 
{
    private Matrice contraintes;
    private Matrice A;
    private Matrice Abarre;
    private Matrice b;
    private Matrice B;
    private Matrice N;
    private Matrice X;
    
    public Simplexe(Matrice contraintes, Matrice A, Matrice b)
    {
        this.contraintes = contraintes;
        this.A = A;
        this.b = b;
    }
    
    //Calcule b barre avec B-1*b
    private Matrice getBbarre() throws MatrixException
    {
        return Matrice.multiply(Matrice.getInverse(this.B), this.b);
    }
    
    //Calcule N barre avec B-1*N
    private Matrice getNbarre() throws MatrixException
    {
        return Matrice.multiply(Matrice.getInverse(this.B), this.N);
    }
    
    //Renvoie la matrice delta pour les valeurs indiquees
    private Matrice getDelta(Matrice ciDansB)
    {
        Matrice delta = new Matrice(1, this.A.getNbColumns());
        
        for(int i = 0; i<delta.getNbColumns(); i++)
        {
            double sum = 0.0;
            for(int j = 0; j<ciDansB.getNbLines(); j++)
            {
                sum += this.Abarre.getValueAt(j, i)*ciDansB.getValueAt(j, 0);
            }
            
           delta.setValueAt(0, i, this.contraintes.getValueAt(0, i) - sum);
        }
        
        return delta;
    }
    
    //Renvoie vrai si tous les deltas sont >=0
    private boolean checkOptimal(Matrice delta)
    {
        boolean res = true;
        
        for(int i = 0; i<delta.getNbColumns(); i++)
        {
            if(delta.getValueAt(0, i) < 0)
                res = false;
        }
        
        return res;
    }
    
    
    private void determineBase()
    {
        //Faire quelquechose...
    }
    
    public void runSimplexe() throws MatrixException
    {
        Matrice Nbarre = null;
        Matrice bBarre = null;
        Matrice delta = null;
        Matrice ciDansB = null;
        boolean nonOptimale = true;
        
        this.determineBase();
        
        while(nonOptimale)
        {
            Nbarre = this.getNbarre();
            bBarre = this.getBbarre();
            delta = getDelta(ciDansB);
            
            if(checkOptimal(delta))
            {
                nonOptimale = false;
            }
            
            else
            {
                //...
            }
            
        }
    }
    
}
