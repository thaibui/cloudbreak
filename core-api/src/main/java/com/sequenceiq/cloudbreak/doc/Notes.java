package com.sequenceiq.cloudbreak.doc;

public class Notes {

    public static final String BLUEPRINT_NOTES = "Ambari Blueprints are a declarative "
            + "definition of a Hadoop cluster. With a Blueprint, you specify a stack, the component layout "
            + "and the configurations to materialize a Hadoop cluster instance. Hostgroups defined in blueprints "
            + "can be associated to different templates, thus you can spin up a highly available cluster "
            + "running on different instance types. This will give you the option to group your "
            + "Hadoop services based on resource needs (e.g. high I/O, CPU or memory) "
            + "and create an infrastructure which fits your workload best.";
    public static final String TEMPLATE_NOTES = "A template gives developers and systems administrators "
            + "an easy way to create and manage a collection of cloud infrastructure related resources, "
            + "maintaining and updating them in an orderly and predictable fashion. Templates are cloud specific "
            + "- and on top of the infrastructural setup they collect the information such as the used machine images, "
            + "the datacenter location, instance types, and can capture and control region-specific infrastructure variations. "
            + "We support heterogenous clusters - this one Hadoop cluster can be built by combining different templates.";
    public static final String CONSTRAINT_NOTES = "A constraint template tells Cloudbreak the resource constraints "
            + "(cpu, memory, disk) of the Ambari containers that will be deployed to the cluster. A constraint template must be created on"
            + "environments where there is no one-to-one mapping between containers and nodes, like YARN.";
    public static final String CREDENTIAL_NOTES = "Cloudbreak is launching Hadoop clusters on the user's behalf - "
            + "on different cloud providers. One key point is that Cloudbreak does not store your "
            + "Cloud provider account details (such as username, password, keys, private SSL certificates, etc). "
            + "We work around the concept that Identity and Access Management is fully controlled by you - the end user. "
            + "The Cloudbreak deployer is purely acting on behalf of the end user - without having access to the user's account.";
    public static final String STACK_NOTES = "Stacks are template instances - a running cloud infrastructure "
            + "created based on a template. Stacks are always launched on behalf of a cloud user account. "
            + "Stacks support a wide range of resources, allowing you to build a highly available, reliable, "
            + "and scalable infrastructure for your application needs.";
    public static final String CLUSTER_NOTES = "Clusters are materialised Hadoop services on a given infrastructure. "
            + "They are built based on a Blueprint (running the components and services specified) and on "
            + "a configured infrastructure Stack. Once a cluster is created and launched, it can be used the usual"
            + " way as any Hadoop cluster. We suggest to start with the Cluster's Ambari UI for an overview of your cluster.";
    public static final String GATEWAY_NOTES = "Gateway is an Apache Knox Gateway, which is an Application Gateway for interacting with REST APIs and"
            + "UIs of Apache Hadoop deployments. Provides Authentication and other services.";
    public static final String CLUSTER_TEMPLATE_NOTES = "Cluster templates are stored cluster configurations, which configurations are reusable any time";
    public static final String RECIPE_NOTES = "Recipes are basically script extensions to a cluster that run on a set of nodes"
            + " before or after the Ambari cluster installation.";
    public static final String USAGE_NOTES = "Cloudbreak gives you an up to date overview of cluster usage based "
            + "on different filtering criteria (start/end date, users, providers, region, etc)";
    public static final String EVENT_NOTES = "Events are used to track stack creation initiated by cloudbreak users. "
            + "Events are generated by the backend when resources requested by the user become available or unavailable";
    public static final String NETWORK_NOTES = "Provider specific network settings could be configured by using Network resources.";
    public static final String SECURITY_GROUP_NOTES = "Different inbound security rules(group) could be configured by using SecurityGroup resources "
            + "and a group could be assigned to any Stack(cluster).";
    public static final String ACCOUNT_PREFERENCES_NOTES = "Account related preferences that could be managed by the account admins and different "
            + "restrictions could be added to Cloudbreak resources.";
    public static final String AMBARI_NOTES = "Ambari is used to provision the Hadoop clusters.";

    public static final String USER_NOTES = "Users can be invited under an account by the administrator, and all resources "
            + "(e.g. resources, networks, blueprints, credentials, clusters) can be shared across account users";
    public static final String TOPOLOGY_NOTES = "A topology gives system administrators an easy way to associate compute nodes with data centers and racks.";
    public static final String RDSCONFIG_NOTES = "An RDS Configuration describe a connection to an external Relational Database Service "
            + "that can be used as the Hive Metastore.";
    public static final String PROXY_CONFIG_NOTES = "An proxy Configuration describe a connection to an external proxy server which provides internet access "
            + "cluster members. It's applied for package manager and Ambari too";
    public static final String MANAGEMENT_PACK_NOTES = "An Apache Ambari Management Pack (Mpack) can bundle multiple service definitions, stack definitions, "
            + "stack add-on service definitions, view definitions services so that releasing these artifacts don’t enforce an Apache Ambari release.";

    public static final String LDAP_CONFIG_NOTES = "LDAP server integration enables the user to provide"
            + " a central place to store usernames and passwords for the users of his/her clusters.";
    public static final String CONNECTOR_NOTES = "Each cloud provider has it's own specific resources like instance types and disk types."
            + " These endpoints are collecting them.";
    public static final String SETTINGS_NOTES = "Collecting Cloudbreak specific resource settings.";
    public static final String SUBSCRIPTION_NOTES = "Accepting client subscriptions to notification events.";
    public static final String FAILURE_REPORT_NOTES = "Endpoint to report the failed nodes in the given cluster. If recovery mode for the node's hostgroup "
            + "is AUTO then autorecovery would be started. If recovery mode for the node's hostgroup is MANUAL, the nodes will be marked as unhealthy.";
    public static final String CLUSTER_REPAIR_NOTES = "Removing the failed nodes and starting new nodes to substitute them.";
    public static final String SMARTSENSE_SUBSCRIPTION_NOTES = "SmartSense subscriptions could be configured.";
    public static final String FLEX_SUBSCRIPTION_NOTES = "Flex subscriptions could be configured.";
    public static final String IMAGE_CATALOG_NOTES = "Provides an interface to determine available Virtual Machine images for the given version of Cloudbreak.";
    public static final String SECURITY_RULE_NOTES = "Security Rules operations";
    public static final String REPOSITORY_CONFIGS_VALIDATION_NOTES = "Repository configs validation related operations";
    public static final String RETRY_STACK_NOTES = "Failed or interrupted stack and cluster operations can be retried, after the cause of the failure "
            + "was eliminated. The operations will continue at the state, where the previous process failed.";
    private Notes() {
    }
}
