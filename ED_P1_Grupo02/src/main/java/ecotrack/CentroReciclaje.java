package ecotrack;

import java.util.LinkedList;
import java.util.Deque;
import java.util.Map;     
import java.util.HashMap;

/**
 *
 * @author Grupo02
 */

public class CentroReciclaje {
    private Deque<Residuo> bahiaDeProcesamiento; 
    private Map<String, Object> estadisticasGlobales;

    public CentroReciclaje() {
        this.bahiaDeProcesamiento = new LinkedList<>();
        this.estadisticasGlobales = new HashMap<>();
    }

    public void recibirResiduo(Residuo r) {
        this.bahiaDeProcesamiento.push(r);
        System.out.println("Residuo " + r.getId() + " recibido en la bahía.");
    }
    
    public Residuo procesarSiguienteResiduo() {
        if (this.bahiaDeProcesamiento.isEmpty()) {
            System.out.println("Bahía de procesamiento vacía.");
            return null;
        }    
        Residuo residuoAProcesar = this.bahiaDeProcesamiento.pop();
        System.out.println("Procesando residuo: " + residuoAProcesar.getId());
        return residuoAProcesar;
    }

    public Deque<Residuo> getBahiaDeProcesamiento() {
        return bahiaDeProcesamiento;
    }

}
