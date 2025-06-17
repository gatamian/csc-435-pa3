package csc435.app;

import java.util.ArrayList;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.lang.Comparable;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;

class IndexResult {
    public double executionTime;
    public long totalBytesRead;

    public IndexResult(double executionTime, long totalBytesRead) {
        this.executionTime = executionTime;
        this.totalBytesRead = totalBytesRead;
    }
}


class DocPathFreqPair {
    public String documentPath;
    public long wordFrequency;

    public DocPathFreqPair(String documentPath, long wordFrequency) {
        this.documentPath = documentPath;
        this.wordFrequency = wordFrequency;
    }
}

class SearchResult {
    public double excutionTime;
    public ArrayList<DocPathFreqPair> documentFrequencies;

    public SearchResult(double executionTime, ArrayList<DocPathFreqPair> documentFrequencies) {
        this.excutionTime = executionTime;
        this.documentFrequencies = documentFrequencies;
    }
}

// class Worker implements Runnable {
//     private String folderPath;
//     private IndexStore store;
//     private ArrayList<String> filePaths;

//     public Worker(String folderPath, IndexStore store, ArrayList<String> filePaths) {
//         this.folderPath = folderPath;
//         this.store = store;
//         this.filePaths = filePaths;
//     }

//     @Override
//     public void run() {
//         HashMap<String, Long> wordFrequencies = new HashMap<>();
//         long totalBytesRead = 0;

//         for (String filePath : filePaths) {
//             long docLength = wordFreq(filePath, wordFrequencies);
//             totalBytesRead += docLength;
//             long docNumber = store.putDocument(filePath);
//             store.updateIndex(docNumber, wordFrequencies);
//         }
//     }
// }

public class ProcessingEngine {
    // keep a reference to the index store
    private IndexStore store;
    
    // the number of worker threads to use during indexing
    private int numWorkerThreads;

    public ProcessingEngine(IndexStore store, int numWorkerThreads) {
        this.store = store;
        this.numWorkerThreads = numWorkerThreads;
    }



    /**
     * This method reads the content of a document, extracts all words/terms, and counts their frequencies.
     * It uses a regular expression to match words and ignores words that are less than or equal to 3 characters.
     * The word frequencies are stored in the provided HashMap.
     *
     * @param documString the path to the document
     * @param wf          the HashMap to store word frequencies
     * @return the length of the document content
     */
    

    private long wordFreq(String documString, HashMap<String , Long> wf){
        String content = "";
        try {
            content = Files.readString(Path.of(documString));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("File not found or Could not open " + documString);
        }
        String reg = "[a-zA-Z_0-9\\-]+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(content);

        while(m.find()){
            String word = m.group();
            //System.out.println(word);
            if(word.length() <= 3){
                continue;
            }
            
            if(wf.containsKey(word)){
                wf.put(word, wf.get(word) + 1);
            }
            else{
                wf.put(word, 1L);
            }
        }

        for(Map.Entry<String,Long> entry : wf.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
            break;
        }
        return content.length();
    } 
    public IndexResult indexFiles(String folderPath) {
        IndexResult result = new IndexResult(0.0, 0);
        long excStartTime = System.currentTimeMillis();
        ArrayList<String> filePath = new ArrayList<String>();
        Stack<File> s = new Stack<File>();
        s.add(new File(folderPath));

        while (!s.empty()){
            File file = s.pop();
            if (file.isDirectory()){
                for(File f : file.listFiles()){
                    s.add(f);
                }
            }
            else{
                filePath.add(file.getAbsolutePath());
            }
        }
        
        // TO-DO get the start time:DONE
        // TO-DO crawl the folder path and extrac all file paths:DONE
        // TO-DO create the worker threads and give to each worker a subset of the documents that need to be indexed
        // TO-DO for each file put the document path in the index store and retrieve the document number
        // TO-DO for each file extract all words/terms and count their frequencies
        // TO-DO increment the total number of read bytes
        // TO-DO update the main index with the word frequencies for each document
        // TO-DO join all of the worker threads
        // TO-DO get the stop time and calculate the execution time
        // TO-DO return the execution time and the total number of bytes read

        return result;
    }
    
    public SearchResult search(ArrayList<String> terms) {
        SearchResult result = new SearchResult(0.0, new ArrayList<DocPathFreqPair>());

        long excStartTime = System.currentTimeMillis();
        ArrayList<ArrayList<DocFreqPair>> individualResults = new ArrayList<>();
        ArrayList<DocFreqPair> tempResults = new ArrayList<>();
        ArrayList<DocFreqPair> finalResults = new ArrayList<>();
        for(String term:terms){
            ArrayList<DocFreqPair> doc = store.lookupIndex(term);
            individualResults.add(doc);

        }

        tempResults.addAll(individualResults.get(0));
        finalResults = tempResults;
        for(int i=1;i<individualResults.size();i++){
            finalResults = new ArrayList<>();
            for(DocFreqPair d:tempResults){
                for(DocFreqPair d2:individualResults.get(i)){
                    if(d.documentNumber == d2.documentNumber){
                        finalResults.add(new DocFreqPair(d.documentNumber,d.wordFrequency + d2.wordFrequency));
                    }
                }
            }
            tempResults=finalResults;
        }

        
        Collections.sort(finalResults);
        ArrayList<DocPathFreqPair> aa = new ArrayList<DocPathFreqPair>();
        for(DocFreqPair d:finalResults){
            aa.add(new DocPathFreqPair(store.getDocument(d.documentNumber),d.wordFrequency));
        }
        System.out.println(aa.size());
        SearchResult result = new SearchResult(System.currentTimeMillis()-excStartTime, aa);
        // TO-DO get the start time
        // TO-DO for each term get the pairs of documents and frequencies from the index store
        // TO-DO combine the returned documents and frequencies from all of the specified terms
        // TO-DO sort the document and frequency pairs and keep only the top 10
        // TO-DO for each document number get from the index store the document path
        // TO-DO get the stop time and calculate the execution time
        // TO-DO return the execution time and the top 10 documents and frequencies

        return result;
    }
}
