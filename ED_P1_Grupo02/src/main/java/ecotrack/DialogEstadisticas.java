package ecotrack;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Map;

public class DialogEstadisticas extends Stage {

    public DialogEstadisticas(Stage owner, Map<String, Object> stats) {
        initOwner(owner);
        setTitle("Estadísticas Globales");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        double pesoTotal = (double) stats.getOrDefault("pesoTotal", 0.0);
        int totalResiduos = (int) stats.getOrDefault("cantidadTotal", 0);
        Map<String, Integer> conteo = (Map<String, Integer>) stats.get("conteoPorTipo");
        Map<String, Double> pesosPorTipo = (Map<String, Double>) stats.get("pesoPorTipo");

        root.getChildren().add(new Label("TOTAL RESIDUOS: " + totalResiduos));
        root.getChildren().add(new Label("PESO TOTAL: " + pesoTotal + " kg"));
        root.getChildren().add(new Separator());
        root.getChildren().add(new Label("DESGLOSE POR TIPO:"));

        TextArea txtDetalle = new TextArea();
        txtDetalle.setEditable(false);
        
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entrada : conteo.entrySet()) {
            String tipo = entrada.getKey();      
            Integer cant = entrada.getValue();  

            double p = pesosPorTipo.getOrDefault(tipo, 0.0);

            sb.append("- ").append(tipo).append(": ")
              .append(cant).append(" unidades | ")
              .append(p).append(" kg\n");
        }
        
        txtDetalle.setText(sb.toString());
        root.getChildren().add(txtDetalle);

        setScene(new Scene(root, 400, 450));
    }
}