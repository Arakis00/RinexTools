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

public class RinGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldMinSignal;

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
		
		JPanel statPanel = new JPanel();
		tabbedPane.addTab("Rinex Statistics", null, statPanel, null);
		statPanel.setLayout(null);
		
		JLabel lblStatInput = new JLabel("Select an Input File (Open Location):");
		lblStatInput.setBounds(129, 11, 250, 14);
		statPanel.add(lblStatInput);
		
		JComboBox comboBoxStatOpen = new JComboBox();
		comboBoxStatOpen.setBounds(79, 39, 300, 20);
		statPanel.add(comboBoxStatOpen);
		
		JLabel lblStatOutput = new JLabel("Select an Output File (Save Location):");
		lblStatOutput.setBounds(129, 70, 250, 14);
		statPanel.add(lblStatOutput);
		
		JComboBox comboBoxStatSave = new JComboBox();
		comboBoxStatSave.setBounds(79, 95, 300, 20);
		statPanel.add(comboBoxStatSave);
		
		JTextArea txtAreaStatFeedback = new JTextArea();
		txtAreaStatFeedback.setEditable(false);
		txtAreaStatFeedback.setText("Please select an input file.");
		txtAreaStatFeedback.setBounds(79, 161, 300, 120);
		statPanel.add(txtAreaStatFeedback);
		
		JButton btnStatOpenReport = new JButton("Open Report");
		btnStatOpenReport.setBounds(171, 293, 113, 23);
		statPanel.add(btnStatOpenReport);
		
		JPanel debugPanel = new JPanel();
		tabbedPane.addTab("Debug Rinex", null, debugPanel, null);
		debugPanel.setLayout(null);
		
		JComboBox comboBoxDebugSave = new JComboBox();
		comboBoxDebugSave.setBounds(79, 95, 300, 20);
		debugPanel.add(comboBoxDebugSave);
		
		JLabel lblDebugOutput = new JLabel("Select an Output File (Save Location):");
		lblDebugOutput.setBounds(129, 70, 250, 14);
		debugPanel.add(lblDebugOutput);
		
		JComboBox comboBoxDebugOpen = new JComboBox();
		comboBoxDebugOpen.setBounds(79, 39, 300, 20);
		debugPanel.add(comboBoxDebugOpen);
		
		JLabel lblDebugInput = new JLabel("Select an Input File (Open Location):");
		lblDebugInput.setBounds(129, 11, 250, 14);
		debugPanel.add(lblDebugInput);
		
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
		
		JLabel lblLossOfLock = new JLabel("<HTML><U><B>Loss of Lock Indicator</B></U></HTML>");
		lblLossOfLock.setBounds(164, 126, 150, 14);
		debugPanel.add(lblLossOfLock);
		
		JCheckBox chckbxMinSignal = new JCheckBox("<HTML><U><B>Enter minimum allowable signal strength?</B></U></HTML>");
		chckbxMinSignal.setBounds(79, 173, 300, 23);
		debugPanel.add(chckbxMinSignal);
		
		textFieldMinSignal = new JTextField();
		textFieldMinSignal.setEditable(false);
		textFieldMinSignal.setBounds(174, 203, 100, 20);
		debugPanel.add(textFieldMinSignal);
		textFieldMinSignal.setColumns(10);
		
		JPanel delPanel = new JPanel();
		tabbedPane.addTab("Delete Satellite", null, delPanel, null);
		delPanel.setLayout(null);
		
		JLabel lblDelInput = new JLabel("Select an Input File (Open Location):");
		lblDelInput.setBounds(129, 11, 250, 14);
		delPanel.add(lblDelInput);
		
		JComboBox comboBoxDelOpen = new JComboBox();
		comboBoxDelOpen.setBounds(79, 39, 300, 20);
		delPanel.add(comboBoxDelOpen);
		
		JLabel lblDelOutput = new JLabel("Select an Output File (Save Location):");
		lblDelOutput.setBounds(129, 70, 250, 14);
		delPanel.add(lblDelOutput);
		
		JComboBox comboBoxDelSave = new JComboBox();
		comboBoxDelSave.setBounds(79, 95, 300, 20);
		delPanel.add(comboBoxDelSave);
		
		JLabel lblRemoveSat = new JLabel("<HTML><U><B>Remove Satellites/Intervals</B></U></HTML>");
		lblRemoveSat.setBounds(143, 126, 180, 14);
		delPanel.add(lblRemoveSat);
		
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
		
		JPanel intervalPanel = new JPanel();
		tabbedPane.addTab("Change Interval", null, intervalPanel, null);
		intervalPanel.setLayout(null);
		
		JLabel lblChangeInput = new JLabel("Select an Input File (Open Location):");
		lblChangeInput.setBounds(129, 11, 250, 14);
		intervalPanel.add(lblChangeInput);
		
		JComboBox comboBoxChangeOpen = new JComboBox();
		comboBoxChangeOpen.setBounds(79, 39, 300, 20);
		intervalPanel.add(comboBoxChangeOpen);
		
		JLabel lblChangeOutput = new JLabel("Select an Output File (Save Location):");
		lblChangeOutput.setBounds(129, 70, 250, 14);
		intervalPanel.add(lblChangeOutput);
		
		JComboBox comboBoxChangeSave = new JComboBox();
		comboBoxChangeSave.setBounds(79, 95, 300, 20);
		intervalPanel.add(comboBoxChangeSave);
		
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
	}
}
