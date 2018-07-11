package models;

import utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

// This class primarily holds all info regarding file path
public class FilePath {
    // URL of file as is
    private String urlString;
    private int port;
    private Protocol protocol;
    // Mostly its serverName
    private String rootPath;
    private URL url;
    // Name of file
    private String fileName;
    private String serverUserName = "anonymous";
    private String serverPassword = "anonymous";
    // Absolute path of file
    private String filePathInServer;

    public FilePath(String urlString){
        String[] propPaths = urlString.split(Constants.PROP_DEL);
        this.urlString = propPaths[0];
        if(propPaths.length>1) {
            serverUserName = propPaths[1];
            serverPassword = propPaths[2];
        }
        String[] parts = this.urlString.split(Constants.URL_SEP_SIGN);
        String[] slashParts = parts[1].split(Constants.SLASH);

        this.filePathInServer = "";
        for (int i = 1;i<slashParts.length;i++){
            this.filePathInServer = this.filePathInServer + "/" + slashParts[i];
        }
        this.rootPath = slashParts[0];
        this.fileName = slashParts[slashParts.length-1];
        for (Protocol p : Protocol.values()) {
            if(parts[0].equalsIgnoreCase(p.getProtocolTypeString())){
                protocol = p;
                break;
            }
        }
        try {
            if(this.protocol==Protocol.HTTP || this.protocol==Protocol.HTTPS)
                this.url = new URL(this.urlString);
        } catch (MalformedURLException e) {
            // TODO Do something For Malformed Exception
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getUrlString() {
        return urlString;
    }

    public URL getUrl() {
        return url;
    }

    public String getServerUserName() {
        return serverUserName;
    }

    public String getServerPassword() {
        return serverPassword;
    }

    public String getFilePathInServer() {
        return filePathInServer;
    }

    @Override
    public String toString() {
        return "FilePath{" +
                "urlString='" + urlString + '\'' +
                ", protocol=" + protocol +
                '}';
    }
}
