package org.tuxotpub.booksmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.MagazineMapper;
import org.tuxotpub.booksmanager.controllers.v1.MagazineController;
import org.tuxotpub.booksmanager.services.parchments.MagazineService;
import org.tuxotpub.booksmanager.services.parchments.MagazineServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tuxotpub.booksmanager.bootstrap.BootstrapData.*;

/**
 * Created by tuxsamo.
 *
 * Integration test
 */

@Slf4j
public class MagazineServiceImplTestIT extends BaseServiceTestIT{

    private MagazineMapper magazineMapper = MagazineMapper.INSTANCE;

    //private ParchmentService<MagazineDTO, Magazine> magazineService;
    private MagazineService magazineService;
    private List<MagazineDTO> magazineDTOS = new ArrayList<>();

    @BeforeClass
    public static void initTest(){
        log.debug("Init test of : {}", MagazineServiceImplTestIT.class.getSimpleName());
    }

    @Before
    public void setUp() throws Exception {
        bootStrapData();
        magazineService = new MagazineServiceImpl(magazineMapper, magazineRepository);
        MAGAZINES.forEach(
                magazine -> {
                    MagazineDTO magazineDTO = magazineMapper.getMagazineDTO(magazine);
                    magazineDTO.setUrl(MagazineController.FINDBYID_PATH + magazineDTO.getId());
                    magazineDTOS.add(magazineDTO);
                });
    }

    @Test
    public void getAllMagazines() throws Exception {
        assertThat( magazineService.getAll() ).containsExactlyInAnyOrderElementsOf( magazineDTOS );
    }

    @Test
    public void getMagazineById() throws Exception {
        assertThat( magazineService.getById( magazineDTOS.get(0).getId() )).isEqualToComparingFieldByField( magazineDTOS.get(0) );
    }

    @Test
    public void getMagazineByIsbn() throws Exception {
        assertThat( magazineService.getByIsbn( magazineDTOS.get(0).getIsbn() )).isEqualToComparingFieldByField( magazineDTOS.get(0) );
    }

    @Test
    public void createMagazine() throws Exception {
        MagazineDTO toCreate = magazineMapper.getMagazineDTO( buildMagazine( PARCHMENTS_SIZE ));
        toCreate = magazineService.create( toCreate );
        assertThat( toCreate ).isEqualToComparingFieldByField( magazineService.getById( toCreate.getId() ) );
    }

    @Test
    public void updateMagazineById() throws Exception {
        Long id = magazineDTOS.get( 0 ).getId();
        MagazineDTO toUpdate = magazineMapper.getMagazineDTO( buildMagazine(PARCHMENTS_SIZE + 1 ) );
        toUpdate = magazineService.updateById( id, toUpdate );
        assertThat( toUpdate ).isEqualToComparingFieldByField( magazineService.getById( id ) );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteMagazineById() throws Exception {
        Long idToDelete = MAGAZINES.get( 0 ).getId();
        magazineService.deleteById( idToDelete );
        magazineService.getOne( idToDelete );
    }
}