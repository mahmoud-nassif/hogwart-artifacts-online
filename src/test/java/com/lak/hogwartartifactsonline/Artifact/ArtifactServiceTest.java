package com.lak.hogwartartifactsonline.Artifact;

import com.lak.hogwartartifactsonline.Artifact.utils.IdWorker;
import com.lak.hogwartartifactsonline.Wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    private ArtifactRepository artifactRepository;
    @Mock
    IdWorker idWorker;

    @InjectMocks
    private ArtifactService artifactService;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        Artifact a1=new Artifact();
        a1.setId(8);
        a1.setName("sword");
        a1.setDescription("highly recommended sword to kill people");
        a1.setImageUrl("url");

        Artifact a2=new Artifact();
        a2.setId(9);
        a2.setName("knife");
        a2.setDescription("highly recommended sword to kill people");
        a2.setImageUrl("url");

        this.artifacts=new ArrayList<>();
        this.artifacts.add(a1);
        this.artifacts.add(a2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //given
        Artifact artifact= new Artifact();
        artifact.setId(21);
        artifact.setName("sword");
        artifact.setDescription(("magic knife to kill"));
        artifact.setImageUrl("sword.jpg");

        Wizard wizard=new Wizard();
        wizard.setId(32);
        wizard.setName("NassifMGK");
        
        artifact.setWizard(wizard);
        given(artifactRepository.findById("21")).willReturn(Optional.of(artifact));
        //when
        Artifact artifact2= artifactService.findById("21");
        //then assert
        assertThat(artifact2.getId().equals(artifact.getId()));
        assertThat(artifact2.getName().equals(artifact.getName()));
        assertThat(artifact2.getDescription().equals(artifact.getDescription()));
        assertThat(artifact2.getImageUrl().equals(artifact.getImageUrl()));
        verify(artifactRepository,times(1)).findById("21");
    }

    @Test
    void testFindByIdNotFound(){
        //given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());
        //when
        Throwable thrown= catchThrowable(()->{
            Artifact artifact2= artifactService.findById("2242421");
        });
        //then
        assertThat(thrown)
             .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("could not find artifact with id 2242421 :(");
        verify(artifactRepository,times(1)).findById("2242421");
    }

    @Test
    void testFindAllSuccess(){
        //given
        given(artifactRepository.findAll()).willReturn(artifacts);
        // when
        List<Artifact> actualArtifacts=  artifactService.findAllArtifacts();
        // then
        assertThat(actualArtifacts.size()==this.artifacts.size());
        verify(artifactRepository,times(1)).findAll();

    }
    @Test
    void testSaveSuccess(){
        //given
        Artifact artifact= new Artifact();
        artifact.setName("okapi");
        artifact.setDescription("bad way to die");
        artifact.setImageUrl("http://local.com/kit.jpg");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(artifact)).willReturn(artifact);

        // when

        Artifact savedArtifact= artifactService.save(artifact);

        // then

        assertThat(savedArtifact.getId()).isEqualTo(123456L);
        assertThat(savedArtifact.getName()).isEqualTo("okapi");
        assertThat(savedArtifact.getDescription()).isEqualTo("bad way to die");
        assertThat(savedArtifact.getImageUrl()).isEqualTo("http://local.com/kit.jpg");
        verify(artifactRepository,times(1)).save(artifact);
    }

    @Test
    void testUpdateSuccess(){
        //given
        Artifact a1=new Artifact();
        a1.setId(8);
        a1.setName("sword");
        a1.setDescription("highly recommended sword to kill people");
        a1.setImageUrl("url");

        Artifact update=new Artifact();
        update.setName("knife");
        update.setDescription("highly recommended sword to kill people");
        update.setImageUrl("url");

        given(artifactRepository.findById("8")).willReturn(Optional.of(a1));
        given(artifactRepository.save(a1)).willReturn(a1);

        // when
        Artifact updatedArtifact= artifactService.update("8",update);
        // then
        assertThat(updatedArtifact.getId().equals(a1.getId()));
        assertThat(updatedArtifact.getName().equals(update.getName()));
        assertThat(updatedArtifact.getDescription().equals(a1.getDescription()));
        assertThat(updatedArtifact.getImageUrl().equals(a1.getImageUrl()));
        verify(artifactRepository,times(1)).findById("8");
        verify(artifactRepository,times(1)).save(a1);
    }

    @Test
    void testUpdateNotFound(){
        //given
        Artifact update=new Artifact();
        update.setName("knife");
        update.setDescription("highly recommended sword to kill people");
        update.setImageUrl("url");

        given(artifactRepository.findById("8")).willReturn(Optional.empty());

        assertThrows(ArtifactNotFoundException.class,()->{
            artifactService.update("8",update);
        });
    }

    @Test
    void testDeleteSuccess(){
        //given
        Artifact artifact=new Artifact();
        artifact.setId(10);
        artifact.setName("knife");
        artifact.setDescription("highly recommended sword to kill people");
        artifact.setImageUrl("url");

        given(artifactRepository.findById(artifact.getId().toString())).willReturn(Optional.of(artifact));
        doNothing().when(artifactRepository).deleteById(artifact.getId().toString());
        // when
        artifactService.delete("10");
        // then
        verify(artifactRepository,times(1)).deleteById("10");
    }

    @Test
    void testDeleteNotFound(){
        //given
        Artifact artifact=new Artifact();
        artifact.setId(10);
        artifact.setName("knife");
        artifact.setDescription("highly recommended sword to kill people");
        artifact.setImageUrl("url");

        given(artifactRepository.findById(artifact.getId().toString())).willReturn(Optional.empty());
        // when
        // then
        assertThrows(ArtifactNotFoundException.class,()->{
            artifactService.delete("10");
        });
        verify(artifactRepository,times(1)).findById("10");


    }

}
