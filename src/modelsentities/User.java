package modelsentities;

public class User {
    private String userId;
    private String userName;
    private String passwordHash;
    private String uuid;

  
    public User(String userId, String userName, String passwordHash, String uuid) {
        this.userId = userId;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.uuid = uuid;
    }

 
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getUuid() {
        return uuid;
    }

   
    public String getName() {
        return userName;
    }
}
