package org.tuxotpub.booksmanager.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazinesDTO;
import org.tuxotpub.booksmanager.services.parchments.MagazineService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by tuxsamo.
 */

@RestController @RequestMapping(MagazineController.BASE_PATH)
@Api(tags = {"magazineDescription"}) @PropertySource(ignoreResourceNotFound=true,value="classpath:swagger.properties")
public class MagazineController {

    public static final String BASE_PATH = "/api/magazines/v1";
    public static final String FINDBYID_PATH = BASE_PATH + "/id/";
    public static final String FINDBYISBN_PATH = BASE_PATH + "/isbn/";
    private final MagazineService magazineService;

    public MagazineController(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    @ApiOperation(value = "Create or Update magazine", notes="Hier you can create or update magazine")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MagazineDTO createMagazine(@Valid @RequestBody MagazineDTO magazineDTO){
        return magazineService.create(magazineDTO);
    }

    @ApiOperation(value = "View List of Magazines", notes="Hier is the list of all Magatines")
    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resources<?> getAllMagazines(){
        List<Resource<MagazineDTO>> magazineResources = new ArrayList<>();
        magazineService.getAll().forEach(
                magazineDTO -> { magazineResources.add(new Resource<>( magazineDTO, getLinks( magazineDTO ) ) );
                } );
        Link link =linkTo(MagazineController.class).withSelfRel();
        return new Resources<Resource<MagazineDTO>>(magazineResources,link);
    }

    @ApiOperation(value = "Find magazine by id", notes="Hier you can find magazine by id")
    @GetMapping(value = "/id/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resource<MagazineDTO> getMagazineById(@PathVariable Long id){
        MagazineDTO magazineDTO = magazineService.getById(id);
        return new Resource<>(magazineDTO, getLinks( magazineDTO ));
    }

    @ApiOperation(value = "Find magazine by isbnTest", notes="Hier you can find magazine by isbnTest")
    @GetMapping(value = "/isbn/{isbn}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resource<MagazineDTO> getMagazineByIsbn(@PathVariable String isbn){
        MagazineDTO magazineDTO = magazineService.getByIsbn(isbn);
        return new Resource<>(magazineDTO, getLinks( magazineDTO ));
    }

    @ApiOperation(value = "Update magazine by id", notes="Hier you can update magazine by id")
    @PutMapping("/{id}")
    public MagazineDTO updateMagazine(@PathVariable Long id, @Valid @RequestBody MagazineDTO magazineDTO){
        return magazineService.updateById(id, magazineDTO);
    }

    @ApiOperation(value = "Delete magazine by id", notes="Hier you can delete magazine by id")
    @DeleteMapping("/{id}")
    public void deleteMagazine(@PathVariable Long id){
        magazineService.deleteById(id);
    }

    private List<Link> getLinks(MagazineDTO magazineDTO) {
        return Arrays.asList(linkTo(methodOn(MagazineController.class).getMagazineById(magazineDTO.getId())).withSelfRel(),
                linkTo(methodOn(MagazineController.class).getMagazineByIsbn(magazineDTO.getIsbn())).withSelfRel(),
                linkTo(methodOn(MagazineController.class).getAllMagazines()).withRel(BASE_PATH));
    }
}
