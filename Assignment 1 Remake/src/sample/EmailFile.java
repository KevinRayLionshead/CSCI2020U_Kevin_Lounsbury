package sample;

import java.util.ArrayList;
import java.util.List;


public class EmailFile {

    private String fileName;
    //public Map<String, Integer> wordCount;
    private String actClass;

    public List<String> words;

    public String getFileName(){return fileName;}
    public String getActClass(){return actClass;}

    public EmailFile(String fileName, String actClass){
        this.fileName = fileName;
        this.actClass = actClass;
        //wordCount = new TreeMap<>();
        words = new ArrayList<>();
    }

}
