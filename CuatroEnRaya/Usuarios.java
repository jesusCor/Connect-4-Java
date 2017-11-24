//Seleccionamos el paquete en el que se encuentran nuestras clases.
package CuatroEnRaya;

//Importamos las clases necesarias
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * Clase Usuarios.
 * @author Jesús Coronado y Federico Grande.
 */
public class Usuarios implements Serializable{

    /**
     * Dato miembro que almacena una lista de objetos de la clase Usuario, que
     * representa la lista de usuarios del juego.
     */
    private ArrayList<Usuario>listaUsuarios;

    /**
     * Constructor de la clase Usuarios.
     */
    public Usuarios(){
        listaUsuarios=new ArrayList<Usuario>();
    }

    /**
     * Método getter para devolver la lista de usuarios.
     * @return Devuelve la lista de usuarios.
     */
    public ArrayList <Usuario> devolverListaUsuarios(){
        return listaUsuarios;
    }


    //Este método es necesario para guardar todos los usuarios antes de cerrar
    //el juego.

    /**
     * Metodo para guardar la lista de usuarios en un archivo.
     */
    public void guardarUsuarios(){
        //Declaración de las variables.
        FileOutputStream flujo=null;
        ObjectOutputStream flujoObjeto=null;
        Usuario usuario;

        //Escribimos cada uno de los usuarios en el flujo.
        try{
            flujo=new FileOutputStream(new File("listaUsuarios"));
            flujoObjeto=new ObjectOutputStream(flujo);
            for(int i=0; i<listaUsuarios.size(); i++){
               usuario=listaUsuarios.get(i);
               flujoObjeto.writeObject(usuario);
               System.out.println("Se guarda el usuario: "+usuario.devolverNombre());
            }

            flujoObjeto.close();
        }
        catch(Exception e){
            System.out.println("Error al guardar los usuarios");
        }

    }


    //Este método es llamado automáticamente al iniciar la aplicación para cargar
    //la lista de usuarios.

    /**
     * Metodo para cargar la lista de usuarios.
     */
    public void cargarUsuarios(){
        //Declaración de las variables.
        FileInputStream archivo=null;
        ObjectInputStream flujo=null;
        Usuario usuario;
        listaUsuarios=new ArrayList<Usuario>();

        //Vamos añadiendo cada uno de los usuarios almacenados en el dato miembro
        //de la lista de usuarios.
        try{
            archivo=new FileInputStream(new File("listaUsuarios"));
            flujo=new ObjectInputStream(archivo);
            while((usuario=(Usuario)flujo.readObject())!=null){
                listaUsuarios.add(usuario);
            }
            flujo.close();
        }
        catch(Exception e){
            System.out.println("Error al cargar los usuarios");
        }
    }

    /**
     * Método para añadir un usuario a la lista de usuarios.
     * @param usuario Recibe usuario que se desea añadir a la lista.
     */
    public void crearUsuario(Usuario usuario){
        //Busca en la lista de usuarios uno cuyo nombre coincida con el introducido:
        //en ese caso se mostrará un mensaje de aviso; en caso contrario, se añade
        //el usuario a la lista.
        boolean encontrado=false;
        for(int i=0;i<listaUsuarios.size(); i++){
            Usuario referencia= listaUsuarios.get(i);
            if(referencia.devolverNombre().equalsIgnoreCase(usuario.devolverNombre())){
                JOptionPane.showMessageDialog(new JFrame(),"El usuario introducido ya existe");
                encontrado=true;
                break;
            }
        }
        if(encontrado==false){
            listaUsuarios.add(usuario);
            JOptionPane.showMessageDialog(new JFrame(),"El usuario introducido ha sido dado de alta");
        }
    }

    /**
     * Método para borrar un usuario de la lista de usuarios.
     * @param usuario Recibe el usuario que se desea borrar de la lista.
     */
    public void borrarUsuario(Usuario usuario){
        //Busca en la lista de usuarios uno cuyo nombre coincida con el introducido:
        //en ese caso se borrará el usuario de la lista; en caso contrario, se
        //mostrará un mensaje de aviso.
        boolean encontrado=false;
            for(int i=0;i<listaUsuarios.size(); i++){
                Usuario referencia= listaUsuarios.get(i);
                if(referencia.devolverNombre().equalsIgnoreCase(usuario.devolverNombre())){
                    JOptionPane.showMessageDialog(new JFrame(),"El usuario ha sido borrado correctamente");
                    listaUsuarios.remove(referencia);
                    encontrado=true;
                    break;
                }
            }
            if(encontrado==false){
                JOptionPane.showMessageDialog(new JFrame(),"No existe ningún usuario con el nombre introducido");
            }
    }


    /**
     * Método para seleccionar un usuario de la lista.
     * @param usuario Recibe el usuario que queremos seleccionar.
     */
    public void seleccionarUsuario(Usuario usuario){

        //Busca en la lista de usuarios uno cuyo nombre coincida con el introducido:
        //en ese caso de que el usuario pasado por argumento no esté en la lista,
        //lo añadimos nosotros.
        boolean encontrado=false;

        for(int i=0;i<listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).devolverNombre().equalsIgnoreCase(usuario.devolverNombre())){
                encontrado=true;
                break;
            }
        }
        if(encontrado==false){
            listaUsuarios.add(usuario);
            JOptionPane.showMessageDialog(new JFrame(),"El usuario introducido ha sido dado de alta");
        }
    }

    /**
     * Método para actualizar las estadísticas de un usuario.
     * @param usuario Recibe el usuario.
     * @param gana Recibe un indicador: true si el usuario ha ganado, o false en
     * caso de que haya perdido.
     */
    public void actualizarEstadisticas(Usuario usuario, boolean gana){

        if (gana){
            for(int i=0;i<listaUsuarios.size(); i++){
                Usuario referencia=listaUsuarios.get(i);
                if(referencia.devolverNombre().equalsIgnoreCase(usuario.devolverNombre())){
                    referencia.aumentarGanadas();
                    referencia.aumentarJugadas();
                }
            }
        }
        else{
            for(int i=0;i<listaUsuarios.size(); i++){
                Usuario referencia=listaUsuarios.get(i);
                if(referencia.devolverNombre().equalsIgnoreCase(usuario.devolverNombre())){
                    referencia.aumentarPerdidas();
                    referencia.aumentarJugadas();
                }
            }

        }

    }


    /**
     * Método para actualizar las estadísticas del usuario en caso de que haya empatado.
     * @param usuario Recibe el usuario.
     */
    public void partidaEmpatada(Usuario usuario){

        //En caso de que el usuario haya empatado, llamamos al método aumentarJugadas()
        //de la clase Usuario.
        for(int i=0;i<listaUsuarios.size(); i++){
            Usuario referencia=listaUsuarios.get(i);
            if(referencia.devolverNombre().equalsIgnoreCase(usuario.devolverNombre())){
                referencia.aumentarJugadas();
            }
        }
    }

}