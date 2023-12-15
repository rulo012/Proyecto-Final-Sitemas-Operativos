package com.mycompany.proyectoindividualsistemas;

public class SistemaOperativo extends Thread {
    private final Interfaz interfaz; 
    public final int capacidadMemoriaMaxima; 
    public int memoriaUtilizada; 

    public SistemaOperativo(Interfaz interfaz, int capacidadMemoriaMaxima) {
        this.interfaz = interfaz;
        this.capacidadMemoriaMaxima = capacidadMemoriaMaxima;
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
            }
        }
    }

    private Proceso seleccionarProceso() {
    Proceso procesoAEjecutar = null;

    synchronized (interfaz.listaEspera) {
        for (Proceso proceso : interfaz.listaEspera) {
            if (memoriaUtilizada + proceso.getMemoriaAsignada() <= capacidadMemoriaMaxima) {
                procesoAEjecutar = proceso;
                break;
            }
        }
    }

    if (procesoAEjecutar != null) {
        interfaz.listaEspera.remove(procesoAEjecutar);
        interfaz.listaEspera.add(procesoAEjecutar);
    }

    return procesoAEjecutar;
}

    
    private void asignarTiempoCPU(Proceso proceso) {
        proceso.setEstado("Ejecutando");
        Thread procesoThread = new Thread(proceso);

        memoriaUtilizada += proceso.getMemoriaAsignada();
        procesoThread.start(); 

        try {
            procesoThread.join();
            proceso.setEstado("Finalizado"); 
            Thread.sleep(1000); 
            interfaz.eliminarProceso(proceso); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memoriaUtilizada -= proceso.getMemoriaAsignada(); 
    }
}
