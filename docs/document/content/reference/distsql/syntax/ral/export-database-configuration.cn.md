+++
title = "EXPORT DATABASE CONFIGURATION"
weight = 9
+++

### 描述

`EXPORT DATABASE CONFIGURATION` 语法用于将 `database` 中的存储单元和规则配置导出为 `YAML` 格式

### 语法

```sql
ExportDatabaseConfiguration ::=
  'EXPORT' 'DATABASE' 'CONFIGURATION' ('FROM' databaseName)? ('TO' 'FILE' filePath)?

databaseName ::=
  identifier

filePath ::=
  string
```

### 补充说明

- 未指定 `databaseName` 时，默认是当前使用的 `DATABASE。` 如果也未使用 `DATABASE` 则会提示 `No database selected`。

- 未指定 `filePath` 时，会将存储单元和规则配置导出至屏幕。

### 示例

- 导出指定逻辑库的存储单元和规则配置到指定路径

```sql
EXPORT DATABASE CONFIGURATION FROM test1 TO FILE "/xxx/config_test1.yaml";
```

- 导出指定逻辑库的存储单元和规则配置到屏幕

```sql
EXPORT DATABASE CONFIGURATION FROM test1;
```

```sql
mysql> EXPORT DATABASE CONFIGURATION FROM test1;
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| result                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| databaseName: test1
dataSources:
  su_1:
    password: 123456
    url: jdbc:mysql://127.0.0.1:3306/migration_ds_0
    username: root
    minPoolSize: 1
    connectionTimeoutMilliseconds: 30000
    maxLifetimeMilliseconds: 2100000
    readOnly: false
    idleTimeoutMilliseconds: 60000
    maxPoolSize: 50
  su_2:
    password: 123456
    url: jdbc:mysql://127.0.0.1:3306/db1
    username: root
    minPoolSize: 1
    connectionTimeoutMilliseconds: 30000
    maxLifetimeMilliseconds: 2100000
    readOnly: false
    idleTimeoutMilliseconds: 60000
    maxPoolSize: 50
rules:
 |
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)
```

- 导出当前逻辑库的存储单元和规则配置到指定路径

```sql
EXPORT DATABASE CONFIGURATION TO FILE "/xxx/config_test1.yaml";
```

- 导出当前逻辑库的存储单元和规则配置到屏幕

```sql
EXPORT DATABASE CONFIGURATION;
```

```sql
mysql> EXPORT DATABASE CONFIGURATION;
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| result                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| databaseName: test1
dataSources:
  su_1:
    password: 123456
    url: jdbc:mysql://127.0.0.1:3306/migration_ds_0
    username: root
    minPoolSize: 1
    connectionTimeoutMilliseconds: 30000
    maxLifetimeMilliseconds: 2100000
    readOnly: false
    idleTimeoutMilliseconds: 60000
    maxPoolSize: 50
  su_2:
    password: 123456
    url: jdbc:mysql://127.0.0.1:3306/db1
    username: root
    minPoolSize: 1
    connectionTimeoutMilliseconds: 30000
    maxLifetimeMilliseconds: 2100000
    readOnly: false
    idleTimeoutMilliseconds: 60000
    maxPoolSize: 50
rules:
 |
+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)
```
### 保留字

`EXPORT`、`DATABASE`、`CONFIGURATION`、`FROM`、`TO`、`FILE`

### 相关链接

- [保留字](/cn/reference/distsql/syntax/reserved-word/)