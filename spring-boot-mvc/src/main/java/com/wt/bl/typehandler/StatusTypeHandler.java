package com.wt.bl.typehandler;

import com.wt.bl.enums.StatusEnum;
import com.wt.bl.enums.ValuableEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 注意这里的泛型是针对于枚举而非整个对象，但是如果我们类型处理对应的是mysql的json可以使用对象
 *
 * @MappedTypes(ValuableEnum.class)  如果不加此注解，不生效，但是如果我们是单个的指定的枚举值，则不需要添加这个注解
 * 通过类型处理器的泛型，MyBatis 可以得知该类型处理器处理的 Java 类型，不过这种行为可以通过两种方法改变：
 * 1. 在类型处理器的配置元素（typeHandler element）上增加一个 javaType 属性（比如：javaType="String"）；
 * 2. 在类型处理器的类上（TypeHandler class）增加一个 @MappedTypes 注解来指定与其关联的 Java 类型列表。 如果在 javaType 属性中也同时指定，则注解方式将被忽略。
 * 可以通过两种方式来指定被关联的 JDBC 类型：
 * 1. 在类型处理器的配置元素上增加一个 jdbcType 属性（比如：jdbcType="VARCHAR"）；
 * 2. 在类型处理器的类上（TypeHandler class）增加一个 @MappedJdbcTypes 注解来指定与其关联的 JDBC 类型列表。 如果在 jdbcType 属性中也同时指定，则注解方式将被忽略。
 *  用于指定映射类型
 * @author WangTao
 *         Created at 18/9/3 下午5:08.
 */
@MappedTypes(ValuableEnum.class)
public class StatusTypeHandler<E extends Enum<E> & ValuableEnum> extends BaseTypeHandler<E> {

    private Class<E> clazz;

    public StatusTypeHandler(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ValuableEnum.getEnum(clazz, rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ValuableEnum.getEnum(clazz, rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ValuableEnum.getEnum(clazz, cs.getInt(columnIndex));
    }

    // 单个写法
    /*@Override
    public void setNonNullParameter(PreparedStatement ps, int i, StatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return StatusEnum.getStatusEnum(rs.getInt(columnName));
    }

    @Override
    public StatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return StatusEnum.getStatusEnum(rs.getInt(columnIndex));
    }

    @Override
    public StatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
*/
}
