package TPFinalEDAT.Estructuras.GrafoEtiquetado;


import TPFinalEDAT.Estructuras.Lineales.*;

public class GrafoEtiq {
    private NodoVert inicio;

    /* Constructor */
    public GrafoEtiq(){
        this.inicio = null;
    }

    public boolean esVacio(){
        return this.inicio == null;
    }

    public void vaciar(){
        this.inicio = null;
    }

    /* Metodo para ubicar vertice */
    private NodoVert ubicarVertice(Object buscado){
        NodoVert aux = this.inicio;
        if(!this.esVacio()){
            while(aux != null && !aux.getElem().equals(buscado)){
                aux = aux.getSigVert();
            }
        }
        return aux;
    }

    public Object modificar(Object buscado){
        return ubicarVertice(buscado).getElem();  
    }

    /* Metodos para insertar y eliminar vertices junto con los arcos (uso recorrido en profundida) */

    public boolean insertarVertice(Object newVert){
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(newVert);
        if(aux == null){
            this.inicio = new NodoVert(newVert,this.inicio,null);
            exito = true;
        }
        return exito;
    }

    public boolean eliminarVertice(Object buscado){
        boolean exito = false;
        Lista visitado = new Lista();
        if(!this.esVacio()){
            NodoVert auxVert = this.inicio, antVert = auxVert;
            while(auxVert != null){
                if(auxVert.getElem().equals(buscado)){
                    // elimino el vertice
                    antVert.setSigVert(auxVert.getSigVert());
                    auxVert = antVert;
                    exito = true;
                }else{
                    if(visitado.localizar(auxVert.getElem()) <0){
                        eliminarArcosEnProfundidad(auxVert, visitado, buscado);
                    }
                }
                antVert = auxVert;
                auxVert = auxVert.getSigVert();
            }
        }        
        return exito;
    }

    private void eliminarArcosEnProfundidad(NodoVert v, Lista vis, Object buscado){
        if(v != null){
            // marca al vertice v como visitado
            vis.insertar(v.getElem(), vis.longitud()+1);
            NodoAdy ady = v.getPrimerAdy(), antAdy = ady;
            while(ady != null){
                // visita en profundidad los adyacentes de v aun no visitados
                if(ady.getVertice().getElem().equals(buscado)){
                    // elimino los arcos al vertice adyacente o nodo que contienen la refenrecia al vertice que se quiere eliminar
                    antAdy.setSigAdyacente(ady.getSigAdyacente());
                    ady = antAdy;
                }else{
                    if(vis.localizar(ady.getVertice().getElem())<0){
                        eliminarArcosEnProfundidad(ady.getVertice(), vis, buscado);
                    }
                }
                antAdy = ady;
                ady = ady.getSigAdyacente();
            }
        }
    }

    /* Metodo para verificar la existencia de un vertice */

    public boolean existeVertice(Object buscado){
        boolean exito = false;
        if(this.ubicarVertice(buscado) != null){
            exito = true;
        }
        return exito;
    }

    /* Metodos para insertar y eliminar arcos (Grafo simple etiquetado)*/


