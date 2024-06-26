package com.lak.hogwartartifactsonline.Artifact;

import com.lak.hogwartartifactsonline.Wizard.Wizard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
@Entity
public class Artifact implements Serializable {

    @Id
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;

    @ManyToOne
    private Wizard wizard;

    public Artifact(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }
}
