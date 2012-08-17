package org.openmrs.module.mclinic.web.controller.concept;

import org.openmrs.api.context.Context;
import org.openmrs.module.mclinic.api.service.MclinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/module/mclinic/concept/configurationList")
public class ConfigurationListController {

	@RequestMapping(method = RequestMethod.GET)
	public void preparePage(final Model model) {
		model.addAttribute("configurations", Context.getService(MclinicService.class).getConceptConfigurations());
	}
}