package com.poc.mongo;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "phones",
        "email",
        "dateOfBirth",
        "registered",
        "emergencyContacts"
})
public class User {

    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phones")
    private Phones phones;
    @JsonProperty("email")
    private List<String> email = new ArrayList<String>();
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("registered")
    private Boolean registered;
    @JsonProperty("emergencyContacts")
    private List<EmergencyContact> emergencyContacts = new ArrayList<EmergencyContact>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public User() {
    }

    /**
     * @param dateOfBirth
     * @param id
     * @param emergencyContacts
     * @param email
     * @param registered
     * @param name
     * @param phones
     */
    public User(String id, String name, Phones phones, List<String> email, String dateOfBirth, Boolean registered, List<EmergencyContact> emergencyContacts) {
        super();
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.registered = registered;
        this.emergencyContacts = emergencyContacts;
    }

    public User(String id) {
        this.id = id;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("phones")
    public Phones getPhones() {
        return phones;
    }

    @JsonProperty("phones")
    public void setPhones(Phones phones) {
        this.phones = phones;
    }

    @JsonProperty("email")
    public List<String> getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(List<String> email) {
        this.email = email;
    }

    @JsonProperty("dateOfBirth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("registered")
    public Boolean getRegistered() {
        return registered;
    }

    @JsonProperty("registered")
    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    @JsonProperty("emergencyContacts")
    public List<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    @JsonProperty("emergencyContacts")
    public void setEmergencyContacts(List<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
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
