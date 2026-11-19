# Defect evidence (draft)

## DEF-001: Spartan creation with existing Course causes HTTP 500

- **Status:** Observed; open for investigation
- **Severity:** High (creation endpoint unavailable for this payload)
- **Endpoint:** `POST /api/spartans` (authenticated)
- **Reproduction:** Use the `@Create @defect` scenario in `crud.feature` with nested Course ID 1 and Stream ID 1.
- **Expected:** Valid create request should return 201 Created; invalid relationships should return a documented 4xx validation response.
- **Actual:** HTTP 500; server log `System.ArgumentException: An item with the same key has already been added. Key: 1` during EF Core InMemory `SaveChangesAsync`.
- **Suspected cause:** Existing related Course or Stream treated as new during persistence; logs do not identify which entity owns the duplicate key.
- **Additional source concern:** `CreatedAtAction("GetSupplier", ...)` references an action that does not exist; this is not the cause of the observed duplicate-key exception.
- **Next steps:** Isolate payload, validate contract, reproduce with clean SUT, attach timestamped request/response and full Docker log.

## DEF-002: Missing Course ID yields 204 instead of expected 404

- **Status:** Previously observed; verify against agreed contract
- **Severity:** Low/Medium, depending on consumer expectations
- **Endpoint:** `GET /api/Courses/999999`
- **Expected in existing test:** 404
- **Observed previously:** 204
- **Next steps:** Confirm whether 404 is required by the API specification before classifying as a defect.
