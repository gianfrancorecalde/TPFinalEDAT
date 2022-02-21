package TPFinalEDAT.Estructuras.Diccionario.HashAbierto;

public class DiccionarioHash {
    
    /*      Atributos       */
    private static int tamanio = 10;   // Tamanio de la tabla hash
    private NodoHashDicc [] hash;  // Arreglo de nodos 
    private int cant;   // Cantidad de elementos que tiene la tabla hash

    /*      Constructor     */

    public DiccionarioHash(){
        this.hash = new NodoHashDicc[tamanio];
        this.cant = 0;
    }

    /*      Hash vacio      */

    public boolean esVacio(){
        return this.cant == 0;
    }

    /*      Insertar        */

    public boolean insertar(Object clave, Object dato){

        int pos = Math.abs(clave.hashCode()) % this.tamanio;   // Calcula la posicion en la que se va a guarda el elemento
        NodoHashDicc aux = this.hash[pos];                      // Obtiene el pimer nodo en esa posicion
        boolean encontrado = false;

        while(!encontrado && aux != null){
            // Busca si esta el nuevo elemento en esa posicion
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }
        if(!encontrado){
            // Si el elemento no se encontro entonces se guarda al principio del conjunto
            this.hash[pos] = new NodoHashDicc(clave,dato, this.hash[pos]);
            this.cant++;
        }
        return !encontrado;
    }

    /*      existeClave()      */
    
    public boolean pertenece(Object clave){

        int pos = Math.abs(clave.hashCode()) % this.tamanio;
        NodoHashDicc aux = this.hash[pos];
        boolean encontrado = false;

        while(!encontrado && aux != null){
            // Busca si esta el nuevo elemento en esa posicion
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }

        return encontrado;
    }
    
    /* obtenerInformacion() */

    public Object obtenerInformacion(Object clave){

        int pos = Math.abs(clave.hashCode()) % this.tamanio;
        NodoHashDicc aux = this.hash[pos];
        boolean exito = false;
        Object dato = null;

        while(!exito && aux != null){
            // Busca si esta el nuevo elemento en esa posicion
            if(aux.getClave().equals(clave)){
                dato = aux.getDato();
                exito = true;
            }
            aux = aux.getEnlace();
        }

        return dato;
    } 

    /*      Eliminar        */

    public boolean eliminar(Object clave){

        int pos = Math.abs(clave.hashCode()) % this.tamanio;
        NodoHashDicc aux = this.hash[pos];
        NodoHashDicc anterior = aux;
        boolean eliminado = false;

        if(aux.getClave().equals(clave)){
            // caso 1: el elemeneto a eliminar es el primero en el conjunto
            this.hash[pos] = aux.getEnlace();
            this.cant--;
            eliminado = true;
        }else{
            aux = aux.getEnlace();
            while(!eliminado && aux != null){
                // Busca si esta el nuevo elemento en esa posicion
                eliminado = aux.getClave().equals(clave);
                if(eliminado){
                    // caso 2: el elemento a eliminar es cualquier del conjunto
                    anterior.setEnlace(aux.getEnlace());
                    this.cant--;
                }
                anterior = aux;
                aux = aux.getEnlace();
            }
        }
        return eliminado;
    }

    public String toString(){
        String cadena = "";
        int i;
        if(!this.esVacio()){
            for (i=0; i < tamanio; i++){
                cadena = cadena + i + " --> ";
                NodoHashDicc aux = this.hash[i];
                while(aux != null){
                    cadena += aux.getClave() + " --> ";
                    aux = aux.getEnlace();
                }
                cadena += "\n";
            }
        }else{
            cadena += "tabla hash vacia";
        }
        return cadena;
    }

    public void vaciar(){
        int i;
        for(i=0; i < tamanio; i++){
                this.hash[i] = null;
                if(!this.esVacio()){
                    this.cant--;
                }
            }
    }
}
