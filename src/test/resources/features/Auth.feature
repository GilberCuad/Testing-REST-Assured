Feature: login

    Scenario: logout
      Given Se usa el base path "auth/logout"
      And Se usa el payload "auth/logout.json"
      When Se envia el request con el metodo POST
      Then Se verifica que el status code sea 200
      
      Scenario:  login
        Given Se usa el base path "auth/login"
        And Se usa el payload "auth/login.json"
        When Se envia el request con el metodo POST
        Then Se verifica que el status code sea 200

        Scenario: Cambiar clave
          Given Se usa el base path "auth/cambiar-clave"
          And Se usa el payload "auth/cambiarClave.json"
          When Se envia el request con el metodo POST
          Then Se verifica que el status code sea 200

          Scenario:  Registro
            Given Se usa el base path "auth/registro"
            And Se usa el payload "auth/registro.json"
            When Se envia el request con el metodo POST
            Then Se verifica que el status code sea 200