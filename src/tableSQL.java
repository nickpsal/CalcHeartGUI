import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class tableSQL {
	private String onoma;
	private String eponimo;
	private String imera;
	private String minas;
	private String etos;
	private int age;
	private String min;
	private String max;
	
	static Connection myConn = null;	
	
	public tableSQL(String onoma, String eponimo, String imera, String minas, String etos, int age, String min, String max) {
		this.onoma = onoma;
		this.eponimo = eponimo;
		this.imera = imera;
		this.minas = minas;
		this.etos = etos;
		this.age = age;
		this.min = min;
		this.max = max;
	}	

	public String getOnoma() {
		return onoma;
	}

	public String getEponimo() {
		return eponimo;
	}

	public String getImera() {
		return imera;
	}

	public String getMinas() {
		return minas;
	}

	public String getEtos() {
		return etos;
	}
	
	public int getAge() {
		return age;
	}

	public String getMin() {
		return min;
	}

	public String getMax() {
		return max;
	}

	static public class work extends JFrame{
		private static final long serialVersionUID = 1L; 
		
		public work() {
			super("ΛΙΣΤΑ ΑΠΟ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ");
			setLocationRelativeTo(null);
			setSize(900,700);
			this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
			setVisible(true);		
		}	
	}
	
	//Δημιουργία function για την Σύνδεση με την βάση Δεδομένων
	
	static Connection getConnection() {
		String url =  "jdbc:mysql://localhost:3306/calculator?serverTimezone=UTC&characterEncoding=UTF-8";
		String user = "root";
		String pass = "";
		
		try {
			myConn = DriverManager.getConnection(url,user,pass);
		}catch (SQLException ex) {
			Logger.getLogger(work.class.getName()).log(Level.SEVERE, null,ex);
		}
				
		return myConn;
	}
	
	static ArrayList<tableSQL> getEggrafes(){
		ArrayList<tableSQL> eggrafes = new ArrayList<tableSQL>();
		String url =  "jdbc:mysql://localhost:3306/calculator?serverTimezone=UTC&characterEncoding=UTF-8";
		String user = "root";
		String pass = "";
		Statement st;
		ResultSet rs;
		tableSQL u;
		
		try {
			Connection myConn = DriverManager.getConnection(url, user, pass);
			st = myConn.createStatement();
			rs = st.executeQuery("SELECT * FROM calc");
			while(rs.next()) {
				u = new tableSQL(
						rs.getString("onoma"),		
						rs.getString("eponimo"),
						rs.getString("imera"),
						rs.getString("minas"),
						rs.getString("etos"),
						rs.getInt("age"),
						rs.getString("min"),
						rs.getString("max")
						);
				eggrafes.add(u);
										
			}
		}catch (SQLException ex) {
			Logger.getLogger(work.class.getName()).log(Level.SEVERE, null,ex);
		}
		return eggrafes;
	}
	
	public void printData() {
		Font font = new Font("Verdana", Font.PLAIN, 16);
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel();		
		Object[] columnsName = new Object[8];
		columnsName[0] = "Όνομα";
		columnsName[1] = "Επώνυμο";
		columnsName[2] = "Ημέρα Γένησσης";
		columnsName[3] = "Μήνας Γέννησης";
		columnsName[4] = "Χρονολογία Γέννησης";
		columnsName[5] = "Ηλικία";
		columnsName[6] = "Ελάχιστο";
		columnsName[7] = "Μέγιστο";
		
		model.setColumnIdentifiers(columnsName);
		Object[] rowData = new Object[8];
		for(int i = 0; i<getEggrafes().size(); i++) {
			rowData[0] = getEggrafes().get(i).getOnoma();
			rowData[1] = getEggrafes().get(i).getEponimo();
			rowData[2] = getEggrafes().get(i).getImera();
			rowData[3] = getEggrafes().get(i).getMinas();
			rowData[4] = getEggrafes().get(i).getEtos();
			rowData[5] = getEggrafes().get(i).getAge();
			rowData[6] = getEggrafes().get(i).getMin();
			rowData[7] = getEggrafes().get(i).getMax();
			
			model.addRow(rowData);
		}
		table.setFont(font);
		table.setModel(model);
		table.setEnabled(false);
		work w = new work();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(table);
		panel.add(pane, BorderLayout.CENTER);
		w.setContentPane(panel);
	}
}
