package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Controller {

    FileLoader fileLoader;


    @FXML
    private Canvas mainCanvas;

    @FXML
    private GraphicsContext gc;

    @FXML
    private Canvas canvas2;
    @FXML
    private GraphicsContext gc2;

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM};

    @FXML
    public void initialize() {

        fileLoader = new FileLoader("weatherwarnings-2015.csv");
        fileLoader.LoadFile();

        gc = mainCanvas.getGraphicsContext2D();
        gc2 = canvas2.getGraphicsContext2D();

        //drawBarGraph(500,500, avgHousingPricesByYear,
                //avgCommercialPricesByYear, Color.RED, Color.BLUE);
        DrawLegend(500,500, fileLoader.GetWarningTypeCount(), pieColours);

        DrawPieCart(500, fileLoader.GetWarningTypeCount(), pieColours);
    }


    //draws the legend and spaces them out based on number of things and height
    public void DrawLegend(int w, int h, Map<String, Integer> data, Color[] colors){
        gc.setFill(Color.BLANCHEDALMOND);
        gc.fillRect(0,0,w,h);

        int yInterval = h / data.keySet().size();

        Set<String> keys = data.keySet();
        Iterator<String> keyIterator = keys.iterator();
        int i = 0;
        while (keyIterator.hasNext()){
            String key = keyIterator.next();
            String count = ": " + data.get(key).toString();

            gc.setFill(colors[i]);

            gc.fillRect(10, 10 + yInterval * i, 50, 50);
            gc.setFill(Color.BLACK);
            gc.fillText(key + count, 70, (double)(40 + yInterval * i));


            i++;
        }


    }


    public void DrawPieCart(double diameter, Map<String, Integer> data, Color[] colors){

        double totalVal = fileLoader.GetTotalVal();

        double startAngle = 0;

        gc2.fillRect(0,0,diameter,diameter);

        Set<String> keys = data.keySet();
        Iterator<String> keyIterator = keys.iterator();

        int i = 0;
        //draws the series of arc to create graph
        while(keyIterator.hasNext()){
            String key = keyIterator.next();

            gc2.setFill(colors[i]);
            double arcAngle = data.get(key) / totalVal * 360;
            gc2.fillArc(0,0,diameter,diameter,
                    startAngle,arcAngle, ArcType.ROUND);
            startAngle += arcAngle;
            i++;
        }

    }

}
