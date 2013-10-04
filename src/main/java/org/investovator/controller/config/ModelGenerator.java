package org.investovator.controller.config;

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
