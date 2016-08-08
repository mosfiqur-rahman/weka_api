package weka_test;


import weka.core.*;
import weka.core.AttributeStats;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.*;
import weka.core.converters.ConverterUtils.*;
import weka.filters.*;
import weka.filters.unsupervised.*;
import weka.filters.unsupervised.attribute.*;
import weka.filters.unsupervised.instance.*;
import weka.filters.supervised.attribute.*;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.attributeSelection.*;
import weka.experiment.*;
import weka.experiment.Stats;
import weka.classifiers.*;
import weka.classifiers.bayes.*;
import weka.classifiers.trees.*;
import weka.classifiers.functions.*;
import weka.classifiers.meta.FilteredClassifier;

import java.io.*;

public class weka_api {
	
	public static void main(String[] args) throws Exception
	{

		DataSource source = new DataSource("/home/xps/Documents/weka_api/weka/spambase.arff");

/*****************************************************************************************

 	       **  SET CLASS INDEX + PRINT NUM CLASSES, ATTRIBUTES, INSTANCES  **
 	       
 	       

		Instances instances = source.getDataSet();
		
		instances.setClassIndex(instances.numAttributes() - 1);
		
		System.out.println("Num Classes: " +instances.numClasses());
		System.out.println("Num Classe Indexes: " +instances.classIndex());
		System.out.println(instances.toSummaryString());
		
		
		PrintWriter writer = new PrintWriter("logFile.txt", "UTF-8");
		
		writer.println("Num Classes: " +instances.numClasses());
		writer.println("Num Class Indexes: " +instances.classIndex());
      
        try {
            // Close the writer regardless of what happens...
            writer.close();
        } catch (Exception e) {
        }
		

**********************  

 
 **********************
 *
 *
 *
 *
 *********************** 
 
 								**	ARFF SAVER	**
 										
 										
 	ArffSaver saver = new ArffSaver();
 	saver.setInstances(instances);
 	saver.setFile(new File("/home/xps/Documents/weka/new_1.arff"));
 	saver.writeBatch();
 
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							**	ARFF to CSV CONVERTER	**
 										
 	
 	ArffLoader loader = new ArffLoader();
 	loader.setSource(new File("-----"));
 	Instances data = loader.getDataSet();
  	
 	
 	CSVSaver saver = new CSVSaver();
 	saver.setInstances(data);
 	saver.setFile(new File("/home/xps/Documents/weka/new_1.arff"));
 	saver.writeBatch();
 
 
 **********************
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							**	CSV to ARFF CONVERTER	**
 										
 	
 	CSVLoader loader = new CSVLoader();
 	loader.setSource(new File("------"));
 	Instances data = loader.getDataSet();
 	
 	
 	ArffSaver saver = new ArffSaver();
 	saver.setInstances(data);
 	saver.setFile(new File("/home/xps/Documents/weka/new_1.arff"));
 	saver.writeBatch();
 
 
 **********************
 
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							**	Filtering Attributes  **
 	
 	Instances dataset = source.getDataSet();
 	
 	// set up options to remove nth attribute
	String[] opts = new String[]{ "-R", "1"};
	// for example removing the first attribute
	Remove remove = new Remove();
	remove.setOptions(opts);
	remove.setInputFormat(dataset);
	Instances newData = Filter.useFilter(dataset, remove);
	
	ArffSaver saver = new ArffSaver();
	saver.setInstances(newData);
	saver.setFile(new File("/home/xps/Documents/weka/new_1.arff"));
	saver.writeBatch();	
	
						
 **********************
 
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							     **	Sparse DATA[SPARSE ARFF]  **
 
 							       
 	Instances dataset = source.getDataSet();
		
	NonSparseToSparse sp = new NonSparseToSparse();
	sp.setInputFormat(dataset);
			
	Instances newData = Filter.useFilter(dataset, sp);
		
				
	ArffSaver saver = new ArffSaver();
	saver.setInstances(newData);
	saver.setFile(new File("/home/xps/Documents/weka/new_1.arff"));
	saver.writeBatch();
	
 	
 						
 **********************
 
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							     **	Discretizing Attributes  **							        
 	
	
	Instances dataset = source.getDataSet();
		
	String[] options = new String[5];
	//choose the number of intervals, e.g. 2
	options[0] = "-B";
	options[1] = "2";
	//choose the range of the attributes on which the filters will be applied
	options[2] = "-R";
	options[3] = "first-last";
	//options[4] = "-V";   FOR inverting the result
		
	Discretize discritize = new Discretize();
	discritize.setOptions(options);
	discritize.setInputFormat(dataset);
	Instances newData = Filter.useFilter(dataset, discritize);
		

	ArffSaver saver = new ArffSaver();
	saver.setInstances(newData);
	saver.setFile(new File("/home/xps/Documents/weka/new_1.arff"));
	saver.writeBatch();
	
	
 **********************
 
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							     ** Attribute Selection **							        
 	


	Instances dataset = source.getDataSet();
		
		
	AttributeSelection filter = new AttributeSelection();
		
	CfsSubsetEval eval = new CfsSubsetEval();
	GreedyStepwise  search = new GreedyStepwise();
	//set the algorithm to search backward
	 	
	search.setSearchBackwards(true);
	filter.setEvaluator(eval);
	filter.setSearch(search);

	filter.setInputFormat(dataset);
		
		
	Instances newData = Filter.useFilter(dataset, filter);
			

	ArffSaver saver = new ArffSaver();
	saver.setInstances(newData);
	saver.setFile(new File("/home/xps/Documents/weka_api/weka/new_1.arff"));
	saver.writeBatch();	
	
	 **********************
 
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							     **  Attribute Stats - Nominal Check - Instance - Class Value  **	
 							     
 	
 	     
	Instances dataset = source.getDataSet();
		
	int numAttr = dataset.numAttributes() - 1; //Nnumber of attributes [class isn't counted]
	for( int i = 0; i  < numAttr; i++)
	{
		if  (dataset.attribute(i).isNominal()) // checking whether it's nominal or not
		{
			System.out.println("The " + i + "th Attribute is Nominal");
	
		int n = dataset.attribute(i).numValues();//printing value of the attribute		
		System.out.println("The " + i + "th Attribute has " + n + "value(s)");
			
		}
		AttributeStats as = dataset.attributeStats(i);
		int dc = as.distinctCount;
			
		System.out.println("The " + i + "th Attribute has " + dc + "Distinct value(s)");
			
		if  (dataset.attribute(i).isNumeric())
		{
			System.out.println("The " + i + "th Attribute is Numeric");
				
			Stats s = as.numericStats; // printing the stats
			System.out.println("The " + i + "th Attribute has min value: " + s.min + " and max value: " + s.max );
		}
		}
		
		int numInst = dataset.numInstances();
		for( int k = 0; k < numInst; k++)
		{
			Instance inst = dataset.instance(k);
			
		if  (inst.isMissing(0))//checking whether the 1st attribute is missing from the Kth instance
		{
			System.out.println("The " + 0 + "th Attribute is missing");
		}
			
		if  (inst.classIsMissing())
		{
			System.out.println("The class is missing at " + k + "th Instance");
		}
			
		double cv = inst.classValue();
		System.out.println(inst.classAttribute().value((int)cv));
		}	
	
	**********************
 
 
 **********************
 *
 *
 *
 *
 ***********************  
 
 							     **	Classifier  Naive Byes - SVM - Decision Tree  **		
	
	
	Instances dataset = source.getDataSet();
		
	dataset.setClassIndex(dataset.numAttributes() - 1);
		
	NaiveBayes nb = new NaiveBayes();
	nb.buildClassifier(dataset);
	System.out.println(nb.getCapabilities().toString());
		
	SMO svm = new SMO();
	svm.buildClassifier(dataset);
	System.out.println(svm.getCapabilities().toString());
	
	J48 tree = new J48();
	tree.buildClassifier(dataset);
		
		<********
		 * 
		 					**	Example of adding OPTIONS  **
		 		
		 		String[] options = new String[5];
				//choose the number of intervals, e.g. 2
				options[0] = "-B";
				options[1] = "2";
				//choose the range of the attributes on which the filters will be applied
				options[2] = "-R";
				options[3] = "first-last";
				//options[4] = "-V";  // FOR inverting the result
		 * 
		 *******>
				
	System.out.println(tree.getCapabilities().toString());
	System.out.println(tree.graph());

	**********************
	 
	 
	 **********************
	 *
	 *
	 *
	 *
	 ***********************  
	 
	 							     **	Filtered  Classifier   **		


	Instances dataset = source.getDataSet();
		
	dataset.setClassIndex(dataset.numAttributes() - 1);
				
	J48 tree = new J48();
		
	Remove remove = new Remove();// the filter
		
	String[] opts = new String[]{ "-R", "1"};
		
	remove.setOptions(opts);// set filter options
		
	FilteredClassifier fc = new FilteredClassifier();
		
	fc.setFilter(remove); //specify filter
	fc.setClassifier(tree); //specify base classifier
	fc.buildClassifier(dataset);//build meta-classifier

**********************
	 
	 
	 **********************
	 *
	 *
	 *
	 *
	 ***********************  
	 
	 	
	 							     **	Regression   **	



****************************************************************************************/

		Instances dataset = source.getDataSet();
		
		dataset.setClassIndex(dataset.numAttributes() - 1);
				
		J48 tree = new J48();
		
		Remove remove = new Remove();// the filter
		
		String[] opts = new String[]{ "-R", "1"};
		
		remove.setOptions(opts);// set filter options
		
		FilteredClassifier fc = new FilteredClassifier();
		
		fc.setFilter(remove); //specify filter
		fc.setClassifier(tree); //specify base classifier
		fc.buildClassifier(dataset);//build meta-classifier
		
		
				
		System.out.println(tree.getCapabilities().toString());
		System.out.println(tree.graph());
		
	}
}