import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestUtils {

    public static String readFile(String filePath) throws IOException {
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String fileContent = null;
        String st;
        while ((st = br.readLine()) != null){
            if(fileContent==null) fileContent = st;
            else fileContent = fileContent + "\n" + st;
        }
        return fileContent;
    }
}
