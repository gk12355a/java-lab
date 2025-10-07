package Exception;
import java.io.*;

public class AllExceptionDemo {

    // 1️ IllegalArgumentException
    public static int getRandomInt(int low, int high) {
        if (low > high) {
            throw new IllegalArgumentException(
                "low phải <= high (low=" + low + ", high=" + high + ")"
            );
        }
        return low + (int) (Math.random() * (high - low + 1));
    }

    // 2️ NullPointerException
    public static void printStringLength(String text) {
        if (text == null) {
            throw new NullPointerException("Đối tượng String bị null!");
        }
        System.out.println("Độ dài chuỗi: " + text.length());
    }

    // 3️ & 5️ IOException + FileNotFoundException
    public static void readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Không tìm thấy file: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("Nội dung file:");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    // 4️ ArithmeticException
    public static int divide(int a, int b) {
        return a / b; // Nếu b = 0 → ArithmeticException
    }

    // 6️ NumberFormatException
    public static int convertToInt(String value) {
        return Integer.parseInt(value); // Nếu value không phải số → NumberFormatException
    }

    // 💡 MAIN DEMO
    public static void main(String[] args) {
        System.out.println("=== Demo Tất Cả Các Loại Ngoại Lệ ===");

        // 1️ IllegalArgumentException
        try {
            System.out.println("\n[1] IllegalArgumentException demo:");
            getRandomInt(10, 5);
        } catch (IllegalArgumentException e) {
            System.err.println("⚠️ Lỗi: " + e.getMessage());
        }

        // 2️ NullPointerException
        try {
            System.out.println("\n[2] NullPointerException demo:");
            printStringLength("null");
        } catch (NullPointerException e) {
            System.err.println("⚠️ Lỗi: " + e.getMessage());
        }

        // 3️ IOException + 5️ FileNotFoundException
        try {
            System.out.println("\n[3] IOException / FileNotFoundException demo:");
            readFile("data/reader.txt"); // Cố tình đọc file không tồn tại
        } catch (FileNotFoundException e) {
            System.err.println("⚠️ FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("⚠️ IOException: " + e.getMessage());
        }

        // 4️ ArithmeticException
        try {
            System.out.println("\n[4] ArithmeticException demo:");
            int result = divide(10, 0);
            System.out.println("Kết quả chia: " + result);
        } catch (ArithmeticException e) {
            System.err.println("⚠️ Lỗi toán học: " + e.getMessage());
        }

        // 6️ NumberFormatException
        try {
            System.out.println("\n[5] NumberFormatException demo:");
            int num = convertToInt("abc");
            System.out.println("Giá trị parse: " + num);
        } catch (NumberFormatException e) {
            System.err.println("⚠️ NumberFormatException: " + e.getMessage());
        }

        System.out.println("\n=== Kết thúc chương trình an toàn ===");
    }
}
