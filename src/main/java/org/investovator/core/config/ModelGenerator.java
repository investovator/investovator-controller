package org.investovator.core.config;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.xpath.XPathConstants;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ModelGenerator {

    XMLParser parser;

    public ModelGenerator(String templateFile){
        parser = new XMLParser(templateFile);
    }

    /**
     * Returns the Types of Agents supported by the System
     * @return
     */
    public String[] getSupportedAgentTypes(){

        //Temporary return Spring


        return null;
    }

}
