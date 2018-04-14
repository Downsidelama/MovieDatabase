package engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import engine.entity.BasicRow;
import engine.entity.Row;

/**
 * <h1>Database handler</h1>
 * Singleton class
 * @author Tamas Pleszkan
 * @version 1.0
 * @since 2018-04-14
 */
public class Database {

	private final String DBNAME = "jdbc:derby://localhost:1527/db;create=true";

	private Connection connection;
	private PreparedStatement selectAllMovie;
	private PreparedStatement selectAllRentedMovies;
	private PreparedStatement insertNewMovie;
	private PreparedStatement insertNewRent;
	private PreparedStatement updateMovieRow;
	private PreparedStatement updateRentals;
	private PreparedStatement deleteRow;
	private static Database instance = null;
	private PreparedStatement deletePirateMaterial;
	
	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		
		return instance;
	}

	/**
	 * <h1>Database constructor</h1> Prepares the basic queries and sets things up.
	 */
	private Database() {
		try {
			connection = DriverManager.getConnection(DBNAME);
			createTablesIfNotExists(connection);
			selectAllMovie = connection.prepareStatement("SELECT * FROM movies");
			selectAllRentedMovies = connection.prepareStatement("SELECT * FROM rented");
			insertNewMovie = connection.prepareStatement("INSERT INTO movies (title, producer,"
					+ " mainactor, release, length, media, genuinity, rented, timesrented, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertNewRent = connection
					.prepareStatement("INSERT INTO rented (movieid, renter, date, dateover) VALUES(?, ?,?,?)");
			updateMovieRow = connection.prepareStatement(
					"UPDATE movies SET title = ?, producer = ?, mainactor = ?, release = ?, length = ?, media = ?, genuinity = ?, rented = ?, timesrented = ?, picture = ? WHERE title = ?");
			updateRentals = connection
					.prepareStatement("UPDATE rented SET movieid = ?, renter = ?, date = ?, dateover = ?");
			deleteRow = connection.prepareStatement("DELETE FROM movies WHERE id = ?");
			deletePirateMaterial = connection.prepareStatement("DELETE FROM movies WHERE genuinity = false");
			//fillDatabaseWithDummyData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes all the pirated material
	 * @return
	 */
	
	// Still need to delete all of them from rented
	public int panic() {
		int results = 0;
		try {
			results = deletePirateMaterial.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Deletes the the movie which has the id in the parameter
	 * @param id
	 * @return
	 */
	public int deleteMovie(int id) {
		int results = 0;
		try {
			deleteRow.setInt(1, id);
			results = deleteRow.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Inserts into Movie table
	 * @param d
	 * @return Returns the number of affected rows
	 */
	public int insertIntoMovie(Row d) {
		int results = 0;
		
		try {
			insertNewMovie.setString(1, d.getTitle());
			insertNewMovie.setString(2, d.getProducer());
			insertNewMovie.setString(3, d.getMainactor());
			insertNewMovie.setInt(4, d.getRelease());
			insertNewMovie.setInt(5, d.getLength());
			insertNewMovie.setString(6, d.getMedia());
			insertNewMovie.setBoolean(7, d.isGenuinity());
			insertNewMovie.setBoolean(8, d.isRented());
			insertNewMovie.setInt(9, d.getTimesrented());
			insertNewMovie.setString(10, d.getPicture());
			
			results = insertNewMovie.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}

	/**
	 * Updates the rented table with the given parameter
	 * 
	 * @param d
	 */
	public void updateRentedRow(BasicRow d) {

	}

	/**
	 * Updates the movie table with the given parameter
	 * 
	 * @param d
	 */
	public void updateMovieRow(Row d) {
		// PreparedStatement ps = connection.prepareStatement("");

	}

	/**
	 * 
	 * @return All of the movies from the database
	 */
	public ArrayList<Row> getAllMovies() {
		ArrayList<Row> results = null;
		ResultSet rs = null;

		try {
			rs = selectAllMovie.executeQuery();
			results = new ArrayList<Row>();

			while (rs.next()) {
				results.add(new Row(rs.getInt("id"), rs.getString("title"), rs.getString("producer"), rs.getString("mainactor"),
						rs.getInt("release"), rs.getInt("length"), rs.getString("media"), rs.getBoolean("genuinity"),
						rs.getBoolean("rented"), rs.getInt("timesrented"), rs.getString("picture")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	/**
	 * 
	 * @return All of the rents from the database
	 */
	public ArrayList<BasicRow> getAllRents() {
		
		return null;
	}

	/**
	 * This method creates the default table in the DB, if they do not exists.
	 * 
	 * @param c
	 * @return true
	 */
	private boolean createTablesIfNotExists(Connection c) {
		try {
			PreparedStatement ps = c.prepareStatement(
					"CREATE TABLE movies (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
							+ "title VARCHAR(255), " + "producer VARCHAR(255) NOT NULL, "
							+ "mainactor VARCHAR(255) NOT NULL, " + "release INT NOT NULL, length INT NOT NULL, "
							+ "media VARCHAR(255) NOT NULL, " + "genuinity BOOLEAN NOT NULL, "
							+ "rented BOOLEAN NOT NULL, " + "timesrented INT NOT NULL,"
									+ "picture VARCHAR(255) NOT NULL,"
							+ "CONSTRAINT primary_key PRIMARY KEY (id))");
			ps.execute();
		} catch (SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				e.printStackTrace();
			} else {
				System.out.println("Table exists");
			}
		}

		try {
			PreparedStatement ps = c.prepareStatement(
					"CREATE TABLE rented (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
							+ "movieid INTEGER NOT NULL," + "renter VARCHAR(255) NOT NULL, " + "date date NOT NULL, "
							+ "dateover date NOT NULL, CONSTRAINT primary_key2 PRIMARY KEY (id))");
			ps.execute();
		} catch (SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				e.printStackTrace();
			} else {
				System.out.println("Table exists");
			}
		}
		return true;
	}
	
	public void fillDatabaseWithDummyData() {
		insertIntoMovie(new Row("Lost in Space", "Toby Stephens", "Molly Parker", 2018, 60, "DVD", true, false, 10, "picture.png"));
		insertIntoMovie(new Row("Incredibles 2", "Producer John", "John Doe", 2017, 123, "CD", true, true, 10, "picture.png"));
		insertIntoMovie(new Row("Overboard", "Bob Fisher", "Anna Faris", 2018, 135, "Blu-Ray", false, false, 10, "picture.png"));
		insertIntoMovie(new Row("The Rider", "Chloe Zhao", "Brady Jandreau", 1995, 104, "DVD", true, false, 10, "picture.png"));
		insertIntoMovie(new Row("Beirut", "Brad Anderson", "Rosamund Pike", 2000, 133, "Blu-Ray", false, true, 10, "picture2.png"));
	}

	/**
	 * Closes the db connection
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
