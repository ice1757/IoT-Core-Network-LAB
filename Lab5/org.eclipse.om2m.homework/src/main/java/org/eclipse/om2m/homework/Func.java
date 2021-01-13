package org.eclipse.om2m.homework;

import java.math.BigInteger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
//import org.eclipse.om2m.homework.RequestSender;
import org.eclipse.om2m.homework.hw_ipeConstants;

public class Func {

	private static Log LOGGER = LogFactory.getLog(Func.class); 

	public static void createResources(String appId, String poa) {
		// Create the Application resource
		Container container = new Container();
		container.getLabels().add("homework");
		container.setMaxNrOfInstances(BigInteger.valueOf(0));
		
		AE ae = new AE();
		ae.setRequestReachability(true);
		ae.getPointOfAccess().add(poa);
		ae.setAppID(appId);
		ae.setName(appId);
		
		ResponsePrimitive response = RequestSender.createAE(ae);
		// Create Application sub-resources only if application not yet created
		if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			container = new Container();
			container.setMaxNrOfInstances(BigInteger.valueOf(10));
			// Create STATE container sub-resource
			container.setName(hw_ipeConstants.DATA);
			LOGGER.info(RequestSender.createContainer(response.getLocation(), container));
		}
	}
}