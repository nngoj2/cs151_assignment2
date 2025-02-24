/**
 * CIS151 Spring 2025 Assignment 2 Solution
 * @author Nam Tan Ngo
 * @version 1.0 02/23/2025
 */

package ReservationSystem_package;

import java.util.TreeSet;
/**
 * a user data type that hold the information of system user 
 * It can make/cancel/check reservation hold by user
 * It can validate user
 */
public class User {
	private String name;
	private int id;
	private String password;
	private int balance;
	private TreeSet<Seat> seats;
	
	/**
	 * construct a user with specific name, id, password
	 * @param name - name of user
	 * @param id - id of account
	 * @param password - password of account
	 * precondition: name and password cannot contain '/space/'
	 */
	public User(String name, int id, String password) {
		seats = new TreeSet<>();
		balance = 0;
		this.name = name;
		this.id = id;
		this.password = password;
	}

	/**
	 * return name of user
	 * @return name
	 */
	public String getName() {return name;}
	
	/**
	 * return id of account
	 * @return id
	 */
	public int getID() {return id;}
	
	/**
	 * return balance of account, how much user have to pay
	 * @return balance
	 */
	public int getBalance() {return balance;}
	
	/**
	 * return password of account
	 * @return password
	 */
	public String getPassword() {return password;}
	
	/**
	 * return all reservation seats of user in order
	 * @return list of reservation seat
	 */
	public TreeSet<Seat> getSeats() {return seats;}
	
	/**
	 * check if person logging in are user
	 * @param password - password of account
	 * @return true if they type in the correct password, false otherwise
	 */
	public boolean correctPassword(String password) {
		return this.password.compareTo(password) == 0;
	}
	
	/**
	 * add seat to user list of reservation
	 * @param seat - a seat in reservation system
	 * @return true if add success, false otherwise
	 * postcondition: it will add one more element to the reservation list
	 */
	public boolean addSeat(Seat seat) {
		balance += seat.getPrice();
		return seats.add(seat);
		}
	
	/**
	 * remove a seat from the user list of reservation
	 * @param column - column in location of seat
	 * @param row - row in location of seat
	 * @return the seat that has been removed from the list
	 * postcondition: remove a seat out of reservation list
	 */
	public Seat removeSeat(int column, char row) {
		try {
			Seat seat = getSeat(column, row);
			seats.remove(getSeat(column, row));
			return seat;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * check if user still have any reservation
	 * @return true if they have any reservation, false otherwise
	 */
	public boolean hasReservation() {
		return !seats.isEmpty();
	}
	
	
	
	/**
	 * return the seat with parameter location if it exist in reservation list of user
	 * @param column - column in location of seat
	 * @param row - row in location of seat
	 * @return - seat in that location
	 * @throws IllegalArgumentException if user doesn't hold this seat
	 */
	public Seat getSeat(int column, char row) {
		if(!seats.isEmpty()) {
			for(Seat seat: seats) {
				if(seat.equal(column, row)) {
					return seat;
				}
			}
			throw new IllegalArgumentException("input invalid, this user doesn't hold this seat");
		}
		throw new IllegalArgumentException("Error, this user doesn't have any reservation");
	}
	

	
}
