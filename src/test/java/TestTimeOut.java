import core.FileDownLoaderFTPImpl;
import models.FilePath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utils.Constants.ROOT_PATH;

public class TestTimeOut {

    @Test public void testFTPTimeOut() throws Exception {
        String url = "ftp://ftp.dlptest.com/.ftpquota`dlpuser@dlptest.com`3D6XZV9MKdhM5fF";
        FilePath filePath = new FilePath(url);
        FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();
        boolean success =
                fileDownLoaderFTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(),60*1000);
        assertEquals(success, true);
    }

    @Test public void testFTPTimeOut2() throws Exception {
        String url = "ftp://ftp.dlptest.com/.ftpquota`dlpuser@dlptest.com`3D6XZV9MKdhM5fF";
        FilePath filePath = new FilePath(url);
        FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();
        boolean success =
                fileDownLoaderFTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(),1);
        assertEquals(success, false);
    }
}

