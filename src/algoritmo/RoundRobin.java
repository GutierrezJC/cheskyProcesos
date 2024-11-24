/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmo;

/**
 *
 * @author jeank
 */
import AlgoritmosRemplazo.FIFO;
import AlgoritmosRemplazo.LRU;
import AlgoritmosRemplazo.OPT;
import AlgortitmosSegmentacion.MejorAjuste;
import AlgortitmosSegmentacion.PeorAjuste;
import AlgortitmosSegmentacion.PrimerAjuste;
import Objetos.Maquina;
import Objetos.Pagina;
import interfaz.VentanaPrincipalPrincipal;
import Objetos.Proceso;
import Objetos.Recurso;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.SwingUtilities;
import segmentacion.Particion;
import utilidades.Calculos;
import static utilidades.Calculos.calcularPuntosFinal;
import static utilidades.Calculos.calcularPuntosInicio;

public class RoundRobin {

    private int quantum;
    private VentanaPrincipalPrincipal ventana; // Referencia a la ventana principal para actualizar la GUI
    public boolean paginacionXsegmentacion;
    public int idalgoritmosRemplazo;
    private int tiempoActual = 0;
    ArrayList<Integer> puntosFinales = new ArrayList<>();
    ArrayList<Integer> puntosInicio = new ArrayList<>();
    private Maquina maquina = new Maquina();

    public RoundRobin(int quantum, VentanaPrincipalPrincipal ventana) {
        this.quantum = quantum;
        this.ventana = ventana; // Guardamos la referencia a la ventana
    }

    public void ejecutar(List<Proceso> procesos) {
        new ejecutarRoundR(procesos).execute();
    }

    public List<Proceso> liberarProcesosSobrepasoRecursos(List<Proceso> procesosOrdenados) {
        for (int i = 0; i < procesosOrdenados.size(); i++) {
            if (maquina.recursosSobrepasaMaquina(procesosOrdenados.get(i).getListaRecursos())) {
                procesosOrdenados.remove(i);
            }
        }
        return procesosOrdenados;
    }

    public void asignarRecursos(ArrayList<Recurso> recursos) {
        maquina.asignarRecurso(recursos);
    }

    public void liberarRecursos(ArrayList<Recurso> recursos) {
        maquina.liberarRecurso(recursos);
    }

    private class ejecutarRoundR extends SwingWorker<Void, Proceso> {

        private List<Proceso> procesos;
        private Queue<Proceso> colaProcecos;
        ArrayList<Proceso> listaunicos;
        int contador = 0;

        public ejecutarRoundR(List<Proceso> procesos) {
            this.procesos = procesos;
            this.colaProcecos = new LinkedList<>(procesos);
            this.listaunicos = new ArrayList<>();
        }

