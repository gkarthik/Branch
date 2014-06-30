package WekaDataTables;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//create table homosap  (
//tax_id int ,
//GeneID int ,
//Symbol varchar(500) ,
//LocusTag varchar(500),
//Synonym varchar(1000),
//dbXrefs varchar(500), 
//chromosome varchar(20),
//map_location varchar(500),
//description varchar(500),
//type_of_gene varchar(500),
//Symbol_from_nomenclature_authority varchar(500), 
//Full_name_from_nomenclature_authority varchar(500),
//Nomenclature_status varchar,
//Other_designations text,
//Modification_date timestamp
//);

@Entity
@Table(name = "HGI")
public class Homosapiens_Gene_Info {

	@Column
	private int tax_id;
	@Column
	@Id
	private String geneid;
	@Column
	private String symbol;
	@Column
	private String locustag;
	@Column
	private String synonym;
	@Column
	private String dbxrefs;
	@Column
	private String chromosome;
	@Column
	private String map_location;
	@Column
	private String description;
	@Column
	private String type_of_gene;
	@Column
	private String symbol_from_nomenclature_authority;
	@Column
	private String full_name_from_nomenclature_authority;
	@Column
	private String nomenclature_status;
	@Column(columnDefinition = "TEXT")
	private Clob other_designations;
	@Column
	private String modification_date;

	public String getChromosome() {
		return chromosome;
	}

	public String getDbxrefs() {
		return dbxrefs;
	}

	public String getDescription() {
		return description;
	}

	public String getFull_name_from_nomenclature_authority() {
		return full_name_from_nomenclature_authority;
	}

	public String getGeneid() {
		return geneid;
	}

	public String getLocustag() {
		return locustag;
	}

	public String getMap_location() {
		return map_location;
	}

	public String getModification_date() {
		return modification_date;
	}

	public String getNomenclature_status() {
		return nomenclature_status;
	}

	public Clob getOther_designations() {
		return other_designations;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getSymbol_from_nomenclature_authority() {
		return symbol_from_nomenclature_authority;
	}

	public String getSynonym() {
		return synonym;
	}

	public int getTax_id() {
		return tax_id;
	}

	public String getType_of_gene() {
		return type_of_gene;
	}

	// /@PersistenceContext EntityManager em;
	public void loadFile() {
		// em.getEntityManagerFactory();

	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public void setDbxrefs(String dbxrefs) {
		this.dbxrefs = dbxrefs;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFull_name_from_nomenclature_authority(
			String full_name_from_nomenclature_authority) {
		this.full_name_from_nomenclature_authority = full_name_from_nomenclature_authority;
	}

	public void setGeneid(String geneid) {
		this.geneid = geneid;
	}

	public void setLocustag(String locustag) {
		this.locustag = locustag;
	}

	public void setMap_location(String map_location) {
		this.map_location = map_location;
	}

	public void setModification_date(String modification_date) {
		this.modification_date = modification_date;
	}

	public void setNomenclature_status(String nomenclature_status) {
		this.nomenclature_status = nomenclature_status;
	}

	public void setOther_designations(Clob other_designations) {
		this.other_designations = other_designations;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setSymbol_from_nomenclature_authority(
			String symbol_from_nomenclature_authority) {
		this.symbol_from_nomenclature_authority = symbol_from_nomenclature_authority;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}

	public void setType_of_gene(String type_of_gene) {
		this.type_of_gene = type_of_gene;
	}
}
