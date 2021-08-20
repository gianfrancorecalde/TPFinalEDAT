package Estructura.TpFinal.Dominio;

public class Habitacion {

    /* Atributos */
    private int cod; 
    private String nombre; 
    private int planta; 
    private double m2;
    private boolean salidaExt;

    /* Constructor */
    public Habitacion(int cod, String nombre, int planta, double m2, boolean salidaExt){
        this.cod = cod;
        this.nombre = nombre;
        this.planta = planta;
        this.m2 = m2;
        this.salidaExt = salidaExt;
    }

    public Habitacion(int cod){
        this.cod = cod;
        this.nombre = null;
        this.planta = 0;
        this.m2 = 0;
        this.salidaExt = false;
    }

    /* Visualizadores */
    public int getCod(){
        return this.cod;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlanta() {
        return planta;
    }

    public boolean getSalidaExt() {
        return salidaExt;
    }

    public double getM2() {
        return m2;
    }

    /* Modificadores */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public void setM2(double m2) {
        this.m2 = m2;
    }

    public void setSalidaExt(Boolean salidaExt) {
        this.salidaExt = salidaExt;
    }

    public String toString(){
        return this.cod+"";
    }
}
