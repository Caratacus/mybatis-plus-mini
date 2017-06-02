# mybatis-plus-mini(MP)
mybatis-plus-mini

##CRUD no sql
* For single table CRUD, you don't need write any sql.
* Auto fit most of database types(current support list: MYSQL, ORACLE, DB2, H2, HSQL, SQLITE, POSTGRE, SQLSERVER2005, SQLSERVER;).
* MP provides a lot of methods(Refer to BaseMapper.java).
* MP EntityWrapper/Condition provides flexible way to construct sql.
* There are 2 ways of usage: normal mapper/ActiveRecord(AR).

##Physical Pagination
* Mybatis-Plus(MP) support physical pagination for the mentioned types below.
* You just need to add PaginationInterceptor into mybatis plugins.

##Optimistic Lock
* You will never care about version, never set version(version=version+1, ect..) manually
* Just add an annotation: @Version(com.baomidou.mybatisplus.annotations.Version), Done!

##Auto Fill common fields
* MP support auto fill fields.
* (1)You just need to define a class extends MetaObjectHandler;
* (2)Implement insertFill()/updateFill();
* (3)Use getFieldValByName()/setFieldValByName() to fill the values.

