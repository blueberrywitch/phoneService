package dika;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Slf4j
@Component
public class IndexMigration {

    private final DataSource ds;

    public IndexMigration(DataSource ds) {
        this.ds = ds;
    }

    @PostConstruct
    @SneakyThrows
    public void migrate() {
        try (Connection c = ds.getConnection();
             Statement st = c.createStatement()) {

            // Явно указываем схему public (если используется):
            st.execute("DROP INDEX IF EXISTS public.idx_phones_model");
            st.execute("DROP INDEX IF EXISTS public.idx_phones_brand");

            // Создаём только если не существует:
            st.execute("CREATE INDEX IF NOT EXISTS idx_phones_model " +
                    "ON phones USING HASH (model)");
            log.info("Hash-индекс создан (если не был): phones(model)");

            st.execute("CREATE INDEX IF NOT EXISTS idx_phones_brand " +
                    "ON phones USING HASH (brand)");
            log.info("Hash-индекс создан (если не был): phones(brand)");
        }
    }
}
