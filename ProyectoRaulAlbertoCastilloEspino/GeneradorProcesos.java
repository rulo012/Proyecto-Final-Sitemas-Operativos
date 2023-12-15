package com.mycompany.proyectoindividualsistemas;
import java.util.Random;

public class GeneradorProcesos extends Thread {
    private final Interfaz interfaz; 
    private final Random random; 

    public GeneradorProcesos(Interfaz interfaz, int capacidadMemoriaMaxima) {
        this.interfaz = interfaz;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            int tiempoAleatorio = generarTiempoAleatorio();
            int memoriaAleatoria = generarMemoriaAleatoria();

            Proceso nuevoProceso = new Proceso(interfaz, tiempoAleatorio, memoriaAleatoria);
            
            if (interfaz.verificarMemoriaDisponible(memoriaAleatoria)) {
                interfaz.agregarProceso(nuevoProceso);
            } else {
            }

            try {
                sleep(generarTiempoEntreProcesos());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int generarTiempoAleatorio() {
        return (random.nextInt(50)+1) * 100; 
    }

    private int generarTiempoEntreProcesos() {
        return random.nextInt(1200) + 1000; 
    }

    private int generarMemoriaAleatoria() {
        return (random.nextInt(10)+1) * 10; 
    }
}
