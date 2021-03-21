package ai.preferred.crawler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ai.preferred.crawler.entity.Property;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * This class file is for storing the data crawled into Excel file (.csv) 
 * Prints out data with the header removed. 
 * This class file will be called by Property Handler file (SRX.ListingHandler and NinetyNine.HouseSpecHandler)
 * @author Zhei Wei
 * @author Aneesha 
 * 
 * @param <T> for the property attributes
 */

public class EntityCSVStorage<T> implements AutoCloseable {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityCSVStorage.class);

	private final CSVPrinter printer;

	private boolean hasHeader;

	/**
	 * Stores data into excel file (.csv)
	 * @param file excel csv
	 * @param propertyClass property class
	 * @throws IOException interrupted I/O operations 
	 */
	
	public EntityCSVStorage(String file, Class<Property> propertyClass) throws IOException {
		printer = new CSVPrinter(new FileWriter(file), CSVFormat.EXCEL);
		hasHeader = false;
	}

	private static List<String> getHeaderList(final Class<?> clazz) {
		final List<String> result = new ArrayList<>();
		for (final Field field : clazz.getDeclaredFields()) {
			result.add(field.getName());
		}
		return result;
	}

	private List<Object> toList(final Object object) throws IllegalAccessException {
		final Field[] fields = object.getClass().getDeclaredFields();
		final List<Object> result = new ArrayList<>();
		for (final Field field : fields) {
			field.setAccessible(true);
			result.add(field.get(object));
		}
		return result;
	}
	
	/**
	 * Appends data
	 * @param object property attributes
	 * @throws IOException interrupted I/O operations 
	 */
	public synchronized void append(final T object) throws IOException {
		if (!hasHeader) {
			printer.flush();
			hasHeader = true;
		}

		try {
			printer.printRecord(toList(object));
			printer.flush();
			LOGGER.debug("Appended {}", object.hashCode());
		} catch (IllegalAccessException e) {
			throw new IOException("unable to store property: ", e);
		}
	}

	@Override
	public void close() throws IOException {
		printer.close(true);
	}

}
