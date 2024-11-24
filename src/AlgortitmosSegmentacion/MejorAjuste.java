/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AlgortitmosSegmentacion;

import Objetos.Proceso;
import java.util.ArrayList;
import segmentacion.Particion;

public class MejorAjuste {

   public boolean asignar(Proceso proceso, ArrayList<Particion> particiones) {
    int tamañoRestante = proceso.getTamañoEnBytes(); // Memoria requerida
    Particion mejorParticion = null;

    for (Particion particion : particiones) {
        if (particion.getEstadoP() == 'L' && particion.getnUnidades() >= tamañoRestante) {
            // Si la partición es mejor (más pequeña que la anterior, pero suficiente)
            if (mejorParticion == null || particion.getnUnidades() < mejorParticion.getnUnidades()) {
                mejorParticion = particion;
            }
        }
    }

    if (mejorParticion != null) {
        // Asignar proceso a la mejor partición encontrada
        mejorParticion.setNombreProceso(proceso.getNombre());
        mejorParticion.setEstadoP('O');
        mejorParticion.setUTiempo(proceso.getTiempoRafaga());
        return true; // Proceso asignado correctamente
    }

    System.out.println("No se encontró una partición adecuada para el proceso " + proceso.getNombre());
    return false; // No se encontró una partición adecuada
}


    public void desasignar(Proceso proceso, ArrayList<Particion> particiones) {
        for (Particion particion : particiones) {
            if (proceso.getNombre().equals(particion.getNombreProceso())) {
                particion.setEstadoP('L');
                particion.setNombreProceso("SN");

                System.out.println("Partición liberada: " + particion.toString());
            }
        }
    }
}
