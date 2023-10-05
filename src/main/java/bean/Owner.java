package bean;

public class Owner implements java.io.Serializable {
	private int member_id;
	private String login;
	private String password;
	private String name;
	
	// ゲッタ
	public int getId() {
		return member_id;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	
	// セッタ
	public void setId(int id) {
		this.member_id=id;
	}
	public void setLogin(String login) {
		this.login=login;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public void setName(String name) {
		this.name=name;
	}
	
}
