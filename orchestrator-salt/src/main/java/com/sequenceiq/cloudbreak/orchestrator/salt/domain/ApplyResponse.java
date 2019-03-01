package com.sequenceiq.cloudbreak.orchestrator.salt.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyResponse {

    @JsonProperty("return")
    private List<Map<String, Object>> result;

    public Iterable<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }

    public String getJid() {
        if (result != null && !result.isEmpty()) {
            return result.get(0).get("jid").toString();
        }

        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(result.toArray());
    }
}
