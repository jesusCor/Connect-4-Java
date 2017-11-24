//Seleccionamos el paquete en el que se encuentran nuestras clases.
package CuatroEnRaya;

//Importamos las clases necesarias

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


/**
 * Clase Conecta4 que deriva de JFrame e implementa la interfaz ActionListener.
 * @author Jesús Coronado y Federico Grande
 */
public class Conecta4 extends JFrame implements ActionListener{

    //Declaración de los datos miembros de la clase.

    /**
     * Datos miembros de la clase Tablero y PanelTablero para crear la relacion
     * de asociación con dichas clases.
     */
    private Tablero tablero;
    private PanelTablero vistaPartida;

    /**
     * Datos miembro de tipo entero que almacenan numero de Jugadores, el número
     * de jugadores de la partida recién guardada y la profundidad máxima del
     * minimax.
     */
    private int numJugadores, numJugadoresTemporal, profundidadMinimax;

    /**
     * Constante de tipo entero para almacenar la dimensión de la casilla.
     */
    private final static int dimensionCasilla=50;

    /**
     * Datos miembros que almacenan los JMenu barraMenu, menuPartida, menuNuevaPartida,
     * menuEstadisticas, menuAyuda, menuUsuario,opcionSalir, menuEstadisitcas, opcionAcerca, opcionAyuda
     */
    private JMenuBar barraMenu;
    private JMenu menuPartida;
    private JMenu menuNuevaPartida;
    private JMenu menuUnJugador;
    private JMenuItem opcionIniciado;
    private JMenuItem opcionMedio;
    private JMenuItem opcionAvanzado;
    private JMenuItem opcionMultijugador;
    private JMenuItem cargarPartida;
    private JMenuItem guardarPartida;
    private JMenuItem opcionSalir;
    private JMenu menuUsuario;
    private JMenu gestionUsuario;
    private JMenuItem crearUsuario;
    private JMenuItem borrarUsuario;
    private JMenuItem mostrarListaUsuarios;
    private JMenuItem menuEstadisticas;
    private JMenuItem mostrarEstadisticas;
    private JMenuItem reiniciarEstadisticas;
    private JMenu menuAyuda;
    private JMenuItem opcionAcerca;
    private JMenuItem opcionAyuda;


    // Lista de usuarios.

    /**
     * Dato miembro que almacena un objeto de la clase Usuarios.
     */
    private Usuarios usuarios;


    /**
     * Dos datos miembros de la clase Usuario para almacenar dos usuarios.
     */
    private Usuario usuario1;
    private Usuario usuario2;




    /**
     * Constructor de la clase Conecta4
     * @param filas Recibe la fila
     * @param columnas Recibe la columna
     */
    public Conecta4(int filas, int columnas){
        //Fijamos el color negro de fondo de la ventana.
        setBackground(Color.BLACK);

        //Cargamos la lista de usuarios almacenada.
        usuarios=new Usuarios();
        usuarios.cargarUsuarios();

        //Creamos los menús.
        crearMenu();

        //Fijamos la dimensión.
        Dimension dimension=new Dimension(columnas*dimensionCasilla+6,filas*dimensionCasilla+48);
        setPreferredSize(dimension);

        //Se pone el titulo al marco.
        setTitle("C O N E C T A  4");

        // Se hace visible
        setVisible(true);

        // Se hace que la aplicacion finalice al cerrar.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Se empaqueta todo.
        pack();
    }

    /**
     * Método getter para devolver el usuario1.
     * @return Devuelve el usuario1.
     */
    public Usuario devolverUsuario1(){
        return usuario1;
    }

    /**
     * Método getter para devolver el usuario2.
     * @return Devuelve el usuario2.
     */
    public Usuario devolverUsuario2(){
        return usuario2;
    }

    /**
     * Método getter para devolver la lista de usuarios.
     * @return Devuelve la lista de usuarios.
     */
    public Usuarios devolverUsuarios(){
        return usuarios;
    }

