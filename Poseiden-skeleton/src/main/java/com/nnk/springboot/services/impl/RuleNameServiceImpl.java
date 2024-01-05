package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleName> findAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    @Override
    public Optional<RuleName> findRuleNameById(Integer ruleNameId) {
        return ruleNameRepository.findById(ruleNameId);
    }

    @Override
    public RuleName saveRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleName(Integer ruleNameId) {
        ruleNameRepository.deleteById(ruleNameId);
    }

}
