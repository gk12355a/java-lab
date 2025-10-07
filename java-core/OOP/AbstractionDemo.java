package OOP;


// -------------------- Interface --------------------
interface Payment {
    void pay(double amount); // phương thức trừu tượng
}

// -------------------- Abstract Class --------------------
abstract class Employee {
    protected String name;
    protected double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // phương thức trừu tượng: lớp con phải override
    public abstract void calculateBonus();

    // phương thức bình thường
    public void displayInfo() {
        System.out.println("Nhân viên: " + name + " - Lương: " + salary);
    }
}

// -------------------- Lớp con 1 --------------------
class FullTimeEmployee extends Employee implements Payment {
    public FullTimeEmployee(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void calculateBonus() {
        double bonus = salary * 0.1;
        System.out.println("Thưởng nhân viên toàn thời gian: " + bonus);
    }

    @Override
    public void pay(double amount) {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Số tiền thanh toán không hợp lệ!");
            }
            System.out.println(name + " đã được trả " + amount + " VND");
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi thanh toán: " + e.getMessage());
        }
    }
}

// -------------------- Lớp con 2 --------------------
class PartTimeEmployee extends Employee implements Payment {
    public PartTimeEmployee(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void calculateBonus() {
        double bonus = salary * 0.05;
        System.out.println("Thưởng nhân viên bán thời gian: " + bonus);
    }

    @Override
    public void pay(double amount) {
        try {
            if (amount < 100) {
                throw new Exception("Thanh toán tối thiểu phải từ 100 VND!");
            }
            System.out.println(name + " đã được trả " + amount + " VND");
        } catch (Exception e) {
            System.out.println("Lỗi thanh toán: " + e.getMessage());
        }
    }
}

// -------------------- Main --------------------
public class AbstractionDemo {
    public static void main(String[] args) {
        // Tạo đối tượng
        Employee fullTime = new FullTimeEmployee("Nguyễn Văn A", 10000);
        Employee partTime = new PartTimeEmployee("Trần Thị B", 4000);

        // Hiển thị thông tin
        fullTime.displayInfo();
        fullTime.calculateBonus();

        partTime.displayInfo();
        partTime.calculateBonus();

        System.out.println("----- Thử nghiệm interface Payment -----");
        Payment pay1 = (Payment) fullTime;
        Payment pay2 = (Payment) partTime;

        // Gọi phương thức pay() với exception
        pay1.pay(8000);
        pay2.pay(50);   // sẽ gây exception
    }
}
