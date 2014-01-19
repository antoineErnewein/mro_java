

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
    
    public Simplexe(Matrice contraintes, Matrice X, Matrice A, Matrice b)
    {
        this.contraintes = contraintes;
        this.A = A;
        this.Abarre = new Matrice(A.getNbLines(), A.getNbColumns());
        this.b = b;
        this.X = X;
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
    
    //Check si les delta sont tous >=0
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
    
    //Renvoie la position du delta minimum
    private int getMinDeltaPos(Matrice delta)
    {
        double deltaMin = Double.MAX_VALUE;
        int pos = 0;
        
        for(int i = 0; i<delta.getNbColumns(); i++)
        {
            if(delta.getValueAt(0, i) < deltaMin)
            {
                deltaMin = delta.getValueAt(0, i);
                pos = i;
            }
        }
        
        return pos;
    }
    
    //Initialise B et N à partir de A
    private void initSimplexe(Matrice ciDansB, Matrice iDansB)
    {
        int colN = 0;
        int colB = 0;
        int nbN = 0;
        
        for(int i = 0; i<this.X.getNbColumns(); i++)
        {
            if(this.X.getValueAt(0, i) == 0)
            {
                nbN++;
            }
        }
        
        this.B = new Matrice(this.A.getNbLines(), this.A.getNbColumns() - nbN);
        this.N = new Matrice(this.A.getNbLines(), nbN);
        
        for(int i = 0; i<this.X.getNbColumns(); i++)
        {
            
            if(this.X.getValueAt(0, i) == 0)
            {
                copyColumn(i, colN, this.A, this.N);
                colN++;
            }
            
            else
            {
                copyColumn(i, colB, this.A, this.B);
                ciDansB.setValueAt(i, 0, this.contraintes.getValueAt(0, i));
                iDansB.setValueAt(i, 0, i);
                colB++;
            }
        }
        
        System.out.println("\n===> Init Simplexe :\n");
        System.out.println("i appartenant à B :\n");
        ciDansB.printMatrice();
        System.out.println("\nMatrice B :\n");
        this.B.printMatrice();
        System.out.println("\nMatrice N :\n");
        this.B.printMatrice();
        
        
    }
    
    //Permet de copier une colonne d'une matrice dans une autre
    private void copyColumn(int colSource, int colDest, Matrice source, Matrice dest)
    {
        for(int lignes = 0; lignes < source.getNbLines(); lignes++)
        {
           dest.setValueAt(lignes, colDest, source.getValueAt(lignes, colSource));
        }
    }
    
    //Renvoie une colonne de matrice identité selon le paramètre rang
    private Matrice getIdentite(int rang)
    {
        Matrice id = new Matrice(this.A.getNbLines(), 1);
        
        for(int lignes = 0; lignes < id.getNbLines(); lignes++)
        {
            if(lignes == rang)
            {
                id.setValueAt(lignes, 0, 1);
            }
            
            else
            {
                id.setValueAt(lignes, 0, 0);
            }  
        }
        
        return id;
    }
    
    //Renvoie un tableau avec [0] = teta et [1] = r
    private double[] getTeta(int s, Matrice bBarre)
    {
        double teta = Double.MAX_VALUE;
        double temp;
        double pos = 0;
        
        for(int i = 0; i<this.Abarre.getNbLines(); i++)
        {
            temp = bBarre.getValueAt(i, 0) / this.Abarre.getValueAt(i, s);
            if(temp < teta && temp >= 0)
            {
                teta = temp;
                pos = i;
            }
        }
        double res[] = {teta,pos};
        return res;
    }
    
    //Remplit A barre à partir de N barre et de la matrice contenant les i dans B
    private void setAbarre(Matrice iDansB, Matrice NBarre)
    {
        int Ncol = 0;
        boolean estDansB = false;
        
        for(int j = 0; j<iDansB.getNbLines(); j++)
        {
            copyColumn(0, (int)iDansB.getValueAt(j, 0), getIdentite(j), this.Abarre);
        }
        
        
        for(int i = 0; i<this.Abarre.getNbColumns(); i++)
        {
            for(int j = 0; j<iDansB.getNbLines(); j++)
            {
                if(i==(int)iDansB.getValueAt(j, 0))
                {
                    estDansB = true;
                }
            }
            
            if(!estDansB)
            {
                copyColumn(Ncol, i, NBarre, this.Abarre);
                Ncol++;
            }
            else
            {
                estDansB = false;
            }
        }
    }
    
    //Determine les nouveaux X à partir de r, s, teta et bBarre
    private void determineX(int r, int s, double teta, Matrice bBarre)
    {
        for(int i = 0; i<this.X.getNbColumns(); i++)
        {
            if(i == r)
            {
                this.X.setValueAt(0, i, 0);
            }
            
            else if(i == s)
            {
                this.X.setValueAt(0, i, teta);
            }
            
            else
            {
                if(i > this.Abarre.getNbLines()-1)
                {
                    this.X.setValueAt(0, i, 0);
                }
                
                else
                {
                    this.X.setValueAt(0, i, (bBarre.getValueAt(i, 0) - (this.Abarre.getValueAt(i, s)*teta)));
                }
            }
        }
    }
    
    //Calcule Z barre pour la première itération
    private double initZbarre(Matrice ciDansB, Matrice bBarre)
    {
        double zbarre = 0;
        
        for(int i = 0; i< ciDansB.getNbLines(); i++)
        {
            zbarre+= ciDansB.getValueAt(i, 0)*bBarre.getValueAt(i, 0);
        }
        
        return zbarre;
    }
    
    //Met les valeurs de i et ci dans B à jour à partir de r et s
    private void updateCiDansB(Matrice ciDansB, Matrice iDansB, int entrant, int sortant)
    {
        for(int i = 0; i<iDansB.getNbLines(); i++)
        {
            if(iDansB.getValueAt(i, 0) == sortant)
            {
                iDansB.setValueAt(i, 0, entrant);
                ciDansB.setValueAt(i, 0, this.contraintes.getValueAt(0, entrant));
            }
        }
    }
    
    //Met B et N à jour à partir de A barre
    private void updateBN(Matrice iDansB)
    {
        int colB = 0;
        int colN = 0;
        boolean estDansB = false;
        
        for(int j = 0; j<iDansB.getNbLines(); j++)
        {
            copyColumn((int)iDansB.getValueAt(j, 0), colB, this.Abarre, this.B);
            colB++;
        }
        
        
        for(int i = 0; i<this.Abarre.getNbColumns(); i++)
        {
            for(int j = 0; j<iDansB.getNbLines(); j++)
            {
                if(i==(int)iDansB.getValueAt(j, 0))
                {
                    estDansB = true;
                }
            }
            
            if(!estDansB)
            {
                copyColumn(i, colN, this.Abarre, this.N);
                colN++;
            }
            else
            {
                estDansB = false;
            }
            
        }
    }
    
    //Déroule le simplexe !
    public void runSimplexe() throws MatrixException
    {
        Matrice Nbarre;
        Matrice bBarre;
        Matrice delta;
        Matrice ciDansB = new Matrice(this.A.getNbLines(), 1);
        Matrice iDansB = new Matrice(this.A.getNbLines(), 1);
        Matrice temp = new Matrice(this.A.getNbLines(), 1);
        boolean nonOptimale = true;
        int s;
        int r;
        double teta;
        double zBarre = 0;
        int etape = 0;
        System.out.println("\n=====>SIMPLEXE<=====");
        initSimplexe(ciDansB, iDansB);
        
        while(nonOptimale)
        {
            System.out.println("\n===> Etape : "+etape);
            Nbarre = this.getNbarre();
            bBarre = this.getBbarre();
            
            setAbarre(iDansB, Nbarre);
            System.out.println("\nA barre :");
            this.Abarre.printMatrice();
            
            delta = getDelta(ciDansB);
            System.out.println("\nDelta :");
            delta.printMatrice();
            
            if(etape == 0)
                {
                    zBarre = initZbarre(ciDansB, bBarre);
                }

            System.out.println("Zbarre : "+zBarre+"\n");
            
            if(checkOptimal(delta))
            {
                nonOptimale = false;
                System.out.println("Solution optimale, tous les deltas sont >= 0 !");
                System.out.println("Pour X valant :");
                this.X.printMatrice();
            }
            
            //AJOUTER SI TOUS LES DELTAS <0
            
            else
            {
                System.out.println("Solution non optimale, tous les deltas ne sont pas >= 0 !\n");
                s = getMinDeltaPos(delta);
                r = (int)getTeta(s, bBarre)[1];
                teta = getTeta(s, bBarre)[0];
                System.out.println("s = "+s+"; r = "+r+"; teta = "+teta+"\n");

                determineX(r, s, teta, bBarre);
                System.out.println("Nouveaux X :\n");
                this.X.printMatrice();
                
                zBarre += delta.getValueAt(0, s)*teta;
                
                //On met r à la place de s
                copyColumn(r, 0, this.Abarre, temp);
                copyColumn(s, r, this.Abarre, this.Abarre);
                copyColumn(r, 0, temp, this.Abarre);
                updateCiDansB(ciDansB, iDansB, s, r);
               
                //Mettre B et N à jour par rapport à A barre
                updateBN(iDansB);
                this.b.setData(bBarre.getData());
            }
            
            etape++;
        }
        
    }
    
    
}
