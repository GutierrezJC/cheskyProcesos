package Objetos;

import javax.swing.JOptionPane;

/**
 *
 * @author araya
 */
public class Recurso {

    private int numeroRecurso;// osea el id el tipo de recurso que es el 
    private String nombreRecurso;
    
    private int unidades;// LA CANTIDAD DE UNIDADES DEL RECURSO TOTALES
    private int uUtilizadas;// LA CANTIDAD DE UNIDADES UTILIZADAS
    private int uDisponibles;// LA CANTIDA DE UNIDADES DISPONIBLES
    private int estado;// 

    //Contructores
    public Recurso() {
    }

    public Recurso(int nRecurso, String nomRecurso, int unidades, int uUtilizadas, int uDisponibles, int estado) {
        this.numeroRecurso = nRecurso;
        this.nombreRecurso = nomRecurso;
        this.unidades = unidades;
        this.uUtilizadas = uUtilizadas;
        this.uDisponibles = uDisponibles;
        this.estado = estado;
    }

    public Recurso(int nRecurso, String nomRecurso, int unidades) {
        this.numeroRecurso = nRecurso;
        this.nombreRecurso = nomRecurso;
        this.unidades = unidades;
        uUtilizadas = 0;
        uDisponibles = unidades;
    }
    public Recurso(int nRecurso, String nomRecurso, int unidades,String s) {
        this.numeroRecurso = nRecurso;
        this.nombreRecurso = nomRecurso;
        this.unidades = unidades;
        this.uDisponibles=unidades;// LE DOY DE UNA VEZ TODAS LAS DISPONIBLES 
    }

    public Recurso(Recurso nuevo) { //CLONAR
        numeroRecurso = nuevo.numeroRecurso;
        nombreRecurso = nuevo.nombreRecurso;
        unidades = nuevo.unidades;
        uUtilizadas = nuevo.uUtilizadas;
        uDisponibles = nuevo.uDisponibles;
    }

    //Metodos set y get
    public int getNrecurso() {
        return numeroRecurso;
    }

    public void setNumeroRecurso_Id(int nRecurso) {
        this.numeroRecurso = nRecurso;
    }

    public String getNomRecurso() {
        return nombreRecurso;
    }

    public void setNomRecurso(String nomRecurso) {
        this.nombreRecurso = nomRecurso;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
        //JOptionPane.showMessageDialog(null, unidades);
    }

    public int getuUtilizadas() {
        return uUtilizadas;
    }

    public void setuUtilizadas(int uUtilizadas) {
        this.uUtilizadas = uUtilizadas;
    }

    public int getUdisponibles() {
        return uDisponibles;
    }

    public void setUdisponibles(int uDisponibles) {
        this.uDisponibles = uDisponibles;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    public void liberarRecurso(int cantidadRecurso) {// LIBERAR RECURSOS OSEA EL RECURSO TIENE UNIDADES DISPONIBLES Y TENGO QUE LIBERARLAS 
        this.uDisponibles += cantidadRecurso;
        this.uUtilizadas -= cantidadRecurso;
    }

    public void asignarRecurso(int cantidadRecurso) { // ESTO ES PARA TOMAR UNIDADES DE ESE RECURSO 
        this.uDisponibles = unidades;
        this.uDisponibles -= cantidadRecurso;
        this.uUtilizadas += cantidadRecurso;
    }

    public void reiniciarRecursos() { // COLOCAR EN CERO TODO 
        this.uDisponibles = unidades;
        this.uUtilizadas = 0;
    }

    //Condiciones y mensajes (Banderas)
    public boolean noHayRecursosSuficientes(int cantidad) {
        return cantidad > uDisponibles;
    }

    public boolean recursoAgotado() {
        return uDisponibles == 0;
    }

    public String mensajeAsignacionRecursos(int cantidad) {
        if (noHayRecursosSuficientes(cantidad)) {
            return "Los recursos disponibles, son insuficientes";
        } else {
            return "Los recursos disponibles, son suficientes";
        }
    }

    //Metodos ToString
    @Override
    /*public String toString() {
        return "Recurso{" + "nRecurso=" + nRecurso + ", nomRecurso=" + nomRecurso + ", unidades=" + unidades + ", uUtilizadas=" + uUtilizadas + ", uDisponibles=" + uDisponibles + ", estado=" + estado + '}';
    }*/

    public String toString() {
        String sal = "";
        sal += "\nNombre Recurso:........." + nombreRecurso;
        sal += "\nRecurso:................" + numeroRecurso;
        sal += "\nUnidades:..............." + unidades;
        sal += "\nUnidades Utilizadas....." + uUtilizadas;
        sal += "\nUnidades Disponibles...." + uDisponibles;
        return sal;
    }

    public String toStringDetalle() {
        String sal = "";
        sal += nombreRecurso + "\t\t  ";
        sal += numeroRecurso + "\t\t  ";
        sal += +unidades + "\t\t  ";
        sal += uUtilizadas + "\t\t  ";
        sal += uDisponibles + "\t\t  ";
        return sal + "\n";
    }

    public String toStringDetalle2() {
        String sal = "";
        sal += nombreRecurso + "\t\t  ";
        //sal += nRecurso + "\t\t  ";
        sal += +unidades + "\t\t  ";
        sal += uUtilizadas + "\t\t  ";
        //sal += uDisponibles + "\t\t  ";
        return sal + "\n";
    }
}