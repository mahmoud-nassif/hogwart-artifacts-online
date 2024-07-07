package com.lak.hogwartartifactsonline.system;

import com.lak.hogwartartifactsonline.Artifact.Artifact;
import com.lak.hogwartartifactsonline.Artifact.ArtifactNotFoundException;
import com.lak.hogwartartifactsonline.Artifact.ArtifactRepository;
import com.lak.hogwartartifactsonline.Wizard.Wizard;
import com.lak.hogwartartifactsonline.Wizard.WizardRepository;
import com.lak.hogwartartifactsonline.hogwartsuser.HogwartsUser;
import com.lak.hogwartartifactsonline.hogwartsuser.UserRepository;
import com.lak.hogwartartifactsonline.hogwartsuser.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DBDataIntializer implements CommandLineRunner {

    private final ArtifactRepository artifactRepository;
    private final WizardRepository wizardRepository;
    private final UserService userService;

    public DBDataIntializer(ArtifactRepository artifactRepository, WizardRepository wizardRepository, UserService userService) {
        this.artifactRepository = artifactRepository;
        this.wizardRepository = wizardRepository;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
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

        Artifact a3=new Artifact();
        a3.setId(10);
        a3.setName("okapi");
        a3.setDescription("highly recommended sword to kill people");
        a3.setImageUrl("url");

        Artifact a4=new Artifact();
        a4.setId(11);
        a4.setName("saforia");
        a4.setDescription("highly recommended sword to kill people");
        a4.setImageUrl("url");

        Artifact a5=new Artifact();
        a5.setId(12);
        a5.setName("gun");
        a5.setDescription("highly recommended sword to kill people");
        a5.setImageUrl("url");

        Artifact a6=new Artifact();
        a6.setId(13);
        a6.setName("mgk");
        a6.setDescription("highly recommended sword to kill people");
        a6.setImageUrl("url");

        Wizard w1 =new Wizard();
        w1.setId(101);
        w1.setName("Harry Botter");
        w1.addArtifact(a1);
        w1.addArtifact(a2);

        Wizard w2 =new Wizard();
        w2.setId(102);
        w2.setName("Sofial");
        w2.addArtifact(a3);
        w2.addArtifact(a4);

        Wizard w3 =new Wizard();
        w3.setId(103);
        w3.setName("Tomborlank");
        w3.addArtifact(a5);

        this.wizardRepository.save(w1);
        this.wizardRepository.save(w2);
        this.wizardRepository.save(w3);

        this.artifactRepository.save(a6);


        // Create some users.
        HogwartsUser u1 = new HogwartsUser();
        u1.setId(1);
        u1.setUsername("john");
        u1.setPassword("123456");
        u1.setEnabled(true);
        u1.setRoles("admin user");

        HogwartsUser u2 = new HogwartsUser();
        u2.setId(2);
        u2.setUsername("eric");
        u2.setPassword("654321");
        u2.setEnabled(true);
        u2.setRoles("user");

        HogwartsUser u3 = new HogwartsUser();
        u3.setId(3);
        u3.setUsername("tom");
        u3.setPassword("qwerty");
        u3.setEnabled(false);
        u3.setRoles("user");

        this.userService.save(u1);
        this.userService.save(u2);
        this.userService.save(u3);

    }
}
