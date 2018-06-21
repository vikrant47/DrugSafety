package com.example.drugsafety.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;

/**
 * The persistent class for the drug_data database table.
 *
 */
@Entity
@Table(name = "drug_data")
@NamedQuery(name = "DrugData.findAll", query = "SELECT d FROM DrugData d")
public class DrugData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private BigInteger drugManufracturer;
    private String genericName;
    private String logoPath;
    private String name;

    public DrugData() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "drug_manufracturer")
    public BigInteger getDrugManufracturer() {
        return this.drugManufracturer;
    }

    public void setDrugManufracturer(BigInteger drugManufracturer) {
        this.drugManufracturer = drugManufracturer;
    }

    @Column(name = "generic_name", length = 255)
    public String getGenericName() {
        return this.genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    @Column(name = "logo_path", length = 255)
    public String getLogoPath() {
        return this.logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Column(nullable = false, length = 255)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
