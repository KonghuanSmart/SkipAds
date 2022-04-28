package com.konghuan.skipads.service;

import android.content.Context;

import com.konghuan.skipads.Dao.RuleDao;
import com.konghuan.skipads.entity.Rule;
import com.konghuan.skipads.service.Impl.RuleDaoImpl;

import java.util.List;
import java.util.Map;

public class RuleService {

    private static RuleDao ruleDao;

    public RuleService(Context context) {
        ruleDao = new RuleDaoImpl(context);
    }

    public int addRule(String name, String rule){
        return ruleDao.addRule(name, rule);
    }

    public int delRule(String name){
        return ruleDao.delRule(name);
    }

    public Rule getRuleByName(String name){
        return ruleDao.getRuleByName(name);
    }

    public int updateRule(String name, String rule){
        return ruleDao.updateRule(name, rule);
    }

    public Map<String, String> getAllRule(){
        return ruleDao.getAllRule();
    }

}
