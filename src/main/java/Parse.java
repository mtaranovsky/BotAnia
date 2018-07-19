import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parse {

    public static String ukraine() {
        Document doc;
        try {
            doc = Jsoup.connect("https://www.slavorum.org/20-facts-about-ukraine-that-you-didnt-know/").get();
           // String  title = doc.body().getElementsByTag("h2").text();
            Element title = null;
            Element pngs= null;
            String [] link= new String[20];
            String [] text= new String[20];

            for (int i = 0; i <20; i++) {
                title=doc.select("h2").get(i);
                pngs = doc.select("img.lazyload.alignnone").get(i);
                link[i] = pngs.attr("data-src");
                text[i]= title.text();

            }

            //Elements pngs=doc.body().getElementsByTag("data-src");
//            for (int i = 0; i <20 ; i++) {
//                System.out.println(text[i]);
//                System.out.println(link[i]);
            Double rand=Math.random()*20;
            int i = rand.intValue();


        return text[i]+link[i];
        } catch (IOException e) {
            e.printStackTrace();
        }return null;
    }

    public static JSONObject getApi(){

        String token = "1832157159.1677ed0.baf34adad1ec43219a6870690fcccc12";
        String url = "https://api.instagram.com/v1/users/self/media/recent/?access_token=1832157159.1677ed0.baf34adad1ec43219a6870690fcccc12";
        try{
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
