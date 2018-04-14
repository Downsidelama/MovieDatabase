package view.listing;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.awt.event.ActionEvent;

public class AddEdit {

	private JFrame frmAddedit;
	private boolean add = true;
	private JTextField title;
	private JTextField director;
	private JTextField mainActors;
	private JTextField imagePathTextField;

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddEdit window = new AddEdit(true);
					window.frmAddedit.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddEdit(boolean add) {
		this.add = add;
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
		frmAddedit = new JFrame();
		if(add) {
			frmAddedit.setTitle("Add movie");
		} else {
			frmAddedit.setTitle("Edit a movie");
		}
		frmAddedit.setBounds(100, 100, 450, 700);
		frmAddedit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAddedit.setMinimumSize(new Dimension(450, 700));
		
		title = new JTextField();
		title.setColumns(10);
		
		director = new JTextField();
		director.setColumns(10);
		
		JLabel lblDirector = new JLabel("Director");
		
		JLabel lblNewLabel = new JLabel("Main Actors");
		
		mainActors = new JTextField();
		mainActors.setColumns(10);
		
		JLabel lblReleaseDate = new JLabel("Release Date");
		
		JSpinner releaseDate = new JSpinner();
		releaseDate.setModel(new SpinnerNumberModel(2018, 1800, 2018, 1));
		
		JLabel lblDuration = new JLabel("Duration");
		
		JSpinner durationSpinner = new JSpinner();
		durationSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		JLabel lblMedia = new JLabel("Media");
		
		JComboBox mediaComboBox = new JComboBox();
		mediaComboBox.addItem("DVD");
		mediaComboBox.addItem("VHS");
		mediaComboBox.addItem("Blu-Ray");
		mediaComboBox.addItem("CD");
		
		JLabel lblCoverImageLocation = new JLabel("Cover image location");
		
		imagePathTextField = new JTextField();
		imagePathTextField.setEnabled(false);
		imagePathTextField.setColumns(10);
		File imageFile = null;
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
				            return true;
				        }
						
				        return true;
					}

					@Override
					public String getDescription() {
						return "Images";
					}
				});
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(frmAddedit);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					imagePathTextField.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		JComboBox genuineComboBox = new JComboBox();
		genuineComboBox.addItem("Yes");
		genuineComboBox.addItem("No");
		
		JLabel lblGenuine = new JLabel("Genuine");
		
		JButton btnSave = new JButton("Save");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAddedit.dispose();
			}
		});
		
		JLabel lblTitle = new JLabel("Title");
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(imagePathTextField.getText());
				try {
					copyFileUsingStream(file, new File("img/" + file.getName()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmAddedit.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(releaseDate, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(title, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(director, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblDirector)
						.addComponent(lblNewLabel)
						.addComponent(mainActors, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblReleaseDate)
						.addComponent(lblDuration)
						.addComponent(durationSpinner, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblMedia)
						.addComponent(mediaComboBox, 0, 414, Short.MAX_VALUE)
						.addComponent(lblCoverImageLocation)
						.addComponent(genuineComboBox, Alignment.TRAILING, 0, 414, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(imagePathTextField, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
									.addGap(18))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblGenuine, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
									.addGap(207)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnCopy)
								.addComponent(browseButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(lblTitle)
					.addGap(18)
					.addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblDirector)
					.addGap(18)
					.addComponent(director, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(mainActors, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblReleaseDate)
					.addGap(18)
					.addComponent(releaseDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblDuration)
					.addGap(18)
					.addComponent(durationSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblMedia)
					.addGap(18)
					.addComponent(mediaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblCoverImageLocation)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(imagePathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(browseButton))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGenuine)
						.addComponent(btnCopy))
					.addGap(18)
					.addComponent(genuineComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnCancel))
					.addContainerGap())
		);
		frmAddedit.getContentPane().setLayout(groupLayout);
	}
	
	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}
