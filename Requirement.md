# Budget Management System

The Wealth Management System provides users with tools to manage their wealth, debts, and transactions while offering insights into their financial health. This document outlines the features for **User Account Management** and **Wealth Data Onboarding and Management**.

---

## **Features**

### 1. User Account Management

The system allows users to register, log in, and manage their account securely.

#### **Key Functionalities**
- **User Registration**
  - Users can create an account using:
    - **Email Address**
    - **Phone Number**
    - **Password**
  - Optional: Verify email or phone using OTP.
- **Login and Authentication**
  - Secure login with email/phone and password.
  - Password reset functionality with email or OTP verification.
- **Profile Management**
  - Update user profile details (e.g., name, contact information).

---

### 2. Wealth Data Onboarding and Management

Users can onboard and manage their wealth data, including financial accounts, debts, and cash in hand.

#### **Key Functionalities**

##### a. Wealth Account Management
- Add and manage multiple wealth accounts:
  - **Default Account Types**:
    - Savings Account
    - Salary Account
    - Investment Account
    - Cash in Hand (default entry always available).
  - **Custom Account Types**: Users can define their own account categories.
- For each account, users can input:
  - Account Name
  - Account Type
  - Current Balance
  - Description (optional).

##### b. Debt Management
- Add and manage debt entries:
  - Specify:
    - **Debtor/Creditor Name**: Person/entity involved.
    - **Debt Type**: Predefined (e.g., Credit Card Debt) or custom.
    - **Amount Owed**
    - **Due Date** (optional).
- Update or close debt entries when repayments are made or money is received.

##### c. Transaction Management
- Record transactions to update balances in accounts or debts:
  - **Transaction Types**:
    - Incoming (e.g., salary credited or loan repayment received).
    - Outgoing (e.g., expenses or debt payments).
    - Account Transfers (e.g., transfer from salary to daily expense account).
  - Specify for each transaction:
    - Amount
    - Source Account (if applicable)
    - Destination Account (if applicable)
    - Transaction Date (default: current date)
    - Notes (optional).
- Transactions:
  - Automatically adjust account balances.
  - Reflect in debt balances (mark debt as "closed" if fully repaid). 
