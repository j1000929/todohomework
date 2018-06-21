package org.study.Dao;

public class MemberBean {
	
	private String username;
	private String userid;
	private String email;
	private String password;
	private String password_a;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword_a() {
		return password_a;
	}
	public void setPassword_a(String password_a) {
		this.password_a = password_a;
	}
	@Override
	public String toString() {
		return "MemberBean [username=" + username + ", userid=" + userid + ", email=" + email + ", password=" + password
				+ ", password_a=" + password_a + "]";
	}

}
