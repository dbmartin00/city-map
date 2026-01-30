# City Map Cleanup Sprint

## Sprint Metadata
**Sprint Name:** Code Cleanup & Modernization
**Date:** 2026-01-28
**Sprint Type:** Refactor
**Priority:** P3-Low
**Status:** Planning

## Objectives

### Primary Goal
Clean up code quality issues: extract constants, remove dead code, modernize dependencies, add graceful shutdown.

### Success Criteria
- [ ] No hardcoded SF coordinates in business logic
- [ ] No unused imports
- [ ] JUnit 5 dependency with working test
- [ ] Clean shutdown on Ctrl+C
- [ ] `mvn clean compile` succeeds
- [ ] `mvn test` passes

### Out of Scope
- Implementing SDK_UPDATE listener (mentioned in README but not requested)
- Adding real unit tests for business logic
- Upgrading other dependencies

---

## Chunk Definitions

### CHUNK_0: Extract SF Coordinates
**File:** [Main.java](../src/main/java/io/harness/fme/citymap/Main.java)
**Dependencies:** None
**Risk:** Low

**Tasks:**
1. Add class-level constants:
   ```java
   private static final double SF_LATITUDE = 37.7562;
   private static final double SF_LONGITUDE = -122.4430;
   ```
2. Replace hardcoded values on line 37-38 with constants

**Verification:**
- [ ] Constants defined at class level
- [ ] `GeoUtils.distance(SF_LATITUDE, SF_LONGITUDE, ...)` compiles

---

### CHUNK_1: Remove Unused Import
**File:** [Main.java](../src/main/java/io/harness/fme/citymap/Main.java)
**Dependencies:** None
**Risk:** Low

**Tasks:**
1. Delete line 6: `import java.util.concurrent.TimeoutException;`

**Verification:**
- [ ] Import removed
- [ ] `mvn compile` succeeds

---

### CHUNK_2: Modernize pom.xml
**File:** [pom.xml](../pom.xml)
**Dependencies:** None
**Risk:** Low

**Tasks:**
1. Remove line 13 (FIXME URL) or replace with actual repo URL
2. Update JUnit dependency:
   ```xml
   <dependency>
     <groupId>org.junit.jupiter</groupId>
     <artifactId>junit-jupiter</artifactId>
     <version>5.10.2</version>
     <scope>test</scope>
   </dependency>
   ```

**Verification:**
- [ ] No FIXME comments remain
- [ ] JUnit 5 dependency present

---

### CHUNK_3: Update Test to JUnit 5
**File:** [AppTest.java](../src/test/java/com/example/AppTest.java)
**Dependencies:** CHUNK_2 (needs JUnit 5 dependency)

**Tasks:**
1. Change imports:
   ```java
   import static org.junit.jupiter.api.Assertions.assertTrue;
   import org.junit.jupiter.api.Test;
   ```
2. Rest of file unchanged (syntax is compatible)

**Verification:**
- [ ] `mvn test` passes

---

### CHUNK_4: Add Graceful Shutdown
**File:** [Main.java](../src/main/java/io/harness/fme/citymap/Main.java)
**Dependencies:** None
**Risk:** Low

**Tasks:**
1. After `client.blockUntilReady();` (line 27), add:
   ```java
   Runtime.getRuntime().addShutdownHook(new Thread(() -> {
       client.destroy();
   }));
   ```

**Verification:**
- [ ] Shutdown hook registered before main loop
- [ ] `mvn exec:java` + Ctrl+C exits cleanly (no error stacktrace)

---

## Execution Order

```
CHUNK_0 ─┐
CHUNK_1 ─┼──> CHUNK_4 (all independent, can run in any order)
CHUNK_2 ─┴──> CHUNK_3 (depends on CHUNK_2)
```

## Files Modified
| File | Changes |
|------|---------|
| `src/main/java/io/harness/fme/citymap/Main.java` | Constants, remove import, shutdown hook |
| `pom.xml` | Remove FIXME, upgrade JUnit |
| `src/test/java/com/example/AppTest.java` | JUnit 5 imports |

## Final Verification
1. `mvn clean compile` - no warnings
2. `mvn test` - passes
3. `mvn exec:java` (with API key) - runs normally
4. Ctrl+C - exits without errors
