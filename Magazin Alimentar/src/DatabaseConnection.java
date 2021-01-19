import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.plaf.synth.SynthSeparatorUI;

import java.sql.Date;

public class DatabaseConnection {
	
	public void adaugaProducator(Producator p) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");
			String query = "INSERT INTO producator VALUES ((SELECT MAX(codpr) from producator AS prod) + 1, '" + p.getNume() + "','" + p.getTelefon() +
					"','" + p.getTara() + "','" + p.getAdresa() + "')";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	public void adaugaProdus(Produs p, String categorie, String producator, int caz) {
		try {
			
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");
			String query = "";
			if(caz == 0) 
				query = "INSERT INTO produs VALUES ((SELECT MAX(codp) from produs AS prod) + 1, '" + p.getDenumire() + "','" + p.getPret() +
					"','" + p.getPretAchizitie() + "', NULL, NULL, NULL, (SELECT codpr FROM producator WHERE nume = '" +
					producator + "'), (SELECT codc FROM categorie WHERE denumire = '" + categorie + "'))";
			else
				query = "INSERT INTO produs VALUES ((SELECT MAX(codp) from produs AS prod) + 1, '" + p.getDenumire() + "','" + p.getPret() +
					"','" + p.getPretAchizitie() + "','" + p.getGramaj() + "','" + p.getDataExpirarii() + "','" + p.getDataFabricatiei() + 
					"', (SELECT codpr FROM producator WHERE nume = '" + producator + "'), (SELECT codc FROM categorie WHERE denumire = '" + categorie + "'))";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	public void stergeProdus(int codp) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");
			String query = "DELETE FROM produs_pe_bon WHERE codp = '" + codp + "'";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			query = "DELETE FROM ingredient_pe_produs WHERE codp = '" + codp + "'";
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			query = "DELETE FROM produs WHERE codp = '" + codp + "'";
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	public void updatePret(int codp, double pret) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");
			String query = "UPDATE produs SET pret = '" + pret + "' WHERE codp = '" + codp + "'";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		
		} catch (Exception e) {
			System.out.println(e);
		} 
	}

	public int getCodProducator(String nume) {
		try {
			int codp = 0;
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");

			String query = "SELECT codp FROM producator WHERE nume = '" + nume + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				codp = rs.getInt(1);
				}
			
			rs.close();
			conn.close();
			return codp;
			
			} catch (Exception e) {
			System.out.println(e);	
			} return 0;
	
	}
	
	public void confirmaAchizitia(ArrayList<Produs> produse, Date data, double pretTotal, String angajat) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");
			String query = "INSERT INTO bon_fiscal VALUES ((SELECT MAX(codb) FROM bon_fiscal as b) + 1, '" + pretTotal + "','" + data + 
					"', (SELECT coda FROM angajat WHERE nume = '" + angajat + "'))";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
		
			for(Produs p: produse) {
				query = "INSERT INTO produs_pe_bon VALUES ('" + p.getCodp() + "',(SELECT MAX(codb) FROM bon_fiscal), '" + p.getProducator().getCodprod() +"')";
				pstmt = conn.prepareStatement(query);
				pstmt.executeUpdate();
			}
			conn.close();
			
			
			} catch (Exception e) {
			System.out.println(e);	
			} 
	}
	
	public ArrayList<Produs> getProduse(String nume, String categorie, double min, double max, String ordine){
		try {
			ArrayList<Produs> produse = new ArrayList<Produs>();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");

			String query = "SELECT * FROM producator pr, categorie c, produs p WHERE p.codpr = pr.codpr AND p.codc = c.codc ";
			if(!nume.equals("")) query += "AND p.denumire LIKE '%" + nume + "%' ";
			query += " AND p.pret >= '" + min + "' ";
			if(max != 0) query += " AND p.pret <= '" + max + "'";
			if(!categorie.equals("Toate categoriile")) query += " AND c.denumire = '" + categorie + "' "; 
 			if(ordine.equals("Nume")) 
				query += " ORDER BY p.denumire, p.pret ";
			else if(ordine.equals("Cel mai mic pret"))
				query += " ORDER BY p.pret, p.denumire";
			else 
				query += " ORDER BY p.pret DESC, p.denumire ASC";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Producator prod = new Producator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				Categorie c = new Categorie(rs.getInt(6), rs.getString(7));
				Produs p = new Produs(rs.getInt(8), rs.getString(9), rs.getDouble(10), rs.getDouble(11), rs.getString(12),
						rs.getDate(13), rs.getDate(14), prod ,c);
				
				produse.add(p);
				}
			
			query = "SELECT * FROM Ingredient i, ingredient_pe_produs ip, produs p WHERE p.codp = ip.codp AND i.codi = ip.codi";
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				int codp = rs.getInt(3);
				for(Produs p: produse) 
					if(p.getCodp() == codp) {
						Ingredient i = new Ingredient(rs.getInt(1), rs.getString(2));
						p.addIngredient(i);
				}
			}
			
			
			rs.close();
			conn.close();
			return produse;
			
			} catch (Exception e) {
			System.out.println(e);	
			} return null;
	}
	

	public Produs cautaProdus(int codp) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");
			Produs p = new Produs();
			String query = "SELECT * FROM produs p, categorie c WHERE p.codc = c.codc AND codp = '" + codp + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Producator pr = new Producator(1, null, null, null, null);
				Categorie c = new Categorie(rs.getInt(10), rs.getString(11));
				p = new Produs(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getString(5), rs.getDate(6), rs.getDate(7), pr, c);
			}
			rs.close();
			conn.close();
			return p;
			
		} catch (Exception e) {
			System.out.println(e);	
		} return null;
	}
	
	public ArrayList<Angajat> getAngajati(){
		try {
			ArrayList<Angajat> angajati = new ArrayList<Angajat>();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");
			String query = "SELECT * FROM angajat WHERE rol = 'vanzator'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Angajat a = new Angajat(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				angajati.add(a);
				}
			
			rs.close();
			conn.close();
			return angajati;
			
			} catch (Exception e) {
			System.out.println(e);	
			} return null;
	}
	
	public ArrayList<Categorie> getCategorii(){
		
		try {
			ArrayList<Categorie> listaCategorii = new ArrayList<Categorie>();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");

			String query = "SELECT * FROM categorie ORDER BY denumire";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				  Categorie c = new Categorie(rs.getInt(1), rs.getString(2));
				  listaCategorii.add(c);
				}
			
			rs.close();
			conn.close();
			return listaCategorii;
			
			} catch (Exception e) {
			System.out.println(e);	
			} return null;

	}
	
	public ArrayList<Producator> getProducatori(){
		try {
			ArrayList<Producator> listaProducatori = new ArrayList<Producator>();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magazin_alimentar", "root", "");

			String query = "SELECT * FROM producator ORDER BY nume";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				 Producator p = new Producator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				 listaProducatori.add(p);
				}
			
			rs.close();
			conn.close();
			return listaProducatori;
			
			} catch (Exception e) {
			System.out.println(e);	
			} return null;
	}

	
}