
This documentation provides an overview of the Rally RESTful Api V4 endpoint.

## Base URL

The endpoint is prefixed with `https://rally.massgeneralbrigham.org/api/v4.

## Endpoint

### Get Studies

This endpoint returns a list of recruiting studies which match the provided search criteria. At least one search criterion must be provided, e.g. `concepts`, `orgs`, or `search`. Search criteria can be combined to fine tune results. Results are paginated and limited to 100 studies per request. One-based indexing is used for pagination.

Studies are limited by the brand rules assigned to the API authorization.

**Endpoint:**  `/api/v4/studies`

**Method:** `GET`

**Parameters:**

* `locale` (Optional, String): Language code for study content (where available, defaults to English text)
* `concepts` (Optional, String): Comma-separated list of concept IDs*
* `orgs` (Optional, String): Comma-separated list of organization codes*
* `search` (Optional, String): Keyword text search
- `p` (Optional, Integer, Default: 1): The page number.

> [!INFO] Concept and Organization Code Lookup
> You can use the builder tools defined here, https://rallly.massgeneralbrigham.org/developer, to lookup supported concept and organization codes.

**Response:**

* **Status Code:** `200 OK`
* **Content Type:** `application/json`
* **Body:** List of `Study` objects containing study details (see example response below)

**Example Requests:**

```http
GET /api/v4/studies?locale=en&concepts=CON1,CON2&p=1
````
<span style="color: gray; font-size: 0.8em;">Get page 1 of studies with the concepts of "CON1" and "CON2".</span>


```http
GET /api/v4/studies?locale=en&orgs=MGH,BWH&p=1
````
<span style="color: gray; font-size: 0.8em;">Get page 1 of studies with the organizations of "MGH" and "BWH".</span>


```http
GET /api/v4/studies?locale=en&search=diabetes&p=1
````
<span style="color: gray; font-size: 0.8em;">Get page 1 of studies with the organizations of "MGH" and "BWH".</span>


**Example Response:**

```json
[
  {
    "studyUrl": "https://rally.massgeneralbrigham.org/study/end_hiv?utm_campaign=API_H9ArZm",
    "brandShortCode": "MGB",
    "studyKey": "end_hiv",
    "title": "Help us end HIV",
    "summary": "Brigham and Women's Hospital researchers are seeking healthy, HIV-negative adults aged 18-50 to volunteer in HIV Vaccine Research Studies. You could be an everyday hero by participating.",
    "imageUrl": "https://rally.massgeneralbrigham.org/study/image/E48E4820-81AC-471E-B8D5-648112D01333",
    "thumbUrl": "https://rally.massgeneralbrigham.org/thumb/study/image/E48E4820-81AC-471E-B8D5-648112D01333",
    "age": "21-61 years",
    "ageShort": "21-61 ",
    "gender": "Any Gender",
    "compensation": "Payment up to $50",
    "tasks": "Injection or IV, Biopsy, Survey, Blood draw, Office visit, Personal health tracking",
    "timeCommitment": "12 visits over 1 year",
    "timeCommitmentShort": "12 visits",
    "healthyVolunteers": true,
    "therapeuticAreas": [
        {
            "name": "HIV",
            "code": "C0019682",
            "badge": "#0f3704"
        },
        {
            "name": "Sexual Health",
            "code": "C2362326",
            "badge": "#004c80"
        },
        {
            "name": "Vaccines",
            "code": "C0042210",
            "badge": "#002a6d"
        }
    ],
    "investigator": {
        "firstName": "Frances",
        "middleName": "D",
        "lastName": "Bodewell",
        "credentials": "MD",
        "email": "fbodewell@abc.harvard.edu",
        "phone": "6171234567",
        "organization": {
            "code": "BWH",
            "name": "Brigham and Women's Hospital",
        },
    }
    "recruitingStartDate": "2016-10-11T04:00:00.000+00:00",
    "recruitingEndDate": "2026-01-06T05:00:00.000+00:00",
  },
  // ... more study objects
]
```
