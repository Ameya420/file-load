import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import core.FileDownLoaderApacheHTTPImpl;
import models.FilePath;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static utils.Constants.ROOT_PATH;


public class HTTPMockTests {

    int port = 8989;
    String fileContent = "<response>Some content</response>";
    @Rule
    public WireMockRule wireMockRule;
    WireMockServer wireMockServer;

    @Before
    public void setUp() throws Exception {
        wireMockServer = new WireMockServer(wireMockConfig().port(port).bindAddress("localhost"));
        wireMockServer.start();
        wireMockRule = new WireMockRule(wireMockConfig().port(port).bindAddress("localhost"));
        WireMock.configureFor("localhost", port);

        stubFor(get(urlEqualTo("/my/resource"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody(fileContent)));
    }

    @Test
    public void simpleHTTPMockTest() throws Exception {
        FileDownLoaderApacheHTTPImpl fileDownLoaderApacheHTTP = new FileDownLoaderApacheHTTPImpl();
        String url = "http://localhost:"+ port +"/my/resource";
        FilePath filePath = new FilePath(url);
        filePath.setPort(port);
        boolean success = fileDownLoaderApacheHTTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), 600);
        assertEquals(success, true);
    }

    @Test
    public void testHTTPFileDownloadWrongName() throws Exception {
        FileDownLoaderApacheHTTPImpl fileDownLoaderApacheHTTP = new FileDownLoaderApacheHTTPImpl();
        String url = "http://localhost:"+ port +"/my/ressdsource";
        FilePath filePath = new FilePath(url);
        filePath.setPort(port);
        boolean success = fileDownLoaderApacheHTTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), 600);
        assertEquals(success, false);
    }

    @Test
    public void testFileContent() throws Exception {
        FileDownLoaderApacheHTTPImpl fileDownLoaderApacheHTTP = new FileDownLoaderApacheHTTPImpl();
        String url = "http://localhost:"+ port +"/my/resource";
        FilePath filePath = new FilePath(url);
        filePath.setPort(port);
        boolean success = fileDownLoaderApacheHTTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), 600);
        assertEquals(success, true);
        assertEquals(TestUtils.readFile(ROOT_PATH+filePath.getFilePathInServer()), fileContent);
    }

    @After
    public void tearDown() throws Exception {
        wireMockServer.stop();
    }
}
