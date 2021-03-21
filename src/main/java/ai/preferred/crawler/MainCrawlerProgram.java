package ai.preferred.crawler;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
//import org.eclipse.swt.program.Program;
import ai.preferred.crawler.NinetyNine.master.HouseMainPageHandler;
import ai.preferred.crawler.SRX.master.ListingMainPageHandler;
import ai.preferred.crawler.entity.Property;
import ai.preferred.venom.Crawler;
import ai.preferred.venom.Session;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.request.VRequest;
import ai.preferred.venom.SleepScheduler;
import ai.preferred.venom.fetcher.AsyncFetcher;
import ai.preferred.venom.fetcher.Fetcher;
import ai.preferred.venom.validator.EmptyContentValidator;
import ai.preferred.venom.validator.StatusOkValidator;
import analyticsChart.Analytics;

/**
 * This is a Venom Crawler Program that crawls for data online, storing them
 * into an excel file (.csv) and generating insightful analytical graph chart
 * using the data crawled.
 * 
 * @author Zhei Wei
 *
 */

public class MainCrawlerProgram {

	/**
	 * Creating session keys to retrieve from Handler Calls the Crawler programs for
	 * 99co and SRX Storing the crawled data in excel file (property.csv)
	 */
	
	public static final Session.Key<List<Property>> PROPERTY_KEY = new Session.Key<>();
	
	/**
	 * Creating session keys to store the property attributes
	 */
	public static final Session.Key<EntityCSVStorage<Property>> CSV_KEY = new Session.Key<>();

	/**
	 * Initialize property list to be put in session Using try and catch to access
	 * the URL of SRX and 99co page Using Polymorphism by creating an initialization
	 * of Crawler and instantiate SRX and 99co crawler
	 * 
	 * @param args main
	 * @exception Exception if crawler couldn't start run or file cannot be opened.
	 */
	
	public static void main(String[] args) throws Exception {

		final String filename = "/data/property.csv";
		String workingDir = System.getProperty("user.dir");
				
				try (final EntityCSVStorage<Property> storage = new EntityCSVStorage<>(workingDir + filename,
						Property.class)) {

			final List<Property> propertyList = new ArrayList<>();
			final Session session = Session.builder().put(PROPERTY_KEY, propertyList).put(CSV_KEY, storage).build();

			try (Crawler crawler = Crawler.builder().setSession(session).build().start()) {
				crawler.getScheduler().add(
						new VRequest("https://www.srx.com.sg/singapore-property-listings/property-for-sale"),
						new ListingMainPageHandler());
			} catch (Exception e) {
				System.out.print("Could not run crawler: " + e.getMessage());
			}

			try (Crawler crawler = Crawler.builder().setSession(session).build().start()) {
				crawler.getScheduler().add(new VRequest("https://www.99.co/singapore/sale"),
						new HouseMainPageHandler());
			} catch (Exception e) {
				System.out.print("Could not run crawler: " + e.getMessage());
			}

			/**
			 * Calling the analytics graph file to generate and display insights in an
			 * analytical graph chart
			 */

			System.out.println("GENERATING ANALYTICS CHART");
			Analytics analytics = new Analytics();
			analytics.main(args);

			System.out.println("OPENING HTML FILE");
			URI uri = new URI("file:///C:/Users/Public/eclipse-workspace/WebCrawler-master/data/home.html");
			uri.normalize();
			Desktop.getDesktop().browse(uri);

		} catch (Exception e) {
			System.out.println("Could not open file: " + e.getMessage());
		}

	}
}
