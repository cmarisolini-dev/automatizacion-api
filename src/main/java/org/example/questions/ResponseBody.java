package org.example.questions;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;
import java.util.List;
public final class ResponseBody {
    private ResponseBody() {}
    public static Question<String> text(String path) { return actor -> SerenityRest.lastResponse().jsonPath().getString(path); }
    public static Question<Long> number(String path) { return actor -> SerenityRest.lastResponse().jsonPath().getLong(path); }
    public static Question<List<?>> items() { return actor -> SerenityRest.lastResponse().jsonPath().getList("$"); }
    public static Question<String> contentType() { return actor -> SerenityRest.lastResponse().contentType(); }
}
