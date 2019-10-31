import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class kentrikoframe extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JLabel label;
	private JButton btn1;
	private JButton btn2;
	private JButton btn4;
	private JButton btn5;
	
	private tableSQL sql = new tableSQL(null, null, null, null, null, 0, null, null);
	
	public kentrikoframe() {
		Font font = new Font("Verdana", Font.PLAIN, 16);
		Font font2 = new Font("Verdana", Font.PLAIN, 16);
		panel = new JPanel();
		label = new JLabel("ΕΦΑΡΜΟΓΗ ΥΠΟΛΟΓΙΣΜΟΣ ΚΑΡΔΙΑΚΟΥ ΠΑΛΜΟΥ", SwingConstants.CENTER); 
		label.setFont(font);
		
		btn1 = new JButton("ΥΠΟΛΟΓΙΣΜΟΣ ΚΑΙ ΑΠΟΘΗΚΕΥΣΗ ΣΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ");
		btn1.setFont(font2);
		btn2 = new JButton("ΛΙΣΤΑ ΑΠΟ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ");
		btn2.setFont(font2);
		btn4 = new JButton("ΠΛΗΡΟΦΟΡΙΕΣ ΓΙΑ ΤΗΝ ΕΦΑΡΜΟΓΗ");
		btn4.setFont(font2);
		btn5 = new JButton("ΕΞΟΔΟΣ");
		btn5.setFont(font2);
		
		panel.add(label);
		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn4);
		panel.add(btn5);
		
		ButtonListener listener = new ButtonListener();
		btn1.addActionListener(listener);
		btn2.addActionListener(listener);
		btn4.addActionListener(listener);
		btn5.addActionListener(listener);
		
		this.setContentPane(panel);
		this.setVisible(true);
		this.setSize(550, 500);
		this.setResizable(false);
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ΕΦΑΡΜΟΓΗ ΥΠΟΛΟΓΙΣΜΟΣ ΚΑΡΔΙΑΚΟΥ ΠΑΛΜΟΥ");		
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(btn1)) {
				new myFrame();
			}else if(e.getSource().equals(btn2)) {
				sql.printData();
			}else if (e.getSource().equals(btn5)) {
				setVisible(false); //αορατο
				dispose(); //Καταστροφη του JFrame
			}else if (e.getSource().equals(btn4)) {
				JOptionPane.showMessageDialog(panel, "Προγραμματιστές : Δέσποινα Αλεξιάδου και Ψαλτάκης Νικόλαος"
						+ "(c)2019",
						"ΠΛΗΡΟΦΟΡΙΕΣ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}