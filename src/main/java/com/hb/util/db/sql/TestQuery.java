package com.hb.util.db.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
 
import java.util.List;
 
import org.junit.Test;
 
/**
 * <pre>
 * 
 *  测试Query生成jpql查询语子
 * 
 * @author XzhiF
 *          Sep 25, 2013
 * </pre>
 */
public class TestQuery
{
 
    private static final String ENTITY_NAME = Object.class.getName();
 
    @Test
    public void testMultiEvaluate() throws Exception
    {
        String expected = "FROM " + ENTITY_NAME + " d WHERE d.name=?";
 
        Query query = new Query( Object.class, "d" )//
        .addWhereAndClause( "d.name=?", "abc" );
 
        String result = query.evaluate();
        assertEquals( expected, result );
        /*
         * second evaluate
         */
        assertEquals( result, query.evaluate() );
    }
 
    @Test
    public void testAndOrWhereClause() throws Exception
    {
        String expected = "FROM " + ENTITY_NAME + " d WHERE d.name=? AND d.id=? OR d.id=? AND d.parent IS NULL";
 
        Query query = new Query( Object.class, "d" )//
        .addWhereAndClause( "d.name=?", "abc" )//
        .addWhereAndClause( "d.id=?", "abc" )//
        .addWhereOrClause( "d.id=?", "aa" )//
        .addWhereAndClause( "d.parent IS NULL" );
        String result = query.evaluate();
        assertEquals( expected, result );
        assertEquals( result, query.evaluate() );
        assertEquals( new Integer(3), new Integer(query.getParameters().size()) );
    }
 
    @Test
    public void testOrderByClause() throws Exception
    {
        String expected = "FROM " + ENTITY_NAME + " d WHERE d.name=? ORDER BY d.id DESC, d.name DESC, d.age ASC";
 
        Query query = new Query( Object.class, "d" )//
        .addWhereAndClause( "d.name=?", "abc" )//
        .addOrderByDescClause( "d.id" )//
        .addOrderByDescClause( "d.name" ).addOrderByAscClause( "d.age" );
 
        String result = query.evaluate();
        // System.out.println(result);
        assertEquals( expected, result );
    }
 
    @Test
    public void testJoinsClause() throws Exception
    {
        String expected = "FROM " + ENTITY_NAME + " d " + //
        "JOIN d.sub s" + //
        " INNER JOIN d.sub s" + //
        " LEFT JOIN d.sub s" + //
        " RIGHT JOIN d.sub s" + //
        " LEFT OUTER JOIN d.sub s" + //
        " RIGHT OUTER JOIN d.sub s" + //
        " LEFT JOIN FETCH d.sub s" + //
        " RIGHT JOIN FETCH d.sub s";
 
        Query query = new Query( Object.class, "d" )//
        .addJoinClause( "d.sub s" )//
        .addInnerJoinClause( "d.sub s" )//
        .addLeftJoinClause( "d.sub s" )//
        .addRightJoinClause( "d.sub s" )//
        .addLeftOuterJoinClause( "d.sub s" )//
        .addRightOuterJoinClause( "d.sub s" )//
        .addLeftJoinFetchClause( "d.sub s" )//
        .addRightJoinFetchClause( "d.sub s" );
 
        String result = query.evaluate();
        // System.out.println(result);
        assertEquals( expected, result );
    }
 
    @Test
    public void testClauseHasCondition() throws Exception
    {
        String expected = "FROM " + ENTITY_NAME + " d INNER JOIN d.sub s WHERE d.id=? OR d.name=? ORDER BY o.id ASC, o.name DESC";
 
        Query query = new Query( Object.class, "d" )//
        .addWhereAndClause( true, "d.id=?", "1" )//
        .addWhereAndClause( false, "d.id=?", "1" )//
        .addWhereOrClause( true, "d.name=?", "a" )//
        .addWhereOrClause( false, "d.name=?", "b" )//
        .addOrderByAscClause( true, "o.id" )//
        .addOrderByAscClause( false, "o.id" )//
        .addOrderByDescClause( true, "o.name" )//
        .addOrderByDescClause( false, "o.name" )//
        .addInnerJoinClause( "d.sub s" );
 
        String result = query.evaluate();
        // System.out.println(result);
        assertEquals( expected, result );
    }
 
    @Test
    public void testSelectClause() throws Exception
    {
        String expected = "SELECT d FROM " + ENTITY_NAME + " d";
 
        Query query = new Query( Object.class, "d" )//
        .addSelectClause( "d" );//
 
        String result = query.evaluate();
        // System.out.println(result);
        assertEquals( expected, result );
 
        expected = "SELECT new EntityVO(id,name) FROM " + ENTITY_NAME + " d";
        query = new Query( Object.class, "d" )//
        .addSelectClause( "new EntityVO(id,name)" );//
        result = query.evaluate();
        assertEquals( expected, result );
 
    }
 
    @Test
    public void testMultiAddSelectClause() throws Exception
    {
        Query query = new Query( Object.class, "d" )//
        .addSelectClause( "d" );
        try
        {
            query.addSelectClause( "a" );
            fail( "retry addSelectCluase should throw a exception" );
        }
        catch (Exception e )
        {
        }
    }
 
    @Test
    public void testWithinParams() throws Exception
    {
 
        Query query = new Query( Object.class, "d" )//
        .addWhereAndClause( "d.id=?", "1" )//
        .addWhereAndClause( "d.id=?", "2" )//
        .addWhereOrClause( "d.name=?", "a" )//
        .addWhereOrClause( "d.name=?", "b" );//
 
        List<Object> paramters = query.getParameters();
        assertEquals( "1", paramters.get( 0 ) );
        assertEquals( "2", paramters.get( 1 ) );
        assertEquals( "a", paramters.get( 2 ) );
        assertEquals( "b", paramters.get( 3 ) );
    }
 
    @Test
    public void testEvaluateCount() throws Exception
    {
        String expected = "SELECT COUNT(d) FROM " + ENTITY_NAME + " d WHERE d.id=? AND d.name=?";
        Query query = new Query( Object.class, "d" )//
        .addWhereAndClause( "d.id=?", "1" )//
        .addWhereAndClause( "d.name=?", "a" )//
        .addOrderByAscClause( "d.id" )//
        .addInnerJoinClause( "d.sub c" )//
        .addSelectClause( "d" );
 
        String result = query.evaluateCount();
        // System.out.println(result);
        assertEquals( expected, result );
 
    }
 
}
