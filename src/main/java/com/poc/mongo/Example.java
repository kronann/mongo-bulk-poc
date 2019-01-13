package com.poc.mongo;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "product",
        "version",
        "releaseDate",
        "demo",
        "user"
})
public class Example {

    @JsonProperty("product")
    private String product;
    @JsonProperty("version")
    private Double version;
    @JsonProperty("releaseDate")
    private String releaseDate;
    @JsonProperty("demo")
    private Boolean demo;
    @JsonProperty("user")
    private User user;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Example() {
    }

    /**
     * @param demo
     * @param product
     * @param user
     * @param releaseDate
     * @param version
     */
    public Example(String product, Double version, String releaseDate, Boolean demo, User user) {
        super();
        this.product = product;
        this.version = version;
        this.releaseDate = releaseDate;
        this.demo = demo;
        this.user = user;
    }

    @JsonProperty("product")
    public String getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(String product) {
        this.product = product;
    }

    @JsonProperty("version")
    public Double getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Double version) {
        this.version = version;
    }

    @JsonProperty("releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("releaseDate")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @JsonProperty("demo")
    public Boolean getDemo() {
        return demo;
    }

    @JsonProperty("demo")
    public void setDemo(Boolean demo) {
        this.demo = demo;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
