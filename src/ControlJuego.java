import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay una
 * mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de la
 * partida
 * 
 * @author Alberto Luis Calero
 *
 */
public class ControlJuego {
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
		depurarTablero();
	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como marque
	 *        la variable MINAS_INICIALES. El resto de posiciones que no son minas
	 *        guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {

		// TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero anterior,
		// lo pongo todo a cero para inicializarlo.
		// Ponemos todas las posiciones a 0 por si hay una partida anterior

		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tablero[i][j] = 0;
			}
		}
		// Puntuacion a 0
		puntuacion = 0;
		int iAux, jAux, contador = 0;
		// Colocamos tantas minas como indique MINAS_INICIALES
		while (contador < MINAS_INICIALES) {
			iAux = numeroRandom(0, LADO_TABLERO);
			jAux = numeroRandom(0, LADO_TABLERO);
			if (tablero[iAux][jAux] != MINA) {
				tablero[iAux][jAux] = MINA;
				contador++;
			}
		}
		// Al final del m�todo hay que guardar el n�mero de minas para las casillas que
		// no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					tablero[i][j] = calculoMinasAdjuntas(i, j);
				}
			}
		}
	}

	/**
	 * Devuelve un numero random entre el minimo y el maximo
	 * 
	 * @param minimo
	 * @param maximo
	 * @return un entero random entre el minimo y el maximo
	 */
	private int numeroRandom(int minimo, int maximo) {
		return (int) (Math.random() * maximo + minimo);
	}

	/**
	 * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como poco la i y la j
	 * valdrán 0.
	 * 
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {
		int numero = 0;
		int[] posiciones = { i, j + 1, i, j - 1, i + 1, j, i - 1, j, i + 1, j + 1, i + 1, j - 1, i - 1, j + 1, i - 1,
				j - 1 };
		/*
		 * Posicion[] posiciones = { new Posicion(i, j + 1), new Posicion(i, j - 1), new
		 * Posicion(i + 1, j), new Posicion(i - 1, j), new Posicion(i + 1, j + 1), new
		 * Posicion(i + 1, j - 1), new Posicion(i - 1, j + 1), new Posicion(i - 1, j -
		 * 1) };
		 */
		for (int index = 0; index < posiciones.length; index += +2) {
			if (posiciones[index] >= 0 && posiciones[index + 1] >= 0 && posiciones[index] < LADO_TABLERO
					&& posiciones[index + 1] < LADO_TABLERO) {
				if (tablero[posiciones[index]][posiciones[index + 1]] == MINA) {
					numero++;
				}
			}
		}

		return numero;
	}

	/**
	 * Método que comprueba si hay mina , si no hay mina sumara un punto y devolvera
	 * false en caso de que haya mina devolvera true
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por
	 *      el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {
		boolean hayMina;
		if (tablero[i][j] == MINA) {
			hayMina = true;
		} else {
			hayMina = false;
			puntuacion++;
		}
		return hayMina;
	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		boolean finalizado = false;

		return finalizado;
	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: " + puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

}
