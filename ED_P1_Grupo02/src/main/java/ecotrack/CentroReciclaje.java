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
        inicializarEstadisticas();
    }
    
    private void inicializarEstadisticas() {
        this.estadisticasGlobales.put("pesoTotal", 0.0);
        this.estadisticasGlobales.put("conteoPorTipo", new HashMap<String, Integer>());
        this.estadisticasGlobales.put("conteoPorZona", new HashMap<String, Integer>());
    }
    
    public void actualizarEstadisticas(Residuo r) {
        System.out.println("Registrando estadísticas para residuo: " + r.getId());
    }
    
    public Map<String, Object> getEstadisticasGlobales() {
        return this.estadisticasGlobales;
    }
    
    public void imprimirEstadisticas() {
        System.out.println("Estadísticas Globales del Centro");
        System.out.println("Peso Total Procesado: " + this.estadisticasGlobales.get("pesoTotal"));
        System.out.println("Conteo por Tipo: " + this.estadisticasGlobales.get("conteoPorTipo").toString());
        System.out.println("Conteo por Zona: " + this.estadisticasGlobales.get("conteoPorZona").toString());
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
