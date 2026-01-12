package ecotrack;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
    
    public static EcoTrackApp instanciaPrincipal;
    
    private Stage primaryStage;
    private Zona[] zonas;
    private GestorRutas gestorRutas;
    private CentroReciclaje centroReciclaje = new CentroReciclaje();
    private TableView<ZonaEstado> tablaZonas;
    
    @Override
    public void start(Stage primaryStage) {
        this.gestorRutas = new GestorRutas();
        this.llenarListaZonas();
        for (Zona z : zonas) {
            gestorRutas.agregarZona(z);
        }
        instanciaPrincipal = this;
        this.primaryStage = primaryStage;
        primaryStage.setTitle("EcoTrack");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setLeft(createLeftPanel());
        root.setCenter(createCenterPanel());      
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void llenarListaZonas(){
        GestorResiduos gestorNorte = new GestorResiduos(); 
        GestorResiduos gestorCentral = new GestorResiduos();
        GestorResiduos gestorSur = new GestorResiduos();
        GestorResiduos gestorIndustrial = new GestorResiduos();
        
        Zona zonaNorte = new Zona("Zona Norte", gestorNorte, 5, 2);
        Zona zonaCentral = new Zona("Zona Central", gestorCentral, 0.0, 1);
        Zona zonaSur = new Zona("Zona Sur", gestorSur, 0.0, 0.0);
        Zona zonaIndustrial = new Zona("Zona Industrial", gestorIndustrial, 7, 1);

        zonaNorte.registrarResiduo(new Residuo("R" + String.format("%04d", 1),"Restos de comida", TipoResiduo.ORGANICO, 150.5, LocalDate.now().minusDays(2), zonaNorte, 5));
        zonaNorte.registrarResiduo( new Residuo("R" + String.format("%04d", 2), "Botellas de cerveza",  TipoResiduo.VIDRIO, 45.0,  LocalDate.now(),  zonaNorte,  3));

        zonaCentral.registrarResiduo(new Residuo("R" + String.format("%04d", 1), "Envases de bebidas", TipoResiduo.PLASTICO, 88.2, LocalDate.now().minusDays(1), zonaCentral,  4));
    
        zonaIndustrial.registrarResiduo(new Residuo("R" + String.format("%04d", 1), "Componentes de PC viejos", TipoResiduo.ELECTRONICO, 12.7, LocalDate.now(), zonaIndustrial, 5));
        
        zonas = new Zona[4];
        zonas[0] = zonaNorte;
        zonas[1] = zonaCentral;
        zonas[2] = zonaSur;
        zonas[3] = zonaIndustrial;
    }


    private VBox createLeftPanel() {
        VBox panelAcciones = new VBox(10); 
        panelAcciones.setPadding(new Insets(10));
        panelAcciones.setMinWidth(220);
        Label lblTitulo = new Label("Acciones Principales");
        lblTitulo.setFont(new Font("Arial", 16));
        
        Label lblCriterio = new Label("Criterio de Prioridad:");
        ComboBox<String> cmbCriterio = new ComboBox<>();
        cmbCriterio.getItems().addAll("Volumen de Residuos", "Impacto Ambiental");
        cmbCriterio.setPromptText("Seleccione Criterio");
        cmbCriterio.setMaxWidth(Double.MAX_VALUE);
        
        cmbCriterio.setOnAction(e -> {
            boolean porVolumen = cmbCriterio.getValue().equals("Volumen de Residuos");
            gestorRutas.cambiarCriterioPrioridad(porVolumen,zonas);
            Arrays.sort(zonas, (z1, z2) -> {
                if (porVolumen) {
                    return Double.compare(z2.getpPendiente(), z1.getpPendiente());
                } else {
                    return Double.compare(z2.getUtilidad(), z1.getUtilidad());
                }
            });
            actualizarTabla();
            Alerta.mostrarAlerta("Criterio Actualizado", "El sistema ahora prioriza por: " + cmbCriterio.getValue(), Alert.AlertType.INFORMATION);
        });
        
        Button btnNuevaZona = new Button("Agregar Nueva Zona");
        btnNuevaZona.setMaxWidth(Double.MAX_VALUE);
        btnNuevaZona.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        btnNuevaZona.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nueva Zona");
            dialog.setHeaderText("Configuración de Zona de Recolección");
            dialog.setContentText("Ingrese el nombre de la zona:");

            dialog.showAndWait().ifPresent(nombre -> {
                if (!nombre.trim().isEmpty()) {
                    Zona nueva = new Zona(nombre, new GestorResiduos() , 0.0, 0); 
                    agregarZona(nueva);
                    gestorRutas.agregarZona(nueva);
                    actualizarTabla();
                    Alerta.mostrarAlerta("Éxito", "Zona '" + nombre + "' agregada al sistema.", Alert.AlertType.INFORMATION);
                }
            });
        });
        
        Button btnStatsGlobales = new Button("Estadísticas Globales");
        btnStatsGlobales.setMaxWidth(Double.MAX_VALUE);

        btnStatsGlobales.setOnAction(e -> {
            Map<String, Object> datosActuales = calcularEstadisticasZonas();
            DialogEstadisticas ventana = new DialogEstadisticas(primaryStage, datosActuales);
            ventana.show();
        });
        
        Button btnRegistrar = new Button("Registrar Nuevo Residuo");
        Button btnDespachar = new Button("Despachar Próximo Camión");
        Button btnCentro = new Button("Centro de reciclaje");
        Button btnVisualizar = new Button("Visualizar Residuos por Zona");
        Button btnGuardar = new Button("Guardar Sistema");
        Button btnCargar = new Button("Cargar Sistema");
        
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnDespachar.setMaxWidth(Double.MAX_VALUE);
        btnCentro.setMaxWidth(Double.MAX_VALUE);
        btnVisualizar.setMaxWidth(Double.MAX_VALUE);
        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        btnCargar.setMaxWidth(Double.MAX_VALUE);
        
        panelAcciones.getChildren().addAll(
            lblTitulo, new Separator(),
            lblCriterio, cmbCriterio, new Separator(),
            btnNuevaZona, btnRegistrar, btnDespachar, 
            btnCentro, btnVisualizar, btnStatsGlobales,
            new Separator(), new Label("Persistencia:"),btnGuardar, 
            btnCargar
        );
        
        btnRegistrar.setOnAction(e -> {
            if (zonas==null || zonas.length==0 ) {
                Alerta.mostrarAlerta("Error", "La cola de zonas está vacía", Alert.AlertType.ERROR);
                return; 
            }
            DialogRegistrarResiduo dialog = new DialogRegistrarResiduo(primaryStage,zonas,this);
            dialog.showAndWait();
        });
        
        btnDespachar.setOnAction(e -> {
            if (gestorRutas == null || gestorRutas.isEmpty()) {
                Alerta.mostrarAlerta("Atención", "No hay rutas pendientes por despachar.", Alert.AlertType.WARNING);
                return;
            }
            Zona zonaADespachar = gestorRutas.despacharProximaRuta();
            eliminarZona(zonaADespachar);
            actualizarTabla();
            abrirVentanaSimulacion(zonaADespachar);
        });
        
        btnVisualizar.setOnAction(e -> {
            if (zonas == null || zonas.length == 0) {
                Alerta.mostrarAlerta("Error", "No hay zonas configuradas.", Alert.AlertType.WARNING);
                return;
            }
            
            DialogVisualizarResiduos dialog = new DialogVisualizarResiduos(primaryStage, zonas);
            dialog.showAndWait();
        });
    
        btnCentro.setOnAction(e -> {
            DialogCentroReciclaje dialog = new DialogCentroReciclaje(primaryStage, centroReciclaje);
            dialog.showAndWait();
        });
        
        btnGuardar.setOnAction(e -> {
            GestorPersistencia.guardarEstado(this.zonas);
            Alerta.mostrarAlerta("Persistencia", "Datos guardados en ecotrack_estado.dat", Alert.AlertType.INFORMATION);
        });
        
        btnCargar.setOnAction(e -> {
            Object recuperado = GestorPersistencia.cargarEstado();
            if (recuperado instanceof Zona[]) {
                this.zonas = (Zona[]) recuperado;

                // Re-sincronizar el gestor de rutas con los nuevos datos
                gestorRutas = new GestorRutas(); 
                for (Zona z : zonas) {
                    gestorRutas.agregarZona(z);
                }

                actualizarTabla();
                Alerta.mostrarAlerta("Persistencia", "Sistema restaurado con éxito", Alert.AlertType.INFORMATION);
            } else {
                Alerta.mostrarAlerta("Error", "No se pudo cargar el archivo o está corrupto.", Alert.AlertType.ERROR);
            }
        });
        return panelAcciones;
    }
    
    private VBox createCenterPanel() {
        VBox panelTabla = new VBox(10);
        panelTabla.setPadding(new Insets(10));

        Label lblTitulo = new Label("Estado de Zonas");
        lblTitulo.setFont(new Font("Arial Bold", 16));

        tablaZonas = new TableView<ZonaEstado>(); 
        
        tablaZonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ZonaEstado, String> colNombre = new TableColumn<>("Zona");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("zona"));

        TableColumn<ZonaEstado, String> colRecolectado = new TableColumn<>("Recolectado");
        colRecolectado.setCellValueFactory(new PropertyValueFactory<>("recolectado"));

        TableColumn<ZonaEstado, String> colPeso = new TableColumn<>("Pendiente");
        colPeso.setCellValueFactory(new PropertyValueFactory<>("pendiente"));

        TableColumn<ZonaEstado, Double> colUtilidad = new TableColumn<>("Utilidad");
        colUtilidad.setCellValueFactory(new PropertyValueFactory<>("utilidad"));

        tablaZonas.getColumns().addAll(colNombre, colRecolectado, colPeso, colUtilidad);

        actualizarTabla(); 

        panelTabla.getChildren().addAll(lblTitulo, tablaZonas);
        return panelTabla;
    }
    
    public void actualizarTabla() {
        if (zonas == null) return;

        ObservableList<ZonaEstado> datosReales = FXCollections.observableArrayList();
        for (Zona z : zonas) {
            if (z != null) {
                datosReales.add(new ZonaEstado(
                    z.getNombre(), 
                    z.getpRecolectado() + " kg", 
                    z.getpPendiente() + " kg", 
                    z.getUtilidad()
                ));
            }
        }
        tablaZonas.setItems(datosReales);
        tablaZonas.refresh();
    }
    
    private void eliminarZona(Zona z) {
        if (z== null) return;
    
        ArrayList<Zona> temp = new ArrayList<>();
        for (Zona z1 : zonas) {
            if (z1 != null && !z1.getNombre().equals(z.getNombre())) {
                temp.addLast(z1);
            }
        }
        int nuevoTamano = temp.getEffectiveSize(); 
        this.zonas = new Zona[nuevoTamano];
        Object[] tempArray = temp.toArray();
        for (int i = 0; i < nuevoTamano; i++) {
            this.zonas[i] = (Zona) tempArray[i];
        }
    }
    
    private void agregarZona(Zona nuevaZona) {
        Zona[] nuevoArreglo = new Zona[zonas.length + 1];
        System.arraycopy(zonas, 0, nuevoArreglo, 0, zonas.length);
        nuevoArreglo[zonas.length] = nuevaZona;
        this.zonas = nuevoArreglo;
    }
    
    public void abrirVentanaSimulacion(Zona zona) {
        Stage stageSim = new Stage();
        stageSim.setTitle("Recoleccion: " + zona.getNombre());

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label lblStatus = new Label("Recolectando residuos en " + zona.getNombre() + "...");
        ProgressBar pb = new ProgressBar(0);
        pb.setPrefWidth(250);
        Label lblPorcentaje = new Label("0%");

        root.getChildren().addAll(lblStatus, pb, lblPorcentaje);
        stageSim.setScene(new Scene(root, 350, 180));
        stageSim.show();

        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i += 10) {
                    final double progreso = i / 100.0;
                    final int valor = i;

                    javafx.application.Platform.runLater(() -> {
                        pb.setProgress(progreso);
                        lblPorcentaje.setText(valor + "% completado");
                        if (valor == 100) lblStatus.setText("¡Zona despejada con éxito!");
                    });
                    Thread.sleep(300);
                }
                    javafx.application.Platform.runLater(() -> {
                    DoublyCircularLinkedList<Residuo> residuosZona = zona.getResiduos().getListaResiduos();

                    if (!residuosZona.isEmpty()) {
                        int cantidad = 0;
                        java.util.Iterator<Residuo> it = residuosZona.iterator();
                        while (it.hasNext()) {
                            Residuo r = it.next();
                            this.centroReciclaje.recibirResiduo(r);
                            cantidad++;
                        }
                        zona.getResiduos().vaciarResiduos();
                        Alerta.mostrarAlerta("Entrega Exitosa", 
                            "Se han entregado " + cantidad + " residuos al Centro de Reciclaje.", 
                            Alert.AlertType.INFORMATION);
                    }
                    new Thread(() -> {
                        try { 
                            Thread.sleep(1500); 
                        } catch (Exception e) {}
                        javafx.application.Platform.runLater(() -> stageSim.close());
                    }).start();
                });
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    
    public Map<String, Object> calcularEstadisticasZonas() {
        Map<String, Object> stats = new HashMap<>();

        double pesoAcumuladoReal = 0.0;
        int totalResiduos = 0;

        Map<String, Integer> conteoPorTipo = new HashMap<>();
        Map<String, Double> pesoPorTipo = new HashMap<>();

        for (Zona z : zonas) {
            if (z != null && z.getResiduos() != null) {
                DoublyCircularLinkedList<Residuo> lista = z.getResiduos().getListaResiduos();

                java.util.Iterator<Residuo> it = lista.iterator();
                while (it.hasNext()) {
                    Residuo r = it.next();
                    pesoAcumuladoReal += r.getPeso();
                    totalResiduos++;
                    String tipo = r.getTipo().toString();
                    conteoPorTipo.put(tipo, conteoPorTipo.getOrDefault(tipo, 0) + 1);
                    double pesoActualTipo = pesoPorTipo.getOrDefault(tipo, 0.0);
                    pesoPorTipo.put(tipo, pesoActualTipo + r.getPeso());
                }
            }
        }

        stats.put("pesoTotal", pesoAcumuladoReal);
        stats.put("cantidadTotal", totalResiduos);
        stats.put("conteoPorTipo", conteoPorTipo);
        stats.put("pesoPorTipo", pesoPorTipo);
        return stats;
    }
    
    public GestorRutas getGestorRutas() {
        return gestorRutas;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}