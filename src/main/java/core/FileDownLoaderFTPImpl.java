package core;

import models.FilePath;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDownLoaderFTPImpl implements FileDownLoader {
    FTPClient ftpClient = new FTPClient();
    @Override
    public boolean downloadFile(FilePath filePath, String fileLocation) {
        try {
            File file = new File(fileLocation);
            FileOutputStream dfile = new FileOutputStream(file);

            ftpClient.connect(filePath.getRootPath());
            ftpClient.user(filePath.getServerUserName());
            ftpClient.pass(filePath.getServerPassword());
            ftpClient.retrieveFile(filePath.getFilePathInServer(), dfile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
