package bean;

public class Member implements java.io.Serializable {
	private int member_id;
	private String login;
	private String password;
	private String customer_id;
	private String card_id;
	private String simei;
	private String furigana;
	private String mail;
	private String tel;
	private String postcode;
	private String prefecture;
	private String city;
	private String address;
	
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
	public String getCustomer_id() {
		return customer_id;
	}
	public String getCard_id() {
		return card_id;
	}
	public String getSimei() {
		return simei;
	}
	public String getFurigana() {
		return furigana;
	}
	public String getMail() {
		return mail;
	}
	public String getTel() {
		return tel;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getPrefecture() {
		return prefecture;
	}
	public String getCity() {
		return city;
	}
	public String getAddress() {
		return address;
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
	public void setCustomer_id(String customer_id) {
		this.customer_id=customer_id;
	}
	public void setCard_id(String card_id) {
		this.card_id=card_id;
	}
	public void setSimei(String simei) {
		this.simei=simei;
	}
	public void setFurigana(String furigana) {
		this.furigana=furigana;
	}
	public void setMail(String mail) {
		this.mail=mail;
	}
	public void setTel(String tel) {
		this.tel=tel;
	}
	public void setPostcode(String postcode) {
		this.postcode=postcode;
	}
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	
}
