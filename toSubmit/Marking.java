/**
 * @author Mohamed Moselhy, Abdullah Khan, Brandon Mathew, Sama Rahimian
 * @version 0.1
 * Winter cs2212
 *
 * Houses functions for all your marking needs.
 *
 */

import offerings.CourseOffering;
import offerings.ICourseOffering;
import registrar.ModelRegister;
import customDatatypes.Marks;
import systemUsers.StudentModel;
import java.util.Hashtable;
import java.util.Map;


public class Marking {

    public Marking(){
    }

    /**
     * allows for mark submission
     * @param cID course id
     * @param sID student id
     * @param type assessment type 'Final', 'Midterm', or 'ASSIGNMENT-x', where x is any integer
     * @param grade grade received
     */
    protected void addMark(String cID, String sID, String type, Double grade){
        CourseOffering course = ModelRegister.getInstance().getRegisteredCourse(cID);
        if(course == null){
            System.out.println("No such course.");
            return;
        }

        StudentModel student = findStudent(course, sID);
        if(student == null){
            System.out.println("No such student.");
            return;
        }

        Map<ICourseOffering, Marks> marksPackage;
        Marks marks;
        if(student.getPerCourseMarks() == null){
            marksPackage = new Hashtable<>();
            marks = new Marks();

            marks.addToEvalStrategy(type, grade);

            marksPackage.put(course, marks);
            student.setPerCourseMarks(marksPackage);
        }
        else{
            marks = student.getPerCourseMarks().get(course);
            marks.addToEvalStrategy(type, grade);
        }

    }
    
    

    /**
     * allows user to change a already submitted grade
     * @param cID course id
     * @param sID student id
     * @param type assessment type 'Final', 'Midterm', or 'ASSIGNMENT-x', where x is any integer
     * @param grade grade to be changed
     */
    protected void updateMark(String cID, String sID, String type, Double grade){
        CourseOffering course = ModelRegister.getInstance().getRegisteredCourse(cID);
        if(course == null){
            System.out.println("\nNo such course.");
            return;
        }

        StudentModel student = findStudent(course, sID);
        if(student == null){
            System.out.println("\nNo such student.");
            return;
        }

        if(student.getPerCourseMarks() == null){
            System.out.println("\nThis student has no assessments on file. Perhaps you meant to add a mark.");
        }
        else{
            Marks marks = student.getPerCourseMarks().get(course);
            marks.addToEvalStrategy(type, grade);
        }

    }

    /**
     * finds a student and returns them
     * @param c course
     * @param id student id
     * @return
     */
    protected StudentModel findStudent(CourseOffering c, String id){
        for (StudentModel s : c.getStudentsEnrolled()){
            if(s.getID().equals(id))
                return s;
        }

        return null;
    }

}
