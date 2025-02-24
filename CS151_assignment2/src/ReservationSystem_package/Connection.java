package ReservationSystem_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Connection {
	private HashMap<Integer, User> users;
	private HashMap<Integer, User> employees;

	private HashMap<Integer, HashMap<Character, Seat>> seats;	
	public Connection() {
		users = new HashMap<>();
		employees = new HashMap<>();
		seats = new HashMap<>();
		
		employees.put(11111, new User("admin",11111,"password"));
		for(int i=1; i<=50;i++) {
			seats.put(i, new HashMap<>());
			for(int j=65;j<=74;j++) {
				if(i<5) {
					seats.get(i).put((char)j, new Seat(i,(char)j, "First Class", 1000));
				}
				else if(i<16) {
					seats.get(i).put((char)j, new Seat(i,(char)j, "Economy Plus", 500));
				}
				else {
					seats.get(i).put((char)j, new Seat(i,(char)j, "Economy", 250));
				}
			}
		}
		
	}
	
	public boolean outOfSeat() {return seats.isEmpty();}

	
	public void printAvailability() {
		System.out.println("Seat Availability");
		
		if(!seats.isEmpty()) {
			for( Integer column: seats.keySet()) {
				
				switch(column) {
				case 1 -> System.out.println("\nFirst (price: $1000/seat)");
				case 5 -> System.out.println("\nEconomy Plus (price: $500/seat)");
				case 16 -> System.out.println("\nEconomy (price: $250/seat)");
				}
				
				System.out.printf("%2d: ",column);
				String delimiter ="";
				if(!seats.get(column).isEmpty()) {
					for(Character row: seats.get(column).keySet()) {
						System.out.print(delimiter+row);
						delimiter = ",";
					}	
					System.out.println();
				}
			}
		}		
	}
	
	public void printSeat(Seat seat) {
		System.out.println("Seat Location: "+seat.getLocation());
		System.out.println("Service type: "+seat.getType());
		System.out.println("Price: $"+seat.getPrice());

	}
	
	public Seat getSeat(int column, char row) {
		if(seats.containsKey(column)) {
			if(seats.get(column).containsKey(row)) {
				return seats.get(column).get(row);
			}
			throw new IllegalArgumentException("input invalid, this seat doesn't exist or have been taken");
		}
		throw new IllegalArgumentException("input invalid, this seat number doesn't exist");
	}
	
	
	
	
	public boolean signUp(String name, int id, String password) {
		if(password.contains("/space/") || name.contains("/space/")) {
			throw new IllegalArgumentException("input invalid, password or name cannot contain /space/");
		}
		users.put(id, new User(name, id, password));
		return true;
	}
	
	public User signIn(int id, String password) {
		if(users.containsKey(id)) {
			User user = users.get(id);
			if(user.correctPassword(password)) {
				return user;
			}
			throw new IllegalArgumentException("incorrect Password");
		}
		throw new IllegalArgumentException("incorrect ID");
	}
	
	public User adminSignIn(int id, String password) {
		if(employees.containsKey(id)) {
			User user = employees.get(id);
			if(user.correctPassword(password)) {
				return user;
			}
			throw new IllegalArgumentException("incorrect Password");
		}
		throw new IllegalArgumentException("incorrect ID");
	}
	
	
	
	public boolean makeReservation(User user, int column, char row) {
		try {
			user.addSeat(getSeat(column, row));
			return seats.get(column).remove(row, seats.get(column).get(row));
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public void viewReservation(User user) {
		if(!user.getSeats().isEmpty()) {
			System.out.println("Name: "+user.getName());
			System.out.print("Seats:");
			String delimiter ="";
			for (Seat seat: user.getSeats()) {
				System.out.print(delimiter+" "+seat.getLocation()+" $"+seat.getPrice());
				delimiter = ",";
			}
			System.out.println();
			System.out.println("Total Balance Due: $"+user.getBalance());			
		}
		
	}
	
	
	public boolean cancelReservation(User user, int column, char row) {
		try {
			seats.get(column).put(row, user.removeSeat(column, row));
			return true;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public void printListOfReservations() {
		TreeMap<Seat, String> tempTreeMap = new TreeMap<>();
		if(!users.isEmpty()) {
			for (int id: users.keySet()) {
				User user = users.get(id);
				if(!user.getSeats().isEmpty()) {
					for(Seat seat : user.getSeats()) {
						tempTreeMap.put(seat, user.getName());
					}
				}
			}
		}
		if(!tempTreeMap.isEmpty()) {
			for(Seat seat: tempTreeMap.keySet()) {
				System.out.println(seat.getLocation()+": "+tempTreeMap.get(seat));
			}
		}
		
	}
	
	
	
	
	
	public boolean readUserFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			String name;
			int id;
			String password;
			while((line = reader.readLine()) != null) {
				Scanner scanner = new Scanner(line).useDelimiter("/space/");
				name = scanner.next();
				id = scanner.nextInt();
				password = scanner.next();
				users.put(id, new User(name, id, password));
				scanner.close();
			}
			reader.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return true;
	}
	
	public boolean readReservationFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			int id;
			int column;
			char row;
			PriorityQueue<Integer> unknownID = new PriorityQueue<>();
			
			while((line = reader.readLine()) != null) {
				Scanner scanner = new Scanner(line).useDelimiter("/space/");
				column = scanner.nextInt();
				row = scanner.next().charAt(0);
				id = scanner.nextInt();
				
				if(users.containsKey(id)) {
					User user = users.get(id);
					this.makeReservation(user, column, row);
				}
				else {
					unknownID.add(id);
				}
				scanner.close();
			}
			reader.close();
			if(!unknownID.isEmpty()) {	
				throw new IllegalArgumentException("read File error: id(s) "+unknownID.toString()+" don't exist");
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return true;
	}
	
	
	
	
	
	
	
	public boolean writeFile(String userFile, String reservationFile) {
		return writeUserFile(userFile) && writeReservationFile(reservationFile);
	}
	
	public boolean writeUserFile(String userFile) {
		String delimiter ="/space/";
		try {
			BufferedWriter userWriter = new BufferedWriter(new FileWriter(userFile));
			if(!users.isEmpty()) {
				for(Integer id : users.keySet()) {
					userWriter.write(users.get(id).getName()+delimiter);
					userWriter.write(users.get(id).getID()+delimiter);
					userWriter.write(users.get(id).getPassword()+"\n");
				}
			}
			userWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	
	public boolean writeReservationFile(String reservationFile) {
		String delimiter ="/space/";
		try {
			BufferedWriter reservationWriter = new BufferedWriter(new FileWriter(reservationFile));
			if(!users.isEmpty()) {
				for(Integer id : users.keySet()) {
					User user = users.get(id);
					TreeSet<Seat> userSeats = user.getSeats();
					if(!userSeats.isEmpty()) {				
						for(Seat seat: userSeats) {
							reservationWriter.write(seat.getColumn()+delimiter);
							reservationWriter.write(seat.getRow()+delimiter);
							reservationWriter.write(user.getID()+"\n");
						}
					}
				}
			}
			reservationWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	
}
