package parser;

import org.jsoup.nodes.Element;

public class ListViewProductParser extends ProductParser {
    ListViewProductParser(){
        super.setProductClass("cat-prod-row");
    }
    String parseName(Element element) {
        return element.getElementsByClass("cat-prod-row-name").text().replace("»", "");
    }
}
