/**
 * implelemts the backend of the SIS
 * @author:Zack Hudgins
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

public class Backend {
    /**
     * DBs that store all info of users/courses
     */
    private CourseDB courseDB;
    private UserDB userDB;

    /**
     * constructor for backend, fills DB with info
     * @param courseFile file to read course info from
     * @param professorFile file to read prof  info from
     * @param studentFile file to read student info from
     * @throws FileNotFoundException
     */
    public Backend(String courseFile, String professorFile, String studentFile) throws FileNotFoundException{
        courseDB = new CourseDB();
        userDB = new UserDB();
        initializeCourseDB(courseFile);
        initializeUserDB(professorFile, studentFile);

    }

    /**
     * helper function to initialize CDB, creates all courses from file
     * @param courseFile file to be read
     * @throws FileNotFoundException
     */
    private void initializeCourseDB(String courseFile) throws FileNotFoundException{
        try (Scanner in = new Scanner(new File(courseFile))){
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                Course course = new Course(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]));
                courseDB.addValue(course);
            }
        }
    }

    /**
     * helper function to initialize UDB, assigns all courses from file
     * @param professorFile file to be read
     * @param studentFile file to be read
     * @throws FileNotFoundException
     */
    private void initializeUserDB(String professorFile, String studentFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(professorFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                User prof = new Professor(fields[0]);
                addCourses(prof, fields);
                userDB.addValue(prof);

            }
        }

        try (Scanner in = new Scanner(new File(studentFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                User stu = new Student(fields[0]);
                addCourses(stu, fields);
                userDB.addValue(stu);
            }
        }
    }

    /**
     * adds courses to each user
     * @param user adds course to this user
     * @param courseIds course being added
     */
    public void addCourses (User user, String[] courseIds){
        for (int i = 1; i < courseIds.length; i++) {
            user.addCourse(courseDB.getValue(Integer.parseInt(courseIds[i])));
            if (user instanceof Professor){
                courseDB.getValue(Integer.parseInt(courseIds[i])).addProfessor(user.getUsername());
            }else{
                courseDB.getValue(Integer.parseInt(courseIds[i])).addStudent(user.getUsername());
            }
        }
    }

    /**
     * checks if course exists
     * @param id course id
     * @return true if exists, false if not
     */
    public boolean courseExists (int id){
        return courseDB.hasKey(id);
    }

    /**
     * enrolls student in course
     * @param username student to be enrolled
     * @param courseId course to be enrolled in
     * @return true if success, false if not
     */
    public boolean enrollStudent(String username, int courseId){
        if (!courseDB.getValue(courseId).getStudents().contains(username)) {
            courseDB.getValue(courseId).addStudent(username);
            userDB.getValue(username).addCourse(courseDB.getValue(courseId));
            return true;
        }
        return  false;
    }

    /**
     *
     * @return all courses in DB
     */
    public Collection<Course> getAllCourses(){
        return courseDB.getAllValues();
    }

    /**
     *
     * @return all user in DB
     */
    public Collection<User> getAllUsers(){
        return userDB.getAllValues();
    }

    /**
     * checks if user is student
     * @param username user to be checked
     * @return true if yes, false if not
     */
    public boolean isStudent(String username){
        return userDB.getValue(username).getType() == User.UserType.STUDENT;
    }

    /**
     * returns course object from id
     * @param id course id
     * @return course object
     */
    public Course getCourse(int id){
        return courseDB.getValue(id);
    }

    /**
     * unenrolls student from course
     * @param username student to be unenrolled
     * @param courseId course to be unenrolled from
     * @return
     */
    public boolean unenrollStudent(String username, int courseId){
        if (courseDB.getValue(courseId).getStudents().contains(username)){
            courseDB.getValue(courseId).removeStudent(username);
            userDB.getValue(username).removeCourse(courseDB.getValue(courseId));
            return true;
        }
        return false;
    }

    /**
     * checks if user exists in DB
     * @param username user to be checked
     * @return true if exists, false if not
     */
    public boolean userExists(String username){
        return userDB.hasKey(username);
    }

    /**
     * gets courses that the user has
     * @param username user that is checked
     * @return collection of user's courses
     */
    public Collection<Course> getCoursesUser(String username){
        return userDB.getValue(username).getCourses();
    }
}