    public boolean insertarArco(Object origen, Object destino, Object etiq){
        // Genero multigrafos, multidigrafos, digrafos y grafos simples.
        boolean exito = true;
        NodoVert auxD, auxO = this.ubicarVertice(origen);
        NodoAdy ady;
        if(auxO != null){
            // vertice origen existe
            auxD = this.ubicarVertice(destino);
            if(auxD != null){
                // vertice destino existe
                ady = auxO.getPrimerAdy();
                while(exito && ady != null){
                    if(ady.getVertice().getElem().equals(destino)){
                        exito = false;
                    }
                    ady = ady.getSigAdyacente();
                }
                if(exito){
                    // seteo el enlace del vertice origen con un nuevo adyacente que tendrá como vertice al destino y como adyacente al primer vertice adyacente del vertice origen.
                    auxO.setPrimerAdy(new NodoAdy(auxD, auxO.getPrimerAdy(), etiq));
                    auxD.setPrimerAdy(new NodoAdy(auxO, auxD.getPrimerAdy(), etiq));
                }
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino, Object etiqueta){
        // elimino los arcos con la etiqueta pasa por paramentro, y que vayan del vertice origen al destino y visceversa. 
        boolean exito = false;
        NodoVert auxO = this.ubicarVertice(origen);
        NodoAdy adyacente, adyAnterior;
        if(auxO != null){
            adyacente = auxO.getPrimerAdy();
            adyAnterior = adyacente;
            while(adyacente != null && !exito){
                // recorro los arcos del vertice origen hasta encontrar el buscado
                if(adyacente.getVertice().getElem().equals(destino)){
                    if(adyacente.getEtiq().equals(etiqueta)){
                        // verifico que el arco tenga la etiqueta buscada
                        adyAnterior.setSigAdyacente(adyacente.getSigAdyacente()); // elimino el arco
                         exito = true;
                        adyacente = adyacente.getVertice().getPrimerAdy(); // voy hasta el primer arco del vertice destino
                    }
                }else{
                    adyAnterior=adyacente;
                    adyacente = adyacente.getSigAdyacente();
                }
            }
            if(exito){
                exito = false;
                adyAnterior = adyacente;
                while(adyacente != null && !exito){
                    // recorro los arcos del vertice destino hasta encontrar el buscado
                    if(adyacente.getVertice().getElem().equals(origen)){
                        if(adyacente.getEtiq().equals(etiqueta)){
                            adyAnterior.setSigAdyacente(adyacente.getSigAdyacente()); // elimino el arco 
                            exito = true;
                        }
                    }
                    adyAnterior=adyacente;
                    adyacente = adyacente.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean existeArco(Object origen , Object destino, Object etiqueta){
        // verifica la existencia de los arcos que van de vertice origen a vertice destino y visceversa; y que sea etiquetado segun el valor de paramatro 
        boolean exito = false;
        NodoVert auxO = this.ubicarVertice(origen);
        NodoAdy adyacente;
        if(auxO != null){
            adyacente = auxO.getPrimerAdy();         
            while(adyacente != null && !exito){
                // recorro arcos del vertice origen 
                if(adyacente.getVertice().getElem().equals(destino)){
                    // verifica que sea el buscado
                    if(adyacente.getEtiq().equals(etiqueta)){
                        //verifica la etiqueta buscada
                        adyacente = adyacente.getVertice().getPrimerAdy();
                        exito = true;
                    }
                }else{
                    adyacente = adyacente.getSigAdyacente();
                }

            }
            if(exito){
                exito = false;
                while(adyacente != null && !exito){
                    // recorro arcos del vertice destino
                    if(adyacente.getVertice().getElem().equals(origen)){
                        if(adyacente.getEtiq().equals(etiqueta)){
                            exito = true;
                        }
                    }else{
                        adyacente = adyacente.getSigAdyacente();
                    }
                }
            }
        }        
        return exito;
    }

    /* metodo de vertices contiguos */

    public Lista verticesContiguos(Object v){
        //Devuelve un listado con los vertices contiguos y las etiquietas de los arcos
        Lista vertContiguos = new Lista();
        NodoVert auxO = this.ubicarVertice(v);
        NodoAdy ady = auxO.getPrimerAdy();
        if(auxO != null){
            while(ady != null){
                vertContiguos.insertar(ady.getVertice().getElem(),vertContiguos.longitud()+1);
                vertContiguos.insertar(ady.getEtiq(),vertContiguos.longitud()+1);
                ady = ady.getSigAdyacente();
            }
        }
        return vertContiguos;
    }

    public Object valorDelArco(Object v1 , Object v2){
        Object valor = null;
        NodoVert auxO = this.ubicarVertice(v1);
        NodoAdy ady = auxO.getPrimerAdy();
        if(auxO != null){
            while(ady != null){
                if(ady.getVertice().equals(v2)){
                    valor = ady.getEtiq(); 
                }
                ady = ady.getSigAdyacente();
            }
        }
        return valor;
    }

    /* metodo exitencia de camino en un grafo simple */

    public boolean existeCaminoConPesoMenorA(Object origen, Object destino, int k){
        boolean existe = false;
        Lista aux = new Lista();
        NodoVert vertInicio = ubicarVertice(origen);
        if(vertInicio != null){
            existe = caminoEnProfundidad(vertInicio, aux, destino, k);
        }
        return existe; 
    }

    private boolean caminoEnProfundidad(NodoVert v, Lista vis, Object destino, int total){
        boolean exito = false;
        if(v != null){
            vis.insertar(v.getElem(), vis.longitud()+1);
            //System.out.println(vis.toString() + " "+ total);
            if(v.getElem().equals(destino) && total >= 0){
                exito = true;
            }else{
                if (total < 0) {
                    //Elimina el vertice que supero el limite fijado
                    vis.eliminar(vis.longitud());
                } else {
                    NodoAdy ady = v.getPrimerAdy();
                    while(ady != null && !exito){
                    // visita en profundidad los adyacentes de v aun no visitados
                        if(vis.localizar(ady.getVertice().getElem())<0){
                            // Si v no esta en la lista de visitado, lo visita
                            exito =  caminoEnProfundidad(ady.getVertice(), vis, destino, total-(int)ady.getEtiq());
                        }
                        ady = ady.getSigAdyacente();
                    } 
                    if (!exito) {
                        // Elimina el vertice por el cual no existe camino al vrertice destino
                        vis.eliminar(vis.longitud());
                    }
                }    
            }
        }
        return exito;
    }

    /* metodo que devuelve una cola de caminos sin pasar por un vertice en especifico y sin superar una cantidad */

    public Lista caminosSinPasarPorVerticeInhabilitado(Object origen, Object destino, Object vertInhabilitado, int k){
        Lista aux = new Lista();
        Lista caminos = new Lista();
        NodoVert vertInicio = ubicarVertice(origen);
        if(vertInicio != null && ubicarVertice(destino) != null && !vertInicio.getElem().equals(vertInhabilitado)){
            caminosEnProfundidad(vertInicio, caminos, aux, destino, vertInhabilitado, k);
        }
        return caminos;
    }

    private void caminosEnProfundidad(NodoVert v, Lista caminos, Lista aux, Object destino, Object vertInhabilitado, int k){
        if(v != null && k >= 0){
            if(v.getElem().equals(destino)){
                aux.insertar(destino,aux.longitud()+1);
                caminos.insertar(aux.clone(), 1);
            }else{
                aux.insertar(v.getElem(), aux.longitud()+1);
                NodoAdy ady = v.getPrimerAdy();
                while(ady != null){
                // visita en profundidad los adyacentes de v aun no visitados
                    if(!ady.getVertice().getElem().equals(vertInhabilitado)){
                        if(aux.localizar(ady.getVertice().getElem()) <0){
                            caminosEnProfundidad(ady.getVertice(), caminos, aux, destino, vertInhabilitado, k-(int)ady.getEtiq());
                            aux.eliminar(aux.longitud());
                        }
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
    }

    /* Metodo toString */
    public String toString(){
        String cadena = "";
        if(!this.esVacio()){
            NodoVert auxV = this.inicio;
            NodoAdy auxAdy;
            while(auxV != null){
                cadena += "Vertice: " + auxV.getElem().toString() + " --> ";
                auxAdy = auxV.getPrimerAdy();
                while(auxAdy != null){
                    cadena += auxAdy.getEtiq()+"/"+auxAdy.getVertice().getElem().toString()+" --> ";
                    auxAdy = auxAdy.getSigAdyacente();
                }
                cadena += "\n";
                auxV = auxV.getSigVert();
            }
        }else{
            cadena += "No existen vertices";
        }
        return cadena;
    }

    public static void main(String[] args) {
        GrafoEtiq g = new GrafoEtiq();

        g.insertarVertice('A');
        g.insertarVertice('B');
        g.insertarVertice('C');
        g.insertarVertice('D');
        g.insertarVertice('E');
        g.insertarVertice('F');
        g.insertarVertice('G');

        g.insertarArco('A', 'B', 20);
        g.insertarArco('A', 'C', 20);
        g.insertarArco('A', 'D', 10);
        g.insertarArco('A', 'E', 10);
        g.insertarArco('B', 'F', 20);
        g.insertarArco('C', 'F', 10);
        g.insertarArco('D', 'C', 40);
        g.insertarArco('E', 'D', 20);
        g.insertarArco('D', 'G', 10);
        
        System.out.println(g.toString());
        g.existeCaminoConPesoMenorA('A', 'F', 50);
    }
    
}
