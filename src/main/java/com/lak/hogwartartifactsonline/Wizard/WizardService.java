package com.lak.hogwartartifactsonline.Wizard;

import com.lak.hogwartartifactsonline.Artifact.Artifact;
import com.lak.hogwartartifactsonline.Artifact.ArtifactNotFoundException;
import com.lak.hogwartartifactsonline.Artifact.ArtifactRepository;
import com.lak.hogwartartifactsonline.Artifact.utils.IdWorker;
import com.lak.hogwartartifactsonline.Wizard.converter.WizradToWizardDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WizardService {

    @Autowired
    WizardRepository wizardRepository;
    @Autowired
    ArtifactRepository artifactRepository;
    @Autowired
    IdWorker idWorker;


    public Wizard findById(int wizardId){
        return wizardRepository.findById(wizardId).orElseThrow(()->new WizardNotFoundException(wizardId+""));
    }

    public List<Wizard> findAllWizards(){
        return wizardRepository.findAll();
    }

    public Wizard addWizard(Wizard wizard){
        wizard.setId((int)idWorker.nextId());
        return wizardRepository.save(wizard);
    }

    public Wizard updateWizard(int wizardId,Wizard wizard){
       return wizardRepository.findById(wizardId).map(foundWizard->{
            foundWizard.setName(wizard.getName());
            return wizardRepository.save(foundWizard);
        }).orElseThrow(()->new WizardNotFoundException(wizardId+""));
    }

    public void deleteWizard(int wizardId){
        Wizard wizard= wizardRepository.findById(wizardId).orElseThrow(()->new WizardNotFoundException(wizardId+""));
        wizard.removeAllArtifacts();
        wizardRepository.deleteById(wizardId);
    }

    public void assignArtifact(int wizardId,int artifactId){
       Artifact artifact= artifactRepository.findById(artifactId+"").orElseThrow(()->new ArtifactNotFoundException(artifactId+""));
       Wizard wizard = wizardRepository.findById(wizardId).orElseThrow(()->new WizardNotFoundException(wizardId+""));

       //if artifact has wizard deattach it
        if(artifact.getWizard() != null){
            artifact.getWizard().deattachArtifact(artifact);
        }
       wizard.addArtifact(artifact);
//       wizardRepository.save(wizard);
    }
}
