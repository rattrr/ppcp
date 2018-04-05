public class ProductsPricesComparisonParser {
    private String url;
    private ComparisonService comparisonService;
    private Writer writer;

    public ProductsPricesComparisonParser(String url, ComparisonService service, Writer writer){
        this.url = url;
        this.comparisonService = service;
        this.writer = writer;
    }

    public void parseToFile(){
        writer.makeFile(comparisonService.getProducts(url), createFilename());
    }

    private String createFilename(){
        url = url.replace("https://www.ceneo.pl/", "");
        url = url.replaceAll(";*[\\d]*\\.htm", ""); //removes additional numbers and expressions
        url = url.replaceAll("[/:]", "_"); //replaces filters/categories separators with '_'
        return url;
    }
}
