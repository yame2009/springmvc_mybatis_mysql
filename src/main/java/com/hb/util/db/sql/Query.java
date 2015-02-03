package com.hb.util.db.sql;

import java.util.ArrayList;
import java.util.List;
 
 
/**
 * <pre>
 * 
 *  JPA 查询对象    
 * 
 * </pre>
 */
public class Query
{
    private static final String SPACE = " ";
    private static final String SELECT = "SELECT";
    private static final String COUNT = "COUNT";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE";
    private static final String AND = "AND";
    private static final String OR = "OR";
    private static final String ORDER_BY = "ORDER BY";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String JOIN = "JOIN";
    private static final String INNER_JOIN = "INNER JOIN";
    private static final String LEFT_JOIN = "LEFT JOIN";
    private static final String RIGHT_JOIN = "RIGHT JOIN";
    private static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN";
    private static final String RIGHT_OUT_JOIN = "RIGHT OUTER JOIN";
    private static final String LEFT_JOIN_FETCH = "LEFT JOIN FETCH";
    private static final String RIGHT_JOIN_FETCH = "RIGHT JOIN FETCH";
 
    private static final boolean ADD_PARAM = true;
    private static final boolean NOT_ADD_PARAM = false;
 
    private Class<?> entityClass;
    private String alias;
    private Integer pageNum;
    private Integer pageSize;
    private List<Object> paramters = new ArrayList<>();
 
    private StringBuilder selectClause = new StringBuilder();
    private StringBuilder fromClause = new StringBuilder();
    private StringBuilder joinClause = new StringBuilder();
    private StringBuilder whereClause = new StringBuilder();
    private StringBuilder orderByClause = new StringBuilder();
    private boolean fetchAllRecords = true;
 
    public Query( Class<?> entityClass, String alias )
    {
        this.entityClass = entityClass;
        this.alias = alias;
 
        /**
         * FROM abc.Def alais
         */
        fromClause//
        .append( SPACE )//
        .append( FROM )//
        .append( SPACE )//
        .append( entityClass.getName() )//
        .append( SPACE )//
        .append( this.alias );
    }
 
    /**
     * evaluate 生成最后的JPQL语句
     */
    public String evaluate()
    {
        StringBuilder result = new StringBuilder();
 
        /**
         * 以下构造要保持顺序，若重构时请注意
         */
        evlauteAppendSelectClause( result );
        evlauteAppendFromClause( result );
        evlauteAppendJoinClause( result );
        evlauteAppendWhereClause( result );
        evlauteAppendOrderByClause( result );
 
        return result.toString().trim();
    }
 
    /**
     * evaluateCount 生成最后的查询总数的JPQL语句
     */
    public String evaluateCount()
    {
        StringBuilder result = new StringBuilder();
 
        /**
         * 以下构造要保持顺序，若重构时请注意
         */
        evlauteAppendSelectCountClause( result );
        evlauteAppendFromClause( result );
        evlauteAppendWhereClause( result );
 
        return result.toString().trim();
    }
 
    private void evlauteAppendSelectClause( StringBuilder result )
    {
        if ( isNotBlank( selectClause ) )
        {
            result.append( selectClause );
        }
    }
 
    private void evlauteAppendSelectCountClause( StringBuilder result )
    {
        result.append( SELECT )//
        .append( SPACE )//
        .append( COUNT )//
        .append( "(" )//
        .append( alias )//
        .append( ")" );
    }
 
    private void evlauteAppendFromClause( StringBuilder result )
    {
        result.append( fromClause );
    }
 
    private void evlauteAppendJoinClause( StringBuilder result )
    {
        if ( isNotBlank( joinClause ) )
        {
            result.append( joinClause );
        }
    }
 
    private void evlauteAppendWhereClause( StringBuilder result )
    {
        if ( isNotBlank( whereClause ) )
        {
            result.append( whereClause );
        }
    }
 
    private void evlauteAppendOrderByClause( StringBuilder result )
    {
        if ( isNotBlank( orderByClause ) )
        {
            result.append( orderByClause );
        }
    }
 
