package com.konghuan.skipads.entity;

public class Rule {

    String name;
    String rule;

    public Rule(String name, String rule) {
        this.name = name;
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
