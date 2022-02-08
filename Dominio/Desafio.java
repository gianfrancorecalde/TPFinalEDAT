package TPFinalEDAT.Dominio;

public class Desafio {
    
    /* Atributos */
    private String nom;
    private int ptje;
    private String tipo;

    /* Constructor */
    public Desafio(String nombre, int ptje, String tipo){
        this.nom = nombre;
        this.ptje = ptje;
        this.tipo = tipo;
    }

    public Desafio(int ptje){
        this.nom = "";
        this.ptje = ptje;
        this.tipo = "";
    }

    /* Visualizadores */
    public String getNom() {
        return nom;
    }

    public int getPtje() {
        return ptje;
    }

    public String getTipo() {
        return tipo;
    }

    /* Modificadores */
    public void setNombre(String nombre) {
        this.nom = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString(){
        return this.ptje+"";
    }
}
