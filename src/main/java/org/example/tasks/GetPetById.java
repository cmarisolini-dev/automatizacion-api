package org.example.tasks;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
public class GetPetById implements Task {
    private final long id;
    public GetPetById(long id) { this.id = id; }
    public static Task with(long id) { return instrumented(GetPetById.class, id); }
    @Override public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource("/pet/" + id).with(r -> r.accept(ContentType.JSON)));
    }
}
