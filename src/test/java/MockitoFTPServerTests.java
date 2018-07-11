import core.FileDownLoaderFTPImpl;
import models.FilePath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static utils.Constants.ROOT_PATH;

public class MockitoFTPServerTests {
    FakeFtpServer fakeFtpServer;
    int port = 8989;
    String fileContent = "This is file \n This contain something";
    @Before
    public void setUp() throws Exception {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(port);
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "c:\\data"));
        FileSystem fileSystem = new WindowsFakeFileSystem();
        fakeFtpServer.setFileSystem(fileSystem);
        FileSystemEntry fileSystemEntry = new FileEntry("c:\\data\\f1",fileContent);
        fakeFtpServer.getFileSystem().add(fileSystemEntry);
        fakeFtpServer.start();
    }

    @Test
    public void testFtpFileDownload() throws Exception {
        FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();
        String url = "ftp://localhost/f1`user`password";
        FilePath filePath = new FilePath(url);
        filePath.setPort(port);
        boolean success = fileDownLoaderFTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), 600);
        assertEquals(success, true);
    }

    @Test
    public void testFtpFileDownloadWrongServerName() throws Exception {
        FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();
        String url = "ftp://localhosts/f1`user`password";
        FilePath filePath = new FilePath(url);
        filePath.setPort(port);
        boolean success = fileDownLoaderFTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), 600);
        assertEquals(success, false);
    }

    @Test
    public void testFileContent() throws Exception {
        FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();
        String url = "ftp://localhost/f1`user`password";
        FilePath filePath = new FilePath(url);
        filePath.setPort(port);
        boolean success = fileDownLoaderFTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), 600);
        assertEquals(success, true);
        assertEquals(TestUtils.readFile(ROOT_PATH+filePath.getFilePathInServer()), fileContent);
    }

    @Test
    public void testFileContentWrong() throws Exception {
        FileDownLoaderFTPImpl fileDownLoaderFTP = new FileDownLoaderFTPImpl();
        String url = "ftp://localhost/f1`user`password";
        FilePath filePath = new FilePath(url);
        filePath.setPort(port);
        boolean success = fileDownLoaderFTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), 600);
        assertEquals(success, true);
        assertNotEquals(TestUtils.readFile(ROOT_PATH+filePath.getFilePathInServer()), fileContent+" ");
    }

    @After
    public void tearDown() throws Exception {
        fakeFtpServer.stop();
    }
}
