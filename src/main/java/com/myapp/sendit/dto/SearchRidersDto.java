package com.myapp.sendit.dto;

import javax.validation.constraints.Size;

public class SearchRidersDto {
	@Size(max=40,min=1)
	private String cityName;
	@Size(max=40,min=1)
	private String stateName;
	
	public String getCityName() {
		return cityName;
	}
	
	public SearchRidersDto() {
	}

	public SearchRidersDto(String cityName, String stateName) {
		this.cityName = cityName;
		this.stateName = stateName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
