package com.lak.hogwartartifactsonline.Wizard;


import com.lak.hogwartartifactsonline.Wizard.converter.WizardDtoToWizardConverter;
import com.lak.hogwartartifactsonline.Wizard.converter.WizradToWizardDtoConverter;
import com.lak.hogwartartifactsonline.Wizard.dto.WizardDto;
import com.lak.hogwartartifactsonline.system.Result;
import com.lak.hogwartartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${baseUrl}/wizards")
public class WizardController {

    @Autowired
    WizardService wizardService;
    @Autowired
    WizradToWizardDtoConverter wizradToWizardDtoConverter;
    @Autowired
    WizardDtoToWizardConverter wizardDtoToWizardConverter;

    @GetMapping("/{wizardId}")
    public Result getWizardById(@PathVariable int wizardId){
        System.out.println(wizardId);
        Wizard wizard=wizardService.findById(wizardId);
        return new Result(true, StatusCode.SUCCESS,"found successfully", wizradToWizardDtoConverter.convert(wizard));
    }

    @GetMapping
    public Result getAllWizards(){
        List<WizardDto> wizardDtos=wizardService.findAllWizards().stream()
                                                .map(wizradToWizardDtoConverter::convert).collect(Collectors.toList());
        return new Result(true,StatusCode.SUCCESS,"found all successfully",wizardDtos);
    }

    @PostMapping
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto){
        Wizard wizard= wizardDtoToWizardConverter.convert(wizardDto);
        Wizard addedWizard= wizardService.addWizard(wizard);
        return new Result(true,StatusCode.SUCCESS,"saved successfully",wizradToWizardDtoConverter.convert(addedWizard));
    }

    @PutMapping("/{wizardId}")
    public Result updateWizard(@PathVariable int wizardId,@Valid @RequestBody WizardDto wizardDto){
        Wizard updatedWizard= wizardService.updateWizard(wizardId,wizardDtoToWizardConverter.convert(wizardDto));
        return new Result(true,StatusCode.SUCCESS,"updated successfullt",wizradToWizardDtoConverter.convert(updatedWizard));
    }

    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable int wizardId){
        wizardService.deleteWizard(wizardId);
        return new Result(true,StatusCode.SUCCESS,"deleted successfully",null);
    }

    @PutMapping("/{wizardId}/artifacts/{artifactId}")
    public Result assignArtifactToWizard(@PathVariable int wizardId,@PathVariable int artifactId){
        this.wizardService.assignArtifact(wizardId,artifactId);
        return new Result(true,StatusCode.SUCCESS,"attached",null);
    }

}
