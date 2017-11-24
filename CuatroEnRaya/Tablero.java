//Seleccionamos el paquete en el que se encuentran nuestras clases.
package CuatroEnRaya;

//Importamos la interfaz Serializable para poder implementarla posteriormente.
import java.io.Serializable;

/**
 * Clase Tablero.
 * @author Jesús Coronado y Federico Grande.
 */
public class Tablero implements Serializable{

    //Declaración de los datos miembros.

    /**
     * Dato miembro que almacena un array de enteros, matrizLogica.
     */
    private int[][]matrizLogica;

    /**
     * Datos miembro de tipo entero que almacenan el color, el número de filas del
     * tablero, el número de columnas y el ganador.
     */
    private int color, filas, columnas, ganador;

    //Declaramos como públicas la constantes referentes al valor de las casiilas
    //del tablero ya que, al tratarse de una constante, no corremos el riesgo de
    //modificación de su valor, y así facilitamos el uso de dicha constante.

    /**
     * Constantes de tipo entero para almacenar el valor de las casillas del tablero.
     */
    public final static int VACIA=0;
    public final static int AMARILLA=1;
    public final static int ROJA=2;
    public final static int EMPATE=-1;


    /**
     * Datos miembro de tipo boolean que almacenan, turno, y las constantes
     * HUMANO y MAQUINA
     */
    private boolean turno;
    private final static boolean HUMANO=true;
    private final static boolean MAQUINA=false;

    /**
     * Dato miembro que almacena un objeto de la clase VistaPartida vistaPartida
     */

    private PanelTablero vistaConsola;

    /**
     * Constructor de la clase Tablero.
     * @param filas Recibe las filas.
     * @param columnas Recibe las columnas.
     */
    public Tablero(int filas,int columnas){
        this.filas=filas;
        this.columnas=columnas;

        //Creamos la matrizLogica con las dimensiones del tablero.
        matrizLogica=new int[filas][columnas];

        //Inicializamos el turno humano y asignamos el valor 0 a la variable
        //ganador (lo cual indica que en dicho tablero no existe aún ningún ganador).
        turno=HUMANO;
        ganador=0;
    }


    /**
     * Constructor de la clase Tablero para copiar los datos de un tablero en otro.
     * @param tablero Recibe un objeto de la clase Tablero.
     */
    public Tablero(Tablero tablero) {
        //Copiamos todos los datos del tablero pasado por argumento en los distintos
        //datos miembros de la clase, e inicializamos la variable ganador a 0.
        this(tablero.filas,tablero.columnas);
        for(int i=0; i < filas; i++){
            for(int j=0; j < columnas; j++){
                matrizLogica[i][j]=tablero.obtenerValor(i, j);
            }
        }
        this.turno=tablero.turno;
        ganador=0;
    }

    /**
     * Método setter para asignar el número de filas del tablero.
     * @param filas Recibe el número de filas.
     */
    public void asignarFilas(int filas){
        this.filas=filas;
    }

    /**
     * Método setter para asignar el número de columnas del tablero.
     * @param columnas Recibe el número de columnas.
     */
    public void asignarColumnas(int columnas){
        this.columnas=columnas;
    }
    
    /**
     * Método getter para devolver el número de filas.
     * @return Devuelve el número de filas.
     */
    public int devolverFilas(){
        return filas;
    }

    /**
     * Método getter para devolver el número de columnas.
     * @return Devuelve el número de columnas.
     */
    public int devolverColumnas(){
        return columnas;
    }

    /**
     * Método setter para asignar un ganador.
     * @param ganador Recibe el ganador.
     */
    public void asignarGanador(int ganador){
        this.ganador=ganador;
    }

    /**
     * Método getter para devolver el ganador.
     * @return Devuelve el ganador.
     */
    public int devolverGanador(){
        return ganador;
    }

    /**
     * Método getter para devolver el turno.
     * @return Devuelve el turno.
     */
    public boolean devolverTurno(){
        return turno;
    }

    /**
     * Metodo Setter para asignar un objeto de la clase PanelTablero.
     * @param vistaConsola Recibe un objeto de la clase PanelTablero.
     */
    public void asignarVista(PanelTablero vistaConsola){
        this.vistaConsola=vistaConsola;
    }


    /**
     * Metodo para determinar el color de la ficha en funcion del turno que recibe
     * @param turno Recibe el turno.
     * @return Devuelve el color: amarillo si el turno es del HUMANO, y rojo si
     * el turno es de la MAQUINA.
     */
    public int determinarColor(boolean turno) {
        color=0;
        if(turno==HUMANO){
            color=Tablero.AMARILLA;
        }
        if(turno==MAQUINA){
            color=Tablero.ROJA;
        }
        return color;
    }


