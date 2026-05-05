Feature: Animales

  Scenario: Crear animal
    Given  Se usa el base path "animales"
    And Se usa el payload "animal/crearAnimal.json"
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea 201

    Scenario: Actualizar animal
      Given Se usa el base path "animales/{id}"
      And Se asigna el path param "id"="5"
      And Se usa el payload "animal/actualizarAnimal.json"
      When Se envia el request con el metodo PUT
      Then Se verifica que el status code sea 200

      Scenario:  Actualizar parcialmente animal
        Given Se usa el base path "animales/{id}"
        And Se asigna el path param "id"="5"
        And Se usa el payload "animal/actualizarParcialmenteAnimal.json"
        When Se envia el request con el metodo PATCH
        Then Se verifica que el status code sea 200
        
        Scenario:  Eliminar un animal
          Given Se usa el base path "animales/{id}"
          And Se asigna el path param "id"="5"
          When Se envia el request con el metodo DELETE
          Then  Se verifica que el status code sea 200
          And Se verifica que "mensaje" == "Animal con id 5 se ha eliminado satisfactoriamente"

          Scenario: Obtener todos los animales
            Given Se usa el base path "animales"
            When Se envia el request con el metodo GET
            Then Se verifica que el status code sea 200
            And Se verifica que existan 30 animales

            @smoke
            Scenario: Obtener Animal
              Given Se usa el base path "animales/{id}"
              And Se asigna el path param "id"="5"
              And Se envia el request con el metodo GET
              And Se verifica que el status code sea 200
              And Se verifica que el animal tenga:
              |id|5|
              |nombre|Fido|
              |peso|42.3|
              |amo.nombre|Dora|
              |amo.edad|52|

        
        