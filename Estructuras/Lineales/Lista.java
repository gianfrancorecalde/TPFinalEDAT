package TPFinalEDAT.Estructuras.Lineales;



public class Lista {
    
    // Atributos  
    private Nodo cabecera;

    // Constructor de una lista
    public Lista(){
        this.cabecera = null;
    }

    // Método esVacio
    public boolean esVacia(){
        return this.cabecera == null;
    }

    public int longitud(){

        int longitud = 0;
        if(this.cabecera != null){
            Nodo recorrido = this.cabecera;
            while(recorrido !=null){
                recorrido= recorrido.getEnlace();
                longitud++;
            }
        }
        return longitud;
    }

    // Método insertar 
    public boolean insertar(Object newElement, int pos){
        
        boolean exito = false;                                                              

            if(pos >= 1 && pos <= this.longitud()+1){                                     // Solo sera falso cuando la posicion ingresada no sea correcta ya que siempre se podra insertar un nuevo elemento
                Nodo nuevo;
                if(pos == 1){                                                       // CASO ESPECIAL
                    this.cabecera = new Nodo(newElement, this.cabecera);                     // Se crea un nuevo nodo y se lo engancha a la cabecera  
                }else{                                                              // CASO GENERAL
                    int i;
                    Nodo aux = this.cabecera;                                       
                    for(i=1; i<pos-1;i++){                                            // AUX se desplazara en la lista de nodos tantas veces como POS-1 se indique
                        aux = aux.getEnlace();
                    }
                    nuevo = new Nodo(newElement,aux.getEnlace());                   // Se crea el nuevo nodo con el NEWELEMENT y se engancha al nodo al q esta enganchado AUX
                    aux.setEnlace(nuevo);                                           // AUX se engancha al nuevo nodo
                }
                exito = true;                                                        
            }
        return exito;
    }

    // Método eliminar
    public boolean eliminar(int pos){
        
        boolean exito = false;

        if(!esVacia()){
            if(pos >= 1 && pos <= this.longitud()){
               if(pos==1){
                   this.cabecera = this.cabecera.getEnlace();
               }else{
                    int i;
                    Nodo aux = this.cabecera;
                    for(i=1; i<pos-1;i++){
                        aux = aux.getEnlace();
                    }
                    aux.setEnlace(aux.getEnlace().getEnlace());
               }
               exito = true;
            }
        }
        return exito;
    }

    // Método recuperar 
    public Object recuperar(int pos){

        Object elementEncontrado = null;
        Nodo recorrido = this.cabecera;
        int i;
        if(!esVacia()){
            if(pos >= 1 && pos <= this.longitud()){
                for(i=1; i<pos; i++){
                    recorrido = recorrido.getEnlace();
                }
                elementEncontrado = recorrido.getElemento();
            }
        }
        return elementEncontrado;
    }

    // Método Localizar
    public int localizar(Object element){

        int pos = -1;
        if(!this.esVacia()){
            Nodo recorrido = this.cabecera;
            boolean encontrado = false;
            int posReal = 0;
            while(!encontrado && recorrido!= null){
                if(element.equals(recorrido.getElemento())){
                    encontrado = true;
                }else{
                    recorrido = recorrido.getEnlace();
                }
                posReal++;
            }
            if(encontrado){
               pos = posReal; 
            }
        }
        return pos;
    }

    // Método Vaciar
    public void vaciar(){
        
        if(!esVacia()){
            this.cabecera = null;
        }else{
            System.out.println("La lista esta vacia");
        }
    }

    // Método Clone Mejorado
    public Lista clone(){

        Lista clone = new Lista();
        if(!this.esVacia()){
            // Utilizo un nodo (recorridoListaOriginal) para recorrer todos los elementos de la lista
            // Utilizo un nodo (recorridoListaClone) para copiar los elementos de la lista original y recorrer esos elementos copiados
            clone.cabecera = new Nodo(this.cabecera.getElemento(),null);        // copia la cabecera de lista original a la cabecera de la lista clone                                
            Nodo recorridoListaOriginal = this.cabecera.getEnlace();            // salto al proximo elemento de la lista original
            Nodo recorridoListaClone = clone.cabecera;                          // comienzo el recorrido desde la cabecera de la lista clon 
            Nodo aux;
            while(recorridoListaOriginal != null){                              // SI el elemento que esto recorriendo es nulo detengo la iteracion
                aux = new Nodo(recorridoListaOriginal.getElemento(), null);     // creo un nodo con el elemento del nodo que estoy recorriendo en la lista original
                recorridoListaClone.setEnlace(aux);                             // seteo el enlace del ultimo nodo copia para enlazarlo con el nuevo nodo creado en la linea de arriba
                recorridoListaClone = recorridoListaClone.getEnlace();          // salto al siguiente nodo copiado  
                recorridoListaOriginal = recorridoListaOriginal.getEnlace();    // salto al siguiente elemento de la lista
            }
        }
        return clone;
    }

