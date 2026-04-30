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