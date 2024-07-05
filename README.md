# Survey API
## Start 
Start the application using `mvn spring-boot:run`  
**Requires Java 21**

## Base URL
All API endpoints are prefixed with `/api`.

## Endpoints

### 1. Get Respondents for a Survey
- **Endpoint:** `/api/respondents`
- **Method:** `GET`
- **Description:** Retrieves a list of respondents for a specific survey.
- **Parameters:**
    - `surveyId` (Integer, required): The ID of the survey.
- **Response:** A list of `Member` objects representing the respondents of the specified survey.

**Example Request:**
```http
GET /api/respondents?surveyId=1
```

### 2. Get All Completed Surveys by Member
- **Endpoint:** `/api/completions`
- **Method:** `GET`
- **Description:** Retrieves all surveys completed by a specific member.
- **Parameters:**
    - `memberId` (Integer, required): The ID of the member.
- **Response:** A list of `Survey` objects representing the surveys completed by the specified member.

**Example Request:**
```http
GET /api/completions?memberId=1
```

### 3. Get Points Collected by Member
- **Endpoint:** `/api/points`
- **Method:** `GET`
- **Description:** Retrieves the total points collected by a specific member.
- **Parameters:**
    - `memberId` (Integer, required): The ID of the member.
- **Response:** A `MemberPoints` object containing the points collected by the specified member.

**Example Request:**
```http
GET /api/points?memberId=1
```

### 4. Get Available Members for a Survey
- **Endpoint:** `/api/available-members`
- **Method:** `GET`
- **Description:** Retrieves a list of members who are available to participate in a specific survey.
- **Parameters:**
    - `surveyId` (Integer, required): The ID of the survey.
- **Response:** A list of `Member` objects representing the available members for the specified survey.

**Example Request:**
```http
GET /api/available-members?surveyId=1
```

### 5. Get Statistics for All Surveys
- **Endpoint:** `/api/survey-statistics`
- **Method:** `GET`
- **Description:** Retrieves statistics for all surveys.
- **Parameters:** None
- **Response:** A list of `SurveyStatistics` objects containing statistics for all surveys.

**Example Request:**
```http
GET /api/survey-statistics
```