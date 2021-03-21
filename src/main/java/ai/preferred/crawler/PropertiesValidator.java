package ai.preferred.crawler;

import ai.preferred.venom.request.Request;
import ai.preferred.venom.response.Response;
import ai.preferred.venom.response.VResponse;
import ai.preferred.venom.validator.Validator;

/**
 * This class file is to validate if the webpage contains the word 'Properties'.
 * 
 * @author Zhei Wei
 * @author Aneesha
 */

public class PropertiesValidator implements Validator{

	public Validator.Status isValid(Request request, Response response) {
		final VResponse vResponse = new VResponse(response);

		/**
		 * Checks for listing validity by checking for keywords in HTML
		 * 
		 * @return status valid
		 */
		if (vResponse.getHtml().contains("Properties")) {
			return Status.VALID;
		}

		/**
		 * @return status invalid
		 */
		return Status.INVALID_CONTENT;
	}
}
