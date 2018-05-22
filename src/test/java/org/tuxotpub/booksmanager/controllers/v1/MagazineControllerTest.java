package org.tuxotpub.booksmanager.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.services.parchments.MagazineService;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.tuxotpub.booksmanager.TestHelper.*;
import static org.tuxotpub.booksmanager.controllers.v1.MagazineController.BASE_PATH;

/**
 * Created by tuxsamo.
 */
public class MagazineControllerTest {

    @Mock
    private MagazineService magazineService;

    @InjectMocks
    private MagazineController magazineController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(magazineController).build();
    }

    @Test
    public void getAllMagazines() throws Exception {
        List<MagazineDTO> magazinesDTO = Arrays.asList( MAGAZINEDTO1, MAGAZINEDTO2 );
        when(magazineService.getAll()).thenReturn(magazinesDTO);

        mockMvc.perform(get(BASE_PATH + "all").contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.magazineDTOS", hasSize( magazinesDTO.size() ) ) );
    }

    @Test
    public void createMagazine() throws Exception {

        when( magazineService.create( MAGAZINEDTO1 ) ).thenReturn( MAGAZINEDTO1 );

        mockMvc.perform(post( BASE_PATH + "create" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(toJsonString(MAGAZINEDTO1))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect( jsonPath( "$.id", equalTo( MAGAZINEDTO1.getId().intValue() ) ) )
                .andExpect( jsonPath( "$.isbn", equalTo( MAGAZINEDTO1.getIsbn() ) ) )
                .andExpect( jsonPath( "$.title", equalTo( MAGAZINEDTO1.getTitle() ) ) )
                .andExpect(jsonPath("$.releaseDate[0]", equalTo(MAGAZINEDTO1.getReleaseDate().getYear())))
                .andExpect(jsonPath("$.releaseDate[1]", equalTo(MAGAZINEDTO1.getReleaseDate().getMonthValue())))
                .andExpect(jsonPath("$.releaseDate[2]", equalTo(MAGAZINEDTO1.getReleaseDate().getDayOfMonth())))
                .andExpect( jsonPath( "$.url", equalTo( MAGAZINEDTO1.getUrl() ) ) );
    }

    @Test
    public void updateMagazineById() throws Exception {
        when(magazineService.updateById(anyLong(),any(MagazineDTO.class))).thenReturn(MAGAZINEDTO1);

        mockMvc.perform(put(BASE_PATH + "updatebyid/" + MAGAZINEDTO1.getId(), MAGAZINEDTO1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(MAGAZINEDTO1)))
                .andExpect(status().isOk())
                .andExpect( jsonPath( "$.id", equalTo( MAGAZINEDTO1.getId().intValue() ) ) )
                .andExpect( jsonPath( "$.isbn", equalTo( MAGAZINEDTO1.getIsbn() ) ) )
                .andExpect( jsonPath( "$.title", equalTo( MAGAZINEDTO1.getTitle() ) ) )
                .andExpect(jsonPath("$.releaseDate[0]", equalTo(MAGAZINEDTO1.getReleaseDate().getYear())))
                .andExpect(jsonPath("$.releaseDate[1]", equalTo(MAGAZINEDTO1.getReleaseDate().getMonthValue())))
                .andExpect(jsonPath("$.releaseDate[2]", equalTo(MAGAZINEDTO1.getReleaseDate().getDayOfMonth())))
                .andExpect( jsonPath( "$.url", equalTo( MAGAZINEDTO1.getUrl() ) ) );
    }


    @Test
    public void deleteMagazineById() throws Exception {

        mockMvc.perform( delete( BASE_PATH + "deletebyid/" + MAGAZINEDTO1.getId() )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
    }
}