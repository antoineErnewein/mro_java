public class FloydWarshall {
    public static Matrice getShortestPaths(Matrice mat){
        Matrice A = new Matrice(mat.getData());
        for(int k=0; k < mat.getNbLines(); k++){
            for(int i=0; i< mat.getNbLines(); i++){
                for(int j=0; j< mat.getNbColumns(); j++){
            if((A.getValueAt(i,k) + A.getValueAt(k,j)) < A.getValueAt(i,j) )
                  A.setValueAt(i,j, (double) Math.min(mat.getValueAt(i,j),(mat.getValueAt(i,k)+mat.getValueAt(k,j) ) ) );
                }
            }
        }
        return A;
    }
 
    public static void main (String[] args) {
        // Exercice 1 : Recherche opérationnelle - Pierre Boutry - 23 Octobre 2013 [Page 49/69]
        double data[][] = {{99999,3,8,6,99999,99999},{99999,99999,99999,2,6,99999},{99999,99999,99999,2,1,99999},{99999,99999,99999,99999,99999,7},{99999,99999,99999,99999,99999,2},{99999,99999,99999,99999,99999,99999}};
        Matrice exo1 = new Matrice(data);
        System.out.println("Enonce de l'exercice 1 du cours :\n");
        exo1.printMatrice();
        Matrice reponse1 = getShortestPaths(exo1);
        System.out.println("\nResolution de l'exercice 1 du cours :\n");
        reponse1.printMatrice();
 
        System.out.println("\n---------------------------------------\n");
 
        // Exercice 2 : Recherche opérationnelle - Pierre Boutry - 23 Octobre 2013 [Page 55/69 ]
        double data2[][] = {{999,0,999,9},{6,999,0,8},{999,4,999,0},{0,7,5,999}};
        Matrice exo2 = new Matrice(data2);
        System.out.println("Enonce de l'exercice 2 du cours :\n");
        exo2.printMatrice();
        Matrice reponse2 = getShortestPaths(exo2);
        System.out.println("\nResolution de l'exercice 2 du cours :\n");
        reponse2.printMatrice();
    }
}