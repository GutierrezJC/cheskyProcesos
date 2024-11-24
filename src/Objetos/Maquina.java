package Objetos;

import java.util.ArrayList;

/**
 *
 * @author luisi
 */
public class Maquina {

    private ArrayList<Recurso> listaRecursoMaquina;
    private ArrayList<Recurso> listaRecursoMaquinaDisponibles;
    private int unidadesMemoriaMaquina;
    private int unidadesMemoriaDisponible;
    private int unidadesMemoriaUtilizada;

    private int unidadesCPUMaquina;
    private int unidadesCPUDisponible;
    private int unidadesCPUUtilizadas;
    private String nombre;
    private String ubicacion;
    private int tiempoInicio;   //Tiempo en que la maquina inicia.

    //Constructores
    public Maquina() {
        this.unidadesMemoriaMaquina = 0;
        this.unidadesMemoriaUtilizada = 0;
        this.unidadesCPUMaquina = 0;
        this.unidadesCPUUtilizadas = 0;
        this.nombre = "Maquina";
        this.ubicacion = "PCI";
        this.tiempoInicio = 0;
        this.unidadesMemoriaDisponible = 0;
        this.unidadesCPUDisponible = 0;
        inicializarMaquinasRecursos();
    }

    public Maquina(int unidadesMemoriaMaquina, String nombre,
            String ubicacion, int tiempoInicio, ArrayList<Recurso> listaRecursos)//crear unidadesCPUMaquina
    {
        this.unidadesMemoriaMaquina = unidadesMemoriaMaquina;
        this.unidadesMemoriaUtilizada = 0;
        this.unidadesCPUMaquina = unidadesCPUMaquina;
        this.unidadesCPUUtilizadas = 0;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoInicio = tiempoInicio;
        this.unidadesMemoriaDisponible = unidadesMemoriaMaquina;
        this.unidadesCPUDisponible = unidadesCPUMaquina;
        this.listaRecursoMaquina = listaRecursos;
        this.listaRecursoMaquinaDisponibles = listaRecursos;
    }

    public Maquina(ArrayList<Recurso> listaNueva, int unidadesMemoriaMaquina,
            String nombre, String ubicacion, int tiempoInicio, ArrayList<Recurso> listaRecursos) {
        this.listaRecursoMaquina = listaNueva;
        this.unidadesMemoriaMaquina = unidadesMemoriaMaquina;
        this.unidadesMemoriaUtilizada = 0;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoInicio = tiempoInicio;
        this.unidadesMemoriaDisponible = unidadesMemoriaMaquina;
        this.listaRecursoMaquina = listaRecursos;
        this.listaRecursoMaquinaDisponibles = listaRecursos;

    }

    //Metodos set y get
    public void setUnidadesMemoriaMaquina(int unidades) {
        this.unidadesMemoriaMaquina = unidades;
    }

