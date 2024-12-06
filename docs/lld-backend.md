### Low-Level Design for Wealth Management System using Java Spring Boot (Deployed in AWS Lambda)

This document outlines a low-level design for implementing a **Wealth Management System** backend API in **Java Spring Boot**, deployed on **AWS Lambda**. The system consists of several key modules like **User Account Management**, **Wealth Data Onboarding & Management**, and **Daily Budgeting**.

---

### 1. **User Account Management Module**

#### a. **User Registration**
- **API Endpoint**: `POST /api/v1/register`
- **Input**:
    - Email (Required)
    - Phone Number (Optional)
    - Password (Required)
- **Process**:
    - Check if the email or phone is already registered.
    - Create a new user entry in the database with hashed password.
    - Send OTP to email/phone for verification (if applicable).
    - Store user details in a `users` table.

- **API Endpoint**: `POST /api/v1/verify/otp`
- **Input**:
    - OTP (Received via email/phone)
    - User Identifier (Email/Phone)
- **Process**:
    - Verify OTP, and if correct, activate user account.

#### b. **Login and Authentication**
- **API Endpoint**: `POST /api/v1/login`
- **Input**:
    - Email or Phone Number
    - Password
- **Process**:
    - Authenticate using JWT (JSON Web Token) or OAuth2.
    - Issue a JWT token for authenticated sessions.

- **API Endpoint**: `POST /api/v1/password/reset`
- **Input**:
    - Email/Phone Number
- **Process**:
    - Send password reset link/OTP.
    - Allow user to reset password.

#### c. **Profile Management**
- **API Endpoint**: `PUT /api/v1/profile/update`
- **Input**:
    - Name
    - Email
    - Phone Number
    - Password (Optional)
- **Process**:
    - Validate input.
    - Update the user profile in the database.

---

### 2. **Wealth Data Onboarding and Management**

#### a. **Wealth Account Management**

- **API Endpoint**: `POST /api/v1/accounts`
- **Input**:
    - Account Name
    - Account Type (Savings, Salary, Investment, etc.)
    - Current Balance
    - Description (Optional)
- **Process**:
    - Create a new account entry in the `accounts` table.
    - Link accounts to the user's profile.

- **API Endpoint**: `GET /api/v1/accounts`
- **Output**:
    - List of all accounts associated with the user.
    - Can include filters by account type (e.g., `type=savings`).

- **API Endpoint**: `PUT /api/v1/accounts/{accountId}`
- **Input**:
    - Account Name
    - Account Type
    - Balance
    - Description
- **Process**:
    - Update account details in the `accounts` table.

#### b. **Debt Management**

- **API Endpoint**: `POST /api/v1/debts`
- **Input**:
    - Debtor/Creditor Name
    - Debt Type (Predefined or Custom)
    - Amount Owed
    - Due Date (Optional)
- **Process**:
    - Create a debt entry in the `debts` table.
    - Link to user.

- **API Endpoint**: `GET /api/v1/debts`
- **Output**:
    - List of all debts associated with the user.

- **API Endpoint**: `PUT /api/v1/debts/{debtId}`
- **Input**:
    - Amount Owed
    - Paid Amount
    - Repayment Status (e.g., Closed, Open)
- **Process**:
    - Update the debt record when payments are made.

#### c. **Transaction Management**

- **API Endpoint**: `POST /api/v1/transactions`
- **Input**:
    - Amount
    - Transaction Type (Incoming, Outgoing, Transfer)
    - Source Account (Optional)
    - Destination Account (Optional)
    - Transaction Date
    - Notes (Optional)
- **Process**:
    - Create a transaction entry and update account balances accordingly.
    - Update the debt balances if the transaction is related to debt repayment.

- **API Endpoint**: `GET /api/v1/transactions`
- **Output**:
    - List of all transactions, with filtering capabilities (date range, amount, etc.).

---

### 3. **Daily Budgeting**

#### a. **Adding Budget Entries**

