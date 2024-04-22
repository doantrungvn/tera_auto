package org.terasoluna.qp.app.wraper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.codelist.AbstractReloadableCodeList;

/**
 * Initializes codelist information from a database using JDBC.
 * <p>
 * The results of {@link #querySql} are stored in the codelist. The column name of 'value' of codelist is set by
 * {@link #valueColumn} and 'label' by {@link #labelColumn}.<br>
 * Each row is put to the codelist unless value or label of it is <code>null</code>.
 * </p>
 */
public class JdbcCodeList extends AbstractReloadableCodeList {

    /**
     * Database access information
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * SQL Query to access the database
     */
    private String querySql;

    /**
     * property that holds the name of the column of the database holding the value part of the codelist
     */
    private String valueColumn;

    /**
     * property that holds the name of the column of the database holding the label part of the codelist
     */
    private String labelColumn;
    
    private boolean isMultilanguage;

    /**
     * Retrieves the codelist from the database and returns it as a Map<br>
     * Each row is put to the codelist unless value or label of it is <code>null</code>.
     * @return Map latest codelist information
     * @see org.terasoluna.gfw.common.codelist.AbstractReloadableCodeList#retrieveMap()
     */
    @Override
    protected Map<String, String> retrieveMap() {
    	if (isMultilanguage) {
    		querySql += "";
    	}
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql);
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        for (Map<String, Object> row : rows) {
            Object key = row.get(valueColumn);
            Object value = row.get(labelColumn);
            if (key != null && value != null) {
                result.put(key.toString(), value.toString());
            }
        }
        return result;
    }

    /**
     * Sets DataSource. <br>
     * <strong>Note that 'fetch size' is set by default (depends on JDBC implementation). Default 'fetch size' cause slow
     * response possibly when the size of codelist is large. If you want to set fetch size, use
     * {@link #setJdbcTemplate(JdbcTemplate)} instead. </strong>
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Sets JdbcTemplate
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * This method is called after the codelist is initialized Checks whether the values of querySql, valueColumn, labelColumn
     * and jdbcTemplate properties are set
     * @see org.terasoluna.gfw.common.codelist.AbstractReloadableCodeList#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        Assert.hasLength(querySql, "querySql is empty");
        Assert.hasLength(valueColumn, "valueColumn is empty");
        Assert.hasLength(labelColumn, "labelColumn is empty");
        Assert.notNull(jdbcTemplate, "jdbcTemplate (or dataSource) is empty");
        super.afterPropertiesSet();
    }

    /**
     * Setter method for labelColumn
     * @param labelColumn
     */
    public void setLabelColumn(String labelColumn) {
        this.labelColumn = labelColumn;
    }

    /**
     * Setter method for valueColumn
     * @param valueColumn
     */
    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    /**
     * Setter method for querySql
     * @param querySql
     */
    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

	public boolean isMultilanguage() {
		return isMultilanguage;
	}

	public void setMultilanguage(boolean isMultilanguage) {
		this.isMultilanguage = isMultilanguage;
	}
}
