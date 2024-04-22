package org.terasoluna.qp.app.domaindesign;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;

public class DomainDesignUtil {
	/**
	 * 
	 * @param objOne
	 * @param objTwo
	 * @param type
	 * @return 0: equal - 1: objOne > objTwo 1: objOne < objTwo
	 * @throws Exception
	 */
	public static int compareObject(String objOne, String objTwo, Integer dataType) throws Exception {
		int iReturn = 0;

		if (DomainDatatypeUtil.isEmpty(objOne) || DomainDatatypeUtil.isEmpty(objTwo)) {
			return 0;
		}

		if (DbDomainConst.PhysicalDataType.INTEGER.equals(dataType) || DbDomainConst.PhysicalDataType.DECIMAL.equals(dataType) || DbDomainConst.PhysicalDataType.CURRENCY.equals(dataType)) {

			try {
				BigDecimal objOneVal = new BigDecimal(objOne);
				BigDecimal objTwoVal = new BigDecimal(objTwo);
				return objOneVal.compareTo(objTwoVal);
			} catch (Exception e) {
				throw new Exception(CommonMessageConst.ERR_SYS_0100);
			}

		} else if (DbDomainConst.PhysicalDataType.DATE.equals(dataType)) {
			try {
				String pattern = DateUtils.getPatternDate(SessionUtils.getCurrentAccountProfile().getDateFormat());
				Date objOneVal = DateUtils.parse(objOne, pattern);
				Date objTwoVal = DateUtils.parse(objTwo, pattern);

				return objOneVal.compareTo(objTwoVal);
			} catch (ParseException e) {
				throw new Exception(CommonMessageConst.ERR_SYS_0005);
			}

		} else if (DbDomainConst.PhysicalDataType.DATETIME.equals(dataType)) {

			try {
				String pattern = DateUtils.getPatternDateTime(SessionUtils.getCurrentAccountProfile().getDateTimeFormat());
				Date objOneVal = DateUtils.parse(objOne, pattern);
				Date objTwoVal = DateUtils.parse(objTwo, pattern);

				return objOneVal.compareTo(objTwoVal);
			} catch (ParseException e) {
				throw new Exception(CommonMessageConst.ERR_SYS_0005);
			}

		} else if (DbDomainConst.PhysicalDataType.TIME.equals(dataType)) {

			try {
				String pattern = DateUtils.getPatternTime(SessionUtils.getCurrentAccountProfile().getTimeFormat());
				Date objOneVal = DateUtils.parse(objOne, pattern);
				Date objTwoVal = DateUtils.parse(objTwo, pattern);

				return objOneVal.compareTo(objTwoVal);
			} catch (ParseException e) {
				throw new Exception(CommonMessageConst.ERR_SYS_0005);
			}

		}

		return iReturn;
	}

	public static void main(String[] args) {
		try {
			// System.out.println(DomainDesignUtil.compareObject("1.2", "2.1", DbDomainConst.PhysicalDataType.INTEGER));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
