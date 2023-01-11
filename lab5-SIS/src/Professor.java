/**
 * implelemts the professor subclass of User
 * @author:Zack Hudgins
 */
import java.util.Comparator;

public class Professor extends User {

    /**
     * constructor for professor, uses comparator that sorts by level then name of course
     * @param username
     */
    public Professor(String username) {
        super(username, UserType.PROFESSOR, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                if (o1.getLevel() < o2.getLevel()) {
                    return -1;
                } else if (o1.getLevel() > o2.getLevel()) {
                    return 1;
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
    }

}