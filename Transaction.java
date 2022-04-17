import java.io.Serializable;

public class Transaction implements Serializable
{
	/*
	Info needed:
	 Transaction No. (int) - tNo // Needed when editing (adding/removing transactions from a receipt)
	 Item Name (String) - item
	 Product Type (String) - pType
	 Price (Double) - price
	 Quantity (int) - quantity
	 Total price (Double) - total
	 
	 Info:
	 - When a customer purchases a product, the sales are recorded as receipt that includes transactions
	 - You can search for receipt ID to view what is included in each receipt
	 - You cannot view transactions individually and search transactions individually
	 - Transactions added with the same product name of an existing transaction will not create a new
	   transaction, but add the new quantity to its existing quantity in the existing transaction.
	 
	 **NOTE: Transactions cannot be modified, but receipts can. In order to modify a transaction in a
	 		 receipt, it must be removed and created again. (This is to avoid over complexity)
	 		 As such, transactions are mostly read-only, except for when we want to add/remove them.
	 		 
	 **NOTE 2: Price is recorded instead of directly accessing the item's price because a product's price
	 		   can change but the transaction must follow the price at the time it was recorded at.
	 		   
	 **NOTE 3: Item is recorded as String instead of Product class as products can be deleted. In the case
	 		   this occurs, errors would occur if the product instance referenced by this transaction is
	 		   deleted. Additionally, a product's name can be modified as well, but we want to keep the old
	 		   name in the transaction record.
	 
	 **NOTE 4: Item name is recorded instead of product ID as the product ID will no longer exist if a
	 		   product is deleted. By storing the name of the product as a String, it is more readable
	 		   and the meaning can be kept even if the product has been deleted. We are assuming that no
	 		   two products share the exact same name and attributes (product name is unique).
	*/
	private static final long serialVersionUID = 1L;
	private int tNo;
	private String item;
	private String pType;
	private double price;
	private int quantity;
	private double total;
	
	public int getTransactionNo()
	{
		return tNo;
	}
	
	public void setTransactionNo(int newNo)
	{
		tNo = newNo;
	}	
	
	public String getItem()
	{
		return item;
	}
	
	public String getProductType()
	{
		return pType;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int _quantity)
	{
		quantity = _quantity;
		calculateTotal(); // recalculate the total price
	}
	
	public double getTotal()
	{
		return total;
	}

	// Constructor
	public Transaction(int _tNo, String _item, String _pType, double _price, int _quantity)
	{
		// Initialize all variables
		tNo = _tNo;
		item = _item;
		pType = _pType;
		price = _price;
		quantity = _quantity;
		calculateTotal();
	}
	
	public void calculateTotal()
	{
		total = price * quantity;
	}
	
	@Override
	public String toString()
	{
		// Returns transaction information in a set format
		return String.format("%5s | %20s | %12s | %11.2f | %8d | %16.2f", tNo, item, pType, price, quantity, total);
	}
}