
public class Angajat {
	int coda;
	String nume;
	String rol;
	String telefon;
	String adresa;
	
	
	public Angajat(int coda, String nume, String rol, String telefon, String adresa) {
		this.coda = coda;
		this.nume = nume;
		this.telefon = telefon;
		this.adresa = adresa;
		this.rol = rol;
	}

	public int getCoda() {
		return coda;
	}

	public String getNume() {
		return nume;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getAdresa() {
		return adresa;
	}

	public String getRol() {
		return rol;
	}
	
	
}
