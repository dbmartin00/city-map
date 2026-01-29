# CHUNK_0: Extract SF Coordinates

## Execution Agent Prompt

You are an execution agent responsible for extracting hardcoded San Francisco coordinates into named constants.

## Context

**Feature:** Code cleanup - extract magic numbers
**Priority:** P3-Low
**Issue:** Hardcoded lat/long values (37.7562, -122.4430) appear directly in business logic
**Goal:** Replace magic numbers with named constants for clarity and maintainability

**Current State:**
- San Francisco coordinates hardcoded on line 37-38 of Main.java
- Values used as reference point for distance calculations
- No documentation of what these numbers represent

## Your Tasks

### 1. Read Current Implementation
```bash
cat src/main/java/io/harness/fme/citymap/Main.java | sed -n '35,45p'
```

Study the distance calculation to understand how coordinates are used.

### 2. Implement the Fix

**File to Modify:** `src/main/java/io/harness/fme/citymap/Main.java`

**Changes Required:**
1. Add constants after the class declaration (after line 10):
```java
    private static final double SF_LATITUDE = 37.7562;
    private static final double SF_LONGITUDE = -122.4430;
```

2. Replace hardcoded values in the loop (around line 37-38):
```java
// Before:
int radius = GeoUtils.distance(37.7562, -122.4430,
        city.getLatitude(), city.getLongitude());

// After:
int radius = GeoUtils.distance(SF_LATITUDE, SF_LONGITUDE,
        city.getLatitude(), city.getLongitude());
```

**Why This Works:**
- Constants are self-documenting (SF = San Francisco)
- Single source of truth if coordinates need updating
- Follows Java naming conventions for constants

### 3. Verify

```bash
mvn compile
# Expected: BUILD SUCCESS
```

```bash
grep -n "SF_LATITUDE\|SF_LONGITUDE\|37.7562\|-122.4430" src/main/java/io/harness/fme/citymap/Main.java
# Expected: Only constant definitions and usages, no raw numbers
```

## Success Criteria

- [ ] `SF_LATITUDE` and `SF_LONGITUDE` constants defined at class level
- [ ] No hardcoded coordinate values remain in business logic
- [ ] `mvn compile` succeeds

## Files Modified
- `src/main/java/io/harness/fme/citymap/Main.java` (lines 11-12, 37-38)

## Report Template

When complete, report:
```
CHUNK_0 STATUS: ✅ SUCCESS / ❌ FAILED

Constants defined: [YES/NO]
Hardcoded values removed: [YES/NO]
Compile succeeds: [YES/NO]

Issues: [None / List any problems encountered]
```
