package core;

import models.FilePath;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDownLoaderFTPImpl implements FileDownLoader {
    private final Logger logger = LoggerFactory.getLogger(FileDownLoaderApacheHTTPImpl.class);

    @Override
    public boolean downloadFile(FilePath filePath, String fileLocation, long timeOutInMillis) {
        File file = new File(fileLocation);
        try {
            FTPClient ftpClient = new FTPClient();

            FileOutputStream dfile = new FileOutputStream(file);
            ftpClient.connect(filePath.getRootPath());
            if(filePath.getServerUserName()!=null) ftpClient.user(filePath.getServerUserName());
            if(filePath.getServerPassword()!=null) ftpClient.pass(filePath.getServerPassword());
            ftpClient.retrieveFile(filePath.getFilePathInServer(), dfile);
            dfile.close();
            ftpClient.disconnect();
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
}
