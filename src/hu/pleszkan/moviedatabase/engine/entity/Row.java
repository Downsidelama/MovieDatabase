package hu.pleszkan.moviedatabase.engine.entity;

/**
 * <h1>Class to hold the movie table's rows</h1>
 * 
 * @author Tamas Pleszkan
 * @version 1.0
 * @since 2018-04-14
 */
public class Row {
	private String title;
	private String producer;
	private String mainactor;
	private int release;
	private int length;
	private String media;
	private boolean genuinity;
	private boolean rented;
	private int timesrented;
	private int id;
	private String picture;

	/**
	 * The constructor to set up the data.
	 * 
	 * @param title
	 * @param producer
	 * @param mainactor
	 * @param release
	 * @param length
	 * @param media
	 * @param genuinity
	 * @param rented
	 * @param timesrented
	 * @exception IllegalArgumentException
	 *                on Strings longer than 255
	 */
	public Row(String title, String producer, String mainactor, int release, int length, String media,
			boolean genuinity, boolean rented, int timesrented, String picture) {
		super();
		if (title.length() <= 255 && producer.length() <= 255 && mainactor.length() <= 255 && media.length() <= 255) {
			this.title = title;
			this.producer = producer;
			this.mainactor = mainactor;
			this.release = release;
			this.length = length;
			this.media = media;
			this.genuinity = genuinity;
			this.rented = rented;
			this.timesrented = timesrented;
			this.picture = picture;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Row(int id, String title, String producer, String mainactor, int release, int length, String media,
			boolean genuinity, boolean rented, int timesrented, String picture) {
		this(title, producer, mainactor, release, length, media, genuinity, rented, timesrented, picture);
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title.length() <= 255) {
			this.title = title;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		if (producer.length() <= 255) {
			this.producer = producer;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getMainactor() {
		return mainactor;
	}

	public void setMainactor(String mainactor) {
		if (mainactor.length() <= 255) {
			this.mainactor = mainactor;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public int getRelease() {
		return release;
	}

	public void setRelease(int release) {
		this.release = release;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		if (media.length() <= 255) {
			this.media = media;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public boolean isGenuinity() {
		return genuinity;
	}

	public void setGenuinity(boolean genuinity) {
		this.genuinity = genuinity;
	}

	public boolean isRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	public int getTimesrented() {
		return timesrented;
	}

	public void setTimesrented(int timesrented) {
		this.timesrented = timesrented;
	}

	public Object column(int arg1) {
		switch (arg1) {
		case 1:
			return getTitle();
		case 2:
			return getProducer();
		case 3:
			return getMainactor();
		case 4:
			return new Integer(getRelease());
		case 5:
			return new Integer(getLength());
		case 6:
			return getMedia();
		case 7:
			return new Boolean(isGenuinity());
		case 8:
			return new Boolean(isRented());
		case 9:
			return new Integer(getTimesrented());
		case 10:
			return getPicture();
		}
		return null;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getId() {
		return id;
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
		result = prime * result + (genuinity ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + length;
		result = prime * result + ((mainactor == null) ? 0 : mainactor.hashCode());
		result = prime * result + ((media == null) ? 0 : media.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + release;
		result = prime * result + (rented ? 1231 : 1237);
		result = prime * result + timesrented;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Row other = (Row) obj;
		if (genuinity != other.genuinity)
			return false;
		if (id != other.id)
			return false;
		if (length != other.length)
			return false;
		if (mainactor == null) {
			if (other.mainactor != null)
				return false;
		} else if (!mainactor.equals(other.mainactor))
			return false;
		if (media == null) {
			if (other.media != null)
				return false;
		} else if (!media.equals(other.media))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (release != other.release)
			return false;
		if (rented != other.rented)
			return false;
		if (timesrented != other.timesrented)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
