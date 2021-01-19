
public class BonFiscal {
	int codb;
	int pretTotal;
	String data;
	String ora;
	Angajat angajat;
	
	public BonFiscal(int codb, int pretTotal, String data, String ora, Angajat angajat) {
		this.codb = codb;
		this.pretTotal = pretTotal;
		this.data = data;
		this.ora = ora;
		this.angajat = angajat;
	}

	public int getCodb() {
		return codb;
	}

	public int getPretTotal() {
		return pretTotal;
	}

	public String getData() {
		return data;
	}

	public String getOra() {
		return ora;
	}

	public Angajat getAngajat() {
		return angajat;
	}
	
}
