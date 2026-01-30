# CHUNK_3: Update Test to JUnit 5

## Execution Agent Prompt

You are an execution agent responsible for migrating the test file from JUnit 4 to JUnit 5 syntax.

## Context

**Feature:** Test modernization
**Priority:** P3-Low
**Dependencies:** CHUNK_2 (JUnit 5 dependency must be in pom.xml first)
**Issue:** Test file uses JUnit 4 imports which won't work with JUnit 5 dependency
**Goal:** Update imports to JUnit 5 equivalents

**Current State:**
- Test uses `org.junit.Test` and `org.junit.Assert`
- These are JUnit 4 packages
- After CHUNK_2, only JUnit 5 (Jupiter) is available

## Your Tasks

### 1. Read Current Implementation
```bash
cat src/test/java/com/example/AppTest.java
```

Study the current JUnit 4 imports and annotations.

### 2. Implement the Fix

**File to Modify:** `src/test/java/com/example/AppTest.java`

**Changes Required:**

Update the imports (lines 3-5):
```java
// Before (JUnit 4)
import static org.junit.Assert.assertTrue;
import org.junit.Test;

// After (JUnit 5)
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
```

**Complete Updated File:**
```java
package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
```

**Why This Works:**
- JUnit 5 moved assertions to `org.junit.jupiter.api.Assertions`
- `@Test` annotation moved to `org.junit.jupiter.api.Test`
- The test method signature and body remain unchanged
- `assertTrue(boolean)` has same signature in both versions

### 3. Verify

```bash
mvn test
# Expected: BUILD SUCCESS, 1 test run
```

```bash
grep -n "org.junit.jupiter" src/test/java/com/example/AppTest.java
# Expected: Shows JUnit 5 imports
```

## Success Criteria

- [ ] JUnit 5 imports (`org.junit.jupiter.api.*`)
- [ ] No JUnit 4 imports remain
- [ ] `mvn test` passes with 1 test run

## Files Modified
- `src/test/java/com/example/AppTest.java` (lines 3-5)

## Report Template

When complete, report:
```
CHUNK_3 STATUS: ✅ SUCCESS / ❌ FAILED

JUnit 5 imports: [YES/NO]
JUnit 4 imports removed: [YES/NO]
mvn test passes: [YES/NO]
Tests run: [count]

Issues: [None / List any problems encountered]
```
