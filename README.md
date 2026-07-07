# FileUploader

A simple Spring Boot file upload example demonstrating a controller-backed upload flow with a filesystem storage service. This repository contains multiple variants (Java, Kotlin, initial/complete samples) showing the same core design.

## Contents
- `complete/` — full Java sample (buildable/runable with Gradle or Maven)
- `complete-kotlin/` — full Kotlin sample
- `initial/`, `initial-kotlin/` — minimal starter projects

## Features
- Upload files via a web form
- Store files on the server filesystem via a `StorageService` abstraction
- Serve uploaded files back to clients
- Tests for upload functionality included in `complete/` module

## System Design (High-level)
![Architecture diagram]
<img width="900" height="420" alt="image" src="https://github.com/user-attachments/assets/be222b35-438d-4e0e-b2be-287aa42af996" />


 
**Goal**: Provide a small, maintainable service that accepts multipart file uploads from browsers, persists them reliably to local storage, and serves them back on request.

**Key Components**
- `FileUploadController` — HTTP layer that handles upload form, file POSTs and file retrieval.
- `StorageService` (interface) — abstraction for storage operations: save, load, delete, list.
- `FileSystemStorageService` — concrete implementation that persists files under a configurable directory.
- `StorageProperties` — configuration holder for storage location and limits.
- `StorageException` / `StorageFileNotFoundException` — domain exceptions used to return appropriate HTTP responses.

Component mapping (files)
- Controller: `complete/src/main/java/com/example/uploadingfiles/FileUploadController.java`
- Storage service: `complete/src/main/java/com/example/uploadingfiles/storage/FileSystemStorageService.java`
- Config: `complete/src/main/resources/application.properties`

Data flow / Sequence (upload)
1. Client GETs the upload form (controller returns `uploadForm.html`).
2. Client submits multipart form to the upload endpoint (POST).
3. `FileUploadController` receives `MultipartFile`, performs basic validation (size/type/name).
4. Controller calls `StorageService.store(file)` to persist file.
5. `FileSystemStorageService` writes file to the configured storage directory and returns a stored filename/metadata.
6. Controller returns success (redirect or JSON) and exposes the file URL for retrieval.

Failure handling
- Upload validation failures: controller returns 4xx with user-friendly messages.
- Storage write failures: wrapped in `StorageException` and translated to 5xx with safe messages.

Security and robustness notes
- Validate and sanitize filenames to avoid path traversal.
- Enforce limits on upload size (Spring Boot properties) and validate content types if required.
- Store files outside the application binary location; make the storage directory configurable.
- Use appropriate permissions on the storage folder so the application user can write/read but cannot execute uploaded content.

Extensibility
- Swap `FileSystemStorageService` with a cloud-backed implementation (S3, GCS, Azure Blob) by providing another `StorageService` implementation and wiring it via Spring configuration.
- Add virus-scanning or content validation as a pre-store pipeline step.

## Running the application

The `complete/` module is the recommended starting point. From the repository root:

Build and run with Gradle (recommended):

```bash
cd complete
./gradlew bootRun
```

Or build and run the produced jar:

```bash
cd complete
./gradlew build
java -jar build/libs/*-SNAPSHOT.jar
```

Run with Maven:

```bash
cd complete
./mvnw spring-boot:run
```

Open `http://localhost:8080` (or configured port) to see the upload form.



