/**
 * CIS151 Spring 2025 Assignment 2 Solution
 * @author Nam Tan Ngo
 * @version 1.0 02/23/2025
 */

package ReservationSystem_package;

/**
 * An seat that holds the location, type, price on a reservation system 
 * It can compare between seat
 */
public class Seat implements Comparable<Seat>{

	private int column;
	private char row;
	private String location;
	private String type;
	private int price;
	
	/**
	 * Construct a seat with specific location, type, price
	 * @param column - the column of the seat in system, a number
	 * @param row - the row of the seat in system, a character
	 * @param type - type of seat, relative to price
	 * @param price - price of seat
	 */
	public Seat(int column, char row, String type, int price) {	
		this.column = column;
		this.row = row;
		this.type = type;
		this.price = price;
		location = Integer.toString(column)+Character.toString((char)row);
	}
	
	/**
	 * return the location of the seat
	 * @return location
	 */
	public String getLocation() {return location;}
	
	/**
	 * return type of the seat
	 * @return type
	 */
	public String getType() {return type;}
	
	/**
	 * return price of the seat
	 * @return price
	 */
	public int getPrice() {return price;}
	
	/**
	 * return column of seat
	 * @return column
	 */
	public int getColumn() {return column;}
	
	/**
	 * return row of the seat
	 * @return row
	 */
	public char getRow() {return row;}

	@Override
	/**
	 * compare between seat
	 * it will return a positive number if this seat has higher column or row than the other seat
	 * 0 if equal,
	 * negative number if lower column or row
	 * @param seat - the other seat
	 * @return an integer
	 */
	public int compareTo(Seat seat) {
		int col = column - seat.getColumn();
		return (col !=0)? col: row-seat.getRow();
	}

	/**
	 * check if this seat are in this location
	 * @param column - column of location
	 * @param row - row of location
	 * @return true if the seat is in the location, false otherwise
	 */
	public boolean equal(int column, char row) {
		return (this.column - column) == 0 &&
				(this.row - row) == 0;
	}
}
