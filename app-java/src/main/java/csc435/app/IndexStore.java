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

// Data structure that stores a document number and the number of time a word/term appears in the document
class DocFreqPair implements Comparable<DocFreqPair>{
    public long documentNumber;
    public long wordFrequency;

    public DocFreqPair(long documentNumber, long wordFrequency) {
        this.documentNumber = documentNumber;
        this.wordFrequency = wordFrequency;
    }

        @Override
    public int compareTo(DocFreqPair d){
         return (int)(this.wordFrequency - d.wordFrequency);
    }
}

public class IndexStore {
    ArrayList<String> docMap;
    HashMap<String,ArrayList<DocFreqPair>> invertedIndex;
    ReentrantLock docMapLock = new ReentrantLock();
    ReentrantLock invertedIndexLock = new ReentrantLock();
    
    
    // TO-DO declare data structure that keeps track of the DocumentMap
    // TO-DO declare data structures that keeps track of the TermInvertedIndex
    // TO-DO declare two locks, one for the DocumentMap and one for the TermInvertedIndex

    public IndexStore() {
        this.docMap = new ArrayList<String>();
        this. invertedIndex = new HashMap<>();
        // TO-DO initialize the DocumentMap and TermInvertedIndex members
    }

    public long putDocument(String documentPath) {
        long documentNumber = 0;
        docMapLock.lock();
        try {
            // TO-DO check if the document path already exists in the DocumentMap
            for (int i = 0; i < docMap.size(); i++) {
                if (docMap.get(i).equals(documentPath)) {
                    return i; // Return the existing document number
                }
            }       
            // If it does not exist, add the document path to the DocumentMap
            documentNumber = docMap.size(); // The new document number is the current size of the DocumentMap
            docMap.add(documentPath);
        } finally {
            docMapLock.unlock();
        }
        // TO-DO assign a unique number to the document path and return the number
        // IMPORTANT! you need to make sure that only one thread at a time can access this method

        return documentNumber;
    }

    public String getDocument(long documentNumber) {
        String documentPath = "";
        // TO-DO retrieve the document path that has the given document number

        return documentPath;
    }

    public void updateIndex(long documentNumber, HashMap<String, Long> wordFrequencies) {
        invertedIndexLock.lock();
        try {       
            // TO-DO update the TermInvertedIndex with the word frequencies of the specified document
            for (Map.Entry<String, Long> entry : wordFrequencies.entrySet()) {
                String key = entry.getKey();
                long value = entry.getValue();

            if(!invertedIndex.containsKey(key)){
                invertedIndex.put(key,new ArrayList<DocFreqPair>());
            }

            ArrayList<DocFreqPair> d = invertedIndex.get(key);
            d.add(new DocFreqPair(documentNumber,value));
            }
/*                 String term = entry.getKey();
                long frequency = entry.getValue();
                
                // Get or create the list of DocFreqPair for this term
                ArrayList<DocFreqPair> docFreqPairs = invertedIndex.getOrDefault(term, new ArrayList<>());
                
                // Add or update the document frequency pair
                boolean found = false;
                for (DocFreqPair pair : docFreqPairs) {
                    if (pair.documentNumber == documentNumber) {
                        pair.wordFrequency += frequency; // Update frequency if document already exists
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    docFreqPairs.add(new DocFreqPair(documentNumber, frequency)); // Add new pair
                }
                
                invertedIndex.put(term, docFreqPairs); // Update the inverted index */
            }
        finally {
            invertedIndexLock.unlock();
        }

        // TO-DO update the TermInvertedIndex with the word frequencies of the specified document
        // IMPORTANT! you need to make sure that only one thread at a time can access this method
    }

    public ArrayList<DocFreqPair> lookupIndex(String term) {
        ArrayList<DocFreqPair> results = new ArrayList<>();
                if(invertedIndex.containsKey(term)){
            results = invertedIndex.get(term);
        }
        // TO-DO return the document and frequency pairs for the specified term

        return results;
  }
}   

