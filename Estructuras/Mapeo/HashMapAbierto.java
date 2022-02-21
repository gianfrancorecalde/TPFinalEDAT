package TPFinalEDAT.Estructuras.Mapeo;

import TPFinalEDAT.Estructuras.Lineales.*;

public class HashMapAbierto {
    
    /*      Atributos       */
    private static int tamanio = 20;   // Tamanio de la tabla hash
    private NodoHashMap [] hash;  // Arreglo de nodos 
    private int cant;   // Cantidad de elementos que tiene la tabla hash

    /*      Constructor     */

    public HashMapAbierto(){
        this.hash = new NodoHashMap[tamanio];
        this.cant = 0;
    }

    /*      Hash vacio      */

    public boolean esVacio(){
        return this.cant == 0;
    }

    /*      Asociar        */

    public boolean asociar(Object valorDominio, Object valorRango){

        boolean encontrado = false;
            int pos = valorDominio.hashCode() % this.tamanio;   // Calcula la posicion en la que se va a guarda el valorDominio
            NodoHashMap aux = this.hash[pos];                      // Obtiene el pimer nodo en esa posicion
            while(!encontrado && aux != null){
                // Busca si esta el el nuevo dominio en esa posicion
                encontrado = aux.getDominio().equals(valorDominio);
                if (!encontrado) {
                    aux = aux.getEnlace();
                }
            }
            if(encontrado){
                // el dominio existe entonces solo agrego el valorRango
                aux.getRango().insertar(valorRango, aux.getRango().longitud()+1);
            }else{
                // El dominio no existe, lo creo y agrego el valorRango 
                this.hash[pos] = new NodoHashMap(valorDominio, this.hash[pos]);
                this.hash[pos].getRango().insertar(valorRango, this.hash[pos].getRango().longitud()+1);
                this.cant++; 
            }
        return !encontrado;
    }

    /*      Pertenece       */
    
    public boolean pertenece(Object valorDominio, Object valorRango){

        boolean encontrado = false;
        if(!this.esVacio()){
            int pos = valorDominio.hashCode() % this.tamanio;
            NodoHashMap aux = this.hash[pos];
            while(!encontrado && aux != null){
                // Busca si esta el nuevo valorDominio en esa posicion
                if(aux.getDominio().equals(valorDominio)){
                    encontrado = aux.getRango().localizar(valorRango) != -1;
                }
                aux = aux.getEnlace();
            }
        }
        return encontrado;
    }
    
    /* ObtenerValores */

    public Lista obtenerValores(Object valorDominio){
        
        Lista rango=new Lista();
        if(!this.esVacio()){
            int pos = valorDominio.hashCode() % this.tamanio;
            NodoHashMap aux = this.hash[pos];
            while(aux != null){
                // Busca si esta el nuevo valorDominio en esa posicion
                if(aux.getDominio().equals(valorDominio)){
                    rango = aux.getRango().clone();
                }
                aux = aux.getEnlace();
            }
        }
        return rango;
    }

    /*      Desasociar        */

    public boolean desasociar(Object valorDominio, Object valorRango){

        boolean exito = false;
        if(!this.esVacio()){
            int pos = valorDominio.hashCode() % this.tamanio;
            NodoHashMap aux = this.hash[pos];
            NodoHashMap anterior = aux;

            if(aux.getDominio().equals(valorDominio)){
                // caso 1: el elemeneto a eliminar es el primero en el conjunto
                this.hash[pos].getRango().eliminarAparicion(valorRango);
                if(this.hash[pos].getRango().esVacia()){
                    // si al eliminar el elemento del rango este queda vacio entonces se elimina el dominio del conjunto
                    this.hash[pos] = aux.getEnlace();
                    this.cant--;
                }   
                exito = true;
            }else{
                aux = aux.getEnlace();
                while(!exito && aux != null){
                    // Busca si esta el nuevo valorDominio en esa posicion
                    exito = aux.getDominio().equals(valorDominio);
                    if(exito){
                        // caso 2: el valorDominio a eliminar es cualquier del conjunto
                        aux.getRango().eliminarAparicion(valorRango);
                        if(!aux.getRango().esVacia()){
                            anterior.setEnlace(aux.getEnlace());
                            this.cant--;
                        }
                    }
                anterior = aux;
                aux = aux.getEnlace();
                }
            }
        }
        return exito;
    }

    public String toString(){
        String cadena = "";
        if(!this.esVacio()){
            int i;
            NodoHashMap aux ;
            for (i=0; i < tamanio; i++){
                aux = this.hash[i];
                while(aux != null){
                    cadena += aux.getDominio() + " --> "+aux.getRango().toString() + "\n";
                    aux = aux.getEnlace();
                }
            }
        }else{
            cadena += "tabla hash vacia";
        }
        return cadena;
    }

    public void vaciar(){
        if(!this.esVacio()){
            int i;
            for(i=0; i < tamanio; i++){
                this.hash[i] = null;
                if(!this.esVacio()){
                    this.cant--;
                }
            }
        }
    }
}
