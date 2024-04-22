package org.terasoluna.qp.app.licensemanagement;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.LicenseDesign;

@Component
public class LicenseManagementFunction {
	public LicenseDesign mapFromFormToObject(LicenseManagementForm licenseDesignForm) {
		LicenseDesign licenseDesign = new LicenseDesign();
		if (licenseDesignForm.getNum() != "") {
			licenseDesign.setNum(Integer.parseInt(licenseDesignForm.getNum()));
		}
		if (licenseDesignForm.getStartDate() != null) {
			licenseDesign.setStartDate(Timestamp.valueOf(licenseDesignForm.getStartDate()));
		}
		if (licenseDesignForm.getExpiredDate() != null) {
			licenseDesign.setExpiredDate(Timestamp.valueOf(licenseDesignForm.getExpiredDate()));
		}
		return licenseDesign;
	}

	public LicenseManagementForm mapFromObjectToForm(LicenseDesign licenseDesign) {
		LicenseManagementForm licenseDesignForm = new LicenseManagementForm();
		licenseDesignForm.setNum(Integer.toString(licenseDesign.getNum()));
		licenseDesignForm.setStartDate(licenseDesignForm.getStartDate().toString());
		licenseDesignForm.setExpiredDate(licenseDesignForm.getExpiredDate().toString());
		return licenseDesignForm;
	}

	public LicenseDesign mapFromFormToObjectViewPage(LicenseManagementForm licenseDesignForm) {
		LicenseDesign licenseDesign = new LicenseDesign();
		licenseDesign.setNum(Integer.parseInt((licenseDesignForm.getNum() == null) ? "0" : licenseDesignForm.getNum()));
		licenseDesign.setStartDate(Timestamp.valueOf((licenseDesignForm.getStartDate() == null) ? "0" : licenseDesignForm.getStartDate()));
		licenseDesignForm.setExpiredDate(licenseDesignForm.getExpiredDate().toString());
		return licenseDesign;
	}
}
