
import AlgortitmosSegmentacion.PrimerAjuste;
import AlgortitmosSegmentacion.MejorAjuste;
import AlgortitmosSegmentacion.PeorAjuste;
import Objetos.Proceso;
import segmentacion.Particion;

import java.util.ArrayList;

public class PruebaSegmentacionSimple {

    public static void main(String[] args) {
        // Crear particiones (asumiendo que el constructor toma tamaño y estado)
        ArrayList<Particion> particiones = new ArrayList<>();
        particiones.add(new Particion(1, 200, 'L')); // Libre
        particiones.add(new Particion(2, 300, 'L')); // Libre
        particiones.add(new Particion(3, 400, 'L')); // Libre

        // Crear un proceso
        Proceso proceso = new Proceso(1, "Proceso1", 150, 100, 0, 10, 1, 5);

        // Probar Primer Ajuste
        System.out.println("=== PRIMER AJUSTE ===");
        PrimerAjuste primerAjuste = new PrimerAjuste();
        probarAlgoritmo(primerAjuste, proceso, particiones);

        // Probar Mejor Ajuste
        System.out.println("\n=== MEJOR AJUSTE ===");
        MejorAjuste mejorAjuste = new MejorAjuste();
        probarAlgoritmo(mejorAjuste, proceso, particiones);

        // Probar Peor Ajuste
        System.out.println("\n=== PEOR AJUSTE ===");
        PeorAjuste peorAjuste = new PeorAjuste();
        probarAlgoritmo(peorAjuste, proceso, particiones);
    }

    private static void probarAlgoritmo(Object algoritmo, Proceso proceso, ArrayList<Particion> particiones) {
        boolean asignado = false;

        if (algoritmo instanceof PrimerAjuste) {
            asignado = ((PrimerAjuste) algoritmo).asignar(proceso, particiones);
        } else if (algoritmo instanceof MejorAjuste) {
            asignado = ((MejorAjuste) algoritmo).asignar(proceso, particiones);
        } else if (algoritmo instanceof PeorAjuste) {
            asignado = ((PeorAjuste) algoritmo).asignar(proceso, particiones);
        }

        if (asignado) {
            System.out.println("Proceso asignado con éxito.");
            imprimirParticiones(particiones);
            if (algoritmo instanceof PrimerAjuste) {
                ((PrimerAjuste) algoritmo).desasignar(proceso, particiones);
            } else if (algoritmo instanceof MejorAjuste) {
                ((MejorAjuste) algoritmo).desasignar(proceso, particiones);
            } else if (algoritmo instanceof PeorAjuste) {
                ((PeorAjuste) algoritmo).desasignar(proceso, particiones);
            }
            System.out.println("Proceso liberado.");
        } else {
            System.out.println("No se pudo asignar el proceso.");
        }
    }

    private static void imprimirParticiones(ArrayList<Particion> particiones) {
        for (Particion particion : particiones) {
            System.out.println(particion);
        }
    }
}
