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
	private int id;
	private String onoma;
	private String eponimo;
	private String imera;
	private String minas;
	private String etos;
	private int age;
	private String min;
	private String max;
	
	static Connection myConn = null;	
	
	public tableSQL(int id, String onoma, String eponimo, String imera, String minas, String etos, int age, String min, String max) {
		this.id = id;
		this.onoma = onoma;
		this.eponimo = eponimo;
		this.imera = imera;
		this.minas = minas;
		this.etos = etos;
		this.age = age;
		this.min = min;
		this.max = max;
	}

	public int getId() {
		return id;
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
		String url =  "jdbc:mysql://localhost:3306/calc?serverTimezone=UTC&characterEncoding=UTF-8";
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
		String url =  "jdbc:mysql://localhost:3306/calc?serverTimezone=UTC&characterEncoding=UTF-8";
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
						rs.getInt("id"),
						rs.getString("onoma"),		
						rs.getString("eponimo"),
						rs.getString("imera"),
						rs.getString("minas"),
						rs.getString("etos"),
						rs.getInt("ilikia"),
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
		Object[] columnsName = new Object[9];
		columnsName[0] = "ID";
		columnsName[1] = "Όνομα";
		columnsName[2] = "Επώνυμο";
		columnsName[3] = "Ημέρα Γένησσης";
		columnsName[4] = "Μήνας Γέννησης";
		columnsName[5] = "Χρονολογία Γέννησης";
		columnsName[6] = "Ηλικία";
		columnsName[7] = "Ελάχιστο";
		columnsName[8] = "Μέγιστο";
		
		model.setColumnIdentifiers(columnsName);
		Object[] rowData = new Object[9];
		for(int i = 0; i<getEggrafes().size(); i++) {
			rowData[0] = getEggrafes().get(i).getId();
			rowData[1] = getEggrafes().get(i).getOnoma();
			rowData[2] = getEggrafes().get(i).getEponimo();
			rowData[3] = getEggrafes().get(i).getImera();
			rowData[4] = getEggrafes().get(i).getMinas();
			rowData[5] = getEggrafes().get(i).getEtos();
			rowData[6] = getEggrafes().get(i).getAge();
			rowData[7] = getEggrafes().get(i).getMin();
			rowData[8] = getEggrafes().get(i).getMax();
			
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
