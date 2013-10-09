package org.investovator.controller.config;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Amila Surendra
 * @version $Revision
 *
 * THis is the main Agent configuration class called by UI.
 */
public class ConfigGenerator {


    private String[] stockIDs;
    private String outputPath;


    private HashMap<String,String> properties = new HashMap<String, String>();
    private HashMap<String,Integer> agentPopulation = new HashMap<String, Integer>();
    String[] dependencyReports;

    private String modelTemlpateFile;
    private String reportTemlpateFile;
    private String mainTemplateFile;

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
        initialize();
    }

    public void createConfigs(){
        for (String stockID : stockIDs) {
            createConfig(stockID);
        }
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
        properties.put("$lengthOfDay",Integer.toString(200000));
        properties.put("$slowSleepInterval",Integer.toString(100));
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

        //Create Report File
        String reportFile = String.format("%s/report_%s.xml",outputPath,stockID);
        ReportGenerator reportFileGenerator = new ReportGenerator(reportTemlpateFile);
        reportFileGenerator.setOutputPath(reportFile);
        reportFileGenerator.generateXML(stockID);


        //Create Model File
        String modelFile = String.format("%s/model_%s.xml",outputPath,stockID);
        ModelGenerator modelFileGenerator = new ModelGenerator(modelTemlpateFile);
        modelFileGenerator.setStockID(stockID);
        modelFileGenerator.setOutputFile(modelFile);

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

    }




}
