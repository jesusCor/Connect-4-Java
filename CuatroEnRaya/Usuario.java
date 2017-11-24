//Seleccionamos el paquete en el que se encuentran nuestras clases.
package CuatroEnRaya;

//Importamos la interfaz Serializable para poder implementarla posteriormente.
import java.io.Serializable;

/**
 * Clase Usuario.
 * @author Jesús Coronado y Federico Grande.
 */
public class Usuario implements Serializable {

    /**
     * Dato miembro de tipo String para almacenar el nombre del usuario.
     */
    private String nombre;

    /**
     * Datos miembro de tipo entero para almacenar el numero de partidas ganadas,
     * perdidas y jugadas.
     */
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasJugadas;


    /**
     * Constructor de la clase Usuario.
     * @param nombre Recibe el nombre del usuario.
     */
    public Usuario(String nombre){
        this.nombre=nombre;
        partidasGanadas=0;
        partidasPerdidas=0;
        partidasJugadas=0;
    }


    /**
     * Metodo Setter para asignar el nombre del usuario.
     * @param nombre Recibe el nombre del usuario.
     */
    public void asignarNombre(String nombre){
        this.nombre=nombre;
    }


    /**
     * Método getter para devolver el nombre del usuario.
     * @return Devuelve el nombre del usuario.
     */
    public String devolverNombre(){
        return nombre;
    }

    /**
     * Método getter para devolver el número de partidas ganadas del usuario.
     * @return Devuelve el número de partidas ganadas del usuario.
     */
    public int devolverGanadas(){
        return partidasGanadas;
    }

    /**
     * Método getter para devolver el número de partidas perdidas del usuario.
     * @return Devuelve el número de partidas perdidas del usuario.
     */
    public int devolverPerdidas(){
        return partidasPerdidas;
    }

    /**
     * Método getter para devolver el número de partidas jugadas del usuario.
     * @return Devuelve el número de partidas jugadas del usuario.
     */
    public int devolverJugadas(){
        return partidasJugadas;
    }

    /**
     * Método para inicializar los datos miembros de las partidas ganadas, perdidas
     * y jugadas a cero.
     */
    public void reiniciarEstadisticas(){
        partidasGanadas=0;
        partidasPerdidas=0;
        partidasJugadas=0;
    }

    /**
     * Método para aumentar en una unidad el número de partidas ganadas.
     */
    public void aumentarGanadas(){
        partidasGanadas++;
    }

    /**
     * Método para aumentar en una unidad el número de partidas perdidas.
     */
    public void aumentarPerdidas(){
        partidasPerdidas++;
    }

    /**
     * Método para aumentar en una unidad el número de partidas jugadas.
     */
    public void aumentarJugadas(){
        partidasJugadas++;
    }


    /**
     * Implementación del metodo equals para comparar dos usuarios por su nombre.
     * @param u Recibe un objeto de la clase Usuario.
     * @return Devuelve true si el nombre coincide con el del usuario pasado por
     * argumento, o false en caso contrario.
     */

    public boolean equals(Usuario u){
        boolean resultado=false;
        if(nombre.equals(u.nombre)==true){
            resultado=true;
        }
        return resultado;
    }
    
}