package textprocessing;
import java.util.Queue;
import java.util.LinkedList;

public class FileNames {
    private Queue<String> queue = new LinkedList<>();
    private boolean closed = false;
    
    /**
     * Almacena un nuevo nombre de fichero
     * @param fileName nombre del fichero
     */
    public synchronized void addName(String fileName) {
        this.queue.add(fileName);
        notifyAll();
    }
    
    /**
     * Extrae un nombre de fichero
     * @return nombre del fichero o null cuando no se van a devolver mas 
     */
    public synchronized String getName() {
        while(this.queue.size() == 0) {
            if(this.closed) {
                return null;
            }
            try {
                wait();
            } catch(InterruptedException e) {}
        }
        return this.queue.remove();
    }
    
    /**
     * Da lugar a que el objeto no admita mas nombres de ficheros
     */
    public synchronized void noMoreNames() {
        this.closed = true;
        notifyAll();
    }
    
}