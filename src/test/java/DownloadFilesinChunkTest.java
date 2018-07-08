import core.FileDownLoaderFTPLargeFilesImpl;
import models.FilePath;
import org.junit.Test;

public class DownloadFilesinChunkTest {

    FileDownLoaderFTPLargeFilesImpl fileDownLoaderFTPLargeFiles = new FileDownLoaderFTPLargeFilesImpl();

    @Test
    public void simpleTest() throws Exception {
        String url = "ftp://ftp.dlptest.com/.ftpquota`dlpuser@dlptest.com`3D6XZV9MKdhM5fF";
        FilePath filePath = new FilePath(url);
        fileDownLoaderFTPLargeFiles.downloadFile(filePath, filePath.getFileName());
    }

    @Test
    public void simpleFTPTest() throws Exception {
        String url = "ftp://speedtest.tele2.net/1KB.zip";
        FilePath filePath = new FilePath(url);
        fileDownLoaderFTPLargeFiles.downloadFile(filePath, filePath.getFileName());
    }


}
