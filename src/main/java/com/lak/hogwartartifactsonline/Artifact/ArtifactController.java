package com.lak.hogwartartifactsonline.Artifact;

import com.lak.hogwartartifactsonline.Artifact.converter.ArtifactDtoToArtifactConverter;
import com.lak.hogwartartifactsonline.Artifact.converter.ArtifactToArtifactDtoConverter;
import com.lak.hogwartartifactsonline.Artifact.dto.ArtifactDto;
import com.lak.hogwartartifactsonline.system.Result;
import com.lak.hogwartartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("${baseUrl}/artifacts")
public class ArtifactController {
    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId){
        Artifact artifact=  this.artifactService.findById(artifactId);
        return new Result(true, StatusCode.SUCCESS,"find one success",this.artifactToArtifactDtoConverter.convert(artifact));
    }

    @GetMapping
    public Result findAllArtifacts(){
        List<Artifact> artifacts= this.artifactService.findAllArtifacts();
        List<ArtifactDto> artifactsDto= artifacts.stream().map(artifactToArtifactDtoConverter::convert).collect(Collectors.toList());
        return new Result(true,StatusCode.SUCCESS,"find all success",artifactsDto);
    }

    @PostMapping
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto){
       Artifact artifact= this.artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact returnedArtifact= artifactService.save(artifact);
        return new Result(true,StatusCode.SUCCESS,"save success",artifactToArtifactDtoConverter.convert(returnedArtifact));
    }

    @PutMapping("/{artifactId}")
    public Result updateArtifact(@PathVariable String artifactId,@Valid @RequestBody ArtifactDto artifactDto){
        Artifact updatedArtifact= artifactService.update(artifactId,this.artifactDtoToArtifactConverter.convert(artifactDto));
        return new Result(true,StatusCode.SUCCESS, "update success",this.artifactToArtifactDtoConverter.convert(updatedArtifact));
    }

    @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId){
        artifactService.delete(artifactId);
        return new Result(true,StatusCode.SUCCESS,"delete success",null);
    }

}
