package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.ApplicationApi.PlaylistApi;
import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
@Epic("Spotify OAuth2.0")
@Feature("Playlist APIs")
public class PlaylistTestsRevised extends BaseTest{
@Story("Create Playlist Story")
//    @Link("https://example.org")
//    @Link(name = "Website", type="mylink")
//    @TmsLink("12345")
//    @Issue("1234567")
    //Another option
//@Link(name = "Website", url = "https://jira.example.org/")
//@Issue("AUTH-123")
//@TmsLink("TMS-456")

    //These two below methods are specific to this API, so you can't call or use them in a separate class, so you can keep them in this class.
    @Step
    public Playlist playlistBuilder (String name, String description, boolean _public) {
        return Playlist.builder().build();
    }
    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }
    // benefits: However, these two below methods can be use in separate classes, not only for this API/ they can be reused in other classes
    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode, equalTo(statusCode.code)); //accessing a constant variable

    }
    @Step
    public void assertError(Error responseErr, StatusCode statusCode){
//        assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
//        assertThat(responseErr.getError().getMessage, equalTo(statusCode.message));
    }
    @Story("Create Playlist Story")
    @Description("This is the first test case")
    @Test(description = "Should be able to create a playlist")
    public void testCreatePlaylist(){
       Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
        }
    @Story("Create Playlist Story")
    @Test
    public void testGetPlaylist(){
        Playlist requestPlaylist = playlistBuilder("New Playlsit","New Playlist Description", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);//Deserializing
    }
    @Story("Create Playlist Story")
    @Test
    public void testUpdatePlaylist() {
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
    }
    @Test
    public void testCreatePlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("", generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);
        assertError(response.as(Error.class), StatusCode.CODE_400);
    }
    @Test
    public void testCreatePlaylistWithAnExpiredToken(){
        String invalid_token = "234566r";
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_401);
        assertError(response.as(Error.class), StatusCode.CODE_401);
    }
}
//The command that we run in parallel inavoiding hard coding the URIs is:
//mvn test -DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com"
