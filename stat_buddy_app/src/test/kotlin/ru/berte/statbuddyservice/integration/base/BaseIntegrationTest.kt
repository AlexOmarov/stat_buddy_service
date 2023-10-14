package ru.berte.statbuddyservice.integration.base

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ResourceLoader
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import ru.berte.statbuddyservice.infrastructure.config.ServiceProps

@Testcontainers
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BaseIntegrationTest {
    private val logger = LoggerFactory.getLogger(BaseIntegrationTest::class.java)

    // Util system beans

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var props: ServiceProps

    @Autowired
    lateinit var dbClient: DatabaseClient

    @Autowired
    lateinit var resourceLoader: ResourceLoader

    // Spy beans

    @BeforeAll
    fun setUp() {
        logger.info("setUp")
    }

    @AfterAll
    fun tearDown() {
        logger.info("tearDown")
    }

    @BeforeEach
    fun beforeEach() {
        logger.info("beforeEach")
    }

    @AfterEach
    fun afterEach() {
        logger.info("afterEach")
    }

    companion object {
        private val postgresql = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withReuse(true)
            start()
        }

        init {
            System.setProperty("spring.flyway.url", postgresql.jdbcUrl)
            System.setProperty("spring.flyway.user", postgresql.username)
            System.setProperty("spring.flyway.password", postgresql.password)
            System.setProperty(
                "spring.r2dbc.url",
                "r2dbc:postgresql://${postgresql.host}:${postgresql.firstMappedPort}/${postgresql.databaseName}"
            )
            System.setProperty("spring.r2dbc.username", postgresql.username)
            System.setProperty("spring.r2dbc.password", postgresql.password)
        }
    }
}
