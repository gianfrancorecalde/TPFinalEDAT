package Estructura.TpFinal.Estructuras.Diccionario.AVL;
import Estructura.TpFinal.Estructuras.Lineales.*;

public class DiccionarioAVL {
    
    /*      Atributo        */

    private NodoAVLDicc raiz;

    /*      Constructor     */

    public DiccionarioAVL(){
        this.raiz = null;
    }

    /*      ESVACIO         */

    public boolean esVacio(){
        return this.raiz == null;
    }
   
    /*      VACIAR         */

    public void vaciar(){
        this.raiz = null;
    }

    /*      Insertar        */

    public boolean insertarRecursivo(Comparable clave, Object dato){

        boolean exito = false;
        if(this.esVacio()){
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
            exito = true;
        }else{
            exito = insertarAux(this.raiz, clave, dato, 0);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLDicc nodo, Comparable clave, Object dato, int encontrado){

        boolean exito = false;
        int balanceHijo;
        if(nodo != null){
            if(nodo.getClave().compareTo(clave) == 0){
                // bandera de corte cuando ya existe el objeto buscado
                encontrado = 1;
            }else{
                if(clave.compareTo(nodo.getClave()) < 0){
                    exito = insertarAux(nodo.getIzquierdo(), clave, dato, encontrado);
                    if(!exito && encontrado == 0){
                        // si exito es falso es porque el hijo izquierdo en null
                        // entonces enlazo el nuevo elemento al nodo actual
                        nodo.setIzquierdo(new NodoAVLDicc(clave,dato, null, null));   
                        exito = true;
                    }
                    nodo.recalcularAltura();    // Recalculo la altura del nodo actual  
                    balanceHijo = getBalance(nodo.getIzquierdo());  // Calculo el balance del hijo izquierdo
                    if(Math.abs(balanceHijo) >= 2){
                        // El hijo se encuentra desbalanceado por lo que sera necesario rotar ese subarbol
                        nodo.setIzquierdo(balancear(nodo.getIzquierdo()));
                    }
                    
                }else{
                    if(clave.compareTo(nodo.getClave()) > 0){
                        exito = insertarAux(nodo.getDerecho(), clave, dato,encontrado);
                        if(!exito && encontrado == 0){
                            // si exito es falso es porque el hijo derecho en null 
                            // entonces enlazo el nuevo elemento al nodo actual
                            nodo.setDerecho(new NodoAVLDicc(clave,dato, null, null));
                            exito = true;
                        }
                        nodo.recalcularAltura();    // recalculo la altura del nodo actual
                        balanceHijo = getBalance(nodo.getDerecho());    // calculo el balance del hizo derecho
                        if(Math.abs(balanceHijo) >= 2){
                            // El hijo se encuentra desbalanceado por lo que sera necesario rotar ese subarbol
                            nodo.setDerecho(balancear(nodo.getDerecho()));
                        }
                    }
                }
                if(nodo == this.raiz){
                    // En el caso de que el nodo actual sea la raiz del arbol mayor y este este esta desbalanceado se debera rotar
                    if(Math.abs(getBalance(nodo)) >= 2){
                        this.raiz = balancear(nodo);
                    }
                }
            }
        }
        return exito;
    }

    /*      Eliminar        */
    
    public boolean eliminar(Comparable clave){

        boolean exito = false;
        if(!this.esVacio()){
            exito = eliminarAux(this.raiz,null, clave);
        }   
        return exito;
    }

    private boolean eliminarAux(NodoAVLDicc nodo, NodoAVLDicc padre,  Comparable clave){

        boolean encontrado = false;
        if(nodo != null){
            if(clave.compareTo(nodo.getClave()) == 0){
                encontrado = true;
                if(nodo.getIzquierdo() == null && nodo.getDerecho() == null){
                    // Caso 1: se manda al mismo hijo para ser eliminado
                    caso1(nodo, padre);
                }else{
                    if(nodo.getDerecho() == null){
                        // Caso 2: solo tiene hijo izquierdo
                        casoSubArbolIzq(nodo, padre);
                    }else{
                        if(nodo.getIzquierdo() == null){
                            // Caso 2: solo tiene hijo derecho
                            casoSubArbolDer(nodo,padre);
                        }else{
                            // Caso 3: Tiene dos hijos
                            buscarCandidato(nodo,nodo.getIzquierdo(), nodo.getDerecho(), padre);
                        }
                    }
                }
            }else{
                if(clave.compareTo(nodo.getClave()) < 0){ // Elemento es menor al nodo
                    // Enlazo el hijo del hijo izquierdo del nodo actual, al nodo actual
                    if(eliminarAux(nodo.getIzquierdo(), nodo, clave)){
                        nodo.recalcularAltura();
                        encontrado = true;
                    }    
                    int balanceHijo = getBalance(nodo.getIzquierdo());  // Calculo el balance del hijo izquierdo
                    if(Math.abs(balanceHijo) > 1){
                        // El hijo se encuentra desbalanceado por lo que sera necesario rotar ese subarbol
                        nodo.setIzquierdo(balancear(nodo.getIzquierdo()));
                    }                               
                }else{  // Elemento es mayor al nodo
                    if(clave.compareTo(nodo.getClave()) > 0){
                        // Enlazo el hijo del hijo derecho del nodo actual, al nodo actual
                        if(eliminarAux(nodo.getDerecho(), nodo, clave)){
                            nodo.recalcularAltura();    // recalculo la altura del nodo actual
                        }
                        int balanceHijo = getBalance(nodo.getDerecho());    // calculo el balance del hizo derecho
                        if(Math.abs(balanceHijo) > 1){
                            // El hijo se encuentra desbalanceado por lo que sera necesario rotar ese subarbol
                            nodo.setDerecho(balancear(nodo.getDerecho()));
                        }
                    }
                }
            }
            if(nodo == this.raiz){
                // En el caso de que el nodo actual sea la raiz del arbol mayor y este este esta desbalanceado se debera rotar
                if(Math.abs(getBalance(nodo)) >1){
                    this.raiz = balancear(nodo);
                }
            }
        }
        return encontrado;
    }

    private void caso1(NodoAVLDicc hijo, NodoAVLDicc padre){
        if(hijo != null){
            if(hijo.getClave().compareTo(padre.getClave()) < 0){
                padre.setIzquierdo(null);
            }else{
                if(hijo.getClave().compareTo(padre.getClave()) > 0){
                    padre.setDerecho(null);
                }
            }
        }
    }

    private void casoSubArbolIzq(NodoAVLDicc hijo, NodoAVLDicc padre){
        if(hijo != null){
            if(hijo.getClave().compareTo(padre.getClave()) < 0){
                padre.setIzquierdo(hijo.getIzquierdo());
            }else{
                padre.setDerecho(hijo.getIzquierdo());
            }
        }
    }

    private void casoSubArbolDer(NodoAVLDicc hijo, NodoAVLDicc padre){
        if(hijo != null){
            if(hijo.getClave().compareTo(padre.getClave()) < 0){
                padre.setIzquierdo(hijo.getDerecho());
            }else{
                padre.setDerecho(hijo.getDerecho());
            }
        }
    }

    

    private void buscarCandidato(NodoAVLDicc hijo, NodoAVLDicc nietoIzq, NodoAVLDicc nietoDer, NodoAVLDicc padre){
        NodoAVLDicc aux = null;
        if(hijo != null){
            if(nietoIzq.getIzquierdo() == null && nietoIzq.getDerecho() == null){
                aux = nietoIzq;
                aux.setDerecho(nietoDer);
            }else{
                if(nietoDer.getIzquierdo() == null && nietoDer.getDerecho() == null){
                    aux = nietoDer;
                    aux.setIzquierdo(nietoIzq);
                }else{
                    aux = menorCandidatoDerecho(nietoDer, hijo);
                    aux.setDerecho(nietoDer);
                    aux.setIzquierdo(nietoIzq);
                }
            }
            if(hijo.getClave().compareTo(padre.getClave()) < 0){
                padre.setIzquierdo(aux);
            }else{
                padre.setDerecho(aux);
            }
        } 
    }

    private NodoAVLDicc menorCandidatoDerecho(NodoAVLDicc hijo, NodoAVLDicc padre){
        NodoAVLDicc n = null;
        if(hijo != null){
                if(hijo.getIzquierdo() == null){
                    n = hijo;
                    padre.setIzquierdo(hijo.getDerecho());
                }else{
                    n = menorCandidatoDerecho(hijo.getIzquierdo(), hijo);
                    hijo.recalcularAltura();
                    int balance = getBalance(hijo);
                    if(Math.abs(balance) > 1){
                        // El hijo se encuentra desbalanceado por lo que sera necesario rotar ese subarbol
                        padre.setIzquierdo(balancear(hijo));
                    }
                }
        }
        
        return n;
    }

    private NodoAVLDicc mayorCandidatoIzq(NodoAVLDicc hijo, NodoAVLDicc padre){
        NodoAVLDicc n = null;
        if(hijo != null){
            if(hijo.getIzquierdo() == null && hijo.getDerecho() == null){
                n = hijo;
                padre.setDerecho(null);
            }else{
                if(hijo.getDerecho() == null){
                    n = hijo;
                    padre.setDerecho(hijo.getIzquierdo());
                }else{
                    n = menorCandidatoDerecho(hijo.getDerecho(), hijo);
                    hijo.recalcularAltura();
                    int balance = getBalance(hijo);
                    if(Math.abs(balance) > 1){
                        // El hijo se encuentra desbalanceado por lo que sera necesario rotar ese subarbol
                        padre.setDerecho(balancear(hijo));
                    }
                }
            }
        }
        return n;
    }

    private NodoAVLDicc balancear(NodoAVLDicc nodo){

        NodoAVLDicc resp = null;
        
        if(nodo != null){

            if(getBalance(nodo) >= 2 && getBalance(nodo.getIzquierdo()) >= 0){
                //  rotacion simple hacia la derecha 
                resp = rotacionDer(nodo);
            }else{
                if(getBalance(nodo) <= -2 && getBalance(nodo.getDerecho()) <= 0){
                    // rotacion simple hacia la izquierda
                    resp = rotacionIzq(nodo);
                }else{
                    if(getBalance(nodo) >=2 && getBalance(nodo.getIzquierdo()) <= 0){
                        // rotacion doble izq - der 
                        nodo.setIzquierdo(rotacionIzq(nodo.getIzquierdo()));
                        resp = rotacionDer(nodo);
                    }else{
                        if(getBalance(nodo) <= -2 && getBalance(nodo.getDerecho()) >= 0){
                            // rotacion doble der - izq
                            nodo.setDerecho(rotacionDer(nodo.getDerecho()));
                            resp = rotacionIzq(nodo);
                        }
                    }
                }
            }
        }
        return resp;
    }


    private int getBalance(NodoAVLDicc nodo){

        int balance = -1;
        if(nodo != null){
            if(nodo.getIzquierdo() != null && nodo.getDerecho() != null){
                // tiene dos hijos 
                balance = nodo.getIzquierdo().getAltura() - nodo.getDerecho().getAltura();
            }else{
                if(nodo.getIzquierdo() != null && nodo.getDerecho() == null){
                    // solo tiene hijo izquierdo
                    balance = nodo.getIzquierdo().getAltura() - (-1);
                }else{
                    if(nodo.getIzquierdo() == null && nodo.getDerecho() != null){
                        // solo tiene hijo derecho
                        balance  = (-1) - nodo.getDerecho().getAltura();
                    }else{
                        // no tiene hijos
                        balance = 0;
                    }
                }
            }
        }
        return balance;
    }

    private NodoAVLDicc rotacionDer(NodoAVLDicc nodo){
        // hace una rotacion a la derecha del subarbol
        NodoAVLDicc h, temp;
        h = nodo.getIzquierdo();
        temp = h.getDerecho();
        h.setDerecho(nodo);
        nodo.setIzquierdo(temp);
        nodo.recalcularAltura();    // recalculo la altura padre
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDicc rotacionIzq(NodoAVLDicc nodo){
        // Hace una rotacion a la izquierda del subarbol
        NodoAVLDicc h, temp;
        h = nodo.getDerecho();
        temp = h.getIzquierdo();
        h.setIzquierdo(nodo);
        nodo.setDerecho(temp);   
        nodo.recalcularAltura();    // recalculo la altura padre
        h.recalcularAltura();
        return h;
    }

    /*      toString        */

    public String toString(){
        
        String cadena;
        if(this.esVacio()){
            cadena = "Arbol vacio";
        }else{
            cadena = generarString(this.raiz);
        }
        return cadena;
    }

    private String generarString(NodoAVLDicc n){
        
        String cadena;
        if(n == null){
            // Cuando el hijo es nulo devuelve "-"
            cadena = "-"; 
        }else{
            String cadenaHI = generarString(n.getIzquierdo());
            String cadenaHD = generarString(n.getDerecho());
            if(cadenaHI.equalsIgnoreCase("-") && cadenaHD.equalsIgnoreCase("-")){
                // Si los hijos son nulos devuelve un String:
                //elemento HI: - HD: -
                cadena = n.getClave()+"("+n.getAltura()+")"+ " HI: "+ cadenaHI + " HD: "+cadenaHD;
            }else{
                if(!cadenaHI.equalsIgnoreCase("-") && cadenaHD.equalsIgnoreCase("-")){
                    // Si solo el hijo derecho es nulo, devuelve un String: 
                    // elemento HI: elementoHI HD: - 
                    // elementoHI HI: ... HD: ...
                    cadena = n.getClave()+"("+n.getAltura()+")" + " HI: " + n.getIzquierdo().getClave() + " HD: "+ cadenaHD + "\n"+ cadenaHI; 
                }else{
                    if(cadenaHI.equalsIgnoreCase("-") && !cadenaHD.equalsIgnoreCase("-")){
                        // Si solo el hijo izquierdo es nulo, devuelve un String:
                        // elemento HI: - HD: elementoHD 
                        // elementoHD HI: ... HD: ...
                        cadena = n.getClave()+"("+n.getAltura()+")" + " HI: " + cadenaHI + " HD: "+ n.getDerecho().getClave() + "\n"+ cadenaHD; 
                    }else{
                        // Si ninguno de los dos hijos es nulo, devuelve:
                        // elemento HI: elementoHI HD: elementoHD 
                        // elementoHI HI: ... HD: ...
                        // elementoHD HI: ... HD: ...
                        cadena = n.getClave()+"("+n.getAltura()+")" + " HI: " + n.getIzquierdo().getClave() + " HD: "+ n.getDerecho().getClave() + "\n"+ cadenaHI + "\n"+ cadenaHD ; 
                    }
                }
            }
            
        }
        return cadena;
    }

    /* obtenerInfomacion() */

    public Object obtenerDato(Comparable clave){
        Object dato = null;
        if(!this.esVacio()){
            dato = obtenerDatoAux(this.raiz, clave);
        }
        return dato;
    }

    private Object obtenerDatoAux(NodoAVLDicc n, Comparable clave){
        Object dato = null;
        if(n!= null){
            if(clave.compareTo(n.getClave()) == 0){
                dato = n.getDato();
            }else{
                if(clave.compareTo(n.getClave()) < 0){
                    dato = obtenerDatoAux(n.getIzquierdo(), clave);
                }else{
                    if(clave.compareTo(n.getClave()) >0){
                        dato = obtenerDatoAux(n.getDerecho(), clave);
                    }
                }
            }
        }
        return dato;
    }

    /* existeClave() */

    public boolean existeClave(Comparable clave){
        boolean existe = false;
        if(!this.esVacio()){
            existe = existeClaveAux(this.raiz, clave);
        }
        return existe;
    }

    private boolean existeClaveAux(NodoAVLDicc n, Comparable clave){
        boolean existe = false;
        if(n!= null){
            if(clave.compareTo(n.getClave()) == 0){
                existe = true;
            }else{
                if(clave.compareTo(n.getClave()) < 0){
                    existe = existeClaveAux(n.getIzquierdo(), clave);
                }else{
                    if(clave.compareTo(n.getClave()) >0){
                        existe = existeClaveAux(n.getDerecho(), clave);
                    }
                }
            }
        }
        return existe;
    }

    public Cola listarRango(Comparable min, Comparable max) {

        Cola cl = new Cola();
        if (!this.esVacio()) {
            listarRangoAux(this.raiz, min, max, cl);
        }
        return cl;
    }

    private void listarRangoAux(NodoAVLDicc nodo, Comparable min, Comparable max, Cola cl) {

        if (nodo != null) {
            if ((min.compareTo(nodo.getClave()) < 0 || min.compareTo(nodo.getClave()) == 0)
                    && (max.compareTo(nodo.getClave()) > 0 || max.compareTo(nodo.getClave()) == 0)) {
                // min menor q nodo y max mayor q nodo
                listarRangoAux(nodo.getIzquierdo(), min, max, cl);
                cl.poner(nodo.getDato());
                listarRangoAux(nodo.getDerecho(), min, max, cl);
            } else {
                if (min.compareTo(nodo.getClave()) > 0 ) {
                    // el maximo es menor q la raiz
                    listarRangoAux(nodo.getDerecho(), min, max, cl);

                } else {
                    if (max.compareTo(nodo.getClave()) < 0){
                        // el minimo es mayor q la raiz
                        listarRangoAux(nodo.getIzquierdo(), min, max, cl);
                    }
                }
            }
        }
    }
}
