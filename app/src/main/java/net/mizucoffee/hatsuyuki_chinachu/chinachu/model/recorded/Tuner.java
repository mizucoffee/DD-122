package net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tuner {

    private String name;
    private Boolean isScrambling;
    private List<String> types = null;
    private String command;
    private Integer n;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsScrambling() {
        return isScrambling;
    }

    public void setIsScrambling(Boolean isScrambling) {
        this.isScrambling = isScrambling;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}