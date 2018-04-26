package parser;

import org.jsoup.nodes.Element;

public class BoxViewProductParser extends ProductParser{

    BoxViewProductParser(){
        super.setProductClass("category-item-box");
    }

    String parseName(Element element) {
        return element.getElementsByClass("category-item-box-name").text();
    }
}
