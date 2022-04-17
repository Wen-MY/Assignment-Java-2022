import java.util.*;
import java.sql.Timestamp;

public class SalesMenuController
{
	/*
	
	FUNCTIONALITIES:
	-SHOW MENU (Display the sales menu)
	-ADD NEW SALES (new Receipt will be generated, transaction are added into the receipt)
	-VIEW SALES (Display receipt generation history, only show basic info such as receipt ID, date, etc.
				 User can enter the receipt number to view its details such as individual transactions)
	-EDIT SALES (User can enter a receipt number, if it exists, show its details and give edit options)
	
	*/
	
	private Scanner scanner = new Scanner(System.in);
	
	// ArrayLists
	private ArrayList<Product> pList;
	private ArrayList<Receipt> rList;
	
	// Login details (for record purposes)
	private Login login;
	private HashMap <Timestamp, String> userlogfile;
	
	// For recording activities performed during the duration of the login
    public HashMap <Timestamp, String> getUserlogfile(){
        return userlogfile;
    }
	
	// Constructor
	public SalesMenuController(ArrayList<Product> _pList, ArrayList<Receipt> _rList, Login _login)
	{
		pList = _pList;
		rList = _rList;
		login = _login;
		userlogfile = new HashMap<Timestamp, String>();
	}
	
	public void showSalesMenu()
	{
		String userInput;
		
		do
		{
			System.out.println("SALES MENU\nEnter the following options:\n(1) - Add Sales Record"
					+ "\n(2) - View Sales Record\n(3) - Edit Sales Record\n(4) - Exit Sales Menu");
			System.out.println("Enter option: ");
			userInput = scanner.nextLine();
			
			switch (userInput)
			{
			case "1": addSales(); break;
			case "2": viewSales(); break;
			case "3": editSales(); break;
			case "4": System.out.println("Exiting sales menu..."); break;
			default: System.out.println("Invalid option. Please enter again."); break;
			}
		} while (!userInput.equals("4"));
	}
	
	public void addSales()
	{
		// add sales method
		
		// Receipt input required
		
		// Transaction input required (same variable reused for multiple transactions)
		String itemName; // Name is used to search for a matching product instance
		String quantity; // quantity is stored as int data type but read as String and checked during input
		System.out.println("ADD SALES RECORD");
	
		// Create receipt
		Receipt newReceipt = new Receipt(login.getUser().getID()); // get the id of the user
		
		String userInput; // to get user input whether to repeat for multiple transaction or not
		int transactionNo = 1; // starts from 1 transaction (first tNo set as 1)
		
		do
		{
			System.out.println("Entering details for Transaction.");
			
			// Get input for item name and quantity
			System.out.println("Enter product name: ");
			itemName = scanner.nextLine();
			
			System.out.println("Enter quantity: ");
			quantity = scanner.nextLine();
			
			// Check if product information entered is valid
			if (validProductName(itemName) != -1 && isInteger(quantity))
			{
				// Get product index 
				int pID = validProductName(itemName);
				
				// Check if product has sufficient stock (stock must be more than quantity), quantity must be a positive integer
				if (pList.get(pID).getStock() > 0  && Integer.parseInt(quantity) > 0 && pList.get(pID).getStock() >= Integer.parseInt(quantity))
				{
					Boolean transactionPerformed = false;
					// Check if product with same name already exists in transaction
					for (Transaction t : newReceipt.getTransactionList())
					{
						if (t.getItem().equals(itemName))
						{
							t.setQuantity(t.getQuantity()+Integer.parseInt(quantity));
							newReceipt.calculateGrandTotal();
							System.out.println("Existing transaction of same product found. The quantities will be summed. "
									+ "Enter any input to add another transaction ((B) - Save and Back)");
							transactionPerformed = true;
							break;
						}
					}
					
					// Create new transaction if existing one is not found
					if (!transactionPerformed)
					{
						
						Transaction newTransaction = new Transaction(transactionNo, itemName, pList.get(pID).getProductType(), pList.get(pID).getPrice(), Integer.parseInt(quantity));
						newReceipt.addTransaction(newTransaction);
						
						transactionNo++; // add to transaction counter
						System.out.println("Transaction added successfully. Enter any input to add another transaction ((B) - Save and Back)");
					
					}
					
					// Update the stock count after recording the transaction (Original stock count - quantity)
					pList.get(pID).updateStock(-(Integer.parseInt(quantity)));
				}
				else
					System.out.println("Insufficient stock! Unable to create sales record. Enter any input to try again. ((B) - Back)");
			}
			else
				System.out.println("Product name or quantity is invalid. Enter any input to try again. ((B) - Back)");
			
			userInput = scanner.nextLine();
			
		} while (!userInput.equals("B"));
		
		// Add the new receipt if it's not empty
		if (newReceipt.getTransactionList().size() != 0)
		{
			rList.add(newReceipt);
			userlogfile.put(new Timestamp(System.currentTimeMillis()), "Recorded Receipt: "+ newReceipt.getReceiptID()+'|'+login.getUser().getID());
		}
		
	}
		
