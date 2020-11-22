package paquete;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal . Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author Alberto Luis Calero
 *
 */
public class ActionBoton implements ActionListener, MouseListener {
	/**
	 * Ventana principal para poder acceder a los botones y paneles
	 */
	VentanaPrincipal ventana;
	/**
	 * Cordena en X qye corresponde al boton pulsado
	 */
	int i;
	/**
	 * Cordenada en Y que corresponde al boton pulsado
	 */
	int j;

	/**
	 * Constructor de Actionboton , recibe la cordenada X , la cordenada Y y la
	 * ventana para poder interactuar con los otros botones y paneles
	 * 
	 */
	public ActionBoton(int i, int j, VentanaPrincipal ventana) {
		this.i = i;
		this.j = j;
		this.ventana = ventana;
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones. Si no hay mina
	 * mostraremos el numero de minas que hay alrededor y actualizaremos la
	 * puntuacion Si hay mina se terminara el juego
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (ventana.botonesJuego[i][j].getBackground() != Color.BLACK) {// Si el color del boton es negro quiere decir
																		// que hemos pulsado clic derecho y no podremos
																		// hacer accion normal
			if (!ventana.getJuego().abrirCasilla(i, j)) {// Si no hay mina
				ventana.mostrarNumMinasAlrededor(i, j);// Mostramos el numero de minas alrededor de la casilla
				ventana.actualizarPuntuacion();// Actualizamos la puntuacion
				if (ventana.getJuego().getMinasAlrededor(i, j) == 0) {// Si el valor de minasAlrededor es 0 despejaremos
																		// las casillas cercanas
					mostrarAlrededor(i, j);
				}
				if (ventana.getJuego().esFinJuego()) {// comprobamos si hemos ganado por puntuacion
					ventana.mostrarFinJuego(false);// Fin del juego por puntuacion
				}
			} else {
				ventana.mostrarFinJuego(true);// Fin del juego por explosion
			}
		}
	}

	/**
	 * Con este metodo autogeneraremos un click si la casilla que hemos tocado es 0
	 * y despejaremos las casillas colindantes.
	 * 
	 * @param i cordenada del boton en X
	 * @param j cordenada del boton en Y
	 */
	public void mostrarAlrededor(int i, int j) {
		for (int k = Math.max(0, i - 1); k <= Math.min(ventana.getJuego().getLado_tablero() - 1, i + 1); k++) {
			for (int k2 = Math.max(0, j - 1); k2 <= Math.min(ventana.getJuego().getLado_tablero() - 1, j + 1); k2++) {
				if (!(k == i && k2 == j)) {// No es la posicion que le hemos pasado
					if (ventana.panelesJuego[k][k2].getComponent(0) instanceof JButton) {// Es un boton
						JButton siguiente = (JButton) ventana.panelesJuego[k][k2].getComponent(0);// Cojemos el boton ya
																									// que el panel solo
																									// tendra un
																									// elemento
						siguiente.doClick(0);// Autogeneramos un click en el boton
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo para "inhabilitar" el boton , si hacemos click derecho la primera vez
	 * lo "inhabilitares" si volvemos a usar el click derecho revertiremos el cambio
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {// Si es click derecho
			if (ventana.botonesJuego[i][j].getBackground() != Color.black) {// Si no es negro lo pondremos negro
				ventana.botonesJuego[i][j].setText("!!");
				ventana.botonesJuego[i][j].setBackground(Color.black);
			} else {// Si es negro revertiremos el cambio
				ventana.botonesJuego[i][j].setBackground(ventana.botonEmpezar.getBackground());
				ventana.botonesJuego[i][j].setText("-");
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
