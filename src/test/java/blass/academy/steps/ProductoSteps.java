package blass.academy.steps;

import blass.academy.models.Animal;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoSteps {
    private final World world;

    public ProductoSteps(World world) {
        this.world = world;
    }

    @And("Se verifica que el producto tenga:")
    public void verificarProducto(Map<String, String> data) {
  // complete assertion with models for request
    }
}
