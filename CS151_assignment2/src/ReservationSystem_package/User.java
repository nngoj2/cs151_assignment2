package ReservationSystem_package;

import java.util.TreeSet;

public class User {
	private String name;
	private int id;
	private String password;
	private int balance;
	private TreeSet<Seat> seats;
	
	public User(String name, int id, String password) {
		seats = new TreeSet<>();
		balance = 0;
		this.name = name;
		this.id = id;
		this.password = password;
	}

	public String getName() {return name;}
	
	public int getID() {return id;}
	
	public int getBalance() {return balance;}
	
	public String getPassword() {return password;}
	
	public TreeSet<Seat> getSeats() {return seats;}
	
	public boolean correctPassword(String password) {
		return this.password.compareTo(password) == 0;
	}
	
	public boolean addSeat(Seat seat) {
		balance += seat.getPrice();
		return seats.add(seat);
		}
	
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
	
	public boolean hasReservation() {
		return !seats.isEmpty();
	}
	
	
	
	
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
