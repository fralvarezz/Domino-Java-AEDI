/*
Autores:

-Fernando Rodriguez Alvarez
-Jorge Portela Gonzales
-Adrian Gallego Beltran
*/

package projectdomino;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;

public class ProjectDomino {
        
    /**
     * Pide el numero de jugadores y sus nombres; los crea y les da fichas aleatorias
     * @param m El monton de fichas
     * @return Lista de jugadores con las fichas
     */
    public static List<Jugador> preguntarJugadores(Monton m){
        Scanner scan = new Scanner(System.in);
        Jugador j;
        List<Jugador> toret = new LinkedList<Jugador>();
        int numJug=0;
        do{
            try{
            System.out.println("Introduce el numero de jugadores: ");
            numJug=Integer.parseInt(scan.nextLine());
            }
            catch(NumberFormatException exc){
                System.err.println("Por favor introduce un numero del 2 al 4");
                numJug=0;
            }
        }while(numJug<2 || numJug>4);
        int i=1;
        String nombre;
        while(i<=numJug){
            System.out.println("Introduce el nombre del jugador "+i);
            nombre=scan.nextLine();
            j= new Jugador(nombre);
            for(int y=0;y<7;y++){
                j.añadirFicha(m.extraerRandom());
            }
            toret.add(j);
            i++;
        }
        return toret;
    }
    
    /**
     * Una condicion del final de la partida
     * @param l Lista de jugadores
     * @return True si algun jugador se quedo sin fichas, false si no
     */
    public static boolean domino(List<Jugador> l){
        boolean toret=false;
        Jugador j;
        Iterator<Jugador> itr = l.iterator();
        while(!toret && itr.hasNext()){
            j=itr.next();
            if(!j.tieneFichas()){
                toret=true;
                System.out.println("Enhorabuena "+j.getNombre()+", eres el ganador!");
            }
        }
        return toret;
    }
    
    /**
     * Condicion de final de partida
     * @param mesa La mesa
     * @return True si se acaba la partida, false si no
     */
    public static boolean cierre(Mesa mesa){
        boolean toret=false;
        if(!mesa.mesaVacia()){
            toret=(mesa.getContador()[mesa.getPrimero().getNum1()]==8 && mesa.getContador()[mesa.getUltimo().getNum2()]==8);
        }
        return toret;
        }
    
    /**
     * La parte interactiva del turno
     * @param j El jugador que juega
     * @param mesa La mesa
     */
    public static void juego(Jugador j, Mesa mesa){
        Scanner scan = new Scanner(System.in);
        List<Ficha> jugables = j.fichasJugables(mesa);
        int opcion;
        Ficha aux;
        System.out.print("Las fichas que puedes jugar son: ");
        for(Ficha i : jugables){
            System.out.print(i);
        }
        do{
            System.out.println("\nEscoge la ficha que quieres jugar (1,2,3...): ");
            try{
                opcion=Integer.parseInt(scan.nextLine());
                }catch(NumberFormatException exc){
                    System.err.println("Por favor, introduce un numero");
                    opcion=-1;
                }
        }while(opcion<1 || opcion>=(jugables.size()+1));
        opcion--;
        aux=jugables.get(opcion);
        System.out.println("La ficha escogida es: "+aux.toString());
        mesa.colocarFicha(j,aux);
    }
    
    /**
     * Desarrollo del turno
     * @param j El jugador al que le toca
     * @param m El monton de fichas
     * @param mesa La mesa
     */    
    public static void turno(Jugador j, Monton m, Mesa mesa){
        Scanner scan = new Scanner(System.in);
        Ficha aux;
        System.out.println("\n///////////////////////////////////////////////////\n");
        System.out.println("Turno de "+j.getNombre());
        System.out.println("\nEl estado de la mesa es "+mesa.toString());
        System.out.print("Tus fichas son: ");
        j.mostrarFichas();
        System.out.print("\n");
        if(j.puedeJugar(mesa)){
            juego(j, mesa);
        }
        else{
            System.out.println("No puedes jugar ninguna ficha");
            if(!m.esVacio()){
                aux=m.extraerRandom();
                System.out.println("Coges la ficha "+aux.toString());
                j.añadirFicha(aux);
                if(aux.esColocable(mesa)){
                    juego(j,mesa);
                }
            }
            else{
                System.out.println("No puedes coger. El monton esta vacio.");
            }
        }
    }
    
    
    
    /**
     * En caso de cierre, calcula quien ha ganado
     * @param jugadores Todos los jugadores de la partida
     */
    public static void calcularGanadorCierre(List<Jugador> jugadores){
        int ganador=0;
        boolean empate=false;
        Jugador mano=null;
        int menor=Integer.MAX_VALUE;
        int[] resultados = new int [jugadores.size()];
        for(int i=0;i<resultados.length;i++){
            resultados[i]=jugadores.get(i).sumarPuntos();
            if(jugadores.get(i).esMano()){
                mano=jugadores.get(i);
            }
            if(resultados[i]==menor){
                empate=true;
            }
            else if(resultados[i]<menor){
                menor=resultados[i];
                ganador=i;
                empate=false;
            }
            
            System.out.println(jugadores.get(i).getNombre() + " suma " + resultados[i] + " puntos.");
        }
        if(empate){
            if(mano.sumarPuntos()==menor){
                System.out.println("Ha habido un empate. Gana el jugador que lleva la mano: " + mano.getNombre());
            }
            else{
                System.out.println("Se ha producido un empate en el que no interviene el jugador mano.");
            }
        }
        else{
            System.out.println("Enhorabuena "+jugadores.get(ganador).getNombre() + ". Eres el ganador por cierre!");
        }
    }
        
    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        List<Jugador> jugadores;
        Monton mont = new Monton();
        int leToca=0;
        jugadores=preguntarJugadores(mont);           //Creamos los jugadores
        Random generador = new Random();
        jugadores.get(generador.nextInt(jugadores.size()-1)).setMano();    //Le damos la mano a un jugador aleatorio
        for(Jugador j : jugadores){                   //Comprobamos qué jugador es mano para darle el primer turno
            if(j.esMano()){
                leToca=jugadores.indexOf(j);
            }
        }
        while(!domino(jugadores) && !cierre(mesa)){
            if(leToca>=jugadores.size()){
                leToca=0;
            }
            turno(jugadores.get(leToca),mont,mesa);
            leToca++;            
        }
        if(cierre(mesa)){
            System.out.println("NADIE MAS PUEDE COLOCAR FICHAS");
            calcularGanadorCierre(jugadores);
        }
        System.out.println("El estado final de la mesa es: "+mesa.toString());
    }
    
}
