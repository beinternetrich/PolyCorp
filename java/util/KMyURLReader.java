package util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class KMyURLReader {
    String result = "";

    public static void main(String[] args) throws Exception {
            URL berich = new URL("http://beinternetrich.net/");
            URLConnection bir = berich.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(bir.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
       // }

}
    public String getJSONFile(){
        String jsonText;
        try {
            InputStream is = null;
            //ERRORING: is = getResources().openRawResource(R.raw.myjson);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonText = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonText;
    }
}