    /**
     * Método getter para devolver el número de jugadores.
     * @return Devuelve el número de jugadores.
     */
    public int devolverNumJugadores(){
        return numJugadores;
    }

    /**
     * Método getter para devolver la profundidad máxima del minimax.
     * @return Devuelve la profundidad máxima del minimax.
     */
    public int devolverProfundidadMinimax(){
        return profundidadMinimax;
    }


    /**
     * Este metodo sirve para leer la dimensión del tablero.
     * @param mensaje Pasamos por argumento la cadena que que se convertira en dimensión.
     * @return Devuelve la dimensión del tablero.
     */
    public int leerDimension(String mensaje){
        int maximo=14;
        int minimo=4;
        int valor=0;
        //La dimensión del tablero debe estar comprendida entre 4 y 14; en caso de no
        //ser así se mostrará un mensaje de aviso por pantalla.
        do{
            valor=Integer.parseInt(JOptionPane.showInputDialog(mensaje));
            if (valor<minimo||valor>maximo){
                JOptionPane.showMessageDialog(new JFrame(), "La dimensión debe estar comprendida entre 4 y 14 filas/columnas");
            }
        }while(valor<minimo||valor>maximo);

        return valor;
    }



    /**
     * Metodo para leer el nombre del usuario introducido en un panel.
     * @param mensaje Pasamos por argumento el mensaje que mostrará la ventana.
     * @return Devuelve el nombre introducido en el panel.
     */
    public String leerNombre(String mensaje){
        String cadena=JOptionPane.showInputDialog(mensaje);
        return cadena;
    }


    /**
     * Metodo para leer el nombre del usuario1.
     */
    public void leerUsuario1(){
        //Leemos el usuario que jugará contra la máquina. En caso de que NO exista, lo añadiremos a la lista.
        String nombreRecibido="";
        do{
            nombreRecibido=leerNombre("Introduce el nombre del Jugador 1");
            usuario1= new Usuario(nombreRecibido);
            if(nombreRecibido.equals("")){
                JOptionPane.showMessageDialog(new JFrame(),"¡Introduzca un nombre válido para el Jugador 1!");
            }
        }while (nombreRecibido.equals(""));

        usuarios.seleccionarUsuario(usuario1);
    }

    /**
     * Metodo para leer el nombre del usuario2.
     */
    public void leerUsuario2(){
        //Leemos el Jugador 2. Mostrará un mensaje de aviso en caso de coincidir con el Jugador 1.
        String nombreRecibido="";

        do{
            nombreRecibido=leerNombre("Introduce el nombre del Jugador 2");
            usuario2= new Usuario(nombreRecibido);
            if(nombreRecibido.equals(usuario1.devolverNombre())){
                JOptionPane.showMessageDialog(new JFrame(),"¡El Jugador 1 y el Jugador 2 no pueden ser el mismo usuario!");
            }
            if(nombreRecibido.equals("")){
                JOptionPane.showMessageDialog(new JFrame(),"¡Introduzca un nombre válido para el Jugador 2!");
            }
        }while (nombreRecibido.equals(usuario1.devolverNombre()) || nombreRecibido.equals(""));


        usuarios.seleccionarUsuario(usuario2);
    }

    /**
     * Método para iniciar la partida.
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     */
    public void iniciarPartida(int filas, int columnas){
        //Fijamos la dimensión de la ventana.
        Dimension dimension=new Dimension(columnas*dimensionCasilla+6,filas*dimensionCasilla+48);
        setPreferredSize(dimension);

        //Creamos los objetos de las clases PanelTablero, Tablero y Controlador,
        //y los asignamos unos a los otros correctamente.
        vistaPartida=new PanelTablero(filas,columnas);
        tablero=new Tablero(filas,columnas);
        tablero.asignarVista(vistaPartida);
        vistaPartida.asignarTablero(tablero);
        Controlador gestorEventosRaton=new Controlador(vistaPartida,tablero,this);
        vistaPartida.asignarControlador(gestorEventosRaton);
        vistaPartida.addMouseListener(gestorEventosRaton);
        this.setContentPane(vistaPartida);

        //Empaquetamos todo y hacemos que no se pueda redimensionar la ventana.
        pack();
        setResizable(false);
    }

