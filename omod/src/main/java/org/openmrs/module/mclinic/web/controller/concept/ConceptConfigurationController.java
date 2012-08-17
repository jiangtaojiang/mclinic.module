package org.openmrs.module.mclinic.web.controller.concept;

import org.openmrs.api.context.Context;
import org.openmrs.module.mclinic.api.ConceptConfiguration;
import org.openmrs.module.mclinic.api.service.MclinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConceptConfigurationController {

	@RequestMapping(value = "/module/mclinic/concept/conceptConfiguration", method = RequestMethod.GET)
	public void preparePage(final @RequestParam(value = "uuid", required = false) String uuid,
	                        final Model model) {
		MclinicService service = Context.getService(MclinicService.class);
		ConceptConfiguration conceptConfiguration = service.getConceptConfigurationByUuid(uuid);
		model.addAttribute("configuration", conceptConfiguration);
	}

	@RequestMapping(value = "/module/mclinic/concept/conceptConfiguration", method = RequestMethod.POST)
	public String process(final @RequestParam(value = "name", required = true) String name,
	                      final @RequestParam(value = "description", required = true) String description,
	                      final @RequestParam(value = "configurationUuid", required = false) String configurationUuid) {
		MclinicService service = Context.getService(MclinicService.class);

		ConceptConfiguration conceptConfiguration = service.getConceptConfigurationByUuid(configurationUuid);
		if (conceptConfiguration == null)
			conceptConfiguration = new ConceptConfiguration();
		conceptConfiguration.setName(name);
		conceptConfiguration.setDescription(description);

		service.saveConceptConfiguration(conceptConfiguration);

		return "redirect:manageConcept.form?uuid=" + conceptConfiguration.getUuid();
	}

	@RequestMapping(value = "/module/mclinic/concept/deleteConfiguration", method = RequestMethod.GET)
	public
	@ResponseBody
	Boolean delete(final @RequestParam(value = "uuid", required = false) String uuid) {
		Boolean deleted = Boolean.FALSE;
		MclinicService service = Context.getService(MclinicService.class);
		ConceptConfiguration conceptConfiguration = service.getConceptConfigurationByUuid(uuid);
		if (conceptConfiguration != null) {
			conceptConfiguration.setRetired(Boolean.TRUE);
			service.saveConceptConfiguration(conceptConfiguration);
			deleted = Boolean.TRUE;
		}
		return deleted;
	}
}