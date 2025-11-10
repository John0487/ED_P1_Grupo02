package ecotrack;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 *
 * @author Grupo02
 */

public class GestorRutas implements Runnable {
    private PriorityQueue<Zona> colaDeZonas;
    
    public GestorRutas() {
        Comparator<Zona> comparadorUtilidad = (z1, z2) -> Double.compare(z1.getUtilidad(), z2.getUtilidad());   
        this.colaDeZonas = new PriorityQueue<>(comparadorUtilidad);
    }
    
    public void actualizarPrioridadDeZona(Zona z) {
        this.colaDeZonas.remove(z); 
        this.colaDeZonas.add(z);
        System.out.println("Zona " + z.getNombre() + " añadida a la cola con utilidad: " + z.getUtilidad());
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

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
