ALTER TABLE transaction_categories ALTER COLUMN user_id drop not NULL;

ALTER TABLE transaction_categories ADD COLUMN is_default boolean NOT NULL DEFAULT false;

INSERT INTO transaction_categories (name, description, is_default) VALUES
    ('comida', 'Comida', true),
    ('transporte', 'Transporte', true),
    ('lazer', 'Lazer', true),
    ('educacao', 'Educação', true),
    ('outros', 'Outros', true),
    ('saude', 'Saúde', true);