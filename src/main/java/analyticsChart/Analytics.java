package analyticsChart;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 
 * This class is for creating graphs and charts using the data from csv file
 * @author Lee Lin
 * 
 */


public class Analytics {
	
	/**
	 * @param args main
	 */
	public static void main(String[] args) {
		
		/**
		 * Add file path and read file
		 */
		String path = "C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\data.csv";
		BufferedReader reader = null;
		String line = "";
		
		try {
			reader = new BufferedReader(new FileReader(path));
			
			/**
			 * Initialize price, total, maximum, minimum and average price
			 */
			float price = 0;
			float totalP = 0;
			float maxP = 0;
			float minP = 1000000;
			float avgP = 0;
			
			/**
			 * Initialize size, total, maximum, minimum and average size
			 */
			float size = 0;
			float totalS = 0;
			float maxS = 0;
			float minS = 1000000;
			float avgS = 0;
			
			/**
			 * Initialize price/sqft, total, maximum, minimum and average price/sqft
			 */
			float pricesize = 0;
			float totalPS = 0;
			float maxPS = 0;
			float minPS = 1000000;
			float avgPS = 0;
			
			/**
			 * Initialize count
			 */
			int count = 0;
			
			/**
			 * Initialize no. of HDB, condo and landed houses
			 */
			int hdb = 0;
			int condo = 0;
			int landed = 0;
			
			/**
			 * Initialize no. of room and house with 2,3,4 and 5 bedrooms
			 */
			int room = 0;
			int room2 = 0;
			int room3 = 0;
			int room4 = 0;
			int room5 = 0;
			
			/**
			 * Read next line in csv until end of file
			 */
			while((line = reader.readLine()) != null) {
				
				/**
				 * Split line at all commas
				 */
				String[] values = line.split(",");
				
				/**
				 * Get price value
				 * Calculate total price
				 * Find maximum/minimum price
				 * Calculate total no. of houses
				 * Calculate average price
				 */
				price = Float.parseFloat(values[1]);
				totalP = calculate_total(totalP, price);
				maxP = calculate_max(maxP, price);
				minP = calculate_min(minP, price);
				count++;
				avgP = calculate_avg(totalP, count);
				
				/**
				 * Get size value
				 * Calculate total size
				 * Find maximum/minimum size
				 * Calculate average size
				 */
				size = Float.parseFloat(values[3]);
				totalS = calculate_total(totalS, size);
				maxS = calculate_max(maxS, size);
				minS = calculate_min(minS, size);
				avgS = calculate_avg(totalS, count);

				/**
				 * Get price/sqft value
				 * Calculate total price/sqft
				 * Find maximum/minimum price/sqft
				 * Calculate average price/sqft
				 */
				pricesize = Float.parseFloat(values[4]);
				totalPS = calculate_total(totalPS, pricesize);
				maxPS = calculate_max(maxPS, pricesize);
				minPS = calculate_min(minPS, pricesize);
				avgPS = calculate_avg(totalPS, count);

				/**
				 * Calculate no. of HDBs/condos/landed houses
				 */
				if(values[2].contains("HDB") || values[2].contains("Apartment"))
					hdb++;
				else if(values[2].contains("Condo"))
					condo++;
				else if(values[2].contains("Landed") || values[2].contains("Detached"))
					landed++;
				
				/**
				 * Get no. of bedroom value
				 * Calculate no. of houses with 2,3,4 and 5 bedrooms
				 */
				room = Integer.parseInt(values[5]);
				if(room == 2)
					room2++;
				if(room == 3)
					room3++;
				if(room == 4)
					room4++;
				if(room == 5)
					room5++;
			}
			
			/**
			 * Call function to create price bar chart
			 * Call function to create size and price/sqft bar chart
			 * Call function to create types of houses pie chart
			 * Call function to create no. of rooms pie chart
			 */
			price_bar(avgP, maxP, minP);
			size_bar(avgS, avgPS, maxS, minS, maxPS, minPS);
			type_pie(hdb,condo, landed, count);
			room_pie(room2, room3, room4, room5, count);
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				/**
				 * Close file
				 */
				reader.close();
			} 
			/**
			 * Catch IOException
			 */
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * Calculate total value
	 * @param total is number calculated
	 * @param value is current number
	 * @return total value
	 */
	public static float calculate_total(float total, float value) {
		total = total + value;
		return total;
	}
	
	/**
	 * Calculate maximum value
	 * @param max is maximum number
	 * @param value is current number
	 * @return maximum value
	 */
	public static float calculate_max(float max, float value) {
		if(value > max)
			max = value;
		return max;
	}
	
	/**
	 * Calculate minimum value
	 * @param min is minimum number
	 * @param value is current number
	 * @return minimum value
	 */
	public static float calculate_min(float min, float value) {
		if(value < min)
			min = value;
		return min;
	}
	
	/**
	 * Calculate average value
	 * @param total number
	 * @param count number of houses
	 * @return average value
	 */
	public static float calculate_avg(float total, int count) {
		float average = 0;
		average = total/count;
		return average;
	}
	
	/**
	 * Round off value to 2 decimal places
	 * @param value is number
	 * @return value rounded off to 2 decimal places
	 */
	public static double round(double value) {

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	/**
	 * Create bar chart to show minimum, average and maximum price of houses
	 * @param average price
	 * @param max price
	 * @param min price
	 */
	public static void price_bar(float average, float max, float min) {
		
		/**
		 * Set data value for minimum, average and maximum price
		 */
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(min, "Minimum", "Min");
		dataset.setValue(average, "Average", "Avg");
		dataset.setValue(max, "Maximum", "Max");
		
		/**
		 * Add values to dataset to generate the pie chart
		 * Title|x-axis title|y-axis title|dataset|orientation of chart
		 */
		JFreeChart chart = ChartFactory.createBarChart("House Pricing($)", "Minimum/Average/Maximum", "Price ($)", dataset, PlotOrientation.VERTICAL, false, true, false);
		
		/**
		 * Generate chart
		 * Save chart into file path as .jpeg
		 */
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\Pricing.jpeg"), chart, 350, 300);
			} 
		/**
		 * Catch Exception
		 */
		catch(Exception e) {
				System.err.println();
		}
	}
	
	
	/**
	 * Create bar chart to show minimum, average and maximum size and price/sqft of houses
	 * @param avgS is average size
	 * @param avgPS is average price/sqft
	 * @param maxS is maximum size
	 * @param minS is minimum size
	 * @param maxPS is maximum size/sqft
	 * @param minPS is minimum size/sqft
	 */
	public static void size_bar(float avgS, float avgPS, float maxS, float minS, float maxPS, float minPS) {
		
		/**
		 * Set data value for minimum, average and maximum size and price/sqft
		 */
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(minS, "MinS", "Size(Sqft)");
		dataset.setValue(avgS, "AvgS", "Size(Sqft)");
		dataset.setValue(maxS, "MaxS", "Size(Sqft)");
		
		dataset.setValue(minPS, "MinPS", "$/Sqft");
		dataset.setValue(avgPS, "AvgPS", "$/Sqft");
		dataset.setValue(maxPS, "MaxPS", "$/Sqft");
		
		/**
		 * Add values to dataset to generate the pie chart
		 * Title|x-axis title|y-axis title|dataset|orientation of chart
		 */
		JFreeChart chart = ChartFactory.createBarChart("Size(Sqft) || Price($)/Sqft", "Minimum/Average/Maximum", "", dataset, PlotOrientation.VERTICAL, false, true, false);

		/**
		 * Generate chart
		 * Save chart into file path as .jpeg
		 */
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\Size.jpeg"), chart, 350, 300);
			} 
		/**
		 * Catch Exception
		 */
		catch(Exception e) {
				System.err.println();
		}
		
	}
	
