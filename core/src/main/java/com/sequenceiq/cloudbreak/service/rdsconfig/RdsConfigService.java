package com.sequenceiq.cloudbreak.service.rdsconfig;

import com.google.common.base.Preconditions;
import com.sequenceiq.cloudbreak.api.model.ResourceStatus;
import com.sequenceiq.cloudbreak.api.model.rds.RdsType;
import com.sequenceiq.cloudbreak.common.model.user.IdentityUser;
import com.sequenceiq.cloudbreak.common.model.user.IdentityUserRole;
import com.sequenceiq.cloudbreak.controller.exception.BadRequestException;
import com.sequenceiq.cloudbreak.controller.exception.NotFoundException;
import com.sequenceiq.cloudbreak.domain.RDSConfig;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.repository.ClusterRepository;
import com.sequenceiq.cloudbreak.repository.RdsConfigRepository;
import com.sequenceiq.cloudbreak.service.AuthorizationService;
import com.sequenceiq.cloudbreak.util.NameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RdsConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RdsConfigService.class);

    @Inject
    private RdsConfigRepository rdsConfigRepository;

    @Inject
    private ClusterRepository clusterRepository;

    @Inject
    private AuthorizationService authorizationService;

    public Set<RDSConfig> retrievePrivateRdsConfigs(IdentityUser user) {
        return rdsConfigRepository.findForUser(user.getUserId());
    }

    public RDSConfig getPrivateRdsConfig(String name, IdentityUser user) {
        RDSConfig rdsConfig = rdsConfigRepository.findByNameInUser(name, user.getUserId());
        if (rdsConfig == null) {
            throw new NotFoundException(String.format("RDS configuration '%s' not found.", name));
        }
        return rdsConfig;
    }

    public RDSConfig getPublicRdsConfig(String name, IdentityUser user) {
        RDSConfig rdsConfig = rdsConfigRepository.findByNameBasedOnAccount(name, user.getAccount(), user.getUserId());
        if (rdsConfig == null) {
            throw new NotFoundException(String.format("RDS configuration '%s' not found.", name));
        }
        return rdsConfig;
    }

    public RDSConfig getByName(String name, IdentityUser user) {
        RDSConfig rdsConfig = rdsConfigRepository.findOneByName(name, user.getAccount());
        if (rdsConfig == null) {
            throw new NotFoundException(String.format("RDS configuration '%s' not found.", name));
        }
        authorizationService.hasReadPermission(rdsConfig);
        return rdsConfig;
    }

    public Set<RDSConfig> retrieveAccountRdsConfigs(IdentityUser user) {
        return user.getRoles().contains(IdentityUserRole.ADMIN) ? rdsConfigRepository.findAllBasedOnAccount(user.getAccount())
                : rdsConfigRepository.findPublicInAccountForUser(user.getUserId(), user.getAccount());
    }

    public RDSConfig get(Long id) {
        RDSConfig rdsConfig = rdsConfigRepository.findById(id);
        if (rdsConfig == null) {
            throw new NotFoundException(String.format("RDS configuration '%s' not found.", id));
        }
        authorizationService.hasReadPermission(rdsConfig);
        return rdsConfig;
    }

    public void delete(Long id, IdentityUser user) {
        RDSConfig rdsConfig = rdsConfigRepository.findByIdInAccount(id, user.getAccount());
        if (rdsConfig == null) {
            throw new NotFoundException(String.format("RDS configuration '%s' not found.", id));
        }
        delete(rdsConfig);
    }

    public void delete(String name, IdentityUser user) {
        RDSConfig rdsConfig = rdsConfigRepository.findByNameBasedOnAccount(name, user.getAccount(), user.getUserId());
        if (rdsConfig == null) {
            throw new NotFoundException(String.format("RDS configuration '%s' not found.", name));
        }
        delete(rdsConfig);
    }

    public RDSConfig create(IdentityUser user, RDSConfig rdsConfig) {
        LOGGER.debug("Creating RDS configuration: [User: '{}', Account: '{}']", user.getUsername(), user.getAccount());
        rdsConfig.setOwner(user.getUserId());
        rdsConfig.setAccount(user.getAccount());
        return rdsConfigRepository.save(rdsConfig);
    }

    public RDSConfig create(RDSConfig rdsConfig) {
        Preconditions.checkNotNull(rdsConfig.getOwner(), "Owner cannot be null");
        Preconditions.checkNotNull(rdsConfig.getAccount(), "Account cannot be null");
        LOGGER.debug("Creating RDS configuration: [User: '{}', Account: '{}']", rdsConfig.getOwner(), rdsConfig.getAccount());
        return rdsConfigRepository.save(rdsConfig);
    }

    public RDSConfig createIfNotExists(IdentityUser user, RDSConfig rdsConfig) {
        try {
            return getPrivateRdsConfig(rdsConfig.getName(), user);
        } catch (NotFoundException ignored) {
            return create(user, rdsConfig);
        }
    }

    public Set<RDSConfig> findByClusterId(String user, String account, Long clusterId) {
        return rdsConfigRepository.findByClusterId(user, account, clusterId);
    }

    public RDSConfig findByClusterIdAndType(String user, String account, Long clusterId, RdsType rdsType) {
        return rdsConfigRepository.findByClusterIdAndType(user, account, clusterId, rdsType.name());
    }

    public Set<RDSConfig> findUserManagedByClusterId(String user, String account, Long clusterId) {
        return rdsConfigRepository.findUserManagedByClusterId(user, account, clusterId);
    }

    public void deleteDefaultRdsConfigs(Set<RDSConfig> rdsConfigs) {
        rdsConfigs.stream().filter(rdsConfig -> ResourceStatus.DEFAULT == rdsConfig.getStatus()).forEach(this::setStatusToDeleted);
    }

    private void delete(RDSConfig rdsConfig) {
        authorizationService.hasWritePermission(rdsConfig);
        checkRdsConfigNotAssociated(rdsConfig);
        if (ResourceStatus.USER_MANAGED.equals(rdsConfig.getStatus())) {
            rdsConfigRepository.delete(rdsConfig);
        } else {
            setStatusToDeleted(rdsConfig);
        }
    }

    private void checkRdsConfigNotAssociated(RDSConfig rdsConfig) {
        List<Cluster> clustersWithProvidedRds = new ArrayList<>(clusterRepository.findAllClustersByRDSConfig(rdsConfig.getId()));
        if (!clustersWithProvidedRds.isEmpty()) {
            if (clustersWithProvidedRds.size() > 1) {
                String clusters = clustersWithProvidedRds
                        .stream()
                        .map(Cluster::getName)
                        .collect(Collectors.joining(", "));
                throw new BadRequestException(String.format(
                        "There are clusters associated with RDS config '%s'. Please remove these before deleting the RDS configuration. "
                                + "The following clusters are using this RDS: [%s]", rdsConfig.getName(), clusters));
            } else {
                throw new BadRequestException(String.format("There is a cluster ['%s'] which uses RDS config '%s'. Please remove this "
                        + "cluster before deleting the RDS", clustersWithProvidedRds.get(0).getName(), rdsConfig.getName()));
            }
        }
    }

    private void setStatusToDeleted(RDSConfig rdsConfig) {
        rdsConfig.setName(NameUtil.postfixWithTimestamp(rdsConfig.getName()));
        rdsConfig.setStatus(ResourceStatus.DEFAULT_DELETED);
        rdsConfigRepository.save(rdsConfig);
    }
}
