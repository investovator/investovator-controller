package org.investovator.core.config;

import net.sf.saxon.TransformerFactoryImpl;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ReportBeanGenerator {

    File templateXML;

    public ReportBeanGenerator(File templateFile){
        this.templateXML = templateFile;
    }

    public File generateXML(String stockID){

        TransformerFactory factory = TransformerFactoryImpl.newInstance();
        Source xslt = new StreamSource(new File(getClass().getResource("reportTemplate.xslt").getPath()));
        Transformer transformer = null;

        try {
            transformer = factory.newTransformer(xslt);

            Source text = new StreamSource(new File(getClass().getResource("input.xml").getPath()));
            transformer.transform(text, new StreamResult(new File("output.xml")));


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return null;
    }

}
