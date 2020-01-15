import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class myFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JLabel label;
	private JLabel labelname;
	private JLabel labelepon;
	private JLabel labelday;
	private JLabel labelmonth;
	private JLabel labelyear;
	private JTextField name;
	private JTextField epon;
	private JTextField day;
	private JTextField month;
	private JTextField year;
	private JButton btcalc;
	private JButton btclear;
	private JButton btexit;
	
	private int age;
	private int torinoEtos = 2019;
	private double MaxHeartRate;
	private double maxHeartRate085;
	private double maxHeartRate05;
	
	public myFrame() {
	    Font font = new Font("Verdana", Font.PLAIN, 39);
	    Font font2 = new Font("Verdana", Font.PLAIN, 26);
		panel = new JPanel();
		label = new JLabel("ΚΟΜΠΙΟΥΤΕΡΑΚΙ ΚΑΡΔΙΑΣ", SwingConstants.CENTER); 
		label.setFont(font);
		labelname = new JLabel("Όνομα");
		labelname.setFont(font2);
		labelepon = new JLabel("Επώνυμο");
		labelepon.setFont(font2);
		labelday = new JLabel("Ημέρα");
		labelday.setFont(font2);
		labelmonth = new JLabel("Μήνας");
		labelmonth.setFont(font2);
		labelyear = new JLabel("Έτος");
		labelyear.setFont(font2);
		name = new JTextField(20);
		name.setFont(font2);
		epon = new JTextField(20); 
		epon.setFont(font2);
		day = new JTextField(20); 
		day.setFont(font2);
		month = new JTextField(20); 
		month.setFont(font2);
		year = new JTextField(20); 
		year.setFont(font2);
		btcalc = new JButton("ΥΠΟΛΟΓΙΣΜΟΣ");
		btclear = new JButton("ΚΑΘΑΡΙΣΜΟΣ");
		btexit = new JButton("ΕΞΟΔΟΣ");
		
		panel.add(label);
		panel.add(labelname);
		panel.add(name);
		panel.add(labelepon);
		panel.add(epon);
		panel.add(labelday);
		panel.add(day);
		panel.add(labelmonth);
		panel.add(month);
		panel.add(labelyear);
		panel.add(year);
		panel.add(btcalc);
		panel.add(btclear);
		panel.add(btexit);
		
		ButtonListener listener = new ButtonListener();
		btcalc.addActionListener(listener);
		btclear.addActionListener(listener);
		btexit.addActionListener(listener);
		
		this.setContentPane(panel);
		this.setVisible(true);
		this.setSize(550, 550);
		this.setResizable(false);
		this.setTitle("ΚΟΜΠΙΟΥΤΕΡΑΚΙ ΚΑΡΔΙΑ");		
	}
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(btexit)) {
				setVisible(false); //αορατο
				dispose(); //Καταστροφη του JFrame
			}else if (e.getSource().equals(btcalc)) {
				if ((name.getText().equals("")) && (epon.getText().equals("")) && (day.getText().equals("")) && (month.getText().equals("")) && (year.getText().equals(""))) {
					JOptionPane.showMessageDialog(panel, "Δεν μπορείτε να αφησετε κενά πεδία" ,
							"ΠΡΟΣΟΧΗ", JOptionPane.INFORMATION_MESSAGE);	
				}
				String onoma = name.getText();
				String Epon = epon.getText();
				String imera = day.getText();
				String minas = month.getText();
				String etos = year.getText();
				//Έλεγχος αν ο χρήστης πληκτρολόγησε 2 αριθμους για ημερα και μηνα και 4 για ετος
				if ((imera.length() !=2) || (minas.length() !=2) || (etos.length() !=4)) {
					JOptionPane.showMessageDialog(panel, "Τα πεδία Ημέρα και Μήνας πρέπει να έχουν 2 Αριθμους και το Πεδίο Ετος 4" ,
							"ΣΦΑΛΜΑ", JOptionPane.INFORMATION_MESSAGE);
				}else {
					age = torinoEtos - Integer.parseInt(year.getText());
					MaxHeartRate = 220 - age;
					maxHeartRate05 = MaxHeartRate * 0.5d;
					maxHeartRate085 = MaxHeartRate * 0.85d;
					JOptionPane.showMessageDialog(panel, "Ο " + name.getText() + " " + epon.getText() + " γεννήθηκε το " + year.getText() +" και είναι " + age + ". Το εύρος καρδιακού ρυθμού είναι από " + maxHeartRate05 + "% εως " + maxHeartRate085 + "%" ,
							"ΠΛΗΡΟΦΟΡΙΕΣ", JOptionPane.INFORMATION_MESSAGE);
					
					String min = Double.toString(maxHeartRate05);
					String max = Double.toString(maxHeartRate085);
					
					//Εισαγωγή στην βάση Δεδομένων	
					//URL ΣΥΝΔΕΣΗΣ
					String url = "jdbc:mysql://localhost:3306/calc?serverTimezone=UTC&characterEncoding=UTF-8";
					//ΟΝΟΜΑ ΧΡΗΣΤΗ ΚΑΙ ΚΩΔΙΚΟΣ ΠΡΟΣΑΒΑΣΗΣ
					String user = "root";
					String pass = "";
					//ΕΡΩΤΗΜΑ
					String query = "INSERT IGNORE INTO calc (ID, ONOMA, EPONIMO, IMERA, MINAS, ETOS, ILIKIA, MIN, MAX) VALUES (default,?, ?, ?, ?, ?, ?, ?, ?)";
					
					try {
						// 1. Σύνδεση με την βάση δεδομένων
						Connection con=DriverManager.getConnection(url,user,pass);  
						
						PreparedStatement stmt=con.prepareStatement(query);  
						// 2. Στείχεια που θα αποθηκευτουν
						stmt.setString(1, onoma);
						stmt.setString(2, Epon);
						stmt.setString(3, imera);
						stmt.setString(4, minas);
						stmt.setString(5, etos);
						stmt.setInt(6, age);
						stmt.setString(7, min);
						stmt.setString(8, max);
						
						int i = stmt.executeUpdate();
						//Ελεγχος αν περάστηκε ή οχι η εγγραφή στην Βάση Δεδομένων
						if (i == 0) {
							JOptionPane.showMessageDialog(panel, "Υπαρχεί ήδη εγγραφή με αυτό το Επώνυμο.",
									"ΠΛΗΡΟΦΟΡΙΕΣ", JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(panel, "Επιτυχής Αποθήκευση στην Βάση Δεδομένων!!!",
									"ΠΛΗΡΟΦΟΡΙΕΣ", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
									
					//Τελος Εισαγωγής στην βάση δεσομένων
				}
			}else {
				name.setText("");
				epon.setText("");
				day.setText("");
				month.setText("");
				year.setText("");
			}
		}
	}	
}