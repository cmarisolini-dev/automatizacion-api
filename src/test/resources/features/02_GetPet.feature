@PetStore @Get_Pet
Feature: Consultar mascotas por identificador
  Background:
    Given el actor establece el endpoint de PetStore

  @CP03 @HappyPath
  Scenario Outline: Recuperar una mascota existente
    Given existe una mascota propia "<nombre>" con estado "<estado>"
    When consulta la mascota por identificador
    Then el codigo de respuesta debe ser 200
    And la respuesta contiene todos los datos de la mascota
    Examples:
      | nombre | estado    |
      | Luna   | available |
      | Toby   | sold      |

  @CP04 @Negative
  Scenario: Consultar identificador inexistente
    Given se dispone de un identificador inexistente
    When consulta la mascota por identificador
    Then el codigo de respuesta debe ser 404
