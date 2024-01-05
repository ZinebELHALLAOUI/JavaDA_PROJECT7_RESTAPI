package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {

    List<RuleName> findAllRuleNames();

    Optional<RuleName> findRuleNameById(Integer ruleNameId);

    RuleName saveRuleName(RuleName ruleName);

    void deleteRuleName(Integer ruleNameId);
}