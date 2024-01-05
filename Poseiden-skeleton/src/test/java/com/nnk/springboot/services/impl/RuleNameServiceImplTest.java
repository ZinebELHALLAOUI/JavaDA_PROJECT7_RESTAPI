package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RuleNameServiceImplTest {

    private final RuleNameRepository ruleNameRepository = mock(RuleNameRepository.class);
    private final RuleNameServiceImpl ruleNameService = new RuleNameServiceImpl(ruleNameRepository);

    @Test
    void testFindAllRuleNames() {
        RuleName sampleRuleName = new RuleName();
        sampleRuleName.setId(1);
        sampleRuleName.setName("Sample Rule");

        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(sampleRuleName);

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        List<RuleName> result = ruleNameService.findAllRuleNames();

        assertEquals(1, result.size());
        assertEquals(sampleRuleName, result.get(0));

        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void testFindRuleNameById() {
        // Arrange
        RuleName sampleRuleName = new RuleName();
        sampleRuleName.setId(1);
        sampleRuleName.setName("Sample Rule");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(sampleRuleName));

        Optional<RuleName> result = ruleNameService.findRuleNameById(1);

        assertEquals(Optional.of(sampleRuleName), result);

        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void testSaveRuleName() {
        RuleName sampleRuleName = new RuleName();
        sampleRuleName.setId(1);
        sampleRuleName.setName("Sample Rule");

        when(ruleNameRepository.save(sampleRuleName)).thenReturn(sampleRuleName);

        RuleName result = ruleNameService.saveRuleName(sampleRuleName);

        assertEquals(sampleRuleName, result);

        verify(ruleNameRepository, times(1)).save(sampleRuleName);
    }

    @Test
    void testDeleteRuleName() {
        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}