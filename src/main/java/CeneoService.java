import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;

public class CeneoService implements ComparisonService {
    private Logger logger = LoggerFactory.getLogger(CeneoService.class);
    private ProductParser productParser;
    private Document currentPage;

    public Products getProducts(String url){
        Products products = new Products();
        try {
            currentPage = Jsoup.connect(url).get();
            productParser = getParserMatchingToPageView(currentPage.body().toString());
            products.addAllProducts(parseProductsFromCurrentPage());

            while (hasNextPage()) {
                setNextPage();
                products.addAllProducts(parseProductsFromCurrentPage());
            }

            System.out.println("Number of parsed products: " + products.size());
        } catch (IOException e) {
            logger.error("Jsoup problem");
        }
        return products;
    }

    private boolean hasNextPage(){
        return currentPage.body().toString().contains("page-arrow arrow-next");
    }

    private void setNextPage() throws IOException {
        Elements elements = currentPage.getElementsByClass("page-arrow arrow-next");
        currentPage = Jsoup.connect("http://www.ceneo.pl" + elements.select("a").attr("href")).get();
    }

    private LinkedList<Product> parseProductsFromCurrentPage(){
        LinkedList<Product> products = new LinkedList<>();
        if(productParser != null){
            Elements pageElements = currentPage.getElementsByClass(productParser.getProductClass());
            for(Element element: pageElements){
                products.add(productParser.getProduct(element));
            }
        }
        return products;
    }

    private ProductParser getParserMatchingToPageView(String pageBody){
        if(pageBody.contains("cat-prod-row")){
            return new ListViewProductParser();
        }else if(pageBody.contains("category-item-box")){
            return new BoxViewProductParser();
        }else if(pageBody.contains("grid-item")){
            return new GalleryViewProductParser();
        }else{
            return null;
        }
    }
}