	public void viewSales()
	{
		// view sales method
		
		int pageStartIndex = 1; // Start from page 1 (10 results are shown on each page)
		
		String userInput;
		
		do
		{
			// UI
			System.out.println("SALES HISTORY");
			System.out.printf("%36s | %23s | %11s\n", "Receipt ID", "Date and Time", "Employee ID");
			
			for (int i = ((pageStartIndex-1)*10); i < (pageStartIndex*10); i++) // show 10 records in a page
			{
				// check if next element is beyond array size (i is only valid if it is less than array size)
				if (i == rList.size())
				{
					break; // if yes, break out of loop
				}
				
				System.out.printf("%10s | %10s | %11s\n", rList.get(i).getReceiptID(), rList.get(i).getDate(), rList.get(i).getEmployeeID());
				
			}
		
			System.out.println("Enter a Receipt ID to view its details / (B) - Return to sales menu / (+/-) - View more/less results");
			System.out.println("Enter input: ");
			userInput = scanner.nextLine();
			
			// take action depending on user input
			switch (userInput)
			{
			case "+":
				if (((rList.size()-1)/10) >= pageStartIndex) // Increase page index only if there are elements on the next page
					pageStartIndex += 1;
				break;
			case "-":
				if ((pageStartIndex-1) >= 1) // else do nothing
					pageStartIndex -= 1;
				break;
			default:
				// Check if user input is a valid receipt ID here (If yes, display)
				int idx = validReceiptID(userInput); // -1 if invalid ID
				if (idx != -1)
					System.out.println(rList.get(idx).toString());
				else if (userInput.equals("B"))
					break;
				else
					System.out.println("Invalid input. Please try again.");
				break;
			}
			
			// Pause screen until user enters input again
			System.out.println("Enter any input to continue...");
			scanner.nextLine();
			
		} while (!userInput.equals("B"));
		
	}
	
