
public class Producator {
	int codprod;
	String nume;
	String adresa;
	String telefon;
	String tara;
	
	public Producator(int codprod, String nume, String telefon, String tara, String adresa) {
		this.codprod = codprod;
		this.nume = nume;
		this.adresa = adresa;
		this.telefon = telefon;
		this.tara = tara;
	}

	public int getCodprod() {
		return codprod;
	}
	
	public void setCodprod(int codprod) {
		this.codprod = codprod; 
	}

	public String getNume() {
		return nume;
	}

	public String getAdresa() {
		return adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getTara() {
		return tara;
	}

	
}
