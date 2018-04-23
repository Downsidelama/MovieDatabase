package view.listing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import engine.Database;
import engine.entity.Row;

/**
 * <h1>Custom TableModel</h1>
 * @author Tamas Pleszkan
 * @version 1.0
 * @since 2018-04-14
 */
public class TableModel extends AbstractTableModel {
	private static final long serialVersionUID = 9047572394630079603L;
	private String[] columnNames = {"Title", "Director", "Main Actors", "Release year", "Duration", "Media", "Genuine", "Loaned", "Times Rented"};
	private ArrayList<Row> data;
	private Database db = null;
	private String title;
	private String director;
	private String mainActor;
	private int release;
	private int duration;
	private String media;
	private boolean genuine;
	private boolean loaned;
	private boolean filterRented;
	private boolean filterGenuine;
	private boolean filterMedia;
	
	public void setRelease(int release) {
		this.release = release;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public void setGenuine(boolean genuine) {
		this.genuine = genuine;
	}

	public void setLoaned(boolean loaned) {
		this.loaned = loaned;
	}

	public TableModel() {
		super();
		try {
			db = Database.getInstance();
		} catch (SQLException e) {}
		data = db.getAllMovies();
		filterRented = false;
		filterGenuine = false;
		filterMedia = false;
		release = -1;
		duration = -1;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return data.get(arg0).column(arg1+1);
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public void fireTableDataChanged() {
		filter();
		super.fireTableDataChanged();
	}
	
	public Row getRow(int rowId) {
		return data.get(rowId);
	}
	
	public void setTitle(String s) {
		title = s;
	}
	
	public void setDirector(String s) {
		director = s;
	}
	
	public void setFilterRented(boolean b) {
		filterRented = b;
	}
	
	public void setFilterGenuine(boolean b) {
		filterGenuine = b;
	}
	
	public void setMainActor(String s) {
		mainActor = s;
	}
	
	public void filter() {
		data = db.getAllMovies();
		if(title != null && !title.equals("")) {
			data = data.stream().filter(t -> t.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
		}
		if(director != null && !director.equals("")) {
			data = data.stream().filter(t -> t.getProducer().toLowerCase().contains(director.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
		}
		if(mainActor != null && !mainActor.equals("")) {
			data = data.stream().filter(t -> t.getMainactor().toLowerCase().contains(mainActor.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
		}
		if(release != -1) {
			data = data.stream().filter(t -> t.getRelease() == release).collect(Collectors.toCollection(ArrayList::new));
		}
		if(duration != -1) {
			data = data.stream().filter(t -> t.getLength() == duration).collect(Collectors.toCollection(ArrayList::new));
		}
		if(media != null && !media.equals("")) {
			data = data.stream().filter(t -> t.getMedia().equals(media)).collect(Collectors.toCollection(ArrayList::new));
		}
		if(filterRented) {
			data = data.stream().filter(t -> t.isRented() == loaned).collect(Collectors.toCollection(ArrayList::new));
		}
		if(filterGenuine) {
			data = data.stream().filter(t -> t.isGenuinity() == genuine).collect(Collectors.toCollection(ArrayList::new));
		}
	}

	public void setFilterMedia(boolean b) {
		filterMedia = b;
	}
	
}
