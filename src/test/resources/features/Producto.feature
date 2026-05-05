Feature: Animales

  Scenario: Crear producto
    Given  Se usa el base path "productos"
    And Se usa el payload "producto/crearProducto.json"
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea 201

  Scenario: Actualizar producto
    Given Se usa el base path "productos/{id}"
    And Se asigna el path param "id"="2"
    And Se usa el payload "producto/actualizarProducto.json"
    When Se envia el request con el metodo PUT
    Then Se verifica que el status code sea 200

  Scenario:  Actualizar parcialmente producto
    Given Se usa el base path "productos/{id}"
    And Se asigna el path param "id"="2"
    And Se usa el payload "producto/actualizarParcialmenteProducto.json"
    When Se envia el request con el metodo PATCH
    Then Se verifica que el status code sea 200

    Scenario: Obtener producto
      Given Se usa el base path "productos/{id}"
      And  Se asigna el path param "id"="2"
      And Se envia el request con el metodo GET
      And Se verifica que el status code sea 200
      And Se verifica que el producto tenga:
        |id|2|
        |nombre|Papas fritas|
        |precio|15.699|
        |criticas[0].usuario|Fredy_Walsh95|
        |criticas[2].puntaje|2.28|