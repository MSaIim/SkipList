import java.util.Scanner;

public class SkipDemo 
{
	public static void main(String [] args)
	{
		SkipList<String> list = new SkipList<String>();

		menu(list);
	}
	
	public static void menu(SkipList<String> list)
	{
		int choice = 0;
		String input= "";
		
		//Create Scanner class for input
		Scanner penguin = new Scanner(System.in);
		
		while(choice != 4)
		{
			System.out.println("\n Menu\n ----");
			System.out.println(" 1. Add Entry");
			System.out.println(" 2. Find Entry");
			System.out.println(" 3. Print list");
			System.out.println(" 4. Exit\n");
			System.out.print(" Choice: ");
			
			choice = penguin.nextInt();
			penguin.nextLine();
			
			switch(choice)
			{
				case 1:
					System.out.print(" Enter an input: ");
					input = penguin.nextLine();
					
					//Make sure correct data type then add
					try {
						list.add(input);
					}
					catch(Exception e) {
						System.out.println(" ** Invalid input!");
					}
					
					break;
				
				case 2:
					System.out.print(" Search for: ");
					input = penguin.nextLine();
					
					if(list.get(input) != null)
						System.out.println(" ** " + input + " found!");
					else
						System.out.println(" ** " + input + " not found!");
					
					break;
					
				case 3:
					System.out.println();
					list.printHorizontal();
					break;
					
				case 4:
					break;
					
				default:
					System.out.println(" ** Invalid input!");
					break;
			}
		}
	}
}
