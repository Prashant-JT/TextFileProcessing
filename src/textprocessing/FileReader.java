package textprocessing;

public class FileReader extends Thread {
    private FileContents fileContents;
    private FileNames fileNames;
    
    /**
     * Inicializa un objeto estableciendo el FileName y FileContents que usará en su ejecucion
     * @param fn FileName a usar
     * @param fc FileContents a usar
     */
    public FileReader(FileNames fn, FileContents fc) {
        this.fileNames = fn;
        this.fileContents = fc;
    }
    
    public void run() {
        try {
            String file = this.fileNames.getName();
            this.fileContents.registerWriter();
            while(file != null) {
                this.fileContents.addContents(Tools.getContents(file));
                file = this.fileNames.getName();
            }
            this.fileContents.unregisterWriter();
        } catch(Exception e) {} 
    }
    
}
