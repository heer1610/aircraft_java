# System Architecture & Project Overview

## Application Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      WEB BROWSER (Frontend)                     │
│                  (HTML5, CSS3, JavaScript)                      │
│                      http://localhost:8080                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │          Responsive Single Page Application              │  │
│  │                                                          │  │
│  │  ┌─────────────────────────────────────────────────┐    │  │
│  │  │  Add Aircraft Tab  │ View Aircraft Tab │ Add      │    │  │
│  │  │  Maintenance Tab   │ Maintenance History           │    │  │
│  │  └─────────────────────────────────────────────────┘    │  │
│  │                                                          │  │
│  │  All forms with client-side validation                  │  │
│  │  Real-time API communication via Fetch                  │  │
│  │  Professional styling with responsive design            │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  HTTP/REST Communication (JSON)                                │
└────────────────────────────┬──────────────────────────────────┘
                             │
                 ┌───────────┴───────────┐
                 │                       │
        ┌────────▼──────────┐   ┌────────▼──────────┐
        │  Aircraft API     │   │  Maintenance API  │
        │   Endpoints       │   │   Endpoints       │
        └───────────────────┘   └───────────────────┘
                 │                       │
                 └───────────┬───────────┘
                             │
┌─────────────────────────────▼──────────────────────────────────┐
│              SPRING BOOT REST API SERVER                       │
│                    Port: 8080                                  │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │              CONTROLLERS LAYER                            │ │
│  │  ┌─────────────────────────┐  ┌──────────────────────┐   │ │
│  │  │ AircraftController       │  │MaintenanceController│   │ │
│  │  │ - POST /api/aircraft     │  │ - POST /api/maint... │   │ │
│  │  │ - GET /api/aircraft      │  │ - GET /api/maint...  │   │ │
│  │  │ - DELETE /api/aircraft   │  │ - GET /aircraft/{id} │   │ │
│  │  └─────────────┬───────────┘  └────────┬─────────────┘   │ │
│  │                │                       │                 │ │
│  │  @RestController, @RequestMapping     │                 │ │
│  │  Handles HTTP requests, validation    │                 │ │
│  └────────────────┼───────────────────────┼─────────────────┘ │
│                   │                       │                   │
│  ┌────────────────▼───────────────────────▼─────────────────┐ │
│  │              SERVICES LAYER                              │ │
│  │  ┌──────────────────────┐  ┌──────────────────────────┐ │ │
│  │  │ AircraftService      │  │MaintenanceRecordService │ │ │
│  │  │ - addAircraft()      │  │ - addMaintenanceRecord()│ │ │
│  │  │ - getAllAircraft()   │  │ - getMaintenanceHistory()││ │
│  │  │ - deleteAircraft()   │  │ - getAllRecords()       │ │ │
│  │  │ - updateAircraft()   │  │                         │ │ │
│  │  │                      │  │ Business logic &        │ │ │
│  │  │ Validation logic     │  │ data processing         │ │ │
│  │  └──────────────┬───────┘  └────────┬──────────────┘ │ │
│  │                 │                   │                 │ │
│  └─────────────────┼───────────────────┼─────────────────┘ │
│                    │                   │                   │
│  ┌─────────────────▼───────────────────▼─────────────────┐ │
│  │         REPOSITORIES (Data Access)                    │ │
│  │  ┌──────────────────────┐  ┌──────────────────────┐  │ │
│  │  │AircraftRepository    │  │MaintenanceRepository│  │ │
│  │  │ extends JpaRepository│  │ extends JpaRepository│  │ │
│  │  │                      │  │                      │  │ │
│  │  │- findAll()           │  │- findByAircraftId()  │  │ │
│  │  │- findById()          │  │- findByCriteria()    │  │ │
│  │  │- save()              │  │- delete()            │  │ │
│  │  │- delete()            │  │                      │  │ │
│  │  └──────────────┬───────┘  └────────┬─────────────┘  │ │
│  │                 │                   │                 │ │
│  └─────────────────┼───────────────────┼─────────────────┘ │
│                    │                   │                   │
│  ┌─────────────────▼───────────────────▼─────────────────┐ │
│  │       EXCEPTION HANDLING LAYER                        │ │
│  │  @RestControllerAdvice                               │ │
│  │  - GlobalExceptionHandler                            │ │
│  │  - AircraftNotFoundException                          │ │
│  │  - AircraftAlreadyExistsException                    │ │
│  │  - ValidationException Handler                       │ │
│  │                                                      │ │
│  │  All exceptions mapped to JSON responses            │ │
│  └──────────────────────────────────────────────────────┘ │
│                                                                 │
└─────────────────────────────┬──────────────────────────────────┘
                              │
               ┌──────────────┴──────────────┐
               │                             │
        ┌──────▼───────────┐      ┌──────────▼──────┐
        │ ENTITY MODELS    │      │ VALIDATION      │
        │                  │      │                 │
        │ Aircraft         │      │ @NotBlank       │
        │ - id             │      │ @NotNull        │
        │ - regNumber      │      │ @Min/@Max       │
        │ - model          │      │ @Size           │
        │ - manufacturer   │      │ @DecimalMin     │
        │ - yearMfg        │      │ @Valid          │
        │ - status         │      │                 │
        │ - createdAt      │      │ Field & Method  │
        │ - maintenance[]  │      │ level validation│
        │                  │      │                 │
        │ MaintenanceRecord│      │ Automatic error │
        │ - id             │      │ response        │
        │ - aircraft (FK)  │      │                 │
        │ - type           │      │                 │
        │ - description    │      │                 │
        │ - dates          │      │                 │
        │ - technician     │      │                 │
        │ - cost           │      │                 │
        │ - recordedAt     │      │                 │
        │                  │      │                 │
        │ @Entity          │      │                 │
        │ @Table           │      │                 │
        │ JPA Annotations  │      │                 │
        └──────┬───────────┘      └──────────┬──────┘
               │                             │
               └──────────────┬──────────────┘
                              │
