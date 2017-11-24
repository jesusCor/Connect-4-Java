//Seleccionamos el paquete en el que se encuentran nuestras clases.
package CuatroEnRaya;

//Importamos las clases necesarias.
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * Clase Controlador que implementa las interfaces MouseListener y Serializable.
 * @author Jesús Coronado y Federico Grande.
 */
public class Controlador implements MouseListener, Serializable{

    //Declaración de los datos miembros.

    //Mediante los siguientes datos miembros mantenemos la relación de asociación
    //entre la clase Controlador y las distintas clases a las que pertenecen
    //los datos.

    /**
     * Datos miembros que almacenan un objeto de la clase Tablero, uno de la
     * clase Minimax, uno de la clase PanelTablero y uno último de la clase
     * Conecta 4.
     */
    private Tablero tablero;
    private Minimax minimax;
    private PanelTablero vistaConsola;
    private Conecta4 gestionAplicacion;




    /**
     * Constructor de la clase Controlador
     * @param vistaConsola Recibe un objeto de la clase PanelTablero.
     * @param tablero Recibe un objeto de la clase Tablero.
     * @param gestionAplicacion Recibe un objeto de la clase Conecta4.
     */
    public Controlador(PanelTablero vistaConsola,Tablero tablero,Conecta4 gestionAplicacion){
      this.tablero=tablero;
      this.vistaConsola=vistaConsola;
      this.gestionAplicacion=gestionAplicacion;
      //Creamos un objeto de la clase Minimax haciendo uso del constructor por
      //defecto y hacemos una llamada al método asignarPensandoJugada().
      minimax=new Minimax();
    }



