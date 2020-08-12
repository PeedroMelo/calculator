package com.vieirapcm.calculator;

import org.springframework.stereotype.Service;

/**
 * Main Calculator Application.
 */
@Service
public class Calculator {
    int sum(int a, int b) {
        return a + b;
    }
}