package edu.chans.endes;

public class Ticket {

    // Atributos privados e inmutables (id y descripcion no cambian)
    private final int id;
    private final String descripcion;
    private boolean cerrado;

    // Constructor principal para crear nuevos tickets (nacen abiertos)
    public Ticket(int id, String descripcion) {

        if (id <= 0) {
            throw new IllegalArgumentException("Id invalido");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("Descripcion invalida");
        }

        this.id = id;
        this.descripcion = descripcion;
        this.cerrado = false;
    }

    // Constructor secundario para cargar tickets desde el archivo
    public Ticket(int id, String descripcion, boolean cerrado) {
        this(id, descripcion); // Reutiliza validaciones del constructor principal
        this.cerrado = cerrado;
    }

    // Devuelve el ID único para el Ticket
    public int getId() {
        return id;
    }

    // Devuelve la descripción
    public String getDescripcion() {
        return descripcion;
    }

    // Cambia el estado del Ticket a cerrado
    public void cerrar() {
        this.cerrado = true;
    }

    // Indica si esta cerrado el Ticket o abierto
    public boolean estaCerrado() {
        return this.cerrado;
    }

    // Construye un resumen
    public String resumen() {
        String estadoTexto = this.cerrado ? "Cerrado" : "Abierto";
        return "Ticket " + this.id + " | Descripción: " + this.descripcion + " | Estado: " + estadoTexto;
    }
}