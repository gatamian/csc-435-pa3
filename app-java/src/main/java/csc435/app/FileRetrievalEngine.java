package csc435.app;

public class FileRetrievalEngine 
{
    public static void main(String[] args)
    {
        int numWorkerThreads = args[0] != null ? Integer.parseInt(args[0]) : 1; // Default to 1 if no argument is provided

        // TO-DO initialize the number of worker threads from args[0]
        
        IndexStore store = new IndexStore();
        ProcessingEngine engine = new ProcessingEngine(store, numWorkerThreads);
        AppInterface appInterface = new AppInterface(engine);

        appInterface.readCommands();
    }
}
