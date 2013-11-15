/*
 * investovator, Stock Market Gaming framework
 * Copyright (C) 2013  investovator
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.investovator.controller.config;

import org.apache.commons.configuration.ConfigurationException;
import org.investovator.core.commons.configuration.ConfigLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Amila Surendra
 * @version $Revision
 *
 * This is the main Agent configuration class called by UI.
 */
public class ConfigGenerator {


    private String[] stockIDs;
    private String outputPath;


    private HashMap<String,String> properties = new HashMap<String, String>();
    private HashMap<String,Integer> agentPopulation = new HashMap<String, Integer>();
    private HashMap<String, String> propertyFiles = new HashMap<>();
    String[] dependencyReports;

    private String modelTemlpateFile;
    private String reportTemlpateFile;
    private String mainTemplateFile;
    private String springBeanConfigTemplate;

    //Main Template
    ArrayList<String> listOfImports;
    ArrayList<String> listOfControllers;


    public void setSpringBeanConfigTemplate(String springBeanConfigTemplate) {
        this.springBeanConfigTemplate = springBeanConfigTemplate;
    }

    public void setModelTemlpateFile(String filePath){
        this.modelTemlpateFile = filePath;
    }

    public void setReportTemlpateFile(String reportTemlpateFile) {
        this.reportTemlpateFile = reportTemlpateFile;
    }

    public void setMainTemplateFile(String mainTemplateFile) {
        this.mainTemplateFile = mainTemplateFile;
    }

    public ConfigGenerator(String[] stockIDs, String outputPath){
        this.stockIDs = stockIDs;
        this.outputPath = outputPath;

        listOfControllers = new ArrayList<String>();
        listOfImports = new ArrayList<String>();


        initialize();
    }

    public void createConfigs(){
        for (String stockID : stockIDs) {
            createConfig(stockID);
        }

        //Create Main File
        String mainFile = String.format("%s/main.xml",outputPath);
        createMainXML(mainFile);
    }

    /**
     * Add property files for each stock
     * @param stockID Company Symbol
     * @param url url for the properties file
     */
    public void addProperties(String stockID, String url){
        propertyFiles.put(stockID,url);
    }

    private void initialize(){

        //Check Output Folder and create if necessary
        File folder = new File(outputPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
    }

    /*
    public String[] getSimulationProperties(){
        String[] properties = {"No of Days", "Length of Day"};
        return properties;
    }

    public void setSimulationProperty(String property, String value){
        properties.put(property,value);
    }
      */

    //Should changed to better way
    public void setInitialPrice(float price){
        properties.put("$initialPrice",Float.toString(price));
    }

    public void setNoOfDays(int days){
        properties.put("$maximumDays",Integer.toString(days));
    }

    public void setSpeedFactor(float speedRatio){
        //Should calculate and put

        int lengthOfDay = 200000;
        int delay = 1000;

        String lengthProp;
        String delayProp;

        if( (lengthProp = System.getProperty("org.investovator.controller.agent.config.lengthofday")) != null){
            lengthOfDay =  Integer.parseInt( lengthProp);
        }

        if( (delayProp = System.getProperty("org.investovator.controller.agent.config.delay")) != null){
            delay =  Integer.parseInt( delayProp );
        }

        properties.put("$lengthOfDay",Integer.toString(lengthOfDay));
        properties.put("$slowSleepInterval",Integer.toString((int)(delay/speedRatio)));
    }

    public void setProperties(String propertiesFile){
        try {
            ConfigLoader.loadProperties(propertiesFile);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void addDependencyReportBean(String[] beanNames){
       this.dependencyReports = beanNames;
    }

    public String[] getSupportedAgentTypes(){
        return  new ModelGenerator(modelTemlpateFile).getSupportedAgentTypes();
    }

    public String[] getSupportedReports(){
        return new ReportGenerator(reportTemlpateFile).getSupportedReports();
    }

    public String[] getDependencyReportBeans(String reportType){
        return  new ReportGenerator(reportTemlpateFile).getDependencyReportBeans(reportType);
    }

    public void addAgent(String agent, int populationSize){
        agentPopulation.put(agent,populationSize);
    }


    private void createConfig(String stockID){

        String reportFileName =  String.format("report_%s.xml",stockID);
        String modelFileName =  String.format("model_%s.xml",stockID);

        //Create Report File
        String reportFile = String.format("%s/%s",outputPath,reportFileName);
        ReportGenerator reportFileGenerator = new ReportGenerator(reportTemlpateFile);
        reportFileGenerator.setOutputPath(reportFile);
        reportFileGenerator.setOutputTemplateDoc(springBeanConfigTemplate);
        reportFileGenerator.generateXML(stockID);


        //Create Model File
        String modelFile = String.format("%s/%s",outputPath,modelFileName);
        ModelGenerator modelFileGenerator = new ModelGenerator(modelTemlpateFile);
        modelFileGenerator.setPropertyFileName(propertyFiles.get(stockID));
        modelFileGenerator.setStockID(stockID);
        modelFileGenerator.setOutputFile(modelFile);
        modelFileGenerator.setOutputTemplateDoc(springBeanConfigTemplate);

        Iterator agentPopulationIterator = agentPopulation.keySet().iterator();

        while (agentPopulationIterator.hasNext()) {
            String next = (String) agentPopulationIterator.next();
            modelFileGenerator.addAgent(next,agentPopulation.get(next));
        }

        Iterator propertiesIterator = properties.keySet().iterator();

        while (propertiesIterator.hasNext()) {
            String property = (String) propertiesIterator.next();
            modelFileGenerator.addSimulationProperty(property,properties.get(property));
        }

        modelFileGenerator.addDependencyReportBean(dependencyReports);

        modelFileGenerator.createModelConfig();


        listOfImports.add(reportFileName);
        listOfImports.add(modelFileName);


        //Controller bean names are hard coded for now
        listOfControllers.add(stockID+"Controller");
    }





    private void createMainXML(String mainFile){

        XMLParser templateParser = new XMLParser(mainTemplateFile);
        Document mainXmlDoc = templateParser.getXMLDocumentModel();

        //Adding imports
        Element[] elements = new Element[listOfImports.size()];
        for (int i = 0; i < listOfImports.size(); i++) {
            elements[i] =   XMLEditor.createImportElement(mainXmlDoc,listOfImports.get(i));
        }
        XMLEditor.replacePlaceholderElement("file-imports", mainXmlDoc, elements);



        //Adding controllers
        int controllersCount =  listOfControllers.size();
        elements = new Element[controllersCount];
        for (int i = 0; i < controllersCount; i++) {
            elements[i] =   XMLEditor.createControllerElement(mainXmlDoc,listOfControllers.get(i));
        }
        XMLEditor.replacePlaceholderElement("controllers", mainXmlDoc, elements);


        //Adding Stocks
        //Adding controllers
        int stocksCount =  stockIDs.length;
        elements = new Element[stocksCount];
        for (int i = 0; i < stocksCount; i++) {
            elements[i] =   XMLEditor.createControllerElement(mainXmlDoc,stockIDs[i]);
        }
        XMLEditor.replacePlaceholderElement("stocks", mainXmlDoc, elements);

        templateParser.saveNewXML(mainFile);

    }



}
