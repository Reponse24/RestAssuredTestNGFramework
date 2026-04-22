package com.spotify.oauth2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.specification.ProxySpecification.auth;

// A class for reusable methods

public class RestResource {
    static String access_token = "BQBiS4iurmc8_XJbSlwfkq9xUnrjXeqALzunOjxM53nUNBQUBwWp51TVkOHDKbauHaiMebjlRx9H9m286jiUJ9" +
            "7mLDSuxzviWwNpG1ZpQ15vduQv4mBb09tXKdCae7Cj4pPCjgRffG-mR9VbKQ-obFt2RhVZ3t6L28_XuU5bd9-fVS3RJJWtoY_EiICBl9E_" +
            "Myc4fjiSl7aLMnWVPcFU83M0OZygWzGhSs1LxZvAgM4dqgXMFTzTh4WMrI5DXMIqbI8bGKd_sdp_" +
            "NucreaZ9D47xDWSb8s-iD9cECFPHxw1MVzHE1wSZEqcnhOBB5Q";

    public static  Response post(String path, String token, Object requestPlaylist){
        return given(getRequestSpec()).
                body(requestPlaylist).
                auth().oauth2(token) //This is a convenient way to pass the token, than the below one
                //header("Authorization", "Bearer " + token)
                .when().post("path")
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }
     public static Response postAccount(HashMap<String, String> formParams){
        return  given(getAccountRequestSpec()).
                 formParams(formParams)
                 .when().post(API + TOKEN).
                 then().spec(getResponseSpec()).
                 extract().
                 response();
     }
    public static Response get(String path, String token){
        return given(getRequestSpec())
                .auth().oauth2(token)
               // .header("Authorization", "Bearer " + token)
                .when().get(path).
                then().spec(getResponseSpec()).
                extract().
                response();
    }
    public static Response update(String path, String token, Object requestPlaylist){
        return given(getRequestSpec())
                .auth().oauth2(token)
                //.header("Authorization", "Bearer " + token)
                .body(requestPlaylist)
                .when().put(path).
                then().spec(getResponseSpec()).
                extract().
                response();

    }
}
