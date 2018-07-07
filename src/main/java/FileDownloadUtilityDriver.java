import core.FileDownLoaderApacheHTTPImpl;
import core.FileDownLoaderFTPImpl;
import models.FilePath;
import models.Protocol;
import utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileDownloadUtilityDriver {

    static FileDownLoaderApacheHTTPImpl fileDownLoaderApache = new FileDownLoaderApacheHTTPImpl();
    static FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();

    public static void main(String[] args) throws Exception{
        FileDownloadUtilityDriver obj = new FileDownloadUtilityDriver();
        List<String> filePathStrings = obj.readFile("filenames");
        List<FilePath> filePaths = filePathStrings.stream().map(filePath-> new FilePath(filePath)).collect(Collectors.toList());
        filePaths.forEach(filePath -> {
            if(filePath.getProtocol()== Protocol.HTTP || filePath.getProtocol()== Protocol.HTTPS){
                fileDownLoaderApache.downloadFile(filePath, Constants.ROOT_PATH + filePath.getFileName());
            } else if(filePath.getProtocol() == Protocol.FTP){
                fileDownLoaderFTP.downloadFile(filePath, Constants.ROOT_PATH + filePath.getFileName());
            }
        });
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
