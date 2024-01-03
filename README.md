# Patient Journal System

This project is a web application designed for managing patient and employee information in a healthcare setting. It provides CRUD operations for patients and employees, enabling communication between them through messaging. Employees (doctors and staff) can create notes about patients for better record-keeping.

## Features

- User Authentication:
  - Three different user roles: Patient, Doctor, Staff.
  - Login and registration for users.

- Patient Management:
  - Patients can view and update their own information.
  - CRUD operations for patients.

- Employee Management:
  - Doctors and staff can view and update patient information.
  - CRUD operations for employees.

- Messaging System:
  - Patients can send messages to doctors and staff.
  - Doctors and staff can view and reply to patient messages.

- Notes:
  - Doctors and staff can create notes about patients.

- Technology Stack:
  - Backend: Spring Boot with MySQL database.
  - Frontend: React.js.

- Containerization:
  - Docker is used to containerize the application with at least three containers (frontend, backend, database).

## Backend (Spring Boot)

- Authentication:
  - Spring Security for user authentication and authorization.
  - Basic authentication.

- Entities:
  - User, Patient, Employee, Message, Note...

- Controllers:
  - AuthController: Manages authentication-related operations.
  - PatientController: Handles patient-specific operations.
  - EmployeeController: Manages employee-related operations.
  - MessageController: Handles messaging functionality.
  - NoteController: Manages note-related operations.

- Security:
  - Role-based access control for different endpoints.

- Service Layer:
  - Implements business logic for CRUD operations and messaging.

## Frontend (React.js)

- React Components:
  - User components for registration, login, and profile.
  - Patient components for patient-specific operations.
  - Employee components for employee-specific operations.
  - Messaging components for sending and receiving messages.
  - Note components for managing patient notes.

- Integration with Backend:
  - Fetch API for making requests to backend APIs.

## Screenshots
    
![Skärmbild 2023-11-21 185940](https://github.com/11yaman/PatientJournalSystem/assets/129747692/a845976e-694d-4be1-ae9d-e568fbb6a967)
![Skärmbild 2023-11-21 190412](https://github.com/11yaman/PatientJournalSystem/assets/129747692/a52e1056-83f2-4454-80f7-f89da738b720)

![Skärmbild 2023-11-21 190058](https://github.com/11yaman/PatientJournalSystem/assets/129747692/699b0d3a-4b8a-4f68-8885-236ad0e5cdd4)
![Skärmbild 2023-11-21 190205](https://github.com/11yaman/PatientJournalSystem/assets/129747692/44a281d6-d4d0-474a-9ad1-0e89ab52d469)

![Skärmbild 2023-11-21 191639](https://github.com/11yaman/PatientJournalSystem/assets/129747692/e16b0bec-2b37-4b66-9b2c-efeca26f3a92)
![Skärmbild 2023-11-21 192349](https://github.com/11yaman/PatientJournalSystem/assets/129747692/fa8a6f39-f8f2-4d14-be46-1cc29ffcf925)
![Skärmbild 2023-11-21 192440](https://github.com/11yaman/PatientJournalSystem/assets/129747692/d5f2e46f-3696-493e-a96f-79d7e41e4e2b)
![Skärmbild 2023-11-21 192519](https://github.com/11yaman/PatientJournalSystem/assets/129747692/766055c5-c828-43e6-992c-f7b56ad7fcd7)
![Skärmbild 2023-11-21 192548](https://github.com/11yaman/PatientJournalSystem/assets/129747692/6ddc0c67-18e7-416d-9cd1-97b66a7c6c09)