    /**
     * Método para actualizar la partida una vez cargada.
     * @param partida Objeto de la clase tablero que queremos cargar.
     */
    public void actualizarPartida(Tablero partida){
        //Fijamos la dimensión de la ventana.
        Dimension dimension=new Dimension(partida.devolverColumnas()*dimensionCasilla+6,partida.devolverFilas()*dimensionCasilla+48);
        setPreferredSize(dimension);

        //Creamos los objetos de las clases PanelTablero, Tablero y Controlador,
        //y los asignamos unos a los otros correctamente.
        tablero=new Tablero(partida);
        vistaPartida=new PanelTablero(partida.devolverFilas(),partida.devolverColumnas());
        tablero.asignarVista(vistaPartida);
        vistaPartida.asignarTablero(tablero);
        Controlador gestorEventosRaton=new Controlador(vistaPartida,tablero,this);
        vistaPartida.asignarControlador(gestorEventosRaton);
        vistaPartida.addMouseListener(gestorEventosRaton);
        this.setContentPane(vistaPartida);

        //Empaquetamos todo y hacemos que no se pueda redimensionar la ventana.
        pack();
        setResizable(false);
    }


    //Este método lo dejamos implementado para comprobar que está bien programado,
    //aunque no hacemos uso de él, ya que, aunque carga correctamente la partida,
    //lo hace cargando una versión distinta de la clase, por lo que el juego deja
    //de funcionar.

    /**
     * Método para cargar una partida usando un objeto de la clase JFileChooser.
     * @throws java.io.IOException Excepción del tipo IOException.
     * @throws java.lang.ClassNotFoundException Excepcion del tipo ClassNotFoundException.
     */
    public void cargarPartida1() throws IOException, ClassNotFoundException{

        //Declaramos las variables correspondientes.
        String userDir= System.getProperty("user.dir");
        Tablero partida;
        FileInputStream fis;
        ObjectInputStream ois;

        //Creamos un objeto de la clase JFileChooser.
        JFileChooser jf= new JFileChooser(userDir);
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue= jf.showOpenDialog(null);

        if (returnValue==jf.CANCEL_OPTION){
            return;
        }

        //En caso de haber pinchado el botón Aceptar, cargamos el tablero señalado.
        if (returnValue==jf.APPROVE_OPTION){
            File selectedFile= jf.getSelectedFile();
            if (selectedFile==null || selectedFile.getName().equals("")){
                JOptionPane.showMessageDialog(new JFrame(), "Nombre de archivo incorrecto");
            }
            else{
                try{
                    fis=new FileInputStream(selectedFile);
                    ois=new ObjectInputStream(fis);
                    partida=(Tablero)ois.readObject();
                    actualizarPartida(partida);
                    tablero.pintarFichas();
                    numJugadores=numJugadoresTemporal;
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(new JFrame(), "Error al abrir el archivo");
                }
            }
        }
    }



    /**
     * Método para cargar una partida grabada previamente.
     * @throws java.io.IOException Excepción del tipo IOException.
     * @throws java.lang.ClassNotFoundException Excepción del tipo ClassNotFoundException.
     */
    public void cargarPartida() throws IOException, ClassNotFoundException{

        //Declaramos las variables correspondientes.
        Tablero partida;
        FileInputStream fis=null;
        ObjectInputStream ois=null;

        //Cargamos el tablero almacenado en el búfer.
        try{
            fis=new FileInputStream("Tablero");
            ois=new ObjectInputStream(fis);
            partida=(Tablero)ois.readObject();
            actualizarPartida(partida);
            tablero.pintarFichas();
            numJugadores=numJugadoresTemporal;
        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("Error al cargar la partida");
        }
    }