    /**
     * Método para hacer un pintado de una ficha en el tablero en caso de se posible.
     * @param columna Recibe la columna donde queremos pintar la ficha.
     * @param turno Recibe el turno.
     * @return Devuelve true si se ha pintado la ficha y false en caso contrario.
     */
    public boolean fichaPosible(int columna, boolean turno){

        color=determinarColor(turno);
        boolean posible=false;
        for (int i=columnas-1; i>=0; i--){
            if (matrizLogica[i][columna]==0){
                matrizLogica[i][columna]=color;
                vistaConsola.repaint();
                posible=true;
                break;
            }
        }

        return posible;
    }

    /**
     * Método para pintar las fichas del tablero.
     */
    public void pintarFichas(){
        //Para pintar todas las fichas del tablero hacemos una llamada el método
        //modelChanged() de la clase PanelTablero.
        vistaConsola.modelChanged();
    }

    /**
     * Método que estudia si una columna tiene algún hueco libre o no.
     * @param columna Recibe la columna.
     * @return Devuelve false si la columna está llena, o true en caso contrario.
     */
    public boolean columnaLibre(int columna){

        boolean libre=false;

        //Si en la columna indicada existe alguna casilla cuyo valor sea 0,
        //asignamos true a la variable libre.
        for (int i=columnas-1; i>=0; i--){
            if (matrizLogica[i][columna]==0){
                libre=true;
                break;
            }
        }

        return libre;
    }

    /**
     * Método que evalúa las columnas donde es posible colocar una ficha.
     * @return Devuelve un array de variables de tipo boolean: false si la columna
     * está llena, o true en caso contrario.
     */
    public boolean[] jugadasPosibles() {

        //Creamos nuestro array y lo inicializamos todo a false.
        boolean [] jugadas = new boolean[columnas];

        for (int i=0; i<jugadas.length; i++){
            jugadas[i]=false;
        }

        //Hacemos sucesivas llamadas al método columnaLibre() para cada columna y
        //asignamos el valor correspondiente a cada posición de nuestro array.
        for (int j=0; j<columnas; j++){
            jugadas[j]=columnaLibre(j);
        }

        return jugadas;
    }

    /**
     * Método para ver cual es la fila dentro de la columna indicada donde se
     * colocará la ficha.
     * @param columna Recibe la columna.
     * @return Devuelve la fila donde se colocaría la ficha.
     */
    public int filaActual(int columna){

        //Mediante un bucle recorremos la columna indicada desde abajo hasta arriba
        //y devolvemos la posición donde encontramos el primer hueco.
        int fila=0;
        for (int i=columnas-1; i>=0; i--){
            if (matrizLogica[i][columna]==0){
                fila=i+1;
                break;
            }
        }

        return fila;
    }

    /**
     * Método para asignar un valor a una casilla de la matrizLogica.
     * @param columna Recibe la columna.
     * @param turno Recibe el turno.
     */
    public void ponerFicha(int columna, boolean turno){

        //Determinamos el valor que colocaremos en la casilla haciendo una llamada
        //al método determinarColor().
        color=determinarColor(turno);

        //Estudiamos la fila donde colocar la ficha haciendo una llamada al metodo
        //filaActual() y le asignamos el valor anterior a la casilla en cuestión.
        int fila=(filaActual(columna)-1);
        matrizLogica[fila][columna]=color;
         
    }


    /**
     * Metodo para comprobar si la matriz está llena.
     * @return Devuelve true si la matriz está llena, o false en caso contrario.
     */
    public boolean verMatrizLlena(){

        //Variable local para indicar si esta llena o no.
        boolean llena=true;

        //Recorremos la matrizLogica.
        for (int i=0; i<columnas;i++){
            for(int j=0; j<matrizLogica[i].length; j++){
                if(matrizLogica[i][j]==VACIA){
                    llena=false;
                    break;
                }
            }
        }

        //Si la matrizLogica está llena, asignamos el valor EMPATE al dato miembro
        //ganador.
        if (llena){
            ganador=EMPATE;
        }

        return llena;
    }


