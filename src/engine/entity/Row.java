package engine.entity;

/**
 * <h1>Class to hold the movie table's rows</h1>
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
	 * @param title
	 * @param producer
	 * @param mainactor
	 * @param release
	 * @param length
	 * @param media
	 * @param genuinity
	 * @param rented
	 * @param timesrented
	 * @exception IllegalArgumentException on Strings longer than 255
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
		switch(arg1) {
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
}
