package tests;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import models.User;
import models.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.SingleUserSpecs.requestSpec;
import static specs.SingleUserSpecs.responseSpec;

public class LombokReqresinTests {



    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }


    @Test
    void singleUserLombokSpecTest() {
        
        //adding this didn't help
        //LogConfig logconfig = new LogConfig().enablePrettyPrinting(true);
        //RestAssured.config().logConfig(logconfig);


        Integer userId = 1;
        UserData sampleUserData = new UserData();
        sampleUserData.setId(userId);
        sampleUserData.setEmail("george.bluth@reqres.in");
        User sampleUser = new User();
        sampleUser.setUserData(sampleUserData);

        User ResponseUser = given()
                //.baseUri("https://reqres.in")
                //.basePath("/api/users")
                //.log().all();
                // replaced with ↓
                .spec(requestSpec)
                .when()
                .get("/" + userId)
                .then()
                //.log().status()
                //.log().body()
                //.statusCode(200)
                //.body(notNullValue())
                // replaced with ↓
                .spec(responseSpec)
                // TODO: 17.07.2022 why I have no status in my logs? 
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
