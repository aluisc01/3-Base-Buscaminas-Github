package buscaminas;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

/**
 * Clase para cofigurar la partida decidiendo el lado del tablero maximo 50 y el
 * numero de minas
 * 
 * @author Alberto Luis Calero
 */
public class JDialogMinas extends JDialog {
    private static final long serialVersionUID = 1L;
    /**
     * Valor maximo para el lado del buscaminas
     */
    final static int LADO_MAXIMO = 50;
    /**
     * Componentes del dialogo
     */
    JButton botonAceptar;
    JTextField mensajeLado;
    JFormattedTextField numeroLado;
    JTextField mensajeMinas;
    JFormattedTextField numeroMinas;
    JTextField titulo;
    VentanaPrincipal ventana;

    /**
     * Constructor del configurador de partida
     * 
     * @param posicionX posicion X donde aparecera la esquina izquierda superior en
     *                  pantalla
     * @param posicionY posicion Y donde aparecera la esquina izquierda superior en
     *                  pantalla
     * @param ventana   le pasaremos la ventana principal para poder settear el lado
     *                  y el numero de minas ya que es necesario para la interfaz
     */
    public JDialogMinas(int posicionX, int posicionY, VentanaPrincipal ventana) {
        super();
        setModal(true);// Mientras esta pantalla este desplegada no podremos usar las demas
        setBounds(posicionX, posicionY, 400, 600);
        anadirElemento();
        anadirListener();
        this.ventana = ventana;
    }

    /**
     * Metodo para crear la interfaz de la configuracion de partida
     */
    public void anadirElemento() {
        this.setLayout(new GridBagLayout());
        titulo = new JTextField();
        titulo.setText("CONFIGURACION DE PARTIDA");
        titulo.setEditable(false);
        mensajeLado = new JTextField("Dimensiones del buscaminas: ");
        mensajeLado.setEditable(false);
        // Le damos formato al formattedTextfield para que solo pueda recoger numeros y
        // el usuario no nos pueda trolear
        NumberFormat formato = NumberFormat.getInstance();
        NumberFormatter formatterLado = new NumberFormatter(formato);
        formatterLado.setValueClass(Integer.class);
        formatterLado.setMinimum(-1);
        formatterLado.setMaximum(10000000);
        formatterLado.setAllowsInvalid(false);
        formatterLado.setCommitsOnValidEdit(true);
        numeroLado = new JFormattedTextField(formatterLado);
        numeroLado.setValue(10);// Valor por defecto
        mensajeMinas = new JTextField("Numero de minas:");
        mensajeMinas.setEditable(false);
        // Lo mismo que el formattedTextfield numeroLado
        NumberFormatter formatterMina = new NumberFormatter(formato);
        formatterMina.setValueClass(Integer.class);
        formatterMina.setMinimum(-1);
        formatterMina.setMaximum(100000);
        formatterMina.setAllowsInvalid(false);
        formatterMina.setCommitsOnValidEdit(true);
        numeroMinas = new JFormattedTextField(formatterMina);
        numeroMinas.setValue(20);// Valor de minas por defecto
        // Colocamos todos los componentes despues de haberlos inicializado
        GridBagConstraints settings = new GridBagConstraints();
        settings.gridx = 1;
        settings.gridy = 0;
        settings.gridwidth = 3;
        settings.weightx = 1;
        settings.insets = new Insets(20, 20, 20, 20);
        this.add(titulo, settings);
        settings.gridx = 0;
        settings.gridy = 1;
        settings.gridwidth = 2;
        settings.anchor = GridBagConstraints.WEST;
        this.add(mensajeLado, settings);
        settings.gridx = 2;
        settings.gridy = 1;
        settings.anchor = GridBagConstraints.CENTER;
        settings.ipadx = 50;
        this.add(numeroLado, settings);
        settings.gridx = 0;
        settings.gridy = 2;
        settings.anchor = GridBagConstraints.WEST;
        this.add(mensajeMinas, settings);
        settings.gridx = 2;
        settings.gridy = 2;
        settings.anchor = GridBagConstraints.CENTER;
        settings.ipadx = 50;
        this.add(numeroMinas, settings);
        settings = new GridBagConstraints();
        botonAceptar = new JButton("Aceptar");
        settings.fill = GridBagConstraints.HORIZONTAL;
        settings.gridx = 1;
        settings.gridy = 3;
        settings.gridwidth = 3;
        settings.weightx = 1;
        settings.insets = new Insets(20, 20, 20, 20);
        this.add(botonAceptar, settings);
    }

    /**
     * Metodo para anadir el listener al boton de la interfaz
     */
    public void anadirListener() {

        botonAceptar.addActionListener((e) -> {
            int lado, mina;
            if (numeroLado.getValue() == null || (int) numeroLado.getValue() == 0) {// Si el valor del textField es nulo
                                                                                    // o =0 le daremos el valor por
                                                                                    // defecto
                lado = 10;
            } else
                lado = (int) numeroLado.getValue();
            if (lado > LADO_MAXIMO)// Queremos que el maximo de longitud sea 50 para evitar errores en la interfaz
                lado = LADO_MAXIMO;// Si es mayor de 50 le daremos el valor de 50
            if (numeroMinas.getValue() == null || (int) numeroMinas.getValue() == 0) {// Si el numero es nulo o 0 le
                                                                                      // asignaremos 20 minas por
                                                                                      // defecto
                mina = 20;
            } else
                mina = (int) numeroMinas.getValue();// Si el textField de minas tiene un valor se lo daremos

            if (lado * lado > mina) {// Si el numero de minas es mayor que el numero de casillas le daremos los
                                     // valores por defecto
                ventana.setLadoTablero(lado);
                ventana.setNumMinas(mina);
                this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                ventana.empezarCuenta();
            } else {
                ventana.setLadoTablero(10);
                ventana.setNumMinas(20);
                this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                ventana.empezarCuenta();
            }

        });
    }

}
