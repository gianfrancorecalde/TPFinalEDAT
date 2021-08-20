package Estructura.TpFinal.Estructuras.Lineales;

public class Cola {
    
    // Atributos 
    private Nodo frente;
    private Nodo fin;

    // Constructor 
    public Cola(){
        // Creo un cola vacia, con FRENTE y FIN sin apuntar a ningun nodo
        this.frente = null; 
        this.fin = null;
    }

    // Cola Vacia 
    public boolean esVacia(){

        return this.frente == null & this.fin == null;
    }

    // Poner Elementos 
    public boolean poner(Object newElement){

        Nodo newNodo = new Nodo(newElement, null);

        if(this.esVacia()){
            
            this.frente = newNodo;          // Hago que FRENTE apunte al nuevo nodo 
            this.fin = newNodo;             // Hago que FIN apunte al nuevo nodo 
        }else{

            this.fin.setEnlace(newNodo);    // al nodo que apunta FIN lo enlazo con el nuevo nodo
            this.fin = newNodo;             // modifico a FIN para que apunte al nuevo nodo
        }

        return true;
    }

    // Sacar elementos
    public boolean sacar(){
        
        boolean exito = false;
        Nodo aux;

        if(!this.esVacia()){

            aux = this.frente.getEnlace();  // el nodo que esta engachado con el nodo al que apunta FRENTE, lo guardo en aux
            this.frente = aux;              // modifo FRENTE para que apunte al nodo guardado en aux 
            exito = true;
            if(this.frente == null){        // Si saco el ultimo nodo de la cola tbien debo modificar a FIN para que deje de aputar a ese ultimo nodo
                this.fin = null;
            }
        }
        return exito;
    }

    // Obtener el elemento del frente
    public Object obtenerFrente(){
        
        Object element = null;

        if(!this.esVacia()){
            element = this.frente.getElemento();
        }
        return element;
    }

    // Vaciar cola
    public void vaciar(){

        if(!esVacia()){
            
            while(this.fin != null){

                this.frente = this.frente.getEnlace();
                if(this.frente == null){  
                    this.fin = null;
                }
            }
        }
    }

    // Clonar nodo
    private Nodo cloneNodo(Nodo nodoDeColaOriginal){
        
        Nodo nodoParaClon = null;

        if(nodoDeColaOriginal != null){

            nodoParaClon = new Nodo(nodoDeColaOriginal.getElemento(),cloneNodo(nodoDeColaOriginal.getEnlace()));

        }

        return nodoParaClon;
    }

    // Clonar cola
    public Cola clone(){

        Cola colaClon = new Cola();

        colaClon.frente = cloneNodo(this.frente);
        colaClon.fin = colaClon.frente;
        while (colaClon.fin.getEnlace() != null){

            colaClon.fin = colaClon.fin.getEnlace();
        
        }
        return colaClon; 
    }

    // toString 
    public String toString(){
        
        String cadena = "";
        Nodo recorrerCola = this.frente;
        
        if(!this.esVacia()){
            
            while(recorrerCola != null){
            
                cadena = cadena + " " + recorrerCola.getElemento();
                recorrerCola = recorrerCola.getEnlace();          
            }
            cadena = cadena+"\n"+"Longitud de la cola: "+ this.longitudDeCola();
        }else{

            System.out.println("Cola vacia");
        }    

        return cadena;
    }

    private int longitudDeCola(){

        int tamanio = 0;
        Nodo recorrido = this.frente;
        while(recorrido != null){
            tamanio++;
            recorrido = recorrido.getEnlace();
        }
        return tamanio;
    }

    // Clonar cola 2nd metodo
    public Cola clone2(){

        Cola clon = new Cola();
        Nodo aux1 = this.frente;
        clon.frente = new Nodo(aux1.getElemento(), null);
        Nodo aux2 = clon.frente;
        aux1 = aux1.getEnlace();

        while(aux1 != null){
            
            aux2.setEnlace(new Nodo(aux1.getElemento(), null));
            aux1 = aux1.getEnlace();
            aux2 = aux2.getEnlace();
        }
        clon.fin = aux2;

        return clon;
    }
}
