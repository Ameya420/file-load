package core;

import models.FilePath;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileDownLoaderApacheHTTPImpl implements FileDownLoader {
    private final Logger logger = LoggerFactory.getLogger(FileDownLoaderApacheHTTPImpl.class);

    @Override
    public boolean downloadFile(FilePath filePath, String fileLocation, long timeOutInMillis) {

        File file = new File(fileLocation);
        try {
            FileUtils.copyURLToFile(filePath.getUrl(), file, (int) timeOutInMillis, (int) timeOutInMillis);
            logger.info("Done with " + filePath.getFileName());
            return true;
        } catch (IOException e) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e1) {
                logger.error("Unable to delete file " + file.getAbsolutePath(), e1);
            }
            logger.error("Unable to download file " + file.getAbsolutePath(), e);
            return false;
        }
    }

    @Override
    public boolean downloadFile(FilePath filePath, String fileLocation, long timeOutInMillis, int numberOfRetries) {
        int retryCount = 1;
        while (retryCount<=numberOfRetries){
            if(!downloadFile(filePath, fileLocation, timeOutInMillis)){
                retryCount++;
            } else return true;
        }
        return false;
    }
}
