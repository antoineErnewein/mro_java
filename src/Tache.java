import java.util.ArrayList;

public class Tache{
    private double duree;
    private ArrayList<Tache> dependances;
    private String label;
    private double datePlusTard;
    private double datePlusTot;
    
    public Tache(String label, double duree, ArrayList<Tache> dependances){
        this.duree = duree;
        this.dependances = dependances;
        this.label = label;
        this.datePlusTard = -1;
        this.datePlusTot = -1;
    }
    
    @Override
    public String toString(){
        ArrayList<String> dep = new ArrayList<String>();
        if(dependances != null){
           for(Tache t : dependances){
                dep.add(label);
            } 
        }
        if(datePlusTard == -1|| datePlusTot == -1){
            return label+dep+"[ durée: "+duree+" ]";
        }
        else{
            return label+dep+"[ durée: "+duree+", date au plus tot: "+datePlusTot+", date au plus tard: "+datePlusTard+" ]";
        }
    }

    public String getLabel() {
        return label;
    }
    
    public double getDuree() {
        return duree;
    }

    public ArrayList<Tache> getDependances() {
        return dependances;
    }
    
    public double getDatePlusTard() {
        return datePlusTard;
    }

    public double getDatePlusTot() {
        return datePlusTot;
    }

    public void setDatePlusTard(double datePlusTard) {
        this.datePlusTard = datePlusTard;
    }

    public void setDatePlusTot(double datePlusTot) {
        this.datePlusTot = datePlusTot;
    }
}