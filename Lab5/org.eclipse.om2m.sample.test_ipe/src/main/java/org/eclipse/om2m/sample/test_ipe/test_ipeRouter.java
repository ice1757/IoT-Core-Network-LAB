package org.eclipse.om2m.sample.test_ipe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.sample.test_ipe.constants.test_ipeConstants;
import org.eclipse.om2m.sample.test_ipe.controller.test_ipeController;

public class test_ipeRouter implements InterworkingService {

	private static Log LOGGER = LogFactory.getLog(test_ipeRouter.class);

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		// handle the user command
		ResponsePrimitive response = new ResponsePrimitive(request);
		if (request.getQueryStrings().containsKey("op")) {
			// �qpostman�Ǫ�parameter�N��op�Bvalue
			String operation = request.getQueryStrings().get("op").get(0);
			String value = "";
			try {
				value = request.getQueryStrings().get("value").get(0);
			} catch (Exception e) {
				response.setContent("OK");
			}

			LOGGER.info("Received request in test_ipe: op=" + operation + " ; value=" + value);
			switch (operation) {
			case "off":
				test_ipeController.setAirConOFF();
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case "on":
				// write your code here
				test_ipeController.setAirConON();
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case "set_temp":
				// write your code here
				if (value.equals("plus") || value.equals("minus")) {
					response.setResponseStatusCode(test_ipeController.setTemp(value) == true ? ResponseStatusCode.OK
							: ResponseStatusCode.BAD_REQUEST);
				} else
					response.setContent("�S����operation");
				break;
			case "set_fan":
				// write your code here
				if (value.equals("plus") || value.equals("minus")) {
					response.setResponseStatusCode(test_ipeController.setFan(value) == true ? ResponseStatusCode.OK
							: ResponseStatusCode.BAD_REQUEST);
				} else
					response.setContent("�S����operation");
				break;
			case "get_state":
				String content = test_ipeController.getAirConState();
				response.setContent(content);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			default:
				throw new BadRequestException();
			}
		}
		if (response.getResponseStatusCode() == null) {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}

	@Override
	public String getAPOCPath() {
		return test_ipeConstants.POA;
	}
}