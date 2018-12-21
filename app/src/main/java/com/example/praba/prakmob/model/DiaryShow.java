package com.example.praba.prakmob.model;


import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class DiaryShow{

	@SerializedName("diary")
	private String diary;

	@SerializedName("id_diary")
	private String idDiary;

	@SerializedName("title")
	private String title;

	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setDiary(String diary){
		this.diary = diary;
	}

	public String getDiary(){
		return diary;
	}

	public void setIdDiary(String idDiary){
		this.idDiary = idDiary;
	}

	public String getIdDiary(){
		return idDiary;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"DiaryShow{" + 
			"diary = '" + diary + '\'' + 
			",id_diary = '" + idDiary + '\'' + 
			",title = '" + title + '\'' +
			",image = '" + image + '\'' +
			"}";
	}

	public static class Entry implements BaseColumns {
		public static final String TABLE_NAME = "diary";
		public static final String COLUMN_ID = "id_diary";
		public static final String COLUMN_TITLE = "title";
		public static final String COLUMN_DIARY= "diary";
		public static final String COLUMN_IMAGE = "image";
		public static final String COLUMN_IDUSER = "id_user";
	}

	public DiaryShow(String id, String title, String diary, String image) {
		this.idDiary = id;
		this.title = title;
		this.diary = diary;
		this.image = image;

	}
}