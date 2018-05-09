package hu.pleszkan.moviedatabase.view.listing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import hu.pleszkan.moviedatabase.engine.Database;
import hu.pleszkan.moviedatabase.engine.entity.Row;

import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * <h1>Movie lister frame</h1>
 * 
 * @author Tamas Pleszkan
 * @version 1.0
 * @since 2018-04-14
 */
public class Lister {

	private JFrame frmMovieDatabase;
	private JTable table;
	private Image scaledImage;
	private BufferedImage image;
	private JLabel label;
	private JButton btnNewButton;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnLend;
	private JButton btnSearch;
	private JButton btnPnik;
	private JButton btnRentals;
	private Database db;
	private TableModel tm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lister window = new Lister();
					window.frmMovieDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lister() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		tm = new TableModel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		try {
			db = Database.getInstance();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "SQL Exception!", JOptionPane.OK_OPTION);
		}
		frmMovieDatabase = new JFrame();
		frmMovieDatabase.setTitle("Movie Database");
		frmMovieDatabase.setBounds(100, 100, 1280, 900);
		frmMovieDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMovieDatabase.setMinimumSize(new Dimension(1280, 900));

		JScrollPane scrollPane = new JScrollPane();
		/*
		 * try { File img = new File("img/" + tm.getRow(0).getPicture()); if
		 * (img.exists()) image = ImageIO.read(img); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		label = new JLabel();

		btnNewButton = new JButton("Add new");
		btnNewButton.addActionListener((e) -> new AddEdit(true, tm).showWindow());

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					new AddEdit(false, tm, tm.getRow(table.getSelectedRow())).showWindow();
				}
			}

		});

		btnDelete = new JButton("Delete");

		btnLend = new JButton("Lend");
		btnLend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					Row r = tm.getRow(table.getSelectedRow());
					if (!r.isRented()) {
						new LendingWindow(r, tm).showWindow();
					}
				}
			}
		});

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SearchWindow(tm).showWindow();
			}
		});

		btnPnik = new JButton("PANIC");
		btnPnik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.panic();
				tm.fireTableDataChanged();
			}
		});

		btnRentals = new JButton("Rentals");
		btnRentals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new hu.pleszkan.moviedatabase.view.loans.Lister(tm).showWindow();
			}
		});

		GroupLayout groupLayout = new GroupLayout(frmMovieDatabase.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(6)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnEdit, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
										.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
										.addComponent(btnLend, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
										.addComponent(btnRentals, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
										.addComponent(btnPnik, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 232,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING,
								groupLayout.createSequentialGroup().addGap(6).addComponent(label,
										GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE).addGap(3)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnNewButton).addGap(22)
								.addComponent(btnEdit).addGap(25).addComponent(btnDelete).addGap(27)
								.addComponent(btnLend).addGap(25).addComponent(btnSearch).addGap(261)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE).addComponent(btnPnik)
								.addGap(21).addComponent(btnRentals)))
				.addContainerGap()));

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tm.fireTableDataChanged();
		table.setModel(tm);
		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						File imgFile = new File("img/" + tm.getRow(table.getSelectedRow()).getPicture());
						if (imgFile.exists()) {
							image = ImageIO.read(imgFile);

							label.setIcon(new ImageIcon(
									image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH)));
						} else {
							label.setIcon(null);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(null, "An error occured when reading the file!");
					}
				}
			}

		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int n = JOptionPane.showConfirmDialog(null, "Would you like to delete this movie?", "Delete",
							JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						if (db.deleteMovie(tm.getRow(table.getSelectedRow()).getId()) > 0) {
							JOptionPane.showMessageDialog(null, "Succesfully deleted!");
							new File("img/" + tm.getRow(table.getSelectedRow()).getPicture()).delete();
							updateTable();
							label.setIcon(null);
						} else {
							JOptionPane.showMessageDialog(null, "An error occured, please contact the developer!");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Select a movie first!", "Error", JOptionPane.OK_OPTION);
				}
			}
		});

		frmMovieDatabase.getContentPane().setLayout(groupLayout);
	}

	public void updateTable() {
		tm.fireTableDataChanged();
	}
}
