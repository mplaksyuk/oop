package lab2.Utils;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import lab2.Beer.Beer;

// import lab2.Beer.Beer;

import java.io.File;
import java.util.ArrayList;

public class Utils {
    public static boolean validateXMLSchema(String xsdPath, String xmlPth) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPth)));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public static ArrayList<Beer> sortByName(ArrayList<Beer> beers){
        beers.sort(Beer::compare);
        return beers;
    }

}