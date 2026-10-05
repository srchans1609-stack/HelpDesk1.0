package edu.chans.Proyectos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorTickets {
    // Lista en memoria donde se guardan los tickets
    private final List<Ticket> tickets;
    // Contador para asignar el siguiente ID
    private int siguienteId;

    // Constructor 1: Inicializa una lista vacía y empieza a contar IDs desde 1
    public GestorTickets() {
        this.tickets = new ArrayList<>();
        this.siguienteId = 1;
    }

    // Constructor 2: Carga una lista previa (de archivo) verificando duplicados y ajuste de ID
    public GestorTickets(List<Ticket> ticketsCargados) {
        if (ticketsCargados == null) {
            throw new IllegalArgumentException("La lista de tickets es nula");
        }

        Set<Integer> idsUnicos = new HashSet<>();
        int maxId = 0;

        for (Ticket ticket : ticketsCargados) {
            if (!idsUnicos.add(ticket.getId())) {
                throw new IllegalArgumentException("IDs duplicados: " + ticket.getId());
            }
            if (ticket.getId() > maxId) {
                maxId = ticket.getId();
            }
        }

        this.tickets = new ArrayList<>(ticketsCargados);
        // El siguiente ID será el máximo encontrado + 1
        this.siguienteId = maxId + 1;
    }

    // Crea un nuevo ticket usando el contador
    public Ticket crearTicket(String descripcion) {
        Ticket nuevo = new Ticket(siguienteId, descripcion);
        tickets.add(nuevo);
        siguienteId++;
        return nuevo;
    }

    // Busca un ticket por su identificador
    public Ticket buscarTicket(int idBuscado) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == idBuscado) {
                return ticket;
            }
        }
        return null;
    }

    // Devuelve una COPIA de la lista de tickets para proteger la lista interna
    public List<Ticket> obtenerTickets() {
        return new ArrayList<>(tickets);
    }



    // Cierra una incidencia por ID
    public boolean cerrarTicket(int id) {
        Ticket ticket = buscarTicket(id);
        if (ticket != null && !ticket.estaCerrado()) {
            ticket.cerrar();
            return true;
        }
        return false;
    }

    // Estadísticas
    public int getTotalTickets() {
        return tickets.size();
    }
 // Incremente el contador
    public int getTicketsAbiertos() {
        int abiertos = 0;
        for (Ticket t : tickets) {
            if (!t.estaCerrado()) {
                abiertos++;
            }
        }
        return abiertos;
    }

    public int getTicketsCerrados() {
        return getTotalTickets() - getTicketsAbiertos();
    }
}
