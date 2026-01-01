package ecotrack;

/**
 *
 * @author Grupo 02
 */
public class Zona {
    private String nombre;
    private GestorResiduos residuos;
    private double pRecolectado;
    private double pPendiente;

    public Zona(String nombre,GestorResiduos residuos, double pRecolectado, double pPendiente) {
        this.nombre = nombre;
        this.residuos = residuos;
        this.pRecolectado = pRecolectado;
        this.pPendiente = pPendiente;
    }
    
    public void registrarResiduo(Residuo r) {
        this.residuos.agregarResiduo(r);
        System.out.println("Residuo " + r.getId() + " registrado.");
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

    public GestorResiduos getResiduos() {
        return residuos;
    }

    public void setResiduos(GestorResiduos residuos) {
        this.residuos = residuos;
    }
    
    
    
}
