@PetStore @Find_Pet
Feature: Filtrar mascotas por estado
  Background:
    Given el actor establece el endpoint de PetStore

  @CP07 @HappyPath
  Scenario Outline: Recuperar mascotas de un estado valido
    Given existe una mascota propia "QA filtro" con estado "<estado>"
    When consulta mascotas con estado "<estado>"
    Then el codigo de respuesta debe ser 200
    And la respuesta contiene solo mascotas del estado solicitado e incluye la propia
    Examples:
      | estado    |
      | available |
      | pending   |
      | sold      |

  @CP08 @Negative
  Scenario: Consultar con un estado fuera del contrato
    When consulta mascotas con estado "qa_estado_invalido"
    Then el estado invalido se rechaza o devuelve una lista vacia
