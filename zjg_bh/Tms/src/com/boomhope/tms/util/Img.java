package com.boomhope.tms.util;
@Number(id = 1)
public class Img {

	@FieldSort(NO = 0)
	private String img;			//图片

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Img() {
		super();
	}

	public Img(String img) {
		super();
		this.img = img;
	}



}
