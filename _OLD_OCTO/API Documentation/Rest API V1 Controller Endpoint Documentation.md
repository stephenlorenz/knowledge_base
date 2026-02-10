
This document outlines the endpoints provided by the Rest Api V1 endpoints.

## Base URL

All endpoints are prefixed with `/api/v1`.

## Endpoints

### 1. Get Study by Study Key

**Endpoint:** `/api/v1/study`

**Method:** `GET`

**Parameters:**

* `studyKey` (Required, String): The unique identifier for the study.

**Response:**

* **Status Code:** `200 OK`
* **Content Type:** `application/json`
* **Body:** `StudyESRO` object containing study details.

**Example Request:**

```http
GET /api/v1/study?studyKey=STUDY123
````

**Example Response:**

```json
{
  "id": "123",
  "studyKey": "STUDY123",
  // ... other fields
}
````

### 2. Get Studies by Department ID

**Endpoint:** `/api/v1/department`

**Method:** `GET`

**Parameters:**

- `type` (Required, String): The department type.
- `dId` (Required, Integer): The department ID.
- `p` (Optional, Integer, Default: 1): The page number.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.

**Example Request:**

```http
GET /api/v1/department?type=research&dId=456&p=2
```

**Example Response:**

```json
[
  {
    "id": "456",
    "studyKey": "STUDY456",
    // ... other fields
  },
  // ... more objects
]
```

### 3. Get Studies by Institution Code

**Endpoint:** `/api/v1/institution`

**Method:** `GET`

**Parameters:**

- `instCode` (Required, String): The institution code.
- `p` (Optional, Integer, Default: 1): The page number.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.

**Example Request:**

```http
GET /api/v1/institution?instCode=INST789&p=1
```

**Example Response:**

```json
[
  {
    "id": "789",
    "studyKey": "STUDY789",
    // ... other StudyESRO fields
  },
  // ... more StudyESRO objects
]
```

### 4. Get Studies by Concept IDs

**Endpoint:** `/api/v1/concept`

**Method:** `GET`

**Parameters:**

- `conceptIds` (Required, String): Comma-separated list of concept IDs.
- `p` (Optional, Integer, Default: 1): The page number.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.
- **Status Code:** `null` if no study is found.

**Example Request:**

```http
GET /api/v1/concept?conceptIds=CON1,CON2&p=1
```

**Example Response:**

```json
[
  {
    "id": "101",
    "studyKey": "STUDY101",
    // ... other fields
  },
  // ... more objects
]
```

### 5. Get Study Search Results by Concept IDs

**Endpoint:** `/api/v1/concepts`

**Method:** `GET`

**Parameters:**

- `conceptIds` (Required, String): Comma-separated list of concept IDs.
- `p` (Optional, Integer, Default: 1): The page number.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.

**Example Request:**

```http
GET /api/v1/concepts?conceptIds=CON3,CON4&p=1
```

**Example Response:**

```json
[
  {
    "id": "202",
    "studyKey": "STUDY202",
    "title": "Study Title",
    "summary": "Study Summary",
    // ... other fields
  },
  // ... more objects
]
```

### 6. Get Studies by Healthy Volunteers

**Endpoint:** `/api/v1/healthyvolunteers`

**Method:** `GET`

**Parameters:**

- `p` (Optional, Integer, Default: 1): The page number.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.

**Example Request:**

```http
GET /api/v1/healthyvolunteers?p=1
```

**Example Response:**

```json
[
  {
    "id": "303",
    "studyKey": "STUDY303",
    // ... other fields
  },
  // ... more objects
]
```

### 7. Get Studies by Category

**Endpoint:** `/api/v1/category`

**Method:** `GET`

**Parameters:**

- `categories` (Required, String): Comma-separated list of category codes.
- `p` (Optional, Integer, Default: 1): The page number.
- `i` (Optional, Array of Strings): Array of institution codes.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.

**Example Request:**

```http
GET /api/v1/category?categories=CAT1,CAT2&p=1&i=INST1,INST2
```

**Example Response:**

```json
[
  {
    "id": "404",
    "studyKey": "STUDY404",
    // ... other fields
  },
  // ... more objects
]
```

### 8. Get Studies by Topic

**Endpoint:** `/api/v1/topic`

**Method:** `GET`

**Parameters:**

- `topics` (Required, String): Comma-separated list of topic codes.
- `p` (Optional, Integer, Default: 1): The page number.
- `i` (Optional, Array of Strings): Array of institution codes.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.

**Example Request:**

```http
GET /api/v1/topic?topics=TOP1,TOP2&p=1&i=INST3,INST4
```

**Example Response:**

```json
[
  {
    "id": "505",
    "studyKey": "STUDY505",
    // ... other fields
  },
  // ... more objects
]
```

### 9. Get Studies by MeSH Codes

**Endpoint:** `/api/v1/meshcode`

**Method:** `GET`

**Parameters:**

- `meshCodes` (Required, String): Comma-separated list of MeSH codes.
- `p` (Optional, Integer, Default: 1): The page number.

**Response:**

- **Status Code:** `200 OK`
- **Content Type:** `application/json`
- **Body:** List of `Study` objects.
- **Status Code:** `null` if no study is found.

**Example Request:**

```http
GET /api/v1/meshcode?meshCodes=MESH1,MESH2&p=1
```

**Example Response:**

```json
[
  {
    "id": "606",
    "studyKey": "STUDY606",
    // ... other fields
  },
  // ... more objects
]
```