    /**
     * Método para ver si tras la ficha colocada tenemos un ganador.
     * @param fila Recibe la fila donde hemos colocado la ficha.
     * @param columna Recibe la columna donde hemos colocado la ficha.
     * @return Devuelve true si existe un ganador, o false en caso contrario.
     */
    public boolean quienGana(int fila,int columna){

        //Variable para indicar si tenemos o no ganador.
        boolean fin=false;

        //Para ver si tenemos un ganador vemos si tenemos 4 fichas de un color
        //consecutivas a partir de la posición indicada. Para ello miramos las
        //fichas en horizontal, en vertical, en diagonal hacia arriba y en
        //diagonal hacia abajo.

        //Si efectivamente hemos encontrado un ganador, asignamos al dato miembro
        //ganador el valor: 1 si ganan amarillas o 2 si ganan rojas; y devolvemos
        //true.

		//HORIZONTAL.
        int amarillas=0;
        int rojas=0;
        for(int i=0;i<columnas;i++){
            if(matrizLogica[fila][i]!=0){
                if(AMARILLA==matrizLogica[fila][i]){
                    amarillas++;
                }
                else{
                    amarillas=0;
                }
                if(amarillas==4){
                    ganador=AMARILLA;
                    fin=true;
                    return fin;
                }
                if(fin==false){
                    if(ROJA==matrizLogica[fila][i]){
                        rojas++;
                    }
                    else{
                        rojas=0;
                    }
                    if(rojas==4){
                        ganador=ROJA;
                        fin=true;
                        return fin;
                    }
                }
            }
            else{
                amarillas=0;
                rojas=0;
            }
        }

        //VERTICAL
        amarillas=0;
        rojas=0;
        for(int i=0;i<columnas;i++){
            if(matrizLogica[i][columna]!=0){
                if(AMARILLA==matrizLogica[i][columna]){
                    amarillas++;
                }
                else{
                    amarillas=0;
                }
                if(amarillas==4){
                    ganador=AMARILLA;
                    fin=true;
                    return fin;
                }
                if(fin==false){
                    if(ROJA==matrizLogica[i][columna]){
                        rojas++;
                    }
                    else{
                        rojas=0;
                    }
                    if(rojas==4){
                        ganador=ROJA;
                        fin=true;
                        return fin;
                    }
                 }
            }
            else{
                amarillas=0;
                rojas=0;
            }
        }

        //OBLICUO DE IZQUIERDA A DERECHA Y DE ARRIBA A ABAJO.
        amarillas=0;
        rojas=0;

        int f=fila;
        int c=columna;

        while (f>0 && c>0){
            f--;
            c--;
        }

        while (f<columnas && c<columnas){
            if(matrizLogica[f][c]!=0){
                if(AMARILLA==matrizLogica[f][c]){
                    amarillas++;
                }
                else{
                    amarillas=0;
                }
                if(amarillas==4){
                    ganador=AMARILLA;
                    fin=true;
                    return fin;
                }
                if(fin!=true){
                    if(ROJA==matrizLogica[f][c]){
                        rojas++;
                    }
                    else{
                        rojas=0;
                    }
                    if(rojas==4){
                        ganador=ROJA;
                        fin=true;
                        return fin;
                    }
                 }
            }
            else{
                amarillas=0;
                rojas=0;
            }

            f++;
            c++;

        }

        //OBLICUO DE IZQUIERDA A DERECHA Y DE ABAJO A ARRIBA.
        amarillas=0;
        rojas=0;

        f=fila;
        c=columna;

        while (f<(columnas-1) && c>0){
            f++;
            c--;
        }

        while (f>-1 && c<columnas){
            if(matrizLogica[f][c]!=0){
                if(AMARILLA==matrizLogica[f][c]){
                    amarillas++;
                }
                else{
                    amarillas=0;
                }
                if(amarillas==4){
                    ganador=AMARILLA;
                    fin=true;
                    return fin;
                }
                if(fin!=true){
                    if(ROJA==matrizLogica[f][c]){
                        rojas++;
                    }
                    else{
                        rojas=0;
                    }
                    if(rojas==4){
                        ganador=ROJA;
                        fin=true;
                        return fin;
                    }
                 }
            }
            else{
                amarillas=0;
                rojas=0;
            }

            f--;
            c++;
        }

        if (verMatrizLlena()){
            ganador=EMPATE;
        }
        else{
            ganador=0;
        }

        return fin;
    }


    /**
     * Metodo que devuelve un valor de la matrizLogica.
     * @param fila Recibe la fila.
     * @param columna Recibe la columna.
     * @return Devuelve el valor de la matrizLogica.
     */
    public int obtenerValor(int fila,int columna){
        return matrizLogica[fila][columna];
    }



