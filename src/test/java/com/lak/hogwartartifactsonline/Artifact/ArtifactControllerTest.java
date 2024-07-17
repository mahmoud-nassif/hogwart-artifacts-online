package com.lak.hogwartartifactsonline.Artifact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lak.hogwartartifactsonline.Artifact.converter.ArtifactToArtifactDtoConverter;
import com.lak.hogwartartifactsonline.Artifact.dto.ArtifactDto;
import com.lak.hogwartartifactsonline.system.StatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ArtifactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ArtifactService artifactService;

    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifacts;
    @BeforeEach
    void setUp() {
        artifacts= new ArrayList<Artifact>();

        Artifact artifact1= new Artifact();
        artifact1.setId(21);
        artifact1.setName("sword");
        artifact1.setDescription(("magic knife to kill"));
        artifact1.setImageUrl("sword.jpg");
        artifacts.add(artifact1);

        Artifact artifact2= new Artifact();
        artifact2.setId(22);
        artifact2.setName("Knife");
        artifact2.setDescription(("magic knife to kill"));
        artifact2.setImageUrl("sword.jpg");
        artifacts.add(artifact2);

        Artifact artifact3= new Artifact();
        artifact3.setId(23);
        artifact3.setName("EKatar");
        artifact3.setDescription(("magic knife to kill"));
        artifact3.setImageUrl("sword.jpg");
        artifacts.add(artifact3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() throws Exception {
        //given
         given(this.artifactService.findById("23")).willReturn(this.artifacts.get(2));
        //when //then
        this.mockMvc.perform(get("/api/v1/artifacts/23").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("find one success"))
                .andExpect(jsonPath("$.data.id").value(23))
                .andExpect(jsonPath("$.data.name").value("EKatar"));

    }
    @Test
    void testFindArtifactByIdNotFound() throws Exception {
        //given
        given(this.artifactService.findById("454545")).willThrow(new ArtifactNotFoundException("454545"));
        //when //then
        this.mockMvc.perform(get("/api/v1/artifacts/454545").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find artifact with id 454545 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testFindAllArtifacts() throws Exception {
        //given
        given(artifactService.findAllArtifacts()).willReturn(this.artifacts);
        // when// then
        this.mockMvc.perform(get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("find all success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value(21));

    }
    @Test
    void testSaveSuccess() throws Exception {
        //given
        ArtifactDto artifactDto= new ArtifactDto(null,"okapi","bad","url",null);
        String json= objectMapper.writeValueAsString(artifactDto);

        Artifact returnedArtifact= new Artifact();
        returnedArtifact.setId(123456);
        returnedArtifact.setName("okapi");
        returnedArtifact.setDescription("bad");
        returnedArtifact.setImageUrl("url");


        given(artifactService.save(Mockito.any(Artifact.class))).willReturn(returnedArtifact);
        // when// then
        this.mockMvc.perform(post("/api/v1/artifacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("save success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(123456))
                .andExpect(jsonPath("$.data.name").value("okapi"))
                .andExpect(jsonPath("$.data.description").value("bad"))
                .andExpect(jsonPath("$.data.imageUrl").value("url"));
    }
    @Test
    void testUpdateSuccess() throws Exception {
        //given
        ArtifactDto artifactDto= new ArtifactDto(8,"okapi","bad","url",null);
        String json= objectMapper.writeValueAsString(artifactDto);

        Artifact updatedArtifact= new Artifact();
        updatedArtifact.setId(8);
        updatedArtifact.setName("okapi");
        updatedArtifact.setDescription("bad");
        updatedArtifact.setImageUrl("url");

        given(artifactService.update(eq("8"),Mockito.any(Artifact.class))).willReturn(updatedArtifact);

        //when
        this.mockMvc.perform(put("/api/v1/artifacts/8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("update success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(8))
                .andExpect(jsonPath("$.data.name").value("okapi"))
                .andExpect(jsonPath("$.data.description").value("bad"))
                .andExpect(jsonPath("$.data.imageUrl").value("url"));
    }
    @Test
    void testUpdateArtifactNotFound() throws Exception {
        //given
        ArtifactDto artifactDto= new ArtifactDto(8,"okapi","bad","url",null);
        String json= objectMapper.writeValueAsString(artifactDto);

        Artifact updatedArtifact= new Artifact();
        updatedArtifact.setId(8);
        updatedArtifact.setName("okapi");
        updatedArtifact.setDescription("bad");
        updatedArtifact.setImageUrl("url");

        given(artifactService.update(eq("8"),Mockito.any(Artifact.class))).willThrow(new ArtifactNotFoundException("8"));

        //when //then
        //when
        this.mockMvc.perform(put("/api/v1/artifacts/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find artifact with id 8 :("))
                .andExpect(jsonPath("$.data").isEmpty());

    }
    @Test
    void testDeleteSuccess() throws Exception {
        //given
        doNothing().when(artifactService).delete("10");

        //when
        this.mockMvc.perform(delete("/api/v1/artifacts/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("delete success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testDeleteNotFound() throws Exception {
        //given
        doThrow(new ArtifactNotFoundException("10")).when(artifactService).delete("10");
        //when
        this.mockMvc.perform(delete("/api/v1/artifacts/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find artifact with id 10 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}