    // Método toString
    public String toString(){
        
        String cadena = "";
        int i;
        if(!this.esVacia()){
            Nodo recorrido = this.cabecera;
            for(i=1; i<=this.longitud();i++){
                cadena = cadena + " "+ recorrido.getElemento().toString();
                recorrido = recorrido.getEnlace();
            }
            cadena = cadena + "\n"+ "El tamaño de la lista es: " + this.longitud();
        }else{
            cadena = "Lista Vacia";
        }
        
        return cadena;
    }

    // ----------------------------------------------EJERCICIOS DE SIMULACRO------------------------------------------ 
    public Lista obtenerMultiplo(int num){
        Lista newLista = new Lista();
        if(!this.esVacia()){
            newLista.cabecera = obtenerMultiploAux(this.cabecera, 1, num);
        }
        return newLista;
    }

    private Nodo obtenerMultiploAux(Nodo nodoOriginal, int pos,int numMultiplo){

        Nodo nodoCopia = null;
        if(nodoOriginal !=null){
            nodoCopia = obtenerMultiploAux(nodoOriginal.getEnlace(), pos+1, numMultiplo);
            if(pos % numMultiplo == 0){
                nodoCopia = new Nodo(nodoOriginal.getElemento(), nodoCopia);
            }
        }
        return nodoCopia;
    }

    //---------------------------------------- PARCIAL
    public void insertarAnterior(Object elemento1, Object elemento2){
        
        Nodo recorrido = this.cabecera;
        if(elemento1.equals(this.cabecera.getElemento())){
            Nodo nodoAnterior = new Nodo(elemento2,recorrido);
            this.cabecera = nodoAnterior;
            Nodo nodoPosterior = new Nodo(elemento2, recorrido.getEnlace());
            recorrido.setEnlace(nodoPosterior);
            recorrido = recorrido.getEnlace();
        }
        while(recorrido != null){
            if(recorrido.getEnlace()!=null && recorrido.getEnlace().getElemento().equals(elemento1)){
                Nodo nodoAnterior = new Nodo(elemento2, recorrido.getEnlace());
                recorrido.setEnlace(nodoAnterior);
                recorrido = nodoAnterior;
            }
            recorrido = recorrido.getEnlace();
        }
    }

    public void insertarAnteriorMejorado(Object elemento1, Object elemento2){
        
        Nodo recorrido = this.cabecera;
        if(this.cabecera != null && this.cabecera.getElemento().equals(elemento1)){
            // caso especial
            Nodo nodoAnterior = new Nodo(elemento2,recorrido);
            this.cabecera = nodoAnterior;
            Nodo nodoPosterior = new Nodo(elemento2, recorrido.getEnlace());
            recorrido.setEnlace(nodoPosterior);
        }
        while(recorrido != null){
            // aso general
            if(recorrido.getEnlace()!=null && recorrido.getEnlace().getElemento().equals(elemento1)){
                Nodo nodoAnterior = new Nodo(elemento2, recorrido.getEnlace());
                recorrido.setEnlace(nodoAnterior);
                recorrido = nodoAnterior;
            }
            recorrido = recorrido.getEnlace();
        }
    }

    // ----------------------------------------- Practica

    public void invertir(){
        if(!this.esVacia()){
            Nodo auxCabecera = this.cabecera; // referencia siempre a la cabecera original
            Nodo recorrido = this.cabecera.getEnlace(); // recorre los nodos de la lista
            Nodo nuevaCabecera = recorrido; // referencia a los nodos que van a tomar el lugar de cabecera
            while(recorrido != null){
                nuevaCabecera = recorrido;  // nodo que va a tomar la posicion de cabecera
                recorrido = recorrido.getEnlace();
                auxCabecera.setEnlace(recorrido); // uno la cabeceraOrginial con los nodos que van a dejar de estar enlazados con la nuevaCabecera
                nuevaCabecera.setEnlace(this.cabecera); // posiciono la nueva cabecera por delante de la cabecera original
                this.cabecera = nuevaCabecera; // cambio cabecera
            }
        }
    }

    public void eliminarAparicion(Object elemento){
        if(!this.esVacia()){
            Nodo recorrido = this.cabecera;
            if(recorrido.getElemento().equals(elemento)){
                // Caso especial: hay una aparicion en la cabecera de la lista
                recorrido = recorrido.getEnlace();
                this.cabecera = recorrido;
            }
            Nodo anterior = recorrido;
            while(recorrido != null){
                // Caso general: esta metodologia se aplica para todos el resto de los casos 
                if(recorrido.getElemento().equals(elemento)){
                    anterior.setEnlace(recorrido.getEnlace());
                }
                anterior = recorrido;
                recorrido = recorrido.getEnlace();
            }     
        }
    }

    public void copiarCola(Cola cl){
        Nodo aux = this.cabecera;
        while(aux != null){
            cl.poner(aux.getElemento());
            aux = aux.getEnlace();
        }
    }

    public Cola copiarLista(){
        Cola cl = new Cola();
        Nodo aux = this.cabecera;
        if(!this.esVacia()){
            while(aux != null){
            cl.poner(aux.getElemento());
            aux = aux.getEnlace();
            }
        }
        return cl;
    }
}
