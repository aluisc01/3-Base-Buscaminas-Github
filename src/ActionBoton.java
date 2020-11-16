import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author Alberto Luis Calero
 *
 */
public class ActionBoton implements ActionListener {
	VentanaPrincipal ventana;
	int i;
	int j;

	public ActionBoton(int i, int j, VentanaPrincipal ventana) {
		// TODO
		this.i = i;
		this.j = j;
		this.ventana = ventana;
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!ventana.getJuego().abrirCasilla(i, j)) {
			ventana.mostrarNumMinasAlrededor(i, j);
			ventana.actualizarPuntuacion();
			if (ventana.getJuego().esFinJuego()) {
				ventana.mostrarFinJuego(false);
			}
		} else {
			ventana.mostrarFinJuego(true);
		}

	}

}
