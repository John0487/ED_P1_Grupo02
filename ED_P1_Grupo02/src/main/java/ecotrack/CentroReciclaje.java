package ecotrack;

import java.util.LinkedList;
import java.util.Deque;

/**
 *
 * @author Grupo02
 */

public class CentroReciclaje {
    private Deque<Residuo> bahiaDeProcesamiento; 

    public CentroReciclaje() {
        this.bahiaDeProcesamiento = new LinkedList<>();
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
}
