package org.example.tasks;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdatePet implements Task {
    private final Object payload;
    private final String contentType;

    public UpdatePet(Object payload, String contentType) {
        this.payload = payload;
        this.contentType = contentType;
    }

    public static Task with(Object payload) {
        return instrumented(UpdatePet.class, payload, ContentType.JSON.toString());
    }

    public static Task withUnsupportedMediaType(String payload) {
        return instrumented(UpdatePet.class, payload, "text/plain");
    }

    @Override public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Put.to("/pet").with(r -> {
            r.contentType(contentType).accept("text/plain".equals(contentType) ? "*/*" : "application/json");
            return payload instanceof String ? r.body((String) payload) : r.body(payload);
        }));
    }
}