- **API Endpoint**: `POST /api/v1/budget`
- **Input**:
    - Amount Spent
    - Category (e.g., Groceries, Utilities)
    - Account (Required)
    - Labels (Optional)
    - Bill Upload (Optional, PDF/JPG/PNG)
- **Process**:
    - Create a new budget entry.
    - If a bill is uploaded, store the file securely in an S3 bucket.
    - Link the entry to the specified account.

#### b. **Labels Management**

- **API Endpoint**: `POST /api/v1/labels`
- **Input**:
    - Label Name
- **Process**:
    - Add a new label to the system.

- **API Endpoint**: `GET /api/v1/labels`
- **Output**:
    - List of all labels in the system.

- **API Endpoint**: `PUT /api/v1/labels/{labelId}`
- **Input**:
    - New Label Name
- **Process**:
    - Update the label name in the system.

- **API Endpoint**: `DELETE /api/v1/labels/{labelId}`
- **Process**:
    - Delete the label from the system.

#### c. **Bill Uploads**

- **API Endpoint**: `POST /api/v1/bills`
- **Input**:
    - Bill File (PDF/JPG/PNG)
    - Budget Entry ID (Linking to an existing budget entry)
- **Process**:
    - Store the bill file securely in an S3 bucket.
    - Provide a download link.

#### d. **Viewing and Filtering Budget Data**

- **API Endpoint**: `GET /api/v1/budget`
- **Input**:
    - Filters: By date range, by spending account, by labels.
- **Output**:
    - List of budget entries matching the filter criteria.
    - Summary reports (e.g., total spending by category/label).

---

### Database Design

- **Users Table**:
    - `username` (Primary Key)
    - `email`
    - `phone_number`
    - `password_hash`
    - `status` (active/inactive)

- **Accounts Table**:
    - `username` (Primary Key)
    - `user_id` (Foreign Key)
    - `account_name`
    - `account_type`
    - `balance`
    - `description`

- **Debts Table**:
    - `debt_id` (Primary Key)
    - `user_id` (Foreign Key)
    - `debtor_name`
    - `creditor_name`
    - `amount_owed`
    - `due_date`
    - `debt_status`

- **Transactions Table**:
    - `transaction_id` (Primary Key)
    - `user_id` (Foreign Key)
    - `amount`
    - `transaction_type`
    - `source_account_id`
    - `destination_account_id`
    - `transaction_date`
    - `notes`

- **Budget Table**:
    - `budget_id` (Primary Key)
    - `username` (Foreign Key)
    - `amount_spent`
    - `category`
    - `account_id`
    - `labels`
    - `bill_url`

- **Labels Table**:
    - `label_id` (Primary Key)
    - `label_name`
    - `frequency_count`

- **Bills Table**:
    - `bill_id` (Primary Key)
    - `file_url`
    - `budget_id` (Foreign Key)

---

### AWS Lambda Architecture

- **API Gateway**: Exposes REST API endpoints to Lambda functions.
- **Lambda Functions**: Handle logic for user authentication, registration, account management, budgeting, etc.
- **DynamoDB**: Optionally, use DynamoDB for fast, scalable storage instead of RDBMS.
- **S3**: Store bills/receipts and user documents.
- **Cognito**: Use AWS Cognito for user authentication, OTP verification, and password reset.
- **CloudWatch**: For logging and monitoring.

---

### Security Considerations

- **Encryption**: Use **AES-256** encryption for sensitive data such as passwords.
- **JWT Tokens**: Use **JWT** for secure stateless authentication.
- **IAM Roles**: Ensure each Lambda function has the least privilege by assigning appropriate IAM roles.
- **SSL/TLS**: Use HTTPS for secure communication between client and API Gateway.

---

### Conclusion

This low-level design outlines the core features of the Wealth Management System, focusing on user account management, wealth data onboarding, and daily budgeting. Each module is mapped to RESTful APIs in **Spring Boot** and integrates with **AWS Lambda** for a scalable, serverless solution. The system ensures secure access, data storage, and processing using various AWS services like **Lambda**, **DynamoDB**, **S3**, and **Cognito**.