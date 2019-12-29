package projectdomino;

public class Ficha {
    private int num1;
    private int num2;
    
    public Ficha(int n1, int n2){
        num1=n1;
        num2=n2;
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }
    
    /**
     * Devuelve la ficha invertida
     * @return ficha invertida
     */
    public Ficha inversa(){
        return new Ficha(num2,num1);
    }
    
    /**
     * Dice si la ficha es colocable en la mesa
     * @param mesa
     * @return true si se puede colocar, false si no
     */
    public boolean esColocable(Mesa mesa){
        boolean toret=false;
        if(num1==mesa.getPrimero().getNum1() || num2==mesa.getPrimero().getNum1() || 
           num1==mesa.getUltimo().getNum2() || num2==mesa.getUltimo().getNum2()){
            toret=true;
        }
        return toret;
    }
    
    public String toString(){
        return("["+num1+"|"+num2+"]");
    }
}