	/**
	 * Create pie chart to show types of houses
	 * @param hdb total number
	 * @param condo total number
	 * @param landed total number
	 * @param count number of houses
	 */
	public static void type_pie(int hdb, int condo, int landed, float count) {
		
		/**
		 * Initialize percentage of HDB, condo and landed houses
		 */
		float percenthdb = 0;
		float percentcondo = 0;
		float percentlanded = 0;
		
		/**
		 * Calculate percentage of HDB, condo and landed houses
		 */
		percenthdb = hdb/count * 100;
		percentcondo = condo/count * 100;
		percentlanded = landed/count * 100;
		
		
		/**
		 * Set data value for no. of HDBs, condos and landed houses
		 */
		DefaultPieDataset piedataset = new DefaultPieDataset();
		piedataset.setValue("HDB (" + round(percenthdb) + "%)", hdb);
		piedataset.setValue("Condo (" + round(percentcondo) + "%)", condo);
		piedataset.setValue("Landed (" + round(percentlanded) + "%)", landed);
		
		/**
		 * Add values to dataset to generate the pie chart
		 * Title|dataset|legend|tooltip|generate URLs
		 */
		JFreeChart chart = ChartFactory.createPieChart("Types of Houses", piedataset, true, true, false);
		
		/**
		 * Generate chart
		 * Save chart into file path as .jpeg
		 */
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\Types.jpeg"), chart, 350, 250);
			} 
		/**
		 * Catch Exception
		 */
		catch(Exception e) {
				System.err.println();
		}	
		
	}
	
	/**
	 * Create pie chart to show no. of houses with 2,3,4 and 5 rooms
	 * @param room2 is number of 2 rooms houses
	 * @param room3 is number of 3 rooms houses
	 * @param room4 is number of 4 rooms houses
	 * @param room5 is number of 5 rooms houses
	 * @param count number of houses
	 */
	public static void room_pie(int room2, int room3, int room4, int room5, float count) {
		
		/**
		 * Initialize percentage of 2,3,4 and 5 rooms
		 */
		float percentroom2 = 0;
		float percentroom3 = 0;
		float percentroom4 = 0;
		float percentroom5 = 0;
				
		/**
		 * Calculate percentage of 2,3,4 and 5 rooms
		 */
		percentroom2 = room2/count * 100;
		percentroom3 = room3/count * 100;
		percentroom4 = room4/count * 100;
		percentroom5 = room5/count * 100;
				
		/**
		 * Set data value for no. of houses with 2,3,4,5 rooms
		 */
		DefaultPieDataset piedataset = new DefaultPieDataset();
		piedataset.setValue("2 Rooms (" +  round(percentroom2) + "%)", room2);
		piedataset.setValue("3 Rooms (" +  round(percentroom3) + "%)", room3);
		piedataset.setValue("4 Rooms (" +  round(percentroom4) + "%)", room4);
		piedataset.setValue("5 Rooms (" +  round(percentroom5) + "%)", room5);
		
		/**
		 * Add values to dataset to generate the pie chart
		 * Title|dataset|legend|tooltip|generate URLs
		 */
		JFreeChart chart = ChartFactory.createPieChart("Number of Bedrooms", piedataset, true, true, false);
		
		/**
		 * Generate chart
		 * Save chart into file path as .jpeg
		 */
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\Bedrooms.jpeg"), chart, 350, 250);
			} 
		/**
		 * Catch Exception
		 */
		catch(Exception e) {
				System.err.println();
		}	
		
	}
}

