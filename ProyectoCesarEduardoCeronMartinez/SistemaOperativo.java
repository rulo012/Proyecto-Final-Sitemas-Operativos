package com.mycompany.proyectoindividualsistemas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * La clase SistemaOperativo simula el funcionamiento de un sistema operativo en la gestión de procesos.
 * Extiende de Thread para operar como un hilo independiente.
 */
public class SistemaOperativo extends Thread {
    private final Animacion animacion; // Referencia a la interfaz gráfica
    public final int capacidadMemoriaMaxima; // Capacidad máxima de memoria del sistema
    public int memoriaUtilizada; // Memoria actualmente utilizada

    /**
     * Constructor de la clase SistemaOperativo.
     *
     * @param animacion La referencia a la interfaz gráfica donde se actualizan los procesos.
     * @param capacidadMemoriaMaxima La capacidad máxima de memoria del sistema.
     */
    public SistemaOperativo(Animacion animacion, int capacidadMemoriaMaxima) {
        this.animacion = animacion;
        this.capacidadMemoriaMaxima = capacidadMemoriaMaxima;
        this.memoriaUtilizada = 0;
    }

    /**
     * El método run() define la lógica de ejecución del hilo para la gestión de procesos.
     */
    @Override
    public void run() {
        while (true) {
            Proceso procesoAEjecutar = seleccionarProceso();

            if (procesoAEjecutar != null) {
                asignarTiempoCPU(procesoAEjecutar);
            }

            try {
                sleep(1000); // Intervalo para verificar el siguiente proceso
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Selecciona el próximo proceso a ejecutar utilizando el algoritmo de planificación Priority Scheduling.
     *
     * @return El proceso seleccionado para ejecución.
     */
    private Proceso seleccionarProceso() {
        List<Proceso> procesosElegibles = new ArrayList<>();
        synchronized (animacion.listaEspera) {
            for (Proceso proceso : animacion.listaEspera) {
                if (memoriaUtilizada + proceso.getMemoriaAsignada() <= capacidadMemoriaMaxima) {
                    procesosElegibles.add(proceso);
                }
            }
        }

        return procesosElegibles.stream()
                .min(Comparator.comparingInt(Proceso::getTiempo)) // Cambiar a getPrioridad() si se implementa la prioridad
                .orElse(null);
    }

    /**
     * Asigna tiempo de CPU al proceso seleccionado y actualiza la gestión de memoria.
     *
     * @param proceso El proceso a ejecutar.
     */
    private void asignarTiempoCPU(Proceso proceso) {
        proceso.setEstado("En ejecucion");
        Thread procesoThread = new Thread(proceso);

        memoriaUtilizada += proceso.getMemoriaAsignada();
        procesoThread.start(); // Inicia la ejecución del proceso

        // Espera a que el proceso termine
        try {
            procesoThread.join();
            proceso.setEstado("Completado"); 
            Thread.sleep(1000); // Espera un segundo antes de eliminar el proceso de la tabla
            animacion.eliminarProceso(proceso); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memoriaUtilizada -= proceso.getMemoriaAsignada(); // Libera la memoria utilizada
}
}
