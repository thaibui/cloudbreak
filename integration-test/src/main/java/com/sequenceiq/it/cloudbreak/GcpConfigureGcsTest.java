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
import com.sequenceiq.it.util.ResourceUtil;

public class GcpConfigureGcsTest extends AbstractCloudbreakIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudbreakUtil.class);

    @Value("${integrationtest.gcpcredential.projectId}")
    private String defaultProjectId;

    @Value("${integrationtest.gcpcredential.serviceAccountId}")
    private String defaultServiceAccountId;

    @Value("${integrationtest.gcpcredential.p12File}")
    private String defaultP12File;

    @Value("${integrationtest.defaultPrivateKeyFile}")
    private String defaultPrivateKeyFile;

    @Test
    @Parameters({ "bucketName", "projectId", "serviceAccountId", "serviceAccountPrivateKeyP12File" })
    public void testGCPFileSystem(@Optional("")String bucketName, @Optional("")String projectId, @Optional("")String serviceAccountId,
            @Optional("")String serviceAccountPrivateKeyP12File) throws Exception {
        //GIVEN
        projectId = StringUtils.hasLength(projectId) ? projectId : defaultProjectId;
        serviceAccountId = StringUtils.hasLength(serviceAccountId) ? serviceAccountId : defaultServiceAccountId;
        serviceAccountPrivateKeyP12File = StringUtils.hasLength(serviceAccountPrivateKeyP12File) ? serviceAccountPrivateKeyP12File : defaultP12File;
        String serviceAccountPrivateKey = ResourceUtil.readBase64EncodedContentFromResource(applicationContext, serviceAccountPrivateKeyP12File);
        // WHEN

        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("projectId", projectId);
        requestProperties.put("serviceAccountEmail", serviceAccountId);
        requestProperties.put("privateKeyEncoded", serviceAccountPrivateKey);
        requestProperties.put("defaultBucketName", bucketName);

        FileSystemRequest fsRequest = new FileSystemRequest();
        fsRequest.setProperties(requestProperties);
        fsRequest.setType(FileSystemType.valueOf("GCS"));
        fsRequest.setName("it-gcs-fs");
        fsRequest.setDefaultFs(false);
        // THEN
        getItContext().putContextParam(GCSFSREQUEST, fsRequest);

//        Boolean resp = false;
//        Session session = null;
//        ChannelExec executor = null;
//        String host = "35.187.30.198";
//        try {
//            JSch jsch = new JSch();
//            jsch.addIdentity(defaultPrivateKeyFile);
//
//            Properties config = new Properties();
//            config.put("StrictHostKeyChecking", "no");
//
//            session = jsch.getSession("cloudbreak", host, 22);
//            session.setConfig(config);
//            session.connect(10000);
//
//            executor = (ChannelExec) session.openChannel("exec");
//            executor.setCommand("hadoop version");
//            executor.setPty(true);
//            executor.connect(10000);
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(executor.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
////                if (line.startsWith(file + ": empty")) {
////                    resp = true;
////                }
//                LOGGER.info(line);
//            }
//        } catch (JSchException | IOException e) {
//            Assert.fail(e.getMessage());
//        } finally {
//            if (executor != null) {
//                executor.disconnect();
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//        }
    }



}
