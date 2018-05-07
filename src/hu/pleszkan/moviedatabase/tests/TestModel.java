package hu.pleszkan.moviedatabase.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import hu.pleszkan.moviedatabase.engine.Database;
import hu.pleszkan.moviedatabase.engine.entity.Row;

public class TestModel {

	private static Connection connection;
	private static final String DBNAME = "jdbc:derby://localhost:1527/db;create=true";
	
	static {
		try {
			connection = DriverManager.getConnection(DBNAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testing the functionality of the panic button.
	 */
	@Test
	public void testPanicButton() {
		try {
			PreparedStatement insertNewMovie = connection.prepareStatement("INSERT INTO movies (title, producer,"
					+ " mainactor, release, length, media, genuinity, rented, timesrented, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertNewMovie.setString(1, "Dummy Data");
			insertNewMovie.setString(2, "Dummy Data");
			insertNewMovie.setString(3, "Dummy Data");
			insertNewMovie.setInt(4, 1999);
			insertNewMovie.setInt(5, 1999);
			insertNewMovie.setString(6, "DVD");
			insertNewMovie.setBoolean(7, false);
			insertNewMovie.setBoolean(8, true);
			insertNewMovie.setInt(9, 1);
			insertNewMovie.setString(10, "Dummy Data");
			
			insertNewMovie.executeUpdate();
			
			Database.getInstance().panic();
			
			PreparedStatement check = connection.prepareStatement("SELECT COUNT(id) FROM movies WHERE genuinity=false");
			ResultSet rs = check.executeQuery();
			int count = 0;
			while(rs.next()) {
				count = rs.getInt("1");
			}
			
			rs.close();
			
			assertEquals(count, 0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testing the functionality of the listing method.
	 */
	@Test
	public void testMovieListing() {
		try {
			PreparedStatement selectAllMovie = connection.prepareStatement("SELECT * FROM movies");
			ResultSet rs = selectAllMovie.executeQuery();
			ArrayList<Row> results = new ArrayList<Row>();
			while(rs.next()) {
				results.add(new Row(rs.getInt("id"), rs.getString("title"), rs.getString("producer"),
						rs.getString("mainactor"), rs.getInt("release"), rs.getInt("length"), rs.getString("media"),
						rs.getBoolean("genuinity"), rs.getBoolean("rented"), rs.getInt("timesrented"),
						rs.getString("picture")));
			}
			
			ArrayList<Row> original = Database.getInstance().getAllMovies();
			assertTrue(original.equals(results));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Tests the movie deleting feature of the program.
	 */
	@Test
	public void testMovieDeletion() {
		try {
			PreparedStatement insertNewMovie = connection.prepareStatement("INSERT INTO movies (title, producer,"
					+ " mainactor, release, length, media, genuinity, rented, timesrented, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertNewMovie.setString(1, "Dummy Data");
			insertNewMovie.setString(2, "Dummy Data");
			insertNewMovie.setString(3, "Dummy Data");
			insertNewMovie.setInt(4, 1999);
			insertNewMovie.setInt(5, 1999);
			insertNewMovie.setString(6, "DVD");
			insertNewMovie.setBoolean(7, false);
			insertNewMovie.setBoolean(8, true);
			insertNewMovie.setInt(9, 1);
			insertNewMovie.setString(10, "Dummy Data");
			
			insertNewMovie.executeUpdate();
			connection.createStatement().setMaxRows(1);
			ResultSet rs = connection.prepareStatement("SELECT id FROM movies ORDER BY id DESC").executeQuery();
			int id = -1;
			while(rs.next()) {
				id = rs.getInt("id");
			}
			insertNewMovie.executeUpdate();
			Database.getInstance().deleteMovie(id+1);
			connection.createStatement().setMaxRows(1);
			rs = connection.prepareStatement("SELECT id FROM movies ORDER BY id DESC").executeQuery();
			int id2 = -1;
			while(rs.next()) {
				id2 = rs.getInt("id");
			}
			assertEquals(id, id2);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the movie editing function.
	 */
	@Test
	public void testMovieEditing() {
		PreparedStatement insertNewMovie;
		try {
			insertNewMovie = connection.prepareStatement("INSERT INTO movies (title, producer,"
					+ " mainactor, release, length, media, genuinity, rented, timesrented, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertNewMovie.setString(1, "Dummy Data");
			insertNewMovie.setString(2, "Dummy Data");
			insertNewMovie.setString(3, "Dummy Data");
			insertNewMovie.setInt(4, 1999);
			insertNewMovie.setInt(5, 1999);
			insertNewMovie.setString(6, "DVD");
			insertNewMovie.setBoolean(7, false);
			insertNewMovie.setBoolean(8, true);
			insertNewMovie.setInt(9, 1);
			insertNewMovie.setString(10, "Dummy Data");
			insertNewMovie.executeUpdate();
			connection.createStatement().setMaxRows(1);
			ResultSet rs = connection.prepareStatement("SELECT id FROM movies ORDER BY id DESC").executeQuery();
			int id = -1;
			while(rs.next()) {
				id = rs.getInt("id");
			}
			
			Database.getInstance().updateMovieRow(new Row(id, "Dummy data", "Dummy Data", "Dummy Data", 1999, 1999, "DVD", false, true, 1, "Dummy Data2"));
			
			connection.createStatement().setMaxRows(1);
			PreparedStatement ps = connection.prepareStatement("SELECT picture FROM movies WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs1 = ps.executeQuery();
			String picture = "";
			while(rs1.next()) {
				picture = rs1.getString("picture");
			}
			
			assertTrue(picture.equals("Dummy Data2"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Tests the movie adding feature.
	 */
	@Test
	public void testMovieAdding() {
		try {
			PreparedStatement check = connection.prepareStatement("SELECT COUNT(id) FROM movies");
			ResultSet rs = check.executeQuery();
			int count = 0;
			while(rs.next()) {
				count = rs.getInt("1");
			}
			
			Database.getInstance().insertIntoMovie(new Row("a", "a", "a", 1, 0, "a", true, true, 1, "a"));
			
			rs = check.executeQuery();
			int count2 = 0;
			while(rs.next()) {
				count2 = rs.getInt("1");
			}
			
			assertTrue(count2 > count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMovieLending() {
		
	}
	
	@Test
	public void testMovieFiltering() {
		
	}
	
	@Test
	public void testLoanListing() {
		
	}
	
	@Test
	public void testTakingBackLoanedMovies() {
		
	}
	
	@Test
	public void testLoanFiltering() {
		
	}
}
