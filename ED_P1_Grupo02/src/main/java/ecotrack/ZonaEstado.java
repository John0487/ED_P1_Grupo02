package ecotrack;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ZonaEstado {
    
    private final SimpleStringProperty zona;
    private final SimpleStringProperty recolectado;
    private final SimpleStringProperty pendiente;
    private final SimpleDoubleProperty utilidad;

    public ZonaEstado(String zona, String recolectado, String pendiente, double utilidad) {
        this.zona = new SimpleStringProperty(zona);
        this.recolectado = new SimpleStringProperty(recolectado);
        this.pendiente = new SimpleStringProperty(pendiente);
        this.utilidad = new SimpleDoubleProperty(utilidad);
    }

    public String getZona() { return zona.get(); }
    public String getRecolectado() { return recolectado.get(); }
    public String getPendiente() { return pendiente.get(); }
    public double getUtilidad() { return utilidad.get(); }
}