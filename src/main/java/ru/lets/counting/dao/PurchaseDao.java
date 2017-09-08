package ru.lets.counting.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import ru.lets.counting.domain.Category;
import ru.lets.counting.domain.Purchase;
import ru.lets.counting.domain.PurchaseFilter;

import static ru.lets.counting.domain.Tables.PURCHASES;
import static ru.lets.counting.domain.Tables.CATEGORIES;

/**
 *
 * @author Евгений
 */
public class PurchaseDao {

    //TODO change and move to base dao
    private final String username = "add_user";
    private final String password = "add_pass";
    private final String databaseUrl = "jdbc:postgresql://localhost:5432/counting";

    private final DSLContext context;

    public PurchaseDao() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, username, password);
        context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    private static class PurchaseMapper implements RecordMapper<Record, Purchase> {

        static final PurchaseMapper INSTANCE = new PurchaseMapper();

        static final TableField[] FIELDS = new TableField[]{
            PURCHASES.ID,
            PURCHASES.AMOUNT,
            PURCHASES.DESCRIPTION,
            PURCHASES.PURCHASE_DATE,
            PURCHASES.CATEGORY_ID,            
            CATEGORIES.TITLE
        };

        @Override
        public Purchase map(Record record) {
            Purchase purchase = new Purchase();
            purchase.setId(record.get(PURCHASES.ID));
            purchase.setAmount(record.get(PURCHASES.AMOUNT));
            purchase.setDescriprtion(record.get(PURCHASES.DESCRIPTION));
            Category category = new Category();
            category.setId(record.get(PURCHASES.CATEGORY_ID));
            category.setTitle(record.get(CATEGORIES.TITLE));
            purchase.setCategory(category);
            return purchase;
        }

    }

    public Purchase getPurchaseById(Integer id) throws SQLException {
        return context
                .select(PurchaseMapper.FIELDS)
                .from(PURCHASES)
                .join(CATEGORIES).on(PURCHASES.CATEGORY_ID.eq(CATEGORIES.ID))
                .where(PURCHASES.ID.eq(id))
                .fetchOne(PurchaseMapper.INSTANCE);
    }

    public List<Purchase> getPurchasesByFilter(PurchaseFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (filter != null) {
            if (filter.getCategoryId() != null) {
                conditions.add(PURCHASES.CATEGORY_ID.eq(filter.getCategoryId()));
            }
            if (filter.getMinAmount() != null) {
                conditions.add(PURCHASES.AMOUNT.ge(filter.getMinAmount()));
            }
            if (filter.getMaxAmount() != null) {
                conditions.add(PURCHASES.AMOUNT.le(filter.getMaxAmount()));
            }
            if (filter.getMinBuyDate() != null) {
                conditions.add(PURCHASES.PURCHASE_DATE.ge(filter.getMinBuyDate()));
            }
            if (filter.getMaxBuyDate() != null) {
                conditions.add(PURCHASES.PURCHASE_DATE.le(filter.getMaxBuyDate()));
            }
        }
        return context
                .select(PurchaseMapper.FIELDS)
                .from(PURCHASES)
                .join(CATEGORIES).on(PURCHASES.CATEGORY_ID.eq(CATEGORIES.ID))
                .where(conditions.stream().toArray(Condition[]::new))
                .fetch(PurchaseMapper.INSTANCE);
    }

    public Purchase save(Integer id, Integer amount, String description, LocalDate buyDate, Category category) {
        if (id == null) {
            return context.insertInto(PURCHASES)
                    .set(PURCHASES.AMOUNT, amount)
                    .set(PURCHASES.DESCRIPTION, description)
                    .set(PURCHASES.CATEGORY_ID, category.getId())
                    .set(PURCHASES.PURCHASE_DATE, buyDate)
                    .returning(PURCHASES.ID)
                    .fetchOne()
                    .map(PurchaseMapper.INSTANCE);
        }
        return context.update(PURCHASES)
                .set(PURCHASES.AMOUNT, amount)
                .set(PURCHASES.DESCRIPTION, description)
                .set(PURCHASES.CATEGORY_ID, category.getId())
                .set(PURCHASES.PURCHASE_DATE, buyDate)
                .where(PURCHASES.ID.eq(id))
                .returning(PURCHASES.ID)
                .fetchOne()
                .map(PurchaseMapper.INSTANCE);
    }
}
