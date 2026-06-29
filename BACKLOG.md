# Backlog and To Do List

---
## Event Management

* Update draft event
* Cancel event
* Complete event
* Pagination for event listing
* Filter events by status
  * Allow only organisers and admins to view unpublished events
* Search events by title
* Allow organisers to edit published events

## Validation & Error Handling

* Problem Details (RFC 9457)
* Standardise business exceptions
* Global exception handling

## Security

* Authentication
* (Member) User role (e.g. participant)
* Organiser role
  * can create events
  * can edit and cancel events
  * can publish events
* Admin role
  * _same as organiser role_
  * can change the roles of users and organisers
* Restrict event creation to authenticated users
* Restrict event publication to organisers/admins
* Restrict event updates to organisers/admins
* Add admin capabilities

## Attendance
_Likely flow_
* User signs in
* User attends an event
* User cancels attendance
* List attendees for an event
* Prevent duplicate attendance
* Prevent attendance when event capacity is reached
* Waiting list support

## Domain

* Introduce User aggregate
* Introduce Attendance aggregate
* Add organiser to Event
* Prevent publishing events in the past

## Infrastructure

* OpenAPI / Swagger documentation
* CI pipeline
* Docker image for application
* Observability (Actuator, metrics)
* Structured logging

## Nice to Have

* Event image upload
* Email notifications
* Calendar integration (Google Calendar, **maybe** Outlook)
* Security Testing