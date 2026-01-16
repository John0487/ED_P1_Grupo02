
package ecotrack;

import javafx.scene.control.Alert;

/**
 *
 * @author Grupo02
 */

public class Alerta {
    public static void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
