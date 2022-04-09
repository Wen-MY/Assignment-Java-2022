import java.util.*;

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
	
	private static Scanner scanner = new Scanner(System.in);
	private static CheckValid validator = new CheckValid();
	
	private ArrayList<Product> pList;
	private ArrayList<Receipt> rList;
	private String empID;
	
	public static void main(String[] args)
	{	
		// MAIN FUNCTION IS FOR TESTING PURPOSES ONLY, WILL BE REMOVED SOON
		// Assuming all the data are read into respective ArrayLists at the beginning of the main menu
		/*
		
			ArrayLists read that are used for sales menu are: Product, Receipt, Transactions
		
		*/
		
		// THIS PART IS HARDCODED FOR TESTING PURPOSES (for view sales part)
		ArrayList<Product> testPList = new ArrayList<Product>();
		ArrayList<Receipt> testRList = new ArrayList<Receipt>();
		
		
		// Creating a controller (this will be done in main menu)
		SalesMenuController smc = new SalesMenuController(testPList, testRList, "TestEmpID");
		testPList.add(new Product("Apple", "0001", 2.00, "Fruit"));
		
		// Test values
		for (int i = 0; i < 10; i++)
		{
			Receipt tempReceipt = new Receipt("01/01/2022", smc.empID);
			Transaction tempTransaction1 = new Transaction(1, "Apple", "Fruit", 2.00, 4);
			
			tempReceipt.addTransaction(tempTransaction1);
			testRList.add(tempReceipt);
		}
		// TEST PART END
		
		smc.showSalesMenu();
		
		scanner.close();
		
	}
	
	public SalesMenuController(ArrayList<Product> _pList, ArrayList<Receipt> _rList, String _empID)
	{
		pList = _pList;
		rList = _rList;
		empID = _empID;
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
		Boolean validDate = false;
		
		// Receipt input required
		String date;
		
		// Transaction input required (same variable reused for multiple transactions)
		String itemName; // Name is used to search for a matching product instance
		String quantity; // quantity is stored as int data type but read as String and checked during input
		
		do
		{
			System.out.println("ADD SALES RECORD");
			System.out.println("Enter Receipt Date (DD/MM/YYYY), EXAMPLE : 01/01/2022: ");
			date = scanner.nextLine();
			
			if (date.equals("B"))
				validDate = true;
			else if (!validator.validDate(date))
			{
				validDate = false;
				System.out.println("Invalid date format! Please try again.");
			}
			else
			{
				// Create receipt
				Receipt newReceipt = new Receipt(date, empID); // SHOULD BE CHANGED TO USE empID OF USER
				
				String userInput; // to get user input whether to repeat for multiple transaction or not
				validDate = true;
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
					if (validator.validProductName(itemName, pList) != -1 && validator.isInteger(quantity))
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
							// Get product index 
							int pID = validator.validProductName(itemName, pList);
							
							Transaction newTransaction = new Transaction(transactionNo, itemName, pList.get(pID).getProductType(), pList.get(pID).getPrice(), Integer.parseInt(quantity));
							newReceipt.addTransaction(newTransaction);
							
							transactionNo++; // add to transaction counter
							System.out.println("Transaction added successfully. Enter any input to add another transaction ((B) - Save and Back)");
						
						}
					}
					else
						System.out.println("Product name or quantity is invalid. Enter any input to try again. ((B) - Back)");
					
					userInput = scanner.nextLine();
					
				} while (!userInput.equals("B"));
				
				// Add the new receipt if it's not empty (Grand Total will be more than 0)
				if (newReceipt.getTransactionList().size() != 0)
					rList.add(newReceipt);
				
			}
			
		} while (!validDate);
		
	}
	
	public void viewSales()
	{
		// view sales method
		
		int pageStartIndex = 1; // Start from page 1
		
		String userInput;
		
		do
		{
			// UI
			System.out.println("SALES HISTORY");
			System.out.printf("%36s | %10s | %11s\n", "Receipt ID", "Date", "Employee ID");
			
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
				int idx = validator.validReceiptID(userInput, rList); // -1 if invalid ID
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
						System.out.println("Edit options:\n(1) - Edit Date\n(2) - Add Transaction\n"
								+ "(3) - Remove Transaction\n(4) - Delete Receipt");
						System.out.println("Enter option (or (B) - Back): ");
						editOption = scanner.nextLine();
						
						switch (editOption)
						{
						case "1": // Edit Date
							System.out.println("Enter new date (DD/MM/YYYY), EXAMPLE: 01/01/2022: ");
							String newDate = scanner.nextLine();
							
							if (validator.validDate(newDate))
								r.setDate(newDate);
							else
								System.out.println("Invalid date format! Please try again.");
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
							if (validator.validProductName(itemName, pList) != -1 && validator.isInteger(quantity))
							{
								// Check if product with same name already exists in transaction
								for (Transaction t : r.getTransactionList())
								{
									if (t.getItem().equals(itemName))
									{
										t.setQuantity(t.getQuantity()+Integer.parseInt(quantity));
										r.calculateGrandTotal();
										break;
									}
									else
									{
										// Get product index 
										int pID = validator.validProductName(itemName, pList);
										
										Transaction newTransaction = new Transaction(r.getTransactionList().size()+1, itemName, pList.get(pID).getProductType(), pList.get(pID).getPrice(), Integer.parseInt(quantity));
										r.addTransaction(newTransaction);
										
										System.out.println("Transaction added successfully.");
									}
								}
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
							
							if (validator.isInteger(tNo))
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
}
