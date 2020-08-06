package textprocessing;
import java.util.Map;
import java.util.HashMap;

public class WordFrequencies {
    private final Map<String,Integer> frecuencies = new HashMap<>();
    
    /**
     * Añade al objeto actual la informacion de palabras/frecuencias pasada
     * @param f palabras/frecuencias 
     */
    public synchronized void addFrequencies(Map<String,Integer> f) {
        for(String word : f.keySet()) {
            this.frecuencies.put(word, this.frecuencies.containsKey(word) ? this.frecuencies.get(word) + f.get(word) : f.get(word));
        }
    }
    
    /**
     * Devuelve una copia del mapa con las parejas de palabras/frecuencias acumuladas
     * @return palabras/frecuencias acumuladas
     */
    public Map<String,Integer> getFrequencies() {
        return new HashMap<>(this.frecuencies);
    }
    
}
