/**
 * Representa un álbum en la biblioteca de música.
 * Cada álbum tiene un id, un título, un artista, y una cantidad de copias disponibles.
 * 
 * @version 0.1
 */
public class Album {
    // id del álbum
    private int id;
    // título del álbum
    private String title;
    // artista del álbum
    private String artist;
    // cantidad de copias disponibles del álbum
    private int copiesAvailable;

    /**
     * Constructor de Album.
     * la id debe ser negativa, el titulo y el artista no pueden ser nulos ni vacios
     * copiesAvailables debe ser positivo
     */
    public Album(int id, String title, String artist, int copiesAvailable) {
        if(id <= 0){
            throw new IllegalArgumentException("la ID debe ser positiva");
        }
        if(title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("el titulo no puede ser nulo ni vacio");
        }
        if(artist == null || artist.trim().isEmpty()){
            throw new IllegalArgumentException("el artista no puede ser nulo ni vacio");
        }
        if(copiesAvailable <= 0){
            throw new IllegalArgumentException("copiesAvailables debe ser positiva");
        }
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.copiesAvailable = copiesAvailable;
    }

    /**
     * Retorna el id del álbum.
     * @return el id del álbum.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna el título del álbum.
     * @return el título del álbum.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retorna el artista del álbum.
     * @return el artista del álbum.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Retorna la cantidad de copias disponibles del álbum.
     * @return la cantidad de copias disponibles del álbum.
     */
    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    /**
     * Presta una copia del álbum, disminuyendo la cantidad de copias disponibles en uno.
     * Precondición: debe haber al menos una copia disponible.
     */
    public void lendCopy() {
        if(copiesAvailable <= 0){
            throw new IllegalArgumentException("no hay copias suficientes para prestar");
        }
        copiesAvailable--;
    }
    
    /**
     * Invariante de clase de Album. Un álbum se considera válido si su id es mayor a cero,
     * su título y artista no son nulos ni vacíos, y la cantidad de copias disponibles es mayor o igual a cero.
     * @return true si y sólo si el objeto satisface el invariante de clase.
     */
    public boolean repOK() {

        if (id <= 0){
            return false;
        }if(title == null || title.trim().isEmpty()){
            return false;
        }
        if(artist == null || artist.trim().isEmpty()){
            return false;
        }
        if(copiesAvailable < 0){
            return false;
        }
        return true;
    }
    
    /**
     * devuelve la informacion del album de la siguiente forma
     * "el albumb con la ID: <id> es <title> del artista <artist>. hay <copiesAvailables> copias"  
     */
    @Override
    public String toString(){
        return "el albumb con la ID: " + id + " es " + title + " del artista " + artist + ". hay " + copiesAvailable + " copias";
    }
    
    /**
     * Cuenta el número de palabras en un título de álbum.
     * @param title el título del álbum
     * @return el número de palabras en el título
     */
    public int countWords(String title) {
        if(title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("titulo invalido");
        }
        return title.split("\\s+").length;
    }
}
