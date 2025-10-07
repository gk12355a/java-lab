package OOP;
class InvalidDataException extends Exception{
    public InvalidDataException(String message) {
        super(message);
    }
}
//class Student {
//    private String studentID;
//    private String studentName;
//    private int age;
//    private double gpa;
//
//    public Student(String studentID, String studentName, int age, double gpa) throws InvalidDataException {
//        this.studentID = studentID;
//        this.studentName = studentName;
//        this.age = age;
//        this.gpa = gpa;
//    }
//
//    public String getStudentID() {
//        return studentID;
//    }
//
//    public void setStudentID(String studentID) throws  InvalidDataException {
//        if (studentID == null || studentID.isEmpty()) {
//            throw new InvalidDataException("Student ID cannot be null or empty");
//        }
//        this.studentID = studentID;
//    }
//
//    public String getStudentName() {
//        return studentName;
//    }
//
//    public void setStudentName(String studentName) throws InvalidDataException {
//        if (studentName == null || studentName.isEmpty()) {
//            throw new InvalidDataException("Student Name cannot be null or empty");
//        }
//        this.studentName = studentName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) throws InvalidDataException {
//        if (age < 0 || age > 150) {
//            throw new InvalidDataException("Age must be between 0 and 150");
//        }
//        this.age = age;
//    }
//
//    public double getGpa() {
//        return gpa;
//    }
//
//    public void setGpa(double gpa) throws InvalidDataException {
//        if (gpa < 0.0 || gpa > 4.0) {
//            throw new InvalidDataException("GPA must be between 0.0 and 4.0");
//        }
//        this.gpa = gpa;
//    }
//
//    @Override
//    public String toString() {
//        return "Student{" +
//                "studentID='" + studentID + '\'' +
//                ", studentName='" + studentName + '\'' +
//                ", age=" + age +
//                ", gpa=" + gpa +
//                '}';
//    }
//}
//public class EncapsulationWithExceptionDemo {
//    public static void main(String[] args) {
//        try {
//            Student student1 = new Student("S001", "Alice", 20, 3.8);
//            System.out.println(student1);
//            student1.setGpa(4.5);
//        } catch (InvalidDataException e) {
//            System.err.println("Error creating student: " + e.getMessage());
//
//        } finally {
//            System.out.println("Execution completed.");
//        }
//    }
//}
