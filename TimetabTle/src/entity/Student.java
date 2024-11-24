package entity;

import java.sql.Date;

public class Student {
    private String studentId;
    private String studentName;
    private String password;
    private Date birthDate;
    private String className;
    private String major;

    public Student(String studentId, String studentName, String password, Date birthDate, String className, String major) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.password = password;
        this.birthDate = birthDate;
        this.className = className;
        this.major = major;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getPassword() { return password; }
    public Date getBirthDate() { return birthDate; }
    public String getClassName() { return className; }
    public String getMajor() { return major; }
}
