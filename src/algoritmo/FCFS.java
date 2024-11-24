package algoritmo;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.SwingUtilities;
import segmentacion.Particion;
import static utilidades.Calculos.dividirEnPartes;
import static utilidades.Calculos.obtenerPuntosInicio;

public class FCFS {
    
    private int tiempoActual = 0;
    private VentanaPrincipalPrincipal ventana;
    // variables de algoritmos 
    public boolean paginacionXsegmentacion;
    public int idalgoritmosRemplazo;
    private Maquina maquina = new Maquina();
    
    public FCFS(VentanaPrincipalPrincipal ventana) {
        this.ventana = ventana;
    }
    
    public void ejecutar(List<Proceso> procesos) {
        new ejecutarAFCFS(procesos).execute();
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
    
    private class ejecutarAFCFS extends SwingWorker<Void, Proceso> {
        
        private List<Proceso> procesos;
        
        public ejecutarAFCFS(List<Proceso> procesos) {
            this.procesos = procesos;
        }
        
        @Override
        protected Void doInBackground() throws Exception {
            ArrayList<Particion> particiones = new ArrayList<>();
            int tiempoTotalRafaga = procesos.stream().mapToInt(Proceso::getTiempoRafaga).sum();
// lista de procesos 
            for (Proceso proceso : procesos) {
                correrTimempo(proceso);
                cronometrarProceso(proceso);
                
                FIFO fifo = new FIFO(proceso.getTablaPaginas().getMaxMarcos(), proceso.getTablaPaginas());
                LRU lru = new LRU(5, proceso.getTablaPaginas());
                ArrayList<Integer> futurasReferencias = obtenerFuturasReferencias(proceso);
                OPT opt = new OPT(5, proceso.getTablaPaginas(), futurasReferencias);
                int rafaga = proceso.getTiempoRafaga();
                
                ArrayList<Integer> puntosFinales = dividirEnPartes(proceso.getCantidadPaginas(), proceso.getTiempoRafaga());
                ArrayList<Integer> puntosInicio = obtenerPuntosInicio(puntosFinales);
                
                proceso.setPuntosFinales(puntosFinales);
                proceso.setPuntosInicio(puntosInicio);
                
                if (paginacionXsegmentacion & idalgoritmosRemplazo == 1) {
                    asignarRecursos(proceso.getListaRecursos());
                    while (rafaga > 0) {
                        
                        int puntoinicio = proceso.getPuntosInicio().get(0);
                        int puntofinal = proceso.getPuntosFinales().get(0);
                        
                        rafaga = cpu(rafaga, proceso);
                        
                        for (int i = puntoinicio; i <= puntofinal; i++) {
                            
                            Pagina pagina = proceso.getTablaPaginas().getPaginas().get(i);
                            
                            fifo.accederPagina(pagina, "C:\\memoriavirtual\\" + pagina.getNombreArchivo());
                            
                        }
                        
                        publish(proceso);
                        
                        proceso.getPuntosInicio().remove(0);
                        proceso.getPuntosFinales().remove(0);
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }
                if (paginacionXsegmentacion & idalgoritmosRemplazo == 2) {
                    asignarRecursos(proceso.getListaRecursos());
                    while (rafaga > 0) {
                        
                        int puntoinicio = proceso.getPuntosInicio().get(0);
                        int puntofinal = proceso.getPuntosFinales().get(0);
                        
                        rafaga = cpu(rafaga, proceso);
                        
                        for (int i = puntoinicio; i <= puntofinal; i++) {
                            
                            Pagina pagina = proceso.getTablaPaginas().getPaginas().get(i);
                            
                            lru.referenciarPagina(pagina, "C:\\memoriavirtual\\" + pagina.getNombreArchivo());
                            
                        }
                        
                        publish(proceso);
                        
                        proceso.getPuntosInicio().remove(0);
                        proceso.getPuntosFinales().remove(0);
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }
                if (paginacionXsegmentacion & idalgoritmosRemplazo == 3) {
                    asignarRecursos(proceso.getListaRecursos());
                    while (rafaga > 0) {
                        
                        int puntoinicio = proceso.getPuntosInicio().get(0);
                        int puntofinal = proceso.getPuntosFinales().get(0);
                        
                        rafaga = cpu(rafaga, proceso);
                        
                        for (int i = puntoinicio; i <= puntofinal; i++) {
                            
                            Pagina pagina = proceso.getTablaPaginas().getPaginas().get(i);
                            
                            opt.referenciarPagina(pagina, "C:\\memoriavirtual\\" + pagina.getNombreArchivo());
                            
                        }
                        
                        publish(proceso);
                        
                        proceso.getPuntosInicio().remove(0);
                        proceso.getPuntosFinales().remove(0);
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }

//                   System.out.println(proceso.getIdProceso() + " ha terminado.");
                if (!paginacionXsegmentacion && idalgoritmosRemplazo == 1) { // Primer Ajuste
                    asignarRecursos(proceso.getListaRecursos());
                    PrimerAjuste primerAjuste = new PrimerAjuste();
                    if (primerAjuste.asignar(proceso, particiones)) {
                        while (rafaga > 0) {
                            rafaga = cpu(rafaga, proceso);
                            publish(proceso);
                        }
                        primerAjuste.desasignar(proceso, particiones); // Liberar memoria al finalizar
                    } else {
                        System.out.println("No se pudo asignar el proceso con Primer Ajuste.");
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }
                
                if (!paginacionXsegmentacion && idalgoritmosRemplazo == 2) { // Mejor Ajuste
                    asignarRecursos(proceso.getListaRecursos());
                    MejorAjuste mejorAjuste = new MejorAjuste();
                    if (mejorAjuste.asignar(proceso, particiones)) {
                        while (rafaga > 0) {
                            rafaga = cpu(rafaga, proceso);
                            publish(proceso);
                        }
                        mejorAjuste.desasignar(proceso, particiones); // Libera la memoria al finalizar
                    } else {
                        System.out.println("No se pudo asignar el proceso con Mejor Ajuste.");
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }
                
                if (!paginacionXsegmentacion && idalgoritmosRemplazo == 3) { // Peor Ajuste
                    asignarRecursos(proceso.getListaRecursos());
                    PeorAjuste peorAjuste = new PeorAjuste();
                    if (peorAjuste.asignar(proceso, particiones)) {
                        while (rafaga > 0) {
                            rafaga = cpu(rafaga, proceso);
                            publish(proceso);
                        }
                        peorAjuste.desasignar(proceso, particiones); // Libera la memoria al finalizar
                    } else {
                        System.out.println("No se pudo asignar el proceso con Peor Ajuste.");
                    }
                    liberarRecursos(proceso.getListaRecursos());
                }
                
                proceso.setTiempoRestante(0);
                proceso.setEstado("terminadoo");
            }
            JOptionPane.showMessageDialog(null, "Simulación completada.");
            return null;
        }
        
        @Override
        protected void process(List<Proceso> chunks) {
            for (Proceso proceso : chunks) {
                actualizarGrafica(proceso);
            }
        }
    }
    
    private void correrTimempo(Proceso proceso) {
        if (tiempoActual < proceso.getTiempoLlegada()) {
            tiempoActual = proceso.getTiempoLlegada();
        }
    }
    
    public int cpu(int rafaga, Proceso proceso) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FCFS.class.getName()).log(Level.SEVERE, null, ex);
        }
        rafaga--;
        proceso.setTiempoRestante(rafaga);
        return rafaga;
    }
    
    public void cronometrarProceso(Proceso proceso) {
        proceso.setTiempoInicio(tiempoActual);
        proceso.setTiempoFinalizacion(tiempoActual + proceso.getTiempoRafaga());
        tiempoActual += proceso.getTiempoRafaga();
        
    }
    
    private void actualizarGrafica(Proceso proceso) {
        SwingUtilities.invokeLater(() -> {
            ventana.actualizarBarraDeProgreso(proceso);
        });
    }
    
    public boolean getPaginacionXsegmentacion() {
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
    
    public ArrayList<Integer> obtenerFuturasReferencias(Proceso proceso) {
        ArrayList<Integer> futurasReferencias = new ArrayList<>();
        
        for (Pagina pagina : proceso.getTablaPaginas().getPaginas()) {
            futurasReferencias.add(pagina.getIdPagina());
        }
        
        return futurasReferencias;
    }
    
}
