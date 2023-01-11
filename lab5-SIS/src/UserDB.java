/**
 * implements UserDataBase
 * @author:Zack Hudgins
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

public class UserDB implements DB<String, User> {
    /**
     * hashmap represents database, stores usernames and users
     */
    private HashMap<String,User> users;

    /**
     * constructor, empty DB to start
     */
    public UserDB(){
        this.users = new HashMap<>();
    }

    /**
     * adds user to DB
     * @param user user to be added
     * @return previous user w/ same username that was replaced, if applicable
     */
    public User addValue(User user){
        User previous = null;
        if (this.users.containsKey(user.getUsername())) {
            previous = this.users.get(user.getUsername());
        }
        this.users.put(user.getUsername(), user);
        return previous;
    }

    /**
     * returns user from username
     * @param username accesses user
     * @return user accessed
     */
    public User getValue(String username){
        return this.users.get(username);
    }

    /**
     * determines if username exists in DB
     * @param username username to be checked
     * @return true if exists, false if not
     */
    public boolean hasKey(String username){
        return this.users.containsKey(username);
    }

    /**
     *
     * @return all users
     */
    public Collection<User> getAllValues(){
        TreeSet<User> tree = new TreeSet<>();
        for (User user: this.users.values()){
            tree.add(user);
        }
        return tree;
    }

}