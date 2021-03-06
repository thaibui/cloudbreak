package com.sequenceiq.it.cloudbreak.newway.listener;

import static com.sequenceiq.it.cloudbreak.newway.log.Log.log;

import java.util.List;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sequenceiq.cloudbreak.structuredevent.event.StructuredEvent;
import com.sequenceiq.it.IntegrationTestContext;
import com.sequenceiq.it.cloudbreak.newway.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.newway.GherkinTest;
import com.sequenceiq.it.cloudbreak.newway.GherkinTestContext;
import com.sequenceiq.it.cloudbreak.newway.Stack;

public class StructuredEventsReporterOnFailingCluster extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult testResult) {
        super.onTestFailure(testResult);
        GherkinTestContext testInstance = new GherkinTestContext((GherkinTest) testResult.getInstance());
        IntegrationTestContext integrationTestContext = testInstance.getIntegrationTestContext();
        Stack stack = Stack.getTestContextStack(Stack.STACK).apply(integrationTestContext);
        CloudbreakClient client = CloudbreakClient.getTestContextCloudbreakClient(CloudbreakClient.CLOUDBREAK_CLIENT).apply(integrationTestContext);
        if (stack != null && client != null && stack.getResponse() != null) {
            List<StructuredEvent> events = client.getCloudbreakClient().eventEndpoint().getStructuredEvents(stack.getResponse().getId());
            String json = null;
            try {
                json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(events);
            } catch (JsonProcessingException e) {
                log("Failed to print structured events json");
            }
            if (json != null) {
                log("Structured events for failing test:");
                log(json);
            }
        }
    }
}