    /**
     * Método para guardar partidas.
     * @throws java.io.IOException Excepción del tipo IOException.
     */
    public void guardarPartida() throws IOException {

        //Declaración de las variables correspondientes.
        FileOutputStream fos=null;
        ObjectOutputStream oos=null;

        //Almacenamos el tablero actual en el búfer.
        try{
            fos=new FileOutputStream("Tablero");
            oos= new ObjectOutputStream(fos);
            oos.writeObject(tablero);
            numJugadoresTemporal=numJugadores;
            JOptionPane.showMessageDialog(new JFrame(), "La partida ha sido guardada correctamente");
        }
        catch (IOException e){
            System.out.println(e);
            System.out.println("Error al guardar la partida");
        }
    }
    


    //Esta es otra versión del método guardarPartida() anterior haciendo uso de
    //un objeto de la clase JFileChooser. Sin embargo, al igual que ocurria con
    //el cargarPartida1(), produce un error al tratar con distintas versiones de
    //las clases.

    /**
     * Método para guardar partidas usando un objeto de la clase JFileChooser.
     * @throws java.io.IOException Excepción del tipo IOException.
     */
    public void guardarPartida1() throws IOException {

        //Declaración de las variables.
        String userDir= System.getProperty("user.dir");
        FileOutputStream fos;
        ObjectOutputStream oos;

        //Creamos un objeto de la clase JFileChooser.
        JFileChooser jf= new JFileChooser(userDir);
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue= jf.showSaveDialog(null);

        if (returnValue==jf.CANCEL_OPTION){
            return;
        }

        //En caso de haber pulsado el botón de Aceptar, almacenamos el tablero
        //actual en el búfer.
        if (returnValue==jf.APPROVE_OPTION){
            File selectedFile= jf.getSelectedFile();
            if (selectedFile==null || selectedFile.getName().equals("")){
                JOptionPane.showMessageDialog(new JFrame(), "Nombre de archivo incorrecto");
            }
            else{
                try{
                    fos=new FileOutputStream(selectedFile);
                    oos=new ObjectOutputStream(fos);
                    oos.writeObject(tablero);
                    oos.flush();
                    numJugadores=numJugadoresTemporal;
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(new JFrame(), "Error al guardar la partida");
                }
            }
        }
    }


