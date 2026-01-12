package ecotrack;

import java.time.LocalDate;
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
  
    public DialogRegistrarResiduo(Window owner, Zona[] zonas, EcoTrackApp app) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Registrar Nuevo Residuo");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        
        grid.add(new Label("Nombre del Residuo:"), 0, 0);
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej: Botellas de PET");
        grid.add(txtNombre, 1, 0);
        
        grid.add(new Label("Tipo de Residuo:"), 0, 1);
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Orgánico", "Plástico", "Vidrio", "Electrónico", "Metal");
        cmbTipo.setPromptText("Seleccione un tipo");
        grid.add(cmbTipo, 1, 1);
        
        grid.add(new Label("Peso (kg):"), 0, 2);
        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Ej: 15.5");
        grid.add(txtPeso, 1, 2);

        grid.add(new Label("Zona de Origen:"), 0, 3);
        ComboBox<String> cmbZona = new ComboBox<>();
        for (Zona zona : zonas) {
            cmbZona.getItems().add(zona.getNombre());
        }
        cmbZona.setPromptText("Seleccione una zona");
        grid.add(cmbZona, 1, 3);

        grid.add(new Label("Prioridad Ambiental:"), 0, 4);
        Slider sliderPrioridad = new Slider(1, 5, 3); 
        sliderPrioridad.setShowTickLabels(true);
        sliderPrioridad.setShowTickMarks(true);
        sliderPrioridad.setMajorTickUnit(1);
        sliderPrioridad.setMinorTickCount(0);
        sliderPrioridad.setSnapToTicks(true);
        grid.add(sliderPrioridad, 1, 4);

        root.setCenter(grid);

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");
        btnGuardar.setDefaultButton(true);
        btnCancelar.setCancelButton(true);
        
        btnGuardar.setOnAction(event -> {
            String tipoResiduoTexto = cmbTipo.getSelectionModel().getSelectedItem();
            String nombreZonaSeleccionada = cmbZona.getSelectionModel().getSelectedItem();
            String nombreResiduo = txtNombre.getText();
            String pesoTexto = txtPeso.getText();
            int prioridad = (int) sliderPrioridad.getValue();
            if (tipoResiduoTexto == null || nombreZonaSeleccionada == null || pesoTexto.isEmpty() || nombreResiduo.trim().isEmpty()) {
                Alerta.mostrarAlerta("Error", "Por favor, complete todos los campos.", Alert.AlertType.ERROR);
                return;
            }
            double peso;
            try {
                peso = Double.parseDouble(pesoTexto);
            } catch (NumberFormatException e) {
                Alerta.mostrarAlerta("Error", "El peso ingresado debe ser un número válido (ej: 15.5).", Alert.AlertType.ERROR);
                return;
            }
            TipoResiduo tipoResiduo;
            try {
                String tipoEnumName = tipoResiduoTexto.toUpperCase().replace("Á", "A").replace("Ó", "O");
                tipoResiduo = TipoResiduo.valueOf(tipoEnumName);
            } catch (IllegalArgumentException e) {
                Alerta.mostrarAlerta("Error", "El tipo de residuo seleccionado no es reconocido.", Alert.AlertType.ERROR);
                return;
            }
            
            LocalDate fechaActual = LocalDate.now(); 
            Zona zonaSeleccionada = null;
            for (Zona zona : zonas) {
                if (zona.getNombre().equals(nombreZonaSeleccionada)) {
                    zonaSeleccionada = zona;
                    break; 
                }
            }          
            
            if (zonaSeleccionada == null) {
                Alerta.mostrarAlerta("Error", "No se pudo encontrar la Zona seleccionada en la lista de Zonas.", Alert.AlertType.ERROR);
                return;
            }
            
            double pesoActual = zonaSeleccionada.getpPendiente();
            
            
            GestorRutas gr = EcoTrackApp.instanciaPrincipal.getGestorRutas();
            
            gr.getColaDeZonas().remove(zonaSeleccionada);
            zonaSeleccionada.setpPendiente(zonaSeleccionada.getpPendiente() + 1);
            gr.agregarZona(zonaSeleccionada);
            
            EcoTrackApp.instanciaPrincipal.actualizarTabla();
            
            int contadorResiduos = zonaSeleccionada.getResiduos().getListaResiduos().size() + 1;
            
            String id = "R" + String.format("%04d", contadorResiduos++);
    
            Residuo nuevoResiduo = new Residuo(id, nombreResiduo, tipoResiduo, peso, fechaActual, zonaSeleccionada, prioridad);
            zonaSeleccionada.registrarResiduo(nuevoResiduo);
            Alerta.mostrarAlerta("Éxito", "Residuo '" + nombreResiduo + "' guardado y asignado a la zona '" + zonaSeleccionada.getNombre() + "'.", Alert.AlertType.INFORMATION);
            close(); 
        });
        
        
        btnCancelar.setOnAction(e -> close()); 

        HBox panelBotones = new HBox(10, btnGuardar, btnCancelar);
        panelBotones.setAlignment(Pos.CENTER_RIGHT);
        panelBotones.setPadding(new Insets(20, 0, 0, 0));
        root.setBottom(panelBotones);

        Scene scene = new Scene(root);
        setScene(scene);
    }
    
    
}