package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Controller {
    @FXML
    private Canvas mainCanvas;

    @FXML
    private GraphicsContext gc;

    @FXML
    private Canvas canvas2;
    @FXML
    private GraphicsContext gc2;

    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7};
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3};

    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247};
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM};

    @FXML
    public void initialize() {
        gc = mainCanvas.getGraphicsContext2D();
        gc2 = canvas2.getGraphicsContext2D();

        drawBarGraph(500,500, avgHousingPricesByYear,
                avgCommercialPricesByYear, Color.RED, Color.BLUE);

        drawPieCart(500, purchasesByAgeGroup,pieColours);
    }



    public void drawBarGraph(int w, int h, double[] data, double[] data2,
                             Color color1, Color color2){
        gc.setFill(Color.BLANCHEDALMOND);
        gc.fillRect(0,0,w,h);

        double xInc = w / (data.length + data2.length);
        //find max and min
        double maxVal = Double.NEGATIVE_INFINITY;
        double minVal = Double.MAX_VALUE;

        for (double val:data){
            if(val > maxVal)
                maxVal = val;
            if (val < minVal)
                minVal = val;
        }
        for (double val:data2){
            if(val > maxVal)
                maxVal = val;
            if (val < minVal)
                minVal = val;
        }

        //makes it so you can still see the smallest value in the dataset
        if (minVal > 0)
            minVal *= 0.5;
        else if (minVal < 0)
            minVal *= 2;

        //drawing bars
        double x = 10;
        for (double val:data){
            double height = ((val - minVal) / (maxVal - minVal)) * (h - 20);
            gc.setFill(color1);
            gc.fillRect(x, (h-height - 10), xInc - 10, height);
            x += xInc * 2;
        }

        x = xInc;
        for (double val:data2){
            double height = ((val - minVal) / (maxVal - minVal)) * (h - 20);
            gc.setFill(color2);
            gc.fillRect(x, (h-height - 10), xInc - 10, height);
            x += xInc * 2;
        }

    }

    public void drawPieCart(double diameter, int[] data, Color[] colors){

        double totalVal = 0;
        for (int val:data){

            totalVal += val;
        }
        double startAngle = 0;

        gc2.fillRect(0,0,diameter,diameter);

        //draws the series of arc to create graph
        for(int i = 0; i < data.length; i++) {
            gc2.setFill(colors[i]);
            double arcAngle = data[i] / totalVal * 360;
            gc2.fillArc(0,0,diameter,diameter,
                    startAngle,arcAngle, ArcType.ROUND);
            startAngle += arcAngle;
        }

    }

}
