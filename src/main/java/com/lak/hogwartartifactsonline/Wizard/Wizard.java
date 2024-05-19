package com.lak.hogwartartifactsonline.Wizard;

import com.lak.hogwartartifactsonline.Artifact.Artifact;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wizard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "wizard",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Artifact> artifacts= new ArrayList<>();

    public Wizard(){}

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

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public void addArtifact(Artifact artifact){
        this.artifacts.add(artifact);
        artifact.setWizard(this);
    }

    public Integer getNumberOfArtifacts() {
        return this.getArtifacts().size();
    }

    public void removeAllArtifacts(){
        this.artifacts.stream().forEach(artifact -> artifact.setWizard(null));
        this.artifacts= new ArrayList<>();
    }
}
