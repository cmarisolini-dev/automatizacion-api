package org.example.tasks;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
public class FindPetsByStatus implements Task {
    private final String status;
    public FindPetsByStatus(String status) { this.status = status; }
    public static Task with(String status) { return instrumented(FindPetsByStatus.class, status); }
    @Override public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource("/pet/findByStatus").with(r -> r.accept(ContentType.JSON).queryParam("status", status)));
    }
}
