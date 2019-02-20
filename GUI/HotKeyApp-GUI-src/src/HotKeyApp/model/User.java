package HotKeyApp.model;

import org.json.simple.JSONObject;

/**
 * Created by poorboy on 15.01.18.
 */
public class User {
    /** Name of the User */
    private String name;

    /** Password of the User */
    private String passwd;

    /**
     * Constructor: Creates a new User
     * @param name name of the user
     * @param passwd password of the user
     */
    public User(String name, String passwd){
        this.name = name;
        this.passwd = passwd;
    }

    /**
     * Command: Get name of this user
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Command: Sets name for this user
     * @param name name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Command: Get password of this user
     * @return the password
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Command: Sets password for this user
     * @param passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Command: Returns this user as JSONObject. Needed to interact with the REST-Service.
     * @return This user as JSONObject
     */
    public JSONObject userAsJSON(){
        final JSONObject user = new JSONObject();
        user.put("name", this.name);
        user.put("passwd", this.passwd);

        return user;
    }
}
