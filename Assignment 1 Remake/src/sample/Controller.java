package sample;

import java.util.*;

public class Controller {

    FileLoader trainHamLoader;
    FileLoader trainSpamLoader;

    FileLoader testHamLoader;
    FileLoader testSpamLoader;

    Map<String, Double> probMap = new TreeMap<>();

    List<TestFile> testFiles;


    private Map<String, Double> getProbabilities(FileLoader fileLoader) {



        Set<String> keys = fileLoader.getWordFileCounter().keySet();
        Iterator<String> keyIterator = keys.iterator();

        Map<String, Double> tempProbMap = new TreeMap<>();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();

            double probability = 0f;

            int numOfFilesContainWord = fileLoader.getWordFileCounter().get(key);
            int numOfFiles = fileLoader.files.size();

            probability = (double)numOfFilesContainWord / (double) numOfFiles;

            tempProbMap.put(key, probability);
        }
        return tempProbMap;
    }

    private void createProbMap(Map<String, Double> spamMap, Map<String, Double> hamMap) {
        //create a list off all of the words(ham and spam)
        List<String> allWords = new ArrayList<>();

        probMap.clear();

        Set<String> keys = spamMap.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();

            if(!allWords.contains(key)){
                allWords.add(key);
            }
        }
        keys = hamMap.keySet();
        keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();

            if(!allWords.contains(key)){
                allWords.add(key);
            }
        }

        for(String key : allWords){
            if (spamMap.containsKey(key) && hamMap.containsKey(key)){
                probMap.put(key, spamMap.get(key) / (spamMap.get(key) + hamMap.get(key)));
            }else if(spamMap.containsKey(key)){
                probMap.put(key, spamMap.get(key) );
            }else {
                probMap.put(key, 0d);
            }
        }


    }

    private void fileSpamProb(FileLoader fileLoader) {


        for(EmailFile emailFile : fileLoader.files) {
            double probability = 0;
            for(String key : emailFile.words){
                if(probMap.containsKey(key)) {
                    probability +=
                            (Math.log(1 - probMap.get(key)) -
                            Math.log(probMap.get(key)));
                }
            }
            TestFile testFile = new TestFile(emailFile.getFileName(), probability,
                    emailFile.getActClass());
            testFiles.add(testFile);
        }

    }


    public void initialize() {

        testFiles = new ArrayList<>();

        trainHamLoader = new FileLoader();
        trainSpamLoader = new FileLoader();

        testHamLoader = new FileLoader();
        testSpamLoader = new FileLoader();

        Map<String, Double> probWordIsSpam = new TreeMap<>();
        Map<String, Double> probWordIsHam = new TreeMap<>();


        trainHamLoader.Load("./data/train/ham", "ham");
        trainSpamLoader.Load("./data/train/spam", "spam");

        testHamLoader.Load("./data/test/ham", "ham");
        testSpamLoader.Load("./data/test/spam", "spam");




        probWordIsHam = getProbabilities(trainHamLoader);
        probWordIsSpam = getProbabilities(trainSpamLoader);

        createProbMap(probWordIsSpam, probWordIsHam);



        Set<String> keys = probMap.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();

            System.out.println(probMap.get(key));

            if(probMap.get(key) > 1 || probMap.get(key) < 0)
                System.exit(10);

        }


        fileSpamProb(testHamLoader);
        fileSpamProb(testSpamLoader);

        for(TestFile file:testFiles){

            //System.out.println(file.getFilename()+": " +file.getSpamProbability());
        }



        //Set<String> keys = probMap.keySet();
        //Iterator<String> keyIterator = keys.iterator();
////
        //while (keyIterator.hasNext()) {
        //    String key = keyIterator.next();
        //    System.out.println(key + ": " + probMap.get(key));
        //}
        //System.out.println(probMap);

    }

}
