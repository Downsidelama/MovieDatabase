package hu.pleszkan.moviedatabase.engine.entity;

import java.sql.Date;

public class BasicRow {
	private int id;
	private int movieid;
	private String renter;
	private Date date;
	private Date dateOver;

	public BasicRow(int id, int movieid, String renter, Date date, Date dateOver) {
		this.id = id;
		this.movieid = movieid;
		this.renter = renter;
		this.date = date;
		this.dateOver = dateOver;
	}
	
	public BasicRow(int movieid, String renter, Date date, Date dateOver) {
		this.id = 0;
		this.movieid = movieid;
		this.renter = renter;
		this.date = date;
		this.dateOver = dateOver;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMovieid() {
		return movieid;
	}

	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}

	public String getRenter() {
		return renter;
	}

	public void setRenter(String renter) {
		if(renter.length() <= 255) {
			this.renter = renter;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateOver() {
		return dateOver;
	}

	public void setDateOver(Date dateOver) {
		this.dateOver = dateOver;
	}
	
	public String toString() {
		return "ID: " + id + " MovieID: " + movieid + " Renter: " + renter + " Date: " + date + " DateOver: " + dateOver;
	}

	public Object column(int i) {
		switch(i) {
		case 1:
			return getMovieid();
		case 2:
			return getRenter();
		case 3:
			return getDate();
		case 4:
			return getDateOver();
		}
		return null;
	}
}
