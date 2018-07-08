import core.FileDownLoaderApacheHTTPImpl;
import core.FileDownLoaderFTPImpl;
import models.FilePath;
import models.Protocol;
import utils.Constants;
import utils.FileDownLoadUtils;

import java.util.List;
import java.util.stream.Collectors;

public class FileDownloadUtilityDriver {

    static FileDownLoaderApacheHTTPImpl fileDownLoaderApache = new FileDownLoaderApacheHTTPImpl();
    static FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();

    public static void main(String[] args) throws Exception{
        List<String> filePathStrings = FileDownLoadUtils.readFile("filenames");
        List<FilePath> filePaths = filePathStrings.stream().map(
                filePath-> new FilePath(filePath)).collect(Collectors.toList());
        filePaths.forEach(filePath -> {
            if(filePath.getProtocol()== Protocol.HTTP || filePath.getProtocol()== Protocol.HTTPS){
                fileDownLoaderApache.downloadFile(
                        filePath, Constants.ROOT_PATH + filePath.getFileName(), Constants.DEFAULT_TIMEOUT_TIME);
            } else if(filePath.getProtocol() == Protocol.FTP || filePath.getProtocol() == Protocol.SFTP){
                fileDownLoaderFTP.downloadFile(
                        filePath, Constants.ROOT_PATH + filePath.getFileName(), Constants.DEFAULT_TIMEOUT_TIME);
            }
        });
    }
}
