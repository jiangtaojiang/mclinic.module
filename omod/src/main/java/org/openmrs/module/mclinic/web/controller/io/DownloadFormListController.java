package org.openmrs.module.mclinic.web.controller.io;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.mclinic.api.MclinicXform;
import org.openmrs.module.mclinic.api.service.MclinicService;
import org.openmrs.module.xforms.XformConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Creates an formList XMl from which a phone will get resources
 * 
 * @author Samuel Mbugua
 */

@Controller
@RequestMapping({"/module/mobilehelper/download/xformList"})
public class DownloadFormListController {
	private static Log log = LogFactory.getLog(DownloadFormListController.class);
	private static final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder;
	
	@RequestMapping(method=RequestMethod.GET)
	protected void getFormList(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			MclinicService mhs = (MclinicService) Context.getService(MclinicService.class);
			List<MclinicXform> xformsList = mhs.getAllDownloadableXforms();

			docBuilder = docBuilderFactory.newDocumentBuilder();

			String xml = "<?xml version='1.0' encoding='UTF-8' ?>";
			if("mclinic".equalsIgnoreCase(request.getParameter("type"))){
				xml += "\n<xforms>";
				for (MclinicXform mclinicXform : xformsList) {
					Document doc = docBuilder.parse(IOUtils.toInputStream(mclinicXform.getXformXml()));
					NodeList nodeList = doc.getElementsByTagName("form");
					Node rootNode= nodeList.item(0);
					NamedNodeMap nodeMap=rootNode.getAttributes();
					String formId = nodeMap.getNamedItem("id").getNodeValue();
					String fileName = mclinicXform.getXformName();
					fileName=formatXmlString(fileName);
					xml += "\n  <xform>";
					xml += "\n <id>" + formId + "</id>";
					xml += "\n <name>" + fileName.replace('_', ' ').substring(0,fileName.lastIndexOf(".")) + "</name>";
					xml += "</xform>";
				}
				xml += "\n</xforms>";
			} else {
				xml += "\n<forms>";
				for (MclinicXform mclinicXform : xformsList) {
					String fileName = mclinicXform.getXformName();
					fileName=formatXmlString(fileName);
					String url = request.getRequestURL().toString();
					String fileUrl = url.substring(0, url.lastIndexOf('/') + 1);
					fileUrl += "form.form?file==";
					xml += "\n  <form ";
					xml += "url=\"" + fileUrl + fileName + "\">";
					xml += fileName.replace('_', ' ').substring(0,fileName.lastIndexOf("."));
					xml += "</form>";
				}
				xml += "\n</forms>";
			}
			response.setHeader("Location", "/module/mclinic/");
			response.setContentType(XformConstants.HTTP_HEADER_CONTENT_TYPE_XML);
			response.getOutputStream().print(xml);
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}
	
	private String formatXmlString(String aString){
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aString);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){
			if (character == '<') {
				result.append("&lt;");
			}
			else if (character == '>') {
				result.append("&gt;");
			}
			else if (character == '\"') {
				result.append("&quot;");
			}
			else if (character == '\'') {
				//result.append("&#039;");
				//result.append("&apos;");
				result.append(character);
			}
			else if (character == '&') {
				result.append("&amp;");
			}
			else {
				//the char is not a special one
				//add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
}