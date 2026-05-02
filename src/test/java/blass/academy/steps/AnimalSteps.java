package blass.academy.steps;

import blass.academy.models.Animal;
import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalSteps {
    private final World world;

    public AnimalSteps(World world) {
        this.world = world;
    }


    @And("Se verifica que existan {int} animales")
    public void verificarLongitud(int logintudEsperada) {
       final var animales = world.obtenerResponseBodyLista(Animal.class);
       assertEquals(logintudEsperada, animales.size(), "logitud incorrrecta");
    }
}
