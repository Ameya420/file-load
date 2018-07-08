package core;

import models.FilePath;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileDownLoaderApacheHTTPImpl implements FileDownLoader {
    @Override
    public boolean downloadFile(FilePath filePath, String fileLocation) {
        File file = new File(fileLocation);
        try {
            FileUtils.copyURLToFile(filePath.getUrl(), file);
            System.out.println("Done with " + filePath.getFileName());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
