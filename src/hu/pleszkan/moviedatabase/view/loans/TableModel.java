package hu.pleszkan.moviedatabase.view.loans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import hu.pleszkan.moviedatabase.engine.Database;
import hu.pleszkan.moviedatabase.engine.entity.BasicRow;

public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = 457624443870320913L;
	private String[] columnNames = { "Movie Name", "Renter", "Date Rented", "Date Returned" };
	private Database db;
	private ArrayList<BasicRow> data;
	private boolean showDone;
	private String itemToSearch;

	public ArrayList<BasicRow> getData() {
		return data;
	}

	public TableModel() {
		super();
		try {
			db = Database.getInstance();
		} catch (SQLException e) {
		}
		data = db.getAllRents();
		showDone = true;
		itemToSearch = "";
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
		if (arg1 == 0) {
			return db.getMovieNameByID(data.get(arg0).getMovieid());
		}
		return data.get(arg0).column(arg1 + 1);
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

	public BasicRow getRow(int rowId) {
		return data.get(rowId);
	}

	public void setShowAll(boolean b) {
		showDone = b;
		fireTableDataChanged();
	}

	public void setSearch(String s) {
		itemToSearch = s;
		fireTableDataChanged();
	}

	public void filter() {
		data = db.getAllRents();
		if (itemToSearch != null && !itemToSearch.equals("")) {
			this.data = this.data.stream().filter(t -> t.getRenter().contains(itemToSearch))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		if (!showDone) {
			this.data = this.data.stream().filter(t -> t.getDateOver() == null)
					.collect(Collectors.toCollection(ArrayList::new));
		}
	}

}
