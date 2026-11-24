package org.example.tasks;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
public class UpdatePetWithForm implements Task {
    private final long id;
    private final String name;
    private final String status;
    public UpdatePetWithForm(long id, String name, String status) { this.id = id; this.name = name; this.status = status; }
    public static Task with(long id, String name, String status) { return instrumented(UpdatePetWithForm.class, id, name, status); }
    @Override public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to("/pet/" + id).with(r -> r.contentType("application/x-www-form-urlencoded").accept(ContentType.JSON).formParam("name", name).formParam("status", status)));
    }
}
