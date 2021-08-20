package Estructura.TpFinal.Dominio;

public class Equipo {
    
    /* Atributo */
    private String nom;
    private int ptjeExigido;
    private int ptjeTotal;
    private int hab;
    private int ptjeActual;

    /* Constructor */
    public Equipo(String nombre, int ptjeExigido){
        this.nom = nombre;
        this.ptjeExigido = ptjeExigido;
        this.ptjeTotal = 0;
        this.ptjeActual = 0;
        this.hab = -1;
    }

    public Equipo(String nombre){
        this.nom = nombre;
        this.ptjeExigido =0;
        this.ptjeTotal = 0;
        this.ptjeActual = 0;
        this.hab = -1;
    }

    /* Visualizadores */

    public String getNom() {
        return nom;
    }

    public int getPtjeExigido() {
        return ptjeExigido;
    }

    public int getHab() {
        return hab;
    }

    public int getPtjeTotal() {
        return ptjeTotal;
    }

    public int getPtjeActual(){
        return this.ptjeActual;
    }

    /* Modificadores */

    public void setPtjeExigido(int ptjeExigido) {
        this.ptjeExigido = ptjeExigido;
    }

    public void setPtjeTotal(int ptjeTotal) {
        this.ptjeTotal = ptjeTotal;
    }

    public void setHab(int hab) {
        this.hab = hab;
    }
    
    public void setPtjeActual(int ptjeActual){
        this.ptjeActual = ptjeActual;
    }

    public String toString(){
        return this.nom;
    }
}
