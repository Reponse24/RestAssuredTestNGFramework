
package com.spotify.oauth2.pojo;

import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
//@Data
//@Getter @Setter
@Jacksonized
@Builder // If we only use @Builder, Lombok with not be able to identify Jackson annotations like @JsonInclude(). That's why we need to use annotation named @Jacksonized
//So, I have to make sure that whenever I use @Builder, I'm using @Jacksonized as well.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Playlist {

    @JsonProperty("collaborative")
    Boolean collaborative;
    @JsonProperty("description")
    String description;
    @JsonProperty("external_urls")
    ExternalUrls externalUrls;
    @JsonProperty("followers")
    Followers followers;
    @JsonProperty("href")
    String href;
    @JsonProperty("id")
    String id;
    @JsonProperty("images")
    List<Object> images;
    @JsonProperty("name")
    String name;
    @JsonProperty("owner")
    Owner owner;
    @JsonProperty("primary_color")
    Object primaryColor;
    @JsonProperty("public")
    Boolean _public;
    @JsonProperty("snapshot_id")
    String snapshotId;
    @JsonProperty("tracks")
    Tracks tracks;
    @JsonProperty("type")
    String type;
    @JsonProperty("uri")
    String uri;



}
