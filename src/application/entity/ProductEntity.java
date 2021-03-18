package application.entity;

import java.math.BigDecimal;



public class ProductEntity {
	
	/* Constants containing standards values from Business-and-Technical-Rules: */
	/* At this point there is an important issue to be discussed on RA(requirement analysis) and
	 * BA(business analysis) scoops: the 0(zero) value in, some attributes, will be allowed or not?
	 * That discussion is not the project's goals up to now, so the fields are allowing 0(zero). */
	public static final int NAME_MIN_LEN = 3;
	public static final int NAME_MAX_LEN = 128;
	public static final BigDecimal PURCHASEPRICE_MIN_VAL = new BigDecimal("0");
	public static final BigDecimal PURCHASEPRICE_MAX_VAL = new BigDecimal("9999999999.99");
	public static final BigDecimal SALEPRICE_MIN_VAL = new BigDecimal("0");
	public static final BigDecimal SALEPRICE_MAX_VAL = new BigDecimal("9999999999.99");
	public static final float STOCKQTTY_MIN_VAL = 0.0f;
	public static final float STOCKQTTY_MAX_VAL = 9999999999.99f;
	
	
	
	/* Private Class' attributes: */
	private long id = 0;
	private String name = "";
	private BigDecimal purchasePrice = new BigDecimal("0.00");
	private BigDecimal salePrice = new BigDecimal("0.00");
	private float stockQtty = 0.0f;
	
	
	
	/* Public Class' attributes: */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	// Overload.
	public void setId(String strId) {
		if ( (!strId.equalsIgnoreCase("")) &&
				(!strId.equalsIgnoreCase(" ")) &&
				(!strId.isEmpty()) &&
				(strId != null) )
		{
			try {
				this.id = Long.parseLong(strId);	
			} catch (Exception e1) {
				System.out.println("Error casting 'string' to 'long' !");
				e1.printStackTrace();
			}
		} else {
			/* If Id was not defined (Empty or Null) the 0 value is adopted as default
			 * meaning this object was not stored in project's persistence, yet. */
			this.id = 0;
		}
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	
	
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	
	
	
	public float getStockQtty() {
		return stockQtty;
	}
	
	public void setStockQtty(float stockQtty) {
		this.stockQtty = stockQtty;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((purchasePrice == null) ? 0 : purchasePrice.hashCode());
		result = prime * result + ((salePrice == null) ? 0 : salePrice.hashCode());
		result = prime * result + Float.floatToIntBits(stockQtty);
		return result;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductEntity other = (ProductEntity) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (purchasePrice == null) {
			if (other.purchasePrice != null)
				return false;
		} else if (!purchasePrice.equals(other.purchasePrice))
			return false;
		if (salePrice == null) {
			if (other.salePrice != null)
				return false;
		} else if (!salePrice.equals(other.salePrice))
			return false;
		if (Float.floatToIntBits(stockQtty) != Float.floatToIntBits(other.stockQtty))
			return false;
		return true;
	}

	
	
	@Override
	public String toString() {
		return "ProductEntity [id=" + id + ", name=" + name + ", purchasePrice=" + purchasePrice + ", salePrice="
				+ salePrice + ", stockQtty=" + stockQtty + "]";
	}

}