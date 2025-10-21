CREATE INDEX "idx_user_email" on app_user(email);
CREATE INDEX "idx_user_username" on app_user(username);
CREATE INDEX idx_user_department ON app_user(department_id);
CREATE INDEX "idx_dept_name" on department(name);
CREATE INDEX "idx_category_name" on category(name);
CREATE INDEX "idx_dept_head" on department(head_user_id);
CREATE INDEX idx_expenditure_requested_by ON expenditure(requested_by);
CREATE INDEX idx_expenditure_status ON expenditure(status);
CREATE INDEX idx_expenditure_date ON expenditure(date);
CREATE INDEX idx_vendor_name ON vendor(name);



