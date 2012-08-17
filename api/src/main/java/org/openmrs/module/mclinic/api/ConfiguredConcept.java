package org.openmrs.module.mclinic.api;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;

public class ConfiguredConcept extends BaseOpenmrsMetadata {

	private Integer id;

	private Concept concept;

	private ConceptConfiguration conceptConfiguration;

	/**
	 * @return id - The unique Identifier for the object
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * @param id - The unique Identifier for the object
	 */
	@Override
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Return the concept
	 *
	 * @return
	 */
	public Concept getConcept() {
		return concept;
	}

	/**
	 * Set the concept
	 *
	 * @param concept the concept
	 */
	public void setConcept(final Concept concept) {
		this.concept = concept;
	}

	/**
	 * @return the concept configuration
	 */
	public ConceptConfiguration getConceptConfiguration() {
		return conceptConfiguration;
	}

	/**
	 * Set the concept configuration
	 *
	 * @param conceptConfiguration the concept configuration
	 */
	public void setConceptConfiguration(final ConceptConfiguration conceptConfiguration) {
		this.conceptConfiguration = conceptConfiguration;
	}
}
