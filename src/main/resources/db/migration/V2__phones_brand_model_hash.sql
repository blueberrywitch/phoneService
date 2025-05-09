-- flyway:transactional=false

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_phones_brand_hash
    ON public.phones USING hash (lower (brand));

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_phones_model_hash
    ON public.phones USING hash (lower (model));

DROP INDEX CONCURRENTLY IF EXISTS idx_phones_brand_btree;
DROP INDEX CONCURRENTLY IF EXISTS idx_phones_model_btree;
