package textprocessing;
import java.util.Map;
import java.util.HashMap;

public class FileProcessor extends Thread {
    private FileContents fileContents;
    private WordFrequencies wordFrequencies;
    
    /**
     * Inicializa un objeto estableciendo el FileContents y WordFrequencies que usará en su ejecucion
     * @param fc FileContents a usar
     * @param wf WordFrequencies a usar
     */
    public FileProcessor(FileContents fc, WordFrequencies wf) {
        this.fileContents = fc;
        this.wordFrequencies = wf;
    }
    
    public void run() {
        try {
            Map<String,Integer> frecuencies = new HashMap<String,Integer>();
            String contents = this.fileContents.getContents();
            String regex = "[^a-zA-Z0-9ñÑÀ-ÿ]+";
            String[] words;
            while(contents != null) {
                words = contents.split(regex);
                for(String word : words) {
                    frecuencies.put(word, frecuencies.containsKey(word) ? frecuencies.get(word) + 1 : 1);
                }
                this.wordFrequencies.addFrequencies(frecuencies);
                frecuencies.clear();
                contents = this.fileContents.getContents();
            }
        } catch(Exception e) {} 
    }

}
