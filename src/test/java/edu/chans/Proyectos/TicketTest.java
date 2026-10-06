package edu.chans.Proyectos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    // Comprueba que al crear un ticket normal, su estado inicial sea siempre abierto (cerrado = false)
    @Test
    void crearTicketConDatosValidos_EstaAbierto() {
        Ticket ticket = new Ticket(1, "Soporte de red");
        assertFalse(ticket.estaCerrado());
    }

    // Comprueba que al llamar al método cerrar(), el estado cambia a cerrado (cerrado = true)
    @Test
    void cerrarTicket_EstaCerrado() {
        Ticket ticket = new Ticket(1, "Soporte de red");
        ticket.cerrar();
        assertTrue(ticket.estaCerrado());
    }

    // Comprueba que cerrar un ticket que ya estaba cerrado no genera errores ni altera su estado
    @Test
    void cerrarDosVecesMismoTicket_SigueCerrado() {
        Ticket ticket = new Ticket(1, "Soporte de red");
        ticket.cerrar();

        assertDoesNotThrow(() -> ticket.cerrar());
        assertTrue(ticket.estaCerrado());
    }

    // Comprueba que salte la excepción si la descripción es solo un espacio en blanco
    @Test
    void crearTicketConDescripcionEspacioEnBlanco() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, " "));
    }

    // Comprueba que salte la excepción si la descripción dada es null
    @Test
    void crearTicketConDescripcionNull() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, null));
    }

    // Comprueba que no permita crear tickets con ID igual a 0
    @Test
    void crearTicketConIDCero() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(0, "Soporte de red"));
    }

    // Comprueba que no permita crear tickets con ID negativo
    @Test
    void crearTicketConIDNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(-5, "Soporte de red"));
    }

    // Comprueba el constructor secundario usado al cargar desde archivo con el estado cerrado
    @Test
    void crearTicketDirectamenteCerrado() {
        Ticket ticket = new Ticket(1, "Soporte de red", true);
        assertTrue(ticket.estaCerrado());
    }

    // Comprueba los métodos de lectura (getters) y la representación en texto (toString)
    @Test
    void testGettersYToString() {
        Ticket t = new Ticket(1, "Test", false);

        // Verifica lectura de ID y Descripción
        assertEquals(1, t.getId());
        assertEquals("Test", t.getDescripcion());

        // Verifica que la conversión a cadena no devuelva null ni texto vacío
        assertNotNull(t.toString());
        assertFalse(t.toString().trim().isEmpty());
    }
}

