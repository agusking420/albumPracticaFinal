import java.util.ArrayList;
import java.util.Iterator;

/**
 * Administra los álbumes de una biblioteca de música.
 * MusicLibrary representa una biblioteca de música, con su nombre, capacidad máxima de la biblioteca
 * (cuántos álbumes puede alojar), y lista de álbumes de la biblioteca.
 * 
 * @version 0.1
 */
public class MusicLibrary {
    // nombre de la biblioteca de música
    private String name;
    // capacidad de la biblioteca de música (cuántos álbumes puede alojar como máximo)
    private int albumCapacity;
    // La lista de álbumes de la biblioteca de música
    private ArrayList<Album> albums;

    /**
     * Constructor de MusicLibrary. 
     * el nombre no puede ser nulo ni vacion. la capacidad maxima del album debe ser positiva.
     */
    public MusicLibrary(String name, int albumCapacity) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("name no pede ser nulo ni vacio");
        }
        if(albumCapacity <= 0){
            throw new IllegalArgumentException("la capacidad maxima del album debe ser positiva");
        }
        this.name = name;
        this.albumCapacity = albumCapacity;
        this.albums = new ArrayList<Album>();
    }

    /**
     * Agrega un álbum a la biblioteca de música
     * @param album es el álbum a agregar
     * Precondición: Agregar el álbum no debe exceder la capacidad de la biblioteca (ejemplares de los álbumes existentes 
     * más los nuevos ejemplares no debe superar la capacidad).
     * Precondición: No debe haber en la biblioteca un álbum con el mismo id que el que se agrega.
     * Postcondición: se agrega el álbum a la lista de álbumes de la biblioteca. La lista de álbumes se debe mantener
     * ordenada por id, con lo cual la inserción debe ubicar el álbum en la posición ordenada correspondiente.
     */
    public void addAlbum(Album album) {
        //verifica que la biblioteca no este llena
        int totalCopies = 0;
        for(Album a : albums){
            totalCopies += a.getCopiesAvailable();
        }
        if(totalCopies + album.getCopiesAvailable() >= albumCapacity){
                throw new IllegalArgumentException("la biblioteca esta llena");
        }
        
        //verifica que no haya un album con la misma ID
        for(Album a : albums){
            int currId = a.getId();
            if(currId == album.getId()){
                throw new IllegalArgumentException("ya hay un album con esa ID");
            }
        }
        //agrega un album en orden segun su ID
        int i = 0;
        while(i < albums.size() && albums.get(i).getId() < album.getId()){
            i++;
        }
        albums.add(i, album);
    }

    /**
     * Presta una copia de un álbum de la biblioteca de música
     * @param id es el id del álbum a prestar.
     * Precondición: Existe un álbum con el id indicado, y hay al menos una copia en existencia para prestar.
     * Postcondición: Se actualiza el número de copias en existencia del álbum correspondiente, disminuyendo la
     * cantidad de copias disponibles en uno.
     * Importante: Este algoritmo debe tener complejidad O(log n) en el peor caso.
     */
    public void lendAlbum(int id) {
        if(id <= 0){
            throw new IllegalArgumentException("la id es invalida");
        }
        int low = 0;
        int high = albums.size() - 1;
        boolean lend = false;
        while(low <= high && !lend){
            int mid = (low + high)/2;
            Album curr = albums.get(mid);
            if(albums.get(mid).getId() == id){
                if( curr.getCopiesAvailable() <= 0){
                    throw new IllegalArgumentException("no hay copias suficientes para el prestamo");
                }
                lend = true;
                curr.lendCopy();
            }
            else if(albums.get(mid).getId() < id){
                low = mid + 1;
            }
            else {
                high = mid - 1;
            }
        }
        if(!lend){
            throw new IllegalArgumentException("no se encontro un album con esa ID");
        }
    }


    /**
     * Retorna el álbum de la biblioteca con la mayor cantidad de palabras en el título.
     * Precondición: debe haber al menos un álbum almacenado en la biblioteca.
     * @return el álbum (objeto) con mayor cantidad de palabras entre los de la biblioteca.
     * Si hay más de uno con la misma cantidad máxima de palabras, debe retornarse el primero de la lista entre ellos (es decir, el de id más chico).
     */
    public Album mostWordsInTitle() {
        if(albums.size() <= 0){
            throw new IllegalArgumentException("no hay albumes en la biblioteca");
        }
        
        Album primerAlbum = albums.get(0);
        int cantWords = primerAlbum.countWords(primerAlbum.getTitle());
        for(int i = 1 ; i < albums.size() ; i++){
            Album currAlbum = albums.get(i);
            int cantW = currAlbum.countWords(currAlbum.getTitle());
            if(cantW > cantWords){
                primerAlbum = currAlbum;
            }
        }
        return primerAlbum;
    }

    /**
     * Elimina de la biblioteca de música todos los álbumes cuyo artista coincida con el artista pasado como parámetro.
     * @param artist es el artista cuyos álbumes hay que eliminar.
     * Precondición: el parámetro artist debe ser no nulo y no vacío.
     */
    public void deleteAlbumsWithArtist(String artist) {
        if(artist == null || artist.trim().isEmpty()){
            throw new IllegalArgumentException("artista invalido");
        }
        int i = 0;
        while(i < albums.size()){
            if(albums.get(i).getArtist().equals(artist)){
            albums.remove(i);
            }else{
            i++;
            }
        }
    }
    
    public void deleteAlbumsWithArtist2(String artist) {
        if(artist == null || artist.trim().isEmpty()){
            throw new IllegalArgumentException("artista invalido");
        }
        Iterator <Album> it = albums.iterator();
        while(it.hasNext()){
            if(it.next().getArtist().equals(artist)){
                it.remove();
            }
        }
    }

    /**
     * Invariante de clase de MusicLibrary. Una biblioteca de música se considera válida, o internamente consistente,
     * si su nombre no es nulo ni vacío, la capacidad es mayor a cero, la lista de álbumes no contiene null, 
     * los álbumes están ordenados crecientemente por id, cada álbum es internamente consistente (satisface su respectivo repOK()), 
     * y la suma de ejemplares de los álbumes no excede la capacidad de la biblioteca.
     * @return true si y sólo si el objeto satisface el invariante de clase.
     */
    public boolean repOK() {
        //nombre sea valido
        if(name == null || name.trim().isEmpty()){
            return false;
        }
        //capacidad sea valida
        if(albumCapacity <= 0){
            return false;
        }
        //no contenga nulos y los objetos sean internamente consistentes
        int copiesTotales = 0;
        for(Album a : albums){
            if( a == null || !a.repOK()){
            return false;
            }
            //la cantidad de objetos de la lista no sea mayor a la capacidad
            copiesTotales += a.getCopiesAvailable();
        }
        if(copiesTotales > albumCapacity){
            return false;
            }
        //esten ordenados por id
        for(int i = 0 ; i < albums.size() - 1 ; i++){
            if(albums.get(i).getId() >= albums.get(i + 1).getId()){
            return false;
            }
        }
        return true;
    }
}
