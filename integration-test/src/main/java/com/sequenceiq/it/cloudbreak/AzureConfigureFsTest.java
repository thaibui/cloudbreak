package com.sequenceiq.it.cloudbreak;

import static com.sequenceiq.it.cloudbreak.CloudbreakITContextConstants.GCSFSREQUEST;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sequenceiq.cloudbreak.api.model.FileSystemRequest;
import com.sequenceiq.cloudbreak.api.model.FileSystemType;


public class AzureConfigureFsTest extends AbstractCloudbreakIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AzureConfigureFsTest.class);

    @Value("${integrationtest.filesystemconfig.accountKeyWASB}")
    private String defaultAccountKey;

    @Test
    @Parameters({"accountName", "accountName"})
    public void testGCPFileSystem(String accountName, @Optional("") String accountKey) throws Exception {
        //GIVEN
        accountKey = StringUtils.hasLength(accountKey) ? accountKey : defaultAccountKey;
        // WHEN
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("accountName", accountName);
        requestProperties.put("accountKey", accountKey);
        FileSystemRequest fsRequest = new FileSystemRequest();
        fsRequest.setProperties(requestProperties);
        fsRequest.setType(FileSystemType.valueOf("WASB"));
        fsRequest.setName("it-azure-fs");
        fsRequest.setDefaultFs(false);
        // THEN
        getItContext().putContextParam(GCSFSREQUEST, fsRequest);
    }
}
