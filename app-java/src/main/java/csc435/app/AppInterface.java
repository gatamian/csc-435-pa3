package csc435.app;

import java.lang.System;
import java.util.Scanner;
import java.util.ArrayList;
public class AppInterface {
    private ProcessingEngine engine;

    public AppInterface(ProcessingEngine engine) {
        this.engine = engine;
    }

    public void readCommands() {
        // TO-DO implement the read commands method
        Scanner sc = new Scanner(System.in);
        String command;
        
        while (true) {
            System.out.print("> ");
            
            // read from command line
            command = sc.nextLine();

            // if the command is quit, terminate the program       
            if (command.compareTo("quit") == 0) {
                break;
            }
            
            // if the command begins with index, index the files from the specified directory
            if (command.length() >= 5 && command.substring(0, 5).compareTo("index") == 0) {
                String path = command.substring(6).trim();
                IndexResult result = engine.indexFiles(path);
                System.out.printf("index finished in %.3f seconds, %d bytes read.\n",result.executionTime,result.totalBytesRead);
                continue;

            }

            // if the command begins with search, search for files that matches the query
            if (command.length() >= 6 && command.substring(0, 6).compareTo("search") == 0) {
                String[] words = command.substring(7).trim().split("\\s+AND\\s+|\\s+");  
                ArrayList<String> terms = new ArrayList<>();    
                for (String term : words) {
                    if (!term.isEmpty()) terms.add((term));
                    }
                SearchResult result = engine.search(terms);
                System.out.printf("search finished in %.3f seconds. \n",result.excutionTime);
                for (DocPathFreqPair p : result.documentFrequencies){
                    System.out.printf("Document: %s, Frequency: %d\n", p.documentPath, p.wordFrequency);
                }
            }
            // TO-DO print the execution time and the top 10 search results quit
            

            System.out.println("unrecognized command!");
        }

        sc.close();
    }
}
