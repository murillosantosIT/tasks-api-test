package br.ce.wcaquino.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;


public class APITest {
    //Antes de tudo cria essa baseUrl para depois começar os testes
    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        //given = dado
        //when = quando
        //then = então
        RestAssured
            .given()
            .when()
                .get("/todo")
            .then()
                .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        //given = dado
        //when = quando
        //then = então
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body("{\"task\":\"Nova Task\",\"dueDate\":\"2021-02-21\"}")
            .when()
                .post("/todo")
            .then()
                .log().all()
                .statusCode(201)
        ;
    }

    @Test
    public void NaodeveAdicionarTarefaInvalida() {
        //given = dado
        //when = quando
        //then = então
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body("{\"task\":\"Nova Task\",\"dueDate\":\"2019-02-21\"}")
            .when()
                .post("/todo")
                .then()
            .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }
}
