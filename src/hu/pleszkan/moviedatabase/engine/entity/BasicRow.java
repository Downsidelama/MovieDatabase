package hu.pleszkan.moviedatabase.engine.entity;

import java.sql.Date;

/**
 * Class to store the lent item's data
 * @author Tamas Pleszkan
 * @since 2018-04-14
 * @version 1.0
 */
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
		if (renter.length() <= 255) {
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
		return "ID: " + id + " MovieID: " + movieid + " Renter: " + renter + " Date: " + date + " DateOver: "
				+ dateOver;
	}

	public Object column(int i) {
		switch (i) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((dateOver == null) ? 0 : dateOver.hashCode());
		result = prime * result + id;
		result = prime * result + movieid;
		result = prime * result + ((renter == null) ? 0 : renter.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicRow other = (BasicRow) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (dateOver == null) {
			if (other.dateOver != null)
				return false;
		} else if (!dateOver.equals(other.dateOver))
			return false;
		if (id != other.id)
			return false;
		if (movieid != other.movieid)
			return false;
		if (renter == null) {
			if (other.renter != null)
				return false;
		} else if (!renter.equals(other.renter))
			return false;
		return true;
	}
}
