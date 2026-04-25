package PaooGame.CtHrt;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
// nush ce sa zic ca am distrus putin harta da mna merge vezi ca e mare soft lock ca is idiot da tre sa schimb numa mapa.txt si gata

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