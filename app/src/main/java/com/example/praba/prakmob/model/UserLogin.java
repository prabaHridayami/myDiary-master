package com.example.praba.prakmob.model;


import com.google.gson.annotations.SerializedName;


public class UserLogin{

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("message")
	private String message;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private boolean status;

	@SerializedName("username")
	private String username;

	private String password;

	private String name;

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"UserLogin{" + 
			"id_user = '" + idUser + '\'' + 
			",message = '" + message + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			",username = '" + username + '\'' +
			",name = '" + username + '\'' +
			"}";
		}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}