    /**
     * <pre>
     * 例如返回一个实体
     *  addSelectClause("e") "e" 跟new Query() 第二个别名参数要一至。
     * 
     * 例如返回一个查询的VO对象
     *  addSelectClause("new VO(e.id,e.name)") "e" 跟new Query() 第二个别名参数要一至。
     * 
     * 注意些方法只能调用一次，同一实例调用多次会抛出RepositoryException异常
     * </pre>
     * 
     * @param expression JPQL片段
     * @return Query
     * @throws Exception 
     */
    public Query addSelectClause( String expression ) throws Exception
    {
        checkSelectClauseIsBinded();
        selectClause.append( SELECT ).append( SPACE );
 
        if ( isBlank( expression ) )
        {
            selectClause.append( this.alias );
        }
        else
        {
            selectClause.append( expression );
        }
        return this;
    }
 
    private void checkSelectClauseIsBinded() throws Exception
    {
        if ( isNotBlank( selectClause ) )
        {
            throw new Exception( "selectClause已经绑定过不能再定绑定." );
        }
    }
 
    /**
     * <pre>
     *  结果将添加如  WHERE e.id=? AND e.name=? 的 JPQL片段
     *  例子:
     *  addWhereAndClause( id!=null, "e.id=?", id )
     * 
     * </pre>
     * 
     * @param canAdd 条件，如果成立 ，JPQL片段将被添加
     * @param expression JPQL片段
     * @param value 设置的值
     * @return Query
     */
    public Query addWhereAndClause( boolean canAdd, String expression, Object value )
    {
        if ( canAdd )
        {
            return addWhereClause( AND, expression, value, ADD_PARAM );
        }
        return this;
    }
 
    public Query addWhereAndClause( boolean canAdd, String expression )
    {
        if ( canAdd )
        {
            return addWhereClause( AND, expression, null, NOT_ADD_PARAM );
        }
        return this;
    }
 
    /**
     * 直接添加JPQL片段
     * 
     * @see Query#addWhereOrClause(boolean,
     *      String, Object)
     */
    public Query addWhereAndClause( String expression, Object value )
    {
        return addWhereClause( AND, expression, value, ADD_PARAM );
    }
 
    /**
     * 直接添加JPQL片段不带有占位参数
     * 
     * @see Query#addWhereOrClause(boolean,
     *      String, Object)
     */
    public Query addWhereAndClause( String expression )
    {
        return addWhereClause( AND, expression, null, NOT_ADD_PARAM );
    }
 
    /**
     * <pre>
     *  结果将添加如  WHERE e.id=? OR e.name=? 的 JPQL片段
     *  例子:
     *  addWhereAndClause( id!=null, "e.id=?", id )
     * 
     * </pre>
     * 
     * @param canAdd 条件，如果成立 ，JPQL片段将被添加
     * @param expression JPQL片段
     * @param value 设置的值
     * @return Query
     */
    public Query addWhereOrClause( boolean canAdd, String expression, Object value )
    {
        if ( canAdd )
        {
            return addWhereClause( OR, expression, value, ADD_PARAM );
        }
        return this;
    }
 
    public Query addWhereOrClause( boolean canAdd, String expression )
    {
        if ( canAdd )
        {
            return addWhereClause( OR, expression, null, NOT_ADD_PARAM );
        }
        return this;
    }
 
    /**
     * 直接添加JPQL片段
     * 
     * @see Query#addWhereOrClause(boolean,
     *      String, Object)
     */
    public Query addWhereOrClause( String expression, Object value )
    {
        return addWhereClause( OR, expression, value, ADD_PARAM );
    }
 
    /**
     * 直接添加JPQL片段， 不添加占位符号查询参数
     * 
     * @see Query#addWhereOrClause(boolean,
     *      String, Object)
     */
    public Query addWhereOrClause( String expression )
    {
        return addWhereClause( OR, expression, null, NOT_ADD_PARAM );
    }
 
    /**
     * addWhereClause
     */
 
    private Query addWhereClause( String operation, String expression, Object value, boolean addParam )
    {
        prepareWhereClause( operation );
        whereClause.append( SPACE )//
        .append( expression.trim() );//
 
        if ( addParam )
        {
            paramters.add( value );
        }
        return this;
    }
 
