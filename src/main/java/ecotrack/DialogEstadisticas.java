package ecotrack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogEstadisticas extends Stage {

    public DialogEstadisticas(Window owner) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Estadísticas");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 1000, 200); // min, max, step
        yAxis.setLabel("Peso (kg)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Peso Total Recolectado por Tipo de Residuo");
        barChart.setLegendVisible(false); // La imagen no tiene leyenda

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Orgánico", 180));
        series.getData().add(new XYChart.Data<>("Plástico", 250));
        series.getData().add(new XYChart.Data<>("Vidrio", 100));
        series.getData().add(new XYChart.Data<>("Electrónico", 950));
        series.getData().add(new XYChart.Data<>("Metal", 900));

        barChart.getData().add(series);
        root.setCenter(barChart);

        Button btnVolver = new Button("Volver al Dashboard");
        btnVolver.setOnAction(e -> close());
        
        HBox panelBoton = new HBox(btnVolver);
        panelBoton.setAlignment(Pos.CENTER_RIGHT);
        panelBoton.setPadding(new Insets(10, 0, 0, 0));
        root.setBottom(panelBoton);

        Scene scene = new Scene(root, 600, 500);
        setScene(scene);
    }
}