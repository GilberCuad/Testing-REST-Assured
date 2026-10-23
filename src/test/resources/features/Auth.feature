@auth
Feature: Auth

  @smoke @login
  Scenario: Login exitoso
    Given Se usa el base path "auth/login"
    And Se usa el payload "auth/login.json"
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea 200
    And Se verifica longitud del "accessToken" == "304"

  @login @negativo
  Scenario: Login con credenciales invalidas
    Given Se usa el base path "auth/login"
    And Se usa el payload "auth/loginInvalido.json"
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea 401
    And Se verifica que "mensaje" == "Usuario/Clave incorrectas"

  @login @negativo
  Scenario Outline: Login rechazado: <caso>
    Given Se usa el base path "auth/login"
    And Se usa el body:
      | username | <usuario> |
      | password | <clave>   |
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea <status>
    And Se verifica que "mensaje" == "<mensaje>"

    Examples:
      | caso                    | usuario       | clave                | status | mensaje                   |
      | clave incorrecta        | standard_user | clave_mala           | 401    | Usuario/Clave incorrectas |
      | usuario inexistente     | no_existe     | secret_blass_academy | 401    | Usuario/Clave incorrectas |
      | usuario en mayusculas   | STANDARD_USER | secret_blass_academy | 401    | Usuario/Clave incorrectas |
      | usuario y clave vacios  |               |                      | 400    | Falta usuario y/o clave   |

  Scenario: Logout
    Given Se usa el base path "auth/logout"
    And Se usa el token del login
    And Se usa el payload "auth/logout.json"
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea 200
    And Se verifica que "mensaje" == "Se ha cerrado sesión satisfactoriamente"

  Scenario: Cambiar clave
    Given Se usa el base path "auth/cambiar-clave"
    And Se usa el token del login
    And Se usa el payload "auth/cambiarClave.json"
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea 200

  Scenario: Registro
    Given Se usa el base path "auth/registro"
    And Se usa el payload "auth/registro.json"
    When Se envia el request con el metodo POST
    Then Se verifica que el status code sea 200
