package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RuleNameServiceImplTest {

    private final RuleNameRepository ruleNameRepository = Mockito.mock(RuleNameRepository.class);
    private final RuleNameService ruleNameService = new RuleNameServiceImpl(ruleNameRepository);

    @Test
    public void testFindAllRuleNames() {
        // Mocking the repository
        List<RuleName> expectedRuleNames = new ArrayList<>();
        when(ruleNameRepository.findAll()).thenReturn(expectedRuleNames);

        // Calling service method
        List<RuleName> result = ruleNameService.findAllRuleNames();

        // Verifying the result
        assertEquals(expectedRuleNames, result);
    }

    @Test
    public void testFindRuleNameById() {
        // Mocking the repository
        int ruleNameId = 1;
        RuleName expectedRuleName = new RuleName();
        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(expectedRuleName));

        // Calling service method
        Optional<RuleName> result = ruleNameService.findRuleNameById(ruleNameId);

        // Verifying the result
        assertTrue(result.isPresent());
        assertEquals(expectedRuleName, result.get());
    }

    @Test
    public void testCreateRuleName() {
        // Mocking the repository
        RuleName ruleName = new RuleName();
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        // Calling service method
        RuleName result = ruleNameService.createRuleName(ruleName);

        // Verifying the result
        assertEquals(ruleName, result);
    }

    @Test
    public void testUpdateRuleName() {
        // Mocking the repository
        int ruleNameId = 1;
        RuleName updatedRuleName = new RuleName();
        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(new RuleName()));
        when(ruleNameRepository.saveAndFlush(updatedRuleName)).thenReturn(updatedRuleName);

        // Calling service method
        RuleName result = ruleNameService.updateRuleName(ruleNameId, updatedRuleName);

        // Verifying the result
        assertEquals(updatedRuleName, result);
    }

    @Test
    public void testDeleteRuleName() {
        // Mocking the repository
        int ruleNameId = 1;
        when(ruleNameRepository.existsById(ruleNameId)).thenReturn(true);

        // Calling service method
        ruleNameService.deleteRuleName(ruleNameId);

        // Verifying the delete operation
        Mockito.verify(ruleNameRepository).deleteById(ruleNameId);
    }
}