    /**
     * <pre>
     * 
     *  结果将添加如  ORDER BY e.id ASC, e.name ASC 的 JPQL片段
     *  例子:
     *  addOrderByAscClause( true, "e.id" )
     *  addOrderByAscClause( true, "e.name" )
     * </pre>
     * 
     * @param canAdd 条件，如果成立 ，JPQL片段将被添加
     * @param expression JPQL片段
     * @return {@link Query}
     */
    public Query addOrderByAscClause( boolean canAdd, String expression )
    {
        if ( canAdd )
        {
            return addOrderByAscClause( expression );
        }
        return this;
    }
 
    /**
     * 直接添加JPQL语句
     * 
     * @see Query#addOrderByAscClause(boolean,
     *      String)
     */
    public Query addOrderByAscClause( String expression )
    {
        return addOrderByClause( ASC, expression );
    }
 
    /**
     * <pre>
     * 
     *  结果将添加如  ORDER BY e.id DESC, e.name DESC 的 JPQL片段
     *  例子:
     *  addOrderByAscClause( true, "e.id" )
     *  addOrderByAscClause( true, "e.name" )
     * </pre>
     * 
     * @param canAdd 条件，如果成立 ，JPQL片段将被添加
     * @param expression JPQL片段
     * @return {@link Query}
     */
    public Query addOrderByDescClause( boolean canAdd, String expression )
    {
        if ( canAdd )
        {
            return addOrderByClause( DESC, expression );
        }
        return this;
    }
 
    /**
     * 直接添加JPQL语句
     * 
     * @see Query#addOrderByDescClause(boolean,
     *      String)
     */
    public Query addOrderByDescClause( String expression )
    {
        return addOrderByClause( DESC, expression );
    }
 
    private Query addOrderByClause( String opration, String expression )
    {
        prepareOrderByClause();
        orderByClause.append( SPACE ).append( expression )//
        .append( SPACE )//
        .append( opration );
        return this;
    }
 
    /**
     * <pre>
     * 
     * 添加JOIN语句，例如:
     *  .addJoinClause("d.sub s")  s为join的对象别名
     * 
     * </pre>
     */
    public Query addJoinClause( String expression )
    {
        return addCaseedJoinClause( JOIN, expression );
    }
 
    /**
     * <pre>
     * 
     * 添加INNER JOIN语句，例如:
     *  .addInnerJoinClause("d.sub s")  s为join的对象别名
     * 
     * </pre>
     */
    public Query addInnerJoinClause( String expression )
    {
        return addCaseedJoinClause( INNER_JOIN, expression );
    }
 
    /**
     * <pre>
     * 
     * 添加LEFT JOIN语句，例如:
     *  .addLeftJoinClause("d.sub s")  s为join对象后别名
     * 
     * </pre>
     */
    public Query addLeftJoinClause( String expression )
    {
        return addCaseedJoinClause( LEFT_JOIN, expression );
    }
 
    /**
     * <pre>
     * 
     * 添加RIGHT JOIN语句，例如:
     *  .addRightJoinClause("d.sub s")  s为join的对象别名
     * 
     * </pre>
     */
    public Query addRightJoinClause( String expression )
    {
        return addCaseedJoinClause( RIGHT_JOIN, expression );
    }
 
    /**
     * <pre>
     * 
     * 添加LEFT OUTER JOIN语句，例如:
     *  .addLeftOuterJoinClause("d.sub s")  s为join的对象别名
     * 
     * </pre>
     */
    public Query addLeftOuterJoinClause( String expression )
    {
        return addCaseedJoinClause( LEFT_OUTER_JOIN, expression );
    }
 
    /**
     * <pre>
     * 
     * 添加RIGHT OUTER JOIN语句，例如:
     *  .addRightOuterJoinClause("d.sub s")  s为join的对象别名
     * 
     * </pre>
     */
    public Query addRightOuterJoinClause( String expression )
    {
        return addCaseedJoinClause( RIGHT_OUT_JOIN, expression );
    }
 
