/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import AlgoritmosRemplazo.FIFO;
import java.util.ArrayList;

import java.util.Random;

public class Proceso {

    private int idProceso;
    private String nombre;  // falta
    private int tamañoEnBytes;
    private int cantidadPaginas;

    private int tiempoInicio;
    private int tiempoFinalizacion;
    private int tiempoLlegada;
    private int tiempoRafaga;
    private int tiempoRestante;
    private String estado;
    private int prioridad;
    private ArrayList<Recurso> listaRecursos;

    // solol para guardar
    FIFO unico;

    ArrayList<Integer> puntosFinales = new ArrayList<>();
    ArrayList<Integer> puntosInicio = new ArrayList<>();

    private TablaPaginas tablaPaginas;   // Referencia a la tabla de páginas para este proceso

    // Constructor
    public Proceso(int idProceso, String nombre, int tamañoEnBytes, int tamañoPagina, int tiempoLlegada, int tiempoRafaga, int prioridad, int maxMarcos) {
        this.idProceso = idProceso;
        this.nombre = nombre;
        this.tamañoEnBytes = tamañoEnBytes;
        this.cantidadPaginas = (int) Math.ceil((double) tamañoEnBytes / tamañoPagina);// paginas del proceso osea las virtuales 
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoRestante = tiempoRafaga;
        this.prioridad = prioridad;
        this.estado = "Listo";           // TablaPaginas(int idTabla, int maxPaginas,int maxMarcos, )
        this.tablaPaginas = new TablaPaginas(idProceso, cantidadPaginas, maxMarcos);
        inicializarRecursos();
        generarPaginas();
    }

    private void generarPaginas() {
        Random random = new Random();
        for (int i = 1; i <= cantidadPaginas; i++) {
            String nombreArchivo = "P" + idProceso + "_Pagina" + i;
            Pagina nuevaPagina = new Pagina(i, random.nextInt(100), nombreArchivo);
            tablaPaginas.agregarPagina(nuevaPagina); // Solo se agrega a la tabla de páginas
        }
    }

    // Métodos Getters y Setters
    public int getIdProceso() {
        return idProceso;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTamañoEnBytes() {
        return tamañoEnBytes;
    }

    public int getCantidadPaginas() {
        return cantidadPaginas;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempoRafaga() {
        return tiempoRafaga;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public int getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(int tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public void setTiempoFinalizacion(int tiempoFinalizacion) {
        this.tiempoFinalizacion = tiempoFinalizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TablaPaginas getTablaPaginas() {
        return tablaPaginas;
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

    public FIFO getUnico() {
        return unico;
    }

    public void setUnico(FIFO unico) {
        this.unico = unico;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTamañoEnBytes(int tamañoEnBytes) {
        this.tamañoEnBytes = tamañoEnBytes;
    }

    public void setCantidadPaginas(int cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public void setTiempoRafaga(int tiempoRafaga) {
        this.tiempoRafaga = tiempoRafaga;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public ArrayList<Recurso> getListaRecursos() {
        return listaRecursos;
    }

    public void setListaRecursos(ArrayList<Recurso> listaRecursos) {
        this.listaRecursos = listaRecursos;
    }

    public void inicializarRecursos() {
        this.listaRecursos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listaRecursos.add(new Recurso(i, "Recurso_" + i, 50));
        }
    }

    @Override
    public String toString() {
        return "Proceso{"
                + "idProceso=" + idProceso
                + ", nombre='" + nombre + '\''
                + ", tamañoEnBytes=" + tamañoEnBytes
                + ", cantidadPaginas=" + cantidadPaginas
                + ", prioridad=" + prioridad
                + ", tiempoRafaga=" + tiempoRafaga
                + ", tiempoLlegada=" + tiempoLlegada
                + ", tiempoRestante=" + tiempoRestante
                + ", tiempoInicio=" + tiempoInicio
                + ", tiempoFinalizacion=" + tiempoFinalizacion
                + ", estado='" + estado + '\''
                + '}';
    }
}
