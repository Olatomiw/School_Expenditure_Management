ALTER TABLE expenditure
    ADD COLUMN dept_head_approver_id UUID,
    ADD COLUMN finance_approver_id UUID,
    ADD COLUMN dept_approval_date TIMESTAMP,
    ADD COLUMN finance_approval_date TIMESTAMP,
    ADD COLUMN rejection_reason VARCHAR(500);

ALTER TABLE expenditure
    ADD CONSTRAINT fk_expenditure_dept_head
        FOREIGN KEY (dept_head_approver_id) REFERENCES app_user(id);

ALTER TABLE expenditure
    ADD CONSTRAINT fk_expenditure_finance
        FOREIGN KEY (finance_approver_id) REFERENCES app_user(id);