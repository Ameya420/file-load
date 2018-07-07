package core;

import models.FilePath;

public interface FileDownLoader {

    boolean downloadFile(FilePath filePath, String fileLocation);
}
