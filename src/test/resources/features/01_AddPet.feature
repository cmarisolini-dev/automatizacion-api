@PetStore @Post_Pet
Feature: Registrar mascotas
  Background:
    Given el actor establece el endpoint de PetStore

  @CP01 @HappyPath
  Scenario Outline: Registrar mascota con datos completos
    When registra una mascota "<nombre>" de categoria <categoriaId> "<categoria>", etiqueta <tagId> "<tag>", foto "<foto>" y estado "<estado>"
    Then el codigo de respuesta debe ser 200
    And la respuesta contiene todos los datos de la mascota
    And la mascota conserva los datos al volver a consultarla
    Examples:
      | nombre   | categoriaId | categoria | tagId | tag      | foto                        | estado    |
      | Firulais | 1           | Dogs      | 1     | friendly | https://example.com/dog.jpg | available |
      | Pelusa   | 2           | Cats      | 2     | playful  | https://example.com/cat.jpg | pending   |
      | Max      | 1           | Dogs      | 3     | active   | https://example.com/max.jpg | sold      |

  @CP02 @Negative
  Scenario: Rechazar registro con tipo de contenido no admitido
    When envia un registro con tipo de contenido no admitido
    Then el codigo de respuesta debe ser 415
