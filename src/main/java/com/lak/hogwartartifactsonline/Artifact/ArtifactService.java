package com.lak.hogwartartifactsonline.Artifact;

import com.lak.hogwartartifactsonline.Artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
@Transactional
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    public Artifact findById(String artifactId){
        return artifactRepository.findById(artifactId).orElseThrow(()->new ArtifactNotFoundException(artifactId));
    }

    public List<Artifact> findAllArtifacts(){
        return this.artifactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact){
        newArtifact.setId((int)idWorker.nextId());
        return artifactRepository.save(newArtifact);
    }

    public Artifact update(String artifactId,Artifact artifact){
        return artifactRepository.findById(artifactId)
                .map(oldArtifact -> {
                    oldArtifact.setName(artifact.getName());
                    oldArtifact.setDescription(artifact.getDescription());
                    oldArtifact.setImageUrl(artifact.getImageUrl());
                    oldArtifact.setWizard(artifact.getWizard());
                    return artifactRepository.save(oldArtifact);
                 })
                .orElseThrow(()->new ArtifactNotFoundException(artifactId));
    }

    public void delete(String artifactId){
      artifactRepository.findById(artifactId).orElseThrow(()->new ArtifactNotFoundException(artifactId));
      artifactRepository.deleteById(artifactId);
    }
}
