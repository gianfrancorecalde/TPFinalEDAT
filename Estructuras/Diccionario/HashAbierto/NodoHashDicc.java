package Estructura.TpFinal.Estructuras.Diccionario.HashAbierto;

public class NodoHashDicc {

    /*  Atributo    */

    private Object clave;
    private Object dato;
    private NodoHashDicc enlace;

    /*  Constructor */

    public NodoHashDicc(Object clave, Object dato, NodoHashDicc enlace) {
        // inicializar las celdas con elemento null y en 0 para podes ingresar los nuevos elemento en la tabla hash cerrado
        this.clave = clave;
        this.dato = dato;
        this.enlace = enlace;
    }

    /* Visualizadores */
    
    public Object getClave() {
        return this.clave;
    }

    public Object getDato() {
        return this.dato;
    }

    public NodoHashDicc getEnlace() {
        return this.enlace;
    }
    
    /* Modificadores */

    public void setDato(Object otroDato) {
        this.dato = otroDato;
    }

    public void setEnlace(NodoHashDicc otroEnlace) {
        this.enlace = otroEnlace;
    }
}
