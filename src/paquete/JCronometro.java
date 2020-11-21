package paquete;

import java.awt.*;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JCronometro extends JPanel implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * DOnde mostraremos el tiempo transcurrido
     */
    JLabel contador;
    /**
     * Es el tiempo desde que empezamos a contar
     */
    double tiempoTranscurrido;
    /**
     * Es el momento en que empezamos a contar
     */
    double tiempoOriginal;
    /**
     * tiempo total transcurridos en segundos
     */
    double tiempoActual;
    /**
     * Los segundos que mostraremos por pantalla
     */
    double segundos;
    /**
     * Los minutos que mostraremos por pantalla
     */
    double minutos;
    /**
     * Booleano con el que controlaremos cuando cuenta y cuando no
     */
    boolean contar;
    /**
     * El hilo que usaremos para ir contando el tiempo
     */
    Thread hilo = null;

    /**
     * Constructor del cronometro , Al ser solo un jlabel lo inicializaremos en el
     * constructor
     */
    public JCronometro() {
        super();
        this.setLayout(new GridLayout());
        this.setBackground(new Color(0, 255, 255));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        contador = new JLabel();
        contador.setFont(new Font("Serief", Font.BOLD, 20));
        contador.setText(0 + "" + 0 + ":" + 0 + "" + 0);
        contador.setHorizontalAlignment(JLabel.CENTER);
        contador.setVerticalAlignment(JLabel.CENTER);
        this.add(contador);
        segundos = 0;
        minutos = 0;
    }

    /**
     * Metodo que sobreescribimos para poder hacer el cronometro.
     */
    @Override
    public void run() {
        while (contar) {
            actualizarPantalla(calcularTiempoTranscurrido());
            try {
                hilo.sleep(50);// Dormimos 50 para no saturar los procesos
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Para empezar a contar
     */
    public void empezar() {
        if (!contar) {
            contar = true;
            hilo = new Thread(this);
            hilo.start();
            tiempoOriginal = System.nanoTime();
        }
    }

    /**
     * Para el contador
     */
    public void parar() {
        contar = false;
    }

    /**
     * Resetea el contador
     */
    public void reset() {
        tiempoOriginal = System.nanoTime();
        segundos = 0;
        minutos = 0;
        actualizarPantalla(calcularTiempoTranscurrido());

    }

    /**
     * Calcula el tiempo transcurrido entre un momento y otro
     * 
     * @return una cadena con el tiempo
     */
    private String calcularTiempoTranscurrido() {

        String minutosCadena;
        String segundosCadena;
        tiempoTranscurrido = System.nanoTime();
        tiempoActual = (tiempoTranscurrido - tiempoOriginal) / 1000000000;// 10e9
        segundos = tiempoActual % 60;// Calculamos los segundos
        minutos = tiempoActual / 60;// Calculamos los minutos
        if (segundos < 10) {// Si es menos que 10 añadimos un cero a la izquierda
            segundosCadena = "0" + (int) segundos;
        } else {
            segundosCadena = "" + (int) segundos;
        }
        if (minutos < 10) {// Si es menos que 10 añadimos un cero a la izquierda
            minutosCadena = "0" + (int) minutos;
        } else {
            minutosCadena = "" + (int) minutos;
        }
        return (minutosCadena + ":" + segundosCadena);
    }

    /**
     * Actualiza el tiempo en pantalla
     * 
     * @param texto Es el texto que hemos calculado o el de reset
     */
    private void actualizarPantalla(String texto) {
        contador.setText(texto);
    }

    // GETTERS Y SETTERS
    public JLabel getContador() {
        return this.contador;
    }

    public void setContador(JLabel contador) {
        this.contador = contador;
    }

    public double getTiempoTranscurrido() {
        return this.tiempoTranscurrido;
    }

    public void setTiempoTranscurrido(double tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public double getTiempoOriginal() {
        return this.tiempoOriginal;
    }

    public void setTiempoOriginal(double tiempoOriginal) {
        this.tiempoOriginal = tiempoOriginal;
    }

    public boolean isContar() {
        return this.contar;
    }

    public boolean getContar() {
        return this.contar;
    }

    public void setContar(boolean contar) {
        this.contar = contar;
    }

    public Thread getHilo() {
        return this.hilo;
    }

    public void setHilo(Thread hilo) {
        this.hilo = hilo;
    }

}