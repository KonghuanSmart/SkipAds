package com.konghuan.skipads.Dao;

import com.konghuan.skipads.entity.Rule;

import java.util.List;
import java.util.Map;

public interface RuleDao {

    int addRule(String name, String rule);
    int updateRule(String name, String rule);
    Rule getRuleByName(String name);
    int delRule(String  name);
    Map<String, String> getAllRule();
}
