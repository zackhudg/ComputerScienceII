/**
 * implements Course Database
 * @author:Zack Hudgins
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

public class CourseDB implements DB<Integer, Course> {
    /**
     * hashmap acts as DB, stores all courses w/ their ids
     */
    private HashMap<Integer, Course> courses;

    /**
     * constructor, creates empty DB
     */
    public CourseDB() {
        this.courses = new HashMap<Integer, Course>();
    }

    /**
     * adds or replaces course of certain id to DB
     * @param course course to be added
     * @return course that was replaced, null if none
     */
    public Course addValue(Course course){
        Course previous = null;
        if (this.courses.containsKey(course.getId())) {
            previous = this.courses.get(course.getId());
        }
        this.courses.put(course.getId(), course);
        return previous;
    }

    /**
     * gets course from id
     * @param id course id
     * @return course from id
     */
    public Course getValue(Integer id){
        return this.courses.get(id);
    }

    /**
     * checks if DB has course w/ certain id
     * @param id id to be checked
     * @return True if exists, false if not
     */
    public boolean hasKey(Integer id){
        return this.courses.containsKey(id);
    }

    /**
     *
     * @return colelction of all courses in DB
     */
    public Collection<Course> getAllValues(){
        TreeSet<Course> tree = new TreeSet<>();
        for (Course course: this.courses.values()){
            tree.add(course);
        }
        return tree;
    }

}
