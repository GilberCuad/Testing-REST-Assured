package blass.apitesting.steps;

import blass.apitesting.models.Producto;
import io.cucumber.java.en.And;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoSteps {
    private final World world;

    public ProductoSteps(World world) {
        this.world = world;
    }

    @And("Se verifica que el producto tenga:")
    public void verificarProducto(Map<String, String> data) {
        final var producto = world.obtenerResponseBody(Producto.class);

        assertAll(
                () -> assertEquals(Integer.parseInt(data.get("id")), producto.id(), "id Incorrecto"),
                () -> assertEquals(data.get("nombre"), producto.nombre(), "nombre Incorrecto"),
                () -> assertEquals(Double.parseDouble(data.get("precio")), producto.precio(), "precio Incorrecto"),
                () -> assertEquals(data.get("criticas[0].usuario"), producto.criticas().get(0).usuario(), "criticas[0].usuario Incorrecto"),
                () -> assertEquals(Double.parseDouble(data.get("criticas[2].puntaje")), producto.criticas().get(2).puntaje(), "criticas[2].puntaje Incorrecto")
        );
    }
}
