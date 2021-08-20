package Estructura.TpFinal.Estructuras.Lineales;

public class Nodo {
    
    // Atributo
    private Object elemento;
    private Nodo enlace;

    // Constructor
    public Nodo(Object elemento, Nodo enlace){
        
        this.elemento = elemento;
        this.enlace = enlace; // el valor qu puede tomar el enlace es un nodo o null
    }

    // Modificadores 
    public void setElemento(Object newElemento){
        
        this.elemento = newElemento;
    }

    public void setEnlace(Nodo newEnlace){

        this.enlace = newEnlace;
    }

    // Observadores
    public Object getElemento(){
        return this.elemento;
    }

    public Nodo getEnlace(){
        return this.enlace;
    }
}
