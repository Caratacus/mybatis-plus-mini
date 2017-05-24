package com.baomidou.mybatisplus.plugins;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;

/**
 * <p>
 * Optimistic Lock Light version<BR>
 *     Intercept on {@link Executor}.update;
 * </p>
 *
 * @author yuxiaobin
 * @date 2017/5/24
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class OptimisticLockLightInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        if(SqlCommandType.UPDATE.compareTo(ms.getSqlCommandType())!=0){
            return invocation.proceed();
        }
        ParameterMap pmap = ms.getParameterMap();

        List<ParameterMapping> plist =  pmap.getParameterMappings();
        Object param = args[1];
        if(param instanceof MapperMethod.ParamMap){
            //mapper.update(updEntity, EntityWrapper<>(whereEntity);
            MapperMethod.ParamMap map = (MapperMethod.ParamMap) param;
            Wrapper ew = (Wrapper) map.get("ew");
            Object et = map.get("et");
            if(ew!=null){
                Object entity = ew.getEntity();
                if(entity!=null){
                    Field versionField = getVersionField(entity.getClass());
                    if (versionField != null) {
                        Integer originalVersionVal = (Integer)versionField.get(entity);//TODO: for testing only, hardcode Integer
                        if(originalVersionVal!=null){
                            versionField.set(et, originalVersionVal+1);
                        }
                    }
                }
            }
        }else{
            //updateById
            Class<?> parameterClass = param.getClass();
            if(Collection.class.isAssignableFrom(parameterClass)){
                return invocation.proceed();
            }
            Map<String,Object> entityMap = new HashMap<>();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(param.getClass());
            if(tableInfo!=null){
                List<EntityField> fields = getFieldsFromClazz(parameterClass, null);
                Field versionField = null;
                for(EntityField ef : fields){
                    Field fd = ef.getField();
                    if(fd.isAccessible()) {
                        entityMap.put(fd.getName(), fd.get(param));
                        if (ef.isVersion()) {
                            versionField = fd;
                        }
                    }
                }
                Integer originalVersionVal = (Integer)versionField.get(param);//TODO: for testing only, hardcode Integer
                if(originalVersionVal!=null){
                    String versionPropertyName = versionField.getName();
                    List<TableFieldInfo> fieldList = tableInfo.getFieldList();
                    String versionColumnName = null;
                    for(TableFieldInfo tf : fieldList){
                        if(versionPropertyName.equals(tf.getProperty())){
                            versionColumnName = tf.getColumn();
                        }
                    }
                    if(versionColumnName!=null) {
                        entityMap.put(versionField.getName(), originalVersionVal+1);
                        entityMap.put("MP_VERSION_ORIGINAL", originalVersionVal);
                        entityMap.put("MP_VERSION_COLUMN", versionColumnName);
                    }

                }

            }
            args[1] = entityMap;
        }
        System.out.println("****param.class"+param.getClass());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Field getVersionField(Class<?> parameterClass) {
        if (parameterClass != Object.class) {
            for (Field field : parameterClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Version.class)) {
                    field.setAccessible(true);
                    return field;
                }
            }
            return getVersionField(parameterClass.getSuperclass());
        }
        return null;
    }

    private List<EntityField> getFieldsFromClazz(Class<?> parameterClass, List<EntityField> fieldList){
        if(fieldList==null){
            fieldList = new ArrayList<>();
        }
        List<Field> fields = ReflectionKit.getFieldList(parameterClass);
        for(Field field:fields){
            field.setAccessible(true);
            if (field.isAnnotationPresent(Version.class)) {
                fieldList.add(new EntityField(field, true));
            }else{
                fieldList.add(new EntityField(field, false));
            }
        }
        return fieldList;
    }

}
class EntityField{

    private Field field;
    private boolean version;
    private String columnName;

    public EntityField() {
    }

    public EntityField(Field field, boolean version) {
        this.field = field;
        this.version = version;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
