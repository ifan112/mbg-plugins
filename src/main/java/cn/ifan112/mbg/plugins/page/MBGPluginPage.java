package cn.ifan112.mbg.plugins.page;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Mybatis Generator Plugin Page
 *
 * 分页插件
 */

public class MBGPluginPage extends PluginAdapter {

    private static final String FIELD_OFFSET = "offset";
    private static final String FIELD_LIMIT = "limit";

    private static final PrimitiveTypeWrapper INTEGER_WRAPPER
            = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // XxxExample 类中加入 offset 和 limit 字段
        newFieldAndGetSetMethods(FIELD_OFFSET, topLevelClass);
        newFieldAndGetSetMethods(FIELD_LIMIT, topLevelClass);

        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // TODO
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }


    private void newFieldAndGetSetMethods(String fieldName, TopLevelClass topLevelClass) {
        Field field = new Field(fieldName, INTEGER_WRAPPER);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(INTEGER_WRAPPER);
        topLevelClass.addField(field);

        topLevelClass.addMethod(getMethod(fieldName));
        topLevelClass.addMethod(setMethod(fieldName));
    }

    private Method getMethod(String fieldName) {
        Method getMethod = new Method(fieldName);

        getMethod.setVisibility(JavaVisibility.PUBLIC);
        getMethod.setReturnType(INTEGER_WRAPPER);
        getMethod.setName(fieldName);
        getMethod.addBodyLine(String.format("return %s;", fieldName));

        return getMethod;
    }

    private Method setMethod(String fieldName) {
        Method setMethod = new Method(fieldName);

        setMethod.setVisibility(JavaVisibility.PUBLIC);
        setMethod.setName(fieldName);
        setMethod.addParameter(new Parameter(INTEGER_WRAPPER, fieldName));
        setMethod.addBodyLine(String.format("this.%s = %s;", fieldName, fieldName));

        return setMethod;
    }
}
