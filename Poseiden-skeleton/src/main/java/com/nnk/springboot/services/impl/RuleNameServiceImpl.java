package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.exceptions.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleName> findAllRuleNames() {
        log.info("Find all Rule Name");
        return ruleNameRepository.findAll();
    }

    @Override
    public Optional<RuleName> findRuleNameById(final Integer ruleNameId) {
        log.info("Find rule Name by id : " + ruleNameId);
        Assert.notNull(ruleNameId, "rule Name id should not be null");
        return ruleNameRepository.findById(ruleNameId);
    }

    @Override
    public RuleName createRuleName(final RuleName ruleName) {
        log.info("Creating rule Name : " + ruleName);
        Assert.isNull(ruleName.getId(), "rule Name id should be null for creation");
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName updateRuleName(RuleName ruleName) {
        log.info("Updating rule Name : " + ruleName);
        final Integer id = ruleName.getId();
        Assert.notNull(id, "rule Name id should not be null for update");
        Assert.isFound(ruleNameRepository.existsById(id), "rule Name requested for update does not exist");
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleName(final Integer ruleNameId) {
        log.info("Deleting by rule Name id : " + ruleNameId);
        Assert.notNull(ruleNameId, "rule Name id should not be null");
        Assert.isFound(ruleNameRepository.existsById(ruleNameId), "rule Name requested for delete does not exist");
        ruleNameRepository.deleteById(ruleNameId);
    }
}
