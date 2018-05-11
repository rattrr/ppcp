package writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.Products;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XMLWriter implements Writer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void makeFile(Products products, String filename) {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(Products.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(products, new File(filename + ".xml"));
        } catch (JAXBException e) {
            logger.error("XML convertion went wrong");
        }
    }
}
