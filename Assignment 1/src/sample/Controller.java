package sample;
import java.util.*;

public class Controller {

    WordCounter trainHamFreq;
    WordCounter testHamFreq;
    WordCounter trainSpamFreq;
    WordCounter testSpamFreq;

    //WordCounter testSpam;

    Map<String, Double> probabilities;
    Map<String, Double> probMap;
    Map<String, Double> probWordInSpam;
    Map<String, Double> probWordInHam;


    private Map<String, Double> getProbabilities(Map<String, Integer[]> wordCounts, int fileNum) {

        Set<String> keys = wordCounts.keySet();
        Iterator<String> keyIterator = keys.iterator();
        Map<String, Double> tempProbMap = new TreeMap<>();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();

            double probability = 0f;

            int numOfFilesContainWord = wordCounts.get(key)[1];
            int numOfFiles = fileNum;

            probability = (double)numOfFilesContainWord / (double)numOfFiles;

            tempProbMap.put(key, probability);
        }
        return tempProbMap;
    }

    private void createProbMap(){
        Set<String> keys = probWordInSpam.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            double probability = 0f;
            if (probWordInSpam.containsKey(key) && probWordInHam.containsKey(key)){
                probMap.put(key, probWordInSpam.get(key) / (probWordInSpam.get(key) + probWordInHam.get(key)));
            }else if(probWordInSpam.containsKey(key)){
                probMap.put(key, probWordInSpam.get(key) / (probWordInSpam.get(key) + 0f));
            }else {
                probMap.put(key, 0d);
            }
        }

        keys = probWordInHam.keySet();
        keyIterator = keys.iterator();


        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            double probability = 0f;
            if (!probMap.containsKey(key)) {


                if (probWordInSpam.containsKey(key) && probWordInHam.containsKey(key)) {
                    probMap.put(key, probWordInSpam.get(key) / (probWordInSpam.get(key) + probWordInHam.get(key)));
                } else if (probWordInSpam.containsKey(key)) {
                    probMap.put(key, probWordInSpam.get(key) / (probWordInSpam.get(key) + 0f));
                } else {
                    probMap.put(key, 0d);
                }
            }
        }

    }

    private double fileSpamProb(TestFile testFile){
        Set<String> keys = testFile.words.keySet();
        Iterator<String> keyIterator = keys.iterator();
        //Map<String, Double> tempProbMap = new TreeMap<>();

        double probability = 0;
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if(probMap.containsKey(key)) {
                probability += (Math.log(1 - probMap.get(key)) - Math.log(probMap.get(key)));
            }
        }

        probability = 1 / (1 + Math.pow(Math.E, probability));

        return probability;
    }


    public void initialize() {

        probMap = new TreeMap<>();
        probWordInSpam = new TreeMap<>();
        probWordInHam = new TreeMap<>();

        trainHamFreq = new WordCounter();
        trainSpamFreq = new WordCounter();
        testHamFreq = new WordCounter();
        testSpamFreq = new WordCounter();

        trainHamFreq.CountWords("./data/train/ham", "ham");
        trainSpamFreq.CountWords("./data/train/spam", "spam");

        testHamFreq.CountWords("./data/test/ham", "ham");
        testSpamFreq.CountWords("./data/test/spam", "spam");

        System.out.println("Total words:" + trainHamFreq.getWordCounts().keySet().size());
        System.out.println("Total words:" + trainSpamFreq.getWordCounts().keySet().size());

        probWordInSpam = getProbabilities(trainSpamFreq.getWordCounts(), trainSpamFreq.getFileNum());
        probWordInHam = getProbabilities(trainHamFreq.getWordCounts(), trainHamFreq.getFileNum());

        createProbMap();

        for(TestFile testFile : testHamFreq.testFiles){
            testFile.setSpamProbability(fileSpamProb(testFile));
        }

        for(TestFile testFile : testSpamFreq.testFiles){
            testFile.setSpamProbability(fileSpamProb(testFile));
        }
        for(TestFile testFile : testSpamFreq.testFiles){
            System.out.println("Spam Probability:" + testFile.getSpamProbability());
        }



    }

}
