package ecotrack;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Grupo 02
 */

public class Residuo implements Serializable {
    private String id;
    private String nombre;
    private TipoResiduo tipo;
    private double peso;
    private LocalDate fechaRecoleccion;
    private Zona zona;
    private int nivelPrioridad;

    public Residuo(String id, String nombre, TipoResiduo tipo, double peso, LocalDate fechaRecoleccion, Zona zona, int nivelPrioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
        this.fechaRecoleccion = fechaRecoleccion;
        this.zona = zona;
        this.nivelPrioridad = nivelPrioridad;
    }
    
    //Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoResiduo getTipo() {
        return tipo;
    }

    public void setTipo(TipoResiduo tipo) {
        this.tipo = tipo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaRecoleccion() {
        return fechaRecoleccion;
    }

    public void setFechaRecoleccion(LocalDate fechaRecoleccion) {
        this.fechaRecoleccion = fechaRecoleccion;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public int getNivelPrioridad() {
        return nivelPrioridad;
    }

    public void setNivelPrioridad(int nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
    }
    
    public String toString() {
        return "ID: " + getId() + "\n" + "Tipo: " + getTipo() + "\n" + "Peso: " + getPeso() + " kg\n" + "Prioridad: " + getNivelPrioridad();
    }
    
    
}
