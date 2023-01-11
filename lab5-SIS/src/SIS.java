import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * The main program and "front end" for the Student Information System.  It
 * is run on the command line in two ways:<br>
 * <br>
 * $ java SIS course-file professor-file student-file<br>
 * $ java SIS course-file professor-file student-file input-file<br>
 * <br>
 * Its job is to create the backend by passing it the course, professor and
 * student input files (see Backend class documentation), and then run
 * an loop reading input (either from standard input (first way of running),
 * or from a file of input commands (second way of running)).<br>
 * <br>
 * The commands the system accepts can be found by running the 'help' command:<br>
 * <br>
 * course {id}: list a course<br>
 * courses: list all courses (by course id)<br>
 * enroll {username} {id}: enroll a student in a course<br>
 * help: this message<br>
 * professor {username}: list courses taught by professor (by course level then by course name)<br>
 * student {username}: list courses taken by student (by course name)<br>
 * unenroll {username} {id}: unenroll a student from a course<br>
 * users: list all users (alphabetically by username) <br>
 * quit: quit SIS<br>
 * <br>
 *
 * @author Sean Strout @ RITCS
 * @author Zack Hudgins
 */
public class SIS {
    /** the course command */
    public final static String COURSE = "course";
    /** the courses command */
    public final static String COURSES = "courses";
    /** the enroll command */
    public final static String ENROLL = "enroll";
    /** the help command */
    public final static String HELP = "help";
    /** the professor command */
    public final static String PROFESSOR = "professor";
    /** the student command */
    public final static String STUDENT = "student";
    /** the unenroll command */
    public final static String UNENROLL = "unenroll";
    /** the users command */
    public final static String USERS = "users";
    /** the quit command */
    public final static String QUIT = "quit";

    private Backend backend;

    /**
     * Create the backend.
     *
     * @param courseFile the course file
     * @param professorFile the professor file
     * @param studentFile the student file
     * @throws FileNotFoundException if any of the files cannot be found
     */
    public SIS(String courseFile, String professorFile, String studentFile) throws FileNotFoundException {
        this.backend = new Backend(courseFile, professorFile, studentFile);
    }

    /**
     * A helper method for displaying the help message.
     */
    private void helpMessage() {
        System.out.println("course {id}: list a course");
        System.out.println("courses: list all courses (by course id)");
        System.out.println("enroll {username} {id}: enroll a student in a course");
        System.out.println("help: this message");
        System.out.println("professor {username}: list courses taught by professor (by course level then by course name)");
        System.out.println("student {username}: list courses taken by student (by course name)");
        System.out.println("unenroll {username} {id}: unenroll a student from a course");
        System.out.println("users: list all users (alphabetically by username) ");
        System.out.println("quit: quit SIS");
    }

    /**
     * enrolls student in course
     * @param username user of student
     * @param courseId course id being enrolled in
     */
    private void enrollStudent (String username, int courseId){
        if (verifyStudentCourse(username,courseId)){
            if (!this.backend.enrollStudent(username, courseId)){
                System.out.println("student "+username+" already enrolled in course");
            }
        }
    }

    /**
     * ensures the student and course are both valid
     * @param username student to be checked
     * @param courseId course to be checked
     * @return true if both valid, false otherwise
     */
    private boolean verifyStudentCourse(String username, int courseId){
        if (!backend.userExists(username)){
            System.out.println("user "+username+" does not exists!");
            return false;
        }else if(!backend.isStudent(username)){
            System.out.println("user "+username+" is not a student!");
            return false;
        }else if(!backend.courseExists(courseId)){
            System.out.println("course "+courseId+" does not exist!");
            return false;
        }
        return true;
    }

    /**
     * lists all courses from parameter
     * @param courses course collection of user
     */
    private void listAllCourses (Collection<Course> courses){

        for(Course course: courses){
            System.out.println(course);
        }
    }

