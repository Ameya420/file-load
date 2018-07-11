package utils;

public class Constants {
    public final static String PROP_DEL = "`";
    public final static String SLASH = "/";
    public final static String URL_SEP_SIGN = "://";
    public final static String ROOT_PATH = ".";
    // 512MB in KB, Files of size more than this would be downloaded in chunks
    public final static long MAX_CHUNK_SIZE_IN_KB = 512000;
    public final static long ONE_KB_IN_BYTES = 1000;
    public final static int NUM_OF_PARALLEL_STREAMS = 10;
    public final static long DEFAULT_TIMEOUT_TIME = 60*1000;
}
