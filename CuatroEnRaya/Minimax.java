//Seleccionamos el paquete en el que se encuentran nuestras clases.
package CuatroEnRaya;

//Importamos las clases necesarias.
import java.util.Random;
import javax.swing.*;
import java.io.*;

/**
 * Clase Minimax.
 * @author Jesús Coronado y Federico Grande.
 */
public class Minimax extends Thread implements Serializable{

    //Datos miembro

    /**
     * Dato miembro de tipo booleano que indica si el método minimax está en ejecución
     * o no.
     */
    private boolean pensandoJugada;

    /**
     * Datos miembros de tipo entero para almacenar la profundidad máxima, y los jugadores
     * MAX y MIN en cada nodo.
     */
    private int maxProfundidad;
    private int jugadorMax;
    private int jugadorMin;

    /**
     * Datos miembros para almacenar un objeto de la clase Conecta4 y otro de la clase
     * Tablero, que permite mantener una relación de asociación con dichas clases.
     */
    private Conecta4 gestionAplicacion;
    private Tablero tablero;

    /**
     * Constantes de tipo entero para almacenar las jugadas de máxima y mínima puntuación.
     */
    private final static int MINIMO=-10000;
    private final static int MAXIMO=10000;
    


    /**
     * Constructor por defecto de la clase Minimax
     */
    public Minimax(){
        //Asignamos el valor false al dato miembro pensandoJugada.
        pensandoJugada=false;
    }
    
    /**
     * Constructor de la clase Minimax.
     * @param tablero Recibe el tablero.
     * @param maxProfundidad Recibe la máxima profundidad.
     * @param gestionAplicacion Recibe un objeto de la clase Conecta4.
     */
    public Minimax(Tablero tablero,int maxProfundidad, Conecta4 gestionAplicacion){
        this.tablero=tablero;
        this.maxProfundidad=maxProfundidad;
        this.gestionAplicacion=gestionAplicacion;
        //Asignamos el valor false al dato miembro pensandoJugada.
        pensandoJugada=false;
    }

    /**
     * Método getter para devolver el dato miembro pensandoJugada.
     * @return Devuelve el dato miembro pensandoJugada.
     */
    public boolean devolverPensandoJugada(){
        return pensandoJugada;
    }

    /**
     * Método para determinar si la capa es impar.
     * @param capa Recibe la capa.
     * @return Devueve true si la capa es impar (MIN), o false en caso contrario.
     */
    public static final boolean esCapaMIN(int capa) {
        return((capa % 2)==1); // es impar
    }

    /**
     * Método para determinar si la capa es par.
     * @param capa Recibe la capa.
     * @return Devuelve true si la capa es par (MAX), o false en caso contrario.
     */
    public static final boolean esCapaMAX(int capa) {
        return((capa % 2)==0); // es par
    }

    /**
     * Método para determinar cual es la jugada más prometedora.
     * @param tablero Recibe el tablero.
     * @param turno Recibel el turno.
     * @return Devuelve la columna donde se encuentra la mejor jugada.
     */
    public int buscarMovimiento(Tablero tablero, boolean turno){

        //Creamos un array de elementos de tipo booleanos que representa las
        //jugadas posibles.
        boolean [] jugadas= tablero.jugadasPosibles();

        //Creamos las diferentes variables que representan la mejor puntuación,
        //la columna donde se encuentra dicha puntuacion, los jugadores MAX y
        //MIN de cada nodo, la puntuación de cada jugada y las variables
        //alfa y beta de la poda,
        int mejorPuntuacion=MINIMO;
        int mejorColumna=-1;
        jugadorMax=tablero.determinarColor(turno);
        jugadorMin=tablero.determinarColor(!turno);
        int valorJugada;
        int alfa=MINIMO;
        int beta=MAXIMO;      

        //Recorremos todas las columnas posibles.
        for(int i=0;i<tablero.devolverColumnas();i++){
            if (jugadas[i]){
                //Creamos un nuevo tablero y comprobamos si existe un ganador.
                Tablero copiaTablero=new Tablero(tablero);
                copiaTablero.ponerFicha(i, turno);
                copiaTablero.quienGana(copiaTablero.filaActual(i), i);

                //Llamamos al metodo minimax cambiando el turno y fijando la capa a 1.
                valorJugada=minimax(copiaTablero,!turno,1, alfa, beta);
                System.out.println("Columna: "+i+". Valor jugada: "+valorJugada);

                //Por ahora no nos sirve el nuevo tablero, por lo que lo anulamos.
                copiaTablero=null;

                //Si la puntuación obtenida es mayor que la mejorPuntuacion actual,
                //actualizamos el valor de la variable.
                //Al estar en un nodo MAX, no hacemos poda en este nivel, por lo que
                //maximizamos el valor de alfa.
                if(valorJugada>mejorPuntuacion){
                    mejorPuntuacion=valorJugada;
                    mejorColumna=i;
                    alfa=mejorPuntuacion;
                }

            }
            
        }
        //Devolvemos la columna donde se encuentra la mejor jugada.
        return mejorColumna;
        
    }

