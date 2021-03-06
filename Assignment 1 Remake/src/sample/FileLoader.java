package sample;

import java.io.*;
import java.util.*;

public class FileLoader {

    //private Map<String, EmailFile> files;
    public List<EmailFile> files;
    //private int numOfFiles;

    private Map<String, Integer> wordFileCounter;

    public Map<String, Integer> getWordFileCounter(){return wordFileCounter;}

    public FileLoader() {
        files = new ArrayList<>();
        //numOfFiles = 0;
        wordFileCounter = new TreeMap<>();
    }

    private void FileCounter() {
      //  List<String> words = new ArrayList<>();
      // for (EmailFile e : files) {
      //     for (String word : e.words) {
      //         if (!words.contains(word))
      //             words.add(word);
      //     }
//
      // }

        for (EmailFile e : files) {
            for (String word : e.words) {
                if (wordFileCounter.containsKey(word)) {
                    int prev = wordFileCounter.get(word);
                    prev += 1;
                    wordFileCounter.put(word, prev);
                }
                else {
                    wordFileCounter.put(word, 1);
                }
            }
        }

    }

    private void parseFile(File file, String actClass) throws IOException{
        System.out.println("Parsing at:" + file.getAbsolutePath());

        if (file.isDirectory()) {
            //parse all files in dir
            File[] fileList = file.listFiles();
            for (File current : fileList) {
                parseFile(current, actClass);
            }
        }
        else{
            //numOfFiles++;

            EmailFile emailFile = new EmailFile(file.getName(), actClass);

            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String token = scanner.next();
                if (isValidWord(token)){
                    if(!emailFile.words.contains(token)){
                        emailFile.words.add(token);
                    }
                }
            }
            files.add(emailFile);
        }
        //System.out.println("Num of files: " + files.size());

    }

    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

    public void Load(String filePath, String actClass) {

        if (filePath == null){
            System.err.println("Usage: java WordCounter <inputDir> <outfile>");
            System.exit(0);
        }

        File dataDir = new File(filePath);

        try {
            this.parseFile(dataDir, actClass);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.FileCounter();

    }
}
