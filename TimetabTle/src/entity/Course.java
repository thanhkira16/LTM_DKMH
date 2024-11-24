package entity;

import java.io.Serializable;
import java.sql.Date;

public class Course implements Serializable {
    private int courseId;
    private String courseName;
    private int credits;
    private String classCode;
    private int studentCount;
    private String dayOfWeek;
    private String startPeriod;
    private int totalPeriods;
    private String room;
    private String instructor;
    private Date startDate;
    private Date endDate;

    // Constructor
    public Course(int courseId, String courseName, int credits, String classCode, int studentCount,
                  String dayOfWeek, String startPeriod, int totalPeriods, String room,
                  String instructor, Date startDate, Date endDate) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.classCode = classCode;
        this.studentCount = studentCount;
        this.dayOfWeek = dayOfWeek;
        this.startPeriod = startPeriod;
        this.totalPeriods = totalPeriods;
        this.room = room;
        this.instructor = instructor;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters (có thể được sinh tự động trong IDE)

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public String getClassCode() {
        return classCode;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public int getTotalPeriods() {
        return totalPeriods;
    }

    public String getRoom() {
        return room;
    }

    public String getInstructor() {
        return instructor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", classCode='" + classCode + '\'' +
                ", studentCount=" + studentCount +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startPeriod=" + startPeriod +
                ", totalPeriods=" + totalPeriods +
                ", room='" + room + '\'' +
                ", instructor='" + instructor + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

