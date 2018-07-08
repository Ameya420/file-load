package core;

import models.FilePath;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDownLoaderFTPLargeFilesImpl implements FileDownLoader {
    FTPClient ftpClient = new FTPClient();

    @Override
    public boolean downloadFile(FilePath filePath, String fileLocation) {

        try {
            ftpClient.connect(filePath.getRootPath());
            if(filePath.getServerUserName()!=null) ftpClient.user(filePath.getServerUserName());
            if(filePath.getServerPassword()!=null) ftpClient.pass(filePath.getServerPassword());
            FTPFile ftpFile = ftpClient.mlistFile(filePath.getFilePathInServer());
            if(ftpFile==null) return false;
            else if(ftpFile.getSize()/Constants.ONE_KB_IN_BYTES<Constants.MAX_CHUNK_SIZE_IN_KB){
                File file = new File(fileLocation);
                FileOutputStream dfile = new FileOutputStream(file);
                ftpClient.retrieveFile(filePath.getFilePathInServer(), dfile);
                dfile.close();
                ftpClient.disconnect();
            } else {

            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
