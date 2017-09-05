package ru.lets.counting.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
import ru.lets.counting.domain.CategoryFilter;
import static ru.lets.counting.domain.tables.Categories.*;

/**
 *
 * @author Евгений
 */
public class CategoryDao {

    //TODO change and move to base dao
    private final String username = "add_user";
    private final String password = "add_pass";
    private final String databaseUrl = "jdbc:postgresql://localhost:5432/counting";

    private final DSLContext context;

    public CategoryDao() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, username, password);
        context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    private static class CategoryMapper implements RecordMapper<Record, Category> {

        static final CategoryMapper INSTANCE = new CategoryMapper();

        static final TableField[] FIELDS = new TableField[]{
            CATEGORIES.ID,
            CATEGORIES.TITLE,
            CATEGORIES.DESCRIPTION,
            CATEGORIES.PARENT_CATEGORY_ID
        };

        @Override
        public Category map(Record r) {
            Category category = new Category();
            category.setId(r.get(CATEGORIES.ID));
            category.setTitle(r.get(CATEGORIES.TITLE));
            category.setDescriprtion(r.get(CATEGORIES.DESCRIPTION));
            Category parent = new Category();
            parent.setId(r.get(CATEGORIES.PARENT_CATEGORY_ID));
            category.setParentCategory(parent);
            return category;
        }
    }

    public Category getCategoryById(Integer id) throws SQLException {
        return context
                .select(CategoryMapper.FIELDS)
                .from(CATEGORIES)
                .where(CATEGORIES.ID.eq(id))
                .fetchOne(CategoryMapper.INSTANCE);
    }
    
    public List<Category> getCategoriesByFilter(CategoryFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (filter != null) {
            if (filter.getEntrance() != null) {
                conditions.add(CATEGORIES.TITLE.lower().contains(filter.getEntrance().toLowerCase()));
            }
            if (filter.getParentCategoryId() != null) {
                conditions.add(CATEGORIES.PARENT_CATEGORY_ID.eq(filter.getParentCategoryId()));
            }
        }
        return context.select(CategoryMapper.FIELDS)
                .from(CATEGORIES)
                .where(conditions.stream().toArray(Condition[]::new))
                .orderBy(CATEGORIES.TITLE)
                .fetch(CategoryMapper.INSTANCE);
    }

    public Category save(Integer id, String title, String description, Category parent) {
        if (id == null) {
            return context
                    .insertInto(CATEGORIES)
                    .set(CATEGORIES.TITLE, title)
                    .set(CATEGORIES.DESCRIPTION, description)
                    .set(CATEGORIES.PARENT_CATEGORY_ID, parent.getId())
                    .returning(CATEGORIES.ID)
                    .fetchOne()
                    .map(CategoryMapper.INSTANCE);
        }
        return context.update(CATEGORIES)
                .set(CATEGORIES.TITLE, title)
                .set(CATEGORIES.DESCRIPTION, description)
                .set(CATEGORIES.PARENT_CATEGORY_ID, parent.getId())
                .where(CATEGORIES.ID.eq(id))
                .returning(CATEGORIES.ID)
                .fetchOne()
                .map(CategoryMapper.INSTANCE);
    }
}
