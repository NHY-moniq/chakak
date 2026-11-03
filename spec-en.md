# ChaKak MVP Spec

> 한국어판: [spec.md](spec.md)

## 1. Product Summary

ChaKak is an in-vehicle recording app that saves location, time, and available vehicle status with a voice command such as “Capture this,” then lets the driver add a voice memo. The MVP focuses on three jobs:

1. Quickly record places worth revisiting and vehicle conditions while driving.
2. Save the basic record first, then optionally add a voice memo.
3. Review and enrich records while parked for later visits and maintenance conversations.

The app will be built in Kotlin for Android on Pleos Connect. Assume a user-installed app, with Mobility Services and Lifestyle as category candidates to resolve against the product purpose and review policy. In-motion voice capture depends on category and regional policy, Gleo integration, and verified SDK availability. Its core direction is **voice-first recording**, **local storage in the vehicle**, and **detailed review and editing while parked**.

### Scope and evidence

- **Chosen product design**: Kotlin, Jetpack Compose, Room, immediate base-record persistence, a parked record library, and separate SDK adapters/mock providers.
- **Unverified targets**: Gleo approval and in-motion headless invocation, follow-up memo correlation, project-level SDK access, and actual returned values.
- **Parked in this spec**: confirmed parking with platform permission for the operation, not merely a momentary zero-speed reading.
- Retain Moniq's 19-section order with ChaKak-specific features, screens, and verification conditions. This is a design specification, not an implementation-completion report.

---

## 2. Problem Statement

- Drivers cannot easily find an unexpectedly discovered café, park, or scenic road again without its name or address.
- Taking a phone photo or dropping a map pin is difficult while driving alone.
- Memories of the location and surroundings fade after arrival.
- Temporary warnings or unusual symptoms may not recur at a repair shop, making the original situation difficult to explain.
- Drivers need a way to keep location, time, vehicle status, and their own observations together.

---

## 3. Product Goals

### Primary goal

Implement and validate an end-to-end MVP in the Pleos Emulator, from voice request to recording, optional memo, and review while parked.

### MVP success criteria

- A user can create a basic record by voice.
- Commands mentioning a place or vehicle status assign the corresponding category.
- A follow-up voice memo is linked to the original record ID.
- The basic record survives a declined memo, no response, or recognition failure.
- While parked, a user can view, edit, and delete records from All, Places, and Vehicle Status lists.
- A vehicle status summary shows collection times, available readings, and the memo.
- The app does not announce success if saving the basic record fails.

### Non-goals for MVP

- Vehicle fault diagnosis or repair decisions
- Server-based accounts, backup, or synchronization
- Phone integration
- Direct navigation or maintenance service integration
- Surround-view image capture without verified platform support

### Completion reporting

Report local storage/library implementation, mock UX verification, real Pleos integration, competition/Gleo approval, and real-vehicle validation separately. Mock previews or Emulator success do not establish in-motion voice availability or real-vehicle support. Driving voice scenarios below assume an approved, verified environment.

---

## 4. Target Users

### Primary user

Drivers who frequently drive alone and want to revisit places discovered during a commute or leisure drive.

### Secondary users

Drivers who want to record temporary warnings or unusual symptoms to explain the circumstances during maintenance consultations.

---

## 5. Product Principles

1. **Save first**: Save the basic record before asking the user to choose what to keep.
2. **Voice-first**: Minimize interaction while driving with voice requests and brief responses.
3. **Optional memo**: Ask only once and retain the record without a memo.
4. **Distinguish timestamps**: Separate command receipt time from collection time for each observation.
5. **Expose missing data**: Never replace unsupported or failed readings with normal values.
6. **Detailed interaction while parked**: Check platform driving restrictions and parking state; restrict detailed interaction when state cannot be confirmed.
7. **Local storage first**: Store records in the vehicle by default and make external sharing the user's choice.

8. **Official design first**: prioritize Pleos source documentation and cross-check Figma resources. Material 3 and other design tools are supporting tools.
9. **Display evidence faithfully**: use text/icons when images are absent. Never present stock photos, inferred sensor values, or mock output as real captures.
10. **Design before implementation**: establish source review, layouts, components/states, the permission matrix, token candidates, and open decisions before writing app code.

---

## 6. UX Direction and Flow Adjustments

### Key UX refinements

1. **One shared capture flow**
   - “Capture this,” “Capture this location,” and “Capture the vehicle status” use the same recording function.
   - Place or vehicle status wording adds a category; generic captures can be categorized while parked.
2. **Separate basic saving from memo attachment**
   - After a successful save, ask: “Saved. Would you like to add a memo?”
   - A failed memo attachment must not delete the saved basic record.
3. **Adapt interaction to driving and parking**
   - Provide brief result announcements while driving.
   - Provide detailed viewing and editing while parked.
4. **Explain what the location means**
   - The pin represents the vehicle's position when recorded, which may differ from the exact location of a nearby café or park.
5. **Show vehicle readings alongside the driver's account**
   - Distinguish collected vehicle data from the voice memo.
   - Verify historical warning access and follow-up voice conversation support before implementation.

### Applying the Pleos design system

