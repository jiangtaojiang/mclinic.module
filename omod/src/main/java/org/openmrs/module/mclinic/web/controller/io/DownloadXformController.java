package org.openmrs.module.mclinic.web.controller.io;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.mclinic.api.MclinicXform;
import org.openmrs.module.mclinic.api.service.MclinicService;
import org.openmrs.module.xforms.XformConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Provides mclinic download services.
 * 
 * @author Samuel Mbugua
 */

@Controller
@RequestMapping({"/module/mclinic/download/xform"})
public class DownloadXformController {
	private Log log = LogFactory.getLog(this.getClass());

	protected void getForm(HttpServletRequest request, HttpServletResponse response) {
		MclinicService mhs = (MclinicService) Context.getService(MclinicService.class);
		String strformId = request.getParameter("formId");
		String fileName = request.getParameter("file");
		if ("mclinic".equals(request.getParameter("type"))) {
			try {
				Integer formId = Integer.parseInt(strformId);
				MclinicXform xform = mhs.getDownloadableXformByFormId(formId);
				response.setHeader(XformConstants.HTTP_HEADER_CONTENT_DISPOSITION, XformConstants.HTTP_HEADER_CONTENT_DISPOSITION_VALUE + xform.getXformName());
				response.setHeader("Location", "/module/mclinic/");
				response.setCharacterEncoding(XformConstants.DEFAULT_CHARACTER_ENCODING);
				response.getWriter().print(xform.getXformXml());
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}else {
			//Here we hope that string fileName is not null
			try {
				MclinicXform xform = mhs.getDownloadableXformByName(fileName);
				response.setHeader(XformConstants.HTTP_HEADER_CONTENT_DISPOSITION, XformConstants.HTTP_HEADER_CONTENT_DISPOSITION_VALUE + xform.getXformName());
				response.setCharacterEncoding(XformConstants.DEFAULT_CHARACTER_ENCODING);
				response.getWriter().print(xform.getXformXml());
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	}
}