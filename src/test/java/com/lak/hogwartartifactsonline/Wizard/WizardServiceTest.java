package com.lak.hogwartartifactsonline.Wizard;

import com.lak.hogwartartifactsonline.Artifact.Artifact;
import com.lak.hogwartartifactsonline.Artifact.ArtifactRepository;
import com.lak.hogwartartifactsonline.Artifact.ArtifactService;
import com.lak.hogwartartifactsonline.Artifact.utils.IdWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class WizardServiceTest {
    @Mock
    WizardRepository wizardRepository;
    @InjectMocks
    WizardService wizardService;
    @Mock
    IdWorker idWorker;

    List<Wizard> wizards;
    List<Artifact> artifacts;
    @BeforeEach
    void setUp(){
        Wizard wizard1=new Wizard();
        wizard1.setId(1);
        wizard1.setName("Harry Butter");

        Wizard wizard2=new Wizard();
        wizard2.setId(2);
        wizard2.setName("Mahmoud Nassif");

        this.wizards=new ArrayList<>();
        this.wizards.add(wizard1);
        this.wizards.add(wizard2);


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
    void tearDown(){}

    @Test
    void testFindWizardByIdSuccess(){
        //given
        Wizard wizard1=new Wizard();
        wizard1.setId(1);
        wizard1.setName("Harry Butter");

        wizard1.setArtifacts(this.artifacts);

        given(wizardRepository.findById(1)).willReturn(Optional.of(wizard1));
        // when
        Wizard foundWizard= wizardService.findById(1);
        // then
        assertThat(foundWizard.getId().equals(wizard1.getId()));
        assertThat(foundWizard.getName().equals(wizard1.getName()));
        verify(wizardRepository,times(1)).findById(1);

    }
    @Test
    void testFindByIdNotFound(){
        //given
        given(wizardRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());
        //when
       Throwable thrown= catchThrowable(()->{
           Wizard wizard= wizardService.findById(1);
        });
       //then
        assertThat(thrown).isInstanceOf(WizardNotFoundException.class).hasMessage("could not find wizard with id 1");
        verify(wizardRepository,times(1)).findById(1);
    }

    @Test
    void testFindAllWizardsSuccess(){
        //given
        given(wizardRepository.findAll()).willReturn(this.wizards);
        //when
        List<Wizard>returnedWizards= wizardService.findAllWizards();
        //then
        assertThat(returnedWizards.size()==this.artifacts.size());
        verify(wizardRepository,times(1)).findAll();

    }

    @Test
    void testAddWizardSuccess(){
        //given
        Wizard wizard1=new Wizard();
        wizard1.setName("Harry Butter");

        given(idWorker.nextId()).willReturn(103L);
        given(wizardRepository.save(wizard1)).willReturn(wizard1);

        //when
        Wizard savedWizard= wizardService.addWizard(wizard1);
        //then
        assertThat(savedWizard.getName().equals(wizard1.getName()));
        assertThat(savedWizard.getId().equals(103L));
        verify(wizardRepository,times(1)).save(wizard1);

    }

    @Test
    void testUpdateWizardSuccess(){
        //given
        Wizard wizard1=new Wizard();
        wizard1.setId(1);
        wizard1.setName("Harry Butter");

        Wizard wizard2=new Wizard();
        wizard2.setName("Harry updated");

        given(wizardRepository.findById(1)).willReturn(Optional.of(wizard1));
        given(wizardRepository.save(wizard1)).willReturn(wizard1);

        //when
        Wizard updatedArtifact= wizardService.updateWizard(1,wizard2);
        //then
        assertThat(updatedArtifact.getId().equals(wizard1.getId()));
        assertThat(updatedArtifact.getName().equals(wizard2.getName()));
        verify(wizardRepository,times(1)).findById(1);
        verify(wizardRepository,times(1)).save(wizard1);



    }
    @Test
    void testUpdateWizardNotFound(){
        //given
        Wizard wizard1=new Wizard();
        wizard1.setId(1);
        wizard1.setName("Harry Butter");
        given(wizardRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());
        //when
        Throwable thrown= catchThrowable(()->{
            wizardService.updateWizard(Mockito.any(Integer.class),wizard1);
        });
        //then
        assertThat(thrown).isInstanceOf(WizardNotFoundException.class).hasMessage(thrown.getMessage());
        verify(wizardRepository,times(1)).findById(Mockito.any(Integer.class));
    }
    @Test
    void testDeleteWizardSuccess(){
        //given
        Wizard wizard1=new Wizard();
        wizard1.setId(102);
        wizard1.setName("Harry Butter");
        given(wizardRepository.findById(102)).willReturn(Optional.of(wizard1));
        doNothing().when(wizardRepository).deleteById(wizard1.getId());
        //when
        wizardService.deleteWizard(102);
        //then
        verify(wizardRepository,times(1)).deleteById(102);
    }
    @Test
    void testDeleteWizardNotFound(){
       given(wizardRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        assertThrows( WizardNotFoundException.class,()->{
            wizardService.deleteWizard(Mockito.any(Integer.class));
        });
       verify(wizardRepository,times(1)).findById(Mockito.any(Integer.class));
    }

}