- Use colors, typography, icons, and components from `docs/Pleos_Connect_design_assets_215.fig`. Apply semantic Light/Dark colors and communicate selection, missing data, and errors beyond color alone.
- Use an Asta Sans title/body/label hierarchy. Test Korean, English, and enlarged fonts for clipping; do not turn a documented minimum into a universal body size. [Typography](https://document.pleos.ai/docs/connect/guide/docs-design/foundations/typography)
- Make key status understandable within 1–2 seconds. Provide visual input feedback within 0.25 seconds and show processing separately for longer saves; this is not a save-completion deadline.
- Verify text/icon contrast of at least 4.5:1. Check physical text heights of 5 mm for key information and 3 mm for secondary information on target displays. Hide long memos, automatic scrolling, and decorative animation while driving. [UI Design Guide](https://document.pleos.ai/docs/connect/guide/docs-design/Introduction/intro)
- Build an Adaptive Icon with separate foreground/background layers. Distinguish the web guide's 48 × 48 dp icon grid from Figma pixel/grid labels; neither defines the touch-target size. [Iconography](https://document.pleos.ai/docs/connect/guide/docs-design/foundations/contents-creation/iconography), [App Icon](https://document.pleos.ai/docs/connect/guide/docs-design/foundations/contents-creation/app-icon)

### Headless capture and design-token candidates

- Aim to handle approved Gleo calls without foregrounding the app. Continuous app-owned microphone listening and a proprietary wake word are outside the MVP.
- Deliver in-motion feedback through the verified Gleo path. Do not force a screen, Toast, or OS notification solely to show progress. Show saved scope in the library/details while parked.
- Define Compose token roles below, but populate HEX values, sizes, spacing, and radii only from verified sources. These names are ChaKak candidates, not official API names.

| Candidate group | Role | Value source |
| --- | --- | --- |
| `ChaKakColors` | Background, surface, primary/secondary text, selection, error, missing data | Semantic Colors and Figma Light/Dark |
| `ChaKakTypography` | Title, body, label hierarchy | Typography and Asta Sans; validate target density |
| `ChaKakSpacing` / `ChaKakShapes` | Panel padding, gaps, control shapes | Source component dimensions; unresolved values stay TBD |
| `ChaKakIcons` | Place, vehicle status, memo, Back | Iconography and Figma grids; separate icons from touch targets |
| `ChaKakElevation` / `ChaKakMotion` | Layers and minimal state transitions | Shadow/Animation; no decorative driving effects |

Record each token's URL or Figma node, unit, theme, and verification status. Distinguish source values from implementation mappings; do not finalize unverified tokens using Material defaults.

---

## 7. Core User Flows

## 7.1 Instant Capture Flow

### Entry point

The driver invokes the voice assistant and speaks a capture command. While parked, a “Capture now” button in the library invokes the same function. Explain permissions and obtain consent while parked; do not require permission setup or keyboard input while driving.

### Inputs

- “Capture this”: uncategorized record
- “Capture this location”: place record
- “Capture the vehicle status”: vehicle status record

### Processing

1. For a permitted request, first commit the record ID, command time, category, and `collecting` state to Room. If this fails, report failure and do not start collection.
2. Call Location and Vehicle adapters asynchronously for the same record ID. Check availability/permissions and persist each result's value, source, observation time, receipt time, and status.
3. Use an initial collection budget of at most 5 seconds from request receipt. Measure the full Action, including final database work and Callback, against Gleo's 7-second timeout.
4. Finalize as `complete` when all selected collection items are obtained; use `partial` for missing, denied, unsupported, or timed-out items. Never fill failed fields with normal values.
5. Announce the actual saved scope. Ask once about a memo if the follow-up path has been validated; otherwise offer editing while parked.

### Error handling

- Do not announce success if the local database save fails.
- Do not replace unsupported or failed readings with normal values.
- ChaKak partial-save policy: the first commit establishes the base record, not complete collection. Preserve it even when location and vehicle data are unavailable, disclosing missing information in final feedback. If later observation updates fail, retain the base record and report only what actually persisted. Never report success before the base commit.

---

## 7.2 Optional Voice Memo Flow

### Purpose

Attach context that numbers and coordinates alone cannot explain.

### Interactions

- Save a response such as “Found a beautiful park on the right” as text.
- Link the response to the original record ID.
- End without another question after “No,” silence, a timeout, or recognition failure.
- Preserve the basic record if memo attachment fails.

### Implementation dependency

Gleo Callback supports `asking` for follow-up interaction. Validate the post-save question sequence and record-ID transfer into the next Action in the Emulator. Do not hold the basic capture Action open while waiting for a memo. If correlation cannot be verified, offer memo editing while parked; never attach an ambiguous response to an arbitrary recent record. [Callback guide](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/action_callback)

---

## 7.3 Record Library Flow

### Purpose

Find previously captured moments while parked.

### Components

- All / Places / Vehicle Status filters
- Chronological record list
- Record time, category, and title or memo preview

### Interactions

- Select a record to open its details.
- Find uncategorized records in All and assign a category later.
- Edit the title, category, or memo, and delete records.

---

## 7.4 Place Review Flow

### Scenario

1. The driver notices a view and says, “Capture this location.”
2. The app saves location, time, and available vehicle status, categorized as a place.
3. The driver optionally adds a voice memo about the surroundings.
4. While parked, the driver opens Places, checks the saved location and memo, and adds a title.
5. The record becomes a clue for finding the place on a future visit.

### UX notes

The saved point is the vehicle's position and does not guarantee the exact location of a nearby venue. Navigation integration is a future consideration.

---

## 7.5 Vehicle Status Review Flow

### Scenario

1. The driver notices a warning or unusual symptom and says, “Capture the vehicle status.”
2. The app saves location, time, and available vehicle status under Vehicle Status.
3. The driver adds a memo such as “An unusual noise started after going over a speed bump.”
4. While parked, the driver reviews the status summary and enriches the symptom description.
5. At a repair shop, the driver uses the time, vehicle data, and memo to explain what happened.

### Recording scope

The baseline scope is data available when the command is received. Historical access to a warning that has already disappeared requires verification; recovery of past warnings is not guaranteed before support is confirmed.

---

## 7.6 Record Editing and Deletion Flow

### Entry point

Open record details while parked.

### Actions

- Edit and save the title, category, or memo.
- Delete the selected record.
- Restrict detailed interaction if driving starts or parking state becomes unavailable.

### Deletion policy

Confirm deletion in a popup while parked. Recovery/trash is outside the MVP; disclose this before deleting.

## 7.7 Unavailable Access and Restart Flow

### Purpose

Preserve existing records when SDK or voice access is unavailable and explain what can be verified.

### Actions

- Show permission denial or unsupported SDK status on the affected detail field. Do not open settings while driving.
- After interruption following the initial commit, recover remaining `collecting` records as `partial` and mark unfinished items `interrupted`. Do not fill past moments with current sensor readings.
- Ignore late responses after collection closes; never recreate deleted records.
- Label mock screens/records as test data. Never automatically fall back to mock data after a real SDK failure.

---

## 8. Information Architecture

## Main surfaces

1. Voice capture processing and feedback (logical flow; no mandatory screen)
2. Record library
3. Record detail
4. Vehicle status summary
5. Record editing

## Suggested screen routes

- /capture
- /records
- /records/{recordId}
- /records/{recordId}/vehicle-status
- /records/{recordId}/edit

These routes are logical screen identifiers. Android navigation and menu layout will be determined during development. Account login and bottom tabs are not required.

### App window and system UI boundaries

- Status bar, Driving view, GNB, and App library belong to the OS. Render ChaKak within its assigned Application window without reproducing or covering system vehicle warnings.
- Use a single-column list → detail flow in narrow Portrait windows and a list/detail layout in wide Landscape windows. Restore the selected record, filter, and editing draft after resizing or orientation changes.
- Validate Connect-S app-area ratios of 1:1 / 2:0 and Connect-W ratios of 2:1 / 1:2. Test available presets for Connect-S 17/14.6/12.9-inch and W/W-Extended/WF, adapting to actual window bounds, insets, and density rather than fixing the app to full-display dimensions.
- Handle system panels and keyboards covering the app, plus background/foreground transitions. `/capture` is a logical processing route; voice commands need not foreground a detail Activity.

Sources: [Display Layout](https://document.pleos.ai/docs/connect/guide/docs-design/getting-started/layout), [Window Policy](https://document.pleos.ai/docs/connect/guide/docs-design/getting-started/window), [App structure and behavior](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/app-structure-screen-model).

---

## 9. Screen Specifications

## 9.1 Voice Capture Feedback

The in-motion headless target has no dedicated Activity. These are processing states; visual presentation applies when the parked app is visible.

### Required UI and responses

- Capture processing status
- Save success or failure feedback
- Optional memo question after a successful save

### States

- Saving base record / Collecting / Complete / Partial / Base save failed
- Awaiting memo / Memo saved / Finished without memo

---

## 9.2 Record Library

### Required UI

- All, Places, and Vehicle Status filters
- Chronological list
- Record previews and detail entry

### States

- Loading / Empty / Normal / Read error / Restricted while driving

---

## 9.3 Place Record Detail

### Required UI

- Record title and category
- Command receipt timestamp
- Saved location and memo
- Notice that the vehicle position may differ from the venue location
- Edit and delete actions

### States

- Normal / Location unavailable or retrieval failed / Restricted while driving

---

## 9.4 Vehicle Status Summary

### Required UI

- Command receipt timestamp and per-item collection timestamps
- Available warning information, odometer reading, and tire status
- Support and retrieval status for each item
- Driver's memo

### UX notes

Distinguish vehicle readings from the driver's account. Do not represent unsupported or failed readings as normal conditions. Actual fields depend on SDK verification.

---

## 9.5 Record Editing

### Required UI

- Title input
- Category selector
- Memo editor
- Save action and result feedback

### States

- Editing / Saving / Save failed / Restricted while driving

---

## 9.6 Shared Components and Input Rules

| Element | ChaKak application |
| --- | --- |
| Page Navigation | Library title, detail Back navigation, and minimal secondary actions |
| Tabs | Single selection of All, Places, or Vehicle Status with visible selection |
| Basic Button | Capture now and Save while parked; Loading state prevents duplicate activation |
| Text Field | Title/memo editing with distinct Focused, Disabled, Read Only, and Invalid states |
| System Popup | Parked deletion confirmation with separate Cancel and Delete actions |
| Toast | Brief completion feedback; retain failure/missing-data reasons in detail states too |

- Resize text-editing windows for the IME so inputs and Save remain accessible. Avoid unnecessary IME movement in dialogs without text fields.
- CCP support is optional for user-installed apps. The MVP completes essential parked operations by touch and declares CCP unsupported. When CCP Back is delivered, navigate back within the app and hide rather than terminate at the root; persist state.

Sources: [Page Navigation](https://document.pleos.ai/docs/connect/guide/docs-design/components/page-navigation), [Tabs](https://document.pleos.ai/docs/connect/guide/docs-design/components/segmented-menu), [Basic Button](https://document.pleos.ai/docs/connect/guide/docs-design/components/actions/basic-button), [Text Field](https://document.pleos.ai/docs/connect/guide/docs-design/components/fields/text-field), [System Popup](https://document.pleos.ai/docs/connect/guide/docs-design/components/popups/system-popup), [Toast](https://document.pleos.ai/docs/connect/guide/docs-design/components/toast), [IME](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/ime-window-resize-handling-guide), [CCP](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/ccp-rotary-controller-development).

---

## 9.7 Compose Component Structure and States

This is a pre-implementation design; Compose previews are future deliverables. UI components must not call SDKs directly.

| Surface | Component tree | States and behavior |
| --- | --- | --- |
| App shell | `ChaKakTheme` → `AppWindowScaffold` → `RestrictionGate` → screen | Apply window bounds, insets, and theme; show restriction feedback for driving/unknown state |
| Library | `RecordLibraryScreen` → `RecordCategoryTabs` + `RecordList` → `RecordRow` | Loading, empty, read error, normal; show completeness and test-data labels on rows |
| Detail | `RecordDetailScreen` → `CaptureSummary` + `LocationSection` + `VehicleObservationList` + `MemoSection` + actions | Values, units, sources, times, and per-field states; icons/text when images are absent |
| Editor | `RecordEditScreen` → title field + category selector + memo field + Save | Read-only, invalid input, saving, failure; preserve draft and prevent duplicate saves |
| Deletion | `DeleteRecordDialog` → target summary + Cancel + Delete | Parked confirmation; retain the record if deletion fails |

### Shared data-state presentation

| State | Presentation rule |
| --- | --- |
| Success | Actual retrieved/persisted values and observation time |
| Partial success | Confirm the record exists; identify missing fields and reasons |
| Base save failure | Report failure; no success Toast or memo question |
| Permission denied | Identify missing permission; offer remediation only while parked |
| Unsupported | Identify unavailable functionality; never substitute normal values |
| Delayed/timed out | Distinguish collecting from timeout; no endless loading |
| Stale location/interrupted | Show observation time or interruption; do not imply freshness |
| Mock | Label all relevant screens and records as test data |

Interpret `Bar` as the documented window/scroll bar and `Indicator` as value/selection presentation. Neither automatically defines app navigation or asynchronous loading; use Page Navigation for navigation.

---

## 10. Roles and Permissions

## Driver

- Create records and add memos by voice
- View, edit, and delete local records while parked

## Platform permissions and usage conditions

- Verify permissions required for location and vehicle data access.
- Verify voice app invocation, command delivery, and response conditions.
- Apply platform driving restrictions and parking state to detailed viewing and editing.
- Separate accounts or remote access for maintenance personnel are outside the MVP.

### Driving restrictions and data access

- Apply the current `CarUxRestrictionsManager` state and `onUxRestrictionsChanged()` updates. Zero speed or gear P alone does not authorize use. ChaKak detail viewing, filtering, editing, and deletion require both confirmed parking and platform permission.
- Declaring Distraction Optimization (DO) does not guarantee in-motion access. Verify screen and voice Action availability for the category, region, and invocation mechanism; background invocation must not bypass restrictions. [Restriction implementation](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/driving-restriction-development-guide), [Regional policy](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/driving-restiction-policy)
- Candidate read permissions are `pleos.car.permission.CAR_DRIVE` for Odometer, `pleos.car.permission.CAR_SAFETY` for Safety/Tire, and `pleos.car.permission.FUSED_LOCATION`. Check each function's `@RequiresPermission`; do not request vehicle-control permissions. Handle runtime location denial and revocation. [Vehicle permissions](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/vehicle-permission), [Location permissions](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/declaring-permissions)
- Explain location and voice-memo purposes while parked. Retain required text rather than raw audio and exclude precise location/memo content from logs. Design shared-vehicle visibility and delete-all handling for driver changes. Do not claim Gleo processing is entirely offline or in-vehicle. [Privacy design](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/security-privacy)

### Feature availability matrix

The cited CarUxRestrictions/regional policies are the official basis; this table also includes ChaKak's deliberately narrower UI scope. Actual in-motion voice eligibility remains unverified.

| Feature | Parking confirmed + platform allows | Driving/restrictions active | State unknown |
| --- | --- | --- | --- |
| Gleo headless capture | After approval, permission, and invocation validation | Unverified; disabled until approval and policy/runtime verification | Disable new requests unless eligibility can be established |
| Follow-up voice memo | After Gleo correlation validation | Verify conversation continuity and eligibility separately | Do not start a new conversation |
| Touch capture, list, filters, details | Available | Restricted by ChaKak policy | Restricted |
| Title/category/memo editing and deletion | Available | Restricted | Restricted |
| Permission requests/settings | Available when needed | Do not require user interaction | Do not require user interaction |
| Committed records | Preserve | Preserve; do not start newly restricted collection | Preserve |

Eligibility and persistence are separate concerns. Cancel/finalize in-flight SDK work according to platform policy while retaining saved observations and missing-data reasons.

---

## 11. Functional Requirements

## Must-have

- Connect voice requests to a shared capture function
- Categorize commands as place or vehicle status when specified
- Collect location, time, and available vehicle status
- Store per-item timestamps and unsupported or failed retrieval states
- Save basic records locally
- Attach an optional voice memo to the same record
- Provide parked touch capture, permission explanations, and partial-save status
- Provide All, Places, and Vehicle Status lists
- Support detail viewing, title/category/memo editing, and deletion
- Provide a vehicle status summary
- Restrict detailed interaction based on driving and parking state

## Extensions subject to support verification

- Surround-view images
- Phone integration
- Consent-based backup and synchronization
- Navigation links from place records
- Maintenance consultation integration for selected vehicle status records

---

## 12. Non-Functional Requirements

- Readable information layout for vehicle displays
- Brief voice responses and minimal interaction while driving
- Isolation of memo failures from basic record persistence
- Explicit missing-data and error states for vehicle-specific support differences
- Restricted detailed interaction when parking state is unknown
- Local storage in the vehicle by default
- User choice for external sharing
- Validation of core flows and failure scenarios in the Pleos Emulator
- Bound collection so database processing and Callback finish before Gleo's 7-second Action timeout. ChaKak proposes an initial collection budget of at most 5 seconds, adjusted after measurement including cold start. Memo waiting is a separate follow-up operation.
- Finalize storage limits, retention, and memo response timeout before implementation

### Resources, lifecycle, and voice output

- Apply non-navigation targets of CPU below 5% while driving, at most 25% while stationary, and RAM at most 400 MB. Collect on request; continuous location tracking and vehicle polling are outside the MVP. [Performance guide](https://document.pleos.ai/docs/pleos-only/vehicle-app-planning-guide/performance-resource-mgmt)
- Restore committed records after hiding, process recreation, and Suspend. Release unused SDK listeners/resources and end incomplete conversations separately from persisted records.
- Let Gleo provide voice output by default. If app-owned TTS is introduced, avoid duplicate Gleo speech, stop output on audio-focus denial/loss or Suspend, and release focus. Apply the recommended 150–200 ms pre-roll silence to app-owned guidance. [Audio focus](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/audio-focus-development-guide/audio-focus-development), [Suspend](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/audio-focus-development-guide/suspend-mode-audio-focus), [Voice guidance](https://document.pleos.ai/docs/pleos-only/vehicle-app-developer-guide/voice-guidance-preroll-silence-guide)

---

## 13. Recommended Tech Stack

## Application

- Kotlin with Jetpack Compose UI and Compose Preview
- Room persistence; ViewModel and Coroutines/Flow for state delivery
- Pleos Connect / Android Automotive OS runtime
- Pleos Emulator for validation

## Voice integration

- Gleo Interface
- Instant capture and memo attachment functions defined in App Schema
- Requests and processing results connected through Gleo Action/Callback

## Data collection

- Validate Pleos FusedLocation SDK first; do not assume equivalent behavior from generic Android/Google location APIs.
- Evaluate the Safety, Odometer, and Tire domains of Vehicle SDK
- Verify actual availability of coordinates, accuracy, observation timestamps, and vehicle status fields

## Storage and application structure

- Room local database with transaction and migration management
- Separate voice request handling, data collection, persistence, and presentation responsibilities
- Attach memos through the record ID after basic saving succeeds
- Initial MVP direction: no separate server

## Why this stack

Connect vehicle data directly to voice requests and validate the two core scenarios with limited server operations. Verify API names, permissions, and capabilities against official documentation and the Emulator before implementation.

### Development baseline and availability

- Prepare Android Studio, Android SDK API 34, `minSdk >= 28`, and Gradle 8.0+. Documentation examples use `ai.pleos.playground:Vehicle:2.3.1` and `ai.pleos.playground:FusedLocation:1.1.3`; pin versions compatible with the actual project/Emulator. Configure the AVD for Cold boot and 4 GB RAM. [Getting started](https://document.pleos.ai/docs/pleos-only/getting-started), [Environment setup](https://document.pleos.ai/docs/connect/guide/getting-started/application-development/setup-connect-sdk-emulator), [FusedLocation setup](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/project-setup)
- The project/application guide lists Vehicle State as available, but FusedLocation, TTS, STT, and Vehicle Control as preparing. Published API documentation does not establish project access. Check My Project and runtime availability; do not present unavailable functionality as implemented in demonstrations. [SDK access guide](https://document.pleos.ai/docs/pleos-only/create-my-project)

### Gleo integration contract

- Propose two Actions: `CAPTURE_RECORD` and `ADD_RECORD_MEMO`. Stay within the three-function limit, pass category as a parameter, and write descriptions in English. Validate missing required values, types, and enums in the app. [App Schema](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/app-capability)
- Package `assets/schema.json` and per-Action manifest filters. Validate `component="service"` broadcast invocation for capture without opening a screen. Schema registration/review and activation are prerequisites. [Schema delivery](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/deliver-schema)
- Return truthful `success` / `asking` / `fail`, an English `message`, and minimal necessary `data`. Localized UX examples are not literal Callback strings. Validate `recordId` as an app-defined correlation field, not an automatic platform guarantee.
- Verify the example's `ACTION_PARAM` JSON input and `APP_ACTION_RESPONSE` response. The prose describes individual extras while the code uses a JSON payload; check the installed Gleo version's actual payload. Exclude raw memos/coordinates from debug logs. [Code examples](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/examples)

### Data retrieval contract

- Initialize Vehicle, obtain domains, and check `checkXxxCapabilities` for READ or READ_WRITE and supported areas before Get. Distinguish null, unsupported, permission errors, and timeouts; release resources when finished. This read-only app does not call Set. [Capability](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/check-capability), [Get](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/get-property)
- Check FusedLocation `isSupported()`, then `initialize()`. `getLastKnownLocation()` returns an existing observation, not a fresh fix; handle observation time, confidence, and null. If needed, receive callbacks for a bounded period, then unregister and release. [Location introduction](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/intro), [Location access](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/location-data-access)
- Save only supported Safety warning states, Odometer data, and actual Tire fields. Do not interpret warning states as history, DTCs, or diagnosis. Mark location missing when unavailable rather than claiming success through an unverified fallback API.

### Application architecture

- **Presentation**: Compose screens, design tokens, and ViewModels. Convert Room observations into UI state without inventing vehicle values.
- **Domain/Application**: `CaptureRecordUseCase`, `AddMemoUseCase`, `UpdateRecordUseCase`, and `DeleteRecordUseCase` own initial persistence, collection deadlines, deduplication, memo correlation, and interaction eligibility.
- **Data**: `RecordRepository` with Room DAOs/entities. Translate SDK callbacks into observation models and apply atomic updates to the same record.
- **SDK adapters**: isolate integration behind `GleoAdapter`, `VehicleStateProvider`, `LocationProvider`, and `RestrictionProvider`. Manage connections, permissions, collection, and listener cleanup inside adapters.
- **Mock providers**: implement the same interfaces for success, delay, denial, unsupported, and error scenarios. Separate providers through build/DI configuration and persist each observation's `source_kind`. Release defaults to real providers; automatic mock fallback is prohibited.
- **Headless execution**: collection coordination must not depend on a screen ViewModel surviving. Verify asynchronous broadcast handling, execution duration, process lifetime, and Android constraints. `component="service"` identifies Gleo broadcast invocation, not unlimited Android Service execution.

### STT and version verification

- SpeechToText converts speech into text; it does not replace Gleo invocation/wake-up. Add app-owned memo STT only when justified and authorized. Verify processing location, registration, and network conditions separately for ON_DEVICE and HYBRID/SERVER. [SpeechToText introduction](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/SpeechToText/intro)
- Getting started uses Vehicle 2.3.1, while the Vehicle introduction/sample use 2.3.2. Service references also differ: 2.3.21 in the introduction and 2.3.22 in the sample. Record and validate the SDK/Service/Emulator combination. Keep product `minSdk >= 28`; do not copy the sample's minSdk 23. [Vehicle introduction](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/Vehicle/intro), [Sample tutorial](https://document.pleos.ai/docs/pleos-only/tutorial/vehicle-sdk-sample-app)

---

## 14. Suggested Data Model (MVP)

This is an implementation draft. Types and constraints will be finalized during development.

### records

- id: record ID
- category: unclassified / place / vehicle_status
- title: optional title that can be added while parked
- command_received_at: command receipt timestamp
- capture_status: collecting / complete / partial; collection progress/completeness after base persistence
- finalized_at: collection end time; absent while collecting
- data_mode: real / mock; distinguish real and test records
- request_key: app-managed deduplication key; delivery mechanism requires validation
- note_text: optional memo entered by voice or editing
- created_at
- updated_at

### record_locations

- record_id
- latitude, longitude: stored when available
- accuracy: stored only if the API provides accuracy and its unit
- confidence: optional SDK confidence; not interchangeable with accuracy
- observed_at: location observation timestamp
- collected_at: app receipt time for the location result
- source_kind: pleos / mock
- source_api: retrieval source identifier
- collection_status: pending / available / unsupported / permission_denied / unavailable / timeout / error / interrupted
- unavailable_reason: optional reason such as unsupported, permission denied, or retrieval failed

### vehicle_observations

- id
- record_id
- domain: verified domain such as Safety / Odometer / Tire
- field_key: identifier of an actually supported field
- value: collected value; absent when unavailable
- unit: stored where applicable
- observed_at: source observation timestamp, if provided
- collected_at: app receipt time for each result
- source_kind: pleos / mock
- source_api: retrieval source identifier
- collection_status: same state set as record_locations
- unavailable_reason: optional

### Future extension candidate

Images are not required fields. Consider an optional attachment model after verifying access APIs, permissions, supported vehicles, and permitted use while driving.

### Persistence and conversation state

- Use nullable values and explicit states for missing observations: `unsupported`, `permission_denied`, `unavailable`, `timeout`, or `error`. Never substitute zero or a normal warning state.
- Distinguish source `observed_at` from app-receipt `collected_at`, marking absent source timestamps explicitly. Late results must not silently change the recorded moment.
- Track memo context by `record_id`, expiry, and handling state. Do not redirect expired, duplicate, or deleted-target responses to another record. Finalize redelivered-Action identification after Gleo validation.

### Asynchronous persistence rules

- Separate the base-record creation transaction from observation updates. Commit initial `collecting` and request time before collection.
- Apply updates only to an existing record ID and active collection session. SDK responses must not overwrite edited titles or memos.
- On deadline, mark unfinished items timeout and persist `finalized_at`; after process-interruption recovery, use interrupted. Do not represent an initial database failure as a successful record.
- `complete` means the defined collection set finished, not that every vehicle condition is normal. Validate record/observation-state migrations when changing the Room schema.

---

## 15. Edge Cases

- Location permission denied, no fix, or stale location
- Warning, odometer, or tire data unsupported on a particular vehicle
- A warning disappears before the command and is unavailable from current readings
- Some vehicle status queries fail or time out
- Local database save fails or storage is full
- Memo declined, no response, or recognition failure after basic saving
- A late memo response could be confused with another record
- Parking state is unavailable or driving begins while details are open
- A place record has no location or its pin differs from the intended venue

Unresolved handling policies are tracked in the next section.

---

## 16. Open Questions for Implementation

Resolve these five decisions during design and SDK feasibility work, recording evidence.

1. **Category and in-motion eligibility**: resolve Mobility Services/Lifestyle, DO requirements, regional/driving-state headless Action eligibility, and competition participants' Gleo approval access. Keep in-motion features unavailable until verified.
2. **Actual SDK combination and data scope**: establish Vehicle SDK/Service/Emulator versions, FusedLocation/STT project access, and actual Safety/Odometer/Tire methods, permissions, units, timestamps, and capabilities. Separate documentation, approval, and runtime results.
3. **Voice and asynchronous transactions**: validate payloads, `asking` sequencing, recordId transfer, deduplication, process lifetime, collection budget, and memo timeout. Verify base-record preservation and late-response handling.
4. **Design and privacy policy**: resolve Figma/web units and versions, final tokens/touch targets, stale-location thresholds, retention/capacity, shared-vehicle separation, and delete-all behavior. Unverified dimensions remain TBD.
5. **Validation and submission readiness**: define real/mock disclosure, required environments, team/APK naming, repository visibility, reviewer identity/access checks, README, and submission paths. Define pilot metrics/periods separately without unsupported MAU targets.

Surround-view images, phone integration, and backup remain extension candidates, not additional MVP prerequisites.

---

## 17. Build Priorities

### Phase 0: Source review and design deliverables

Prepare these before app code. These are planned artifacts, not files created or verified by this specification update.

| Deliverable | Required content |
| --- | --- |
| `docs/pleos/verified-links.md` | Source title, URL, review date, key requirements, reading status; unread sources marked UNREAD |
| `docs/design/design-tokens.md` | Sourced color/type/spacing/icon candidates; unverified values stay TBD |
| `docs/design/ux-flows.md` | Headless target, parked library, failure flows, feature-availability matrix |
| `docs/design/screens.md` | Narrow/wide and day/night layouts, component trees, empty/partial/access-failure states |
| `docs/technical/sdk-feasibility.md` | Separate documentation, project approval, Emulator, and real-vehicle evidence; five open decisions |

### Phase 1: SDK feasibility

- First verify project access, Gleo approval/schema registration, headless invocation, and in-motion eligibility.
- Then validate Vehicle capability/Get, FusedLocation, optional STT, and follow-up conversation.
- Record unavailable-feature alternatives and real/mock demonstration scope.

### Phase 2: Persistence and test providers

- Room models, DAOs, repository, and adapter interfaces
- Initial persistence, asynchronous updates, partial results, deduplication, interruption, deletion, and late responses
- Separate mock providers for failures, denial, unsupported features, and delays

### Phase 3: Library and Compose design system

- Sourced tokens, app shell, list/detail/editor/deletion
- Compose previews for narrow/wide, day/night, font scaling, IME, and restriction states
- Icon/typographic presentation for records without images

### Phase 4: Real capture and optional memo

- Integrate validated Gleo, Vehicle, and Location adapters
- Return results within the 7-second Action limit and persist actual observations
- Correlate approved follow-up memos; preserve records and provide parked editing on failure

### Phase 5: Integration validation

- Verify CarUxRestrictions, Virtual Console, FormFactorConsole, restart, and Suspend
- Record real/mock and Emulator/vehicle results separately
- Install/run the release APK and verify core user flows

### Phase 6: Competition submission

Apply the requirements in [git-manual.md](git-manual.md).

- Prepare source code and an executable release APK in the same GitHub repository.
- Use `apk/팀명_앱이름_release.apk`, substituting the confirmed team/app names.
- If the APK exceeds GitHub upload limits, publish it through GitHub Releases and submit that Release page URL.
- Enter the repository URL and APK path or the instructed Release URL in the submission page.
- For a private repository, invite the announced reviewer GitHub account as a Collaborator and verify access before the deadline. Check the write access granted by personal private repositories before inviting.
- README must include app name/one-line summary, run instructions, test environment/settings, and APK location or Release link. Distinguish SDK approval prerequisites and mock use in the run instructions.
- Demonstrate only verified functionality/support in the video. Repository creation, upload, invitations, and submission are not actions performed by this documentation update.

### Validation and submission additions

- In Phase 1, verify project/SDK access, Gleo schema activation, and in-motion eligibility. Validate design tokens and Portrait/Landscape shells early.
- Change gear, odometer, and supported Telltales in Virtual Console and compare saved readings. Test unavailable console fields such as Tire through another supported environment or explicitly labeled test doubles, separate from real-vehicle validation. [Virtual Console](https://document.pleos.ai/docs/pleos-only/app-test-guide/virtual-console-guide)
- Record presets, orientation, and dp/DPI/px in FormFactorConsole; test themes, IME, Back, driving transitions, and process restoration. Use a standalone Emulator window and Host Display Bridge for form-factor testing. [Form-factor tests](https://document.pleos.ai/docs/pleos-only/app-test-guide/form-factor-test-guide)
- Distinguish competition submission from App Market review. For market distribution, prepare the APK, icon/screenshots, privacy policy, location terms, permission/category disclosures, and operating instructions. [App submission](https://document.pleos.ai/docs/pleos-only/distribute/app-submission-guide)

---

## 18. UX Acceptance Criteria

- A user can start a record with “Capture this” without a category selection screen.
- Commands mentioning a place or vehicle status assign the category within the same capture flow.
- The app asks about a memo only once after a successful save.
- The basic record remains when no memo is provided or memo saving fails.
- A failed basic save is not announced as successful.
- While parked, a user can review time, location, vehicle status, and memo and enrich the record.
- A user can understand that the location represents the vehicle's position.
- Unsupported or failed vehicle readings cannot be mistaken for normal values.
- Detailed interaction is restricted while driving or when parking state cannot be confirmed.

### Pleos acceptance checks

- Permitted voice Actions return actual save results before the 7-second timeout without waiting for a memo. Partial saves disclose missing data; duplicate/late responses cannot alter another record.
- Restriction changes promptly block affected input, filtering, and detail operations while preserving the basic record. Recheck current restrictions when parking resumes.
- Supported Portrait/Landscape and Light/Dark combinations allow viewing and saving without clipping or overlap. Keyboard, system-panel, and app-hide transitions preserve consistent selection/editing state.
- Permission denial, null/stale location, and unreadable capabilities never appear as normal data. Verify FusedLocation runtime support separately from project access.
- Any app-owned audio handles emergency/call priority, focus loss, and Suspend. Do not promise offline voice operation before its network dependency is verified.

### Implementation and submission acceptance

- Before app code, establish source evidence, screen states, token candidates, and the five open decisions. UNREAD/TBD cannot be reported as verified.
- Persist the record ID and request time before slow SDK collection. Interruption, deletion, late callbacks, or memo failure cannot mix data across records.
- Distinguish base-save failure, partial results, denial, unsupported data, and timeout in UI/feedback. Never display fabricated images or normal sensor values.
- Mock previews carry test labels; a release build cannot turn real SDK failures into mock successes.
- Release APK naming/location and README run instructions satisfy `git-manual.md`; verify repository/APK access for submission.

---

## 19. References

### Input materials and precedence

| Source | Specification application |
| --- | --- |
| [Astra starting prompt](chakak_astra_start_prompt.md) | Design before code; components/states, matrix, tokens, and five decisions |
| [Pleos index and design instructions](chakak_pleos_docs_for_astra.md) | Headless target, parked UI, Compose/Room/adapters/mocks, unverified SDK distinctions |
| [Complete sidebar URL index](pelos_guide_url.txt) | Source discovery; URL collection is not full-content review or SDK validation |
| [GitHub submission instructions](git-manual.md) | Release APK name/path, Releases alternative, private access, README requirements |
| [Design assets](docs/Pleos_Connect_design_assets_215.fig) | Color, typography, and component reference |
| [Display guide](docs/Pleos_Connect_display_guide_215.fig) | Window, orientation, theme, and form-factor reference |

Official sources govern platform behavior/design; cross-check local summaries against them. Follow the supplied git-manual.md for competition repository submission. Label product choices as ChaKak design decisions. The index's 44 source links were opened during this conversation; this does not claim reading all 911 unique URLs in the full index. SDK execution/approval and complete Figma rendering remain separate validation work.

## Pleos Sources and Review Scope

Reviewed: 2026-11-03. Review covered linked design pages and development/validation material relevant to ChaKak. Distinguish public documentation, local design resources, and checks still required in the actual project.

| Source | Application |
| --- | --- |
| [UI Design Guide](https://document.pleos.ai/docs/connect/guide/docs-design/Introduction/intro) | Reviewed Getting Started layout/status bar/GNB/app library/window policy, Foundations colors/typography/icons/app icons/animation/shadow, and component descendants |
| [Getting started](https://document.pleos.ai/docs/pleos-only/getting-started) | Reviewed project/SDK access, setup, planning/development, Vehicle permissions/Capability/Get, Virtual Console/form-factor tests, and submission guidance |
| [Gleo Interface](https://document.pleos.ai/docs/pleos-only/app-integration-guide/guide/core-concepts) | Reviewed App Schema, Callback, code examples, and schema delivery |
| [FusedLocation](https://document.pleos.ai/docs/pleos-only/connect-sdk-pleos/FusedLocation/intro) | Reviewed dependencies, permissions, location access, initialization/release, and errors |
| [Design assets 2.1.5](docs/Pleos_Connect_design_assets_215.fig) | Inspected text/component names for color tokens, Asta Sans, icon grids, buttons, fields, and popups |
| [Display guide 2.1.5](docs/Pleos_Connect_display_guide_215.fig) | Inspected Connect-S/W/Waterfall, Portrait/Landscape, Light/Dark, and brand variant names |

Figma inspection covered archive metadata, thumbnails, and text extracted from decompressed canvas data. It did not include rendering every frame or verifying pixel-level dimensions. Confirm exact spacing, touch targets, and token values in the original Figma files during implementation. Distinguish local Connect-L labels from the current web guide's Connect-S 12.9-inch classification; `215` in asset filenames is not an SDK or Emulator version.

Partial saving, parked touch capture, two Actions, the 5-second collection budget, and deletion confirmation are ChaKak design decisions in this spec, distinct from platform requirements. This documentation review does not establish successful SDK execution, access approval, or real-vehicle support.

## Competition context

- Hyundai Motor Group in-vehicle app development competition
- Slogan: Code your App into Pleos App Market
- Assignment: in-vehicle app ideas and development using Pleos Playground public APIs

### Evaluation items recorded in the original proposal

| Criterion | ChaKak's approach |
| --- | --- |
| Vehicle-specific value | Voice capture while driving, combined vehicle status and location, detailed interaction while parked |
| Creativity | One instant-recording flow for places to revisit and maintenance context |
| Technology use | Validate Gleo voice integration and use of location and Vehicle SDK capabilities |
| Business potential | Defined drivers, repeat-use value, local-storage operations, and expansion potential |
| Bonus items | Prepare a demo video, GitHub source code, and APK |

Competition details were carried over from the original proposal. Check the latest official announcement before submission.

### Supporting documents

- Structural reference: `/Users/hannah.b/Module/moniq/spec.md`

### Business and expansion considerations

- Validate place recording as an everyday use case and vehicle status recording as support for maintenance conversations.
- First-record creation rate, record revisit rate, and reuse within four weeks are candidate pilot metrics; finalize their definitions, population, and observation periods.
- Any MAU forecast should state assumptions about supported vehicles, reachable users, installation rate, and activation rate.
- Consider phone integration, consent-based backup and synchronization, navigation, and maintenance integration after checking SDK support and usage conditions.
- Evaluate map, voice, and synchronization costs when expanding, and account for vehicle-specific support differences.
