@PetStore @Delete_Pet
Feature: Eliminar mascotas
  Background:
    Given el actor establece el endpoint de PetStore

  @CP11 @HappyPath
  Scenario Outline: Eliminar una mascota propia y comprobar su ausencia
    Given existe una mascota propia "<nombre>" con estado "<estado>"
    When elimina la mascota por identificador
    Then el codigo de respuesta debe ser 200
    And la mascota ya no existe al consultarla
    Examples:
      | nombre | estado    |
      | Rocky  | available |
      | Coco   | pending   |

  @CP12 @Negative
  Scenario: Eliminar una mascota inexistente
    Given se dispone de un identificador inexistente
    When elimina la mascota por identificador
    Then el codigo de respuesta debe ser 404
    And la mascota ya no existe al consultarla
