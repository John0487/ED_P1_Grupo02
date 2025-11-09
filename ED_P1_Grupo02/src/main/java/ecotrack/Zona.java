package ecotrack;

/**
 *
 * @author Grupo 02
 */
public class Zona {
    private String nombre;
    private double pRecolectado;
    private double pPendiente;

    public Zona(String nombre, double pRecolectado, double pPendiente) {
        this.nombre = nombre;
        this.pRecolectado = pRecolectado;
        this.pPendiente = pPendiente;
    }
    
    //Función de Utilidad
    public double getUtilidad(){
        return pRecolectado - pPendiente;
    }
    
    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getpRecolectado() {
        return pRecolectado;
    }

    public void setpRecolectado(double pRecolectado) {
        this.pRecolectado = pRecolectado;
    }

    public double getpPendiente() {
        return pPendiente;
    }

    public void setpPendiente(double pPendiente) {
        this.pPendiente = pPendiente;
    }
    
    
}