	public void editSales() // edit sales method
	{
		/*
			EDIT OPTIONS (Other than (B) - Back)
			1) Edit Date
			2) Add Transaction
			3) Remove Transaction - Not allowed if there is only one transaction
			4) Delete Receipt
		*/
		String userInput;
		
		do
		{
			System.out.println("EDIT SALES RECORD");
			System.out.println("Enter an existing receipt ID to edit (or (B) - Back): ");
			
			userInput = scanner.nextLine();
			for (Receipt r : rList) // If receipt ID exists, display its details and give edit options
			{
				if (userInput.equals(r.getReceiptID()))
				{
					String editOption;
					Boolean deleted = false; // Check if this receipt is deleted during this editing
					do 
					{
						System.out.println(r.toString());
						System.out.println("Edit options:\n(1) - Update Date\n(2) - Add Transaction\n"
								+ "(3) - Remove Transaction\n(4) - Delete Receipt");
						System.out.println("Enter option (or (B) - Back): ");
						editOption = scanner.nextLine();
						
						switch (editOption)
						{
						case "1": // Update Date
							System.out.println("Are you sure you would like to update the current date? (Y) - Yes / (Any other input) - No: ");
							String confirm = scanner.nextLine();
							
							if (confirm.contentEquals("Y"))
							{
								r.setDate();
								// Record the activity of editing date
								userlogfile.put(new Timestamp(System.currentTimeMillis()), "Updated date of Receipt ID: "+ r.getReceiptID()+'|'+login.getUser().getID());
							}
							else
								System.out.println("Receipt date has not been changed. Returning to previous menu....");
							break;
							
						case "2": // Add Transaction
							// Similar process to addSales but only allow one at a time
							System.out.println("Creating new transaction...");
							
							String itemName;
							String quantity;
							
							// Get input for item name and quantity
							System.out.println("Enter product name: ");
							itemName = scanner.nextLine();
							
							System.out.println("Enter quantity: ");
							quantity = scanner.nextLine();
							
							// Check if product information entered is valid
							if (validProductName(itemName) != -1 && isInteger(quantity))
							{
								// Get product index 
								int pID = validProductName(itemName);
								
								// Check if product has sufficient stock (stock must be more than quantity), quantity must be a positive integer
								if (pList.get(pID).getStock() > 0  && Integer.parseInt(quantity) > 0 && pList.get(pID).getStock() >= Integer.parseInt(quantity))
								{
									Boolean transactionPerformed = false;
									// Check if product with same name already exists in transaction
									for (Transaction t : r.getTransactionList())
									{
										if (t.getItem().equals(itemName))
										{
											t.setQuantity(t.getQuantity()+Integer.parseInt(quantity));
											r.calculateGrandTotal();
											System.out.println("Existing transaction of same product found. The quantities will be summed.");
											transactionPerformed = true;
											break;
										}
									}
									
									// Create new transaction if existing one is not found
									if (!transactionPerformed)
									{
										
										Transaction newTransaction = new Transaction(r.getTransactionList().size()+1, itemName, pList.get(pID).getProductType(), pList.get(pID).getPrice(), Integer.parseInt(quantity));
										r.addTransaction(newTransaction);
										System.out.println("Transaction added successfully.");
									
									}
									
									// Update the stock count after recording the transaction (Original stock count - quantity)
									pList.get(pID).updateStock(-(Integer.parseInt(quantity)));
									
									// Record the activity of editing transaction
									userlogfile.put(new Timestamp(System.currentTimeMillis()), "Modified Transaction of Receipt ID: "+ r.getReceiptID()+'|'+login.getUser().getID());
								}
								else
									System.out.println("Insufficient stock! Unable to create new transaction.");
							}
							else
								System.out.println("Product name or quantity is invalid.");
							
							break;
							
						case "3": // Remove Transaction
							ArrayList<Transaction> tList = r.getTransactionList();
							
							// Only allow removal of transaction if there are 2 or more
							if (tList.size() < 2)
							{
								System.out.println("Receipts must contain at least one transaction!");
								break;
							}
							
							System.out.println("Enter transaction No. to remove: ");
							String tNo = scanner.nextLine();
							
							if (isInteger(tNo))
							{
								if (Integer.parseInt(tNo) > 0 && Integer.parseInt(tNo) <= tList.size())
								{
									Boolean reduceNo = false; // If true, the iteration will reduce tNo by 1
									for (int i = 0; i < tList.size(); i++)
									{
										if (Integer.parseInt(tNo) == tList.get(i).getTransactionNo())
										{
											tList.remove(i);
											reduceNo = true;
										}
										
										if (reduceNo)
											tList.get(i).setTransactionNo(tList.get(i).getTransactionNo()-1);
										
									}
									// Record the activity of editing transaction
									userlogfile.put(new Timestamp(System.currentTimeMillis()), "Modified Transaction of Receipt ID: "+ r.getReceiptID()+'|'+login.getUser().getID());
								}
								else
									System.out.println("Invalid input! Please try again.");
							}
							else
								System.out.println("Invalid input! Please try again.");
							
							break;
							
						case "4": // Delete Receipt
							System.out.println("Are you sure want to delete this record? (Y) - Yes / Enter any other input to cancel: ");
							String confirmation = scanner.nextLine();
							
							if (confirmation.equals("Y"))
							{
								rList.remove(r);
								System.out.println("Receipt deleted successfully.");
								deleted = true;
								
								// Record the activity of deleting the receipt
								userlogfile.put(new Timestamp(System.currentTimeMillis()), "Deleted Receipt ID: "+ r.getReceiptID()+'|'+login.getUser().getID());
								
							}
							else
								System.out.println("Receipt deletion cancelled.");
							
							break;
							
						case "B": break; // Return to Sales Menu
						
						default: System.out.println("Invalid option! Please try again.");
								 scanner.nextLine(); break;
						}
					} while (!editOption.equals("B") && !deleted);
					break;
				}
			}
		} while (!userInput.equals("B"));
		
	}
	
	// Validation methods required
	
	private Boolean isInteger(String value)
	{
		// Checks if a String value is integer
		Boolean validity = true;
		try
		{
		    Integer.parseInt(value);
		}
		catch (NumberFormatException ex) {
		    validity = false;
		}
		return validity;
	}
	
	private int validReceiptID(String id)
	{
		int index = -1;
		
		// Search if receipt id exists in receipt ArrayList, and returns index (otherwise, return -1)
		
		for (int i = 0; i < rList.size(); i++)
			if (rList.get(i).getReceiptID().equals(id))
				index = i;
		
		return index;
		
	}
	
	private int validProductName(String itemName)
	{
		 int index = -1;
		
		// Search in product list to check whether the product exists or not, and returns index (-1 if does not exist)
		for (int i = 0; i < pList.size(); i++)
			if (pList.get(i).getName().equals(itemName))
				index = i;
		
		return index;
	}
	
}
