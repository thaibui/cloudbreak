package com.sequenceiq.cloudbreak.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.sequenceiq.cloudbreak.FileReaderUtil;
import com.sequenceiq.cloudbreak.api.model.ExposedService;
import com.sequenceiq.cloudbreak.blueprint.BlueprintTextProcessor;

public class AmbariHaComponentFilterTest {

    private final AmbariHaComponentFilter underTest = new AmbariHaComponentFilter();

    @Test
    public void testGetHaComponents() {
        String blueprintText = FileReaderUtil.readResourceFile(this, "ha-components.bp");
        Set<String> haComponents = underTest.getHaComponents(new BlueprintTextProcessor(blueprintText));

        assertEquals(2, haComponents.size());
        assertTrue(haComponents.contains(ExposedService.ATLAS.getServiceName()));
        assertTrue(haComponents.contains(ExposedService.RESOURCEMANAGER_WEB.getServiceName()));
    }
}