package ecotrack; 

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;


public class GestorPersistencia {

    private static final String NOMBRE_ARCHIVO = "ecotrack_estado.dat";

    private GestorPersistencia() {
    }

    public static void guardarEstado(Object datos) {
        
        try (FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(datos); 
            
            System.out.println("Estado del sistema guardado exitosamente en: " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar el estado: " + e.getMessage());
        }
    }

    public static Object cargarEstado() {
        Object datosCargados = null;
        try (FileInputStream fis = new FileInputStream(NOMBRE_ARCHIVO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            datosCargados = ois.readObject();           
            System.out.println("Estado del sistema cargado exitosamente desde: " + NOMBRE_ARCHIVO);
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró archivo de guardado. Se iniciará un sistema nuevo.");
        } catch (IOException e) {
            System.err.println("Error al cargar el estado (IOException): " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el estado (Clase no encontrada): " + e.getMessage());
        }
        return datosCargados;
    }
}