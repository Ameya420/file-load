import models.FilePath;
import models.Protocol;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilePathTest {

    @Test
    public void simpleUrlParseTest() throws Exception {
        FilePath filePath = new FilePath("https://github.com/github/choosealicense.com/blob/gh-pages/_data/rules.yml");
        assertEquals(filePath.getProtocol(), Protocol.HTTPS);
        assertEquals(filePath.getRootPath(), "github.com");
        assertEquals(filePath.getServerPassword(), null);
        assertEquals(filePath.getServerUserName(), null);
        assertEquals(filePath.getUrl().toString(), "https://github.com/github/choosealicense.com/blob/gh-pages/_data/rules.yml");
        assertEquals(filePath.getUrlString(), "https://github.com/github/choosealicense.com/blob/gh-pages/_data/rules.yml");
        assertEquals(filePath.getFileName(), "rules.yml");
        assertEquals(filePath.getFilePathInServer(), "/github/choosealicense.com/blob/gh-pages/_data/rules.yml");
    }

    @Test
    public void simpleUrlWithUserPassParseTest() throws Exception {
        String url = "ftp://ftp.dlptest.com/.ftpquota`dlpuser@dlptest.com`3D6XZV9MKdhM5fF";
        FilePath filePath = new FilePath(url);
        assertEquals(filePath.getProtocol(), Protocol.FTP);
        assertEquals(filePath.getRootPath(), "ftp.dlptest.com");
        assertEquals(filePath.getServerUserName(), "dlpuser@dlptest.com");
        assertEquals(filePath.getServerPassword(), "3D6XZV9MKdhM5fF");
        assertEquals(filePath.getUrl().toString(), "ftp://ftp.dlptest.com/.ftpquota");
        assertEquals(filePath.getUrlString(), "ftp://ftp.dlptest.com/.ftpquota");
        assertEquals(filePath.getFileName(), ".ftpquota");
        assertEquals(filePath.getFilePathInServer(), "/.ftpquota");
    }
}
