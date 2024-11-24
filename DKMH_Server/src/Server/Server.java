package Server;

import entity.Course;
import entity.Class;
import entity.Student;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 3333;
    private static final String URL = "jdbc:mysql://localhost:3306/studentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static ClientLogger logger = new ClientLogger();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientInfo = clientSocket.getInetAddress().getHostAddress();
                logger.logConnection(clientInfo);
                new ClientHandler(clientSocket, logger).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private ClientLogger logger;
        private int clientId;

        public ClientHandler(Socket socket, ClientLogger logger) {
            this.clientSocket = socket;
            this.logger = logger;
            this.clientId = socket.hashCode(); // Sử dụng hashCode của socket làm ID duy nhất.
        }

        @Override
        public void run() {
            try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                String response = (String) in.readObject();
                logger.logQuery(clientId, "Request received: " + response);

                if (response.equals("login")) {
                    String studentId = (String) in.readObject();
                    String password = (String) in.readObject();

                    String result = handleLogin(studentId, password);
                    logger.logQuery(clientId, "Login attempt for student ID: " + studentId);

                    out.writeObject(result);

                    if (result.equals("true")) {
                        out.writeObject(getFullName(studentId));
                        out.writeObject(getClassName(studentId));
                        out.writeObject(getCoursesData());
                        out.writeObject(getClassData());
                    } else {
                        out.writeObject(null);
                    }
                } else if (response.equals("getStudentInfo")) {
                    List<Student> studentList = getStudentInfo();
                    logger.logQuery(clientId, "Fetching all student info");
                    out.writeObject(studentList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                logger.logDisconnection(clientId);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String handleLogin(String studentId, String password) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT * FROM students WHERE student_id = ? AND password = ?")) {

                preparedStatement.setString(1, studentId);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) return "true";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "false";
        }

        private String getFullName(String studentId) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT student_name FROM students WHERE student_id = ?")) {

                preparedStatement.setString(1, studentId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("student_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getClassName(String studentId) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT class_name FROM students WHERE student_id = ?")) {

                preparedStatement.setString(1, studentId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("class_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        private List<Course> getCoursesData() {
            List<Course> courses = new ArrayList<>();
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM courses");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Course course = new Course(
                            resultSet.getInt("course_id"),
                            resultSet.getString("course_name"),
                            resultSet.getInt("credits"),
                            resultSet.getString("class_code"),
                            resultSet.getInt("student_count"),
                            resultSet.getString("day_of_week"),
                            resultSet.getString("start_period"),
                            resultSet.getInt("total_periods"),
                            resultSet.getString("room"),
                            resultSet.getString("instructor"),
                            resultSet.getDate("start_date"),
                            resultSet.getDate("end_date")
                    );
                    courses.add(course);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return courses;
        }

        private List<Class> getClassData() {
            List<Class> classes = new ArrayList<>();
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Class");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Class cls = new Class(
                            resultSet.getString("class_code"),
                            resultSet.getString("class_name")
                    );
                    classes.add(cls);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return classes;
        }

        private List<Student> getStudentInfo() {
            List<Student> students = new ArrayList<>();
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT student_id, student_name, password, birth_date, class_name, major FROM students");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Student student = new Student(
                            resultSet.getString("student_id"),
                            resultSet.getString("student_name"),
                            resultSet.getString("password"),
                            resultSet.getDate("birth_date"),
                            resultSet.getString("class_name"),
                            resultSet.getString("major")
                    );
                    students.add(student);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return students;
        }
    }
}
