package projectdomino;

import java.util.List;
import java.util.LinkedList;

public class Jugador {
    private String nombre;
    private List<Ficha> array;
    private boolean mano;
    
    public Jugador(String nom){
        nombre=nom;
        array = new LinkedList<Ficha>();
        mano=false;
    }
    
    public void añadirFicha(Ficha f){
        array.add(f);
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setMano(){
        mano=true;
    }
    
    public boolean esMano(){
        return mano;
    }
    
    public boolean tieneFichas(){
        return !array.isEmpty();
    }
    
    /**
     * Comprueba si el jugador tiene fichas que puede jugar
     * @param mesa El estado actual de la mesa
     * @return true si puede jugar, false si no
     */
    public boolean puedeJugar (Mesa mesa){
        boolean toret=false;
        Ficha aux;
        //Iterator<Ficha> itr = j.getArray().iterator();
        if(!mesa.mesaVacia()){
            for(Ficha i : array){
                if(i.esColocable(mesa)){
                    toret=true;
                }
            }
        }
        else{
            toret=true;
        }
        if(toret){
            System.out.println(nombre+", puedes jugar.");
        }
        else{
            System.out.println(nombre+", no puedes jugar.");
        }
        return toret;
    }
    
    /**
     * Comprueba que fichas puede jugar (para que no la lien los noobs)
     * @param mesa La mesa
     * @return Una lista con las fichas que puede jugar
     */
    public List<Ficha> fichasJugables(Mesa mesa){
        List<Ficha> jugables = new LinkedList<Ficha>();
        if(mesa.mesaVacia()){
            jugables = array;
        }
        else{
            for(Ficha i: array){
                if(i.esColocable(mesa)){
                    jugables.add(i);
                }
            }
        }
        return jugables;
    }
    
    /**
     * Suma los puntos del jugador j
     * @return La suma de los puntos de cada ficha
     */
    public int sumarPuntos(){
        int toret=0;
        for(Ficha i : array){
            toret+=i.getNum1();
            toret+=i.getNum2();
        }
        return toret;
    }
    
    public void mostrarFichas(){
        for(Ficha i : array){
            System.out.print(i.toString());
        }
    }
    
    public boolean eliminaFicha(Ficha f){
        return array.remove(f);
    }
    
    public String toString(){
        StringBuilder toret = new StringBuilder("Nombre: ");
        toret.append(nombre + "\nFichas: ");
        for(Ficha i : array){
            toret.append(i.toString()+ " ");
        }
        return toret.toString();
    }
    
    
}
