package org.example.clients;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
public final class PetStoreClient {
    private PetStoreClient() {}
    public static Actor newActor() {
        return Actor.named("Usuario de PetStore").whoCan(CallAnApi.at(
            System.getProperty("petstore.baseUrl", "https://petstore.swagger.io/v2")));
    }
}
