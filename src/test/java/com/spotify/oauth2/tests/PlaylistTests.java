package com.spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token= "BQC0vm1uGEOOsAMGH_pKO7JVx06zcZOlMIKub9yFLOIp-yHu3PjWok_esn-sRB-ciOxBsJCQ_XVHauU29lr2vpaQV2aLpeVIoVuNcCnKbB16nmzs5LazLdBt4PpofX76SEUpehuGPsPw7dgfSHDCz5uFc-19c6KRUrahCIilko58ZRJRGYOzWf9gzuB9IXf6LI1leisPw77b-Bg8y1gd-lnuboyfvoJtNILHCEzqxfWFR_0aWvdXKy2zPlXRaX1cw5L2-cgH0wtJBNJKPOn0Glhr8Z9tdU3RR9rec40rR5tbbsedbcQWnKzTv8B14Q";
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder().
              setBaseUri("https://api.spotify.com").
              setBasePath("/v1").
              addHeader("Authorization", "Bearer "+ access_token).
              setContentType(ContentType.JSON).
              log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder= new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void testCreatePlaylist(){
        String paylood= "{\n" +
                "    \"name\": \"New Playlist\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given(requestSpecification).
                body(paylood).
                when().post("/users/3165xj6kw7uebnv3xpqnep55mgvi/playlists")
                .then()
                .spec(responseSpecification)
                .statusCode(201)
                .body("name", equalTo("New Playlist"), "description", equalTo("New playlist description"), "public", equalTo(false));

    }
    @Test
    public void testGetPlaylist(){
        given(requestSpecification).
                when().get("/playlists/3cEYpjA9oz9GiPac4AsH4n")
                .then()
                .spec(responseSpecification).
                assertThat()
                .statusCode(200)
                .body("name", equalTo("New Playlist"), "description", equalTo("New playlist description"), "public", equalTo(false));

    }
    @Test
    public void testUpdatePlaylist() {
        String paylood= "{\n" +
                "    \"name\": \"New Playlist\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given(requestSpecification).
                body(paylood).
                when().put("/playlists/3cEYpjA9oz9GiPac4AsH4n")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("New Playlist"), "description", equalTo("New playlist description"), "public", equalTo(false));
    }
    @Test
    public void testCreatePlaylistWithoutName(){
        String paylood= "{\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given(requestSpecification).
                body(paylood).
                when().post("/users/3165xj6kw7uebnv3xpqnep55mgvi/playlists")
                .then()
                .spec(responseSpecification)
                .statusCode(400)
                .body("error.status", equalTo(400), "error.message", equalTo("Missing required field: name"));

    }
    @Test
    public void testCreatePlaylistWithAnExpiredToken(){
        String paylood= "{\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given().
                baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization", "Bearer "+ "123456").
                contentType(ContentType.JSON).
                log().all().

                body(paylood).
                when().post("/users/3165xj6kw7uebnv3xpqnep55mgvi/playlists")
                .then()
                .spec(responseSpecification)
                .statusCode(401)
                .body("error.status", equalTo(401), "error.message", equalTo("Invalid access token"));

    }
}
