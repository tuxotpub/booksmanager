package org.tuxotpub.booksmanager.controllers.v1;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.tuxotpub.booksmanager.api.v1.dtos.*;
import org.tuxotpub.booksmanager.api.v1.mapper.MagazineMapper;
import org.tuxotpub.booksmanager.bootstrap.BootstrapData;
import org.tuxotpub.booksmanager.services.parchments.MagazineService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tuxotpub.booksmanager.controllers.v1.MagazineController.*;
import static org.tuxotpub.booksmanager.bootstrap.BootstrapData.*;

/**
 * Created by tuxsamo.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MagazineControllerTestIT {

    @Autowired
    //private ParchmentService<MagazineDTO, Magazine> magazineService;
    private MagazineService magazineService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BootstrapData bootstrap;

    @Autowired
    private MagazineMapper magazineMapper;

    private List<MagazineDTO> magazineDTOS = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
            MAGAZINES.forEach(
                magazine -> {
                    MagazineDTO magazineDTO = magazineMapper.getMagazineDTO(magazine);
                    magazineDTO.setUrl(MagazineController.FINDBYID_PATH + magazineDTO.getId());
                    magazineDTOS.add(magazineDTO);
                });
    }

    @Test
    public void t1getAllMagazines() {
        MagazinesDTO response = restTemplate.getForObject(BASE_PATH + "all", MagazinesDTO.class);
        assertThat( response.getMagazineDTOS() ).containsExactlyInAnyOrderElementsOf( magazineDTOS );
    }

    @Test
    public void t2getMagazineById() {
        MagazineDTO response = restTemplate.getForObject(FINDBYID_PATH + magazineDTOS.get(0).getId(), MagazineDTO.class);
        assertThat(response).isEqualToComparingFieldByField(magazineDTOS.get(0));
    }

    @Test
    public void t3createMagazine() {
        MagazineDTO magazineDTO = magazineMapper.getMagazineDTO( buildMagazine( PARCHMENTS_SIZE ) );
        HttpEntity<MagazineDTO> request = new HttpEntity<>( magazineDTO );
        MagazineDTO reponse = restTemplate.postForObject(BASE_PATH + "create", request, MagazineDTO.class );
        assertThat( reponse ).isEqualToComparingFieldByField( magazineService.getById( reponse.getId() ) );
        MAGAZINES.add( magazineMapper.getMagazine( reponse ) );
    }

    @Test
    public void t4updateMagazineById() {
        Long id = magazineDTOS.get( PARCHMENTS_SIZE ).getId();
        MagazineDTO toupdate = magazineMapper.getMagazineDTO( buildMagazine( PARCHMENTS_SIZE + 1 ) );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<MagazineDTO> request = new HttpEntity<>( toupdate , headers );
        ResponseEntity<MagazineDTO> response = restTemplate.exchange(BASE_PATH + "updatebyid/" + id, HttpMethod.PUT, request, MagazineDTO.class);
        assertThat( response.getBody() ).isEqualToComparingFieldByField( magazineService.getById( id ) );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void t5deleteMagazineById() {
        long todelete = magazineDTOS.get( PARCHMENTS_SIZE ).getId();
        restTemplate.delete( BASE_PATH + "deletebyid/" + todelete );
        MAGAZINES.remove( PARCHMENTS_SIZE );
        magazineService.getById( todelete );
    }
}