import java.util.*;
public class Receipt 
{
	/*
		Info needed:
		Receipt ID (String) - rID (generated)
		Date (String/Date) - date
		Transactions included (ArrayList) - tList
	 	Grand total (double) - gTotal
	 	Employee ID (String) - empID
	 	
	 	**NOTE: Saved in text file as a String with each attribute separated by a delimiter
	 	 		--> Transactions are possibly stored in the format (T1, T2..) via as Object**
	*/
	
	private String rID;
	private String date;
	private ArrayList<Transaction> tList;
	private double gTotal; // Sum of all total prices of all transactions
	private String empID;
	
	public String getReceiptID()
	{
		return rID;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String _date)
	{
		date = _date; // change to new date
	}
	
	public ArrayList<Transaction> getTransactionList()
	{
		return tList;
	}
	
	public double getGrandTotal()
	{
		return gTotal;
	}
	
	public String getEmployeeID()
	{
		return empID;
	}
	
	public Receipt(String _date, String _empID)
	{
		rID = generateReceiptID();
		date = _date;
		empID = _empID; // should be automatically assigned according to user's ID
		tList = new ArrayList<Transaction>(); // all transactions will be added in this list later
		calculateGrandTotal();

	}
	
	public void addTransaction(Transaction t)
	{
		tList.add(t);
		calculateGrandTotal(); // Grant total is recalculated when transactions within receipt are modified
	}
	
	public void removeTransaction(int tNo)
	{
		// Removes transaction from receipt by ID
		for (Transaction t: tList)
		{
			if (t.getTransactionNo() == (tNo))
			{
				int index = tList.indexOf(t);
				tList.remove(index);
			}
		}
		calculateGrandTotal();
	}
	
	public void calculateGrandTotal()
	{
		// Calculate grand total
		gTotal = 0;
		for (Transaction t: tList)
		{
			gTotal += t.getTotal();
		}
	}
	
	public String generateReceiptID()
	{
		return UUID.randomUUID().toString();
	}
	
	@Override
	public String toString()
	{
		// Displays all receipt details including transactions in the receipt
		
		// rString stores receipt's own information excluding transaction
		String rString = String.format("RECEIPT DETAILS OF : %s (Recorded on %s by Employee ID %s)\n",
				rID, date, empID);
		
		// Stores transaction information
		String tString = String.format("Transactions included:\n%5s | %20s | %12s | %11s | %5s | %11s\n",
				"No.", "Item", "Product Type", "Price (RM)", "Quantity", "Total Price (RM)"); 

		// Loop through all transaction and add their information to the tString
		for (Transaction t : tList)
			tString += t.toString() + "\n";
		
		return rString + tString + "Grand Total: RM " + String.format("%.2f", gTotal);
	}
}
