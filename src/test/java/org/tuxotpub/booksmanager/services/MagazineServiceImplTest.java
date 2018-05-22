package org.tuxotpub.booksmanager.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazinesDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.MagazineMapper;
import org.tuxotpub.booksmanager.entities.Magazine;
import org.tuxotpub.booksmanager.repositories.parchments.MagazineRepository;
import org.tuxotpub.booksmanager.services.parchments.MagazineServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.tuxotpub.booksmanager.TestHelper.*;

/**
 * Created by tuxsamo.
 *
 * Assertj and mockito BDD style testing
 */
public class MagazineServiceImplTest {

    @Mock
    private MagazineRepository magazineRepository;

    private MagazineServiceImpl magazineService;
    //private ParchmentService<MagazineDTO, Magazine> magazineService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        magazineService = new MagazineServiceImpl(MagazineMapper.INSTANCE, magazineRepository);
    }

    @Test
    public void getAllMagazines() {
        List<Magazine> magazines = Arrays.asList(MAGAZINE1, MAGAZINE2);
        given(magazineRepository.findAll()).willReturn(magazines);
        MagazinesDTO magazinesDTO = new MagazinesDTO(magazineService.getAll());
        then(magazineRepository).should(times(1)).findAll();
        assertThat(magazinesDTO.getMagazineDTOS().size()).isEqualTo(2);
    }

    @Test
    public void getMagazineById() {
        given(magazineRepository.findWithAuthorsByIdEager(anyLong())).willReturn(Optional.of(MAGAZINE1));
        MagazineDTO magazineDTO = magazineService.getById(1L);
        then(magazineRepository).should(times(1)).findWithAuthorsByIdEager(anyLong());
        assertThat(magazineDTO).isEqualToComparingFieldByField(MAGAZINEDTO1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getMagazineByIdNotFound() throws Exception {
        given(magazineRepository.findWithAuthorsByIdEager(anyLong())).willReturn(Optional.empty());
        magazineService.getById(1L);
    }

    @Test
    public void createMagazine() {
        given(magazineRepository.save(any(Magazine.class))).willReturn(MAGAZINE1);
        MagazineDTO savedMagazineDTO = magazineService.create(MAGAZINEDTO1);
        then(magazineRepository).should().save(any(Magazine.class));
        assertThat(savedMagazineDTO).isEqualToComparingFieldByField(MAGAZINEDTO1);
    }

    @Test
    public void updateMagazineById() {
        given(magazineRepository.findWithAuthorsByIdEager(anyLong())).willReturn(Optional.of(MAGAZINE1));
        given(magazineRepository.save(any(Magazine.class))).willReturn(MAGAZINE1);
        MagazineDTO savedMagazinesDTO = magazineService.updateById(MAGAZINEDTO1.getId(), MAGAZINEDTO1);
        then(magazineRepository).should().save(any(Magazine.class));
        assertThat(savedMagazinesDTO).isEqualToComparingFieldByField(MAGAZINEDTO1);
    }

    @Test
    public void deleteMagazineById() {
        magazineRepository.deleteById(1L);
        verify(magazineRepository, times(1)).deleteById(1L);
    }
}