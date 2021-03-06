package com.sequenceiq.cloudbreak.cloud.template;

import java.util.List;

import com.sequenceiq.cloudbreak.cloud.context.AuthenticatedContext;
import com.sequenceiq.cloudbreak.cloud.model.CloudResource;
import com.sequenceiq.cloudbreak.cloud.model.CloudResourceStatus;
import com.sequenceiq.cloudbreak.cloud.template.context.ResourceBuilderContext;

/**
 * Resource creation and deletion requests sent to the provider later needs status checking. The implementation supposed to provide a defined
 * {@link com.sequenceiq.cloudbreak.cloud.model.ResourceStatus} to track the progress. If the resource status reaches a permanent state the flow will continue.
 */
public interface ResourceChecker<C extends ResourceBuilderContext> {

    /**
     * Checks the status of the resource creation/deletion. This method will be called as long as the status is in <b>TRANSIENT</b> state with a timed delay.
     *
     * @param context   Generic context object passed along with the flow to all methods. It is created by the {@link ResourceContextBuilder}.
     * @param auth      Authenticated context is provided to be able to send the requests to the cloud provider.
     * @param resources List of resources to be checked.
     * @return Returns the status of the requested resources.
     */
    List<CloudResourceStatus> checkResources(C context, AuthenticatedContext auth, List<CloudResource> resources);
}