    /**
     * Metodo para crear los menus y submenus
     */
    public void crearMenu(){

        //Creamos los objetos de las clases JMenu, JMenuBar y JMenuItem.
        barraMenu=new JMenuBar();
        menuPartida=new JMenu("Partida");
        menuPartida.setMnemonic(KeyEvent.VK_P);
        menuUsuario=new JMenu("Usuario");
        menuUsuario.setMnemonic(KeyEvent.VK_U);
        menuAyuda=new JMenu("Ayuda");
        menuAyuda.setMnemonic(KeyEvent.VK_A);
        menuNuevaPartida=new JMenu("Nueva Partida");
        menuUnJugador= new JMenu ("Un jugador");
        opcionIniciado= new JMenuItem ("Nivel iniciado");
        opcionMedio= new JMenuItem ("Nivel medio");
        opcionAvanzado= new JMenuItem ("Nivel avanzado");
        opcionMultijugador= new JMenuItem ("Multijugador");
        cargarPartida= new JMenuItem ("Cargar partida");
        guardarPartida= new JMenuItem ("Guardar partida");
        opcionSalir=new JMenuItem("Salir");
        gestionUsuario=new JMenu("Gestión de usuarios");
        crearUsuario= new JMenuItem("Crear usuario");
        borrarUsuario=new JMenuItem("Borrar usuario");
        mostrarListaUsuarios= new JMenuItem("Mostrar lista de usuarios");
        menuEstadisticas= new JMenu("Estadísticas");
        mostrarEstadisticas= new JMenuItem("Mostrar estadísticas");
        reiniciarEstadisticas= new JMenuItem("Reiniciar estadísticas");
        opcionAcerca=new JMenuItem("Acerca de");
        opcionAyuda=new JMenuItem("Ayuda");

        //Conjuntamos los distintos menús.
        barraMenu.add(menuPartida);
        barraMenu.add(menuUsuario);
        barraMenu.add(menuAyuda);
        menuPartida.add(menuNuevaPartida);
        menuPartida.add(cargarPartida);
        menuPartida.add(guardarPartida);
        menuPartida.addSeparator();
        menuPartida.add(opcionSalir);
        menuNuevaPartida.add(menuUnJugador);
        menuUnJugador.add(opcionIniciado);
        menuUnJugador.add(opcionMedio);
        menuUnJugador.add(opcionAvanzado);
        menuNuevaPartida.add(opcionMultijugador);
        menuUsuario.add(gestionUsuario);
        menuUsuario.add(menuEstadisticas);
        gestionUsuario.add(crearUsuario);
        gestionUsuario.add(borrarUsuario);
        gestionUsuario.add(mostrarListaUsuarios);
        menuEstadisticas.add(mostrarEstadisticas);
        menuEstadisticas.add(reiniciarEstadisticas);
        menuAyuda.add(opcionAcerca);
        menuAyuda.add(opcionAyuda);

        //Agregamos el control de eventos a los JMenuItem.
        opcionIniciado.addActionListener(this);
        opcionMedio.addActionListener(this);
        opcionAvanzado.addActionListener(this);
        opcionMultijugador.addActionListener(this);
        cargarPartida.addActionListener(this);
        guardarPartida.addActionListener(this);
        opcionSalir.addActionListener(this);
        crearUsuario.addActionListener(this);
        borrarUsuario.addActionListener(this);
        mostrarListaUsuarios.addActionListener(this);
        mostrarEstadisticas.addActionListener(this);
        reiniciarEstadisticas.addActionListener(this);
        opcionAcerca.addActionListener(this);
        opcionAyuda.addActionListener(this);

        //Agregar la barra de menus al marco de la aplicacion
        setJMenuBar(barraMenu);
    }


