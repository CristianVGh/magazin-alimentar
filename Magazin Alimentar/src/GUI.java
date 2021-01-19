import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JTextArea;

public class GUI {

	DatabaseConnection conn;
	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private String filtru;
	private String nume;
	private JTextField cautaField;
	private JTextField minField;
	private JTextField maxField;
	private DefaultTableModel modelProdus;
	private DefaultTableModel modelCos;
	private JTable tableProd;
	private JTextField produsField;
	private JTextField pretField;
	private JTextField gramajField;
	private JTextField pretAField;
	private JTextField producatorField;
	private JTextField telefonField;
	private JTextField taraField;
	private JTextField adresaField;
	private ArrayList<Produs> cosProduse;
	private double pretTotal;
	private JTable cosTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		conn = new DatabaseConnection();
		cosProduse = new ArrayList<Produs>();
		pretTotal = 0;
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 962, 637);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel searchProdPanel = new JPanel();
		searchProdPanel.setBorder(BorderFactory.createTitledBorder("Cautare Produs"));
		frame.getContentPane().add(searchProdPanel, "name_11111511204713");
		searchProdPanel.setLayout(null);

		Object[] columnsProdus = {"Cod","Denumire", "Producator", "Pret", "Gramaj", "Data Expirarii", "Data Fabricatiei", "Tara", "Categorie", "Ingrediente"};
		modelProdus = new DefaultTableModel();
		modelProdus.setColumnIdentifiers(columnsProdus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 193, 899, 304);
		searchProdPanel.add(scrollPane);
		
		tableProd = new JTable();
		tableProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableProd.setModel(modelProdus);
		tableProd.getColumnModel().getColumn(0).setPreferredWidth(10);
		tableProd.getColumnModel().getColumn(3).setPreferredWidth(30);
		tableProd.getColumnModel().getColumn(4).setPreferredWidth(30);
		tableProd.getColumnModel().getColumn(7).setPreferredWidth(50);
		tableProd.getColumnModel().getColumn(9).setPreferredWidth(100);
		tableProd.setFillsViewportHeight(true);
		tableProd.setAutoscrolls(true);
		scrollPane.setViewportView(tableProd);
		
		JLabel lblNewLabel = new JLabel("Cauta: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(32, 43, 88, 22);
		searchProdPanel.add(lblNewLabel);
		
		JLabel lblSorteazaDupa = new JLabel("Sorteaza dupa: ");
		lblSorteazaDupa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSorteazaDupa.setBounds(584, 43, 127, 22);
		searchProdPanel.add(lblSorteazaDupa);
		
		JComboBox sorteazaBox = new JComboBox();
		sorteazaBox.setBounds(723, 44, 131, 22);
		sorteazaBox.addItem("Nume");
		sorteazaBox.addItem("Cel mai mic pret");
		sorteazaBox.addItem("Cel mai mare pret");
		searchProdPanel.add(sorteazaBox);
	
		cautaField = new JTextField();
		cautaField.setBounds(111, 44, 116, 22);
		searchProdPanel.add(cautaField);
		cautaField.setColumns(10);
		
		JLabel lblCautaIn = new JLabel("Cauta in: ");
		lblCautaIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCautaIn.setBounds(32, 106, 79, 22);
		searchProdPanel.add(lblCautaIn);
		
		ArrayList<Categorie> listaCategorii = conn.getCategorii();
		JComboBox categorieBox = new JComboBox();
		categorieBox.addItem("Toate categoriile");
		for(Categorie c: listaCategorii)
			categorieBox.addItem(c.getDenumire());
		categorieBox.setBounds(111, 107, 136, 22);
		searchProdPanel.add(categorieBox);
		
		JLabel lblNewLabel_1 = new JLabel("Pret de la:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(341, 47, 79, 16);
		searchProdPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Pret pana in:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(341, 110, 94, 16);
		searchProdPanel.add(lblNewLabel_2);
		
		minField = new JTextField();
		minField.setBounds(447, 44, 56, 22);
		searchProdPanel.add(minField);
		minField.setColumns(10);
		
		maxField = new JTextField();
		maxField.setBounds(447, 107, 56, 22);
		searchProdPanel.add(maxField);
		maxField.setColumns(10);
		
		JButton cautaButton = new JButton("Cauta");
		cautaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = modelProdus.getRowCount()-1; i >= 0;i--) {
					modelProdus.removeRow(i);
				}
				
				String nume = "";
				double min = 0;
				double max = 0;
				String sorteaza = sorteazaBox.getSelectedItem().toString();
				String categorie = categorieBox.getSelectedItem().toString();
				if(!minField.getText().isEmpty())
					min = Double.parseDouble(minField.getText());
				if(!maxField.getText().isEmpty())
					max = Double.parseDouble(maxField.getText());
				nume = cautaField.getText();
				
				ArrayList<Produs> produse = conn.getProduse(nume, categorie, min, max, sorteaza);
				Object[] rows = new Object[10];
				
				for(Produs p: produse) {
					Producator prod = p.getProducator();
					Categorie c = p.getCategorie();
					
					//"Denumire", "Producator", "Pret", "Gramaj", "Data Expirarii", "Data Fabricatiei", "Tara", "Categorie", "Ingrediente"
					rows[0] = p.getCodp();
					rows[1] = p.getDenumire();
					rows[2] = prod.getNume();
					rows[3] = p.getPret();
					
					if(p.getPret() == 1) 
						rows[3] += " leu";
					else 
						rows[3] += " lei"; 
					
					if(c.getDenumire().equals("Legume") || c.getDenumire().equals("Fructe") || c.getDenumire().equals("Carne"))
						rows[3] += "/kg" ;
					
					rows[4] = p.getGramaj();
					rows[5] = p.getDataExpirarii();
					rows[6] = p.getDataFabricatiei();
					rows[7] = prod.getTara();
					rows[8] = c.getDenumire();
					ArrayList<Ingredient> ingrediente = p.getIngrediente();
					String ing = "";
					
					for(int i = 0; i< ingrediente.size();i++) {
						if(i!=0) ing+= ", ";
						ing += ingrediente.get(i).getDenumire() ;
					}	
					rows[9] = ing;
					modelProdus.addRow(rows);
				}
				
			}
		});
		cautaButton.setBounds(825, 122, 97, 25);
		searchProdPanel.add(cautaButton);
		
		JButton btnNewButton = new JButton("Sterge");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableProd.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frame, "Nu ati ales niciun produs", "Atentie", JOptionPane.WARNING_MESSAGE);
				} else {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Sunteti sigur ca vreti sa stergeti produsul?","Atentie",dialogButton);
					
					if(dialogResult == JOptionPane.YES_OPTION){
						int codp = (int)tableProd.getValueAt(tableProd.getSelectedRow(), 0);
						conn.stergeProdus(codp);
					}
				}
				
				
				for(int i = modelProdus.getRowCount()-1; i >= 0;i--) {
					modelProdus.removeRow(i);
				}
				
				String nume = "";
				double min = 0;
				double max = 0;
				String sorteaza = sorteazaBox.getSelectedItem().toString();
				String categorie = categorieBox.getSelectedItem().toString();
				if(!minField.getText().isEmpty())
					min = Double.parseDouble(minField.getText());
				if(!maxField.getText().isEmpty())
					max = Double.parseDouble(maxField.getText());
				nume = cautaField.getText();
				
				ArrayList<Produs> produse = conn.getProduse(nume, categorie, min, max, sorteaza);
				Object[] rows = new Object[10];
				
				for(Produs p: produse) {
					Producator prod = p.getProducator();
					Categorie c = p.getCategorie();
					
					rows[0] = p.getCodp();
					rows[1] = p.getDenumire();
					rows[2] = prod.getNume();
					rows[3] = p.getPret();
					
					if(p.getPret() == 1) 
						rows[3] += " leu";
					else 
						rows[3] += " lei"; 
					
					if(c.getDenumire().equals("Legume") || c.getDenumire().equals("Fructe") || c.getDenumire().equals("Carne"))
						rows[3] += "/kg" ;
					
					rows[4] = p.getGramaj();
					rows[5] = p.getDataExpirarii();
					rows[6] = p.getDataFabricatiei();
					rows[7] = prod.getTara();
					rows[8] = c.getDenumire();
					ArrayList<Ingredient> ingrediente = p.getIngrediente();
					String ing = "";
					
					for(int i = 0; i< ingrediente.size();i++) {
						if(i!=0) ing+= ", ";
						ing += ingrediente.get(i).getDenumire() ;
					}	
					rows[9] = ing;
					modelProdus.addRow(rows);
				}
			}
		});
		btnNewButton.setBounds(32, 527, 97, 25);
		searchProdPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Modifica pret");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(tableProd.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frame, "Nu ati ales niciun produs", "Atentie", JOptionPane.WARNING_MESSAGE);
				} else {
				String pr = (String)JOptionPane.showInputDialog(frame, "Introduceti noul pret", "Modifica", JOptionPane.QUESTION_MESSAGE);
				
				try {
					double pretNou = Double.parseDouble(pr);
					int codp = (int)tableProd.getValueAt(tableProd.getSelectedRow(), 0);	
					conn.updatePret(codp, pretNou);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(frame, "Nu ati introdus un pret valid", "Atentie", JOptionPane.WARNING_MESSAGE);
				}
			}
				
				for(int i = modelProdus.getRowCount()-1; i >= 0;i--) {
					modelProdus.removeRow(i);
				}
				
				String nume = "";
				double min = 0;
				double max = 0;
				String sorteaza = sorteazaBox.getSelectedItem().toString();
				String categorie = categorieBox.getSelectedItem().toString();
				if(!minField.getText().isEmpty())
					min = Double.parseDouble(minField.getText());
				if(!maxField.getText().isEmpty())
					max = Double.parseDouble(maxField.getText());
				nume = cautaField.getText();
				
				ArrayList<Produs> produse = conn.getProduse(nume, categorie, min, max, sorteaza);
				Object[] rows = new Object[10];
				
				for(Produs p: produse) {
					Producator prod = p.getProducator();
					Categorie c = p.getCategorie();
					
					rows[0] = p.getCodp();
					rows[1] = p.getDenumire();
					rows[2] = prod.getNume();
					rows[3] = p.getPret();
					
					if(p.getPret() == 1) 
						rows[3] += " leu";
					else 
						rows[3] += " lei"; 
					
					if(c.getDenumire().equals("Legume") || c.getDenumire().equals("Fructe") || c.getDenumire().equals("Carne"))
						rows[3] += "/kg" ;
					
					rows[4] = p.getGramaj();
					rows[5] = p.getDataExpirarii();
					rows[6] = p.getDataFabricatiei();
					rows[7] = prod.getTara();
					rows[8] = c.getDenumire();
					ArrayList<Ingredient> ingrediente = p.getIngrediente();
					String ing = "";
					
					for(int i = 0; i< ingrediente.size();i++) {
						if(i!=0) ing+= ", ";
						ing += ingrediente.get(i).getDenumire() ;
					}	
					rows[9] = ing;
					modelProdus.addRow(rows);
				}
			}
		});
		btnNewButton_1.setBounds(250, 527, 116, 25);
		searchProdPanel.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Adauga in cos");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(tableProd.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frame, "Nu ati ales niciun produs", "Atentie", JOptionPane.WARNING_MESSAGE);
				} else {
					int codp = (int)tableProd.getValueAt(tableProd.getSelectedRow(), 0);
					int gasit = 0;
					
					for(Produs p: cosProduse) {
						if(p.getCodp() == codp) {
							int cantitate = p.getProducator().getCodprod();
							p.getProducator().setCodprod(cantitate + 1);
							gasit = 1;
						} 
						
					}
					if(gasit == 0)
						cosProduse.add(conn.cautaProdus(codp));
				}
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_3.setBounds(595, 527, 116, 25);
		searchProdPanel.add(btnNewButton_3);
		
		JPanel adaugaProdusPanel = new JPanel();
		adaugaProdusPanel.setBorder(BorderFactory.createTitledBorder("Adauga Produs"));
		frame.getContentPane().add(adaugaProdusPanel, "name_11111519391376");
		adaugaProdusPanel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Denumire:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(22, 50, 84, 16);
		adaugaProdusPanel.add(lblNewLabel_3);
		
		JLabel lblCategorie = new JLabel("Categorie: ");
		lblCategorie.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCategorie.setBounds(22, 182, 84, 16);
		adaugaProdusPanel.add(lblCategorie);
		
		JLabel lblPret = new JLabel("Pret: ");
		lblPret.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPret.setBounds(22, 317, 84, 16);
		adaugaProdusPanel.add(lblPret);
		
		JLabel lblDataExpirarii = new JLabel("Data Expirarii:");
		lblDataExpirarii.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDataExpirarii.setBounds(265, 437, 116, 16);
		adaugaProdusPanel.add(lblDataExpirarii);
		
		JLabel lblPretAchizitie = new JLabel("Pret achizitie:");
		lblPretAchizitie.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPretAchizitie.setBounds(22, 437, 98, 16);
		adaugaProdusPanel.add(lblPretAchizitie);
		
		JLabel lblGramaj = new JLabel("Gramaj:");
		lblGramaj.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGramaj.setBounds(279, 50, 84, 16);
		adaugaProdusPanel.add(lblGramaj);
		
		JLabel lblProducator = new JLabel("Producator: ");
		lblProducator.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProducator.setBounds(265, 182, 98, 16);
		adaugaProdusPanel.add(lblProducator);
		
		JLabel lblDataFabricatiei = new JLabel("Data fabricatiei:");
		lblDataFabricatiei.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDataFabricatiei.setBounds(265, 317, 116, 16);
		adaugaProdusPanel.add(lblDataFabricatiei);
		
		produsField = new JTextField();
		produsField.setBounds(132, 48, 98, 22);
		adaugaProdusPanel.add(produsField);
		produsField.setColumns(10);
		
		pretField = new JTextField();
		pretField.setBounds(132, 315, 55, 22);
		adaugaProdusPanel.add(pretField);
		pretField.setColumns(10);
		
		gramajField = new JTextField();
		gramajField.setBounds(397, 48, 55, 22);
		adaugaProdusPanel.add(gramajField);
		gramajField.setColumns(10);
		
		pretAField = new JTextField();
		pretAField.setBounds(132, 435, 55, 22);
		adaugaProdusPanel.add(pretAField);
		pretAField.setColumns(10);
		
		ArrayList<Producator> listaProducatori = conn.getProducatori();
		JComboBox producatorBox = new JComboBox();
		for(Producator p: listaProducatori)
			producatorBox.addItem(p.getNume());
		producatorBox.setBounds(397, 180, 116, 22);
		adaugaProdusPanel.add(producatorBox);
		
		JPanel adaugaProdPanel = new JPanel();
		adaugaProdPanel.setBorder(BorderFactory.createTitledBorder("Adauga producator nou"));
		adaugaProdPanel.setBounds(556, 50, 388, 403);
		adaugaProdusPanel.add(adaugaProdPanel);
		adaugaProdPanel.setLayout(null);
		
		JLabel lblNume = new JLabel("Nume: ");
		lblNume.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNume.setBounds(12, 43, 84, 16);
		adaugaProdPanel.add(lblNume);
		
		JLabel lblTelefon = new JLabel("Telefon: ");
		lblTelefon.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTelefon.setBounds(12, 137, 84, 16);
		adaugaProdPanel.add(lblTelefon);
		
		JLabel lblTara = new JLabel("Tara: ");
		lblTara.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTara.setBounds(12, 248, 84, 16);
		adaugaProdPanel.add(lblTara);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAdresa.setBounds(12, 339, 84, 16);
		adaugaProdPanel.add(lblAdresa);
		
		JButton btnConfirma = new JButton("Confirma");
		btnConfirma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Producator p = new Producator(0, producatorField.getText(), telefonField.getText(), taraField.getText(), adresaField.getText());
				conn.adaugaProducator(p);
				
				producatorBox.removeAllItems();
				ArrayList<Producator> listaProducatori = conn.getProducatori();
				for(Producator pr: listaProducatori)
					producatorBox.addItem(pr.getNume());
			}
		});
		btnConfirma.setBounds(279, 365, 97, 25);
		adaugaProdPanel.add(btnConfirma);
		
		producatorField = new JTextField();
		producatorField.setColumns(10);
		producatorField.setBounds(108, 41, 116, 22);
		adaugaProdPanel.add(producatorField);
		
		telefonField = new JTextField();
		telefonField.setColumns(10);
		telefonField.setBounds(108, 135, 116, 22);
		adaugaProdPanel.add(telefonField);
		
		taraField = new JTextField();
		taraField.setColumns(10);
		taraField.setBounds(108, 246, 116, 22);
		adaugaProdPanel.add(taraField);
		
		adresaField = new JTextField();
		adresaField.setColumns(10);
		adresaField.setBounds(108, 337, 116, 22);
		adaugaProdPanel.add(adresaField);
		
		JComboBox zifBox = new JComboBox();
		for(int i = 1; i <= 31; i++) 
			zifBox.addItem(i);
		zifBox.setBounds(397, 315, 43, 22);
		adaugaProdusPanel.add(zifBox);
		
		JComboBox lunafBox = new JComboBox();
		for(int i = 1; i <= 12; i++) 
			lunafBox.addItem(i);
		lunafBox.setBounds(440, 315, 43, 22);
		adaugaProdusPanel.add(lunafBox);
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		JComboBox anfBox = new JComboBox();
		for(int i = year; i >= year - 2; i--)
			anfBox.addItem(i);
		anfBox.setBounds(483, 315, 55, 22);
		adaugaProdusPanel.add(anfBox);
		
		JComboBox zieBox = new JComboBox();
		for(int i = 1; i <= 31; i++) 
			zieBox.addItem(i);
		zieBox.setBounds(397, 435, 43, 22);
		adaugaProdusPanel.add(zieBox);
		
		JComboBox lunaeBox = new JComboBox();
		for(int i = 1; i <= 12; i++) 
			lunaeBox.addItem(i);
		lunaeBox.setBounds(440, 435, 43, 22);
		adaugaProdusPanel.add(lunaeBox);
		
		JComboBox aneBox = new JComboBox();
		for(int i = year; i < year + 3; i++)
			aneBox.addItem(i);
		aneBox.setBounds(483, 435, 55, 22);
		adaugaProdusPanel.add(aneBox);
		
		JComboBox categoriePBox = new JComboBox();
		categoriePBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cat = categoriePBox.getSelectedItem().toString();
				if(cat.equals("Fructe") || cat.equals("Legume") || cat.equals("Carne")) {
					zifBox.setEnabled(false);
					lunafBox.setEnabled(false);
					anfBox.setEnabled(false);
					zieBox.setEnabled(false);
					lunaeBox.setEnabled(false);
					aneBox.setEnabled(false);
					gramajField.setEnabled(false);
	
				} else {
					zifBox.setEnabled(true);
					lunafBox.setEnabled(true);
					anfBox.setEnabled(true);
					zieBox.setEnabled(true);
					lunaeBox.setEnabled(true);
					aneBox.setEnabled(true);
					gramajField.setEnabled(true);
				}
			}
		});
		for(Categorie c: listaCategorii) {
			categoriePBox.addItem(c.getDenumire());
		}
		categoriePBox.setBounds(132, 180, 98, 22);
		adaugaProdusPanel.add(categoriePBox);
		
		JButton btnConfirma_1 = new JButton("Confirma");
		btnConfirma_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nume = produsField.getText();
				double pret = Double.parseDouble(pretField.getText());
				double pretA = Double.parseDouble(pretAField.getText());
				String categorie = categoriePBox.getSelectedItem().toString();
				String producator = producatorBox.getSelectedItem().toString();
				Produs  p;
				int caz = 0;
				if(categorie.equals("Fructe") || categorie.equals("Legume") || categorie.equals("Carne")) {
					p = new Produs(0, nume, pret, pretA, null, null, null, null, null);	
					caz = 0;
				} else {
					String gramaj = gramajField.getText();
					String strExpirare = aneBox.getSelectedItem().toString() + "-" + lunaeBox.getSelectedItem().toString() + "-" + zieBox.getSelectedItem().toString();
					String strFabricare = anfBox.getSelectedItem().toString() + "-" + lunafBox.getSelectedItem().toString() + "-" + zifBox.getSelectedItem().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date dataExpirare;
					java.sql.Date sqlDataExpirare = null;
					java.util.Date dataFabricare;
					java.sql.Date sqlDataFabricare = null;
//						
					try {
						dataExpirare = sdf.parse(strExpirare);
					    sqlDataExpirare = new java.sql.Date(dataExpirare.getTime()); 
					    dataFabricare = sdf.parse(strFabricare);
					    sqlDataFabricare = new java.sql.Date(dataFabricare.getTime());
						} catch (ParseException e1) {
							e1.printStackTrace();
						}	
					p = new Produs(0, nume, pret, pretA, gramaj, sqlDataFabricare, sqlDataExpirare, null, null);	
					caz = 1;
				}
				
				conn.adaugaProdus(p, categorie, producator, caz);
			}
		});
		btnConfirma_1.setBounds(847, 538, 97, 25);
		adaugaProdusPanel.add(btnConfirma_1);
		
		JPanel cumparaPanel = new JPanel();
		cumparaPanel.setBorder(BorderFactory.createTitledBorder("Cumpara Produse"));
		frame.getContentPane().add(cumparaPanel, "name_33457834364375");
		cumparaPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(77, 73, 229, 329);
		cumparaPanel.add(scrollPane_1);
		
		Object[] columnsCos = {"Produs", "Cantitate", "Pret"};
		modelCos = new DefaultTableModel();
		modelCos.setColumnIdentifiers(columnsCos);
		
		cosTable = new JTable();
		cosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cosTable.setFillsViewportHeight(true);
		cosTable.setModel(modelCos);
		cosTable.getColumnModel().getColumn(1).setPreferredWidth(20);
		cosTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		scrollPane_1.setViewportView(cosTable);
		
		JLabel lblNewLabel_4 = new JLabel("Pret Total:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(384, 461, 84, 16);
		cumparaPanel.add(lblNewLabel_4);
		
		JLabel pretTotalLabel = new JLabel("New label");
		pretTotalLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pretTotalLabel.setBounds(480, 461, 105, 16);
		cumparaPanel.add(pretTotalLabel);
		
		JTextArea bonArea = new JTextArea();
		bonArea.setBounds(653, 69, 229, 329);
		cumparaPanel.add(bonArea);
		
		JLabel lblNewLabel_5 = new JLabel("Bon fiscal:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(653, 37, 73, 16);
		cumparaPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Cos: ");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(77, 38, 56, 16);
		cumparaPanel.add(lblNewLabel_6);
		
		JComboBox angajatBox = new JComboBox();
		angajatBox.setBounds(480, 410, 120, 22);
		ArrayList<Angajat> angajati = conn.getAngajati();
		for(Angajat a: angajati) {
			angajatBox.addItem(a.getNume());
		}
		cumparaPanel.add(angajatBox);
		
		JButton btnNewButton_2 = new JButton("Vezi cos");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = modelCos.getRowCount()-1; i >= 0;i--) {
					modelCos.removeRow(i);
				}
				pretTotal = 0;
				searchProdPanel.setVisible(false);
				cumparaPanel.setVisible(true);
				
				
				Object[] rows = new Object[3];
				for(Produs p: cosProduse) {
					rows[0] = p.getDenumire();
					rows[1] = p.getProducator().getCodprod();
					String c = p.getCategorie().getDenumire();
					if(c.equals("Fructe") || c.equals("Legume") || c.equals("Carne"))
						rows[1] += "kg";
					else rows[1] += "buc";
					rows[2] = p.getPret() * p.getProducator().getCodprod();
					pretTotal += p.getPret() * p.getProducator().getCodprod();
					modelCos.addRow(rows);
				}
				pretTotalLabel.setText(String.valueOf(pretTotal) + "lei");
			}
		});
		btnNewButton_2.setBounds(834, 527, 97, 25);
		searchProdPanel.add(btnNewButton_2);
		
		JButton btnNewButton_4 = new JButton("Elimina");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cosTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frame, "Nu ati ales niciun produs", "Atentie", JOptionPane.WARNING_MESSAGE);
				} else {
					int codp = cosTable.getSelectedRow();
					cosProduse.remove(codp);
					
					for(int i = modelCos.getRowCount()-1; i >= 0;i--) {
						modelCos.removeRow(i);
					}
					pretTotal = 0;
					Object[] rows = new Object[3];
					for(Produs p: cosProduse) {
						rows[0] = p.getDenumire();
						rows[1] = p.getProducator().getCodprod();
						String c = p.getCategorie().getDenumire();
						if(c.equals("Fructe") || c.equals("Legume") || c.equals("Carne"))
							rows[1] += "kg";
						else rows[1] += "buc";
						rows[2] = p.getPret() * p.getProducator().getCodprod();
						pretTotal += p.getPret() * p.getProducator().getCodprod();
						modelCos.addRow(rows);
					}
					pretTotalLabel.setText(String.valueOf(pretTotal) + "lei");
				}
			}
		});
		btnNewButton_4.setBounds(209, 434, 97, 25);
		cumparaPanel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Confirma");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String newline = "\n";
				java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
				
				bonArea.append("            S.C. Magazin Alimentar Parkside" + newline + newline);
				for(Produs p: cosProduse) {
					String c = p.getCategorie().getDenumire();
					String term = "";
					if(c.equals("Fructe") || c.equals("Legume") || c.equals("Carne"))
						term = "kg";
					else term = "buc";
					bonArea.append(p.getDenumire() + " X " + p.getProducator().getCodprod() + term + " Pret: " + p.getPret() * p.getProducator().getCodprod() +
							" RON" + newline);
				}
				bonArea.append("-----------------------------------------------");
				bonArea.append(newline + "Total: " + String.valueOf(pretTotal) + " RON" + newline +
						"Casier: " + angajatBox.getSelectedItem().toString() + newline + 
						"Incheiat la data de " + sqlDate + newline +
						"Va multumim pentru cumparaturi!");
				java.sql.Date sqlDateBon = new java.sql.Date(new java.util.Date().getTime());
				conn.confirmaAchizitia(cosProduse, sqlDateBon, pretTotal, angajatBox.getSelectedItem().toString());
				for(int i=cosProduse.size()-1;i >= 0 ;i--) {
					cosProduse.remove(i);
				}
			}
		});
		btnNewButton_5.setBounds(429, 501, 97, 25);
		cumparaPanel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Inapoi");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProdPanel.setVisible(true);
				cumparaPanel.setVisible(false);
			}
		});
		btnNewButton_6.setBounds(77, 501, 97, 25);
		cumparaPanel.add(btnNewButton_6);
		
		JLabel lblVanzator = new JLabel("Vanzator: ");
		lblVanzator.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVanzator.setBounds(384, 412, 84, 16);
		cumparaPanel.add(lblVanzator);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu cautaMenu = new JMenu("Cauta");
		menuBar.add(cautaMenu);
		
		JMenuItem cautaProdus = new JMenuItem("Produs");
		cautaProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProdPanel.setVisible(true);
				adaugaProdusPanel.setVisible(false);
				cumparaPanel.setVisible(false);
			}
		});
		cautaMenu.add(cautaProdus);
		
		JMenu adaugaMenu = new JMenu("Adauga");
		menuBar.add(adaugaMenu);
		
		JMenuItem adaugaProdus = new JMenuItem("Produs");
		adaugaProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchProdPanel.setVisible(false);
				adaugaProdusPanel.setVisible(true);
				cumparaPanel.setVisible(false);
			}
		});
		adaugaMenu.add(adaugaProdus);
	}
}
