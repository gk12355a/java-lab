package OOP;

import OOP.InvalidDataException;
// 🧩 Lớp cha: Person
class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) throws InvalidDataException {
        setName(name);
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidDataException {
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("❌ Tên không được để trống!");
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws InvalidDataException {
        if (age < 0 || age > 120) {
            throw new InvalidDataException("❌ Tuổi phải nằm trong khoảng 0–120!");
        }
        this.age = age;
    }

    public void displayInfo() {
        System.out.println("Họ tên: " + name);
        System.out.println("Tuổi: " + age);
    }
}

// 🎓 Lớp con: Student kế thừa từ Person
class Student extends Person {
    private String studentId;
    private double gpa;

    public Student(String name, int age, String studentId, double gpa) throws InvalidDataException {
        // Gọi constructor lớp cha bằng super()
        super(name, age);
        setStudentId(studentId);
        setGpa(gpa);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) throws InvalidDataException {
        if (studentId == null || studentId.isEmpty()) {
            throw new InvalidDataException("❌ Mã sinh viên không được để trống!");
        }
        this.studentId = studentId;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) throws InvalidDataException {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new InvalidDataException("❌ GPA phải trong khoảng 0.0 – 4.0!");
        }
        this.gpa = gpa;
    }

    // Ghi đè phương thức của lớp cha (Overriding)
    @Override
    public void displayInfo() {
        System.out.println("=== Thông tin sinh viên ===");
        super.displayInfo(); // Gọi lại phương thức lớp cha
        System.out.println("Mã SV: " + studentId);
        System.out.println("GPA: " + gpa);
        System.out.println("===========================\n");
    }
}
// 👨‍🏫 Lớp con khác: Teacher kế thừa từ Person
class Teacher extends Person {
    private String subject;

    public Teacher(String name, int age, String subject) throws InvalidDataException {
        super(name, age);
        setSubject(subject);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) throws InvalidDataException {
        if (subject == null || subject.isEmpty()) {
            throw new InvalidDataException("❌ Môn giảng dạy không được để trống!");
        }
        this.subject = subject;
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Thông tin giảng viên ===");
        super.displayInfo();
        System.out.println("Môn giảng dạy: " + subject);
        System.out.println("============================\n");
    }
}
public class InheritanceDemo {
    public static void main(String[] args) {
        try {
            // Tạo đối tượng Student (lớp con)
            Student s1 = new Student("Nguyen Van A", 20, "SV001", 3.7);
            s1.displayInfo();

            // Tạo đối tượng Teacher (lớp con khác)
            Teacher t1 = new Teacher("Tran Thi B", 35, "Lập trình Java");
            t1.displayInfo();

            // ⚠️ Gây lỗi để test exception
            Student s2 = new Student("Le Van C", 19, "", 3.2);

        } catch (InvalidDataException e) {
            System.out.println("⚠️ Lỗi dữ liệu: " + e.getMessage());
        } finally {
            System.out.println("✅ Chương trình kết thúc an toàn!");
        }
    }
}
