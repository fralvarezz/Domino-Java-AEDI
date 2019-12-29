package projectdomino;

import java.util.LinkedList;
import java.util.Deque;
import java.util.Scanner;

public class Mesa {
    private Deque<Ficha> m;
    private int[] contador;
    
    public Mesa(){
        m= new LinkedList();
        contador = new int[7];
    }
        
    public Ficha getPrimero(){
        return m.getFirst();
    }
    
    public Ficha getUltimo(){
        return m.getLast();
    }
    
    public void insertarPrincipio(Ficha f){
        m.addFirst(f);
    }
    
    public void insertarFinal(Ficha f){
        m.addLast(f);
    }

    public int[] getContador() {
        return contador;
    }
    
    /**
     * Coloca la ficha seleccionada en un extremo de la mesa a elegir por el jugador
     * @param j El jugador que coloca la ficha
     * @param aux La ficha a colocar
     */
    public void colocarFicha(Jugador j,Ficha aux){
        Scanner scan = new Scanner(System.in);
        char opcionColoc;
        boolean principio=false;
        boolean ultim=false;
        boolean colocada=false;
        if(mesaVacia()){
                principio=true;
                ultim=true;
            }
            else{
                if(aux.getNum1()==getPrimero().getNum1() || aux.getNum2()==getPrimero().getNum1()){
                    principio=true;
                }
                if(aux.getNum1()==getUltimo().getNum2() || aux.getNum2()==getUltimo().getNum2()){
                    ultim=true;
                }
            }
        System.out.print("La ficha se puede colocar ");
        if(principio){
            System.out.print("al principio");
        }
        if(ultim){
            System.out.print("al final.");
        }
        do{    
            do{
                System.out.println("\nDonde la quieres colocar?(p/f)");
                opcionColoc=scan.nextLine().charAt(0);
            }while(opcionColoc!='p' && opcionColoc!='f');
            if(opcionColoc=='p'){
                if(principio){
                     if(mesaVacia() || aux.getNum2()==getPrimero().getNum1()){
                        insertarPrincipio(aux);
                    }
                    else{
                        insertarPrincipio(aux.inversa());
                    }
                    j.eliminaFicha(aux);
                    getContador()[aux.getNum1()]++;
                    getContador()[aux.getNum2()]++;
                    colocada=true;
                }
                else{
                    System.out.println("No puedes colocar esa ficha al principio");
                    colocada=false;
                }
            }
            else if(opcionColoc=='f'){
                if(ultim){
                    if(mesaVacia() || aux.getNum1()==getUltimo().getNum2()){
                        insertarFinal(aux);
                    }
                    else{
                        insertarFinal(aux.inversa());
                    }
                    j.eliminaFicha(aux);
                    getContador()[aux.getNum1()]++;
                    getContador()[aux.getNum2()]++;
                    colocada=true;
                }
                else{
                    System.out.println("No puedes colocar esa ficha al final");
                    colocada=false;
                }
            }
        }while(!colocada);
    }
    
    public boolean mesaVacia(){
        return m.isEmpty();
    }
    
    public String toString(){
        StringBuilder toret = new StringBuilder("");
        for(Ficha  i : m){
            toret.append(i.toString());
        }
        return toret.toString();
    }
}