    /**
     * Implementación del metodo mouseClicked de la interfaz MouseListener.
     * @param evento Objeto de la clase MouseEvent.
     */
    public void mouseClicked(MouseEvent evento){
        System.out.println("El evento mouseClicked activado");

        //Obtenemos la columna en la que hemos pinchado.
        int posx=evento.getX();
        int columna=posx/PanelTablero.dimensionCasilla;
        //En caso de que el metodo devolverPensandoJugada() de la clase Minimax
        //devuelva true, significa que la máquina está pensando la jugada, por lo
        //que NO podremos poner ficha hasta que termine de pensar.
        if (minimax.devolverPensandoJugada()){
            JOptionPane.showMessageDialog(new JFrame(), "¡Espere a que la máquina termine de pensar la jugada!");
        }
        else{
            //Vemos si es posible poner una ficha en la columna indicada llamando
            //al método fichaPosible() de la clase Tablero().
            boolean posible=tablero.fichaPosible(columna, tablero.devolverTurno());
            if(posible){
                //Vemos la fila en la que se ha colocado la ficha.
                int filaActual= tablero.filaActual(columna);
                //NÚMERO DE JUGADORES = 1 (jugamos contra la máquina).
                if (gestionAplicacion.devolverNumJugadores()==1){
                    //Vemos si con la ficha colocada hay un ganador. En ese caso
                    //mostramos un mensaje por pantalla y actualizamos las estadísticas
                    //del jugador.
                    boolean fin=tablero.quienGana(filaActual, columna);
                    if (fin){
                        if (tablero.devolverGanador()==1){
                            JOptionPane.showMessageDialog(new JFrame(),"¡Ganan las amarillas!");
                            gestionAplicacion.devolverUsuarios().actualizarEstadisticas(gestionAplicacion.devolverUsuario1(), true);
                        }
                        if (tablero.devolverGanador()==2){
                            JOptionPane.showMessageDialog(new JFrame(),"¡Ganan las rojas!");
                            gestionAplicacion.devolverUsuarios().actualizarEstadisticas(gestionAplicacion.devolverUsuario1(), false);
                        }
                        tablero.asignarGanador(0);
                        gestionAplicacion.iniciarPartida(tablero.devolverFilas(), tablero.devolverColumnas());
                    }
                    //Vemos si se ha producido un empate.
                    if(tablero.devolverGanador()==-1){
                        JOptionPane.showMessageDialog(new JFrame(),"¡Se ha producido un empate!");
                        gestionAplicacion.devolverUsuarios().partidaEmpatada(gestionAplicacion.devolverUsuario1());
                        gestionAplicacion.iniciarPartida(tablero.devolverFilas(), tablero.devolverColumnas());
                    }

                    //Llamamos al método lanzarMinimax().
                    lanzarMinimax();
                }
                //NÚMERO DE JUGADORES= 2 (jugamos contra un adversario humano).
                if (gestionAplicacion.devolverNumJugadores()==2){
                    //Cambiamos el turno y vemos si con la ficha colocada hay un ganador.
                    //En ese caso mostramos un mensaje por pantalla y actualizamos
                    //las estadísticas de cada jugador.
                    tablero.cambiarTurno();
                    boolean fin=tablero.quienGana(filaActual, columna);
                    if (fin){
                        if (tablero.devolverGanador()==1){
                            JOptionPane.showMessageDialog(new JFrame(),"¡Ganan las amarillas!");
                            gestionAplicacion.devolverUsuarios().actualizarEstadisticas(gestionAplicacion.devolverUsuario1(), true);
                            gestionAplicacion.devolverUsuarios().actualizarEstadisticas(gestionAplicacion.devolverUsuario2(), false);
                        }
                        if (tablero.devolverGanador()==2){
                            JOptionPane.showMessageDialog(new JFrame(),"¡Ganan las rojas!");
                            gestionAplicacion.devolverUsuarios().actualizarEstadisticas(gestionAplicacion.devolverUsuario1(), false);
                            gestionAplicacion.devolverUsuarios().actualizarEstadisticas(gestionAplicacion.devolverUsuario2(), true);
                        }
                        tablero.asignarGanador(0);
                        gestionAplicacion.iniciarPartida(tablero.devolverFilas(), tablero.devolverColumnas());
                    }
                    //Vemos si se ha producido un empate.
                    if(tablero.verMatrizLlena()){
                        //Hemos comprobado primero si se gana con la última ficha, en caso contrario:
                        JOptionPane.showMessageDialog(new JFrame(),"¡Se ha producido un empate!");
                        gestionAplicacion.devolverUsuarios().partidaEmpatada(gestionAplicacion.devolverUsuario1());
                        gestionAplicacion.devolverUsuarios().partidaEmpatada(gestionAplicacion.devolverUsuario2());
                        gestionAplicacion.iniciarPartida(tablero.devolverFilas(), tablero.devolverColumnas());
                    }
                }
            }
        }
    }


    /**
     * Metodo lanzarMinimax para lanzar el minimax
     */
    public void lanzarMinimax(){
        System.out.println("Se lanza minimax......PROFUNDIDAD: "+gestionAplicacion.devolverProfundidadMinimax());
        //Creamos un objeto de la clase Minimax y hacemos una llamada al método run().
        minimax=new Minimax(tablero,gestionAplicacion.devolverProfundidadMinimax(), gestionAplicacion);
        minimax.start();
    }


    /**
     * Implementación del método mouseEntered de la clase MouseListener.
     * @param evento Objeto de la clase MouseEvent.
     */
    public void mouseEntered(MouseEvent evento){
        System.out.println("El evento mouseEntered activado");
    }


    /**
     * Implementación del método mouseExited de la clase MouseListener.
     * @param evento Objeto de la clase MouseEvent.
     */
    public void mouseExited(MouseEvent evento){
        System.out.println("El evento mouseExited activado");
    }



    /**
     * Implementación del método mousePressed de la clase MouseListener.
     * @param evento Objeto de la clase MouseEvent.
     */
    public void mousePressed(MouseEvent evento){
        System.out.println("El evento mousePressed activado");
    }



    /**
     * Implementación del método mouseReleased de la clase MouseListener.
     * @param evento Objeto de la clase MouseEvent.
     */
    public void mouseReleased(MouseEvent evento){
        System.out.println("El evento mouseReleased activado");
    }

}
