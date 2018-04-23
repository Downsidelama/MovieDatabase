package engine;

import java.sql.Date;
import java.sql.SQLException;

import engine.entity.BasicRow;

public class tester {

	public static void main(String[] args) {
		try {
			Database d = Database.getInstance();
			d.insertIntoRented(new BasicRow(1, "Tomi", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()+100000000)));
			for(BasicRow br : d.getAllRents()) {
				System.out.println(br.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
