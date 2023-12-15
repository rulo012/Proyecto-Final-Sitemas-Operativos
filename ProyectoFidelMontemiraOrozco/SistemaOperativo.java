
package com.mycompany.proyectoindividualsistemas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.SwingUtilities;


public class SistemaOperativo extends Thread {
    private final Simulacion simulacion; 
    public final int capacidadMemoria; 
    public int memoriaUtilizada; 

  
    public SistemaOperativo(Simulacion simulacion, int capacidadMemoria) {
        this.simulacion = simulacion;
        this.capacidadMemoria = capacidadMemoria;
        this.memoriaUtilizada = 0;
    }

   
    @Override
    public void run() {
        while (true) {
            Proceso procesoAEjecutar = seleccionarProceso();

            if (procesoAEjecutar != null) {
                asignarTiempoCPU(procesoAEjecutar);
            }

            try {
                sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

   
   private Proceso seleccionarProceso() {
    Proceso procesoSeleccionado = null;
    int tiempoMinimo = Integer.MAX_VALUE;

    synchronized (simulacion.listaEspera) {
        for (Proceso proceso : simulacion.listaEspera) {
            if (memoriaUtilizada + proceso.getMemoriaAsignada() <= capacidadMemoria &&
                proceso.getTiempo() < tiempoMinimo) {
                procesoSeleccionado = proceso;
                tiempoMinimo = proceso.getTiempo();
            }
        }
    }

    return procesoSeleccionado;
}

   
    private void asignarTiempoCPU(Proceso proceso) {
        proceso.setEstado("En ejecucion");
        Thread procesoThread = new Thread(proceso);

        memoriaUtilizada += proceso.getMemoriaAsignada();
        procesoThread.start(); 

        
        try {
            procesoThread.join();
            proceso.setEstado("Completado"); 
            Thread.sleep(1000); 
            simulacion.eliminarProceso(proceso); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memoriaUtilizada -= proceso.getMemoriaAsignada(); 
}
}
