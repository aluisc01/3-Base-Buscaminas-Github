package buscaminas;

//import java.util.ArrayList;
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
	/**
	 * Minas con las que cuenta el tablero
	 */
	int minas_iniciales;
	/**
	 * Longitud de cada lado del tablero
	 */
	int lado_tablero;
	/**
	 * Matriz donde almacenaremos si la casilla es mina , o en su defecto las minas
	 * que tiene alrededor
	 */
	private int[][] tablero;
	/**
	 * Puntuacion del usuario cada vez que abramos una casilla que no sea mina
	 * sumaremos uno
	 */
	private int puntuacion;

	/**
	 * Constructor parametrizado del juego
	 * 
	 * @param lado_tablero    longitud del lado del tablero
	 * @param minas_iniciales numero de minas con las que jugaremos
	 */
	public ControlJuego(int lado_tablero, int minas_iniciales) {
		this.lado_tablero = lado_tablero;
		this.minas_iniciales = minas_iniciales;
		// Creamos el tablero:
		tablero = new int[lado_tablero][lado_tablero];

		// Inicializamos una nueva partida
		inicializarPartida();
		depurarTablero();// Para ver donde estan las minas y las minas que tiene alrededor cada elemento
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
		while (contador < minas_iniciales) {
			iAux = numeroRandom(0, lado_tablero);
			jAux = numeroRandom(0, lado_tablero);
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
	 * @param minimo un numero minimo para generar el random
	 * @param maximo un numero maximo para generar el random
	 * @return un entero random entre el minimo y el maximo
	 */
	private int numeroRandom(int minimo, int maximo) {
		Random rd = new Random();
		return (int) (rd.nextInt(maximo) + minimo);
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

		int iInicial = Math.max(0, i - 1);
		int iFinal = Math.min(lado_tablero - 1, i + 1);
		int jInicial = Math.max(0, j - 1);
		int jFinal = Math.min(lado_tablero - 1, j + 1);

		for (int k = iInicial; k <= iFinal; k++) {
			for (int k2 = jInicial; k2 <= jFinal; k2++) {
				if (tablero[k][k2] == MINA) {
					numero++;
				}
			}
		}
		/*
		 * for (int index = 0; index < posiciones.length; index += +2) { if
		 * (posiciones[index] >= 0 && posiciones[index + 1] >= 0 && posiciones[index] <
		 * LADO_TABLERO && posiciones[index + 1] < LADO_TABLERO) { if
		 * (tablero[posiciones[index]][posiciones[index + 1]] == MINA) { numero++; } } }
		 */

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
		return (lado_tablero * lado_tablero - minas_iniciales) == puntuacion;
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

	/**
	 * metodo que devuelve las minas en el tablero
	 * 
	 * @return entero con el numero de minas
	 */
	public int getMinas_iniciales() {
		return this.minas_iniciales;
	}

	/**
	 * Metodo que devuelve la longitud del lado del tablero
	 * 
	 * @return
	 */
	public int getLado_tablero() {
		return this.lado_tablero;
	}

}