    public void setUnidadesCPUMaquina(int unidades) {
        this.unidadesCPUMaquina = unidades;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setTiempoInicio(int tiempo) {
        this.tiempoInicio = tiempo;
    }

    public int getUnidadesMemoriaMaquina() {
        return this.unidadesMemoriaMaquina;
    }

    public ArrayList<Recurso> getListaRecursoMaquina() {
        return listaRecursoMaquina;
    }

    public void setListaRecursoMaquina(ArrayList<Recurso> listaRecursoMaquina) {
        this.listaRecursoMaquina = listaRecursoMaquina;
    }

    public ArrayList<Recurso> getListaRecursoMaquinaDisponibles() {
        return listaRecursoMaquinaDisponibles;
    }

    public void setListaRecursoMaquinaDisponibles(ArrayList<Recurso> listaRecursoMaquinaDisponibles) {
        this.listaRecursoMaquinaDisponibles = listaRecursoMaquinaDisponibles;
    }

    public int getUnidadesCPUDisponible() {
        return unidadesCPUDisponible;
    }

    public void setUnidadesCPUDisponible(int unidadesCPUDisponible) {
        this.unidadesCPUDisponible = unidadesCPUDisponible;
    }

    public int getUnidadesCPUUtilizadas() {
        return unidadesCPUUtilizadas;
    }

    public void setUnidadesCPUUtilizadas(int unidadesCPUUtilizadas) {
        this.unidadesCPUUtilizadas = unidadesCPUUtilizadas;
    }

    public int getUnidadesMemoriaUtilizada() {
        return this.unidadesMemoriaUtilizada;
    }

    public int getUnidadesMemoriaDisponible() {
        return this.unidadesMemoriaDisponible;
    }

    public int getUnidadesCPUMaquina() {
        return this.unidadesCPUMaquina;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public int getTiempoInicio() {
        return this.tiempoInicio;
    }

    public void liberarMemoria(int cantidadLiberar) {
        if (cantidadLiberar > unidadesMemoriaUtilizada) {
            return;
        }
        this.unidadesMemoriaDisponible += cantidadLiberar;
        this.unidadesMemoriaUtilizada -= cantidadLiberar;
    }

    public void asignarMemoria(int cantidadAsignar) {
        if (cantidadAsignar > unidadesMemoriaDisponible) {
            return;
        }
        this.unidadesMemoriaDisponible -= cantidadAsignar;
        this.unidadesMemoriaUtilizada += cantidadAsignar;
    }

    public void inicializarMaquinasRecursos() {
        listaRecursoMaquina = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listaRecursoMaquina.add(new Recurso(i, "Recurso_" + i, 100 - (i * 10)));
        }
        listaRecursoMaquinaDisponibles = listaRecursoMaquina;
    }

    public String listaRecursosToString() {
        String mensaje = "";
        for (int i = 0; i < listaRecursoMaquina.size(); i++) {
            mensaje += "Recurso " + (i + 1) + ": " + listaRecursoMaquina.get(i).getUnidades() + "\n";
        }
        return mensaje;
    }

    public String toString() {
        String sal = "Maquina..............." + getNombre();
        sal += "\nUbicacion..........." + getUbicacion();
        sal += "\nTiempo de Inicio...." + getTiempoInicio();
        sal += "\nMemoria Disponible ." + getUnidadesMemoriaMaquina();
        sal += "\nMemoria Usada......." + getUnidadesMemoriaUtilizada();
        sal += "\nLista de Recursos:.." + "\n";
        sal += listaRecursosToString();

        return sal;
    }

    public String listarMaquinaDetalle() {
        String sal = "" + getNombre();
        sal += "\t\t" + getUbicacion();
        sal += "\t" + getTiempoInicio();
        sal += "\t" + unidadesMemoriaMaquina;
        sal += "\t" + unidadesMemoriaDisponible;
        sal += "\t" + unidadesMemoriaUtilizada;
        sal += "\nLista de Recursos:\n";
        sal += listaRecursosToString();

        return sal;
    }

    public boolean recursosSobrepasaMaquina(ArrayList<Recurso> recursos) {
        for (int i = 0; i < recursos.size(); i++) {
            if (recursos.get(i).getUnidades() > listaRecursoMaquina.get(i).getUnidades()) {
                return true;
            }
        }
        return false;
    }

    // ASIGNAR RECURSOS A LA MAQUINA 
    public void asignarRecurso(Recurso nuevo) {
        listaRecursoMaquinaDisponibles.get(nuevo.getNrecurso()).asignarRecurso(nuevo.getUnidades());
    }

    public void liberarRecurso(Recurso nuevo) {
        listaRecursoMaquinaDisponibles.set(nuevo.getNrecurso(), listaRecursoMaquina.get(nuevo.getNrecurso()));
    }

    public void asignarRecurso(ArrayList<Recurso> recursos) {
        for (int i = 0; i < listaRecursoMaquina.size(); i++) {
            asignarRecurso(recursos.get(i));
        }
    }

    public void liberarRecurso(ArrayList<Recurso> recursos) {
        for (int i = 0; i < listaRecursoMaquina.size(); i++) {
            liberarRecurso(recursos.get(i));
        }
    }

    public void asignarCPU(int cantidadAsignar) {
        if (cantidadAsignar > unidadesCPUDisponible) {
            return;
        }
        this.unidadesCPUDisponible -= cantidadAsignar;
        this.unidadesCPUUtilizadas += cantidadAsignar;
    }

    public void liberarCPU(int cantidadLiberar) {
        if (cantidadLiberar > unidadesCPUUtilizadas) {
            return;
        }
        this.unidadesCPUDisponible += cantidadLiberar;
        this.unidadesCPUUtilizadas -= cantidadLiberar;
    }

}
