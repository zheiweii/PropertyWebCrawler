package ai.preferred.crawler.SRX.master;

import ai.preferred.crawler.PropertiesValidator;
import ai.preferred.venom.Crawler;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.SleepScheduler;
import ai.preferred.venom.Worker;
import ai.preferred.venom.fetcher.AsyncFetcher;
import ai.preferred.venom.fetcher.Fetcher;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.request.VRequest;
import ai.preferred.venom.response.VResponse;
import ai.preferred.venom.validator.EmptyContentValidator;
import ai.preferred.venom.validator.StatusOkValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This is the main page for handling the main SRX property page. It gets the
 * URL to each property listing on the page so that the data can be crawled from
 * the second page
 * 
 * @author Aneesha
 *
 */

public class ListingMainPageHandler implements Handler {
	/**
	 * Gets elements from the specified tag in the HTML page
	 */
	public void handle(Request request, VResponse vResponse, Scheduler scheduler, Session session, Worker worker) {

		Elements elements = vResponse.getJsoup().select("a[class~=listingPhoto]");

		/**
		 * Starts the new crawler session by adding new ListingHander()
		 */
		for (Element element : elements) {
			try (Crawler crawler = createCrawler(createFetcher(), session).start()) {
				String secondUrl = element.attr("abs:href");
				scheduler.add(new VRequest(secondUrl), new ListingHandler());
			} catch (Exception e) {
				System.out.println("Could not run crawler: ");
			}
		}

		/**
		 * Automatically goes to the next page of listing and crawls till the page limit
		 * set
		 */
		Elements next = vResponse.getJsoup().select("a[aria-label~=Next]");
		String nextlink = next.attr("abs:href");
		if (nextlink.equals("https://www.srx.com.sg/singapore-property-listings/property-for-sale?page=7")) {
			return;
		} else {
			System.out.println(nextlink);
			scheduler.add(new VRequest(nextlink), this);

		}
	}

	/**
	 * Creates a new fetcher which creates the validators
	 */
	private static Fetcher createFetcher() {
		return AsyncFetcher.builder()
				.setValidator(new EmptyContentValidator(), new StatusOkValidator(), new PropertiesValidator()).build();
	}

	/**
	 * Creates a new crawler session
	 * 
	 * @param fetcher
	 * @param session
	 */
	private static Crawler createCrawler(Fetcher fetcher, Session session) {
		return Crawler.builder().setFetcher(fetcher).setSession(session)
				.setSleepScheduler(new SleepScheduler(1500, 3000)).build();
	}
}
