package com.bookstore.service;

import com.bookstore.entity.Fine;
import com.bookstore.util.FileUtil;
import com.bookstore.util.InputUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FineService {
    private static final String DATA_FILE = "Project/src/main/resources/fine.txt";
    private static final String HISTORY_FILE = "Project/src/main/resources/fine_history.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final double DEFAULT_FINE_RATE = 10000.0;

    private final List<Fine> fines;
    private int nextFineId;

    // Constructor
    public FineService() {
        this.fines = new ArrayList<>();
        this.nextFineId = 1;
        loadFinesFromFile();
    }

    private void loadFinesFromFile() {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                readFinesFromFile(DATA_FILE);
            } else {
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    System.err.println("Failed to create directory: " + file.getParentFile().getAbsolutePath());
                    return;
                }
                saveFinesToFile();
            }
        }catch (Exception e){
            System.err.println("Error loading fines from file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void readFinesFromFile(String filePath) {

        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Data file path cannot be null or empty");
        }

        try {
            List<String> lines = FileUtil.readLines(filePath);
            fines.clear();
            for (String line : lines){
                if (line == null || line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split("\\|");
                if (parts.length < 8){
                    System.err.println("Invalid line format: " + line);
                    continue; // Skip invalid lines
                }
                double amount;
                try {
                    amount = Double.parseDouble(parts[4]);
                    if (amount < 0){
                        throw new NumberFormatException("Amount cannot be negative");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid amount format in line: " + line);
                    continue; // Skip lines with invalid amount
                }
                Date fineDate;
                try {
                    fineDate = DATE_FORMAT.parse(parts[5]);
                } catch (Exception e) {
                    System.err.println("Invalid date format in line: " + line);
                    continue; // Skip lines with invalid date
                }
                Fine fine = new Fine(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        amount,
                        fineDate,
                        parts[6],
                        parts[7].isEmpty() ? null : parts[7]
                );
                fines.add(fine);
            }
            updateNextFineId();
        } catch (Exception e) {
            System.err.println("Error reading fines from file: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }



    private void saveFinesToFile() {
        try {
            List<String> lines = fines.stream().filter(Objects::nonNull).map(Fine::toString).collect(Collectors.toList());
            FileUtil.writeLines(DATA_FILE, lines);
        } catch (Exception e) {
            System.err.println("Error saving fines to file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void updateNextFineId() {
        nextFineId = fines.stream()
                .mapToInt(f -> Integer.parseInt(f.getFineId().substring(1)))
                .max()
                .orElse(0) + 1;
    }

    private String generateNextFineId() {
        return String.format("F%03d",nextFineId++);
    }

    public double calculateFine(int daysLate){
        return daysLate > 0 ? daysLate * DEFAULT_FINE_RATE : 0;
    }

    public void addFine() {
        try {
            System.out.println("Them phat");
            String fineId = generateNextFineId();
            System.out.println("Ma phat "+ fineId);
            String readerId = InputUtil.getNonEmptyString("Nhap ma doc gia: ");
            String borrowingId = InputUtil.getNonEmptyString("Nhap ma muon sach: ");
            String reason = InputUtil.getNonEmptyString("Nhap ly do phat: ");
            if (reason.length() > 255) {
                System.out.println("Ly do phat khong duoc vuot qua 255 ky tu.");
                return;
            }
            int daysLate;
            try {
                daysLate = InputUtil.getInt("Nhap so ngay muon: ");
                if (daysLate < 0)  {
                    System.out.println("So ngay muon khong duoc am.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("So ngay muon khong hop le.");
                return;
            }
            double amount;
            if (daysLate > 0) {
                amount = calculateFine(daysLate);
                System.out.printf("Số tiền phạt: %,.0f VND (%,d ngày x %,.0f VND/ngày)%n",
                        amount, daysLate, DEFAULT_FINE_RATE);
            } else {
                try {
                    amount = InputUtil.getDouble("Nhập số tiền phạt: ");
                    if (amount < 0) {
                        System.out.println("Lỗi: Số tiền phạt không được âm");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Số tiền phạt không hợp lệ");
                    return;
                }
            }
            Date fineDate = new Date(); // Ngày hiện tại
            String paymentStatus = "Chưa thanh toán";

            String note = InputUtil.getString("Nhập ghi chú (tùy chọn): ");
            if (note != null && note.length() > 200) {
                note = note.substring(0, 197) + "...";
            }

            Fine fine = new Fine(fineId, readerId, borrowingId, reason, amount, fineDate, paymentStatus, note);
            fines.add(fine);
            saveFinesToFile();
            System.out.println("Phat da duoc them thanh cong: " + fine);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  void payFine(){
        try {
            String fineId = InputUtil.getNonEmptyString("Nhap ma phat can thanh toan: ");
            Fine existingFine = findFineById(fineId);
            if (existingFine == null) {
                System.out.println("Khong tim thay phat voi ma: " + fineId);
                return;
            }
            if ("Đã thanh toán".equalsIgnoreCase(existingFine.getPaymentStatus())) {
                System.out.println("Phat da duoc thanh toan truoc do: " + existingFine);
                return;
            }
            try {
                existingFine.setPaymentStatus("Đã thanh toán");
                saveFinesToFile();
                logPaymentHistory(existingFine);
                System.out.println("Phat da duoc thanh toan thanh cong: " + existingFine);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Fine findFineById(String fineId) {
        return fines.stream().filter(fine -> fine.getFineId().equals(fineId)).findFirst().orElse(null);
    }

    private void logPaymentHistory(Fine fine) {
        try {
            if (fine == null) {
                throw new IllegalArgumentException("Fine cannot be null");
            }
            File historyFile = new File(HISTORY_FILE);
            if (!historyFile.exists()) {
                if (!historyFile.getParentFile().exists() && !historyFile.getParentFile().mkdirs()) {
                    System.err.println("Failed to create directory: " + historyFile.getParentFile().getAbsolutePath());
                    return;
                }
                if (!historyFile.createNewFile()) {
                    System.err.println("Failed to create history file: " + HISTORY_FILE);
                    return;
                }
            }
            String logEntry = String.format("%s|%s|%s|%s|%.0f|%s|%s",
                    fine.getFineId(),
                    fine.getReaderId(),
                    fine.getBorrowingId(),
                    fine.getReason(),
                    fine.getAmount(),
                    DATE_FORMAT.format(new Date()),
                    "Đã thanh toán");
            FileUtil.appendLine(HISTORY_FILE, logEntry);
        } catch (Exception e) {
            System.err.println("Error logging payment history: " + e.getMessage());
        }
    }

    public void searchFine() {
        System.out.println("Tim kiem phat");
        System.out.println("1. Tim kiem theo ma phat");
        System.out.println("2. Tim kiem theo ma doc gia");
        System.out.println("3. Tim kiem theo ma muon");
        int choice = InputUtil.getInt("Chon phuong thuc tim kiem (1-3): ");
        List<Fine> results = new ArrayList<>();

        switch (choice) {
            case 1:
                String fineId = InputUtil.getNonEmptyString("Nhap ma phat: ");
                results = fines.stream()
                        .filter(f -> f.getFineId().equalsIgnoreCase(fineId))
                        .collect(Collectors.toList());
                break;
            case 2:
                String readerId = InputUtil.getNonEmptyString("Nhap ma doc gia: ");
                results = fines.stream()
                        .filter(f -> f.getReaderId().equalsIgnoreCase(readerId))
                        .collect(Collectors.toList());
                break;
            case 3:
                String borrowingId = InputUtil.getNonEmptyString("Nhap ma muon: ");
                results = fines.stream()
                        .filter(f -> f.getBorrowingId().equalsIgnoreCase(borrowingId))
                        .collect(Collectors.toList());
                break;
            default:
                System.out.println("Lua chon khong hop le.");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("Khong tim thay phat.");
        } else {
            System.out.println("Ket qua tim kiem:");
            results.forEach(System.out::println);
        }
    }

    public void generateFineReport() {
        System.out.println("Bao cao phat");

        Date startDate = InputUtil.getDate("Nhap ngay bat dau (yyyy-MM-dd): ");
        Date endDate = InputUtil.getDate("Nhap ngay ket thuc (yyyy-MM-dd): ");
        String filePath = InputUtil.getNonEmptyString("Nhap duong dan luu bao cao (vd: report.txt): ");

        List<Fine> reportFines = fines.stream()
                .filter(f -> !f.getFineDate().before(startDate) && !f.getFineDate().after(endDate))
                .toList();

        List<String> lines = new ArrayList<>();
        lines.add("Bao cao phat tu " + DATE_FORMAT.format(startDate) + " den " + DATE_FORMAT.format(endDate));
        lines.add("--------------------------------------------------");
        double total = 0;
        for (Fine fine : reportFines) {
            String row = String.format("%s|%s|%s|%s|%.0f|%s|%s|%s",
                    fine.getFineId(),
                    fine.getReaderId(),
                    fine.getBorrowingId(),
                    fine.getReason(),
                    fine.getAmount(),
                    DATE_FORMAT.format(fine.getFineDate()),
                    fine.getPaymentStatus(),
                    fine.getNote() != null ? fine.getNote() : "");
            lines.add(row);
            total += fine.getAmount();
        }

        lines.add("--------------------------------------------------");
        lines.add(String.format("Tong so phat: %d, so tien phat %.0f", reportFines.size(), total ));
        try {
            FileUtil.writeLines(filePath, lines);
            System.out.println("Bao cao da duoc luu vao: " + filePath);
        } catch (Exception e) {
            System.err.println("Error generating fine report: " + e.getMessage());
        }
    }

    public void displayAllFines(){
        if (fines.isEmpty()) {
            System.out.println("Không có dữ liệu phạt.");
            return;
        }

        System.out.println("\n===== DANH SÁCH PHẠT =====");
        System.out.printf("%-6s %-10s %-10s %-20s %15s %-12s %-15s %s%n",
                "Mã", "Mã ĐG", "Mã Mượn", "Lý do", "Số tiền", "Ngày", "Trạng thái", "Ghi chú");
        System.out.println("-".repeat(110));

        double total = 0;
        for (Fine fine : fines) {
            System.out.printf("%-6s %-10s %-10s %-20s %,15.0f %-12s %-15s %s%n",
                    fine.getFineId(),
                    fine.getReaderId(),
                    fine.getBorrowingId(),
                    fine.getReason().length() > 18 ? fine.getReason().substring(0, 15) + "..." : fine.getReason(),
                    fine.getAmount(),
                    DATE_FORMAT.format(fine.getFineDate()),
                    fine.getPaymentStatus(),
                    fine.getNote().length() > 20 ? fine.getNote().substring(0, 17) + "..." : fine.getNote());
            total += fine.getAmount();
        }

        System.out.println("-".repeat(110));
        System.out.printf("Tổng cộng: %,d khoản phạt | Tổng tiền: %,15.0f VND%n", fines.size(), total);
    }



}
