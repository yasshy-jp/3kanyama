package bean;

public class Item implements java.io.Serializable{
	// 商品番号(id),商品名(name),価格(price)をプロパティに持つProductオブジェクトをプロパティに持つ
	private Product product;
	private int count;   // 商品の個数
	
	public Product getProduct() {
		return product;
	}
	public int getCount() {
		return count;
	}

	public void setProduct(Product product) {
		this.product=product;
	}
	public void setCount(int count) {
		this.count=count;
	}
}