    /**
     * <pre>
     * 注意fetch也不应该与setMaxResults() 或 setFirstResult()共用，
     * 这是因为这些操作是基于结果集的，而在预先抓取集合类时可能包含重复的数据，也就是说无法预先知道精确的行数。
     * 
     * 添加LEFT JOIN FETCH 语句，例如:
     *  .addLeftJoinFetchClause("d.sub s")  s为join的对象别名
     * 
     * </pre>
     */
    public Query addLeftJoinFetchClause( String expression )
    {
        return addCaseedJoinClause( LEFT_JOIN_FETCH, expression );
    }
 
    /**
     * <pre>
     * 注意fetch也不应该与setMaxResults() 或 setFirstResult()共用，
     * 这是因为这些操作是基于结果集的，而在预先抓取集合类时可能包含重复的数据，也就是说无法预先知道精确的行数。
     * 
     * 添加LEFT JOIN FETCH 语句，例如:
     *  .addRightJoinFetchClause("d.sub s")  s为join的对象别名
     * 
     * </pre>
     */
    public Query addRightJoinFetchClause( String expression )
    {
        return addCaseedJoinClause( RIGHT_JOIN_FETCH, expression );
    }
 
    private Query addCaseedJoinClause( String whatJoin, String expression )
    {
        joinClause.append( SPACE ).append( whatJoin ).append( SPACE ).append( expression );
        return this;
    }
 
    private void prepareOrderByClause()
    {
        if ( isBlank( orderByClause ) )
        {
            orderByClause.append( SPACE ).append( ORDER_BY );
        }
        prepareOrderByClauseIfHasCondition();
    }
 
    private void prepareOrderByClauseIfHasCondition()
    {
        if ( orderByClause.length() != new String( SPACE + ORDER_BY ).length() )
        {
            orderByClause.append( "," );
        }
    }
 
    private void prepareWhereClause( String operation )
    {
        if ( isBlank( whereClause ) )
        {
            whereClause.append( SPACE ).append( WHERE );
        }
        prepareWhereClauseIfHasCondition( operation );
    }
 
    private void prepareWhereClauseIfHasCondition( String operation )
    {
        if ( whereClause.length() != new String( SPACE + WHERE ).length() )
        {
            whereClause.append( SPACE ).append( operation );
        }
    }
 
    private boolean isBlank( CharSequence c )
    {
        if ( c == null || c.length() == 0 )
        {
            return true;
        }
        return false;
    }
 
    private boolean isNotBlank( CharSequence c )
    {
        return !isBlank( c );
    }
 
    public Class<?> getEntityClass()
    {
        return entityClass;
    }
 
    public List<Object> getParameters()
    {
        return paramters;
    }
 
    /**
     * <pre>
     * 
     * 设置该参数抓取数据库所有数据
     * 设置的分页数将无效，改方法不能与setPageNum 跟 setPageSize 同时使用
     * 
     * 设置为true为全部抓取
     * 默认为false
     * 
     * </pre>
     * 
     * @param fetch 是否抓取全部数据
     * @return
     */
    public Query setFetchAllRecords( boolean fetch )
    {
        this.fetchAllRecords = fetch;
        return this;
    }
 
    public boolean isFetchAllRecords()
    {
        return fetchAllRecords;
    }
 
    /**
     * - page
     */
    public Query setPageNum( Integer pageNum )
    {
        this.fetchAllRecords = false;
        this.pageNum = pageNum;
        return this;
    }
 
    public Query setPageSize( int pageSize )
    {
        this.fetchAllRecords = false;
        this.pageSize = pageSize;
        return this;
    }
 
    public Integer getPageNum()
    {
        return ( pageNum == null || pageNum < 1 ) ? 1 : pageNum;
    }
 
    public Integer getPageSize()
    {
        return ( pageSize == null || pageSize < 1 ) ? 20 : pageSize;
    }
 
    public Integer getFirstResult()
    {
        return ( getPageNum() - 1 ) * getPageSize();
    }
 
    public Integer getMaxResults()
    {
        return getPageSize();
    }
 
}