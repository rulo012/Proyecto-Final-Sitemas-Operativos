
package com.mycompany.proyectoindividualsistemas;

/**
 * La clase Proceso representa un proceso individual en la simulación. Cada proceso tiene un ID único,
 * un tiempo de ejecución, una cantidad de memoria asignada y un estado.
 */
public class Proceso implements Runnable {
    private static int contadorProcesos = 0; // Contador estático para asignar ID único a cada proceso
    private int idP; // ID del proceso
    private int tiempo; // Tiempo de ejecución del proceso
    private int memoriaAsignada; // Cantidad de memoria asignada al proceso
    private String estado;
    private Animacion animacion;// Estado actual del proceso (ej., "En ejecución", "Completado", "Interrumpido")

    /**
     * Constructor de la clase Proceso.
     *
     * @param tiempo El tiempo de ejecución del proceso.
     * @param memoriaAsignada La cantidad de memoria asignada al proceso.
     */
    public Proceso(Animacion animacion, int tiempo, int memoriaAsignada) {
        this.idP = ++contadorProcesos; // Asigna un ID único
        this.tiempo = tiempo;
        this.memoriaAsignada = memoriaAsignada;
        this.estado = "En lista de espera"; // Estado inicial del proceso
        this.animacion = animacion;
    }

    // Getters y setters

    public int getIdP() {
        return idP;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getMemoriaAsignada() {
        return memoriaAsignada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * El método run() define la lógica de ejecución del proceso. Este método es llamado cuando el hilo
     * del proceso se inicia.
     */
    @Override
    public void run() {
        try {
            estado = "En ejecución";
            while (tiempo > 0) {
                tiempo-=30; // Simula la ejecución reduciendo el tiempo restante
                Thread.sleep(100); // Simula un tiempo de procesamiento
            }
            estado = "Completado";
            Thread.sleep(2000); 
            animacion.eliminarProceso(this);// Actualiza el estado a completado una vez finalizado el tiempo
        } catch (InterruptedException e) {
            estado = "Interrumpido"; // Actualiza el estado a interrumpido en caso de interrupción
        } finally {
            // Aquí puedes agregar cualquier limpieza o actualización de la interfaz necesaria después de la ejecución
        }
    }
}
