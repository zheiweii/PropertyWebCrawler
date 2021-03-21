package ai.preferred.crawler.SRX.master;

import ai.preferred.crawler.entity.Property;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.response.VResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for crawling the attributes of the property. It contains the
 * selector for all property attributes.
 * 
 * @author Aneesha
 *
 */

public class ListingParser implements Handler {

	/**
	 * Get logger to print out the validity status
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ListingParser.class);

	/**
	 * Selects elements from the HTML page which are inside the given div
	 */
	static List<Property> parseListing(Document document) {
		final Elements properties = document.select("div[id~=about-this-property-div]");
		final ArrayList<Property> result = new ArrayList<>(properties.size());
		for (final Element p : properties) {
			result.add(parseProperty(p));
		}
		return result;
	}

	/**
	 * Checks if the element is a string or a null value
	 */
	private static String textOrNull(Element element) {
		return null == element ? null : element.text();
	}

	/**
	 * Selects the elements from the respective HTML tags for each property and
	 * assigns it to the respective variables
	 * 
	 * @return property
	 */
	private static Property parseProperty(Element e) {

		final Property property = new Property();
		property.setTitle(textOrNull(e.select("div[id~=listing-name]").first()).trim());
		property.setType(textOrNull(e.select("div[id~=property-type]").first()).trim());
		property.setNumBeds(textOrNull(e.select("div[class~=col-7 col-md-6 col-xl-8 listing-about-main-value]").get(2))
				.replaceAll("[a-zA-Z!@#$%^&*()/,?\":{}|<>+]", "").trim());
		property.setPrice(
				textOrNull(e.select("div[class~=col-7 col-md-6 col-xl-8 listing-about-main-value]:contains($)").first())
						.replaceAll("[\\D]", ""));
		property.setArea(textOrNull(e.select("span[itemprop~=value]:contains(sqft)").first()).replaceAll("[\\D]", ""));
		property.setPsf(textOrNull(e.select("span[itemprop~=value]:contains(psf)").first()).replaceAll("[\\D]", ""));
		LOGGER.error(property.getTitle());
		return property;
	}


	public void handle(Request request, VResponse response, Scheduler scheduler, Session session, Worker worker) {
		// TODO Auto-generated method stub

	}

}
