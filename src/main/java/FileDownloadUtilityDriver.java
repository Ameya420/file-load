import core.FileDownLoaderApacheHTTPImpl;
import models.FilePath;
import utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileDownloadUtilityDriver {

    static FileDownLoaderApacheHTTPImpl fileDownLoaderApache = new FileDownLoaderApacheHTTPImpl();

    public static void main(String[] args) throws Exception{
        FileDownloadUtilityDriver obj = new FileDownloadUtilityDriver();
        List<String> filePathStrings = obj.readFile("filenames");
        List<FilePath> filePaths = filePathStrings.stream().map(filePath-> new FilePath(filePath)).collect(Collectors.toList());
        filePaths.forEach(filePath -> System.out.print(filePath));
        filePaths.forEach(filePath -> fileDownLoaderApache.downloadFile(filePath, Constants.ROOT_PATH + filePath.toString().hashCode()));
    }

    private List<String> readFile(String fileName) {
        List<String> results = new ArrayList<>();

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
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