    /**
     * Metodo actionPerformed implementado de la intefaz ActionListener.
     * @param e Recibe un objeto de la clase ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        //Definimos la variable itemPulsado como el objeto del menú que pulsemo.
        JMenuItem itemPulsado=(JMenuItem)(e.getSource());

        //NUEVA PARTIDA -> UN JUGADOR -> NIVEL INICIADO.
        if (itemPulsado==opcionIniciado){
            //Fijamos el número de jugadores y la máxima profundidad del minimax.
            //(fijamos una máxima profundidad de 2 para el nivel de dificultad bajo).
            numJugadores=1;
            profundidadMinimax=2;

            //Leemos la dimensión del tablero.
            int dimension=leerDimension("Introduzca la dimensión del tablero (entre 4 y 14 filas/columnas): ");

            //Leemos el Jugador 1.
            leerUsuario1();

            //Iniciamos la partida.
            iniciarPartida(dimension, dimension);
        }

        //NUEVA PARTIDA -> UN JUGADOR -> NIVEL MEDIO.
        if (itemPulsado==opcionMedio){
            //Fijamos el número de jugadores y la máxima profundidad del minimax.
            //(fijamos una máxima profundidad de 4 para el nivel de dificultad medio).
            numJugadores=1;
            profundidadMinimax=4;

            //Leemos la dimensión del tablero.
            int dimension=leerDimension("Introduzca la dimensión del tablero (entre 4 y 14 filas/columnas): ");

            //Leemos el Jugador 1.
            leerUsuario1();

            //Iniciamos la partida.
            iniciarPartida(dimension, dimension);

        }

        //NUEVA PARTIDA -> UN JUGADOR -> NIVEL AVANZADO.
        if (itemPulsado==opcionAvanzado){
            //Fijamos el número de jugadores y la máxima profundidad del minimax
            //(fijamos una máxima profundidad de 6 para el nivel de dificultad alto).
            numJugadores=1;
            profundidadMinimax=6;

            //Leemos la dimensión del tablero.
            int dimension=leerDimension("Introduzca la dimensión del tablero (entre 4 y 14 filas/columnas): ");

            //Leemos el Jugador 1.
            leerUsuario1();

            //Iniciamos la partida.
            iniciarPartida(dimension, dimension);

        }

        //NUEVA PARTIDA -> MULTIJUGADOR.
        if(itemPulsado==opcionMultijugador){
            numJugadores=2;

            int dimension=leerDimension("Introduzca la dimensión del tablero (entre 4 y 14 filas/columnas): ");

            //Leemos el Jugador 1.
            leerUsuario1();

            //Leemos el Jugador 2.
            leerUsuario2();

            //Iniciamos la partida.
            iniciarPartida(dimension, dimension);

        }

        //PARTIDA -> CARGAR PARTIDA.
        if (itemPulsado==cargarPartida){

            //Se pide una confirmación para cargar la partida grabada anteriormente.
            //En caso afirmativo, llamamos al método cargarPartida().
            int opcion=JOptionPane.showConfirmDialog(new JFrame(), "¿Está seguro de que desea cargar la última partida grabada?"
                    +"\n (La partida actual se perderá)");

            if (opcion==0){
                try{
                    cargarPartida();
                }
                catch (IOException ex){
                    System.out.println("Error IO");
                }
                catch (ClassNotFoundException ex){
                    System.out.println("Clase no encontrada");
                }
            }
        }

        //PARTIDA -> GUARDAR PARTIDA.
        if (itemPulsado==guardarPartida){
            //Llamamos al método guardarPartida().
            try{
                guardarPartida();
            }
            catch(IOException ex){
                System.out.println("Error IO");
                //Logger.getLogger(CuatroRaya.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        //PARTIDA -> SALIR.
        if(itemPulsado==opcionSalir){
            int opcion=JOptionPane.showConfirmDialog(new JFrame(), "¿Está seguro de que desea salir del juego?");

            if (opcion==0){
                usuarios.guardarUsuarios();
                System.exit(0);
            }
        }

        //USUARIO -> GESTIÓN DE USUARIOS -> CREAR USUARIO.
        if (itemPulsado==crearUsuario){
            //Leemos el usuario introducido y llamamos al metodo crearUsuario() de la clase Usuarios.
            String nombreRecibido=leerNombre("Introduce tu nombre: ");
            usuario1= new Usuario(nombreRecibido);
            usuarios.crearUsuario(usuario1);
        }

        //USUARIO -> GESTIÓN DE USUARIOS -> BORRAR USUARIO.
        if (itemPulsado==borrarUsuario){
            //Leemos el usuario introducido y llamamos al metodo borrarUsuario() de la clase Usuarios.
            String nombreRecibido=leerNombre("Introduce el nombre del usuario que desea borrar: ");
            usuario1= new Usuario(nombreRecibido);
            usuarios.borrarUsuario(usuario1);
        }

        //USUARIO -> GESTIÓN DE USUARIOS -> MOSTRAR LISTA DE USUARIOS.
        if (itemPulsado==mostrarListaUsuarios){

            //Mostramos un mensaje con la lista de usuarios y las respectivas victorias de cada uno.
            String cadena="RANKING DE USUARIOS: \n \n";

            for(int i=0;i<usuarios.devolverListaUsuarios().size(); i++){
                Usuario referencia= usuarios.devolverListaUsuarios().get(i);
                cadena=cadena+((i+1)+". "+referencia.devolverNombre()+
                        ".................Nº de victorias: "+referencia.devolverGanadas()+"\n");
            }

            JOptionPane.showMessageDialog(new JFrame(),cadena);

        }

        //USUARIO -> ESTADISTICAS -> MOSTRAR ESTADÍSTICAS.
        if (itemPulsado==mostrarEstadisticas){
            //Mostramos por pantalla una serie de estadísticas acerca de las victorias
            //y derrotas de un usuario concreto que introduzcamos.
            String nombreRecibido=leerNombre("Introduce el nombre del usuario: ");
            usuario1= new Usuario(nombreRecibido);
            boolean encontrado=false;
            double porcentaje=0;
            
            for(int i=0;i<usuarios.devolverListaUsuarios().size(); i++){
                Usuario referencia= usuarios.devolverListaUsuarios().get(i);
                if(referencia.devolverNombre().equalsIgnoreCase(nombreRecibido)){
                    if (referencia.devolverJugadas()!=0){
                        porcentaje=(((double)(referencia.devolverGanadas())/(double)(referencia.devolverJugadas()))*100);
                    }
                    JOptionPane.showMessageDialog(new JFrame(),"ESTADÍSTICAS DE "+nombreRecibido+": \n \n - Nº de partidas ganadas: "+
                            referencia.devolverGanadas()+"\n - Nº de partidas perdidas: "+referencia.devolverPerdidas()+
                            "\n - Nº de partidas jugadas: "+referencia.devolverJugadas()+"\n - Porcentaje de victorias: "+
                            (int)(porcentaje)+" %");
                    encontrado=true;
                    break;
                }
            }
            if(encontrado==false){
                System.out.println("No existe ningún usuario con el nombre introducido");
                JOptionPane.showMessageDialog(new JFrame(),"No existe ningún usuario con el nombre introducido");
            }

        }

        //USUARIO -> ESTADISTICAS -> REINICIAR ESTADISTICAS.
        if (itemPulsado==reiniciarEstadisticas){

            //Pedimos una confirmación para reiniciar todas las estadísticas de
            //los usuarios almacenados. En caso afirmativo, llamamos al método
            //reiniciarEstadisticas() de la clase Usuario.
            int opcion=JOptionPane.showConfirmDialog(new JFrame(), "¿Está seguro de que desea reiniciar las estadísticas?");

            if (opcion==0){
                for(int i=0;i<usuarios.devolverListaUsuarios().size(); i++){
                    Usuario referencia= usuarios.devolverListaUsuarios().get(i);
                    referencia.reiniciarEstadisticas();
                }

                JOptionPane.showMessageDialog(new JFrame(),"¡Las estadísticas han sido reiniciadas!");
            }
            
        }

        //AYUDA -> ACERCA DE.
        if(itemPulsado==opcionAcerca){
            //Mostramos un mensaje con información útil sobre el Conecta 4.
            JOptionPane.showMessageDialog(new JFrame(),"               " +
                    "                         Conecta 4, versión 1.0 \n\n" +
                    "Realizado por Jesús Coronado Escobar y Federico Grande Nieto \n \n" +
                    "Se trata de una implementación en java del clásico juego del 4 en raya \n" +
                    "cuyo objetivo básico es conseguir alinear 4 fichas del mismo color para \n" +
                    "ganar la partida.");
        }

        //AYUDA -> AYUDA.
        if(itemPulsado==opcionAyuda){
            //Mostramos por pantalla un breve manual sobre el juego Conecta 4.
            JOptionPane.showMessageDialog(new JFrame(),"REGLAS DEL JUEGO: \n \n" +
                    "- El funcionamiento básico de este juego consiste en ir colocando fichas \n" +
                    "  alternativamente cada jugador dejándolas caer por cada columna. \n" +
                    "- Gana el primer jugador que consiga alinear 4 fichas del mismo color, \n" +
                    "  ya sea de forma horizontal, vertical o en diagonal. \n" +
                    "- En caso de llenar el tablero sin haber alineado ninguno de los dos \n" +
                    "  jugadores 4 fichas del mismo color, se habrá llegado a un empate.\n \n" +
                    "Para más información, acceder a la página: http://en.wikipedia.org/wiki/Connect_Four");
        }

    }


    /**
     * Método main de la clase.
     * @param args Argumentos.
     */
    public static void main(String args[]){
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                //Creamos un objeto de la clase Conecta4.
                Conecta4 aplicacion=new Conecta4(8,8);
            }
        });
    }

}
