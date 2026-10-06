package edu.chans.Proyectos;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorTicketTest {

    // Comprueba que al instanciar un gestor nuevo, la lista de tickets comience vacía
    @Test
    void coleccionInicialmenteVacia() {
        GestorTickets gestor = new GestorTickets();
        assertTrue(gestor.obtenerTickets().isEmpty());
    }

    // Comprueba que los IDs autoincrementales se asignen correlativamente (1, 2...)
    @Test
    void identificadoresConsecutivos() {
        GestorTickets gestor = new GestorTickets();
        Ticket t1 = gestor.crearTicket("Primera incidencia");
        Ticket t2 = gestor.crearTicket("Segunda incidencia");

        assertEquals(1, t1.getId());
        assertEquals(2, t2.getId());
    }

    // Comprueba que la búsqueda por ID devuelva exactamente la misma referencia en memoria
    @Test
    void busquedaDelMismoObjeto() {
        GestorTickets gestor = new GestorTickets();
        Ticket creado = gestor.crearTicket("Incidencia de red");
        Ticket encontrado = gestor.buscarTicket(creado.getId());

        assertNotNull(encontrado);
        assertSame(creado, encontrado);
    }

    // Comprueba que buscar un ID que no existe devuelva null en lugar de lanzar excepción
    @Test
    void busquedaInexistente_DevuelveNull() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Incidencia de red");

        assertNull(gestor.buscarTicket(99));
    }

    // Comprueba que un intento fallido de creación no altere la lista ni consuma un ID
    @Test
    void creacionInvalida_NoAlteraColeccionNiContador() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Ticket valido");

        assertThrows(IllegalArgumentException.class, () -> gestor.crearTicket("   "));

        assertEquals(1, gestor.obtenerTickets().size());

        Ticket nuevoValido = gestor.crearTicket("Otro ticket valido");
        assertEquals(2, nuevoValido.getId());
    }

    // Comprueba la copia defensiva: vaciar la lista devuelta por obtenerTickets() no afecta a la original
    @Test
    void proteccionDeLaListaInterna() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Ticket 1");

        List<Ticket> copia = gestor.obtenerTickets();
        copia.clear();

        assertEquals(1, gestor.obtenerTickets().size());
    }

    // Comprueba que al cargar desde lista se calcule siguienteId = maxId + 1
    @Test
    void cargarTicketsValidos_AjustaSiguienteId() {
        List<Ticket> ticketsGuardados = new ArrayList<>();
        ticketsGuardados.add(new Ticket(1, "Primera incidencia"));
        ticketsGuardados.add(new Ticket(5, "Segunda incidencia"));

        GestorTickets gestor = new GestorTickets(ticketsGuardados);

        Ticket nuevo = gestor.crearTicket("Tercera incidencia");
        assertEquals(6, nuevo.getId());
    }

    // Comprueba que cargar una lista vacía inicie el contador de IDs en 1
    @Test
    void cargarColeccionVacia_SiguienteIdEsUno() {
        GestorTickets gestor = new GestorTickets(new ArrayList<>());

        Ticket nuevo = gestor.crearTicket("Primer ticket");
        assertEquals(1, nuevo.getId());
    }

    // Comprueba que si la lista a cargar contiene IDs repetidos IllegalArgumentException
    @Test
    void cargarTicketsConIdsDuplicados() {
        List<Ticket> ticketsDuplicados = new ArrayList<>();
        ticketsDuplicados.add(new Ticket(1, "Incidencia A"));
        ticketsDuplicados.add(new Ticket(1, "Incidencia B"));

        assertThrows(IllegalArgumentException.class, () -> new GestorTickets(ticketsDuplicados));
    }

    // Comprueba que pasar null al constructor del gestor lance una excepción
    @Test
    void cargarListaNull_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new GestorTickets(null));
    }

    // Comprueba el cálculo de estadísticas con el gestor recién creado
    @Test
    void estadisticas_GestorVacio() {
        GestorTickets gestor = new GestorTickets();

        assertEquals(0, gestor.getTotalTickets());
        assertEquals(0, gestor.getTicketsAbiertos());
        assertEquals(0, gestor.getTicketsCerrados());
    }

    // Comprueba las estadísticas con varias incidencias abiertas
    @Test
    void estadisticas_DosIncidenciasAbiertas() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Incidencia 1");
        gestor.crearTicket("Incidencia 2");

        assertEquals(2, gestor.getTotalTickets());
        assertEquals(2, gestor.getTicketsAbiertos());
        assertEquals(0, gestor.getTicketsCerrados());
    }

    // Comprueba las estadísticas tras cerrar un ticket existente
    @Test
    void estadisticas_DosIncidenciasConUnaCerrada() {
        GestorTickets gestor = new GestorTickets();
        Ticket t1 = gestor.crearTicket("Incidencia 1");
        gestor.crearTicket("Incidencia 2");

        boolean cerradoExitoso = gestor.cerrarTicket(t1.getId());

        assertTrue(cerradoExitoso);
        assertEquals(2, gestor.getTotalTickets());
        assertEquals(1, gestor.getTicketsAbiertos());
        assertEquals(1, gestor.getTicketsCerrados());
    }

    // Comprueba el comportamiento y rama condicional al intentar cerrar un ticket que no existe
    @Test
    void cerrarTicketInexistente_DevuelveFalse() {
        GestorTickets gestor = new GestorTickets();
        gestor.crearTicket("Incidencia 1");

        boolean resultado = gestor.cerrarTicket(999);
        assertFalse(resultado);
    }

    // Comprueba el comportamiento y rama condicional al intentar cerrar un ticket que ya estaba cerrado
    @Test
    void cerrarTicketYaCerrado_DevuelveFalse() {
        GestorTickets gestor = new GestorTickets();
        Ticket t1 = gestor.crearTicket("Incidencia 1");

        gestor.cerrarTicket(t1.getId()); // Primer cierre (devuelve true)
        boolean segundoCierre = gestor.cerrarTicket(t1.getId()); // Segundo cierre (devuelve false)

        assertFalse(segundoCierre);
    }
}
