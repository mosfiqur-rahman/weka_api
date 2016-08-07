package weka_test;

import weka.*;
import weka.core.*;
import weka.core.converters.*;
import weka.core.converters.ConverterUtils.*;
import weka.filters.*;
import weka.filters.unsupervised.*;
import weka.filters.unsupervised.attribute.*;
import weka.filters.unsupervised.instance.*;
import java.io.*;

public class weka_api {

	public static void main(String[] args) throws Exception
	{
		System.out.println("Hello weka");
		
		DataSource source = new DataSource("/home/xps/Documents/weka_api/weka/spambase.arff");
		
		Instances dataset = source.getDataSet();
		
		
		String[] options = new String[4];
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
		saver.setFile(new File("/home/xps/Documents/weka_api/weka/new_1.arff"));
		saver.writeBatch();
	}
}