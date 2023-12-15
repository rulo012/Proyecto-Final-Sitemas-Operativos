package com.mycompany.proyectoindividualsistemas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kahun
 */
public class Animacion extends javax.swing.JFrame {
    private SistemaOperativo sistemaOperativo;
    private GeneradorProcesos generadorProcesos;
    public static List<Proceso> listaEspera = new ArrayList<>();
    public static DefaultTableModel modeloTabla;

    /**
     * Constructor de la clase Animacion.
     */
    public Animacion() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Simulación de procesos por Priority Scheduling");

        sistemaOperativo = new SistemaOperativo(this, 500); // Ajusta el valor según tus necesidades
        generadorProcesos = new GeneradorProcesos(this, 500); // Asumiendo 500 MB como la capacidad máxima

        modeloTabla = (DefaultTableModel) jTable2.getModel();
    }
    
    public JLabel getMensaje(){
        return mensaje;
    }
    
    public JLabel getTope(){
        return tope;
    }
    
    public void actualizarInterfaz() {
        SwingUtilities.invokeLater(() -> {
            modeloTabla.setRowCount(0);
            synchronized (listaEspera) {
                for (Proceso proceso : listaEspera) {
                    Object[] rowData = {proceso.getIdP(), proceso.getTiempo(), proceso.getMemoriaAsignada(), proceso.getEstado()};
                    modeloTabla.addRow(rowData);
                }
            }
            jTable2.setModel(modeloTabla);
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Fondo = new javax.swing.JPanel();
        boton = new javax.swing.JButton();
        mensaje = new javax.swing.JLabel();
        tope = new javax.swing.JLabel();
        tiempo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Fondo.setBackground(new java.awt.Color(150, 186, 218));
        Fondo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        boton.setText("Iniciar");
        boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActionPerformed(evt);
            }
        });
        Fondo.add(boton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 420, -1, -1));

        mensaje.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        Fondo.add(mensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 200, 40));

        tope.setBackground(new java.awt.Color(149, 185, 216));
        Fondo.add(tope, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 20, 210));

        tiempo.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        Fondo.add(tiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 290, 30));

        jLabel1.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        jLabel1.setText("Simulador");
        Fondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Proceso", "Tiempo", "Memoria", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Fondo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 490, 320));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>                        

    private void botonActionPerformed(java.awt.event.ActionEvent evt) {
        sistemaOperativo.start();
        generadorProcesos.start();
    }                                     

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Animacion().setVisible(true);
            }
        });
    }
    public synchronized boolean verificarMemoriaDisponible(int memoriaSolicitada) {
        if(sistemaOperativo.memoriaUtilizada<sistemaOperativo.capacidadMemoriaMaxima){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Agrega un proceso a la lista de espera y actualiza la interfaz gráfica.
     *
     * @param proceso El proceso a agregar.
     */
    public synchronized void agregarProceso(Proceso proceso) {
        listaEspera.add(proceso);
        actualizarInterfaz();
    }
    
    public synchronized void eliminarProceso(Proceso proceso) {
        int fila=proceso.getIdP();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                if ((int) modeloTabla.getValueAt(i, 0) == fila) {
                    listaEspera.remove(i);
                    break; // Rompe el bucle después de eliminar la primera ocurrencia
                }
            }
        actualizarInterfaz();
    }
    // Variables declaration - do not modify                     
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton boton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel mensaje;
    private javax.swing.JLabel tiempo;
    private javax.swing.JLabel tope;
    // End of variables declaration                   
}