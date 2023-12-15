package com.mycompany.proyectoindividualsistemas;
import java.util.Random;
import javax.swing.JLabel;

public class GeneradorProcesos extends Thread {
    private final Simulacion simulacion; 
    private final Random random; 
    private final int capacidadMemoriaMaxima; 

    public GeneradorProcesos(Simulacion simulacion, int capacidadMemoriaMaxima) {
        this.simulacion = simulacion;
        this.random = new Random();
        this.capacidadMemoriaMaxima = capacidadMemoriaMaxima;
    }

   
    @Override
    public void run() {
        while (true) {
            int tiempoAleatorio = generarTiempoAleatorio();
            int memoriaAleatoria = generarMemoriaAleatoria();

            Proceso nuevoProceso = new Proceso(simulacion,tiempoAleatorio, memoriaAleatoria);
            
        
            if (simulacion.verificarMemoriaDisponible(memoriaAleatoria)) {
                simulacion.agregarProceso(nuevoProceso);
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
       
        return (random.nextInt(50)+1) * 30; 
    }
    private int generarTiempoEntreProcesos() {
        
        return random.nextInt(1200) + 800; 
    }

  
    private int generarMemoriaAleatoria() {
       
        return (random.nextInt(10)+1) * 10; 
    }
}