    /**
     * lists all users in DB
     */
    private void listAllUsers(){
        for(User user: this.backend.getAllUsers()){
            System.out.println(user);
        }
    }

    /**
     * prints course if it exists
     * @param courseId course to be printed/checked
     */
    private void listCourse(int courseId){
        if (this.backend.courseExists(courseId)) {
            System.out.println(this.backend.getCourse(courseId));
        }else{
            System.out.println("course "+courseId+" does not exist!");
        }
    }

    /**
     * prints user and their classes if they exist
     * @param username user to be printed/checked
     */
    private void listUser(String username){
        if(this.backend.userExists(username)) {
                listAllCourses(this.backend.getCoursesUser(username));

        }else{
            System.out.println(username + " does not exist!");
        }
    }

    /**
     * removes student from course if enrolled
     * @param username student to be removed
     * @param courseId course to be removed from
     */
    private void unenrollStudent(String username, int courseId){
        if (verifyStudentCourse(username, courseId)){
            if(!this.backend.unenrollStudent(username, courseId)){
                System.out.println("student "+username+" was not enrolled in course "+courseId+"!");
            }
        }
    }



    /**
     * The main loop runs through the input commands that 'in' is attached
     * to via the Scanner.  It prompts with '&gt;'.  If the command is valid it calls
     * the appropriate private helper method.  Otherwise it should display the message:<br>
     * <br>
     * Unrecognized command {command}<br>
     * <br>
     * And reprompts the user.
     *
     * @param in a Scanner attached to the input (either stdin or the input file)
     * @param stdin tells whether the scanner is attached to stdin or not.  If
     * not, the input command should be displayed to standard output so it is
     * easier to follow the output.
     */
    public void mainLoop(Scanner in, boolean stdin) {
        if (stdin) {
            System.out.println("Type 'help' for the list of commands.");
        }
        System.out.print("> ");
        // continue looping until the user enters "quit" or there is no more input
        boolean done = false;
        while (!done && in.hasNext()) {
            // read the next command and then call the appropriate method to process it
            String line = in.nextLine();
            if (!stdin) {
                System.out.println(line);
            }
            String fields[] = line.split("\\s+");

            // TODO handle commands here.  With a switch statement remember to put a break
            // in after each case or else one case will "fall into" another!

            switch (fields[0]) {
                case COURSE:
                    listCourse(Integer.parseInt(fields[1]));
                    break;
                case COURSES:
                    listAllCourses(this.backend.getAllCourses());
                    break;
                case ENROLL:
                    enrollStudent(fields[1], Integer.parseInt(fields[2]));
                    break;
                case PROFESSOR:
                    listUser(fields[1]);
                    break;
                case STUDENT:
                    listUser(fields[1]);
                    break;
                case UNENROLL:
                    unenrollStudent(fields[1], Integer.parseInt(fields[2]));
                    break;
                case USERS:
                    listAllUsers();
                    break;
                case HELP:
                    helpMessage();
                    break;
                case QUIT:
                    done = true;
                    break;
                default:
                    System.out.println("Unrecognized command " + fields[0]);
            }

            // reprompt
            System.out.print("> ");
        }
    }

    /**
     * The main method.
     *
     * @param args command line arguments (used - see class description)
     * @throws FileNotFoundException if a file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        // display a usage message if the number of command line arguments
        // is not correct
        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java SIS course-file professor-file student-file [input]");
            return;
        }

        // create the frontend and tie it to the backend

        SIS sis = new SIS(args[0], args[1], args[2]);

        // create a scanner that is tied to either standard input or an input file
        Scanner in;
        boolean stdin = true;
        // if no arguments, tie the scanner to standard input
        if (args.length == 3) {
            in = new Scanner(System.in);
        } else {
            // otherwise tie scanner to a file using the last command
            // line argument as the filename
            in = new Scanner(new File(args[3]));
            stdin = false;
        }

        // enter the main loop
        sis.mainLoop(in, stdin);

        // close the scanner
        in.close();
    }
}
