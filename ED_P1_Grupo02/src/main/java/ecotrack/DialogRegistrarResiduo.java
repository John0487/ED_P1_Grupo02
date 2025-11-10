package ecotrack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogRegistrarResiduo extends Stage {

    public DialogRegistrarResiduo(Window owner) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Registrar Nuevo Residuo");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);

        grid.add(new Label("Tipo de Residuo:"), 0, 0);
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Orgánico", "Plástico", "Vidrio", "Electrónico", "Metal");
        cmbTipo.setPromptText("Seleccione un tipo");
        grid.add(cmbTipo, 1, 0);

        grid.add(new Label("Peso (kg):"), 0, 1);
        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Ej: 15.5");
        grid.add(txtPeso, 1, 1);

        grid.add(new Label("Zona de Origen:"), 0, 2);
        ComboBox<String> cmbZona = new ComboBox<>();
        cmbZona.getItems().addAll("Zona Norte", "Zona Centro", "Zona Sur");
        cmbZona.setPromptText("Seleccione una zona");
        grid.add(cmbZona, 1, 2);

        grid.add(new Label("Prioridad Ambiental:"), 0, 3);
        Slider sliderPrioridad = new Slider(1, 5, 3); // min, max, inicial
        sliderPrioridad.setShowTickLabels(true);
        sliderPrioridad.setShowTickMarks(true);
        sliderPrioridad.setMajorTickUnit(1);
        sliderPrioridad.setMinorTickCount(0);
        sliderPrioridad.setSnapToTicks(true);
        grid.add(sliderPrioridad, 1, 3);

        root.setCenter(grid);

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");
        btnGuardar.setDefaultButton(true);
        btnCancelar.setCancelButton(true);

        btnCancelar.setOnAction(e -> close()); // Cierra el diálogo

        HBox panelBotones = new HBox(10, btnGuardar, btnCancelar);
        panelBotones.setAlignment(Pos.CENTER_RIGHT);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));
        root.setBottom(panelBotones);

        Scene scene = new Scene(root);
        setScene(scene);
    }
}