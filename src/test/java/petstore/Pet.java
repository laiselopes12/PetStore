package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("dados/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Myke"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("sta"))
        ;

    }
    @Test
    public void consultarPet(){
        String petId = "199812053";

        String token =
                given()
                        .contentType("application/json")
                        .log().all()
                        .when()
                        .get(uri + "/" + petId)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is("Myke"))
                        .body("category.name", is("dog"))
                        .body("status",is("available"))
                        .extract()
                        .path("category.name")
                ;
        System.out.println("O token é " + token);

    }

}
