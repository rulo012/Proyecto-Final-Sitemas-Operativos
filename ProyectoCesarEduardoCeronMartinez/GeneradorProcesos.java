package com.mycompany.proyectoindividualsistemas;
import java.util.Random;
import javax.swing.JLabel;

/**
 * La clase GeneradorProcesos se encarga de generar procesos de manera aleatoria.
 * Extiende de Thread, lo que le permite funcionar como un hilo independiente en la simulación.
 */
public class GeneradorProcesos extends Thread {
    private final Animacion animacion; // Referencia a la interfaz gráfica
    private final Random random; // Generador de números aleatorios
    private final int capacidadMemoriaMaxima; // Capacidad máxima de memoria para controlar la generación de procesos

    /**
     * Constructor de la clase GeneradorProcesos.
     *
     * @param animacion La referencia a la interfaz gráfica donde se actualizan los procesos.
     * @param capacidadMemoriaMaxima La capacidad máxima de memoria del sistema.
     */
    public GeneradorProcesos(Animacion animacion, int capacidadMemoriaMaxima) {
        this.animacion = animacion;
        this.random = new Random();
        this.capacidadMemoriaMaxima = capacidadMemoriaMaxima;
    }

    /**
     * El método run() define la lógica de ejecución del hilo para la generación de procesos.
     */
    @Override
    public void run() {
        while (true) {
            int tiempoAleatorio = generarTiempoAleatorio();
            int memoriaAleatoria = generarMemoriaAleatoria();

            Proceso nuevoProceso = new Proceso(animacion,tiempoAleatorio, memoriaAleatoria);
            
            // Verifica la memoria disponible antes de añadir el proceso
            if (animacion.verificarMemoriaDisponible(memoriaAleatoria)) {
                animacion.agregarProceso(nuevoProceso);
            } else {
                // Manejo cuando no hay suficiente memoria
                // Puedes poner el proceso en una cola de espera o simplemente omitirlo
            }

            try {
                sleep(generarTiempoEntreProcesos());
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Manejo de la interrupción del hilo
            }
        }
    }

    /**
     * Genera un tiempo aleatorio para la ejecución del proceso.
     *
     * @return Un valor de tiempo aleatorio.
     */
    private int generarTiempoAleatorio() {
        // Lógica para generar el tiempo aleatorio según tus necesidades
        return (random.nextInt(50)+1) * 100; // Ejemplo: entre 1 y 500 milisegundos
    }

    /**
     * Genera un tiempo entre la creación de procesos consecutivos.
     *
     * @return Un valor de tiempo para esperar antes de generar el siguiente proceso.
     */
    private int generarTiempoEntreProcesos() {
        // Lógica para generar el tiempo entre procesos según tus necesidades
        return random.nextInt(1200) + 1000; // Ejemplo: entre 1 y 3 segundos
    }

    /**
     * Genera una cantidad aleatoria de memoria que se asignará al proceso.
     *
     * @return Un valor de memoria aleatorio.
     */
    private int generarMemoriaAleatoria() {
        // Lógica para generar la memoria aleatoria según tus necesidades
        // Asegúrate de que no supere la capacidad máxima
        return (random.nextInt(10)+1) * 10; // Ejemplo: entre 10 y 1/10 de la capacidad máxima
    }
}
