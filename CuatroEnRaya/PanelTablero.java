//Seleccionamos el paquete en el que se encuentran nuestras clases.
package CuatroEnRaya;

//Importamos las clases necesarias.
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.JPanel;


/**
 * Clase PanelTablero que deriva de JPanel e implementa la interfaz Vista.
 * @author Jesús Coronado y Federico Grande
 */
public class PanelTablero extends JPanel implements Vista{

    //Declaración de los datos miembros.

    /**
     * Datos miembros de tipo entero que almacenan filas, columnas, xSupIzq,
     * ySupIzq.
     */
    private int filas,columnas;
    private int xSupIzq, ySupIzq;

    //Declaramos como pública la constante dimensionCasilla ya que, al tratarse
    //de una constante, no corremos el riesgo de modificación de su valor, y así
    //facilitamos el uso de dicha constante.

    /**
     * Constante de tipo entero para almacenar la dimensión de la casilla.
     */
    public final static int dimensionCasilla=50;

    /**
     * Dato miembro que almacena una lista de objetos de la clase Line2D.
     */
    private ArrayList<Line2D.Float>lineasDelimitadoras;

    /**
     * Datos miembros que almacena un objeto de la clase Controlador,
     * gestorEventosRaton; y un objeto de la clase Tablero, tablero.
     * Gracias a estos datos miembros mantenemos la relación de asociación
     * de esta clase con las mencionadas.
     */
    private Controlador gestorEventosRaton;
    private Tablero tablero;


    /**
     * Constructor de la clase PanelTablero.
     * @param filas Recibe la fila.
     * @param columnas Recibe la columna.
     */
    public PanelTablero(int filas,int columnas){
        this.filas=filas;
        this.columnas=columnas;

        //Se crea el array de lineas.
        lineasDelimitadoras=new ArrayList<Line2D.Float>();

        //Se fija la dimension.
        setPreferredSize(new Dimension(filas*dimensionCasilla,columnas*dimensionCasilla));

        //Se crean las lineas delimitadoras, llamando al método crearLineasDelimitadoras()
        //de esta misma clase.
        crearLineasDelimitadoras();
    }


    /**
     * Método que llama a su vez al método toString() de la clase Tablero, para imprimir
     * por pantalla los datos de un tablero.
     */
    public void imprimirTablero(){
        System.out.println(tablero.toString());
    }


    /**
     * Metodo setter para asignar un tablero.
     * @param tablero Recibe un objeto de la clase Tablero.
     */
    public void asignarTablero(Tablero tablero){
        this.tablero=tablero;
        this.imprimirTablero();
    }


    /**
     * Metodo setter para asignar un controlador.
     * @param gestorEventosRaton Recibe un objeto de la clase Controlador.
     */
    public void asignarControlador(Controlador gestorEventosRaton){
        this.gestorEventosRaton=gestorEventosRaton;
    }


     /**
      * Metodo painComponent para realizar los pintados del tablero y lineas.
      * @param g Recibe un objeto de la clase Graphics.
      */
     @Override
     public void paintComponent(Graphics g){

         //Hacemos uso del constructor de la clase base y del constructor de
         //la clase actual.
         super.paintComponent(g);
         Graphics2D g2=(Graphics2D)g;

         //Fijamos el color y la dimensión del fondo negro.
         g2.setColor(Color.BLACK);
         g2.fillRect(0, 0, 800, 800);

         //Fijamos el color de las líneas delimitadores y las dibujamos.
         g2.setColor(Color.BLUE);
         for(Line2D.Float linea: lineasDelimitadoras){
             g2.draw(linea);
         }

         //Pintamos las fichas que estén presentes en el tablero haciendo
         //una llamada al metodo pintarValor() de esta clase.
         for(int i=0; i<filas; i++){
             for(int j=0; j<columnas; j++){
                 if(tablero.obtenerValor(i,j)!=Tablero.VACIA){
                     pintarValor(g,i,j,tablero.obtenerValor(i,j));
                 }
             }
         }
     }


    /**
     * Metodo para crear las lineas delimitadoras del tablero de juego.
     */
     public void crearLineasDelimitadoras(){

         //Declaración de las variables del método.
         Line2D.Float linea;
         int x1,y1,x2,y2;

         //Creamos las diferentes líneas verticales de nuestro tablero.
         for(int i=0; i < columnas+1; i++){
             x1=i*dimensionCasilla;
             y1=0;
             x2=x1;
             y2=filas*dimensionCasilla;
             linea=new Line2D.Float(x1,y1,x2,y2);
             lineasDelimitadoras.add(linea);
         }

         //Creamos las diferentes lineas horizontales de nuestro tablero.
         for(int i=0; i < filas+1; i++){
             x1=0;
             y1=i*dimensionCasilla;
             x2=filas*dimensionCasilla;
             y2=y1;
             linea=new Line2D.Float(x1,y1,x2,y2);
             lineasDelimitadoras.add(linea);
         }
     }



     /**
      * Implementación del método modelChanged() de la interfaz Vista.
      */
     public void modelChanged(){
         this.repaint();
     }



    /**
     * Metodo para colorear las fichas del tablero.
     * @param g Recibe objeto de la clase Graphics.
     * @param fila Recibe la fila.
     * @param columna Recibe la columna.
     * @param valor Recibe el valor que indica si la ficha es amarilla o roja.
     */
     public void pintarValor(Graphics g,int fila,int columna,int valor){
         Graphics2D g2=(Graphics2D)g;

         //Estructura de control para asignar el color correspondiente en función
         //de la variable de tipo entero valor que pasemos por argumento.
         switch(valor){
             case Tablero.AMARILLA: g2.setColor(Color.YELLOW);
             break;
             case Tablero.ROJA: g2.setColor(Color.RED);
             break;
         }

         //Coloreamos la ficha en el lugar indicado.
         xSupIzq=columna*dimensionCasilla+5;
         ySupIzq=fila*dimensionCasilla+5;
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
         g2.fillOval(xSupIzq,ySupIzq,dimensionCasilla-10,dimensionCasilla-10);
         g2.drawOval(xSupIzq,ySupIzq,dimensionCasilla-10,dimensionCasilla-10);
     }

}
