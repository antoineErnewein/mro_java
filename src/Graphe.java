
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author etien_000
 */
public class Graphe {

    private List<String> sommets;
    private Matrice aretes;

    public Graphe(Matrice aretes) {
        this.aretes = aretes;

        List<String> sommetsParDefaut = new ArrayList(aretes.getNbColumns());
        for (int i = 1; i < aretes.getNbColumns() + 1; i++)
            sommetsParDefaut.add(String.valueOf(i));

        this.sommets = sommetsParDefaut;
    }

    public Graphe(Matrice aretes, List<String> sommets) {
        if (sommets.size() != aretes.getNbLines())
            throw  new IllegalArgumentException("Nombre de sommets incorrect.");
        
        this.aretes = aretes;        
        this.sommets = sommets;
    }
    
    public Matrice getAretes() {
        return this.aretes;
    }
    
    public List<String> getSommets() {
        return this.sommets;
    }
}
