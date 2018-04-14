package view.listing;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import engine.Database;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private JTextField textField;
	private Database db;
	private JButton btnDummydata;
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		db = Database.getInstance();
		frmMovieDatabase = new JFrame();
		frmMovieDatabase.setTitle("Movie Database");
		frmMovieDatabase.setBounds(100, 100, 1132, 766);
		frmMovieDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMovieDatabase.setMinimumSize(new Dimension(1280, 768));

		JScrollPane scrollPane = new JScrollPane();
		tm = new TableModel();
		try {
			File img = new File("img/" + tm.getRow(0).getPicture());
			System.out.println(img.getAbsolutePath());
			image = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		label = new JLabel();
		
		btnNewButton = new JButton("Add new");
		
		btnEdit = new JButton("Edit");
		
		btnDelete = new JButton("Delete");
		
		
		btnLend = new JButton("Lend");
		
		btnSearch = new JButton("Search");
		
		btnPnik = new JButton("P\u00C1NIK");
		btnPnik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.panic();
				tm.fireTableDataChanged();
			}
		});
		
		btnRentals = new JButton("Rentals");
		
		textField = new JTextField();
		textField.setToolTipText("Search");
		textField.setColumns(10);
		
		btnDummydata = new JButton("dummydata");
		btnDummydata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.fillDatabaseWithDummyData();
				tm.fireTableDataChanged();
			}
		});

		GroupLayout groupLayout = new GroupLayout(frmMovieDatabase.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(textField)
							.addComponent(btnEdit, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addComponent(btnLend, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addComponent(btnPnik, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addComponent(btnRentals, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addComponent(label, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnDummydata))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(22)
							.addComponent(btnEdit)
							.addGap(25)
							.addComponent(btnDelete)
							.addGap(27)
							.addComponent(btnLend)
							.addGap(35)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(btnSearch)
							.addGap(48)
							.addComponent(btnPnik)
							.addGap(35)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
							.addComponent(btnDummydata)
							.addGap(13)
							.addComponent(btnRentals)))
					.addContainerGap())
		);

		table = new JTable();
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
						image = ImageIO.read(new File("img/" + tm.getRow(table.getSelectedRow()).getPicture()));

						label.setIcon(new ImageIcon(
								image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					int n = JOptionPane.showConfirmDialog(null, "Would you like to delete this movie?", "Delete", JOptionPane.YES_NO_OPTION);
					if(n == JOptionPane.YES_OPTION) {
						if(db.deleteMovie(tm.getRow(table.getSelectedRow()).getId()) > 0) {
							JOptionPane.showMessageDialog(null, "Succesfully deleted!");
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
