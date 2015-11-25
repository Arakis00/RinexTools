package rbrown.rinex;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Label;
import java.io.File;
import java.io.IOException;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.ImageIcon;

public class RinGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldMinSignal;
	private File loadedStatFile, loadedDebugFile, loadedDelFile, loadedChangeFile; //holds the last opened file for each separate tab
	private RinexController rinController = new RinexController(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RinGUI frame = new RinGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RinGUI() {
		setResizable(false);
		setTitle("Rinex Editing Tools");
		try {
		    setIconImage(ImageIO.read(new File("res/rinexIcon.png")));
		}
		catch (IOException exc) {
		    exc.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 425);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		//************************************
		//GPS Statistics Panel and Components*
		//************************************
		
		JPanel statPanel = new JPanel();
		tabbedPane.addTab("Rinex Statistics", null, statPanel, null);
		statPanel.setLayout(null);
		
		JLabel lblStatInputNoEdit = new JLabel("<HTML><U><B>Select an Input File (Open Location):</B></U></HTML>");
		lblStatInputNoEdit.setBounds(129, 11, 250, 14);
		statPanel.add(lblStatInputNoEdit);
		
		JLabel lblStatOutputNoEdit = new JLabel("<HTML><U><B>Select an Output File (Save Location):</B></U></HTML>");
		lblStatOutputNoEdit.setBounds(129, 70, 250, 14);
		statPanel.add(lblStatOutputNoEdit);
		
		JTextArea txtAreaStatFeedback = new JTextArea();
		txtAreaStatFeedback.setEditable(false);
		txtAreaStatFeedback.setText("Please select an input file.");
		txtAreaStatFeedback.setBounds(79, 161, 300, 120);
		statPanel.add(txtAreaStatFeedback);
		
		JLabel lblStatOpen = new JLabel("");
		lblStatOpen.setBackground(Color.WHITE);
		lblStatOpen.setBounds(79, 45, 255, 14);
		statPanel.add(lblStatOpen);
		
		JLabel lblStatSave = new JLabel("");
		lblStatSave.setBackground(Color.WHITE);
		lblStatSave.setBounds(79, 104, 255, 14);
		statPanel.add(lblStatSave);
		
		//Statistics - Buttons and ActionListeners
		
		JButton btnStatOpen = new JButton("");
		btnStatOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showDialog(null, "Open");
				//upon file selection, load the new file, store its' path in the corresponding label, and set the default save path
				if(returnVal == JFileChooser.APPROVE_OPTION){
					loadedStatFile = fc.getSelectedFile();
					lblStatOpen.setText(loadedStatFile.getPath());
					lblStatSave.setText(loadedStatFile.getPath().substring(0, loadedStatFile.getPath().length()- 4) + ".rpt");
					txtAreaStatFeedback.setText("File selected and default save path set." 
												+ "\nYou may now do either of the following:" 
												+ "\n\t1. Change the default save path"
												+ "\n\t2. Proceed with processing");
				}
			}
		});
		btnStatOpen.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnStatOpen.setBounds(344, 36, 35, 23);
		statPanel.add(btnStatOpen);
		
		JButton btnStatSave = new JButton("");
		btnStatSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblStatOpen.getText().equals("") || lblStatOpen.getText().equals(null))
					txtAreaStatFeedback.setText("Please select an input file first.");
				else {
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showDialog(null, "Open");
					//upon file selection, set the path in the corresponding save label and print feedback to the user
					if(returnVal == JFileChooser.APPROVE_OPTION){
						lblStatSave.setText(fc.getSelectedFile().getPath());
						txtAreaStatFeedback.setText("New save location selected."
													+ "\nProceed with processing.");
					}
				}
			}
		});
		btnStatSave.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnStatSave.setBounds(344, 95, 35, 23);
		statPanel.add(btnStatSave);
		
		JButton btnStatOpenReport = new JButton("Open Report");
		btnStatOpenReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//attempt to open the current save file location label path in notepad
				if (Desktop.isDesktopSupported()) {
				    try { //TODO - check that exception can be thrown
				    	File file = new File(lblStatSave.getText());
						Desktop.getDesktop().edit(file);
						txtAreaStatFeedback.setText("Opening report... \nSuccess.");
					} catch (Exception e1) {
						txtAreaStatFeedback.setText("Unable to open the report." 
													+ "\nDo one of the following and try again:"
													+ "\n\t1. Reprocess the file" 
													+ "\n\t2. Select a valid save location");
					}
				} else {
				    //desktop isn't supported, prompt to update Java
					txtAreaStatFeedback.setText("A needed feature isn't supported. \nUpdate Java(JRE) and try again.");
				}
			}
		});
		btnStatOpenReport.setBounds(255, 293, 124, 23);
		statPanel.add(btnStatOpenReport);
		
		JButton btnStatProcessFile = new JButton("Process Selected File");
		btnStatProcessFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File(lblStatOpen.getText());
					//call controller to run statistics on the selected file
					rinController.getStatistics(file, lblStatSave.getText());
				}
				catch (Exception e2) {
					txtAreaStatFeedback.setText("Failed one of the following:"
												+ "\n\t1. Opening the selected file" 
												+ "\n\t2. Saving to the selected file"
												+ "\nCheck selections and try again.");
				}
			}
		});
		btnStatProcessFile.setBounds(79, 293, 169, 23);
		statPanel.add(btnStatProcessFile);
		
		//*********************************
		//Debug Rinex Panel and Components*
		//*********************************
		
		JPanel debugPanel = new JPanel();
		tabbedPane.addTab("Debug Rinex", null, debugPanel, null);
		debugPanel.setLayout(null);
		
		JLabel lblDebugOutputNoEdit = new JLabel("<HTML><U><B>Select an Output File (Save Location):</B></U></HTML>");
		lblDebugOutputNoEdit.setBounds(129, 70, 250, 14);
		debugPanel.add(lblDebugOutputNoEdit);
		
		JLabel lblDebugInputNoEdit = new JLabel("<HTML><U><B>Select an Input File (Open Location):</B></U></HTML>");
		lblDebugInputNoEdit.setBounds(129, 11, 250, 14);
		debugPanel.add(lblDebugInputNoEdit);
		
		JTextArea txtAreaDebugFeedback = new JTextArea();
		txtAreaDebugFeedback.setText("Please select an input file.");
		txtAreaDebugFeedback.setEditable(false);
		txtAreaDebugFeedback.setBounds(14, 234, 300, 82);
		debugPanel.add(txtAreaDebugFeedback);
		
		JButton btnDebugBegin = new JButton("Begin");
		btnDebugBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDebugBegin.setBounds(346, 293, 113, 23);
		debugPanel.add(btnDebugBegin);
		
		JCheckBox chckbxDelLLI1 = new JCheckBox("Delete with LLI = 1?");
		chckbxDelLLI1.setBounds(79, 147, 150, 23);
		debugPanel.add(chckbxDelLLI1);
		
		JCheckBox chckbxDelLLI5 = new JCheckBox("Delete with LLI = 5?");
		chckbxDelLLI5.setBounds(229, 147, 150, 23);
		debugPanel.add(chckbxDelLLI5);
		
		JLabel lblLossOfLockNoEdit = new JLabel("<HTML><U><B>Loss of Lock Indicator</B></U></HTML>");
		lblLossOfLockNoEdit.setBounds(164, 126, 150, 14);
		debugPanel.add(lblLossOfLockNoEdit);
		
		JCheckBox chckbxMinSignal = new JCheckBox("<HTML><U><B>Enter minimum allowable signal strength?</B></U></HTML>");
		chckbxMinSignal.setBounds(79, 173, 300, 23);
		debugPanel.add(chckbxMinSignal);
		
		textFieldMinSignal = new JTextField();
		textFieldMinSignal.setEditable(false);
		textFieldMinSignal.setBounds(174, 203, 100, 20);
		debugPanel.add(textFieldMinSignal);
		textFieldMinSignal.setColumns(10);
		
		JLabel lblDebugOpen = new JLabel("");
		lblDebugOpen.setBackground(Color.WHITE);
		lblDebugOpen.setBounds(79, 45, 255, 14);
		debugPanel.add(lblDebugOpen);
		
		JButton btnDebugOpen = new JButton("");
		btnDebugOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDebugOpen.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnDebugOpen.setBounds(344, 36, 35, 23);
		debugPanel.add(btnDebugOpen);
		
		JLabel lblDebugSave = new JLabel("");
		lblDebugSave.setBackground(Color.WHITE);
		lblDebugSave.setBounds(79, 104, 255, 14);
		debugPanel.add(lblDebugSave);
		
		JButton btnDebugSave = new JButton("");
		btnDebugSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDebugSave.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnDebugSave.setBounds(344, 95, 35, 23);
		debugPanel.add(btnDebugSave);
		
		//**************************************
		//Delete Satellite Panel and Components*
		//**************************************
		
		JPanel delPanel = new JPanel();
		tabbedPane.addTab("Delete Satellite", null, delPanel, null);
		delPanel.setLayout(null);
		
		JLabel lblDelInputNoEdit = new JLabel("<HTML><U><B>Select an Input File (Open Location):</B></U></HTML>");
		lblDelInputNoEdit.setBounds(129, 11, 250, 14);
		delPanel.add(lblDelInputNoEdit);
		
		JLabel lblDelOutputNoEdit = new JLabel("<HTML><U><B>Select an Output File (Save Location):</B></U></HTML>");
		lblDelOutputNoEdit.setBounds(129, 70, 250, 14);
		delPanel.add(lblDelOutputNoEdit);
		
		JLabel lblRemoveSatNoEdit = new JLabel("<HTML><U><B>Remove Satellites/Intervals</B></U></HTML>");
		lblRemoveSatNoEdit.setBounds(143, 126, 180, 14);
		delPanel.add(lblRemoveSatNoEdit);
		
		JComboBox comboBoxPickSat = new JComboBox();
		comboBoxPickSat.setBounds(38, 167, 91, 20);
		delPanel.add(comboBoxPickSat);
		
		JComboBox comboBoxPickInterval = new JComboBox();
		comboBoxPickInterval.setBounds(143, 167, 164, 20);
		delPanel.add(comboBoxPickInterval);
		
		JButton btnDeleteRecord = new JButton("Delete Record");
		btnDeleteRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDeleteRecord.setBounds(317, 166, 125, 23);
		delPanel.add(btnDeleteRecord);
		
		JTextArea textAreaDelFeedback = new JTextArea();
		textAreaDelFeedback.setText("Please select an input file.");
		textAreaDelFeedback.setEditable(false);
		textAreaDelFeedback.setBounds(79, 206, 300, 120);
		delPanel.add(textAreaDelFeedback);
		
		JLabel lblDelOpen = new JLabel("");
		lblDelOpen.setBackground(Color.WHITE);
		lblDelOpen.setBounds(79, 45, 255, 14);
		delPanel.add(lblDelOpen);
		
		JButton btnDelOpen = new JButton("");
		btnDelOpen.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnDelOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelOpen.setBounds(344, 36, 35, 23);
		delPanel.add(btnDelOpen);
		
		JLabel lblDelSave = new JLabel("");
		lblDelSave.setBackground(Color.WHITE);
		lblDelSave.setBounds(79, 104, 255, 14);
		delPanel.add(lblDelSave);
		
		JButton btnDelSave = new JButton("");
		btnDelSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelSave.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnDelSave.setBounds(344, 95, 35, 23);
		delPanel.add(btnDelSave);
		
		//**************************************
		//Change Interval Panel and Components*
		//**************************************
		
		JPanel intervalPanel = new JPanel();
		tabbedPane.addTab("Change Interval", null, intervalPanel, null);
		intervalPanel.setLayout(null);
		
		JLabel lblChangeInputNoEdit = new JLabel("<HTML><U><B>Select an Input File (Open Location):</B></U></HTML>");
		lblChangeInputNoEdit.setBounds(129, 11, 250, 14);
		intervalPanel.add(lblChangeInputNoEdit);
		
		JLabel lblChangeOutputNoEdit = new JLabel("<HTML><U><B>Select an Output File (Save Location):</B></U></HTML>");
		lblChangeOutputNoEdit.setBounds(129, 70, 250, 14);
		intervalPanel.add(lblChangeOutputNoEdit);
		
		JTextArea txtAreaChangeFeedback = new JTextArea();
		txtAreaChangeFeedback.setText("Please select an input file.");
		txtAreaChangeFeedback.setEditable(false);
		txtAreaChangeFeedback.setBounds(79, 161, 300, 120);
		intervalPanel.add(txtAreaChangeFeedback);
		
		JButton btnChangeInterval = new JButton("Process File");
		btnChangeInterval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnChangeInterval.setBounds(171, 293, 113, 23);
		intervalPanel.add(btnChangeInterval);
		
		JLabel lblChangeOpen = new JLabel("");
		lblChangeOpen.setBackground(Color.WHITE);
		lblChangeOpen.setBounds(79, 45, 255, 14);
		intervalPanel.add(lblChangeOpen);
		
		JButton btnChangeOpen = new JButton("");
		btnChangeOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnChangeOpen.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnChangeOpen.setBounds(344, 36, 35, 23);
		intervalPanel.add(btnChangeOpen);
		
		JLabel lblChangeSave = new JLabel("");
		lblChangeSave.setBackground(Color.WHITE);
		lblChangeSave.setBounds(79, 104, 255, 14);
		intervalPanel.add(lblChangeSave);
		
		JButton btnChangeSave = new JButton("");
		btnChangeSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnChangeSave.setIcon(new ImageIcon(RinGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnChangeSave.setBounds(344, 95, 35, 23);
		intervalPanel.add(btnChangeSave);
		
	}
}
