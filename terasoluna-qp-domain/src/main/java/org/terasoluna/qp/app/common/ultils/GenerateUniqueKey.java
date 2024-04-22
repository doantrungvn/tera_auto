package org.terasoluna.qp.app.common.ultils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.domain.model.MessageDesign;

public class GenerateUniqueKey {
	public final static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	private int maxlengthOfCode;
	private int maxlengthOfName;

	public GenerateUniqueKey(int pmaxlengthOfName, int pmaxlengthOfCode) {
		maxlengthOfCode = pmaxlengthOfCode;
		maxlengthOfName = pmaxlengthOfName;
	}

	public GenerateUniqueKey() {
	}
	
	public static String generateFolderName(String name) {
		StringBuilder strReturn = new StringBuilder(name);
		Date today = new Date();
		strReturn.append(formatter.format(today));

		return strReturn.toString();

	}

	public void setMaxlengthOfCode(int maxlengthOfCode) {
		this.maxlengthOfCode = maxlengthOfCode;
	}
	
	/**
	 * 
	 * @return string with format: yyyMMdd_random_uuid
	 * @author dungnn1
	 */
	public static String generateWithDatePrefix() {
		StringBuilder strReturn = new StringBuilder();
		Date today = new Date();
		strReturn.append(formatter.format(today));
		strReturn.append("_");
		strReturn.append(UUID.randomUUID().toString().replaceAll("-", ""));

		return strReturn.toString();
	}

	/**
	 * 
	 * @return random uuid
	 * @author dungnn1
	 */
	public static String generateAutoCode() {
		return (UUID.randomUUID().toString().replaceAll("-", ""));
	}

