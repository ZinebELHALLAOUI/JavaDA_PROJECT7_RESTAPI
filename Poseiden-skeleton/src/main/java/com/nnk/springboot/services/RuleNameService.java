package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {

    List<RuleName> findAllRuleNames();

    Optional<RuleName> findRuleNameById(int ruleNameId);

    RuleName createRuleName(RuleName ruleName);

    RuleName updateRuleName(int id, RuleName ruleName);

    void deleteRuleName(int ruleNameId);
}
