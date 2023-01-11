/**
 * implelemts the User class
 * @author:Zack Hudgins
 */
import java.util.*;

public class User implements Comparable<User> {
    /**
     * username - name that user can be accessed by
     * type - student of prof
     * courses - set of courses user is enrolled in or teaches
     */
    private String username;
    private User.UserType type;
    private TreeSet<Course> courses;

    enum UserType{
        PROFESSOR, STUDENT
    }

    /**
     * constructor for user
     * @param username name to be accessed by
     * @param type student of prof
     * @param comp comparator to determine sorting method
     */
    public User(String username, UserType type, Comparator<Course> comp) {
        this.username = username;
        this.type = type;
        this.courses = new TreeSet<>(comp);
    }

    /**
     *
     * @return type of user
     */
    public UserType getType() {
        return this.type;
    }

    /**
     *
     * @return user's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * adds course to user's courses
     * @param course course to be added
     * @return true if success, false if failed (already enrolled)
     */
    public boolean addCourse(Course course){
        if(!this.courses.contains(course)) {
            this.courses.add(course);
            return true;
        }
        return false;
    }

    /**
     * removes course from user's courses
     * @param course course to be removed
     * @return true if success, false if failed
     */
    public boolean removeCourse(Course course){
        if (this.courses.contains(course)){
            this.courses.remove(course);
            return true;
        }
        return false;
    }

    /**
     *
     * @return collection of user's courses
     */
    public Collection<Course> getCourses() {
        return this.courses;
    }

    /**
     * determines if usernames are identical
     * @param other other user to be compared
     * @return true if same username, false otherwise
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof User){
            return this.username == ((User) other).getUsername();
        }
        return false;
    }

    /**
     *
     * @return hashed version of username
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.username);
    }

    /**
     *
     * @return stringified version of user
     */
    @Override
    public String toString() {
        String result="";
        Iterator it = courses.iterator();
        while (it.hasNext()){
            Object next = it.next();
            if (next instanceof Course || next != courses.last()){
                result += ((Course) next).getName() + ", ";
            }
        }
        result = result.substring(0, result.length() - 2);


        return "User{" +
                "username='" + username + '\'' +
                ", type=" + type +
                ", courses=[" + result + "]}";
    }

    /**
     * comparison function for default sorting
     * @param other other user to be compared
     * @return - if username comes first, + if comes after, 0 if equal
     */
    public int compareTo(User other){
        return this.username.compareTo(other.getUsername());
    }
}
