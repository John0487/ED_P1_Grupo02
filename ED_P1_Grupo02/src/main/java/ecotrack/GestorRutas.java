package ecotrack;

import java.util.PriorityQueue;
import java.util.Comparator;


/**
 *
 * @author Grupo02
 */

public class GestorRutas{
    private PriorityQueue<Zona> colaDeZonas;
    
    private final Comparator<Zona> criterio_volumen = (z1, z2) -> (int)(z2.getpPendiente() - z1.getpPendiente());
    
    private final Comparator<Zona> criterio_impacto = (z1, z2) -> (int) (z2.getUtilidad() - z1.getUtilidad());
    
    public GestorRutas() { 
        this.colaDeZonas = new PriorityQueue<>(criterio_volumen);
    }
    
    public void cambiarCriterioPrioridad(boolean porVolumen, Zona[] zonas) {
        Comparator<Zona> nuevoComparador;

        if (porVolumen) {
            nuevoComparador = criterio_volumen;
        } else {
            nuevoComparador = criterio_impacto;
        }

        PriorityQueue<Zona> nuevaCola = new PriorityQueue<>(nuevoComparador);
        
        for(Zona z: zonas){
            nuevaCola.offer(z);
        }
        this.colaDeZonas = nuevaCola;
        if (porVolumen) {
            System.out.println("Criterio cambiado a: Volumen");
        } else {
        System.out.println("Criterio cambiado a: Impacto Ambiental");
        }
    }
    
    public boolean agregarZona(Zona z){
        if (z==null){
            return false;
        }
        this.colaDeZonas.offer(z);
        return true;
    }
      
    public Zona despacharProximaRuta() {
        Zona zonaUrgente = this.colaDeZonas.poll(); 
        if (zonaUrgente != null) {
            System.out.println("Despachando vehículo a: " + zonaUrgente.getNombre());
        }
        return zonaUrgente;
    }
    
    public boolean hayZonasPendientes() {
        return !this.colaDeZonas.isEmpty();
    }

    public PriorityQueue<Zona> getColaDeZonas() {
        return colaDeZonas;
    }
    
    public boolean isEmpty(){
        return this.colaDeZonas.isEmpty();
    }
    
    
}
