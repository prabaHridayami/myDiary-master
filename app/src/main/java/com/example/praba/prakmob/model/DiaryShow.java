package com.example.praba.prakmob.model;


import com.google.gson.annotations.SerializedName;

public class DiaryShow{

	@SerializedName("diary")
	private String diary;

	@SerializedName("id_diary")
	private String idDiary;

	@SerializedName("title")
	private String title;

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
			"}";
		}
}