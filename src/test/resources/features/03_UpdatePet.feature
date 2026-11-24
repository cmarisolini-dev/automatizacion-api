@PetStore @Put_Pet
Feature: Actualizar mascotas mediante JSON
  Background:
    Given el actor establece el endpoint de PetStore

  @CP05 @HappyPath
  Scenario Outline: Actualizar nombre y estado de mascota
    Given existe una mascota propia "<antes>" con estado "<inicial>"
    When actualiza la mascota a nombre "<despues>" y estado "<final>"
    Then el codigo de respuesta debe ser 200
    And la respuesta contiene todos los datos de la mascota
    And la mascota conserva los datos al volver a consultarla
    Examples:
      | antes | inicial   | despues | final   |
      | Nala  | available | Nala QA | pending |
      | Milo  | pending   | Milo QA | sold    |

  @CP06 @Negative
  Scenario: Rechazar actualizacion con tipo de contenido no admitido
    When envia una actualizacion con tipo de contenido no admitido
    Then el codigo de respuesta debe ser 415
