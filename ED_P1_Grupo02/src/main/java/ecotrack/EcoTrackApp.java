package ecotrack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EcoTrackApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("EcoTrack - Dashboard de Control");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setLeft(createLeftPanel());
        root.setCenter(createCenterPanel());      
        root.setBottom(createBottomPanel());
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createLeftPanel() {
        VBox panelAcciones = new VBox(10); 
        panelAcciones.setPadding(new Insets(10));
        panelAcciones.setMinWidth(200);
        Label lblTitulo = new Label("Acciones Principales");
        lblTitulo.setFont(new Font("Arial", 16));   
        Button btnRegistrar = new Button("Registrar Nuevo Residuo");
        Button btnDespachar = new Button("Despachar Próximo Camión");
        Button btnProcesar = new Button("Procesar Residuos (Pila)");
        Button btnGestionar = new Button("Gestionar Lista (Iterator)");
        Button btnEstadisticas = new Button("Ver Estadísticas (Mapa)");
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnDespachar.setMaxWidth(Double.MAX_VALUE);
        btnProcesar.setMaxWidth(Double.MAX_VALUE);
        btnGestionar.setMaxWidth(Double.MAX_VALUE);
        btnEstadisticas.setMaxWidth(Double.MAX_VALUE);
        panelAcciones.getChildren().addAll(
            lblTitulo, new Separator(),
            btnRegistrar, btnDespachar, btnProcesar, 
            btnGestionar, btnEstadisticas
        );
        
        btnRegistrar.setOnAction(e -> {
            DialogRegistrarResiduo dialog = new DialogRegistrarResiduo(primaryStage);
            dialog.showAndWait();
        });
        
        btnGestionar.setOnAction(e -> {
            DialogGestionResiduos dialog = new DialogGestionResiduos(primaryStage);
            dialog.showAndWait();
        });

        btnEstadisticas.setOnAction(e -> {
            DialogEstadisticas dialog = new DialogEstadisticas(primaryStage);
            dialog.showAndWait();
        });

        return panelAcciones;
    }

    private VBox createCenterPanel() {
        VBox panelTabla = new VBox(10);
        panelTabla.setPadding(new Insets(10));

        Label lblTitulo = new Label("Estado de Zonas (Ordenado por Utilidad)");
        lblTitulo.setFont(new Font("Arial", 16));

        TableView<ZonaEstado> tabla = new TableView<>();
        
        TableColumn<ZonaEstado, String> colZona = new TableColumn<>("Zona");
        colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));

        TableColumn<ZonaEstado, String> colRecolectado = new TableColumn<>("Recolectado");
        colRecolectado.setCellValueFactory(new PropertyValueFactory<>("recolectado"));

        TableColumn<ZonaEstado, String> colPendiente = new TableColumn<>("P. pendiente");
        colPendiente.setCellValueFactory(new PropertyValueFactory<>("pendiente"));

        TableColumn<ZonaEstado, Double> colUtilidad = new TableColumn<>("Utilidad (U;)");
        colUtilidad.setCellValueFactory(new PropertyValueFactory<>("utilidad"));
        
        tabla.getColumns().addAll(colZona, colRecolectado, colPendiente, colUtilidad);

        ObservableList<ZonaEstado> datos = FXCollections.observableArrayList(
            new ZonaEstado("Norte", "2.0 kg", "8.0 kg", -6.0),
            new ZonaEstado("1. Zona Norte", "1.0 kg", "5.0 kg", 5.0),
            new ZonaEstado("Centro", "10.0 kg", "5.0 kg", 5.0),
            new ZonaEstado("Sur", "2.0 kg", "1.5 kg", 5.0)
        );
        tabla.setItems(datos);

        panelTabla.getChildren().addAll(lblTitulo, tabla);
        return panelTabla;
    }

    private VBox createBottomPanel() {
        VBox panelInfo = new VBox(5);
        panelInfo.setPadding(new Insets(10, 0, 0, 10)); // Padding solo arriba e izquierda
        
        TitledPane pilaPane = new TitledPane("Centro de Reciclaje (Pila)", 
            new Label("Cima: [W-001] Botellas (Plástico)")
        );
        pilaPane.setCollapsible(false);
        
        TitledPane colaPane = new TitledPane("Cola de Prioridad (Rutas)", 
            new Label("(Vacía)")
        );
        colaPane.setCollapsible(false);

        panelInfo.getChildren().addAll(pilaPane, colaPane);
        return panelInfo;
    }

    public static void main(String[] args) {
        launch(args);
    }
}