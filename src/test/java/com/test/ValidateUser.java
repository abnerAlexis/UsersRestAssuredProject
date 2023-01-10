package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;

public class ValidateUser extends Base{

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri((String) getPropertyValue("baseURL"))
            .addHeader("x-api-key", (String) getPropertyValue("API_KEY"))
            .log(LogDetail.ALL)
            .setContentType((ContentType.JSON));
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_CREATED)
            .expectContentType(ContentType.JSON)
            .log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test(dataProvider = "userParams")
    public void validateUser(String name, String username, String email, Address address) {
        User user = new User(name, username, email, address);
        RequestBodyRoot userRoot = new RequestBodyRoot(user);
        RequestBodyRoot deserializedUserRoot = given()
            .body(userRoot)
            .when()
            .post("/users")
            .then().spec(responseSpecification)
            .extract().response()
            .as(RequestBodyRoot.class)
        ;
        assertThat(deserializedUserRoot.getUser().getName(), equalTo(user.getName()));
        assertThat(deserializedUserRoot.getUser().getUsername(), equalTo(user.getUsername()));
        assertThat(deserializedUserRoot.getUser().getEmail(), equalTo(user.getEmail()));
        assertThat(deserializedUserRoot.getUser().getAddress().city, equalTo(user.getAddress().city));
        assertThat(deserializedUserRoot.getUser().getAddress().street, equalTo(user.getAddress().street));
        assertThat(deserializedUserRoot.getUser().getAddress().suite, equalTo(user.getAddress().suite));
        assertThat(deserializedUserRoot.getUser().getAddress().zipcode, equalTo(user.getAddress().zipcode));
        assertThat(deserializedUserRoot.getUser().getAddress().geo.lat, equalTo(user.getAddress().geo.lat));
        assertThat(deserializedUserRoot.getUser().getAddress().geo.lng, equalTo(user.getAddress().geo.lng));
        assertThat(deserializedUserRoot.getId(), isA(Integer.class));
    }

    @DataProvider()
    public Object[][] userParams() {
        return new Object[][]{
            {"Leanne Graham", "Bret", "Sincere@april.biz",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
                    new Geo("-37.3159", "81.1496"))
            }
        };
    }
}

/*

    {
        "name": "Leanne Graham",
        "username": "Bret",
        "email": "Sincere@april.biz",
        "address": {
          "street": "Kulas Light",
          "suite": "Apt. 556",
          "city": "Gwenborough",
          "zipcode": "92998-3874",
          "geo": {
            "lat": "-37.3159",
            "lng": "81.1496"
          }
        }
      }
 */