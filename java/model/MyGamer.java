package model;

/**
 * Created by Marcy on 17/07/2015.
 */
public class MyGamer {
    public String persons;
    public String gamename;
    public long created;
    // public int GAME_LEVEL = 1;
    // public int X_POINTS = 0;
    // public int DOZH = 500;
    // public int JEMZ = 30;
    // public String KEY_ID = "_id";

    public String getPersons() {
        return persons;
    }
    public void setPersons(String persons) {
        this.persons = persons;
    }
    public String getGamename() {
        return gamename;
    }
    public void setGamename(String gamename) {
        this.gamename = gamename;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
}
