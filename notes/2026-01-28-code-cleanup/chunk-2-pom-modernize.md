# CHUNK_2: Modernize pom.xml

## Execution Agent Prompt

You are an execution agent responsible for cleaning up and modernizing the Maven POM file.

## Context

**Feature:** Dependency modernization
**Priority:** P3-Low
**Issue:** POM has FIXME placeholder and outdated JUnit 4.11 dependency
**Goal:** Clean up POM and upgrade to JUnit 5

**Current State:**
- Line 13 has `<!-- FIXME change it to the project's website -->` with placeholder URL
- JUnit 4.11 is from 2012, JUnit 5.10.2 is current stable
- Test class will need corresponding update (CHUNK_3)

## Your Tasks

### 1. Read Current Implementation
```bash
cat pom.xml | sed -n '10,30p'
```

Verify the FIXME comment and JUnit 4 dependency.

### 2. Implement the Fix

**File to Modify:** `pom.xml`

**Changes Required:**

1. Remove or replace the FIXME URL (lines 12-13):
```xml
<!-- Before -->
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

<!-- After (remove both lines, or replace with actual URL) -->
  <url>https://github.com/dbmartin00/city-map</url>
```

2. Replace JUnit 4 with JUnit 5 (lines 21-27):
```xml
<!-- Before -->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>

<!-- After -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.10.2</version>
      <scope>test</scope>
    </dependency>
```

**Why This Works:**
- Removes TODO/FIXME noise from codebase
- JUnit 5 is the current standard with better assertions and extensions
- junit-jupiter artifact includes all JUnit 5 components

### 3. Verify

```bash
mvn dependency:resolve
# Expected: Downloads JUnit Jupiter dependencies
```

```bash
grep -n "FIXME\|junit:junit" pom.xml
# Expected: No output (both removed)
```

```bash
grep -n "junit-jupiter" pom.xml
# Expected: Shows the new dependency
```

## Success Criteria

- [ ] No FIXME comments in pom.xml
- [ ] JUnit 5 (junit-jupiter) dependency present
- [ ] JUnit 4 dependency removed
- [ ] `mvn dependency:resolve` succeeds

## Files Modified
- `pom.xml` (lines 12-13, 21-27)

## Report Template

When complete, report:
```
CHUNK_2 STATUS: ✅ SUCCESS / ❌ FAILED

FIXME removed: [YES/NO]
JUnit 5 dependency added: [YES/NO]
JUnit 4 dependency removed: [YES/NO]
Dependency resolution succeeds: [YES/NO]

Issues: [None / List any problems encountered]
```

## Notes

**IMPORTANT:** After this chunk, CHUNK_3 must be executed to update the test file to JUnit 5 syntax, otherwise `mvn test` will fail.
