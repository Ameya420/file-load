import models.FilePath;
import org.junit.Test;
import utils.FileDownLoadUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ParellelDownLoadTest {
    FileDownloaderHandler fileDownloaderHandler = new FileDownloaderHandler();

    @Test
    public void simpleParallelDownLatest() throws Exception {
        List<String> filePathStrings = FileDownLoadUtils.readFile("filenames");
        List<FilePath> filePaths = filePathStrings.stream().map(FilePath::new).collect(Collectors.toList());
        boolean success = fileDownloaderHandler.downLoadAllFilesParallel(filePaths);
        assertEquals(success, true);
    }

    @Test
    public void simpleSeqDownLatest() throws Exception {
        List<String> filePathStrings = FileDownLoadUtils.readFile("filenames");
        List<FilePath> filePaths = filePathStrings.stream().map(FilePath::new).collect(Collectors.toList());
        boolean success = fileDownloaderHandler.downLoadAllFilesSeq(filePaths);
        assertEquals(success, true);
    }
}
