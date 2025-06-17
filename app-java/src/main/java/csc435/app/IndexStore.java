package csc435.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        // TO-DO update the TermInvertedIndex with the word frequencies of the specified document
        // IMPORTANT! you need to make sure that only one thread at a time can access this method
    }

    public ArrayList<DocFreqPair> lookupIndex(String term) {
        ArrayList<DocFreqPair> results = new ArrayList<>();
        // TO-DO return the document and frequency pairs for the specified term

        return results;
    }
}
