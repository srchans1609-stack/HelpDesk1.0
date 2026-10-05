package edu.chans.endes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void crearTicketConDatosValidos_EstaAbierto() {
        Ticket ticket = new Ticket(1, "Soporte de red");
        assertFalse(ticket.estaCerrado());
    }

    @Test
    void cerrarTicket_EstaCerrado() {
        Ticket ticket = new Ticket(1, "Soporte de red");
        ticket.cerrar();
        assertTrue(ticket.estaCerrado());
    }

    @Test
    void cerrarDosVecesMismoTicket_SigueCerrado() {
        Ticket ticket = new Ticket(1, "Soporte de red");
        ticket.cerrar();

        assertDoesNotThrow(() -> ticket.cerrar());
        assertTrue(ticket.estaCerrado());
    }

    @Test
    void crearTicketConDescripcionEspacioEnBlanco() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, " "));
    }

    @Test
    void crearTicketConDescripcionNull() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, null));
    }

    @Test
    void crearTicketConIDCero() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(0, "Soporte de red"));
    }

    @Test
    void crearTicketConIDNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(-5, "Soporte de red"));
    }

    @Test
    void crearTicketDirectamenteCerrado() {
        Ticket ticket = new Ticket(1, "Soporte de red", true);
        assertTrue(ticket.estaCerrado());
    }
}

