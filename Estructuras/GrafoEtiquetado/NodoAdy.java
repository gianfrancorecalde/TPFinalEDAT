package TPFinalEDAT.Estructuras.GrafoEtiquetado;

public class NodoAdy {
    
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private Object etiqueta;  

    public NodoAdy(NodoVert vert, NodoAdy ady, Object etiqueta){
        this.vertice = vert;
        this.sigAdyacente = ady;
        this.etiqueta = etiqueta;
    }

    public NodoVert getVertice(){
        return this.vertice;        
    }
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }

    public void setVertice(NodoVert newVertice){
        this.vertice = newVertice;
    }
    public void setSigAdyacente(NodoAdy newAdyacente){
        this.sigAdyacente = newAdyacente;
    }

    public Object getEtiq(){
        return this.etiqueta;
    }

    public void setEtiq(Object etiq){
        this.etiqueta = etiq;
    }
}
