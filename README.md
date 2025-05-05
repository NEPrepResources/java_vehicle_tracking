Here's a comprehensive `README.md` file for your Vehicle Tracking System API, including all working endpoints and testing data:

```markdown
# RRA Vehicle Tracking System API

![RRA Logo](https://www.rra.gov.rw/fileadmin/templates/images/logo.png)

## Table of Contents
- [API Documentation](#api-documentation)
- [Authentication](#authentication)
- [Endpoints](#endpoints)
  - [User Management](#user-management)
  - [Vehicle Owners](#vehicle-owners)
  - [Plate Numbers](#plate-numbers)
  - [Vehicle Registration](#vehicle-registration)
  - [Vehicle Transfer](#vehicle-transfer)
  - [Ownership History](#ownership-history)
- [Testing Data](#testing-data)
- [Setup Instructions](#setup-instructions)
- [Swagger UI](#swagger-ui)

## API Documentation

Base URL: `http://localhost:9000/api`

## Authentication

All endpoints (except auth) require JWT authentication.

1. Register a user first
2. Login to get JWT token
3. Include token in headers:  
   `Authorization: Bearer <your_token>`

## Endpoints

### User Management

#### Register User
```
POST /auth/signup
```
Request:
```json
{
  "names": "Admin User",
  "email": "admin@rra.gov.rw",
  "phone": "+250788123456",
  "nationalId": "1234567890123456",
  "password": "admin123",
  "address": "Kigali, Rwanda",
  "role": "admin"
}
```

#### Login
```
POST /auth/signin
```
Request:
```json
{
  "email": "admin@rra.gov.rw",
  "password": "admin123"
}
```
Response:
```json
{
  "token": "eyJhbGci...",
  "type": "Bearer",
  "id": 1,
  "username": "Admin User",
  "email": "admin@rra.gov.rw",
  "roles": ["ROLE_ADMIN"]
}
```

### Vehicle Owners

#### Create Owner
```
POST /owners
```
Request:
```json
{
  "ownerNames": "John Doe",
  "nationalId": "1199887766554433",
  "phoneNumber": "+250788112233",
  "address": "Kigali, Rwanda"
}
```

#### Search Owners
```
GET /owners/search?nationalId=1199887766554433
GET /owners/search?email=admin@rra.gov.rw
GET /owners/search?phone=+250788112233
```

#### Get Owner by ID
```
GET /owners/{id}
```

### Plate Numbers

#### Register Plate
```
POST /plate-numbers
```
Request:
```json
{
  "plateNumber": "RAA 123A",
  "ownerId": 1
}
```

#### Get Plates by Owner
```
GET /owners/{ownerId}/plates
```

### Vehicle Registration

#### Register Vehicle
```
POST /vehicles
```
Request:
```json
{
  "chassisNumber": "ABC123456789",
  "manufactureCompany": "Toyota",
  "manufactureYear": 2020,
  "price": 25000,
  "modelName": "RAV4",
  "ownerId": 1,
  "plateNumber": "RAA 123A"
}
```

#### Search Vehicles
```
GET /vehicles/plate/{plateNumber}
GET /vehicles/chassis/{chassisNumber}
GET /vehicles/search?nationalId=1199887766554433
```

### Vehicle Transfer

#### Transfer Vehicle
```
POST /vehicles/transfer
```
Request:
```json
{
  "vehicleIdentifier": "RAA 123A",
  "newOwnerId": 2,
  "newPlateNumber": "RAA 456B",
  "purchasePrice": 30000,
  "comments": "Sold to new owner"
}
```

### Ownership History

#### Get Ownership History
```
GET /vehicles/{vehicleId}/history
```
Response:
```json
[
  {
    "ownerName": "John Doe",
    "startDate": "2025-05-05",
    "endDate": "2025-05-10",
    "purchasePrice": 25000,
    "plateNumber": "RAA 123A"
  },
  {
    "ownerName": "New Owner",
    "startDate": "2025-05-10",
    "endDate": null,
    "purchasePrice": 30000,
    "plateNumber": "RAA 456B"
  }
]
```

## Testing Data

### Users
| Field        | Value                   |
|--------------|-------------------------|
| Names        | Admin User              |
| Email        | admin@rra.gov.rw        |
| Phone        | +250788123456           |
| National ID  | 1234567890123456        |
| Password     | admin123                |
| Role         | ROLE_ADMIN              |

### Vehicle Owners
| Field        | Value                   |
|--------------|-------------------------|
| Owner Names  | John Doe                |
| National ID  | 1199887766554433        |
| Phone        | +250788112233           |
| Address      | Kigali, Rwanda          |

### Plate Numbers
| Plate Number | Owner ID | Status    |
|--------------|----------|-----------|
| RAA 123A     | 1        | AVAILABLE |
| RAA 456B     | 2        | AVAILABLE |

### Vehicles
| Chassis      | Model | Year | Price | Plate    | Owner |
|--------------|-------|------|-------|----------|-------|
| ABC123456789 | RAV4  | 2020 | 25000 | RAA 123A | 1     |

## Setup Instructions

1. Clone the repository
2. Configure database in `application.properties`
3. Run the application:
```bash
mvn spring-boot:run
```
4. Access API at `http://localhost:9000/api`

## Swagger UI

Access API documentation at:  
`http://localhost:9000/swagger-ui.html`

![Swagger UI](https://miro.medium.com/v2/resize:fit:1400/1*J9X5JgX1Q0yALbSWz3Vp4w.png)

To authenticate in Swagger:
1. Click "Authorize" button
2. Enter: `Bearer <your_jwt_token>`
3. Click "Authorize"
