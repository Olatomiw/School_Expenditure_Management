-- =============================================
-- School Expenditure Management System
-- Complete Database Schema (Flyway Migration)
-- =============================================

-- 1. Create department table (standalone first)
CREATE TABLE department (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    head_user_id UUID, -- Will be referenced after user table exists
    total_budget DECIMAL(12, 2) NOT NULL,
    budget_start_date DATE NOT NULL,
    budget_end_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Create category table (standalone)
CREATE TABLE category (
  id UUID PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Create vendor table (standalone)
CREATE TABLE vendor (
id UUID PRIMARY KEY,
name VARCHAR(100) NOT NULL,
contact_email VARCHAR(100),
phone_number VARCHAR(20),
tax_id VARCHAR(50),
address TEXT,
bank_account_details TEXT, -- Encrypt in application
payment_terms VARCHAR(100),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Create user table (references department)
CREATE TABLE app_user (
  id UUID PRIMARY KEY,
  firstname VARCHAR(50) NOT NULL ,
  lastname VARCHAR(50) NOT NULL ,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL, -- BCrypt hash
  email VARCHAR(100) UNIQUE NOT NULL,
  role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'FINANCE_OFFICER', 'DEPARTMENT_HEAD', 'TEACHER')),
  department_id UUID REFERENCES department(id),
  active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Add department head reference (now that user exists)
ALTER TABLE department
ADD CONSTRAINT fk_department_head
FOREIGN KEY (head_user_id) REFERENCES app_user(id);

-- 6. Create expenditure table (references department, category, vendor, user)
CREATE TABLE expenditure (
     id UUID PRIMARY KEY,
     description TEXT NOT NULL,
     amount DECIMAL(12, 2) NOT NULL,
     date DATE NOT NULL,
     department_id UUID NOT NULL REFERENCES department(id),
     category_id UUID NOT NULL REFERENCES category(id),
     vendor_id UUID REFERENCES vendor(id),
     requested_by UUID NOT NULL REFERENCES app_user(id),
     status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'PAID')),
     receipt_reference VARCHAR(100),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. Create transaction table (references expenditure, vendor, user)
CREATE TABLE transaction (
     id UUID PRIMARY KEY,
     expenditure_id UUID UNIQUE NOT NULL REFERENCES expenditure(id),
     vendor_id UUID NOT NULL REFERENCES vendor(id),
     amount DECIMAL(12, 2) NOT NULL,
     payment_date DATE NOT NULL,
     payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('BANK_TRANSFER', 'CASH', 'CHEQUE', 'CARD')),
     reference_number VARCHAR(100) NOT NULL,
     processed_by UUID NOT NULL REFERENCES app_user(id),
     notes TEXT,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. Create indexes for performance
CREATE INDEX idx_expenditure_department ON expenditure(department_id);
CREATE INDEX idx_expenditure_status ON expenditure(status);
CREATE INDEX idx_transaction_vendor ON transaction(vendor_id);
CREATE INDEX idx_transaction_date ON transaction(payment_date);