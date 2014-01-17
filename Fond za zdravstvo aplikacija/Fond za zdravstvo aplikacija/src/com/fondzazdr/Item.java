package com.fondzazdr;

public class Item {
	// private int id;
	private String id;
	private String naziv;
	private String proizvoditel;
	private String cena;
	private String slicni;
	private String slicnid;
	private String FoodInteractions;
	private String Indications;
    private String Description;
    
	public String getslicniid() {
		return slicnid;
	}

	public void setslicniid(String slicnid) {
		this.slicnid = slicnid;
	}

	public String getslicni() {
		return slicni;
	}

	public void setslicni(String slicni) {
		this.slicni = slicni;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getcena() {
		return cena;
	}

	public void setcena(String cena) {
		this.cena = cena;
	}

	public String getnaziv() {
		return naziv;
	}

	public void setnaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getproizvoditel() {
		return proizvoditel;
	}

	public void setproizvoditel(String proizvoditel) {
		this.proizvoditel = proizvoditel;
	}
	
	public String getfoodinteractions() {
		return FoodInteractions;
	}

	public void setfoodinteractions(String FoodInteractions) {
		this.FoodInteractions = FoodInteractions;
	}
	
	public String getIndications() {
		return Indications;
	}

	public void setIndications(String Indications) {
		this.Indications = Indications;
	}
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}
	
}
