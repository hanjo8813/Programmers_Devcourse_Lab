package org.prgrms.kdt.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerNamedRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerNamedRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.customer"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            // MySql
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("root1234")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        // dataSource??? ???????????? -> DataSourceTransactionManager???????????? -> JdbcCustomerNamedRepository?????? ????????????
        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    JdbcCustomerNamedRepository jdbcCustomerRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user.mail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("????????? ????????? ??? ??????.")
    public void testInsert() {

//        try{
//            jdbcCustomerRepository.insert(newCustomer);
//        }catch (BadSqlGrammarException e){
//            logger.error("{}", e.getSQLException().getErrorCode());
//        }

        jdbcCustomerRepository.insert(newCustomer);
        var retrievedCustomer = jdbcCustomerRepository.findById(newCustomer.getCustomerId());
        // ??? ?????????????
        assertThat(retrievedCustomer.isEmpty(), is(false));
        // customer ????????? ?????? ?????? ??? ??????????
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }


    @Test
    @Order(3)
    @DisplayName("?????? ????????? ????????? ??? ??????.")
    public void testFindAll() throws InterruptedException {
        var customers = jdbcCustomerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("???????????? ????????? ????????? ??? ??????.")
    public void testFindByName() {
        var customer = jdbcCustomerRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(), is(false));

        var unknown = jdbcCustomerRepository.findByName("unknown_user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("??????????????? ????????? ????????? ??? ??????.")
    public void testFindByEmail() {
        var customer = jdbcCustomerRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(), is(false));

        var unknown = jdbcCustomerRepository.findByEmail("unknownr@mail.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("????????? ????????? ??? ??????.")
    public void testUpdate() {
        newCustomer.changeName("updated-user");
        jdbcCustomerRepository.update(newCustomer);

        var allCustomers = jdbcCustomerRepository.findAll();
        assertThat(allCustomers, hasSize(1));
        assertThat(allCustomers, everyItem(samePropertyValuesAs(newCustomer)));

        var retrievedCustomer = jdbcCustomerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(7)
    @DisplayName("???????????? ?????????")
    public void testTransaction() {
//        var prevOne = jdbcCustomerRepository.findById(newCustomer.getCustomerId());
//        assertThat(prevOne.isEmpty(), is(false));
//
//        var newOne = new Customer(UUID.randomUUID(), "a", "a@mail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
//        var insertedNewOne = jdbcCustomerRepository.insert(newOne);
//
//        try {
//            jdbcCustomerRepository.testTransaction(new Customer(
//                    insertedNewOne.getCustomerId(),
//                    "b",
//                    prevOne.get().getEmail(),
//                    newOne.getCreatedAt()
//            ));
//        } catch (DataAccessException e) {
//        }
//
//        var maybeNewOne = jdbcCustomerRepository.findById(insertedNewOne.getCustomerId());
//
//        assertThat(maybeNewOne.isEmpty(), is(false));
//        assertThat(maybeNewOne.get(), samePropertyValuesAs(newOne));

    }


}