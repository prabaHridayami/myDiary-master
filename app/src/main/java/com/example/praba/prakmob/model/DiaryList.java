package com.example.praba.prakmob.model;

import com.google.gson.annotations.SerializedName;


public class DiaryList{

	@SerializedName("diary")
	private String diary;

	@SerializedName("id_diary")
	private String idDiary;

	@SerializedName("title")
	private String title;

	private String id;

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
			"DiaryList{" + 
			"diary = '" + diary + '\'' + 
			",id_diary = '" + idDiary + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DiaryList(String diary, String idDiary, String title) {
		this.diary = diary;
		this.idDiary = idDiary;
		this.title = title;
	}
}