        @Override
        protected Void doInBackground() throws Exception {

            ArrayList<Particion> particiones = new ArrayList<>();

            while (!colaProcecos.isEmpty()) {
                Proceso proceso = colaProcecos.poll();

                FIFO fifo = new FIFO(5, proceso.getTablaPaginas());
                LRU lru = new LRU(5, proceso.getTablaPaginas());
                ArrayList<Integer> futurasReferencias = obtenerFuturasReferencias(proceso);
                OPT opt = new OPT(5, proceso.getTablaPaginas(), futurasReferencias);

                if (!listaunicos.contains(proceso)) {
                    listaunicos.add(proceso);
                    ArrayList<Integer> puntosInicio = calcularPuntosInicio(proceso.getCantidadPaginas(), proceso.getTiempoRafaga(), quantum);
                    ArrayList<Integer> puntosFinales = calcularPuntosFinal(puntosInicio, proceso.getCantidadPaginas());

                    proceso.setPuntosFinales(puntosFinales);
                    proceso.setPuntosInicio(puntosInicio);
                } else {
                    fifo = proceso.getUnico();
                }

                if (paginacionXsegmentacion && idalgoritmosRemplazo == 1) {
                    asignarRecursos(proceso.getListaRecursos());
                    if (proceso.getTiempoRestante() >= quantum) {
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - quantum);

                        int puntoinicio = proceso.getPuntosInicio().get(0);
                        int puntofinal = proceso.getPuntosFinales().get(0);

                        for (int i = puntoinicio; i <= puntofinal; i++) {
                            Pagina pagina = proceso.getTablaPaginas().getPaginas().get(i);

                            fifo.accederPagina(pagina, "C:\\memoriavirtual\\" + pagina.getNombreArchivo());
                        }

                        proceso.getPuntosInicio().remove(0);
                        proceso.getPuntosFinales().remove(0);
                        publish(proceso);
                        cpu();

                        if (!listaunicos.contains(proceso)) {
                        } else {
                            proceso.setUnico(fifo);
                        }
                        if (proceso.getTiempoRestante() != 0) {
                            colaProcecos.offer(proceso);
                        }

                    } else {

                        publish(proceso);
                        proceso.setTiempoRestante(0); // Proceso terminado
                        //                    System.out.println(proceso.getIdProceso() + " ha terminado.");
                        cpu();

                    }
                    liberarRecursos(proceso.getListaRecursos());
                }

                if (paginacionXsegmentacion && idalgoritmosRemplazo == 2) {
                    asignarRecursos(proceso.getListaRecursos());
                    if (proceso.getTiempoRestante() >= quantum) {
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - quantum);

                        int puntoinicio = proceso.getPuntosInicio().get(0);
                        int puntofinal = proceso.getPuntosFinales().get(0);

                        for (int i = puntoinicio; i <= puntofinal; i++) {
                            Pagina pagina = proceso.getTablaPaginas().getPaginas().get(i);
                            lru.referenciarPagina(pagina, "C:\\memoriavirtual\\" + pagina.getNombreArchivo());
                        }
                        proceso.getPuntosInicio().remove(0);
                        proceso.getPuntosFinales().remove(0);
                        publish(proceso);

                        cpu();
                        if (proceso.getTiempoRestante() != 0) {
                            colaProcecos.offer(proceso);
                        }

                        // Volver a la colaProcecos si no ha terminado
                    } else {

                        publish(proceso);
                        proceso.setTiempoRestante(0); // Proceso terminado
                        //                    System.out.println(proceso.getIdProceso() + " ha terminado.");
                        cpu();
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }

                if (paginacionXsegmentacion && idalgoritmosRemplazo == 3) {
                    asignarRecursos(proceso.getListaRecursos());
                    if (proceso.getTiempoRestante() > quantum) {
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - quantum);

                        int puntoinicio = proceso.getPuntosInicio().get(0);
                        int puntofinal = proceso.getPuntosFinales().get(0);

                        for (int i = puntoinicio; i <= puntofinal; i++) {
                            Pagina pagina = proceso.getTablaPaginas().getPaginas().get(i);
                            opt.referenciarPagina(pagina, "C:\\memoriavirtual\\" + pagina.getNombreArchivo());
                        }
                        proceso.getPuntosInicio().remove(0);
                        proceso.getPuntosFinales().remove(0);
                        publish(proceso);
                        cpu();
                        colaProcecos.offer(proceso); // Volver a la colaProcecos si no ha terminado
                    } else {

                        publish(proceso);
                        proceso.setTiempoRestante(0); // Proceso terminado
                        //                    System.out.println(proceso.getIdProceso() + " ha terminado.");
                        cpu();
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }

                //Aqui se colocan los tres if de segmentacion
                // Primer Ajuste
                if (!paginacionXsegmentacion && idalgoritmosRemplazo == 1) {
                    asignarRecursos(proceso.getListaRecursos());
                    PrimerAjuste primerAjuste = new PrimerAjuste();

                    if (primerAjuste.asignar(proceso, particiones)) {
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - quantum);
                        publish(proceso);
                        cpu();

                        if (proceso.getTiempoRestante() > 0) {
                            colaProcecos.offer(proceso); // Reagregar a la cola si no ha terminado
                        } else {
                            primerAjuste.desasignar(proceso, particiones); // Liberar memoria si ha terminado
                        }
                    } else {
                        System.out.println("No se encontró una partición para el proceso con Primer Ajuste.");
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }

// Mejor Ajuste
                if (!paginacionXsegmentacion && idalgoritmosRemplazo == 2) {
                    asignarRecursos(proceso.getListaRecursos());
                    MejorAjuste mejorAjuste = new MejorAjuste();

                    if (mejorAjuste.asignar(proceso, particiones)) {
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - quantum);
                        publish(proceso);
                        cpu();

                        if (proceso.getTiempoRestante() > 0) {
                            colaProcecos.offer(proceso);
                        } else {
                            mejorAjuste.desasignar(proceso, particiones);
                        }
                    } else {
                        System.out.println("No se encontró una partición para el proceso con Mejor Ajuste.");
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }

// Peor Ajuste
                if (!paginacionXsegmentacion && idalgoritmosRemplazo == 3) {
                    asignarRecursos(proceso.getListaRecursos());
                    PeorAjuste peorAjuste = new PeorAjuste();

                    if (peorAjuste.asignar(proceso, particiones)) {
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - quantum);
                        publish(proceso);
                        cpu();

                        if (proceso.getTiempoRestante() > 0) {
                            colaProcecos.offer(proceso);
                        } else {
                            peorAjuste.desasignar(proceso, particiones);
                        }
                    } else {
                        System.out.println("No se encontró una partición para el proceso con Peor Ajuste.");
                    }
                }
                liberarRecursos(proceso.getListaRecursos());
            }
            JOptionPane.showMessageDialog(null, "Simulación completada.");
            return null;
        }

        @Override
        protected void process(List<Proceso> chunks
        ) {
            for (Proceso proceso : chunks) {
                // Aquí, actualiza los componentes de tu GUI con la información del proceso
                actualizarGrafica(proceso);
            }
        }
    }

    public ArrayList<Integer> obtenerFuturasReferencias(Proceso proceso) {
        ArrayList<Integer> futurasReferencias = new ArrayList<>();

        for (Pagina pagina : proceso.getTablaPaginas().getPaginas()) {
            futurasReferencias.add(pagina.getIdPagina());
        }

        return futurasReferencias;
    }

    public void cpu() {
        try {
            Thread.sleep(2000);  // Simula que el proceso está corriendo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void actualizarGrafica(Proceso proceso) {
        SwingUtilities.invokeLater(() -> {
            ventana.actualizarBarraDeProgreso(proceso);
        });
    }

    public void cantidadPaginas(Proceso proceso, int quantum) {
        int catidadaPaginas = proceso.getCantidadPaginas();
        int cantidaTiempoenCpu = proceso.getTiempoRafaga();
        if (cantidaTiempoenCpu < quantum) {

        } else {

        }
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public VentanaPrincipalPrincipal getVentana() {
        return ventana;
    }

    public void setVentana(VentanaPrincipalPrincipal ventana) {
        this.ventana = ventana;
    }

    public boolean isPaginacionXsegmentacion() {
        return paginacionXsegmentacion;
    }

    public void setPaginacionXsegmentacion(boolean paginacionXsegmentacion) {
        this.paginacionXsegmentacion = paginacionXsegmentacion;
    }

    public int getIdalgoritmosRemplazo() {
        return idalgoritmosRemplazo;
    }

    public void setIdalgoritmosRemplazo(int idalgoritmosRemplazo) {
        this.idalgoritmosRemplazo = idalgoritmosRemplazo;
    }

    public int getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoActual(int tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    public ArrayList<Integer> getPuntosFinales() {
        return puntosFinales;
    }

    public void setPuntosFinales(ArrayList<Integer> puntosFinales) {
        this.puntosFinales = puntosFinales;
    }

    public ArrayList<Integer> getPuntosInicio() {
        return puntosInicio;
    }

    public void setPuntosInicio(ArrayList<Integer> puntosInicio) {
        this.puntosInicio = puntosInicio;
    }
}
