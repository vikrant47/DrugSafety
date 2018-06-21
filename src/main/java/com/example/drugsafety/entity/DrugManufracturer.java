package com.example.drugsafety.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the drug_manufracturer database table.
 *
 */
@Entity
@Table(name = "drug_manufracturer")
@NamedQuery(name = "DrugManufracturer.findAll", query = "SELECT d FROM DrugManufracturer d")
public class DrugManufracturer implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String logoPath;
    private String name;

    public DrugManufracturer() {
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
