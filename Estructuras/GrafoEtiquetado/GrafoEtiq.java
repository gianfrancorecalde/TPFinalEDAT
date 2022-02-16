package TPFinalEDAT.Estructuras.GrafoEtiquetado;

import TPFinalEDAT.Estructuras.Lineales.*;

public class GrafoEtiq {
    private NodoVert inicio;

    /* Constructor */
    public GrafoEtiq() {
        this.inicio = null;
    }

    public boolean esVacio() {
        return this.inicio == null;
    }

    public void vaciar() {
        this.inicio = null;
    }

    /* Metodo para ubicar vertice */
    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        if (!this.esVacio()) {
            while (aux != null && !aux.getElem().equals(buscado)) {
                aux = aux.getSigVert();
            }
        }
        return aux;
    }

    public Object modificar(Object buscado) {
        NodoVert aux = this.inicio;
        Object auxElemento = null;
        while (!aux.getElem().equals(buscado) && aux != null) {
            aux = aux.getSigVert();
        }
        if (aux != null) {
            auxElemento = aux.getElem();
        }
        return auxElemento;
    }

    /*
     * Metodos para insertar y eliminar vertices junto con los arcos (uso recorrido
     * en profundida)
     */

    public boolean insertarVertice(Object newVert) {
        boolean exito = false;
        NodoVert aux = this.inicio;
        while (!aux.getElem().equals(newVert) && aux != null) {
            aux = aux.getSigVert();
        }
        if (aux == null) {
            this.inicio = new NodoVert(newVert, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    public boolean eliminarVertice(Object buscado) {
        boolean exito = false;
        Lista visitado = new Lista();
        if (!this.esVacio()) {
            NodoVert auxVert = this.inicio, antVert = auxVert;
            while (auxVert != null) {
                if (auxVert.getElem().equals(buscado)) {
                    // elimino el vertice
                    antVert.setSigVert(auxVert.getSigVert());
                    auxVert = antVert;
                    exito = true;
                } else {
                    if (visitado.localizar(auxVert.getElem()) < 0) {
                        eliminarArcosEnProfundidad(auxVert, visitado, buscado);
                    }
                }
                antVert = auxVert;
                auxVert = auxVert.getSigVert();
            }
        }
        return exito;
    }

    private void eliminarArcosEnProfundidad(NodoVert v, Lista vis, Object buscado) {
        if (v != null) {
            // marca al vertice v como visitado
            vis.insertar(v.getElem(), vis.longitud() + 1);
            NodoAdy ady = v.getPrimerAdy(), antAdy = ady;
            while (ady != null) {
                // visita en profundidad los adyacentes de v aun no visitados
                if (ady.getVertice().getElem().equals(buscado)) {
                    // elimino los arcos al vertice ady o nodo que contienen la refenrecia al
                    // vertice que se quiere eliminar
                    antAdy.setSigAdyacente(ady.getSigAdyacente());
                    ady = antAdy;
                } else {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        eliminarArcosEnProfundidad(ady.getVertice(), vis, buscado);
                    }
                }
                antAdy = ady;
                ady = ady.getSigAdyacente();
            }
        }
    }

    /* Metodo para verificar la existencia de un vertice */

    public boolean existeVertice(Object buscado) {
        boolean exito = false;
        NodoVert aux = this.inicio;
        while (!aux.getElem().equals(buscado) && aux != null) {
            aux = aux.getSigVert();
        }
        if (aux != null) {
            exito = true;
        }
        return exito;
    }

    /* Metodos para insertar y eliminar arcos (Grafo simple etiquetado) */

    public boolean insertarArco(Object origen, Object destino, Object etiq) {
        boolean exito = true;
        NodoVert auxBusqueda = this.inicio, vertD = null, vertO = null;
        NodoAdy ady;
        while ((vertO == null || vertD == null) && auxBusqueda != null) {
            //Ubico vertices
            if (auxBusqueda.getElem().equals(origen)) {
                vertO = auxBusqueda;
            }
            if (auxBusqueda.getElem().equals(destino)) {
                vertD = auxBusqueda;
            }
            auxBusqueda = auxBusqueda.getSigVert();
        }
        if (vertO == null && vertD == null) {
            exito = false;
        } else {
            ady = vertO.getPrimerAdy();
            while (exito && ady != null) {
                if (ady.getVertice().getElem().equals(destino)) {
                    exito = false;
                }
                ady = ady.getSigAdyacente();
            }
            if (exito) {
                // seteo el enlace del vertice origen con un nuevo ady que tendrá como vertice
                // al destino
                // y como ady al primer vertice ady del vertice origen.
                vertO.setPrimerAdy(new NodoAdy(vertD, vertO.getPrimerAdy(), etiq));
                vertD.setPrimerAdy(new NodoAdy(vertO, vertD.getPrimerAdy(), etiq));
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino, Object etiqueta) {
        // elimino los arcos con la etiqueta pasa por paramentro, y que vayan del
        // vertice origen al destino y visceversa.
        boolean exito = false;
        NodoVert vertO = this.ubicarVertice(origen);
        NodoAdy ady, adyAnterior;
        if (vertO != null) {
            ady = vertO.getPrimerAdy();
            adyAnterior = ady;
            while (ady != null && !exito) {
                // recorro los arcos del vertice origen hasta encontrar el buscado
                if (ady.getVertice().getElem().equals(destino)) {
                    if (ady.getEtiq().equals(etiqueta)) {
                        // verifico que el arco tenga la etiqueta buscada
                        adyAnterior.setSigAdyacente(ady.getSigAdyacente()); // elimino el arco
                        exito = true;
                        ady = ady.getVertice().getPrimerAdy(); // voy hasta el primer arco del vertice destino
                    }
                } else {
                    adyAnterior = ady;
                    ady = ady.getSigAdyacente();
                }
            }
            if (exito) {
                exito = false;
                adyAnterior = ady;
                while (ady != null && !exito) {
                    // recorro los arcos del vertice destino hasta encontrar el buscado
                    if (ady.getVertice().getElem().equals(origen)) {
                        if (ady.getEtiq().equals(etiqueta)) {
                            adyAnterior.setSigAdyacente(ady.getSigAdyacente()); // elimino el arco
                            exito = true;
                        }
                    }
                    adyAnterior = ady;
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean existeArco(Object origen, Object destino, Object etiqueta) {
        // verifica la existencia de los arcos que van de vertice origen a vertice
        // destino y visceversa; y que sea etiquetado segun el valor de paramatro
        boolean existeArcoIda = false, existeArcoVuelta = false;
        ;
        NodoVert vertO = this.inicio;
        NodoAdy ady;
        while (!vertO.getElem().equals(origen) && vertO != null) {
            vertO = vertO.getSigVert();
        }
        if (vertO != null) {
            ady = vertO.getPrimerAdy();
            while (!existeArcoIda && ady != null) {
                // recorro arcos del vertice origen
                if (ady.getVertice().getElem().equals(destino) && ady.getEtiq().equals(etiqueta)) {
                    // verifica que sea el buscado
                    // verifica la etiqueta buscada
                    ady = ady.getVertice().getPrimerAdy();
                    existeArcoIda = true;
                } else {
                    ady = ady.getSigAdyacente();
                }

            }
            if (existeArcoIda) {
                while (ady != null && !existeArcoVuelta) {
                    // recorro arcos del vertice destino
                    if (ady.getVertice().getElem().equals(origen) && ady.getEtiq().equals(etiqueta)) {
                        existeArcoVuelta = true;
                    } else {
                        ady = ady.getSigAdyacente();
                    }
                }
            }
        }
        return existeArcoVuelta;
    }

    /* metodo de vertices contiguos */

    public Lista verticesContiguos(Object v) {
        // Devuelve un listado con los vertices contiguos y las etiquietas de los arcos
        Lista vertContiguos = new Lista();
        NodoVert vertO = this.inicio;
        while (!vertO.getElem().equals(v) && vertO != null) {
            vertO = vertO.getSigVert();
        }
        if (vertO != null) {
            NodoAdy ady = vertO.getPrimerAdy();
            while (ady != null) {
                Object[] arr = { ady.getVertice().getElem(), ady.getEtiq() };
                vertContiguos.insertar(arr, vertContiguos.longitud() + 1);
                ady = ady.getSigAdyacente();
            }
        }
        return vertContiguos;
    }

    public Object valorDelArco(Object v1, Object v2) {
        // Devuelve la etiqueta del arco entre vertice 1 y vercie 2
        Object valor = null;
        NodoVert vertO = this.inicio;
        while (!vertO.getElem().equals(v1) && vertO != null) {
            // Ubico vertice
            vertO = vertO.getSigVert();
        }
        if (vertO != null) {
            NodoAdy ady = vertO.getPrimerAdy();
            while (ady != null) {
                if (ady.getVertice().equals(v2)) {
                    valor = ady.getEtiq();
                }
                ady = ady.getSigAdyacente();
            }
        }
        return valor;
    }

    /* metodo exitencia de camino en un grafo simple */

    public boolean existeCaminoConPesoMenorA(Object origen, Object destino, int k) {
        boolean existe = false;
        Lista aux = new Lista();
        NodoVert vertInicio = this.inicio;
        while (!vertInicio.getElem().equals(origen) && vertInicio != null) {
            //Ubico vertice
            vertInicio = vertInicio.getSigVert();
        }
        if (vertInicio != null) {
            existe = caminoEnProfundidad(vertInicio, aux, destino, k);
        }
        return existe;
    }

    private boolean caminoEnProfundidad(NodoVert v, Lista vis, Object destino, int k) {
        boolean exito = false;
        if (v != null && k >= 0) {
            vis.insertar(v.getElem(), vis.longitud() + 1);
            // System.out.println(vis.toString() + " "+ k);
            if (v.getElem().equals(destino)) {
                exito = true;
            } else {
                NodoAdy ady = v.getPrimerAdy();
                while (ady != null && !exito) {
                    // visita en profundidad los adyacentes de v aun no visitados
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        // Si v no esta en la lista de visitado, lo visita
                        exito = caminoEnProfundidad(ady.getVertice(), vis, destino, k - (int) ady.getEtiq());
                    }
                    ady = ady.getSigAdyacente();
                }
                if (!exito) {
                    // Elimina el vertice por el cual no existe camino al vrertice destino
                    vis.eliminar(vis.longitud());
                }
            }
        }
        return exito;
    }

    /* metodo que devuelve una lista de caminos sin pasar por un vertice en especifico y sin superar una cantidad */

    public Lista caminosSinPasarPorVerticeInhabilitado(Object origen, Object destino, Object vertInhabilitado, int k) {
        Lista aux = new Lista();
        Lista caminos = new Lista();
        if (!origen.equals(vertInhabilitado) && !destino.equals(vertInhabilitado)) {
            NodoVert vertInicio = this.inicio;
            while (!vertInicio.getElem().equals(origen)) {
                //Ubico vertice
                vertInicio = vertInicio.getSigVert();
            }
            if (vertInicio != null) {
                caminosEnProfundidad(vertInicio, caminos, aux, destino, vertInhabilitado, k);
            }
        }
        return caminos;
    }

    private void caminosEnProfundidad(NodoVert v, Lista caminos, Lista aux, Object destino, Object vertInhabilitado,int k) {
        if (v != null && k >= 0) {
            aux.insertar(v.getElem(), aux.longitud() + 1);
            if (v.getElem().equals(destino)) {
                caminos.insertar(aux.clone(), caminos.longitud() + 1);
            } else {
                aux.insertar(v.getElem(), aux.longitud() + 1);
                NodoAdy ady = v.getPrimerAdy();
                while (ady != null) {
                    // visita en profundidad los adyacentes de v aun no visitados
                    if (!ady.getVertice().getElem().equals(vertInhabilitado)) {
                        if (aux.localizar(ady.getVertice().getElem()) < 0) {
                            caminosEnProfundidad(ady.getVertice(), caminos, aux, destino, vertInhabilitado,k - (int) ady.getEtiq());
                            aux.eliminar(aux.longitud());
                        }
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
    }

    /* Metodo toString */

    public String toString() {
        String cadena = "";
        if (!this.esVacio()) {
            NodoVert auxV = this.inicio;
            NodoAdy auxAdy;
            while (auxV != null) {
                cadena += "Vertice: " + auxV.getElem().toString() + " --> ";
                auxAdy = auxV.getPrimerAdy();
                while (auxAdy != null) {
                    cadena += auxAdy.getEtiq() + "/" + auxAdy.getVertice().getElem().toString() + " --> ";
                    auxAdy = auxAdy.getSigAdyacente();
                }
                cadena += "\n";
                auxV = auxV.getSigVert();
            }
        } else {
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
