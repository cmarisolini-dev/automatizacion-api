package org.example.StepDefinitions;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import org.example.clients.PetStoreClient;
import org.example.models.PetData;
import org.example.questions.ResponseBody;
import org.example.questions.ResponseCode;
import org.example.tasks.*;
import org.example.utils.TestIds;
import java.util.List;
import java.util.Map;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PetStepDefinition {
    private Actor actor;
    private PetData pet;
    private Long ownedId;
    private String filter;

    @Given("el actor establece el endpoint de PetStore")
    public void connect() { actor = PetStoreClient.newActor(); }

    private long unusedId() {
        for (int i=0;i<4;i++) {
            long id = TestIds.unique();
            actor.attemptsTo(GetPetById.with(id));
            int code = SerenityRest.lastResponse().statusCode();
            if (code == 404) return id;
            assertEquals("Verificacion de ID disponible",200,code);
        }
        throw new AssertionError("No se pudo reservar un ID libre");
    }

    private void prepare(String name, long categoryId, String category, long tagId, String tag, String photo, String status) {
        pet = new PetData(unusedId(),categoryId,category,name,photo,tagId,tag,status);
        ownedId = pet.id();
        actor.attemptsTo(AddPet.with(pet.payload()));
    }

    @When("registra una mascota {string} de categoria {long} {string}, etiqueta {long} {string}, foto {string} y estado {string}")
    public void create(String name,long categoryId,String category,long tagId,String tag,String photo,String status) {
        prepare(name,categoryId,category,tagId,tag,photo,status);
    }
    @Given("existe una mascota propia {string} con estado {string}")
    public void existing(String name,String status) {
        prepare(name,1,"Dogs",1,"qa", "https://example.com/pet.jpg",status);
        status(200); petBody();
    }
    @Given("se dispone de un identificador inexistente")
    public void absent() { pet = new PetData(unusedId(),1,"Dogs","Inexistente","https://example.com/pet.jpg",1,"qa","available"); }

    @When("consulta la mascota por identificador")
    public void get() { actor.attemptsTo(GetPetById.with(pet.id())); }
    @When("actualiza la mascota a nombre {string} y estado {string}")
    public void update(String name,String status) { pet=pet.updated(name,status); actor.attemptsTo(UpdatePet.with(pet.payload())); }
    @When("envia un registro con identificador de tipo invalido")
    public void badPost() { actor.attemptsTo(AddPet.with("{\"id\":\"no-numerico\",\"name\":\"QA\",\"photoUrls\":[]}")); }
    @When("envia una actualizacion con identificador de tipo invalido")
    public void badPut() { actor.attemptsTo(UpdatePet.with("{\"id\":\"no-numerico\",\"name\":\"QA\",\"photoUrls\":[]}")); }
    @When("envia un registro con tipo de contenido no admitido")
    public void unsupportedPost() { actor.attemptsTo(AddPet.withUnsupportedMediaType("invalid")); }
    @When("envia una actualizacion con tipo de contenido no admitido")
    public void unsupportedPut() { actor.attemptsTo(UpdatePet.withUnsupportedMediaType("invalid")); }
    @When("consulta mascotas con estado {string}")
    public void find(String status) { filter=status; actor.attemptsTo(FindPetsByStatus.with(status)); }
    @When("actualiza por formulario a nombre {string} y estado {string}")
    public void form(String name,String status) { pet=pet.updated(name,status); actor.attemptsTo(UpdatePetWithForm.with(pet.id(),name,status)); }
    @When("elimina la mascota por identificador")
    public void delete() { actor.attemptsTo(DeletePet.with(pet.id())); }

    @Then("el codigo de respuesta debe ser {int}")
    public void status(int code) { actor.should(seeThat("Codigo HTTP",ResponseCode.status(),equalTo(code))); }
    @Then("la respuesta contiene todos los datos de la mascota")
    public void petBody() {
        actor.should(seeThat("Tipo de contenido",ResponseBody.contentType(),containsString("application/json")),
            seeThat("ID",ResponseBody.number("id"),equalTo(pet.id())),
            seeThat("Nombre",ResponseBody.text("name"),equalTo(pet.name())),
            seeThat("Estado",ResponseBody.text("status"),equalTo(pet.status())),
            seeThat("Categoria ID",ResponseBody.number("category.id"),equalTo(pet.categoryId())),
            seeThat("Categoria",ResponseBody.text("category.name"),equalTo(pet.categoryName())),
            seeThat("Foto",ResponseBody.text("photoUrls[0]"),equalTo(pet.photoUrl())),
            seeThat("Etiqueta ID",ResponseBody.number("tags[0].id"),equalTo(pet.tagId())),
            seeThat("Etiqueta",ResponseBody.text("tags[0].name"),equalTo(pet.tagName())));
    }
    @Then("la mascota conserva los datos al volver a consultarla")
    public void persisted() { get(); status(200); petBody(); }
    @Then("el servidor rechaza el cuerpo invalido")
    public void rejectedBody() { actor.should(seeThat("Rechazo del cuerpo (400 o 405)",ResponseCode.status(),anyOf(equalTo(400),equalTo(405)))); }
    @Then("la respuesta contiene solo mascotas del estado solicitado e incluye la propia")
    public void filtered() {
        actor.should(seeThat("Tipo de contenido",ResponseBody.contentType(),containsString("application/json")));
        List<?> items=ResponseBody.items().answeredBy(actor);
        assertNotNull("Lista JSON",items);
        boolean found=false;
        for(Object item:items) {
            assertTrue("Elemento JSON objeto",item instanceof Map);
            Map<?,?> row=(Map<?,?>)item;
            assertEquals("Estado de cada resultado",filter,row.get("status"));
            Object id=row.get("id");
            if(id instanceof Number && ((Number)id).longValue()==pet.id()) {
                assertEquals("Nombre del registro propio",pet.name(),row.get("name")); found=true;
            }
        }
        assertTrue("Mascota propia incluida en el filtro",found);
    }
    @Then("el estado invalido se rechaza o devuelve una lista vacia")
    public void invalidFilter() {
        int code=ResponseCode.status().answeredBy(actor);
        assertTrue("Se espera 400 o 200 con lista vacia; recibido "+code,code==400 || code==200);
        if(code==200) assertTrue("No debe devolver mascotas para estado invalido",ResponseBody.items().answeredBy(actor).isEmpty());
    }
    @Then("la mascota ya no existe al consultarla")
    public void removed() { get(); status(404); }

    @After
    public void cleanOwnedPet() {
        if(ownedId==null || actor==null) return;
        // Delete only this scenario's generated ID. Cleanup must not hide the original failure.
        try {
            actor.attemptsTo(GetPetById.with(ownedId));
            if(SerenityRest.lastResponse().statusCode()==200) {
                actor.attemptsTo(DeletePet.with(ownedId));
                int code=SerenityRest.lastResponse().statusCode();
                if(code!=200 && code!=404) System.err.println("Limpieza HTTP "+code+" para "+ownedId);
            }
        } catch(RuntimeException ex) { System.err.println("Limpieza no completada: "+ex.getMessage()); }
    }
}
