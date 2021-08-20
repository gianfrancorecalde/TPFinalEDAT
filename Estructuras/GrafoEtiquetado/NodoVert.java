package Estructura.TpFinal.Estructuras.GrafoEtiquetado;

public class NodoVert {
    
    private Object elemento;
    private NodoVert sigVert;
    private NodoAdy primerAdy;

    public NodoVert(Object elem, NodoVert vert, NodoAdy ady){
        this.elemento = elem;
        this.sigVert = vert;
        this.primerAdy = ady;
    }

    public Object getElem(){
        return this.elemento;
    }
    public NodoVert getSigVert(){
        return this.sigVert;
    }
    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }

    public void setElem(Object newElem){
        this.elemento = newElem;
    }
    public void setSigVert(NodoVert newVert){
        this.sigVert = newVert;
    }
    public void setPrimerAdy(NodoAdy newAdy){
        this.primerAdy = newAdy;
    }
}
