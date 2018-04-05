import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public abstract class ProductParser {
    private Logger logger = LoggerFactory.getLogger(ProductParser.class);
    private String productClass;

    public Product getProduct(Element element) {
        Product product = new Product();
        product.setName(parseName(element));
        product.setPrice(parsePrice(element));
        product.setPhoto(parsePhoto(element));
        return product;
    }

    public void setProductClass(String productClass){
        this.productClass = productClass;
    }

    public String getProductClass(){
        return productClass;
    }

    abstract String parseName(Element element);

    Float parsePrice(Element element){
        try {
            return Float.valueOf(element.getElementsByClass("price").text().replace(",", "."));
        }catch (NumberFormatException e){
            logger.info("{} has no price", parseName(element));
            return 0.0f;
        }
    }

    private String parsePhoto(Element element){
        String base64;
        try {
            URL imageURL = getPhotoURLByAlternativeText(element);
            InputStream in = imageURL.openStream();
            byte[] bytes = IOUtils.readFully(in, -1, false);
            base64 = Base64.getEncoder().encodeToString(bytes);

        } catch (FileNotFoundException | IllegalArgumentException e){
            base64 = "";
            logger.info("{} has no photo available", parseName(element));
        } catch (Exception e) {
            base64 = "";
            e.printStackTrace();
        }
        return base64;
    }

    private URL getPhotoURLByAlternativeText(Element element){
        URL imageURL = null;
        Elements photoTag = element.getElementsByAttribute("alt");
        try {
            if (photoTag.hasAttr("data-original")) {
                imageURL = new URL("http:" + element.getElementsByAttribute("data-original").attr("data-original"));
            } else {
                imageURL = new URL("http:" + element.getElementsByAttribute("src").attr("src"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imageURL;
    }

}
