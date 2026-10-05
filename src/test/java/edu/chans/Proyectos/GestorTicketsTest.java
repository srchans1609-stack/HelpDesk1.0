package edu.chans.Proyectos;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorTicketTest {
    @Test
    void coleccionInicialmenteVacia() {
        //El  Array este vacio
        GestorTickets gestor = new GestorTickets();
        assertTrue(gestor.obtenerTickets().isEmpty());
    }


@Test
void identificadoresConsecutivos() {
        //Identificar
    GestorTickets gestor = new GestorTickets();
    Ticket t1 = gestor.crearTicket("Primera incidencia");
    Ticket t2 = gestor.crearTicket("Segunda incidencia");

    assertEquals(1, t1.getId());
    assertEquals(2, t2.getId());
}

    @Test
    void busquedaDelMismoObjeto() {
        //Buscador por ids
        GestorTickets gestor = new GestorTickets();
        Ticket creado = gestor.crearTicket("Incidencia de red");
        Ticket encontrado = gestor.buscarTicket(creado.getId());

        assertNotNull(encontrado);
        assertSame(creado, encontrado); // Verifica que es exactamente la misma instancia
    }

    @Test
    void busquedaInexistente_DevuelveNull() {
        //Error por null
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Incidencia de red");

        assertNull(gestor.buscarTicket(99)); // ID que no existe
    }
    @Test
    void creacionInvalida_NoAlteraColeccionNiContador() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Ticket valido"); // ID 1

        // Intentar crear ticket con descripción vacía
        assertThrows(IllegalArgumentException.class, () -> gestor.crearTicket("   "));

        // Comprobamos que la colección sigue teniendo solo 1 ticket
        assertEquals(1, gestor.obtenerTickets().size());

        // Comprobamos que el siguiente ticket válido recibe el ID 2 (el contador no se consumió)
        Ticket nuevoValido = gestor.crearTicket("Otro ticket valido");
        assertEquals(2, nuevoValido.getId());
    }
    @Test
    void proteccionDeLaListaInterna() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Ticket 1");

        List<Ticket> copia = gestor.obtenerTickets();
        copia.clear(); // Intentamos vaciar la copia obtenida

        // La lista interna del gestor debe seguir teniendo su ticket intacto
        assertEquals(1, gestor.obtenerTickets().size());
    }



    @Test
    void cargarTicketsValidos_AjustaSiguienteId() {
        List<Ticket> ticketsGuardados = new ArrayList<>();
        ticketsGuardados.add(new Ticket(1, "Primera incidencia"));
        ticketsGuardados.add(new Ticket(5, "Segunda incidencia"));

        GestorTickets gestor = new GestorTickets(ticketsGuardados);

        // El máximo ID era 5, por lo que el siguiente ticket creado debe recibir ID = 6
        Ticket nuevo = gestor.crearTicket("Tercera incidencia");
        assertEquals(6, nuevo.getId());
    }

    @Test
    void cargarColeccionVacia_SiguienteIdEsUno() {
        //cargar coleccion
        GestorTickets gestor = new GestorTickets(new ArrayList<>());

        Ticket nuevo = gestor.crearTicket("Primer ticket");
        assertEquals(1, nuevo.getId());
    }

    @Test
    void cargarTicketsConIdsDuplicados() {
        List<Ticket> ticketsDuplicados = new ArrayList<>();
        ticketsDuplicados.add(new Ticket(1, "Incidencia A"));
        ticketsDuplicados.add(new Ticket(1, "Incidencia B")); // ID repetido

        assertThrows(IllegalArgumentException.class, () -> new GestorTickets(ticketsDuplicados));
    }



    @Test
    void estadisticas_GestorVacio() {
        GestorTickets gestor = new GestorTickets();

        assertEquals(0, gestor.getTotalTickets());
        assertEquals(0, gestor.getTicketsAbiertos());
        assertEquals(0, gestor.getTicketsCerrados());
    }

    @Test
    void estadisticas_DosIncidenciasAbiertas() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Incidencia 1");
        gestor.crearTicket("Incidencia 2");

        assertEquals(2, gestor.getTotalTickets());
        assertEquals(2, gestor.getTicketsAbiertos());
        assertEquals(0, gestor.getTicketsCerrados());
    }

    @Test
    void estadisticas_DosIncidenciasConUnaCerrada() {
        GestorTickets gestor = new GestorTickets();
        Ticket t1 = gestor.crearTicket("Incidencia 1");
        gestor.crearTicket("Incidencia 2");

        gestor.cerrarTicket(t1.getId()); // Cerramos una de ellas

        assertEquals(2, gestor.getTotalTickets());
        assertEquals(1, gestor.getTicketsAbiertos());
        assertEquals(1, gestor.getTicketsCerrados());
    }
}
