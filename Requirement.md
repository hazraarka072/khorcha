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
 
### 3. Daily Budgeting

The Daily Budgeting feature allows users to manage and track their daily expenses, allocate budgets, and attach additional details such as labels and bills to each entry.

#### **Key Functionalities**

##### a. Adding Budget Entries
- Users can create new budget entries specifying:
  - **Amount Spent**: The amount for the entry.
  - **Category**: Select or define a spending category (e.g., groceries, utilities).
  - **Account**: Specify the account from which the expense is deducted.  
    - Default account will be pre-selected if no account is specified.
  - **Labels**: Add one or more descriptive labels for the entry.
  - **Bill Upload** (optional): Attach a receipt or bill for the expense.

##### b. Labels Management
To avoid duplication and improve usability:
- **Auto-Suggestion for Labels**:
  - As users type in the label field, the system suggests matching existing labels.
  - Suggestions are case-insensitive and based on string similarity.
- **Frequently Used Labels**:
  - A dropdown of the most frequently used labels appears for quick selection.
- **Label Management Dashboard**:
  - Users can:
    - View all labels.
    - Edit existing labels.
    - Merge similar labels into one. All linked budget entries will be updated.
    - Delete unused labels.

##### c. Bill Uploads
- Users can upload bills or receipts for each budget entry:
  - Supported formats: PDF, JPG, PNG.
  - Maximum file size: 5 MB.
- Files are stored securely and accessible via a download link.

##### d. Viewing and Filtering Budget Data
- **Filters**:
  - By date range.
  - By spending account.
  - By labels (e.g., "Groceries").
- **Reports**:
  - Summarize total spending by categories and labels.
  - Include linked bills for reference.