┌─────────────────────────────▼──────────────────────────────────┐
│                    H2 DATABASE                                 │
│            (In-Memory, File-Based Optional)                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌────────────────────────────────────────────────────────┐   │
│  │              TABLES & SCHEMA                           │   │
│  │                                                        │   │
│  │  TABLE: aircraft                                      │   │
│  │  ┌──────────────────────────────────────────────┐    │   │
│  │  │ Column                   │ Type    │ Constraint    │   │
│  │  ├──────────────────────────┼─────────┼──────────────┤   │
│  │  │ id                       │ BIGINT  │ PK, AUTO_INC │   │
│  │  │ registration_number      │ VARCHAR │ UNIQUE, NOT NULL│ │
│  │  │ model                    │ VARCHAR │ NOT NULL     │   │
│  │  │ manufacturer             │ VARCHAR │ NOT NULL     │   │
│  │  │ year_of_manufacture      │ INT     │ NOT NULL     │   │
│  │  │ status                   │ VARCHAR │ NOT NULL     │   │
│  │  │ created_at               │ TIMESTAMP │ DEFAULT NOW│   │
│  │  └──────────────────────────────────────────────────┘    │
│  │                                                        │   │
│  │  TABLE: maintenance_record                           │   │
│  │  ┌──────────────────────────────────────────────┐    │   │
│  │  │ Column                   │ Type    │ Constraint    │   │
│  │  ├──────────────────────────┼─────────┼──────────────┤   │
│  │  │ id                       │ BIGINT  │ PK, AUTO_INC │   │
│  │  │ aircraft_id              │ BIGINT  │ FK, NOT NULL │   │
│  │  │ maintenance_type         │ VARCHAR │ NOT NULL     │   │
│  │  │ description              │ VARCHAR │ NOT NULL     │   │
│  │  │ maintenance_date         │ TIMESTAMP │ NOT NULL    │   │
│  │  │ completion_date          │ TIMESTAMP │ NOT NULL    │   │
│  │  │ technician_name          │ VARCHAR │ NOT NULL     │   │
│  │  │ cost                     │ DECIMAL │ NOT NULL     │   │
│  │  │ recorded_at              │ TIMESTAMP │ DEFAULT NOW│   │
│  │  │                          │         │ CASCADE DELETE│   │
│  │  └──────────────────────────────────────────────────┘    │
│  │                                                        │   │
│  │  Cascade Delete enabled on aircraft.id               │   │
│  │  Referential integrity enforced                      │   │
│  │                                                        │   │
│  └────────────────────────────────────────────────────────┘   │
│                                                                 │
│  Browser Access: http://localhost:8080/h2-console              │
│  Username: sa | Password: (empty)                              │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Data Flow Diagram

