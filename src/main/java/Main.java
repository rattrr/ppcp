import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        ProductsPricesComparisonParser productsPricesComparisonParser = new ProductsPricesComparisonParser(readURL(), new CeneoService(), new XMLWriter());
        productsPricesComparisonParser.parseToFile();
    }

    private static String readURL(){
        String url = "";
        Pattern pattern= Pattern.compile("^https://www.ceneo.pl/(.*)");
        Scanner scanner = new Scanner(System.in);
        while(!pattern.matcher(url).matches()) {
            url = scanner.nextLine();
        }
        System.out.println("URL OK");
        return url.trim();
    }
}
