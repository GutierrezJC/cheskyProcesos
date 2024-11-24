/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AlgortitmosSegmentacion;

import Objetos.Proceso;
import java.util.ArrayList;
import segmentacion.Particion;

public class PrimerAjuste {

    public boolean asignar(Proceso proceso, ArrayList<Particion> particiones) {
        int tamañoRestante = proceso.getTamañoEnBytes();

        // Validar que el tamaño del proceso sea válido
        if (tamañoRestante <= 0) {
            System.out.println("Error: Tamaño del proceso inválido.");
            return false;
        }

        for (Particion particion : particiones) {
            // Buscar la primera partición libre con suficiente espacio
            if (particion.getEstadoP() == 'L' && particion.getnUnidades() >= tamañoRestante) {
                particion.setNombreProceso(proceso.getNombre());
                particion.setEstadoP('O');
                particion.setUTiempo(proceso.getTiempoRafaga());

                // Manejar fragmentación interna
                int fragmentacion = particion.getnUnidades() - tamañoRestante;
                if (fragmentacion > 0) {
                    particion.setnUnidades(tamañoRestante);
                    // Crear una nueva partición con el espacio sobrante
                    Particion nuevaParticion = new Particion(
                        particion.getFinalP() - fragmentacion, 
                        particion.getFinalP(), 
                        fragmentacion
                    );
                    particiones.add(particiones.indexOf(particion) + 1, nuevaParticion);
                }

                System.out.println("Proceso asignado a la partición: " + particion);
                return true; // Proceso asignado correctamente
            }
        }

        System.out.println("Error: No se encontró una partición adecuada para el proceso " + proceso.getNombre());
        return false; // No se encontró una partición adecuada
    }

    public void desasignar(Proceso proceso, ArrayList<Particion> particiones) {
        for (Particion particion : particiones) {
            if (proceso.getNombre().equals(particion.getNombreProceso())) {
                particion.setEstadoP('L');
                particion.setNombreProceso("SN");
                System.out.println("Proceso desasignado de la partición: " + particion);
            }
        }
    }
}

