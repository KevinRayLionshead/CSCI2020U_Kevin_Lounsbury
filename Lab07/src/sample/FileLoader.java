package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileLoader {

    private String _fileName;

    private int _totalVal;

    private Map<String, Integer> _warningTypeCount;

    public FileLoader(String filename){
        _fileName = filename;
        _warningTypeCount = new TreeMap<>();
    }

    public int GetTotalVal() { return _totalVal; }
    public Map<String, Integer> GetWarningTypeCount(){ return _warningTypeCount; }

    public void LoadFile(){
        String line = "";
        _totalVal = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(_fileName));
            while( (line = bufferedReader.readLine()) != null){
                //System.out.println(line);
                String[] cols = line.split(",");
                //System.out.println(cols[5]);
                if (_warningTypeCount.containsKey(cols[5])){
                    int prev = _warningTypeCount.get(cols[5]);
                    _warningTypeCount.put(cols[5], prev + 1);
                    _totalVal +=1;
                }
                else {
                    _warningTypeCount.put(cols[5], 1);
                    _totalVal += 1;
                }

            }
            //System.out.println(_totalVal);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
