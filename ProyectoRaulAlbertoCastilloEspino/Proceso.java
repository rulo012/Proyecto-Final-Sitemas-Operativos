package com.mycompany.proyectoindividualsistemas;

public class Proceso implements Runnable {
    private static int contadorProcesos = 0; 
    private int idP; 
    private int tiempo; 
    private int memoriaAsignada; 
    private String estado;
    private Interfaz interfaz;

    public Proceso(Interfaz interfaz, int tiempo, int memoriaAsignada) {
        this.idP = ++contadorProcesos; 
        this.tiempo = tiempo;
        this.memoriaAsignada = memoriaAsignada;
        this.estado = "En cola"; 
        this.interfaz = interfaz;
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
                tiempo -= 30; 
                Thread.sleep(100); 
            }
            estado = "Finalizado";
            Thread.sleep(2000); 
            interfaz.eliminarProceso(this);
        } catch (InterruptedException e) {
            estado = "Interrumpido"; 
        } finally {
        }
    }
}
