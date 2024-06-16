package dev.m2t.unlucky.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "rooms")
public class Room {
    @Id
    private String id;
    private String name;
    private String description;
    private String owner;
    private boolean isPublic;
    private List<String> pendingUserInvites = new ArrayList<>();
    private List<String> users = new ArrayList<>();
    private Map<String, Integer> userCorrectPredictions = new HashMap<>();

    public Room() {

    }

    public Room(String name, String description, String owner, boolean isPublic, List<String> pendingUserInvites, List<String> users) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.isPublic = isPublic;
        this.pendingUserInvites = pendingUserInvites;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<String> getPendingUserInvites() {
        return pendingUserInvites;
    }

    public void setPendingUserInvites(List<String> pendingUserInvites) {
        this.pendingUserInvites = pendingUserInvites;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public Map<String, Integer> getUserCorrectPredictions() {
        return userCorrectPredictions;
    }

    public void setUserCorrectPredictions(Map<String, Integer> userCorrectPredictions) {
        this.userCorrectPredictions = userCorrectPredictions;
    }
}
