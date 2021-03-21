package ai.preferred.crawler;


import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Testing {
	
Testing testing;

	@BeforeEach
	void setUp() throws Exception{
		testing = new Testing();
	}
	
	@AfterEach
	void tearDown() throws Exception{
	}

	@Test
	void testCSV() {
		File file = new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\data\\property.csv");
		boolean actual = false;
		if(file.exists())
			actual = true;
		assertEquals(true,actual);
	}
	
	@Test
	void testHTML() {
		File file = new File("C:\\Users\\Public\\eclipse-workspace\\WebCrawler-master\\data\\home.html");
		boolean actual = false;
		if(file.exists())
			actual = true;
		assertEquals(true,actual);
	}

}
