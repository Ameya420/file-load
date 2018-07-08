import core.FileDownLoaderApacheHTTPImpl;
import core.FileDownLoaderFTPImpl;
import models.FilePath;
import models.Protocol;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static utils.Constants.ROOT_PATH;

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
        return downLoadHTTPFiles(filePath, Constants.DEFAULT_TIMEOUT_TIME);
    }

    /**
     * Downloads files with HTTP protocol.
     *
     * @param filePath File details in filePath object
     * @retur true if file successfully downloaded, false otherwise
     */
    private boolean downLoadHTTPFiles(FilePath filePath, long timeOutInMillis) {
        return fileDownLoaderApache.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), timeOutInMillis);
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
     * @param timeInMillis timeout limit
     * @return true if file successfully downloaded, false otherwise
     */
    private boolean downLoadFTPFiles(FilePath filePath, Long timeInMillis) {
        return fileDownLoaderFTP.downloadFile(filePath, ROOT_PATH+filePath.getFilePathInServer(), timeInMillis);
    }

    /**
     * Downloads files with FTP/SFTP protocol.
     *
     * @param filePath File details in filePath object
     * @return true if file successfully downloaded, false otherwise
     */
    private boolean downLoadFTPFiles(FilePath filePath) {
        return downLoadFTPFiles(filePath, Constants.DEFAULT_TIMEOUT_TIME);
    }

    /**
     * Download all files using thread-pool of size NUM_OF_PARALLEL_STREAMS.
     *
     * @param filePaths List of all files to be downloaded
     * @return true if all files are downloaded successfully
     * @throws InterruptedException
     */
    public boolean downLoadAllFilesParallel(List<FilePath> filePaths) throws InterruptedException {
        return downLoadAllFilesParallel(filePaths, Constants.NUM_OF_PARALLEL_STREAMS);
    }

    public boolean downLoadAllFilesParallel(
            List<FilePath> filePaths, int numOfParallelStreams) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(numOfParallelStreams);

        List<Callable<Boolean>> callables = new ArrayList<>();
        for (FilePath filePath: filePaths){
            callables.add(()-> {
                if(filePath.getProtocol()== Protocol.HTTP || filePath.getProtocol()==Protocol.HTTPS){
                    return downLoadHTTPFiles(filePath);
                } else if(filePath.getProtocol()==Protocol.FTP || filePath.getProtocol()==Protocol.SFTP) {
                    return downLoadFTPFiles(filePath);
                } else return false;
            });
        }

        List<Boolean> lst = executor.invokeAll(callables).stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }).collect(Collectors.toList());
        return lst.parallelStream().anyMatch(e->e);
    }

    /**
     * Download all files sequentially.
     *
     * @param filePaths List of all files to be downloaded
     * @return true if all files are downloaded successfully
     */
    public Boolean downLoadAllFilesSeq(List<FilePath> filePaths){
        List<Boolean> lst = filePaths.stream().map(filePath -> {
            if(filePath.getProtocol()== Protocol.HTTP || filePath.getProtocol()== Protocol.HTTPS){
                return downLoadHTTPFiles(filePath);
            } else if(filePath.getProtocol() == Protocol.FTP || filePath.getProtocol() == Protocol.SFTP){
                return downLoadFTPFiles(filePath);
            } else return false;
        }).collect(Collectors.toList());
        return lst.parallelStream().anyMatch(e->e);
    }
}
