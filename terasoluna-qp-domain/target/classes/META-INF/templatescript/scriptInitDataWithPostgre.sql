INSERT INTO qp_account(account_id, username, password_, account_non_expired, account_non_locked, credentials_non_expired, created_date, updated_date) VALUES(1, 'admin', '$2a$10$w0BUx8etUKdn9bfTzgciee7SHbe1OdtfdYLa/Ix3P54PDAIXJoniu', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO qp_role(role_id, role_name, role_code, status, remark, created_by, created_date, updated_by, updated_date) VALUES (1, 'Administrator', 'administrator', 1, null, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);
INSERT INTO qp_account_role(role_id, account_id, created_by, created_date, updated_by, updated_date) VALUES(1, 1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountruledefinitionSearch','accountruledefinitionSearch','tqp.accountruledefinition',1,'sc.permission.accountruledefinitionSearch.remark','/accountruledefinition/search');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountruledefinitionRegister','accountruledefinitionRegister','tqp.accountruledefinition',2,'sc.permission.accountruledefinitionRegister.remark','/accountruledefinition/register');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountruledefinitionView','accountruledefinitionView','tqp.accountruledefinition',3,'sc.permission.accountruledefinitionView.remark','/accountruledefinition/view');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountruledefinitionModify','accountruledefinitionModify','tqp.accountruledefinition',4,'sc.permission.accountruledefinitionModify.remark','/accountruledefinition/modify');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountruledefinitionDelete','accountruledefinitionDelete','tqp.accountruledefinition',5,'sc.permission.accountruledefinitionDelete.remark','/accountruledefinition/delete');

INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountprofileModifytheme','accountprofileModifytheme','tqp.accountprofile',1,'sc.permission.accountprofileModifytheme.remark','/accountprofile/modifyTheme');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountprofileSetdefaulttheme','accountprofileSetdefaulttheme','tqp.accountprofile',2,'sc.permission.accountprofileSetdefaulttheme.remark','/accountprofile/setDefaultTheme');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountprofileModifysystemsetting','accountprofileModifysystemsetting','tqp.accountprofile',3,'sc.permission.accountprofileModifysystemsetting.remark','/accountprofile/modifySystemSetting');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountprofileView','accountprofileView','tqp.accountprofile',3,'sc.permission.accountprofileView.remark','/accountprofile/view');

INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountroleView','accountroleView','tqp.accountrole',1,'sc.permission.accountroleView.remark','/accountrole/view');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountroleModify','accountroleModify','tqp.accountrole',1,'sc.permission.accountroleModify.remark','/accountrole/modify');

INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.roleSearch','roleSearch','tqp.rolemanagement',1,'sc.permission.roleSearch.remark','/role/search');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.roleRegister','roleRegister','tqp.rolemanagement',2,'sc.permission.roleRegister.remark','/role/register');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.roleView','roleView','tqp.rolemanagement',3,'sc.permission.roleView.remark','/role/view');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.roleModify','roleModify','tqp.rolemanagement',4,'sc.permission.roleModify.remark','/role/modify');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.roleDelete','roleDelete','tqp.rolemanagement',5,'sc.permission.roleDelete.remark','/role/delete');

INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.messageSearch','messageSearch','tqp.message',2,'sc.permission.messageSearch.remark','/message/search');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.messageModify','messageModify','tqp.message',3,'sc.permission.messageModify.remark','/message/modify');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.messageReload','messageReload','tqp.message',4,'sc.permission.messageReload.remark','/message/reaload');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountSearch','accountSearch','tqp.account',2,'sc.permission.accountSearch.remark','');

INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountRegister','accountRegister','tqp.account',7,'sc.permission.accountRegister.remark','');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountView','accountView','tqp.account',3,'sc.permission.accountView.remark','/account/view');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountModify','accountModify','tqp.account',5,'sc.permission.accountModify.remark','/account/modify');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountDelete','accountDelete','tqp.account',7,'sc.permission.accountDelete.remark','/account/delete');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountpermissionView','accountpermissionView','tqp.accountpermission',1,'sc.permission.accountpermissionView.remark','/accountpermission/view');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.accountpermissionModify','accountpermissionModify','tqp.accountpermission',1,'sc.permission.accountpermissionModify.remark','/accountpermission/modify');
/*language management*/
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('Initialize register language system process','languageRegister','tqp.language',1,'Display register language system','');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.languageRegister','languageRegister','tqp.language',2,'sc.permission.languageRegister.remark','/language/register');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.languageView','languageView','tqp.language',3,'sc.permission.languageView.remark','/language/view');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('Initialize modify language system screen','languageModify','tqp.language',4,'Display modify language system','');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.languageModify','languageModify','tqp.language',5,'sc.permission.languageModify.remark','/language/modify');
INSERT INTO qp_permission(permission_name, permission_code, module_code, sort_key, remark, action_path) VALUES('sc.permission.languageDelete','languageDelete','tqp.language',8,'sc.permission.languageDelete.remark','/language/delete');

INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-font-size', '12', '12px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-background-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-button-bg-color', '#5cb85c', '#5cb85c',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-button-bg-active-color', '#419641', '#419641',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-button-text-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-button-delete-bg-color', '#eb9316', '#eb9316',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-button-delete-bg-active-color', '#eb9316', '#eb9316',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-button-delete-text-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'client-button-delete-bg-color', '#eb9316', '#eb9316',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'client-button-delete-bg-active-color', '#eb9316', '#eb9316',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'client-button-delete-text-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'common-link-popup-text-color', '#0033cc', '#0033cc',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-list-td-firstChild-height', '40', '40px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'footer-text-color', '#555555', '#555555',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'footer-text-size', '12', '12px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'menu-bg-color', '#7895cf', '#7895cf',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'menu-brand-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'menu-brand-size', '18', '18px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'menu-font-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'menu-font-size', '18', '18px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'menu-selected-bg-color', '#e7e7e7', '#e7e7e7',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'menu-selected-text-color', '#555555', '#555555',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'item-menu-bg-color', '#7895cf', '#7895cf',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'item-menu-font-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'item-menu-font-size', '12', '12px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'item-menu-hover-bg-color', '#e8e8e8', '#e8e8e8',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'item-menu-hover-text-color', '#262626', '#262626',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'header-title-color', '#333333', '#333333',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'header-title-size', '18', '18px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'header-link-position', 'right', 'right',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'header-link-color', '#0033cc', '#0033cc',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'header-link-font-size', '14', '14px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'header-title-position', 'right', 'right',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-header-background-color', '#7895cf', '#7895cf',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-header-height', '28', '28px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-header-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-header-font-size', '12', '12px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-body-background-color', '#ffffff', '#ffffff',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-body-padding', '10', '10px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-table-padding', '0', '0px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-table-border-spacing', '0', '0px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-table-border', '1px solid #cbcfed', '1px solid #cbcfed',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-th-background-color', '#eff5f9', '#eff5f9',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-th-font-size', '12', '12px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-td-text', 'left', 'left',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-td-numeric', 'right', 'right',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-td-date', 'center', 'center',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-td-date-time', 'center', 'center',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-td-no-number', 'left', 'left',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-list-td-action-column', 'left', 'left',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-form-table-padding', '0', '0px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-form-table-border-spacing', '0', '0px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-form-table-border', '1px solid #cbcfed', '1px solid #cbcfed',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-form-th-font-size', '12', '12px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-form-th-background-color', '#eff5f9', '#eff5f9',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-form-th-text-align', 'right', 'right',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, is_default, delete_flg) VALUES('clThemeDefault', 'panel-table-form-td-font-size', '12', '12px',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clConditionType','cl.sqlconditiontype.0001','0','Value','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clConditionType','cl.sqlconditiontype.0002','1','Entity','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clConditionType','cl.sqlconditiontype.0003','2','Parameters','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clFactoryFunction','cl.factoryfunction.0001','0','Now','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clPageSize','cl.PageSize.001','10','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clPageSize','cl.PageSize.002','20','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clPageSize','cl.PageSize.003','30','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clPageSize','cl.PageSize.004','50','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clPageSize','cl.PageSize.005','100','','','','','',1,1);

INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clMessageType','cl.MessageType.001','Screen label','sc','','','','','','');
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clMessageType','cl.MessageType.002','Information','inf','','','','','','');
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clMessageType','cl.MessageType.003','Warning','wrn','','','','','','');
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('clMessageType','cl.MessageLevel.004','Error','err','','','','','','');
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','namePattern','^[^\\:*?"<>|/]*$','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','nameMask','\ /: * ? \" < > |','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','codePattern','^[a-zA-Z][0-9a-zA-Z_]*$','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','codeMask','\\ / : * ? \ < > | white-space"','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','nameMinLength','1','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','nameMaxLength','64','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','codeMinLength','1','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','codeMaxLength','64','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','sqlCodeMinLength','1','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','sqlCodeMaxLength','64','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','remarkMinLength','1','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','remarkMaxLength','2000','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','proxyHost','192.168.1.166','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','proxyPort','8080','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','proxyUser',' ','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','proxyPass',' ','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','datamarketAccessUri','https://datamarket.accesscontrol.windows.net/v2/OAuth2-13','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','bingClientId','terasolunaqp','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','bingClientSecret','4rWibW6YWNmDP5DSWvg3e0DFMUBR6kL3mTKcI+DpcQE=','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','serviceUrl','http://api.microsofttranslator.com/V2/Ajax.svc/Translate?','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','arrayServiceUrl','http://api.microsofttranslator.com/V2/Ajax.svc/TranslateArray?','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','arrayJsonObjectProperty','TranslatedText','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','maxTranslatedItem','30','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','numberBatchForOneExucute','20','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','intervalReload','60','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','sessionTimeOut','30','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','maxNestedObject','3','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','urlScreenCapture','http://localhost:8080/terasoluna-qp-web','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','batchJobPath','''','','','','','',1,1);
INSERT INTO qp_resources(category_cd, resource_cd, value1, value2, value3,value4, value5, resource_type, is_default, delete_flg) VALUES('systemSettings','maxJobNumber','20','','','','','',1,1);

