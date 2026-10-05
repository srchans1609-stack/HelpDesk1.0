package edu.chans.Proyectos;

import edu.chans.Proyectos.Ticket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArchivoTicket {

    // Ruta del archivo en el sistema de archivos (ej. Path.of("tickets.txt"))
    private final Path ruta;

    // Recibe el objeto Path de la ruta donde se guardarán los datos
    public ArchivoTicket(Path ruta) {
        this.ruta = ruta;
    }



    // Convierte la lista de tickets a formato de texto y sobrescribe el fichero
    public void guardar(List<Ticket> tickets) throws IOException {
        List<String> lineas = new ArrayList<>();

        // Formatea cada objeto Ticket a una cadena separada por punto y coma
        for (Ticket ticket : tickets) {
            String linea = ticket.getId() + ";"
                    + ticket.estaCerrado() + ";"
                    + ticket.getDescripcion();

            lineas.add(linea);
        }

        // Escribe todas las líneas codificadas
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }

    // Lee el archivo de texto y convierte cada línea en un objeto Ticket
    public List<Ticket> cargar() throws IOException {
        List<String> lineas;

        try {
            // Lee todas las líneas
            lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
        } catch (NoSuchFileException e) {
            // Si el archivo no existe aún, retorna lista vacía sin dar error
            return new ArrayList<>();
        }

        List<Ticket> recuperados = new ArrayList<>();

        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            int numeroLinea = i + 1;

            // Divide la línea por ';' en un máximo de 3 partes
            String[] partes = linea.split(";", 3);

            // Verifica que la línea tenga exactamente los 3 campos esperados
            if (partes.length != 3) {
                throw new IOException("Línea " + numeroLinea + ": se esperaban tres campos.");
            }

            try {
                // Convierte el primer campo a número entero
                int id = Integer.parseInt(partes[0]);

                // Valida que el segundo campo sea estrictamente "true" o "false"
                if (!partes[1].equals("true") && !partes[1].equals("false")) {
                    throw new IllegalArgumentException("El estado debe ser true o false.");
                }

                boolean cerrado = Boolean.parseBoolean(partes[1]);
                String descripcion = partes[2];

                // Instancia el ticket usando el constructor con estado previo
                Ticket ticket = new Ticket(id, descripcion, cerrado);

                recuperados.add(ticket);

            } catch (IllegalArgumentException e) {
                // Captura fallos de conversión de ID o datos inválidos e indica la línea con error
                throw new IOException("Línea " + numeroLinea + ": " + e.getMessage(), e);
            }
        }

        // Devuelve la lista reconstruida solo si todas las líneas fueron válidas
        return recuperados;
    }
}