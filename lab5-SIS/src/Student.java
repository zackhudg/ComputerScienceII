/**
 * implelemts the student subclass of User
 * @author:Zack Hudgins
 */
public class Student extends User {

    /**
     * constructor for student
     * @param username
     */
    public Student(String username) {
        super(username, UserType.STUDENT, new CourseComparator());
    }
}