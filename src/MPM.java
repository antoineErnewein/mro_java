import java.util.ArrayList;
import java.util.Arrays;

public class MPM {
    public static void setEarliestAndLatest(ArrayList<Tache> taches){
        ArrayList<Tache> start = new ArrayList<Tache>();
        
        for(Tache tache : taches){
            if(tache.getDependances() == null){
                tache.setDatePlusTard(tache.getDuree());
                tache.setDatePlusTot(tache.getDuree());
                start.add(tache);
            }
            else{
                dateAuPlutTot(tache);
            }
        }
        double maxDateAuPlusTot = taches.get(0).getDatePlusTot();
        Tache last = taches.get(0);
        for(Tache tache : taches){
            if(tache.getDatePlusTot() > maxDateAuPlusTot){
                last = tache; 
            }
        }
        last.setDatePlusTard(last.getDatePlusTot());
        dateAuPlutTard(last);
    }
    
    private static void dateAuPlutTard(Tache t){
        ArrayList<Tache> dependances = t.getDependances();
        if(dependances==null){
            return;
        }
        for(Tache dependence : dependances){
            dependence.setDatePlusTard(t.getDatePlusTard()-t.getDuree());
            dateAuPlutTard(dependence);
        }
    }
    
    private static void dateAuPlutTot(Tache t){
        ArrayList<Tache> dependances = t.getDependances();
        Tache max = dependances.get(0);
        double tmpMax = max.getDatePlusTot();
        
        for(Tache dependence : dependances){
            if(dependence.getDatePlusTard() == -1){
                dateAuPlutTot(dependence);
            }
        }
        
        for(Tache dependence : dependances){
            double tmp = dependence.getDatePlusTot();
            if(tmp > tmpMax){
                tmpMax = tmp;
            }
        }
        t.setDatePlusTot(tmpMax+t.getDuree());
    }
    
    public static void main (String[] args){
       ArrayList<Tache> taches;
       Tache a = new Tache("a",1, null);
       Tache b = new Tache("b",2, new ArrayList<Tache>(Arrays.asList(a)));
       Tache c = new Tache("c",1, new ArrayList<Tache>(Arrays.asList(b)));
       Tache d = new Tache("d",1, new ArrayList<Tache>(Arrays.asList(c)));
       Tache e = new Tache("e",2, new ArrayList<Tache>(Arrays.asList(a)));
       Tache f = new Tache("f",1, new ArrayList<Tache>(Arrays.asList(e)));
       Tache g = new Tache("g",2, new ArrayList<Tache>(Arrays.asList(f)));
       Tache h = new Tache("h",2, new ArrayList<Tache>(Arrays.asList(f)));
       Tache i = new Tache("i",1, new ArrayList<Tache>(Arrays.asList(g)));
       Tache j = new Tache("j",2, new ArrayList<Tache>(Arrays.asList(h,i)));
       Tache k = new Tache("k",1, new ArrayList<Tache>(Arrays.asList(d,j)));
       Tache l = new Tache("l",2, new ArrayList<Tache>(Arrays.asList(k)));
       
       taches = new ArrayList<Tache>(new ArrayList<Tache>(Arrays.asList(a,b,c,d,e,f,g,h,i,j,k,l)));  
       for(Tache t:taches){
           System.out.println(t);
       }
       System.out.println("MPM :");
       setEarliestAndLatest(taches);
       for(Tache t:taches){
           System.out.println(t);
       }
    }
}


