import org.jsoup.nodes.Element;

public class GalleryViewProductParser extends ProductParser {

    GalleryViewProductParser(){
        super.setProductClass("grid-item");
    }

    String parseName(Element element) {
        return element.getElementsByClass("category-item-additional-txt").text();
    }

}
