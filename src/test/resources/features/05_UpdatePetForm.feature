@PetStore @Form_Pet
Feature: Actualizar mascotas mediante formulario
  Background:
    Given el actor establece el endpoint de PetStore

  @CP09 @HappyPath
  Scenario Outline: Modificar mascota por formulario
    Given existe una mascota propia "<antes>" con estado "<inicial>"
    When actualiza por formulario a nombre "<despues>" y estado "<final>"
    Then el codigo de respuesta debe ser 200
    And la mascota conserva los datos al volver a consultarla
    Examples:
      | antes | inicial   | despues | final   |
      | Rex   | available | Rex QA  | sold    |
      | Kira  | sold      | Kira QA | pending |

  @CP10 @Negative
  Scenario: Actualizar por formulario una mascota inexistente
    Given se dispone de un identificador inexistente
    When actualiza por formulario a nombre "QA ausente" y estado "pending"
    Then el codigo de respuesta debe ser 404
    And la mascota ya no existe al consultarla
