import java.sql.Date;
import java.util.ArrayList;

public class Produs {
	int codp;
	String denumire;
	double pret;
	double pretAchizitie;
	String gramaj;
	Date dataFabricatiei;
	Date dataExpirarii;
	Producator producator;
	Categorie categorie;
	ArrayList<Ingredient> ingrediente;
	
	public Produs(int codp, String denumire, double pret, double pretAchizitie,  String gramaj, Date dataFabricatiei, Date dataExpirarii,
			Producator producator, Categorie categorie) {
		this.codp = codp;
		this.denumire = denumire;
		this.pret = pret;
		this.dataFabricatiei = dataFabricatiei;
		this.dataExpirarii = dataExpirarii;
		this.gramaj = gramaj;
		this.pretAchizitie = pretAchizitie;
		this.producator = producator;
		this.categorie = categorie;
		ingrediente = new ArrayList<Ingredient>();
	}
	
	public Produs() {
		
	}
	
	public void addIngrediente(ArrayList<Ingredient> ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	public void addIngredient(Ingredient i) {
		ingrediente.add(i);
	}

	public int getCodp() {
		return codp;
	}

	public String getDenumire() {
		return denumire;
	}

	public double getPret() {
		return pret;
	}

	public Date getDataFabricatiei() {
		return dataFabricatiei;
	}

	public Date getDataExpirarii() {
		return dataExpirarii;
	}

	public String getGramaj() {
		return gramaj;
	}

	public double getPretAchizitie() {
		return pretAchizitie;
	}

	public Producator getProducator() {
		return producator;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public ArrayList<Ingredient> getIngrediente() {
		return ingrediente;
	}

	
	
}
