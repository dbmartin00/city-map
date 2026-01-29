# CHUNK_4: Add Graceful Shutdown

## Execution Agent Prompt

You are an execution agent responsible for adding a shutdown hook for clean SDK client termination.

## Context

**Feature:** Resource cleanup on exit
**Priority:** P3-Low
**Issue:** When user hits Ctrl+C, the Split SDK client is never properly closed
**Goal:** Register a shutdown hook that calls `client.destroy()` on JVM exit

**Current State:**
- Main loop runs forever with `while (true)`
- No cleanup code exists
- Ctrl+C terminates abruptly without releasing SDK resources
- May cause connection leaks or incomplete telemetry

## Your Tasks

### 1. Read Current Implementation
```bash
cat src/main/java/io/harness/fme/citymap/Main.java | sed -n '22,35p'
```

Study where the client is initialized and where the main loop begins.

### 2. Implement the Fix

**File to Modify:** `src/main/java/io/harness/fme/citymap/Main.java`

**Change Required:**

After `client.blockUntilReady();` (line 27) and before `MapRenderer renderer = ...` (line 29), add:
```java
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            client.destroy();
        }));
```

**Context - the surrounding code should look like:**
```java
        SplitClient client = SplitFactoryBuilder.build(apiKey, config).client();
        client.blockUntilReady();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            client.destroy();
        }));

        MapRenderer renderer = new MapRenderer(width, height);
```

**Why This Works:**
- `Runtime.addShutdownHook()` registers a thread to run on JVM shutdown
- Works for Ctrl+C (SIGINT), `kill` (SIGTERM), and normal exit
- `client.destroy()` flushes pending events and closes connections
- Lambda captures `client` reference from enclosing scope

### 3. Verify

```bash
mvn compile
# Expected: BUILD SUCCESS
```

```bash
grep -n "addShutdownHook\|destroy" src/main/java/io/harness/fme/citymap/Main.java
# Expected: Shows both the hook registration and destroy call
```

**Manual Test (if API key available):**
```bash
export FME_SERVER_SIDE_API_KEY="your-key"
mvn exec:java
# Wait for map to render, then press Ctrl+C
# Expected: Clean exit, no stack trace or error messages
```

## Success Criteria

- [ ] Shutdown hook registered before main loop
- [ ] `client.destroy()` called in hook
- [ ] `mvn compile` succeeds
- [ ] Ctrl+C exits cleanly (no error output)

## Files Modified
- `src/main/java/io/harness/fme/citymap/Main.java` (insert after line 27)

## Report Template

When complete, report:
```
CHUNK_4 STATUS: ✅ SUCCESS / ❌ FAILED

Shutdown hook added: [YES/NO]
Compile succeeds: [YES/NO]
Clean exit on Ctrl+C: [YES/NO/NOT TESTED]

Issues: [None / List any problems encountered]
```
