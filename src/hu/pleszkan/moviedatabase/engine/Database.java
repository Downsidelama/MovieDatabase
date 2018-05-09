package hu.pleszkan.moviedatabase.engine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hu.pleszkan.moviedatabase.engine.entity.BasicRow;
import hu.pleszkan.moviedatabase.engine.entity.Row;

/**
 * <h1>Database handler</h1> Singleton class
 * 
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

	public static Database getInstance() throws SQLException {
		if (instance == null) {
			try {
				instance = new Database();
			} catch (SQLException e) {
				throw e;
			}
		}

		return instance;
	}

	/**
	 * <h1>Database constructor</h1> Prepares the basic queries and sets things up.
	 * 
	 * @throws SQLException
	 */
	private Database() throws SQLException {
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
					"UPDATE movies SET title = ?, producer = ?, mainactor = ?, release = ?, length = ?, media = ?, genuinity = ?, rented = ?, timesrented = ?, picture = ? WHERE id=?");
			updateRentals = connection
					.prepareStatement("UPDATE rented SET movieid = ?, renter = ?, date = ?, dateover = ?");
			deleteRow = connection.prepareStatement("DELETE FROM movies WHERE id = ?");
			deletePirateMaterial = connection.prepareStatement("DELETE FROM movies WHERE genuinity = false");
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Deletes all the pirated material
	 * 
	 * @return returns the number of rows affected
	 */

	// Still need to delete all of them from rented
	public int panic() {
		int results = 0;
		try {
			results = deletePirateMaterial.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * Deletes the the movie which has the id in the parameter
	 * 
	 * @param id ID of the movie
	 * @return returns the number of rows affected
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
	 * Inserts into the rented table.
	 * 
	 * @param d BasicRow
	 * @return returns the rows affected
	 * @throws SQLException If something goes wrong
	 */
	public int insertIntoRented(BasicRow d) throws SQLException {
		int results = 0;

		try {
			insertNewRent.setInt(1, d.getMovieid());
			insertNewRent.setString(2, d.getRenter());
			insertNewRent.setDate(3, d.getDate());
			insertNewRent.setDate(4, d.getDateOver());

			results = insertNewRent.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}

		return results;
	}

	/**
	 * Inserts into Movie table
	 * 
	 * @param d Row
	 * @return Returns the number of affected rows
	 * @throws SQLException SQLexception
	 */
	public int insertIntoMovie(Row d) throws SQLException {
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
			throw e;
		}

		return results;
	}

	/**
	 * Updates the movie table with the given parameter
	 * 
	 * @param d Row
	 * @throws SQLException SQLexception
	 * @return Returns the affected number of rows
	 */
	public int updateMovieRow(Row d) throws SQLException {
		int results = 0;

		try {
			updateMovieRow.setString(1, d.getTitle());
			updateMovieRow.setString(2, d.getProducer());
			updateMovieRow.setString(3, d.getMainactor());
			updateMovieRow.setInt(4, d.getRelease());
			updateMovieRow.setInt(5, d.getLength());
			updateMovieRow.setString(6, d.getMedia());
			updateMovieRow.setBoolean(7, d.isGenuinity());
			updateMovieRow.setBoolean(8, d.isRented());
			updateMovieRow.setInt(9, d.getTimesrented());
			updateMovieRow.setString(10, d.getPicture());
			updateMovieRow.setInt(11, d.getId());

			results = updateMovieRow.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}

		return results;
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
				results.add(new Row(rs.getInt("id"), rs.getString("title"), rs.getString("producer"),
						rs.getString("mainactor"), rs.getInt("release"), rs.getInt("length"), rs.getString("media"),
						rs.getBoolean("genuinity"), rs.getBoolean("rented"), rs.getInt("timesrented"),
						rs.getString("picture")));
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
		ArrayList<BasicRow> results = null;
		ResultSet rs = null;
		try {
			rs = selectAllRentedMovies.executeQuery();
			results = new ArrayList<>();
			while (rs.next()) {
				results.add(new BasicRow(rs.getInt("id"), rs.getInt("movieid"), rs.getString("renter"),
						rs.getDate("date"), rs.getDate("dateover")));
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
							+ "picture VARCHAR(255) NOT NULL," + "CONSTRAINT primary_key PRIMARY KEY (id))");
			ps.execute();
		} catch (SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				e.printStackTrace();
			}
		}

		try {
			PreparedStatement ps = c.prepareStatement(
					"CREATE TABLE rented (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
							+ "movieid INTEGER NOT NULL," + "renter VARCHAR(255) NOT NULL, " + "date date NOT NULL, "
							+ "dateover date, CONSTRAINT primary_key2 PRIMARY KEY (id))");
			ps.execute();
		} catch (SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * Gets a movie by its ID.
	 * 
	 * @param id ID of the movie
	 * @return The name
	 */
	public String getMovieNameByID(int id) {
		ResultSet rs = null;
		String result = null;
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT title FROM movies WHERE id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getString("title");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Closes the db connection
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loans the movie.
	 * 
	 * @param r Row
	 * @param renter Renter
	 * @param date Date
	 * @param dateOver DateOver
	 * @throws SQLException Exception
	 */
	public void rent(Row r, String renter, Date date, Date dateOver) throws SQLException {
		r.setRented(true);
		r.setTimesrented(r.getTimesrented() + 1);
		try {
			updateMovieRow(r);
			insertIntoRented(new BasicRow(r.getId(), renter, date, dateOver));
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Takes back the movie from the person who rented it.
	 * 
	 * @param row Row
	 */
	public void takeBack(BasicRow row) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE rented SET dateOver = ? WHERE id=?");
			ps.setDate(1, new Date(System.currentTimeMillis()));
			ps.setInt(2, row.getId());
			ps.executeUpdate();

			ps = connection.prepareStatement("UPDATE movies SET rented = false WHERE id=?");
			ps.setInt(1, row.getMovieid());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
