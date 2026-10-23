package blass.academy.models;

import java.util.List;

public record Producto(
        int id,
        String nombre,
        double precio,
        int cantidad,
        double peso,
        boolean perecible,
        double volumen,
        List<Critica> criticas,
        List<String> etiquetas
) {
}