```
USER                         FRONTEND                    BACKEND                  DATABASE
┌──────┐                   ┌─────────────┐            ┌──────────────┐          ┌──────────┐
│      │  Click Tab        │             │            │              │          │          │
│      ├─────────────────→ │ Render Form │            │              │          │          │
│ User │                   │             │            │              │          │          │
│      │                   └─────────────┘            │              │          │          │
│      │                          │                   │              │          │          │
│      │  Fill Form &            │                   │              │          │          │
│      │  Click Submit           │                   │              │          │          │
│      ├──────────────────────────→ FormData         │              │          │          │
│      │                           │                 │              │          │          │
│      │                           │ JSON Request   │              │          │          │
│      │                           ├────────────────→ Controller     │          │          │
│      │                           │                 │              │          │          │
│      │                           │                 │ Validation   │          │          │
│      │                           │                 │ Check        │          │          │
│      │                           │                 │              │          │          │
│      │                           │                 │ Service Call │          │          │
│      │                           │                 ├─────────────→ Repository│          │
│      │                           │                 │              │          │          │
│      │                           │                 │              │ SQL Exec │          │
│      │                           │                 │              ├─────────→ Save/Get  │
│      │                           │                 │              │          │ Data     │
│      │                           │                 │              ←─────────┤          │
│      │   Show Success            │  Success/Error │              │          │          │
│      │ Message & Update          │   JSON Response              │          │          │
│      │        List        ←─────────────────────────┤          │          │          │
│      │                           │                 │          │          │          │
└──────┘                   └─────────────┘            └──────────────┘          └──────────┘
```

## Request/Response Flow Example

```
ADD AIRCRAFT REQUEST:
═════════════════════

POST /api/aircraft HTTP/1.1
Content-Type: application/json

{
  "registrationNumber": "N123456",
  "model": "Boeing 737",
  "manufacturer": "Boeing",
  "yearOfManufacture": 2020,
  "status": "ACTIVE"
}

         │
         ↓ (GlobalExceptionHandler wraps all errors)

ValidationAspect checks @Valid annotations
        │
        ├─ registrationNumber: mandatory ✓
        ├─ model: mandatory ✓
        ├─ manufacturer: mandatory ✓
        ├─ yearOfManufacture: 2020 is valid ✓
        ├─ status: valid enum ✓
        └─ No duplicates exist ✓ All pass!

         │
         ↓

AircraftController.addAircraft() receives valid data
         │
         ↓

AircraftService.addAircraft()
         │
         ├─ Checks for duplicate registration
         ├─ If exists: throws AircraftAlreadyExistsException
         └─ If not: proceeds to save

         │
         ↓

AircraftRepository.save(aircraft)
         │
         ↓

H2 Database Stores:
{id: 1, registrationNumber: N123456, ...}

         │
         ↓

HTTP Response (201 CREATED):
{
  "id": 1,
  "registrationNumber": "N123456",
  "model": "Boeing 737",
  "manufacturer": "Boeing",
  "yearOfManufacture": 2020,
  "status": "ACTIVE",
  "createdAt": "2026-02-16T10:30:00",
  "maintenanceRecords": []
}

         │
         ↓

Frontend receives response
Display success message
Update aircraft list


ERROR RESPONSE EXAMPLE (Duplicate Aircraft):
═════════════════════════════════════════════

if (aircraftRepository.findByRegistrationNumber(regNumber).isPresent()) {
    throw AircraftAlreadyExistsException(
        "Aircraft with registration number 'N123456' already exists"
    )
}

GlobalExceptionHandler catches exception
         │
         ↓

HTTP Response (409 CONFLICT):
{
  "timestamp": "2026-02-16T10:35:00",
  "status": 409,
  "error": "Aircraft Already Exists",
  "message": "Aircraft with registration number 'N123456' already exists",
  "path": "/api/aircraft"
}

         │
         ↓

Frontend displays error message to user
```

## Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      Aircraft Management                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  User Input (HTML Form)                                        │
│         │                                                       │
│         ├─→ Client-side Validation (JavaScript)                │
│         │         │                                             │
│         │         └─→ Show validation errors                    │
│         │               (form doesn't submit)                   │
│         │                                                       │
│         ├─→ If valid, create JSON payload                      │
│         │         │                                             │
│         │         └─→ POST /api/aircraft (Fetch API)           │
│         │                  │                                    │
│         │                  └─→ AircraftController               │
│         │                      @PostMapping("/")                │
│         │                      @Valid @RequestBody              │
│         │                           │                           │
│         │                           ├─→ Bean Validation         │
│         │                           │   (Field level)           │
│         │                           │   @NotBlank              │
│         │                           │   @Min/@Max              │
│         │                           │        │                  │
│         │                           │        └─→ Valid? No      │
│         │                           │          Return 400 error │
│         │                           │                           │
│         │                           ├─→ Valid? Yes              │
│         │                           │                           │
│         │                           └─→ AircraftService         │
│         │                               .addAircraft(data)      │
│         │                                    │                  │
│         │                                    ├─→ Check duplicate │
│         │                                    │   in database     │
│         │                                    │        │          │
│         │                                    │        ├─ Exists? │
│         │                                    │        │  Throw409│
│         │                                    │        │          │
│         │                                    │        └─ Not exist│
│         │                                    │          ✓ OK     │
│         │                                    │                  │
│         │                                    └─→ AircraftRepo   │
│         │                                        .save(entity)  │
│         │                                             │         │
│         │                                             └─→ H2 DB │
│         │                                             Persist & │
│         │                                            Auto-incr  │
│         │                                            ID         │
│         │                                                       │
│         └─← Return Aircraft Object (201 Created)               │
│                 │                                               │
│                 └─→ JavaScript processes response              │
│                     Display success message                    │
│                     Update aircraft list display               │
│                     Clear form                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Transaction & Consistency Guarantees

```
┌───────────────────────────────────────────────────────────────┐
│          Database Transaction Management (ACID)                │
├───────────────────────────────────────────────────────────────┤
│                                                               │
│  @Transactional on Service Methods ensures:                  │
│                                                               │
│  A - Atomicity                                               │
│      All operations succeed or all rollback                  │
│      Add aircraft > Save aircraft > Return success or fail   │
│                                                               │
│  C - Consistency                                             │
│      Referential integrity maintained                        │
│      Unique constraints enforced                             │
│      Cascade delete properly handled                         │
│                                                               │
│  I - Isolation                                               │
│      Concurrent requests don't interfere                     │
│      H2 default isolation level applies                      │
│                                                               │
│  D - Durability                                              │
│      Data persists after successful transaction              │
│      H2 file-based optional for persistence                  │
│                                                               │
│  CASCADE DELETE ensures:                                     │
│  When aircraft is deleted:                                   │
│    1. All related maintenance_records deleted                │
│    2. Happens in single transaction                          │
│    3. All or nothing - no orphaned records                   │
│                                                               │
└───────────────────────────────────────────────────────────────┘
```

## Security Flow

```
┌────────────────────────────────────────────────────────────────┐
│                    Security Layers                             │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  1. CORS Protection (Configured):                             │
│     @CrossOrigin(origins = "*", maxAge = 3600)               │
│     Allows all origins (can be restricted later)              │
│                                                                │
│  2. Input Validation:                                         │
│     JSR380 (@Valid annotations)                               │
│     All @RequestBody validated before processing              │
│                                                                │
│  3. SQL Injection Prevention:                                 │
│     JPA @Query or derived queries (no raw SQL)                │
│     Parameterized queries by default                          │
│     Entity attribute names as parameters                      │
│                                                                │
│  4. Exception Handling:                                       │
│     Global error handler masks internal details              │
│     No stack traces in client response                       │
│     Safe error messages                                      │
│                                                                │
│  5. Data Integrity:                                          │
│     Unique constraints on registrationNumber                 │
│     Foreign key constraints on aircraft_id                   │
│     Cascade delete prevents orphaned data                    │
│                                                                │
│  6. HTTP Headers:                                            │
│     Content-Type: application/json                           │
│     No sensitive data in responses                           │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

---

## Deployment Architecture (Optional)

```
┌─────────────────┐
│  Client Browser │ HTTP/HTTPS
└────────┬────────┘
         │
         ↓
┌──────────────────────────┐
│  Load Balancer (Nginx)   │ (Optional for production)
└────────┬─────────────────┘
         │
    ┌────┴────┐
    │          │
    ↓          ↓
┌─────────┐ ┌─────────┐
│ App 1   │ │ App 2   │ (Multiple instances of Spring Boot)
│port8080 │ │port8081 │
└────┬────┘ └────┬────┘
     │           │
     └─────┬─────┘
           │
    ┌──────▼──────┐
    │   Database  │
    │  (MySQL)    │ (Shared database for multiple instances)
    └─────────────┘
```

---

**This architecture provides:**
- Clean separation of concerns
- Easy testing and maintenance
- Scalability
- Comprehensive error handling
- Data integrity
- User-friendly experience

Ready for deployment! ✈️
