package bean;

// 商品番号(id),商品名(name),価格(price)を属性に持つProduct型インスタンスを属性に持つItemクラス。
public class Item implements java.io.Serializable {
	private Product product;
	private int count; // 商品の個数

	public Product getProduct() {
		return product;
	}

	public int getCount() {
		return count;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(o == null) return false;
		if(!(o instanceof Item)) return false;
		Item r = (Item) o;
		if(this.product.getId() != r.product.getId()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return product != null ? product.getId() : 0;
	}

}
