package buscaminas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana principal del Buscaminas
 * <p>
 * En esta clase se generara un Frame en el que meteremos los componentes
 * necesarios para jugar al buscaminas.
 * </p>
 * {@link #inicializar()}
 * 
 * @see
 *      <p>
 *      <a href="../ControlJuego">Documentacion de la clase ControlJuego</a> con
 *      la que se controla toda la logica de el buscaminas
 *      </p>
 * @author Alberto Luis Calero
 * @author Jesús Redondo
 * @version 1.0
 * @since 1.0
 */
public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	/**
	 * JPanel que en esta version ira vacio
	 */
	JPanel panelImagen;
	/**
	 * JPanel en el que ira el botonEmpezar
	 */
	JPanel panelEmpezar;
	/**
	 * JPanel Panel donde se introducira botonEmpezar
	 */
	JPanel panelPuntuacion;
	/**
	 * JPanel panel donde se jugara al buscaminas
	 */
	JPanel panelJuego;

	/**
	 * JPanel[][] Se almacenan los paneles en los que iran los botones o paneles del
	 * buscaminas
	 */
	JPanel[][] panelesJuego;
	/**
	 * JButton[][] Se almacenan todos los botones del buscaminas
	 */
	JButton[][] botonesJuego;

	/**
	 * Color[] Se almacena la correspondencia de los colores segun las minas que
	 * haya alrededor
	 */
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };
	/**
	 * JButton Boton con el que podremos resetear todo el Frame
	 */
	JButton botonEmpezar;
	/**
	 * JTextField Se mostrara la puntuacion que llevamos
	 */
	JTextField pantallaPuntuacion;

	/**
	 * ControJuego Se almacenara un objeto ControlJuego con el que se realizara la
	 * logica del programa
	 */
	ControlJuego juego;
	/**
	 * JCronometro panel que controla y muestra el tiempo que tardamos en finalizar
	 * la partida
	 */
	JCronometro cronometro;
	/**
	 * Almacena la cantidad de celdas de cada lado del tablero
	 */
	int ladoTablero = 10;
	int numMinas = 20;

	/**
	 * Constructor de la clase , se inicializa el frame se le dan las medidas y se
	 * crea un ControlJuego nuevo
	 */
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cronometro = new JCronometro();
		JDialogMinas dialogo = new JDialogMinas(ventana.getX(), ventana.getY(), this);
		dialogo.setVisible(true);

		juego = new ControlJuego(getLadoTablero(), getNumMinas());
	}

	/**
	 * Inicializa y añade todos los componentes necesarios para jugar
	 */
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelImagen.setLayout(new GridLayout());
		panelImagen.add(cronometro);
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(ladoTablero, ladoTablero));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.ipady = 40;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.ipady = 40;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.ipady = 40;// Si el lado del tablero es el maximo no se ven ni en reloj ni el boton de
							// empezar por lo que le doy 40 para que se vea
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[ladoTablero][ladoTablero];
		for (int i = 0; i < ladoTablero; i++) {
			for (int j = 0; j < ladoTablero; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[ladoTablero][ladoTablero];
		for (int i = 0; i < ladoTablero; i++) {
			for (int j = 0; j < ladoTablero; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Metodo para empezar la cuenta del cronometro
	 */
	public void empezarCuenta() {
		cronometro.empezar();
	}

	/**
	 * Para la cuenta
	 */
	public void pararCuenta() {
		cronometro.parar();
	}

	/**
	 * Resetea la cuenta
	 */
	public void resetearCuenta() {
		cronometro.reset();
	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa. Los botones para jugar se abriran con los eventos predeterminados ,
	 * si usamos el click derecho el boton se dejara de funcionar hasta que volvamos
	 * a usar el click derecho en el mismo boton
	 * 
	 * El botonEmpezar pregunta la configuracion del buscaminas y resetea el
	 * buscaminas
	 */
	public void inicializarListeners() {
		ActionBoton accion;
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				accion = new ActionBoton(i, j, this);
				botonesJuego[i][j].addActionListener(accion);// La accion predeterminada
				botonesJuego[i][j].addMouseListener(accion);// La accion con el click derecho
			}
		}
		// Con este boton reiniciaremos el juego
		botonEmpezar.addActionListener((e) -> {// Sobre escribimos el metodo actionPerformed para darle una accion al
												// boton
			ventana.getContentPane().removeAll();
			JDialogMinas dialogo = new JDialogMinas(ventana.getX(), ventana.getY(), this);// Volvemos a preguntar la
																							// configuracion del
																							// buscaminas
			dialogo.setVisible(true);
			juego = new ControlJuego(getLadoTablero(), getNumMinas());
			inicializarComponentes();
			inicializarListeners();
			refrescarPantalla();
			resetearCuenta();// Resetea la cuenta del cronometro
		});
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca el
	 * botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto según
	 * la siguiente correspondecia (consultar la variable correspondeciaColor): - 0
	 * : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó más : rojo
	 * 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		JLabel label = new JLabel();
		panelesJuego[i][j].removeAll();
		label.setForeground(correspondenciaColores[juego.getMinasAlrededor(i, j)]);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		panelesJuego[i][j].add(label);
		label.setText("" + juego.getMinasAlrededor(i, j));

	}

	/**
	 * Muestra una ventana que indica el fin del juego Inhabilita todos los botones
	 * Muestra todas las minas poniendolas de color naranja
	 * 
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		cronometro.parar();
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				botonesJuego[i][j].setEnabled(false);// Inhabilitamos los botones
				if (juego.getMinasAlrededor(i, j) == -1) {// Si son minas las ponemos de color naranja
					botonesJuego[i][j].setText("MINA");
					botonesJuego[i][j].setBackground(Color.ORANGE);
				}
			}
		}
		if (porExplosion) {// Si ha sido por explosion ha perdido
			JOptionPane.showMessageDialog(ventana,
					"Ha explotado una mina,has perdido\n Tu puntuacion ha sido :" + juego.getPuntuacion()
							+ "\n La partida ha durado :" + cronometro.getContador().getText(),
					"FINAL", JOptionPane.ERROR_MESSAGE);// ERROR_MESSAGE
		} else {// Si no, habra ganado
			JOptionPane.showMessageDialog(
					ventana, "HAS GANADO!!\n Tu puntuacion ha sido :" + juego.getPuntuacion()
							+ "\n La partida ha durado :" + cronometro.getContador().getText(),
					"FINAL", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText("" + juego.getPuntuacion());
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Metodo que devuelve la longitud del tablero
	 * 
	 * @return un entero con la longitud del tablero
	 */
	public int getLadoTablero() {
		return this.ladoTablero;
	}

	/**
	 * Metodo para settear la longitud del tablero
	 * 
	 * @param ladoTablero un entero con la longitud del tablero
	 */
	public void setLadoTablero(int ladoTablero) {
		this.ladoTablero = ladoTablero;
	}

	/**
	 * El numero de minas con las que vamos a jugar
	 * 
	 * @return numero de minas para jugar
	 */
	public int getNumMinas() {
		return this.numMinas;
	}

	/**
	 * metodo para settear el numero de minas con las que vamos a jugar
	 * 
	 * @param numMinas numero de minas con las que vamos a jugar
	 */
	public void setNumMinas(int numMinas) {
		this.numMinas = numMinas;
	}

	/**
	 * Método para inicializar el programa
	 * 
	 * <pre>
	 * 		{@code	
	 * 				public void inicializar() {
	 *					ventana.setVisible(true);
	 *					inicializarComponentes();
	 *					inicializarListeners();
	 *				}
	 * 				
	 * 		}
	 * </pre>
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

}
