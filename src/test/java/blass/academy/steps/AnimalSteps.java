package blass.academy.steps;

import blass.academy.models.Animal;
import io.cucumber.java.en.And;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
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

    @And("Se verifica que el animal tenga:")
    public void veriricarAnimal(Map<String, String> data) {
     final var animal = world.obtenerResponseBody(Animal.class);

     assertAll(
             () -> assertEquals(Integer.parseInt(data.get("id")), animal.id(), "id Incorrecto"),
             () -> assertEquals(data.get("nombre"), animal.nombre(), "nombre Incorrecto"),
             () -> assertEquals(Double.parseDouble(data.get("peso")), animal.peso(), "Peso Incorrecto"),
             () -> assertEquals(data.get("amo.nombre"), animal.amo().nombre(), "animal.nombre Incorrecto"),
             () -> assertEquals(Integer.parseInt(data.get("amo.edad")), animal.amo().edad(), "edad Incorrecta")
     );
    }
}
