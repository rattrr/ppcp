import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.regex.Pattern;

public class URLReader {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getFirstMatching(String URLpattern){
        String url = "";
        Pattern pattern= Pattern.compile(URLpattern);
        Scanner scanner = new Scanner(System.in);
        while(!pattern.matcher(url).matches()) {
            url = scanner.nextLine();
        }
        logger.info("URL OK");
        return url.trim();
    }
}
