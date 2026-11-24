@KnownDefect @PetStore
Feature: Diagnostico de actualizacion con identificador no numerico
  Background:
    Given el actor establece el endpoint de PetStore

  @DEF_CP06 @Negative
  Scenario: Rechazar actualizacion con identificador no numerico
    When envia una actualizacion con identificador de tipo invalido
    Then el servidor rechaza el cuerpo invalido
