package Estructura.TpFinal.Estructuras.Diccionario.AVL;

public class NodoAVLDicc {
    
    // Atributos
    private Comparable clave;
    private Object dato;
    private NodoAVLDicc izquierdo;    // Guarda el nodo hijo izquierdo
    private NodoAVLDicc derecho;      // Guarda el nodo hijo derecho
    private int altura;

    // Construtor 
    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc hijoIzquierdo, NodoAVLDicc hijoDerecho){

        this.clave = clave;
        this.dato = dato;
        this.izquierdo = hijoIzquierdo;
        this.derecho = hijoDerecho;
        this.altura = 0;
    }

    // Métodos Gets
    public Comparable getClave(){
        return this.clave;
    }
    public Object getDato(){
        return this.dato;
    }
    public NodoAVLDicc getIzquierdo(){
        return this.izquierdo;
    }
    public NodoAVLDicc getDerecho(){
        return this.derecho;
    }
    public int getAltura(){
        return this.altura;
    }

    // Métodos Sets
    public void setDato(Object otroDato){
        this.dato = otroDato;
    }
    public void setIzquierdo(NodoAVLDicc otroHijoIzquierdo){
        this.izquierdo = otroHijoIzquierdo;
    }
    public void setDerecho(NodoAVLDicc otroHijoDerecho){
        this.derecho = otroHijoDerecho;
    }
    public void recalcularAltura(){

        if(this.izquierdo != null && this.derecho != null){
            if(this.izquierdo.getAltura() > this.derecho.getAltura()){
                this.altura = this.izquierdo.getAltura()+1;
            }else{
                this.altura = this.derecho.getAltura() +1;
            }
        }else{
            if(this.izquierdo != null && this.derecho == null){
                this.altura = this.izquierdo.getAltura() +1;
            }else{
                if(this.izquierdo == null && this.derecho != null){
                    this.altura = this.derecho.getAltura() +1;
                }else{
                    if(this.izquierdo == null && this.derecho == null){
                        this.altura = 0;
                    }
                }
            }
        }
    }

    public void recalcularMejora(){
        int aux1 = -1, aux2 = -1;
        if(this.izquierdo != null){
            aux1 = this.izquierdo.altura;
        }
        if(this.derecho != null){
            aux2 = this.derecho.altura;
        }
        this.altura = Math.max(aux1, aux2) +1;
    }
}
