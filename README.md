# Sparta Academy API Tests

Java 21 / Maven / Cucumber / REST Assured / JUnit 5 / Mockito test framework for the provided Sparta Academy API Docker SUT.

## Architecture

```text
Gherkin features -> Cucumber step definitions -> ApiClient (REST Assured) -> Docker API
                        |                         ^
                        +-> ScenarioContext       |
                        +-> TokenManager ---------+
External JSON data -> ExternalDataSteps
JUnit / Mockito -> unit tests of framework code
```

## Prerequisites

- JDK 21, Maven 3.9+, Docker Desktop with the Sparta Academy API image running on port 8080.
- Verify the API at http://localhost:8080/swagger/index.html.

## Running

```bash
mvn test
mvn test -Dcucumber.filter.tags="@Security"
mvn test -Dcucumber.filter.tags="@defect"
mvn -Dtest=TokenManagerTest,ApiConfigTest verify
mvn test -Dapi.base.url=http://localhost:8080
```

The normal JUnit Platform configuration excludes `@defect` scenarios. Defect-tagged tests intentionally document known or suspected API discrepancies; run them separately when investigating. Cucumber reports are written to `target/cucumber-reports/`; JaCoCo coverage is written to `target/site/jacoco/`.

## Known defects / caveats

- Creating a Spartan using nested existing Course/Stream objects has produced HTTP 500 with an Entity Framework in-memory duplicate-key exception (`Key: 1`). See `docs/defects.md`.
- `SpartansController.PostSpartan` uses `CreatedAtAction("GetSupplier", ...)`, but the GET action is named `GetSpartan`; after fixing persistence, this may cause another HTTP 500.
- The SUT's `GetSpartan` explicitly returns HTTP 204 for a missing ID; the course missing-ID response has previously been observed as HTTP 204. The existing course `@defect` scenario retains the desired 404 contract assertion.
- Several negative tests express intended API behaviour; verify actual results and classification before reporting them as confirmed defects.
- Do not run successful PUT/DELETE against seeded records unless the database is disposable. The supplied tests only target missing records or mismatch requests.

## Contribution guidelines

Add reusable HTTP steps to `CommonSteps`; response assertions to `ResponseSteps`; resource-specific logic to dedicated step classes. Use `@HappyPath`, `@SadPath`, `@Boundary`, and `@defect` tags. Keep scenarios independent, do not mutate seeded data, and document any verified defects with request, response, logs and severity.

## CI

`.github/workflows/unit-tests.yml` runs unit tests on PRs and pushes. Full API integration tests require a running SUT and are not run in this workflow. **Automatic merging is not enabled**: repository permissions and branch protection must be configured deliberately by the owner. The assignment requires this additional step.

## Outstanding assignment deliverables

Project board/user stories, completed defect reports, integration test run evidence, README PDF export, coverage report, and five-minute non-technical video. See `docs/assignment-checklist.md`.
 
## Auto-merge workflow test 
Testing automatic pull request merging. 
Retesting automatic PR merging. 
