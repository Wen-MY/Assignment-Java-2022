import java.util.*;

public class CheckValid 
{
	public Boolean isInteger(String value)
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
	
	public int validReceiptID(String id, ArrayList<Receipt> rList)
	{
		int index = -1;
		
		// Search if receipt id exists in receipt ArrayList, and returns index (otherwise, return -1)
		
		for (int i = 0; i < rList.size(); i++)
			if (rList.get(i).getReceiptID().equals(id))
				index = i;
		
		return index;
		
	}
	
	public int validProductName(String itemName, ArrayList<Product> pList)
	{
		 int index = -1;
		
		// Search in product list to check whether the product exists or not, and returns index (-1 if does not exist)
		for (int i = 0; i < pList.size(); i++)
			if (pList.get(i).getName().equals(itemName))
				index = i;
		
		return index;
	}
	
	public int validProductID(String itemID, ArrayList<Product> pList)
	{
		 int index = -1;
		
		// Search in product list to check whether the product exists or not, and returns index (-1 if does not exist)
		for (int i = 0; i < pList.size(); i++)
			if (pList.get(i).getID().equals(itemID))
				index = i;
		
		return index;
	}
	
	public Boolean validDate(String date)
	{
		// Expected format of date - DD/MM/YYYY
		Boolean validity = true;
		
		// Split the day, month and year into 3 elements of an array
		String[] dmyArr = new String[3];
		
		// Check length and whether the '/' characters are in the correct position
		if (date.length() == 10 && date.charAt(2) == '/' && date.charAt(5) == '/')
		{
			dmyArr[0] = date.substring(0, 2); // Get the first the 2 digits (day)
			dmyArr[1] = date.substring(3, 5); // Get the month digits
			dmyArr[2] = date.substring(6); // Get the year digits
			
			// Loop through array to check if all chars in each substring are digits
			for (int i = 0; i < dmyArr.length; i++)
			{
				// Loop through the digits
				for (int c = 0; c < dmyArr[i].length(); c++)
				{
					if (!Character.isDigit(dmyArr[i].charAt(c)))
					{
						validity = false;
						break;
					}
				}
				
				// No need to continue the loop if one of them if already invalid
				if (validity == false)
					break;
			}
			
			// If the date is still valid,
			int[] intDMYArr = new int[3];
			for (int i = 0; i < 3; i++) // convert and store all dates as integers
				intDMYArr[i] = Integer.parseInt(dmyArr[i]);
			
			/*
				Check the day (whether it is less than or equal to 0)
				Check the month (whether it is less than or equal to 0 or more than 12)
			*/
			
			if (intDMYArr[0] <= 0 || intDMYArr[1] <= 0 || intDMYArr[1] > 12)
				validity = false;
			
			int maxDays = 0;
			switch (intDMYArr[1])
			{
			// Check for months with 31 days first
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12: maxDays = 31; break;
			case 4:
			case 6:
			case 9:
			case 11: maxDays = 30; break;
			case 2:
				// Check for leap year
				if (intDMYArr[2] % 4 == 0)
				{
					if (intDMYArr[2] % 100 == 0)
					{
						if (intDMYArr[2] % 400 == 0)
							maxDays = 29; // leap year
						else
							maxDays = 28; // not a leap year
					}
					else
						maxDays = 29; // leap year

				}
				else
					maxDays = 28; // not a leap year
			}
			
			// Check if the days exceed the maximum days set earlier
			if (intDMYArr[0] > maxDays)
				validity = false;
			
		}
		else
			validity = false;
		
		return  validity;
	}
}

