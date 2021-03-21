package analyticsChart;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Lee Lin
 * 
 */

class AnalyticsTest {

Analytics analytics;
	
	@BeforeEach
	void setUp() throws Exception{
		analytics = new Analytics();
	}
	
	@AfterEach
	void tearDown() throws Exception{
	}

	@Test
	void testcalculate_total() {
		assertEquals(250, analytics.calculate_total(200, 50));
	}
	
	@Test
	void testcalculate_max() {
		assertEquals(500, analytics.calculate_max(500, 20));
	}
	
	@Test
	void testcalculate_min() {
		assertEquals(50, analytics.calculate_min(300, 50));
	}
	
	@Test
	void testcalculate_avg() {
		assertEquals(100, analytics.calculate_avg(200, 2));
	}
	
	@Test
	void testround() {
		assertEquals(15.45, analytics.round(15.445));
	}
	
	@Test
	void testprice_bar() {
		File file = new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\data\\Pricing.jpeg");
		boolean actual = false;
		if(file.exists())
			actual = true;
		assertEquals(true,actual);
	}
	
	@Test
	void testsize_bar() {
		File file = new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\data\\Size.jpeg");
		boolean actual = false;
		if(file.exists())
			actual = true;
		assertEquals(true,actual);
	}

	@Test
	void testtype_pie() {
		File file = new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\data\\Types.jpeg");
		boolean actual = false;
		if(file.exists())
			actual = true;
		assertEquals(true,actual);
	}

	@Test
	void testroom_pie() {
		File file = new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\data\\Bedrooms.jpeg");
		boolean actual = false;
		if(file.exists())
			actual = true;
		assertEquals(true,actual);
	}
}
