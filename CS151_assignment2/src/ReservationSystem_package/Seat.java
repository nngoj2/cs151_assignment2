package ReservationSystem_package;

public class Seat implements Comparable<Seat>{

	private int column;
	private char row;
	private String location;
	private String type;
	private int price;
	
	public Seat(int column, char row, String type, int price) {	
		this.column = column;
		this.row = row;
		this.type = type;
		this.price = price;
		location = Integer.toString(column)+Character.toString((char)row);
	}
	
	public String getLocation() {return location;}
	public String getType() {return type;}
	public int getPrice() {return price;}
	public int getColumn() {return column;}
	public char getRow() {return row;}

	@Override
	public int compareTo(Seat seat) {
		int col = column - seat.getColumn();
		return (col !=0)? col: row-seat.getRow();
	}

	public boolean equal(int column, char row) {
		return (this.column - column) == 0 &&
				(this.row - row) == 0;
	}
	
}
