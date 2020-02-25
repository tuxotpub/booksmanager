package org.tuxotpub.booksmanager.controllers;

import org.tuxotpub.booksmanager.BaseTest;

class AuthorControllerWithHalTest extends BaseTest {

    //TODO upgrade to 2.2.4
    /*@Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;
    private MockMvc mockMvc;
    private JacksonTester<Author> jsonAuthor;
    private Author author1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setControllerAdvice(new RestResponseExceptionHandler()).build();

        author1 = TestHelper.GET_NEW_AUTHOR(1L);
        TestHelper.SETUP_ENTITY(author1, 1L);
    }


    @Test
    public void getByEmail() throws Exception {
        when(authorService.getByEmail(author1.getEmail())).thenReturn(author1);

        ResultActions result = mockMvc.perform(
                get(BASE_PATH + "/withhal/" + author1.getEntityId()).accept(MediaTypes.HAL_JSON_VALUE)
        ).andExpect(status().isOk());

        verifyWithHal(result, author1);
    }



    private void verifyWithHal(final ResultActions action, Author author) throws Exception {
        String server = "http://" + action.andReturn().getRequest().getServerName();

        action.andExpect( jsonPath("$.entityId", equalTo( author.getEntityId().intValue() ) ) )
                .andExpect( jsonPath("$.name", equalTo( author.getName() ) ) )
                .andExpect( jsonPath("$.surname", equalTo( author.getSurname() ) ))
                .andExpect( jsonPath("links[0].rel", equalTo("self") ) )
                .andExpect( jsonPath("links[0].href", equalTo(server + BASE_PATH + "/" + author.getEntityId() ) ) )
                .andExpect( jsonPath("links[1].rel", equalTo("self") ) )
                .andExpect( jsonPath("links[1].href", equalTo( server + BASE_PATH ) ) )
                .andExpect( jsonPath("links[2].rel", equalTo("self") ) )
                .andExpect( jsonPath("links[2].href", equalTo(server + BASE_PATH + "/email/" + author.getEmail() ) ) );
    }*/

}