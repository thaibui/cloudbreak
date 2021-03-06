package com.sequenceiq.cloudbreak.converter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.sequenceiq.cloudbreak.api.model.AmbariRepoDetailsJson;
import com.sequenceiq.cloudbreak.cloud.model.component.AmbariRepoDetails;

@Mapper(componentModel = "spring")
public interface AmbariRepoDetailsMapper {

    @Mappings({
            @Mapping(source = "baseurl", target = "baseUrl"),
            @Mapping(source = "gpgkey", target = "gpgKeyUrl"),
            @Mapping(target = "version", ignore = true)
    })
    AmbariRepoDetailsJson mapAmbariRepoDetailsToAmbariRepoDetailsJson(AmbariRepoDetails ambariRepoDetails);
}

