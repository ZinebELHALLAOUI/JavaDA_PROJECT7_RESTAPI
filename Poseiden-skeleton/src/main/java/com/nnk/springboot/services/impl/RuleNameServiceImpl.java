package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.NotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.exceptions.Assert;
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
    public Optional<RuleName> findRuleNameById(final int ruleNameId) {
        log.info("Find rule Name by id : " + ruleNameId);
        return ruleNameRepository.findById(ruleNameId);
    }

    @Override
    public RuleName createRuleName(final RuleName ruleName) {
        log.info("Creating rule Name : " + ruleName);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName updateRuleName(int id, RuleName ruleName) {
        log.info("Updating rule Name : " + ruleName);
        RuleName ruleNameFound = ruleNameRepository.findById(id).orElseThrow(() -> new NotFoundException("Rule name does not exist"));
        ruleNameFound.setName(ruleName.getName());
        ruleNameFound.setDescription(ruleName.getDescription());
        ruleNameFound.setJson(ruleName.getJson());
        return ruleNameRepository.saveAndFlush(ruleNameFound);
    }

    @Override
    public void deleteRuleName(final int ruleNameId) {
        log.info("Deleting by rule Name id : " + ruleNameId);
        Assert.isFound(ruleNameRepository.existsById(ruleNameId), "rule Name requested for delete does not exist");
        ruleNameRepository.deleteById(ruleNameId);
    }
}
