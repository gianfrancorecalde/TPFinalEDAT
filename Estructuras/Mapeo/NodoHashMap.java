package TPFinalEDAT.Estructuras.Mapeo;
import TPFinalEDAT.Estructuras.Lineales.*;

public class NodoHashMap {

    /*  Atributo    */

    private Object dominio;
    private Lista rango;
    private NodoHashMap enlace;

    /*  Constructor */

    public NodoHashMap(Object dominio, NodoHashMap enlace) {
        // inicializar las celdas con elemento null y en 0 para podes ingresar los nuevos elemento en la tabla hash cerrado
        this.dominio = dominio;
        this.rango = new Lista();
        this.enlace = enlace;
    }

    /* Visualizadores */
    
    public Object getDominio() {
        return this.dominio;
    }

    public Lista getRango() {
        return this.rango;
    }

    public NodoHashMap getEnlace() {
        return this.enlace;
    }
    
    /* Modificadores */

    public void setRango(Lista otroRango) {
        this.rango = otroRango;
    }

    public void setEnlace(NodoHashMap otroEnlace) {
        this.enlace = otroEnlace;
    }
}
