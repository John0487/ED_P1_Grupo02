package ecotrack;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Grupo02
 */
public class DialogCentroReciclaje extends Stage {
    private CentroReciclaje centro;
    private ListView<String> listaVista;

    public DialogCentroReciclaje(Stage owner, CentroReciclaje centro) {
        this.centro = centro;
        initOwner(owner);
        setTitle("Bahía de Procesamiento");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Residuos en espera: ");
        listaVista = new ListView<>();
        actualizarLista();

        Button btnProcesar = new Button("Procesar Siguiente Residuo");
        btnProcesar.setMaxWidth(Double.MAX_VALUE);
        btnProcesar.setOnAction(e -> {
            Residuo p = centro.procesarSiguienteResiduo();
            if (p != null) {
                Alerta.mostrarAlerta("Procesado", "Se procesó: " + p.getNombre(), Alert.AlertType.INFORMATION);
                actualizarLista();
            } else {
                Alerta.mostrarAlerta("Vacío", "No hay más residuos en la pila.", Alert.AlertType.WARNING);
            }
        });

        root.getChildren().addAll(titulo, listaVista, btnProcesar);
        
        setScene(new Scene(root, 400, 500));
    }

    private void actualizarLista() {
        listaVista.getItems().clear();
        if (centro.getBahiaDeProcesamiento().isEmpty()) {
            listaVista.getItems().add("No hay residuos pendientes.");
        } else {
            for (Residuo r : centro.getBahiaDeProcesamiento()) {
                listaVista.getItems().add("[" + r.getId() + "] " + r.getNombre() + " - " + r.getPeso() + "kg");
            }
        }
    }
}