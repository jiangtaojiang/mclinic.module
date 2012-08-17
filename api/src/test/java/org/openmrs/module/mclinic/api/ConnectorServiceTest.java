package org.openmrs.module.mclinic.api;

import junit.framework.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.mclinic.api.service.MclinicService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link MclinicService}.
 */
public class ConnectorServiceTest extends BaseModuleContextSensitiveTest {

	@Test
	public void getConceptConfiguration_shouldSaveConceptConfigurationIntoTheDatabase() {
		ConceptConfiguration conceptConfiguration = new ConceptConfiguration();
		conceptConfiguration.setName("Concept Configuration");
		conceptConfiguration.setDescription("Concept Configuration Description");

		ConfiguredConcept configuredConcept = new ConfiguredConcept();
		configuredConcept.setConcept(Context.getConceptService().getConcept(20));
		configuredConcept.setConceptConfiguration(conceptConfiguration);

		conceptConfiguration.addConfiguredConcept(configuredConcept);

		Context.getService(MclinicService.class).saveConceptConfiguration(conceptConfiguration);
		Assert.assertNotNull(conceptConfiguration.getId());
		Assert.assertEquals("Concept Configuration", conceptConfiguration.getName());
		Assert.assertEquals("Concept Configuration Description", conceptConfiguration.getDescription());
		Assert.assertEquals(1, conceptConfiguration.getConfiguredConcepts().size());
	}

	@Test
	public void getConceptConfiguration_shouldReturnConceptConfigurationGivenAnId() {
		ConceptConfiguration conceptConfiguration = new ConceptConfiguration();
		conceptConfiguration.setName("Concept Configuration");
		conceptConfiguration.setDescription("Concept Configuration Description");

		ConfiguredConcept configuredConcept = new ConfiguredConcept();
		configuredConcept.setConcept(Context.getConceptService().getConcept(20));
		configuredConcept.setConceptConfiguration(conceptConfiguration);

		conceptConfiguration.addConfiguredConcept(configuredConcept);

		Context.getService(MclinicService.class).saveConceptConfiguration(conceptConfiguration);
		Assert.assertNotNull(conceptConfiguration.getId());
		Assert.assertEquals("Concept Configuration", conceptConfiguration.getName());
		Assert.assertEquals("Concept Configuration Description", conceptConfiguration.getDescription());
		Assert.assertEquals(1, conceptConfiguration.getConfiguredConcepts().size());

		Integer id = conceptConfiguration.getId();
		ConceptConfiguration savedConceptConfiguration = Context.getService(MclinicService.class).getConceptConfiguration(id);
		Assert.assertNotNull(savedConceptConfiguration);
		Assert.assertEquals("Concept Configuration", conceptConfiguration.getName());
		Assert.assertEquals("Concept Configuration Description", conceptConfiguration.getDescription());
		Assert.assertEquals(1, conceptConfiguration.getConfiguredConcepts().size());
	}
}
