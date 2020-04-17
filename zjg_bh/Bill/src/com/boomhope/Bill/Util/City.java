package com.boomhope.Bill.Util;
/**
 * 城市Bean
 * hao
 */
public class City {
	private String proId;
	private String cityId;
	private String cityName;
	private String cityCode;
	@Override
	public String toString() {
		return "City [proId=" + proId + ", cityId=" + cityId + ", cityName="
				+ cityName + ", cityCode=" + cityCode + "]";
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
