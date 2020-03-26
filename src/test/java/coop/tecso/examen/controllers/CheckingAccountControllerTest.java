package coop.tecso.examen.controllers;

import com.google.gson.Gson;
import coop.tecso.examen.dto.CheckingAccountDTO;
import coop.tecso.examen.enums.Currency;
import coop.tecso.examen.handlers.RestExceptionHandler;
import coop.tecso.examen.model.CheckingAccount;
import coop.tecso.examen.model.Movement;
import coop.tecso.examen.service.impl.CheckingAccountServiceImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CheckingAccountController.class)
public class CheckingAccountControllerTest {

    String root;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CheckingAccountController checkingAccountController;
    @MockBean
    private CheckingAccountServiceImpl checkingAccountService;
    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @Before
    public void setUp() throws Exception {
        checkingAccountController = new CheckingAccountController(checkingAccountService);

        // Setting up mockMvc with our own @ControllerAdvice to handle exceptions
        mvc = MockMvcBuilders.standaloneSetup(checkingAccountController)
                .setControllerAdvice(restExceptionHandler)
                .build();

        // Setting up requests root path
        root = checkingAccountController.getClass().getAnnotation(RequestMapping.class).value()[0];
    }

    @Test
    public void getExistingCheckingAccountById() throws Exception {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCurrency(Currency.ARS);
        checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.setBalance(BigDecimal.ZERO);
        checkingAccount.setId(1L);
        doReturn(checkingAccount).when(checkingAccountService).findById(1L);

        mvc.perform(get(root + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.currency", is("ARS")))
                .andExpect(jsonPath("$.balance", is(0)))
                .andReturn();
    }

    @Test
    public void getNonExistentCheckingAccountById() throws Exception {
        String message = "Checking account not found";
        doThrow(new NoSuchElementException(message)).when(checkingAccountService).findById(1L);
        mvc.perform(get(root + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(message)))
                .andReturn();

    }

    @Test
    public void getAllCheckingAccounts() throws Exception {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCurrency(Currency.EUR);
        checkingAccount.setMovements(new ArrayList<>());
        checkingAccount.setBalance(BigDecimal.ZERO);
        checkingAccount.setId(1L);
        when(checkingAccountService.findAll()).thenReturn(Arrays.asList(checkingAccount));

        mvc.perform(get(root + "/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].balance", is(0)))
                .andExpect(jsonPath("$[0].currency", is("EUR")))
                .andReturn();
    }

    @Test
    public void addCheckingAccount() throws Exception {
        CheckingAccountDTO stub = new CheckingAccountDTO();
        stub.setCurrency(Currency.EUR);
        stub.setBalance(BigDecimal.ZERO);

        Gson gson = new Gson();
        String json = gson.toJson(stub);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(root)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void addNullBalanceCheckingAccount() throws Exception {
        CheckingAccountDTO stub = new CheckingAccountDTO();
        stub.setCurrency(Currency.EUR);
        stub.setBalance(null);

        Gson gson = new Gson();
        String json = gson.toJson(stub);

        doThrow(new ConstraintViolationException(new HashSet<>()))
                .when(checkingAccountService).createCheckingAccount(mock(CheckingAccount.class));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(root)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void addNullCurrencyCheckingAccount() throws Exception {
        CheckingAccountDTO stub = new CheckingAccountDTO();
        stub.setCurrency(null);
        stub.setBalance(BigDecimal.ZERO);

        Gson gson = new Gson();
        String json = gson.toJson(stub);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(root)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void deleteCheckingAccountById() throws Exception {
        doNothing().when(checkingAccountService).deleteAccount(1L);

        mvc.perform(delete(root + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void deleteCheckingAccountByIdWithMovements() throws Exception {
        String message = "Cannot delete accounts w/movements";
        doThrow(new IllegalStateException(message)).when(checkingAccountService).deleteAccount(1L);

        mvc.perform(delete(root + "/1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(message)))
                .andReturn();

    }

    @Test
    public void deleteCheckingAccountByINonExistent() throws Exception {
        String message = "Account doesn't exist";
        doThrow(new NoSuchElementException(message)).when(checkingAccountService).deleteAccount(1L);

        mvc.perform(delete(root + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(message)))
                .andReturn();

    }

    @Test
    public void getMovementsByAccountId() throws Exception {

        Movement movementA = new Movement();
        movementA.setAmount(BigDecimal.TEN);
        movementA.setDate(new DateTime());
        movementA.setDescription("ZZZ");

        Movement movementB = new Movement();
        movementB.setAmount(BigDecimal.TEN);
        movementB.setDate(new DateTime());
        movementB.setDescription("ZZZ");

        Movement movementC = new Movement();
        movementC.setAmount(BigDecimal.ONE);
        movementC.setDate(new DateTime());
        movementC.setDescription("ZZZ");

        List<Movement> movementsStub = new ArrayList<>();
        movementsStub.add(movementA);
        movementsStub.add(movementB);
        movementsStub.add(movementC);

        when(checkingAccountService.getMovementsByCheckingAccountId(1L)).thenReturn(movementsStub);

        mvc.perform(get(root + "/1/movements"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].amount", is(10)))
                .andExpect(jsonPath("$[0].description", is("ZZZ")))
                .andReturn();
    }

    @Test
    public void getMovementsByNonExistentAccountId() throws Exception {
        String message = "Account doesn't exist";
        doThrow(new NoSuchElementException(message)).when(checkingAccountService).getMovementsByCheckingAccountId(1L);

        mvc.perform(get(root + "/1/movements"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addMovementToCheckingAccount() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = new Date();
        String json = "{" +
                "\"amount\":10," +
                "\"description\":\"ZZZ\"," +
                "\"date\":\"" + sdf.format(date) + "\"" +
                "}";

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(root + "/1/movement")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);

        doNothing().when(checkingAccountService).addMovementToCheckingAccountById(1L, mock(Movement.class));

        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void addBadMovementsToCheckingAccount() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = new Date();
        String json = "{" +
                "\"amount\":null," +
                "\"description\":\"ZZZ\"," +
                "\"date\":\"" + sdf.format(date) + "\"" +
                "}";

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(root + "/1/movement")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);

        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        json = "{" +
                "\"amount\":10," +
                "\"description\": null," +
                "\"date\":\"" + sdf.format(date) + "\"" +
                "}";

        builder = MockMvcRequestBuilders.put(root + "/1/movement")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);

        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        json = "{" +
                "\"amount\":10," +
                "\"description\":\"ZZZ\"," +
                "\"date\": null" +
                "}";

        builder = MockMvcRequestBuilders.put(root + "/1/movement")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);

        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void addMovementToNonExistentCheckingAccount() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = new Date();

        String json = "{\"amount\":10,\"description\":\"ZZZ\",\"date\":\"2020-03-25T18:00:12.961-0000\"}";


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(root + "/500/movement")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);

        String message = "Account doesn't exist";
        doThrow(new NoSuchElementException(message)).when(checkingAccountService)
                .addMovementToCheckingAccountById(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Movement.class));


        mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(message)))
                .andReturn();
    }
}