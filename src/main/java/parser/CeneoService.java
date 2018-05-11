package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;

public class CeneoService implements ComparisonService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private LinkedList<ProductParser> productParsers = new LinkedList<>();
    private Document currentPage;

    public Products getProducts(String url){
        Products products = new Products();
        try {
            currentPage = Jsoup.connect(url).get();
            makeParsersMatchingToPageView(currentPage.body().toString());
            products.addAllProducts(parseProductsFromCurrentPage());

            while (hasNextPage()) {
                setNextPage();
                products.addAllProducts(parseProductsFromCurrentPage());
            }

            System.out.println("Number of parsed products: " + products.size());
        } catch (IOException e) {
            logger.error("GET request error");
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
        for(ProductParser productParser : productParsers){
            Elements pageElements = currentPage.getElementsByClass(productParser.getProductClass());
            for(Element element: pageElements){
                products.add(productParser.getProduct(element));
            }
        }
        return products;
    }

    private void makeParsersMatchingToPageView(String pageBody){
        if(pageBody.contains("cat-prod-row")) {
            productParsers.add(new ListViewProductParser());
        }
        if(pageBody.contains("category-item-box")) {
            productParsers.add(new BoxViewProductParser());
        }
        if(pageBody.contains("grid-item")){
            productParsers.add(new GalleryViewProductParser());
        }
    }
}
