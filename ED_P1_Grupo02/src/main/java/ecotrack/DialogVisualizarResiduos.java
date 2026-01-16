package ecotrack;

import java.util.Comparator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.sound.midi.SoundbankResource;


public class DialogVisualizarResiduos extends Stage {
    private IteratorBidireccional<Residuo> iterador;
    private ComboBox<Zona> comboZonas;
    private ComboBox<String> comboCriterio;
    private Label lblInfoResiduo;
    private Button btnAnterior;
    private Button btnSiguiente;
    private Button btnSearch;
    private TextField searchBar;
    

    public DialogVisualizarResiduos(Stage owner, Zona[] zonas) {
        initOwner(owner);
        setTitle("Explorador de Residuos por Zona");

        VBox root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);
        
        // Inicializar barra de busqueda
        searchBar = new TextField();
        searchBar.setPromptText("Ingrese un ID ej. (R0001)");
        searchBar.setMaxHeight(Double.MAX_VALUE);
        
        // inicalizar boton de busqueda
        btnSearch = new Button();
        btnSearch.setText("Buscar");
        
        
        comboZonas = new ComboBox<>();
        comboZonas.getItems().addAll(zonas);
        comboZonas.setPromptText("Seleccione una zona");
        comboZonas.setMaxWidth(Double.MAX_VALUE);
        
        comboCriterio = new ComboBox<>();
        comboCriterio.getItems().addAll("Orden de Registro", "Por Peso", "Por Tipo", "Por Prioridad");
        comboCriterio.setValue("Orden de Registro");
        comboCriterio.setMaxWidth(Double.MAX_VALUE);
        
        lblInfoResiduo = new Label("Seleccione una zona para comenzar");
        lblInfoResiduo.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2; -fx-padding: 20; -fx-background-color: white; -fx-font-size: 14px;");
        lblInfoResiduo.setPrefSize(320, 160);
        lblInfoResiduo.setAlignment(Pos.CENTER);
        lblInfoResiduo.setWrapText(true);

        btnAnterior = new Button("Anterior");
        btnSiguiente = new Button("Siguiente");
        
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnSearch.setDisable(true);
        
        HBox controles = new HBox(20, btnAnterior, btnSearch, btnSiguiente);
        controles.setAlignment(Pos.CENTER);

        comboZonas.setOnAction(e -> {
            Zona seleccionada = comboZonas.getValue();
            if (seleccionada != null) {
                DoublyCircularLinkedList<Residuo> lista = seleccionada.getResiduos().getListaResiduos();
                
                if (lista == null || lista.isEmpty()) {
                    iterador = null;
                    lblInfoResiduo.setText("Esta zona no tiene residuos registrados.");
                    btnAnterior.setDisable(true);
                    btnSiguiente.setDisable(true);

                } else {
                    actualizarListaConOrden();
                }
            }
        });
        
        comboCriterio.setOnAction(e -> actualizarListaConOrden());

        btnAnterior.setOnAction(e -> {
            if (iterador != null) {
                iterador.anterior();
                mostrarResiduo();
            }
        });

        btnSiguiente.setOnAction(e -> {
            if (iterador != null) {
                iterador.siguiente();
                mostrarResiduo();
            }
        });
        
        // Funcionamiento del boton "BUSCAR"
        btnSearch.setOnAction(e -> {
            Zona seleccionada = comboZonas.getValue();
            DoublyCircularLinkedList<Residuo> tempList = seleccionada.getResiduos().getListaResiduos();
            
            if (seleccionada!=null) {
                String ID = searchBar.getText();
                
                IteratorBidireccional<Residuo> iteradorBusqueda = new IteratorBidireccional<>(tempList.getHeader());
                Residuo rTest = iteradorBusqueda.obtenerActual();
                if (validarBusqueda(ID, tempList)) {
                    while (!iteradorBusqueda.obtenerActual().getId().equals(ID)) {
                        rTest = iteradorBusqueda.siguiente();
                    }
                    lblInfoResiduo.setText(rTest.toString());
                } else {
                    searchBar.setText("");
                    Alerta.mostrarAlerta("Error", "Ingrese una ID valida.", Alert.AlertType.ERROR);
                }
            } else {
                searchBar.setText("");
                Alerta.mostrarAlerta("Error", "Ingrese una zona valida.", Alert.AlertType.ERROR);
            }
        });

        root.getChildren().addAll(new Label("Seleccione Zona:"), comboZonas,new Label("Criterio de Ordenamiento:"), comboCriterio, new Label("Buscar residuo por ID:"), searchBar, lblInfoResiduo, controles);
        setScene(new Scene(root, 400, 450));
    }

    private void mostrarResiduo() {
        if (iterador != null) {
            Residuo r = iterador.obtenerActual();
            if (r != null) {
                lblInfoResiduo.setText(
                    "ID: " + r.getId() + "\n" +
                    "Tipo: " + r.getTipo() + "\n" +
                    "Peso: " + r.getPeso() + " kg\n" +
                    "Prioridad: " + r.getNivelPrioridad()
                );
            }
        }
    }
    
    private void actualizarListaConOrden() {
        Zona seleccionada = comboZonas.getValue();
        if (seleccionada == null) return;

        DoublyCircularLinkedList<Residuo> listaOrg = seleccionada.getResiduos().getListaResiduos();
        String criterio = comboCriterio.getValue();
        
        if (listaOrg == null || listaOrg.isEmpty()) {
            iterador = null;
            lblInfoResiduo.setText("Esta zona no tiene residuos registrados.");
            btnAnterior.setDisable(true);
            btnSiguiente.setDisable(true);
            return;
        }
        
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnSearch.setDisable(false);
        
        ArrayList<Residuo> tempArray = new ArrayList<>();
        DoublyCircularNodeList<Residuo> aux = listaOrg.getHeader();
        for (int i = 0; i < listaOrg.getSize(); i++) {
            tempArray.add(aux.getContent());
            aux = aux.getNext();
        }
        
        Comparator<Residuo> comp;
        switch (criterio) {
            case "Por Peso":
                comp = (r1, r2) -> Double.compare(r2.getPeso(), r1.getPeso()); 
                break;
            case "Por Tipo":
                comp = (r1, r2) -> r1.getTipo().compareToIgnoreCase(r2.getTipo());
                break;
            case "Por Prioridad":
                comp = (r1, r2) -> Integer.compare(r1.getNivelPrioridad(), r2.getNivelPrioridad());
                break;
            default:
                comp = (r1, r2) -> r1.getId().compareTo(r2.getId());
        }

        tempArray.sort(comp);

        DoublyCircularLinkedList<Residuo> tempCirc = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tempArray.size(); i++) {
            tempCirc.add(tempArray.get(i));
        }

        this.iterador = new IteratorBidireccional<>(tempCirc.getHeader());
        mostrarResiduo();
    }
    
    public boolean validarBusqueda(String ID, DoublyCircularLinkedList<Residuo> listaResiduos) {
        ArrayList<String> listaId = new ArrayList<>();
        for (Residuo r : listaResiduos) {
            listaId.add(r.getId());
        }
        
        return listaId.contains(ID);
    }
    
}
