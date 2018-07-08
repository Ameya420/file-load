package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDownLoadUtils {

    public static List<String> readFile(String fileName) {
        FileDownLoadUtils fileDownLoadUtils = new FileDownLoadUtils();
        List<String> results = new ArrayList<>();
        //Get file from resources folder
        ClassLoader classLoader = fileDownLoadUtils.getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                results.add(line);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }
}
