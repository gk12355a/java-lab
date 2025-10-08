package com.bookstore.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> readLines(String filePath){
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null){
                if (!line.trim().isEmpty()){
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
    
    public static void writeLines(String filePath, List<String> lines){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (String line : lines){
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void appendLine(String filePath, String line){
        try (FileWriter fileWriter = new FileWriter(filePath, true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                PrintWriter out = new PrintWriter(writer)
                ){
                out.println(line);

            } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }
}
