To run this
Run "gradle fatjar"

then run following command
java -cp file-load-all-1.0-SNAPSHOT.jar FileDownloadUtilityDriver {file containing list of files to be downloaded}

FileDownloadUtilityDriver is just a test implementation.
For detailed implementation see documentation of FileDownloaderHandler.
It contains different implementations according to need. 
Take look at test cases for clear Idea about the code.