	/**
	 * 
	 * @return random String using SecureRandom
	 * @author dungnn1
	 */
	public static String generateUsingSecureRandom() {
		try {
			// cryptographically strong random number generator. Options:
			// NativePRNG or SHA1PRNG
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// generate a random number
			return new Long(secureRandom.nextLong()).toString().replace("-", "");
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	/**
	 * 
	 * @return string with format: yyyMMdd_random_uuid
	 * @author dungnn1
	 */
	public static String generateUsingSecureRandomWithDatePrefix() {
		try {
			// Strong random number generator. Options: NativePRNG or SHA1PRNG
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// generate a random number

			StringBuilder strReturn = new StringBuilder();
			Date today = new Date();
			strReturn.append(formatter.format(today));
			strReturn.append("_");
			strReturn.append(new Long(secureRandom.nextLong()).toString().replace("-", ""));
			return strReturn.toString();
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	public String generateAutoCode(String prefix, String content) {
		try {
			StringBuilder strReturn = new StringBuilder(prefix);
			strReturn.append(StringUtils.capitalize(content));

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// generate a random number
			String idTemp = new Integer(secureRandom.nextInt()).toString().replace("-", "");
			// num of character avaiable
			int lengthValid = maxlengthOfCode - StringUtils.length(idTemp) - 1;// add _ beforce random
			// calculate for code
			if (lengthValid < StringUtils.length(strReturn)) {
				strReturn.delete(lengthValid, strReturn.length());
			}
			strReturn.append("_");
			strReturn.append(idTemp);
			return FunctionCommon.removeMultiSpaces(strReturn.toString());
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	public String generateAutoName(String prefix, String content) {
		try {
			StringBuilder strReturn = new StringBuilder(prefix);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// generate a random number
			String idTemp = new Integer(secureRandom.nextInt()).toString().replace("-", "");
			// num of character avaiable
			int lengthValid = maxlengthOfName - StringUtils.length(idTemp) - 1;// add _ beforce random
			strReturn.append(content);

			// calculate strong
			if (lengthValid < StringUtils.length(strReturn)) {
				strReturn.delete(lengthValid, strReturn.length());
			}
			strReturn.append(" ");
			strReturn.append(idTemp);
			return FunctionCommon.removeMultiSpaces(strReturn.toString());
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	public String calculateCodeForAuto(String content, String suffix, boolean separate) {
		try {
			StringBuilder strReturn = new StringBuilder(content);
			int lengthValid = maxlengthOfCode - StringUtils.length(suffix);
			if (StringUtils.isNotBlank(suffix)) {
				lengthValid--;
				// calculate strong
				if (lengthValid < StringUtils.length(strReturn)) {
					strReturn.delete(lengthValid, strReturn.length());
				}
				if (separate) {
					appendIfMissing(strReturn, DbDomainConst.SEPARATE_LANGUAGE_COUNTRY);
				}
				strReturn.append(suffix);
			} else if (lengthValid < StringUtils.length(strReturn)) {
				strReturn.delete(lengthValid, strReturn.length());
			}
			return StringUtils.deleteWhitespace(strReturn.toString());
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	/**
	 * 
	 * @param prefix
	 * @param content
	 * @param suffix
	 * @return
	 */
	public String calculateCodeForAuto(String prefix, String content, String suffix) {
		StringBuilder strReturn = new StringBuilder(prefix);
		strReturn.append(StringUtils.capitalize(content));
		return calculateCodeForAuto(strReturn.toString(), suffix, true);
	}

	public String calculateCodeForAuto(String content, String suffix) {
		return calculateCodeForAuto(content, suffix, true);
	}

	public String calculateCodeForAutoWithOutSeparate(String content, String suffix) {
		return calculateCodeForAuto(content, suffix, false);
	}

	public String calculateCodeForManual(String content, String suffix, boolean separate) {
		try {
			StringBuilder strReturn = new StringBuilder(content);

			if (maxlengthOfCode >= StringUtils.length(strReturn)) {
				return FunctionCommon.removeMultiSpaces(strReturn.toString());
			}

			if (StringUtils.isBlank(suffix)) {
				suffix = generateRandomInteger();
			}

			int lengthValid = maxlengthOfCode - StringUtils.length(suffix);
			strReturn.delete(lengthValid, strReturn.length());
			if (separate) {
				strReturn.delete(strReturn.length() - 1, strReturn.length());
				appendIfMissing(strReturn, DbDomainConst.SEPARATE_LANGUAGE_COUNTRY);
			}

			strReturn.append(suffix);
			return StringUtils.deleteWhitespace(strReturn.toString());
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	public String calculateCodeForManual(String content, String suffix) {
		return calculateCodeForManual(content, suffix, true);
	}

	public String calculateCodeForManual(String content) {
		return calculateCodeForManual(content, generateRandomInteger(), true);
	}
	
	public String calculateCodeForManualWithOutSeparate(String content, String suffix) {
		return calculateCodeForManual(content, suffix, false);
	}

	public String calculateCodeForManual(String prefix, String content, String suffix) {
		StringBuilder strReturn = new StringBuilder(prefix);
		strReturn.append(StringUtils.capitalize(content));
		return calculateCodeForManual(strReturn.toString(), suffix, true);
	}

	/**
	 * 
	 * @param prefix
	 * @param content
	 * @param suffix
	 * @return
	 */
	public String calculateName(String prefix, String content, String suffix) {
		try {
			StringBuilder strReturn = new StringBuilder(prefix);
			strReturn.append(" ");
			strReturn.append(content);

			int lengthValid = maxlengthOfName - StringUtils.length(suffix);// add _ beforce random
			if (StringUtils.isNotBlank(suffix)) {
				lengthValid--;

				// calculate strong
				if (lengthValid < StringUtils.length(strReturn)) {
					strReturn.delete(lengthValid, strReturn.length());
				}

				appendIfMissing(strReturn, StringUtils.SPACE);
				
				strReturn.append(suffix);
			} else if (lengthValid < StringUtils.length(strReturn)) {
				strReturn.delete(lengthValid, strReturn.length());
			}
			return FunctionCommon.removeMultiSpaces(strReturn.toString());
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	public void appendIfMissing (StringBuilder strReturn, String charAp) {
		if (!StringUtils.endsWith(strReturn, charAp)) {
			strReturn.append(charAp);
		}
		
	}
	
	/**
	 * 
	 * @param content
	 * @param suffix
	 * @return
	 */
	public String calculateName(String content, String suffix) {
		try {
			StringBuilder strReturn = new StringBuilder(" ");
			strReturn.append(content);

			if (maxlengthOfName >= StringUtils.length(strReturn)) {
				return FunctionCommon.removeMultiSpaces(strReturn.toString());
			}

			if (StringUtils.isBlank(suffix)) {
				suffix = generateRandomInteger();
			}

			int lengthValid = maxlengthOfName - StringUtils.length(suffix) - 1;
			strReturn.delete(lengthValid, strReturn.length());
			appendIfMissing(strReturn, StringUtils.SPACE);
			strReturn.append(suffix);

			return FunctionCommon.removeMultiSpaces(strReturn.toString());
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	/**
	 * 
	 * @return
	 */
	public static String generateRandomInteger() {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			return new Integer(secureRandom.nextInt()).toString().replace("-", "");
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	public static String generateAutoCodeStatic(String prefix, String content) {
		try {
			StringBuilder strReturn = new StringBuilder(prefix);
			strReturn.append(content);

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// generate a random number
			String idTemp = new Integer(secureRandom.nextInt()).toString().replace("-", "");
			// num of character avaiable
			int lengthValid = DbDomainConst.MAX_LENGTH_OF_CODE - StringUtils.length(idTemp) - 1;// add . beforce random
			// calculate for code
			if (lengthValid < StringUtils.length(strReturn)) {
				strReturn.delete(lengthValid, strReturn.length());
			}
			strReturn.append(MessageDesign.STR_DOT);
			strReturn.append(idTemp);
			return StringUtils.deleteWhitespace(strReturn.toString());
		} catch (Exception e) {
			return (UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}

	public static void main(String[] args) {
		// System.out.println(GenerateUniqueKey.generateAutoCode("reg", "CodelistManagementAbcDefGhiJklMLDNDDLDLDLD"));
		/*
		 * System.out.println(GenerateUniqueKey.removeMultiSpaces(" abc  acb  acn    ")); System.out.println(GenerateUniqueKey.removeMultiSpaces("     ")); System.out.println(GenerateUniqueKey.removeMultiSpaces(null));
		 */

		/*System.out.println(FunctionCommon.removeMultiSpaces("  aa  bb   cc  "));
		
		StringBuilder strReturn = new StringBuilder("123");*/
		/*System.out.println(GenerateUniqueKey.appendIfMissing(strReturn, DbDomainConst.SEPARATE_LANGUAGE_COUNTRY) );*/
		
	}

}
