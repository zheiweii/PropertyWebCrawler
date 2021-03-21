package ai.preferred.crawler.SRX.master;

import ai.preferred.crawler.EntityCSVStorage;
import ai.preferred.crawler.MainCrawlerProgram;
import ai.preferred.crawler.entity.Property;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.response.VResponse;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

/**
 * This page is responsible for handling the SRX listings. It checks if there
 * are listings and gets the CSV printer
 * 
 * @author Aneesha
 *
 */

public class ListingHandler implements Handler {

	/**
	 * Get logger to print out the validity status
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ListingHandler.class);

	public void handle(Request request, VResponse response, Scheduler scheduler, Session session, Worker worker) {

		/**
		 * Get logger to print out the info
		 */
		LOGGER.info("processing: {}", request.getUrl());

		final Document document = response.getJsoup();
		final List<Property> propertyList = ListingParser.parseListing(document);

		/**
		 * Checks if there are listings and prints that there are no results
		 */
		if (propertyList.isEmpty()) {
			LOGGER.info("there is no results on this page, stopping: {}", request.getUrl());
			return;
		}

		/**
		 * Gets the CSV printer
		 */
		final EntityCSVStorage<Property> storage = session.get(MainCrawlerProgram.CSV_KEY);

		/**
		 * Executes this wrapper for every IO task to maintain the CPU utilization to
		 * speed up crawling
		 */
		worker.executeBlockingIO(() -> {
			for (final Property p : propertyList) {
				LOGGER.info("storing property: {} [{}]", p.getTitle());
				try {
					storage.append(p);
				} catch (IOException e) {
					LOGGER.error("Unable to store listing.", e);
				}
			}
		});

	}

}
