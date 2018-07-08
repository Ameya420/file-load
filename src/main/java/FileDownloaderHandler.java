import core.FileDownLoaderApacheHTTPImpl;
import core.FileDownLoaderFTPImpl;
import models.FilePath;

public class FileDownloaderHandler {
    static FileDownLoaderApacheHTTPImpl fileDownLoaderApache = new FileDownLoaderApacheHTTPImpl();
    static FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();

    /**
     * Downloads files with HTTP protocol.
     *
     * @param fileDetails File details as String
     * @return true if file successfully downloaded, false otherwise
     */
    public boolean downLoadHTTPFiles(String fileDetails){
        FilePath filePath = new FilePath(fileDetails);
        return downLoadHTTPFiles(filePath);
    }

    /**
     * Downloads files with HTTP protocol.
     *
     * @param filePath File details in filePath object
     * @retur true if file successfully downloaded, false otherwise
     */
    private boolean downLoadHTTPFiles(FilePath filePath) {
        return fileDownLoaderApache.downloadFile(filePath, filePath.getFilePathInServer());
    }

    /**
     * Downloads files with FTP/SFTP protocol.
     *
     * @param fileDetails File details as String
     * @return true if file successfully downloaded, false otherwise
     */
    public boolean downLoadFTPFiles(String fileDetails){
        FilePath filePath = new FilePath(fileDetails);
        return downLoadFTPFiles(filePath);
    }

    /**
     * Downloads files with FTP/SFTP protocol.
     *
     * @param filePath File details in filePath object
     * @return true if file successfully downloaded, false otherwise
     */
    private boolean downLoadFTPFiles(FilePath filePath) {
        return fileDownLoaderFTP.downloadFile(filePath, filePath.getFilePathInServer());
    }

    private boolean downLoadAllFilesSeq(){
        return false;
    }
}
