package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private Canvas mainCanvas;
    @FXML
    private GraphicsContext gc;

    List<String> goog;
    List<String> aapl;

    public List<String> DownloadFile(String tickerSymbol){
        List<String> list = new ArrayList<>();
        try {
            String url = "https://query1.finance.yahoo.com/v7/finance/download/"
                    + tickerSymbol
                    + "?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
            InputStream inputStream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while((line = reader.readLine()) != null){
                list.add(line);
                System.out.println(line);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private void PlotLine(List<Float> list, float max){

        float xInc = ((float)mainCanvas.getWidth() - 100f) / (float)list.size();
        float xVal = 50f;
        float graphHeight = (float)mainCanvas.getHeight() - 100f;
        float h = (float)mainCanvas.getHeight() - 50f;
        for (int i = 1; i < list.size(); i++){
            float yVal1 = (list.get(i-1) / max) * graphHeight;
            float yVal2 = (list.get(i) / max) * graphHeight;
            gc.strokeLine(xVal, h - yVal1, xVal+xInc, h - yVal2);
            xVal += xInc;
        }

    }

    private void DrawLinePlot(List<Float> list1, List<Float> list2){
        gc.setFill(Color.BLACK);
        gc.strokeLine(50,50, 50, mainCanvas.getHeight() - 50);
        gc.strokeLine(50,mainCanvas.getHeight() - 50,
                mainCanvas.getWidth() - 50, mainCanvas.getHeight() - 50);
        //gc.fillRect(50,50, 400,400);
        float max = Float.NEGATIVE_INFINITY;
        for(float close: list1){
            if(max < close)
                max = close;
        }
        for(float close: list2){
            if(max < close)
                max = close;
        }
        System.out.println(max);
        gc.setStroke(Color.RED);
        PlotLine(list1, max);
        gc.setStroke(Color.BLUE);
        PlotLine(list2, max);
    }
    @FXML
    public void initialize(){
        goog = DownloadFile("GOOG");
        aapl = DownloadFile("AAPL");

        gc = mainCanvas.getGraphicsContext2D();

        List<Float> googClose = new ArrayList<>();
        List<Float> aaplClose = new ArrayList<>();

        for(String line : goog){
            String[] lineArray = line.split(",");
            if(!lineArray[4].contains("Close")) {
                googClose.add(Float.parseFloat(lineArray[4]));
            }
        }
        System.out.println(googClose);

        for(String line : aapl){
            String[] lineArray = line.split(",");
            if(!lineArray[4].contains("Close")) {
                aaplClose.add(Float.parseFloat(lineArray[4]));
            }
        }
        System.out.println(aaplClose);


        DrawLinePlot(googClose, aaplClose);
    }
}
