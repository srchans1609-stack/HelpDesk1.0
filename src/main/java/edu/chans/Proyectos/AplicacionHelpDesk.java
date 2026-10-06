package edu.chans.Proyectos;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class AplicacionHelpDesk {

    public static void main(String[] args) {
        Path rutaArchivo = Paths.get("tickets.txt");
        ArchivoTicket archivo = new ArchivoTicket(rutaArchivo);
        GestorTickets gestor; // Corregido: GestorTickets en plural

        try {
            List<Ticket> recuperados = archivo.cargar();
            gestor = new GestorTickets(recuperados);
            System.out.println("Datos cargados correctamente. Tickets en sistema: " + recuperados.size());
        } catch (Exception e) {
            System.out.println("ERROR CRÍTICO AL CARGAR ARCHIVO: " + e.getMessage());
            System.out.println("Deteniendo el arranque de la aplicación para no sobrescribir datos.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        do {
            mostrarMenu();

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("ERROR: Debe ingresar un número válido.");
                sc.nextLine();
                continue;
            }

            switch (opcion) {
                case 1 -> crearTicket(sc, gestor);
                case 2 -> listarTickets(gestor);
                case 3 -> buscarTicket(sc, gestor);
                case 4 -> cerrarTicket(sc, gestor);
                case 5 -> mostrarEstadisticas(gestor);
                case 6 -> guardarTicket(archivo, gestor);
                case 0 -> System.out.println("Saliendo de la aplicación. ¡Recuerda guardar si hiciste cambios!");
                default -> System.out.println("ERROR: Opción no válida. Intente de nuevo.");
            }

            System.out.println();
        } while (opcion != 0);

        sc.close();
    }

    public static void mostrarMenu() {
        System.out.println("==========================================");
        System.out.println("          HELPDESK DEL CENTRO             ");
        System.out.println("==========================================");
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar incidencias");
        System.out.println("3. Buscar incidencia por identificador");
        System.out.println("4. Cerrar incidencia");
        System.out.println("5. Mostrar estadísticas");
        System.out.println("6. Guardar incidencias");
        System.out.println("0. Salir");
        System.out.println("==========================================");
        System.out.print("Seleccione una opción: ");
    }

    public static void crearTicket(Scanner sc, GestorTickets gestor) {
        System.out.println("\n--- CREAR INCIDENCIA ---");
        System.out.print("Ingrese la descripción del problema: ");
        String descripcion = sc.nextLine().trim();

        try {
            Ticket nuevo = gestor.crearTicket(descripcion);
            System.out.println("¡Incidencia creada con éxito!");
            System.out.println(nuevo.resumen());
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR AL CREAR INCIDENCIA: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR INESPERADO: " + e.getMessage());
        }
    }

    public static void listarTickets(GestorTickets gestor) {
        System.out.println("\n--- LISTADO DE INCIDENCIAS ---");
        List<Ticket> lista = gestor.obtenerTickets();

        if (lista.isEmpty()) {
            System.out.println("AVISO: No hay incidencias registradas en el sistema.");
        } else {
            for (Ticket t : lista) {
                System.out.println(t.resumen());
            }
        }
    }

    public static void buscarTicket(Scanner sc, GestorTickets gestor) {
        System.out.println("\n--- BUSCAR INCIDENCIA ---");
        int id = leerId(sc);

        Ticket encontrado = gestor.buscarTicket(id);
        if (encontrado == null) {
            System.out.println("AVISO: No existe ninguna incidencia con el ID " + id + ".");
        } else {
            System.out.println("Incidencia encontrada:");
            System.out.println(encontrado.resumen());
        }
    }

    public static void cerrarTicket(Scanner sc, GestorTickets gestor) {
        System.out.println("\n--- CERRAR INCIDENCIA ---");
        int id = leerId(sc);

        Ticket encontrado = gestor.buscarTicket(id);
        if (encontrado == null) {
            System.out.println("AVISO: No existe ninguna incidencia con el ID " + id + ".");
        } else if (encontrado.estaCerrado()) {
            System.out.println("AVISO: La incidencia #" + id + " ya se encuentra cerrada.");
        } else {
            encontrado.cerrar();
            System.out.println("La incidencia #" + id + " ha sido cerrada correctamente.");
        }
    }

    public static void mostrarEstadisticas(GestorTickets gestor) {
        System.out.println("\n--- ESTADÍSTICAS DEL SISTEMA ---");
        System.out.println("Total de incidencias: " + gestor.getTotalTickets());
        System.out.println("Incidencias abiertas: " + gestor.getTicketsAbiertos());
        System.out.println("Incidencias cerradas: " + gestor.getTicketsCerrados());
    }

    public static void guardarTicket(ArchivoTicket archivo, GestorTickets gestor) {
        System.out.println("\n--- GUARDANDO CAMBIOS ---");
        try {
            archivo.guardar(gestor.obtenerTickets());
            System.out.println("¡Datos guardados correctamente en tickets.txt!");
        } catch (IOException e) {
            System.out.println("ERROR AL GUARDAR EN ARCHIVO: " + e.getMessage());
        }
    }

    public static int leerId(Scanner sc) {
        while (true) {
            System.out.print("Ingrese el ID de la incidencia: ");
            String entrada = sc.nextLine().trim();

            try {
                int id = Integer.parseInt(entrada);
                if (id <= 0) {
                    System.out.println("ERROR: El ID debe ser un número entero mayor que 0.");
                } else {
                    return id;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ingresar un número entero válido.");
            }
        }
    }
}