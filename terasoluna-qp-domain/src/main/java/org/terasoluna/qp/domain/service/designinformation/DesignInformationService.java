/*
 * @(#)ProjectService.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.service.designinformation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.DesignInformation;
import org.terasoluna.qp.domain.repository.designinformation.DesignInformationCriteria;

@Service
public interface DesignInformationService {

	/**
	 * Finds all project information with search criteria
	 * 
	 * @param criteria
	 *            Project criteria
	 * @return List of all project
	 */
	Page<DesignInformation> searchDesignInformation(DesignInformationCriteria criteria, Pageable pageable);

	void register(DesignInformation designInformation);

	void modify(DesignInformation designInformation);

	DesignInformation findDesignInformationById(DesignInformation designInformation);

	DesignInformation deleteDesignInformation(DesignInformation designInformation);
}
