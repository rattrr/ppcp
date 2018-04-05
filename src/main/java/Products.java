import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {
    @XmlElement(name = "product")
    private LinkedList<Product> products = new LinkedList<>();

    public void addAllProducts(LinkedList<Product> newProducts){
        products.addAll(newProducts);
    }

    public int size(){
        return products.size();
    }
}
