package org.ruthie.exec.rbc.integration;

import org.junit.runner.RunWith;
import org.ruthie.exec.rbc.model.FruitType;
import org.ruthie.exec.rbc.server.EmbeddedServer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedServer.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class AbstractSteps {
    protected static RestTestHelper restTestHelper = new RestTestHelper();
}