package ai.preferred.crawler.NinetyNine.master;

import ai.preferred.venom.Crawler;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.request.VRequest;
import ai.preferred.venom.response.VResponse;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This is the main page for handling 99co Getting the URL to each property
 * listing on the page Getting the URL to the next page of the webpage
 * 
 * @author Zhei Wei
 *
 */

public class HouseMainPageHandler implements Handler {

	@Override
	public void handle(Request request, VResponse vResponse, Scheduler scheduler, Session session, Worker worker) {

		/**
		 * The selector crawls for the URL to get to the next page of the webpage. It
		 * stops when the page number hits 15.
		 */

		Elements next = vResponse.getJsoup().select("#appContent > div > div > div > div > ul > li.next > a");
		String nextlink = next.attr("abs:href");

		if (nextlink.equals("https://www.99.co/singapore/sale?page_num=15")) {
			System.out.println("stopping crawler");
			return;

		} else {
			System.out.println(nextlink);
			scheduler.add(new VRequest(nextlink), this);
			
			/**
			 * Proceeds with nextlink URL and crawls for the URL of each property listing in the current page.
			 * Repeats the process in getting the URL of each property listing in the every nextlink URL. 
			 * URL for each property listing is passed on to Specs Handler to crawl for individual attributes.
			 */
			
			Elements elements = vResponse.getJsoup().select("#appContent > div > div > div > div > div > div > div > a");
			for (Element element : elements) {
				String link = element.attr("abs:href");
				scheduler.add(new VRequest(link), new HouseSpecHandler());

			}

		}
	}
}
