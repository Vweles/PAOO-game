package PaooGame.CtHrt;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Utils {
    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream in = Utils.class.getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null)
                builder.append(line + "\n");
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}