    /**
     * Metodo para cambiar el turno actual.
     */
    public void cambiarTurno() {
      turno=!turno;
    }


    /**
     * Método para dar una puntuación al tablero usando nuestro primer criterio.
     * @param turno Recibe el turno.
     * @return Devuelve la puntuación del tablero.
     */
    public int evaluar1(boolean turno){

        //Este criterio consiste en dar mayor puntuación a las fichas pertenecientes
        //a la columna central.

        //Declaración de las variables.
        int valor=0;
        int color1=determinarColor(turno);
        int color2=determinarColor(!turno);
        int contador1=0;
        int contador2=0;

        //Estudiamos la posición central de nuestro tablero.
        int medio=(int)Math.floor(columnas/2);

        //Contamos las fichas de cada color y su valor en la primera mitad del tablero.
        for (int i=0; i<medio; i++){
            valor=i;
            for (int j=0; j<filas; j++){
                if (matrizLogica[j][i]==color1){
                    contador1=contador1+valor;
                }
                if (matrizLogica[j][i]==color2){
                    contador2=contador2+valor;
                }
            }
        }

        //Contamos las fichas de cada color y su valor en la segunda mitad del tablero.

        valor=medio;

        for (int i=medio; i<columnas; i++){
            for (int j=0; j<filas; j++){
                if (matrizLogica[j][i]==color1){
                    contador1=contador1+valor;
                }
                if (matrizLogica[j][i]==color2){
                    contador2=contador2+valor;
                }
            }

            valor--;
        }

        //Devolvemos la resta de ambos contadores.
        return (contador1-contador2);
    }


    /**
     * Método para asignar una puntuación al tablero usando nuestro segundo criterio.
     * @param turno Recibe el turno.
     * @return Devuelve la puntuación del tablero.
     */
    public int evaluar2(boolean turno){

        //Este criterio asigna una puntuación al tablero contando, a partir de
        //una casilla dada, el número de esquinas (de las 8 posibles) que contienen
        //una ficha del mismo color que la casilla. Para ello estudiamos las 8 esquinas
        //de cada casilla.

        //Declaración de las variables.
        int color1=determinarColor(turno);
        int contador1=0;
        int contador2=0;

        //Bucle que recorre todas las fichas del tablero.
        for (int i=0; i<columnas; i++){
            for (int j=0; j<filas; j++){
                if (matrizLogica[j][i]!=VACIA){

                    //ARRIBA.
                    if (j>0){
                        if (matrizLogica[j][i]==matrizLogica[j-1][i]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }

                    //ABAJO.
                    if (j<(filas-1)){
                        if (matrizLogica[j][i]==matrizLogica[j+1][i]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }

                    //IZQUIERDA.
                    if (i>0){
                        if (matrizLogica[j][i]==matrizLogica[j][i-1]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }

                    //DERECHA.
                    if (i<(columnas-1)){
                        if (matrizLogica[j][i]==matrizLogica[j][i+1]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }

                    //DIAGONAL SUPERIOR IZQUIERDA.
                    if (i>0 && j>0){
                        if (matrizLogica[j][i]==matrizLogica[j-1][i-1]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }

                    //DIAGONAL SUPERIOR DERECHA.
                    if (i<(columnas-1) && j>0){
                        if (matrizLogica[j][i]==matrizLogica[j-1][i+1]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }

                    //DIAGONAL INFERIOR IZQUIERDA.
                    if (i>0 && j<(filas-1)){
                        if (matrizLogica[j][i]==matrizLogica[j+1][i-1]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }

                    //DIAGONAL INFERIOR DERECHA.
                    if (i<(columnas-1) && j<(filas-1)){
                        if (matrizLogica[j][i]==matrizLogica[j+1][i+1]){
                            if (matrizLogica[j][i]==color1){
                                contador1++;
                            }
                            else{
                                contador2++;
                            }
                        }
                    }
                }
            }
        }

        //Devolvemos la resta de ambos contadores.
        return (contador1-contador2);

    }

    /**
     * Método que asigna una puntuación a la jugada en función de los dos
     * criterios anteriores.
     * @param turno Recibe el turno.
     * @return Devuelve la puntuación del tablero.
     */
    public int valoracionTablero(boolean turno){
        int resultado1=evaluar1(turno);
        int resultado2=evaluar2(turno);

        //Devolvemos la suma de la puntuación de los dos criterios.
        return (resultado1+resultado2);
    }





}