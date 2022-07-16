package tests;

import io.restassured.RestAssured;
import models.User;
import models.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LombokReqresinTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void singleUserLombokTest() {
        Integer userId = 1;
        UserData sampleUserData = new UserData();
        sampleUserData.setId(userId);
        sampleUserData.setEmail("george.bluth@reqres.in");
        User sampleUser = new User();
        sampleUser.setUserData(sampleUserData);

        User ResponseUser = given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users/" + userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                //.body(notNullValue())
                .extract().as(User.class)
                ;
        System.out.println("userId= " + ResponseUser.getUserData().getId());
        assertThat(ResponseUser.getUserData().getId(), equalTo(sampleUser.getUserData().getId()));
        System.out.println("userEmail= " + sampleUser.getUserData().getEmail());
        assertThat(ResponseUser.getUserData().getEmail(), equalTo(sampleUser.getUserData().getEmail()));
    }

    @Test
    void listUsersGroovyTest() {
        ArrayList nameList = given()
                .log().uri()
                .log().body()
        .when()
                .get("/api/users?page=1")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(notNullValue())
                //search for all elements where .email = *s@reqres.in
                .body("data.findAll{it.email =~/.*?s@reqres.in/}.email.flatten()",hasItem("charles.morris@reqres.in"))
                //.extract().path("data.first_name.flatten()")
                .extract().path("data.first_name")  //extract all first names as a list
        ;

        System.out.println("nameList= " + nameList);
    }
}
