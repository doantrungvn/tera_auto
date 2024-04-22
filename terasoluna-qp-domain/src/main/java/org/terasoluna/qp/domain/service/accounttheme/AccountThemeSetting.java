package org.terasoluna.qp.domain.service.accounttheme;

import java.util.HashMap;

public class AccountThemeSetting {

	private String bodyBackgroundColor ; 
	private String menuBackGroundColor ;
	private String menuTextColor ;
	private String menuBrandColor;
	private String headerTitleColor ;
	private double headerTitleSize;
	private String headerLinkPosition;
	private String headerLinkColor;
	private HashMap<String, String> mapTheme;

	
	public String getBodyBackgroundColor() {
		return bodyBackgroundColor;
	}
	public void setBodyBackgroundColor(String bodyBackgroundColor) {
		this.bodyBackgroundColor = bodyBackgroundColor;
	}
	public String getMenuBackGroundColor() {
		return menuBackGroundColor;
	}
	public void setMenuBackGroundColor(String menuBackGroundColor) {
		this.menuBackGroundColor = menuBackGroundColor;
	}
	public String getMenuTextColor() {
		return menuTextColor;
	}
	public void setMenuTextColor(String menuTextColor) {
		this.menuTextColor = menuTextColor;
	}
	public String getHeaderTitleColor() {
		return headerTitleColor;
	}
	public void setHeaderTitleColor(String headerTitleColor) {
		this.headerTitleColor = headerTitleColor;
	}
	public String getMenuBrandColor() {
		return menuBrandColor;
	}
	public void setMenuBrandColor(String menuBrandColor) {
		this.menuBrandColor = menuBrandColor;
	}
	public double getHeaderTitleSize() {
		return headerTitleSize;
	}
	public void setHeaderTitleSize(double headerTitleSize) {
		this.headerTitleSize = headerTitleSize;
	}
	public String getHeaderLinkPosition() {
		return headerLinkPosition;
	}
	public void setHeaderLinkPosition(String headerLinkPosition) {
		this.headerLinkPosition = headerLinkPosition;
	}
	public String getHeaderLinkColor() {
		return headerLinkColor;
	}
	public void setHeaderLinkColor(String headerLinkColor) {
		this.headerLinkColor = headerLinkColor;
	}
	public HashMap<String, String> getMapTheme() {
		return mapTheme;
	}
	public void setMapTheme(HashMap<String, String> mapTheme) {
		this.mapTheme = mapTheme;
	}
}
