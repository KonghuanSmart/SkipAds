package com.konghuan.skipads.service;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.konghuan.skipads.Dao.RuleDao;
import com.konghuan.skipads.entity.Rule;
import com.konghuan.skipads.service.Impl.RuleDaoImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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

    public int importRules(List<Rule> rules){
        int i = 0;
        Map<String, String> allRule = getAllRule();
        Iterator<Rule> iterator = rules.iterator();
        while (iterator.hasNext()){
            Rule rule = iterator.next();
            if (allRule.containsKey(rule.getName())){
                i += updateRule(rule.getName(), rule.getRule());
            }else {
                i += addRule(rule.getName(), rule.getRule());
            }
        }
        return i;
    }

    public List<Rule> analysisRules(String str){
        List<Rule> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()){
                String key = iterator.next();
                String rule = jsonObject.optString(key);
                if (!TextUtils.isEmpty(rule)){
                    list.add(new Rule(key,rule));
                }
            }
        } catch (JSONException e) {
            return list;
        }
        return list;
    }

    public List<Rule> getRuleFromClipboard(Context context){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (null != clipboardManager) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (null != clipData && clipData.getItemCount() > 0) {
                ClipData.Item item = clipData.getItemAt(0);
                if (null != item) {
                    String content = item.getText().toString();
                    return analysisRules(content);
                }
            }
        }
        return null;
    }


}
