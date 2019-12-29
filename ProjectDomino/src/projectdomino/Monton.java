package projectdomino;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Monton {
    private List<Ficha> m;
    
    public Monton(){
        m = new LinkedList<Ficha>();
        for(int i=6;i>=0;i--){
            for(int j=0;j<=i;j++){
                m.add(new Ficha(i,j));
            }
        }
    }
    
    public Ficha extraerRandom(){
        Random rand = new Random();
        int valor = rand.nextInt(m.size());
        return m.remove(valor);
    }
    
    public boolean esVacio(){
        return m.isEmpty();
    }
    
    public String toString(){
        StringBuilder toret = new StringBuilder("El monton tiene: ");
        for(Ficha i : m){
            toret.append(i.toString()).append(" ");
        }
        return toret.toString();
    }
    
}