    /**
     * Metodo recursivo minimax, para controlar el funcionamiento de la maquina a
     * partir del segundo nivel (capa 1).
     * @param tablero Recibe el tablero.
     * @param turno Recibe el turno.
     * @param profundidad Recibe la profundidad.
     * @param alfa Recibe alfa.
     * @param beta Recibe beta.
     * @return Devuelve la puntuación final.
     */

    public int minimax(Tablero tablero, boolean turno,int profundidad, int alfa, int beta){

        //Vemos los diferentes casos base.

        //Si tenemos un empate, devolvemos 0.
        if (tablero.devolverGanador()==-1){
            return 0;
        }

        //Si gana el jugador MAX, devolvemos la máxima puntuación.
        if (tablero.devolverGanador()==jugadorMax){
            return MAXIMO;
        }

        //Si gana el jugador MIN, devolvemos la mínima puntuación.
        if (tablero.devolverGanador()==jugadorMin){
            return MINIMO;
        }
        
        //Si hemos alcanzado la máxima profundidad, llamamos al método valoracionTablero
        //de la clase Tablero para obtener una puntuación del tablero.
        if((profundidad==maxProfundidad)){
            return tablero.valoracionTablero(turno);
        }

        //Creamos un array de elementos de tipo booleanos que representa las
        //jugadas posibles.
        boolean [] jugadas= tablero.jugadasPosibles();

        //Variable para almacenar la puntuación de cada jugada.
        int valorJugada;

        //Caso recursivo: recorremos las diferentes columnas.
        for(int i=0;i<tablero.devolverColumnas();i++){
            if (jugadas[i]){
                //Creamos un nuevo tablero y comprobamos ganador.
                Tablero copiaTablero=new Tablero(tablero);
                copiaTablero.ponerFicha(i, turno);
                copiaTablero.quienGana(copiaTablero.filaActual(i), i);

                //Llamamos al método minimax cambiando el turno y aumentando en
                //una unidad el valor de la variable profundidad.
                valorJugada=minimax(copiaTablero,!turno, (profundidad+1), alfa, beta);

                //Por ahora no nos sirve el nuevo tablero, por lo que lo anulamos.
                copiaTablero=null;

                //Actualizamos los valores de puntuación, de alfa y de beta en función
                //de la capa usando la poda alfa-beta.

                //Si la capa es máxima, actualizamos alfa si el valor obtenido es mayor
                //que el valor anterior de alfa.
                if (esCapaMAX(profundidad)){
                    if (valorJugada>alfa){
                        alfa=valorJugada;
                    }
                    //Si alfa es mayor o igual que beta, podamos.
                    if (alfa>=beta){
                        return alfa;
                    }
                }
                //Si la capa es mínima, actualizamos beta si el valor obtenido es menor
                //que el valor anterior de beta.
                else{
                    if (valorJugada<beta){
                        beta=valorJugada;
                    }
                    //Si beta es menor o igual que alfa, podamos.
                    if (beta<=alfa){
                        return beta;
                    }
                }
            }
        }

        //Si la capa es MAX, devolvemos alfa; en caso contrario (MIN), devolvemos beta.
        if (esCapaMAX(profundidad)){
            return alfa;
        }
        else{
            return beta;
        }
    }



    /**
     * Metodo run para comenzar el funcionamiento del minimax.
     */
    public void run(){

        //Creamos un objeto de la clase Random para obtener un valor aleatorio.
        Random generador= new Random();

        //Asignamos el valor true al dato miembro pensandoJugada hasta que el
        //método buscarMovimiento llegue a su fin.
        pensandoJugada=true;
        int mejorColumna=buscarMovimiento(tablero,false);
        pensandoJugada=false;
        System.out.println("Mejor jugada elegida por la máquina= "+mejorColumna);

        //Si la columna obtenida es -1, colocamos una ficha en una columna libre
        //aleatoria dentro del tablero.
        if(mejorColumna==-1){
            do{
                mejorColumna=generador.nextInt(tablero.devolverColumnas());
            }while (!tablero.columnaLibre(mejorColumna));
        }

        //Vemos si con la ficha colocada hay un ganador. En ese caso
        //mostramos un mensaje por pantalla y actualizamos las estadísticas
        //del jugador.
        tablero.fichaPosible(mejorColumna, false);
        int filaActual= tablero.filaActual(mejorColumna);
        boolean fin=tablero.quienGana(filaActual, mejorColumna);
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

        //En caso de que se haya producido un empate, mostramos un mensaje de aviso
        //y actualizamos las estadísticas.
        if(tablero.devolverGanador()==-1){
            JOptionPane.showMessageDialog(new JFrame(),"¡Se ha producido un empate!");
            gestionAplicacion.devolverUsuarios().partidaEmpatada(gestionAplicacion.devolverUsuario1());
            gestionAplicacion.iniciarPartida(tablero.devolverFilas(), tablero.devolverColumnas());
        }
    }
}
