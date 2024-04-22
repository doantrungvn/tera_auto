package org.terasoluna.qp.app.common.ultils;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.terasoluna.qp.app.message.CommonMessageConst;

/**
 * check permission of user login
 * @author dungnn1
 *
 */
public class PermissionUtils {

	/**
	 * 
	 * @param roleName
	 * @return
	 */
	public static boolean hasRole(String roleName) {
		List<? extends GrantedAuthority> lstPermission = SessionUtils.getCurrentAccount().getAuthorities();
		boolean bHasRole = false;
		for (GrantedAuthority objPermission : lstPermission) {
			if (objPermission.toString().equals(roleName)) {
				bHasRole = true;
				break;
			}
		}

		return bHasRole;
	}
	
	/**
	 * check user has permission delete object has foreign key
	 */
	public static boolean deleteObjectHasFk() {
		return hasRole(CommonMessageConst.PERMISSION_DELETE_FK);
	}
}
