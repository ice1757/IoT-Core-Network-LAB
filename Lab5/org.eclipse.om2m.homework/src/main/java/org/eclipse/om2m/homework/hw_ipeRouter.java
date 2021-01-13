package org.eclipse.om2m.homework;

import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Str;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.eclipse.om2m.homework.hw_ipeConstants;

public class hw_ipeRouter implements InterworkingService {

	private static Log LOGGER = LogFactory.getLog(hw_ipeRouter.class);

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		// handle the user command
		ResponsePrimitive response = new ResponsePrimitive(request);
		String t1 = request.getContent() + "";
		System.out.println("----------------getContent-----------------");
		String t2 = "";
		t2 = t1.substring(t1.indexOf("<con>"), t1.lastIndexOf("</con>") + 6);
//		t2 = t2.substring(5);

		String[] spl = t2.split("&quot;");
		String name = spl[1];
		String val = spl[3];
		System.out.println("----------------getContent-----------------" + t2);

		DocumentBuilder builder;
		InputSource src;
		Document doc = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			src = new InputSource();
			src.setCharacterStream(new StringReader(request.getContent().toString()));
			doc = builder.parse(src);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String con = doc.getElementsByTagName("con").item(0).getTextContent();
		System.out.println("---------------------con----------------\n"+con);
		
		// Send the information to the CSE
		String targetID = "";
		// write your code here (targetID = ?)
		targetID = hw_ipeConstants.CSE_PREFIX + "/" + hw_ipeConstants.AE_NAME + "/" + hw_ipeConstants.DATA;

		ContentInstance cin = new ContentInstance();
//		Obj obj = new Obj();
		// write your code here
//		obj.add(new Str(name, val));
		cin.setContent(con);
		cin.setContentInfo(MimeMediaType.OBIX + ":" + MimeMediaType.ENCOD_PLAIN);
		RequestSender.createContentInstance(targetID, cin);

		response.setResponseStatusCode(ResponseStatusCode.OK);
		return response;
	}

	@Override
	public String getAPOCPath() {
		return hw_ipeConstants.POA;
	}
}