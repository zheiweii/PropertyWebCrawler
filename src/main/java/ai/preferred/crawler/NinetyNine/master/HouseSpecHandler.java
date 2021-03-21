package ai.preferred.crawler.NinetyNine.master;

import java.io.IOException;
import java.util.List;
import com.mysql.cj.util.StringUtils;
import ai.preferred.crawler.EntityCSVStorage;
import ai.preferred.crawler.MainCrawlerProgram;
import ai.preferred.crawler.entity.Property;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.response.VResponse;

/**
 * This class is for crawling the attributes of the property. It contains the
 * selector for all property attributes.
 * 
 * @author Zhei Wei
 * 
 *         Using selector to get the property attributes. Using .trim() to
 *         remove irrelevant data that has been crawled.
 */

public class HouseSpecHandler implements Handler {
	public void handle(Request request, VResponse vResponse, Scheduler scheduler, Session session, Worker worker) {
		String title = null, price = null, area = null, type = null, psf = null, numBeds = null;

		title = vResponse.getJsoup().select("#overview > div > div > div > h1").text();
		price = vResponse.getJsoup().select("#price > h2").text().replaceAll("[a-zA-Z!@#$%^&*()/,?\":{}|<>]", "")
				.trim();
		area = vResponse.getJsoup().select("#overview > div > div > div > div:nth-child(3) > p").text()
				.replaceAll("[a-zA-Z!@#$%^&*()/,?\":{}|<>-]", "").trim();
		type = vResponse.getJsoup().select("#overview > div > div > div > p > a").text().replaceAll("for Sale", "")
				.trim();
		numBeds = vResponse.getJsoup()
				.select("#listingPageContent > div > div:nth-child(4) > div:nth-child(2) > div > p > a").text()
				.replaceAll("[a-zA-Z!@#$%^&*()/,?\":{}|<>-]", "").trim();
		psf = vResponse.getJsoup().select("#propertyDetails > div > div:nth-child(1) > div > div:nth-child(1) > div")
				.text().replaceAll("[a-zA-Z!@#$%^&*()/,?\":{}|<>]", "").trim();

		/**
		 * Gets the CSV storage file and store the property attributes inside the file.
		 * Filters out any null values, eg. There is an attribute that is empty/null, it
		 * will not be stored into excel.
		 * Uses generic collections to support the types of objects stored. 
		 */

		if (!StringUtils.isNullOrEmpty(title) && !StringUtils.isNullOrEmpty(price) && !StringUtils.isNullOrEmpty(area)
				&& !StringUtils.isNullOrEmpty(type) && !StringUtils.isNullOrEmpty(psf)
				&& !StringUtils.isNullOrEmpty(numBeds)) {

			final List<Property> PropertyList = session.get(MainCrawlerProgram.PROPERTY_KEY);
			final EntityCSVStorage<Property> csvStorage = session.get(MainCrawlerProgram.CSV_KEY);

			Property current_prop = new Property(title, price, type, area, psf, numBeds);
			PropertyList.add(current_prop);
			try {
				csvStorage.append(current_prop);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
