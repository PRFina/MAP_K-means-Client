import java.awt.FlowLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class KMeans {
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public void init() {
		/*Inizializza la componente grafica della interfaccia grafica istanziando un oggetto della
		 *classe JtabbedPane e aggiungendolo al container della JFrame. Inoltre avvia la richiesta
		 *di connessione al Server ed inizializza i flussi di comunicazione (membri dato in e out). 
		 *Ip del server e porta sono acquisiti come parametri (lo studente decida come).
		 */
		JFrame mainWindow = new JFrame("KMeans");
		
		TabbedPane tabbedPane = new TabbedPane();
		mainWindow.getContentPane().add(tabbedPane);
		
		mainWindow.setSize(800, 600);
		mainWindow.setVisible(true);
		

	}
	
	class TabbedPane extends JTabbedPane {
		
		private JPanelCluster panelDB;
		private JPanelCluster panelFile;
		
		TabbedPane(){
			
			panelDB = new JPanelCluster("Mine", evt-> {System.out.println("button mine pressed");});
			addTab("DB", null, panelDB, null);
			panelDB.setLayout(new BoxLayout(panelDB, BoxLayout.PAGE_AXIS));
			
			panelFile = new JPanelCluster("Store from File", evt-> {System.out.println("button file pressed");});
			addTab("File", null, panelFile, null);
			panelFile.setLayout(new BoxLayout(panelFile, BoxLayout.PAGE_AXIS));
		}
/*	
		private void learningFromDBAction() throws SocketException, IOException, ClassNotFoundException {
			int k;
			try{
				k=new Integer(PanelDB.kText.getText()).intValue();
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}

		}
		
		private void learningFromFileAction() throws SocketException, IOException, ClassNotFoundException{

		}
*/		
		public class JPanelCluster extends JPanel {
			
			private JTextField tableText = new JTextField(20);
			private JTextField kText = new JTextField(10);
			private JTextArea clusterOutput = new JTextArea();
			private JButton executeButton;
			
			JPanelCluster(String buttonName, java.awt.event.ActionListener a){
				JPanel upPanel = new JPanel();
				add(upPanel);
				upPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				JPanel tablePanel = new JPanel();
				upPanel.add(tablePanel);
				
				JLabel tableName = new JLabel("Table Name");
				tablePanel.add(tableName);
				
				tableText = new JTextField();
				tablePanel.add(tableText);
				tableText.setColumns(10);
				
				JPanel kPanel = new JPanel();
				upPanel.add(kPanel);
				
				JLabel kCluster = new JLabel("k Cluster");
				kPanel.add(kCluster);
				
				kText = new JTextField();
				kPanel.add(kText);
				kText.setColumns(10);
				
				JPanel centerPanel = new JPanel();
				add(centerPanel);
				centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
				
				centerPanel.add(clusterOutput);
				
				JPanel downPanel = new JPanel();
				add(downPanel);
				
				JButton executeButton = new JButton(buttonName);
				downPanel.add(executeButton);
				executeButton.addActionListener(a);
			}
		}
		
	}

	public static void main(String[] args) {
		KMeans test = new KMeans();
		test.init();
	}
	
	
	
	
	/*
	public static void main(String[] args) {
    JFrame f=new JFrame("Button Example");
    //JButton b=new JButton("Click Here");  
    //b.setBounds(50,100,95,30);  
    //f.add(b);
    JToggleButton jtb1 = new JToggleButton ("Butt One");
    JToggleButton jtb2 = new JToggleButton ("Butt Two");
    JToggleButton jtb3 = new JToggleButton ("Butt Three");
    jtb1.setBounds(50,100,95,30);
    jtb2.setBounds(150,100,95,30);
    jtb3.setBounds(250,100,95,30);
    f.add (jtb1);
    f.add (jtb2);
    f.add (jtb3);
    
    f.setSize(250,300);  
    f.setLayout(null);  
    f.setVisible(true);   
	}  
	*/
}  
