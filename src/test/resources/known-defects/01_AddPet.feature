@KnownDefect @PetStore
Feature: Diagnostico de registro con identificador no numerico
  Background:
    Given el actor establece el endpoint de PetStore

  @DEF_CP02 @Negative
  Scenario: Rechazar registro con identificador no numerico
    When envia un registro con identificador de tipo invalido
    Then el servidor rechaza el cuerpo invalido
