package ecotrack;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogGestionResiduos extends Stage {

    public DialogGestionResiduos(Window owner) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Gestión de Residuos");
        setMinWidth(500);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        HBox panelOrden = new HBox(10);
        panelOrden.setAlignment(Pos.CENTER_LEFT);
        
        ComboBox<String> cmbOrdenar = new ComboBox<>();
        cmbOrdenar.getItems().addAll("Por Peso", "Por Prioridad", "Por Tipo");
        cmbOrdenar.setValue("Por Prioridad"); // Valor por defecto
        
        panelOrden.getChildren().addAll(
            new Label("Ordenar por:"), 
            cmbOrdenar
        );
        root.setTop(panelOrden);

        ListView<String> listaResiduos = new ListView<>();
        listaResiduos.setItems(FXCollections.observableArrayList(
            "[Plástico] Botellas PET (Norte) - 5.2 kg - Prioridad: 3",
            "[Orgánico] Restos Comida (Zona) - 10.5 kg - Prioridad: 3",
            "[Metal]    Latas de Aluminio (Centro) - 7.8 kg - Prioridad: 5",
            "[Electrónico] Baterías (Sur) - 1.1 kg - Prioridad: 4"
        ));
        BorderPane.setMargin(listaResiduos, new Insets(15, 0, 15, 0));
        root.setCenter(listaResiduos);

        BorderPane panelInferior = new BorderPane();
        
        HBox panelIterador = new HBox(10);
        panelIterador.setAlignment(Pos.CENTER_LEFT);
        panelIterador.getChildren().addAll(
            new Label("Iterador Personalizado:"),
            new Button("< Anterior"),
            new Button("Siguiente >")
        );
        panelInferior.setLeft(panelIterador);

        Button btnVolver = new Button("Volver al Dashboard");
        btnVolver.setOnAction(e -> close());
        panelInferior.setRight(btnVolver);

        root.setBottom(panelInferior);

        Scene scene = new Scene(root, 600, 400);
        setScene(scene);
    }
}