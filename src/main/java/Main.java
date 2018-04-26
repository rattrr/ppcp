import parser.CeneoService;
import parser.ProductsPricesComparisonParser;
import writer.XMLWriter;

public class Main {
    public static void main(String[] args){
        URLReader urlreader = new URLReader();
        String url = urlreader.getFirstMatching("^https://www.ceneo.pl/(.*)");
        ProductsPricesComparisonParser productsPricesComparisonParser = new ProductsPricesComparisonParser(url, new CeneoService(), new XMLWriter());
        productsPricesComparisonParser.parseToFile();
    }
}
