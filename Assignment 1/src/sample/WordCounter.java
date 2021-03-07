package sample;

import java.io.*;
import java.util.*;

//code from inclass example
public class WordCounter{

    private Map<String, Integer[]> wordCounts;
    //private Map<String, Integer> wordFileCounts;
    private int fileNum = 0;

    public WordCounter(){
        wordCounts = new TreeMap<>();
    }

    public Map<String, Integer[]> getWordCounts(){return wordCounts;}
    public int getFileNum(){return fileNum;}

    public List<TestFile> testFiles = new ArrayList<>();



    public void parseFile(File file, String actClass) throws IOException{
        System.out.println("Starting parsing the file:" + file.getAbsolutePath());


        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                parseFile(current, actClass);
            }
        }else{

            TestFile testFile = new TestFile(file.getName(), 0, actClass);


            fileNum++;
            Scanner scanner = new Scanner(file);
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                if (isValidWord(token)){
                    countWord(token);
                    testFile.words.put(token, 1);
                }
            }
            testFiles.add(testFile);
        }

    }

    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

    private void countWord(String word){
        if(wordCounts.containsKey(word)){
            Integer[] previous = wordCounts.get(word);
            previous[0] += 1;
            if(previous[2] < fileNum) {
                previous[1] += 1;
                previous[2] = fileNum;
            }
            wordCounts.put(word, previous);
        }else{
            Integer[] temp = {1,1,fileNum};
            wordCounts.put(word, temp);
        }
    }

    /*public void outputWordCount(int minCount, File output) throws IOException {
        System.out.println("Saving word counts to file:" + output.getAbsolutePath());
        System.out.println("Total words:" + wordCounts.keySet().size());


        output.createNewFile();
        if (output.canWrite()) {
            PrintWriter fileOutput = new PrintWriter(output);

            Set<String> keys = wordCounts.keySet();
            Iterator<String> keyIterator = keys.iterator();

            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                int count = wordCounts.get(key);
                // testing minimum number of occurances
                if (count >= minCount) {
                    fileOutput.println(key + ": " + count);
                }
            }

            fileOutput.close();
        }


    }*/


    public void CountWords(String inPath, String actClass/*, String outPath*/) {

        if(inPath == null /*|| outPath == null*/){
            System.err.println("Usage: java WordCounter <inputDir> <outfile>");
            System.exit(0);
        }

        File dataDir = new File(inPath);
        //File outFile = new File(outPath);

        try{
            this.parseFile(dataDir, actClass);
            //this.outputWordCount(2, outFile);
        }catch(FileNotFoundException e){
            System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }


    }


}