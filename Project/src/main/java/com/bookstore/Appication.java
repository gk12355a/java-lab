package com.bookstore;

import com.bookstore.entity.Fine;
import com.bookstore.service.FineService;
import com.bookstore.util.InputUtil;
import com.bookstore.util.MenuOptions;

public class Appication {

    public static void main(String[] args) {
        FineService fineService = new FineService();
        while (true) {
            System.out.println("=== Library Fine Management ===");
            System.out.println("1. Manage Fines");
            System.out.println("0. Exit");
            int choice = InputUtil.getInt("Choose an option: ");
            switch (choice) {
                case MenuOptions.MENU_FINE_MANAGEMENT -> fineMenu(fineService);
                case MenuOptions.MENU_EXIT -> {
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

    }
    private static void fineMenu(FineService fineService) {
        while (true) {
            System.out.println("\n--- QUẢN LÝ PHẠT ---");
            System.out.println("1. Thêm phạt mới");
            System.out.println("2. Thanh toán phạt");
            System.out.println("3. Tìm kiếm phạt");
            System.out.println("4. Hiển thị tất cả phạt");
            System.out.println("5. Báo cáo thu phạt");
            System.out.println("0. Quay lại");
            int choice = InputUtil.getInt("Chọn chức năng: ");

            switch (choice) {
                case MenuOptions.FINE_ADD -> fineService.addFine();
                case MenuOptions.FINE_PAY -> fineService.payFine();
                case MenuOptions.FINE_SEARCH -> fineService.searchFine();
                case MenuOptions.FINE_VIEW_ALL -> fineService.displayAllFines();
                case MenuOptions.FINE_GENERATE_REPORT -> fineService.generateFineReport();
                case MenuOptions.FINE_BACK -> {
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }




}
