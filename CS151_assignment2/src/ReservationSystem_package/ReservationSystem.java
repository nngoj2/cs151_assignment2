/**
 * CIS151 Spring 2025 Assignment 2 Solution
 * @author Nam Tan Ngo
 * @version 1.0 02/23/2025
 */

package ReservationSystem_package;

import java.io.File;
import java.util.Scanner;
/**
 * this class is the main to run test on reservation system
 * it ensures the smoothly connection between the user and the system
 */
public class ReservationSystem {

	public static void main(String[] args) {
		Connection system = new Connection();
		User user;
		Scanner scanner = new Scanner(System.in).useDelimiter("[^A-Za-z0-9]+");
		String reservationFileName = args[0]+".txt";
		String userFileName = args[1]+".txt";
		
		try {
			File reservationFile = new File(reservationFileName);
			File userFile = new File(userFileName);
			
			if(userFile.createNewFile()) {
				System.out.println(userFileName+" is now created");
			}
			else {
				system.readUserFile(userFileName);
				System.out.println("Existing Users are loaded.");
			}
			if(reservationFile.createNewFile()) {
				System.out.println(reservationFileName+" is now created");
			}
			else {
				system.readReservationFile(reservationFileName);
				System.out.println("Existing Reservations are loaded.");
			}
		}
		catch(Exception e){
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		char choice ='D';		
		do {
			int id;
			String password;
			char member;
			System.out.print("Are you an [a]dmin or public [u]ser?: ");
			choice = scanner.next().toUpperCase().charAt(0);
			scanner.nextLine();
			if(choice != 'A' && choice != 'U') {
				System.out.println("Invalid option, please choose again");
				choice ='D';
				continue;
			}
			if(choice == 'U') {
				System.out.print("Do you have an account? [Y]es [N]o: ");
				member = scanner.next().toUpperCase().charAt(0);
				scanner.nextLine();
				if(member != 'Y' && member != 'N') {
					System.out.println("Invalid option, please choose again");
					choice ='D';
					continue;
				}
				if(member == 'N') {
					String name;
					System.out.println("Sign up log");

					System.out.print("Name: ");
					scanner.reset();
					
					name = scanner.nextLine();
					
					System.out.print("ID: ");
					scanner.useDelimiter("[^0-9]+");
					id = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Password: ");
					scanner.reset();
					password = scanner.nextLine();
					try {
						system.signUp(name, id, password);
						System.out.println("Sign up success!");
					}catch(Exception e){
						System.out.println(e);
						System.out.println("make new account fail, please try again");
					}
					continue;
				}
			}
		
			
			
			System.out.println("Sign in log");
			
			System.out.print("ID: ");
			scanner.useDelimiter("[^0-9]+");
			id = scanner.nextInt();
			scanner.nextLine();
			
			System.out.print("Password: ");
			scanner.reset();
			password = scanner.nextLine();
			
			switch(choice) {
			case 'A':
				try {
					user = system.adminSignIn(id, password);
				}
				catch(Exception e) {
					System.out.println(e);
					System.out.println("sign in fail, please try again");
					choice = 'D';
					break;
				}
				
				do {
					System.out.println("Select one of the following main menu options: ");
					System.out.print("Show [M]anifest list  	[D]one		E[X]it : ");
					scanner.useDelimiter("[^A-Za-z]+");
					choice = scanner.next().toUpperCase().charAt(0);
					scanner.nextLine();
					switch(choice) {
					case 'X':
						break;
					case 'D':
						break;
					case 'M':
						system.printListOfReservations();
						break;	
					default:
						System.out.println("Invalid option, please choose again");
					}	
					System.out.println();
				}while(choice != 'X' && choice != 'D');
				break;
				
				
			case 'U':
				try {
					user = system.signIn(id, password);
				}
				catch(Exception e) {
					System.out.println(e);
					System.out.println("sign in fail, please try again");
					choice = 'D';
					break;
				}
				
				do {
					System.out.println("Select one of the following main menu options: ");
					System.out.print("Check [A]availability	Make [R]eservation	"
							+ "[C]ancel Reservation   [V]iew Reservations  [D]one : ");
					scanner.useDelimiter("[^A-Za-z]+");
					choice = scanner.next().toUpperCase().charAt(0);
					scanner.nextLine();
					switch(choice) {
					case 'A':
						system.printAvailability();
						break;
						
						
					case 'R':
						do {
							String line;
							String number="";
							int column;
							char row='@';
							System.out.println("Make reservation");
							System.out.print("seat location (2B): ");
							scanner.useDelimiter("[^A-Za-z0-9]+");
							do {
								line =scanner.nextLine();
							}while(line.isBlank());
							
							for(char c: line.toCharArray()) {
								if(Character.isDigit(c)) {
									number +=c;
								}
								else if(Character.isAlphabetic(c)){
									row=Character.toUpperCase(c);
								}	
							}
							column=Integer.parseInt(number);
							try{	
								system.printSeat(system.getSeat(column, row));
								
								System.out.print("Do you want to add this seat? [Y]es [N]o: ");
								scanner.useDelimiter("[^A-Za-z]+");
								choice = scanner.next().toUpperCase().charAt(0);
								scanner.nextLine();
								
								if(choice == 'Y') {
								
									system.makeReservation(user, column, row);
									System.out.println("make reservation success");
								}
								else {
									System.out.println("make reservation stopped");
								}

							}catch(Exception e) {
								System.out.println(e);
								System.out.println("make reservation fail, please try again");
								if(system.outOfSeat()) {
									break;
								}
								continue;
							}
							
							
							System.out.print("Do you want to add another seat? [Y]es [N]o: ");
							scanner.useDelimiter("[^A-Za-z]+");
							choice = scanner.next().toUpperCase().charAt(0);
							scanner.nextLine();
							System.out.println();
						}while(choice =='Y');
						break;
						
						
					case 'C':
						do {
							String line;
							String number="";
							int column;
							char row='@';
							system.viewReservation(user);
							System.out.println("Cancel Reservation");
							System.out.print("seat location (2B): ");
							scanner.useDelimiter("[^A-Za-z0-9]+");
							do {
								line =scanner.nextLine();
							}while(line.isBlank());
							
							for(char c: line.toCharArray()) {
								if(Character.isDigit(c)) {
									number +=c;
								}
								else {
									row=Character.toUpperCase(c);
								}	
							}
							column=Integer.parseInt(number);	
							
							
						
							try{		
								system.printSeat(user.getSeat(column, row));
								System.out.print("Do you want to cancel this seat? [Y]es [N]o: ");
								scanner.useDelimiter("[^A-Za-z]+");
								choice = scanner.next().toUpperCase().charAt(0);
								scanner.nextLine();
								if(choice == 'Y') {
									system.cancelReservation(user,column, row);
									System.out.println("cancel reservation success");
								}
								else {
									System.out.println("cancel reservation stopped");
								}
							}catch(Exception e) {
								System.out.println(e);
								System.out.println("cancel reservation fail, please try again");
								if(user.hasReservation()) {
									continue;
								}
								break;
							}
							
							
							System.out.print("Do you want to cancel another seat? [Y]es [N]o: ");
							scanner.useDelimiter("[^A-Za-z]+");
							choice = scanner.next().toUpperCase().charAt(0);
							System.out.println();
						}while(choice =='Y');
						break;
						
						
					case 'V':
						system.viewReservation(user);
						break;	
					case 'D':
						break;	
					default:
						System.out.println("Invalid option, please choose again");
					}	
					System.out.println();
				}while(choice != 'D');			
				System.out.println();						
				break;
			default:
				System.out.println("Invalid option, please choose again");
			}
			System.out.println();
		}while(choice !='X');
		system.writeFile(userFileName, reservationFileName);
		scanner.close();
	}
}
