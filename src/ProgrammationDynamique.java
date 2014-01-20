
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author etien_000
 */
public class ProgrammationDynamique {

    private Graphe graphe;
    private boolean findMax;

    public ProgrammationDynamique(Graphe graphe) {
        this.graphe = graphe;
        this.findMax = false;
    }

    public ProgrammationDynamique(Graphe graphe, boolean findMax) {
        this.graphe = graphe;
        this.findMax = findMax;
    }

    public List<String> runProgrammationDynamique() {
        Matrice aretes = this.graphe.getAretes();
        List<String> sommets = this.graphe.getSommets();
        
        List<List<String>> cheminsCourants = new ArrayList();
        List<Double> poidsCourants = new ArrayList();
        
        for (int i = 1; i < aretes.getNbColumns(); i++) {
            double poids = aretes.getValueAt(0, i);
            
            if (poids != -1) {
                ArrayList<String> premierChemin = new ArrayList();
                premierChemin.add(sommets.get(0));
                premierChemin.add(sommets.get(i));
                
                cheminsCourants.add(premierChemin);
                poidsCourants.add(poids);
                
                System.out.print("f(" + sommets.get(i) + ") = " + poids + " : ");
                for (String sommet : premierChemin) {
                    System.out.print(sommet + " ");
                }
                System.out.println();
            }
        }
        
        System.out.println();
        
        List<String> dernierCheminCourant = cheminsCourants.get(cheminsCourants.size() - 1);
        while (!dernierCheminCourant.get(dernierCheminCourant.size() - 1).equals(sommets.get(sommets.size() - 1))) {
            List<List<String>> nouveauxCheminsCourants = new ArrayList();
            List<Double> nouveauxPoidsCourants = new ArrayList();
            
            for (int i = 0; i < cheminsCourants.size(); i++) {
                List<String> cheminCourant = cheminsCourants.get(i);
                double poidsCheminCourant = poidsCourants.get(i);
                int placeInGraphe = sommets.indexOf(cheminCourant.get(cheminCourant.size() - 1));

                for (int j = placeInGraphe + 1; j < aretes.getNbColumns(); j++) {
                    double poids = aretes.getValueAt(placeInGraphe, j);
                    
                    if (poids != -1) {
                        double nouveauPoids = poids + poidsCheminCourant;
                        List<String> nouveauChemin = new ArrayList(cheminCourant);
                        nouveauChemin.add(sommets.get(j));
                        
                        int indexAncienChemin = -1;
                        for (int k = 0; k < nouveauxCheminsCourants.size(); k++) {
                            List<String> nouveauCheminCourant = nouveauxCheminsCourants.get(k);
                            if (nouveauCheminCourant.get(nouveauCheminCourant.size() - 1).equals(sommets.get(j))) {
                                indexAncienChemin = k;
                            }
                        }
                        
                        if (indexAncienChemin == -1) {
                            nouveauxPoidsCourants.add(nouveauPoids);
                            nouveauxCheminsCourants.add(nouveauChemin);
                        }
                        else {                                                        
                            if ((this.findMax && nouveauPoids > nouveauxPoidsCourants.get(indexAncienChemin)) || (!this.findMax && nouveauPoids < nouveauxPoidsCourants.get(indexAncienChemin))) {
                                    nouveauxCheminsCourants.remove(indexAncienChemin);
                                    nouveauxCheminsCourants.remove(indexAncienChemin);

                                    nouveauxPoidsCourants.add(nouveauPoids);
                                    nouveauxCheminsCourants.add(nouveauChemin);
                            }
                        }
                    }
                }
            }

            cheminsCourants = nouveauxCheminsCourants;
            poidsCourants = nouveauxPoidsCourants;

            for (List<String> cheminCourant : cheminsCourants) {
                System.out.print("f(" + cheminCourant.get(cheminCourant.size() - 1) + ") = " + poidsCourants.get(cheminsCourants.indexOf(cheminCourant)) + " : ");
                for (String sommet : cheminCourant) {
                    System.out.print(sommet + " ");
                }
                System.out.println();
            }
            System.out.println();
            
            dernierCheminCourant = cheminsCourants.get(cheminsCourants.size() - 1);
        }
      
        return cheminsCourants.get(cheminsCourants.size() - 1);
    }
    
    public static void main (String[] args) {
        System.out.println("Exemples d'exécution de l'algorithme de programmation dynamique :");
        System.out.println("1- Exercice 1 du TD 1");
        System.out.println("2- Exercice 2 du TD 2");
        System.out.print("\nChoix : ");
        
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.println("\n");
        
        switch(choice) {
            case 1 :
                double data[][] = {
                    { -1,  0,  5,  6, -1, -1, -1, -1, -1, -1, -1, -1 },
                    {  0, -1, -1, -1,  7,  8, 12, -1, -1, -1, -1, -1 },
                    {  5, -1, -1, -1, -1,  4,  8,  9, -1, -1, -1, -1 },
                    {  6, -1, -1, -1, -1, -1,  5,  7, -1, -1, -1, -1 },
                    { -1,  7, -1, -1, -1, -1, -1, -1,  2, -1, -1, -1 },
                    { -1,  8,  4,  5, -1, -1, -1, -1,  6,  3, -1, -1 },
                    { -1, 12,  8,  7, -1, -1, -1, -1,  4,  8,  0, -1 },
                    { -1, -1,  9, -1, -1, -1, -1, -1, -1,  4,  6, -1 },
                    { -1, -1, -1, -1,  2,  6,  4, -1, -1, -1, -1,  5 },
                    { -1, -1, -1, -1, -1,  3,  8,  4, -1, -1, -1,  3 },
                    { -1, -1, -1, -1, -1, -1,  0,  6, -1, -1, -1,  7 },
                    { -1, -1, -1, -1, -1, -1, -1, -1,  5,  3,  7, -1 }
                };

                Matrice aretes = new Matrice(data);
                List<String> sommets = new ArrayList(Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" }));
                Graphe graphe = new Graphe(aretes, sommets);

                ProgrammationDynamique pd = new ProgrammationDynamique(graphe);
                pd.runProgrammationDynamique();
                
                break;
            case 2 :
                break;
        }
    }
}
