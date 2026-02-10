
This documentation outlines the Rally RESTful Api V4 endpoint.

## Terms of Use

All Rally with Mass General Brigham developer tools may only be used on web pages that present information about research for informational purposes in a manner consistent with the policies described in detail in the Mass General Brigham Terms of Use, https://www.massgeneralbrigham.org/notices. Rally with Mass General Brigham may experience interruptions in service. The developer tools should only be used on websites in ways that enable those sites to continue to function during such service interruptions. The project contacts who hold API authorization keys should watch for email communications about updates to the developer tools and the documentation on these pages. API authorization keys are used by Mass General Brigham to track and control usage. Mass General Brigham reserves the unqualified right to revoke API authorization keys.

## Registration

You must register with Rally at Mass General Brigham and receive an authorization key in order to use the API. You can register here, https://rally.massgeneralbrigham.org/developer/register.

## Authentication

API requests must include an HTTP **Authorization header**. This header utilizes the **Bearer token** scheme, as defined in [RFC 6750](https://datatracker.ietf.org/doc/html/rfc6750).

The format of the Authorization header is as follows:

```
Authorization: Bearer <your_authorization_key>
```

Where:

- **`Authorization`**: This is the standard HTTP header used to convey authentication information.      
- **`Bearer`**: This is the authentication scheme being used. It indicates that the credentials following it are an OAuth 2.0 bearer token.
- **`<your_authorization_key>`**: This is the actual security token that you have obtained through the registration process, described above. This is a sensitive piece of information and should be kept confidential.

## Base URL

The endpoint is prefixed with `https://rally.massgeneralbrigham.org/api/v4.

## Endpoint

### Get Studies

This endpoint returns a list of recruiting studies which match the provided search criteria. At least one search criterion must be provided, e.g. `concepts`, `orgs`, or `search`. Search criteria can be combined to fine tune results. Results are paginated and limited to 50 studies per request. One-based indexing is used for pagination.

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
    "studyUrl": "https://rally.massgeneralbrigham.org/study/end_hiv",
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
