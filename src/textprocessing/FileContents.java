package textprocessing;
import java.util.Queue;
import java.util.LinkedList;

public class FileContents {
    private Queue<String> queue = new LinkedList<>();
    private  int registerCount = 0;
    private boolean closed = false;
    private int maxFiles;
    private int maxChars;
    private int numChars = 0;
    
    /**
     * Inicializa estableciendo el numero maximo de ficheros que se puede 
     * almacenar y el tamaño total maximo de las ristras que se pueden almacenar 
     * en un momento dado
     * @param maxFiles numero maximo de ficheros
     * @param maxChars tamaño total maximo de las ristras
     */
    public FileContents(int maxFiles, int maxChars) {
        this.maxFiles = maxFiles;
        this.maxChars = maxChars;
    }
    
    /**
     * Indica que se ha registrado un nuevo FileReader
     */
    public synchronized void registerWriter() {
        this.registerCount++;
    }
    
    /**
     * Indica que un FileReader ha dejado de producir contenido
     * Cuando no hayan mas FileReader no se recibirá mas contenido
     */
    public synchronized void unregisterWriter() {
        this.registerCount--;
        if(this.registerCount == 0) {
            this.closed = true;
            notifyAll();
        }else{
            this.closed = false;
        }
    }
    
    /**
     * Un FileReader añade el contenido de un fichero siempre que no se haya 
     * alcanzado el límite de ficheros o fuera a superarse el maximo establecido
     * para el tamaño total de contenidos
     * El límite de tamaño maximo de contenido no se aplica si no hay contenido almacenado
     * @param contents contenido del fichero 
     */
    public synchronized void addContents(String contents) {
        if(!this.closed){
            while((this.queue.size() >= this.maxFiles || this.numChars + contents.length() > this.maxChars) && this.queue.size() != 0) {
                try {
                    wait();
                } catch(InterruptedException e) {}
            }
            this.queue.add(contents);
            this.numChars += contents.length();
        }
        notifyAll();
    }
    
    /**
     * Extrae el contenido un fichero para que un FileProcessor lo vaya a procesar
     * @return contenido extraido o null si el ultimo FileReader se ha desregistrado
     */
    public synchronized String getContents() {
        while(this.queue.size() == 0) {
            if(this.closed) {
                return null;
            }
            try {
                wait();
            } catch(InterruptedException e) {}
        }
        String contents = this.queue.remove();
        this.numChars -= contents.length();
        notifyAll();
        return contents;
    }
    
}
