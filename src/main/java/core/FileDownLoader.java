package core;

import models.FilePath;

// Primary Interface for File Download functions
// Different implementation of FileDownLoader interface depending upon type of protocol, size of file etc.
public interface FileDownLoader {

    /**
     * Downloads file from remote location filePath to local location fileLocation.
     *
     * @param filePath Remote file location
     * @param fileLocation Local file destination where we intend to save file
     * @return true in case of success, false otherwise
     */
    boolean downloadFile(FilePath filePath, String fileLocation, long timeOutInMillis);
}
