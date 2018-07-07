package models;

import utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class FilePath {
    private String urlString;
    private Protocol protocol;
    private String rootPath;
    private URL url;

    public FilePath(String urlString){
        this.urlString = urlString;
        try {
            this.url = new URL(urlString);
        } catch (MalformedURLException e) {
            // TODO Do something For Malformed Exception
            e.printStackTrace();
        }
        String[] parts = urlString.split(Constants.URL_SEP_SIGN);
        for (Protocol p : Protocol.values()) {
            if(parts[0].equalsIgnoreCase(p.getProtocolTypeString())){
                protocol = p;
                break;
            }
        }
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "FilePath{" +
                "urlString='" + urlString + '\'' +
                ", protocol=" + protocol +
                '}';
    }
}
