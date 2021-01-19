
public class Client {
	int codcl;
	String nume;
	String telefon;
	String adresa;
	String email;
	
	public Client(int codcl, String nume, String telefon, String adresa, String email) {
		this.codcl = codcl;
		this.nume = nume;
		this.telefon = telefon;
		this.adresa = adresa;
		this.email = email;
	}

	public int getCodcl() {
		return codcl;
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

	public String getEmail() {
		return email;
	}
	
	
}
