
package com.mycompany.proyectoindividualsistemas;


public class Proceso implements Runnable {
    private static int contadorProcesos = 0; 
    private int idP; 
    private int tiempo; 
    private int memoriaAsignada; 
    private String estado;
    private Simulacion simulacion;

    public Proceso(Simulacion simulacion, int tiempo, int memoriaAsignada) {
        this.idP = ++contadorProcesos; // Asigna un ID único
        this.tiempo = tiempo;
        this.memoriaAsignada = memoriaAsignada;
        this.estado = "Esperando..."; // Estado inicial del proceso
        this.simulacion = simulacion;
    }


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

    @Override
    public void run() {
        try {
            estado = "Ejecutando";
            while (tiempo > 0) {
                tiempo-=30; 
                Thread.sleep(100);
            }
            estado = "Terminado";
            Thread.sleep(2000); 
            simulacion.eliminarProceso(this);
        } catch (InterruptedException e) {
            estado = "Hubo una interrupción"; 
        } finally {
            
        }
    }
}
