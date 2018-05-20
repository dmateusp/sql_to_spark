# sql_to_spark

Parsing SQL scripts and translating them into their Spark DataFrame API equivalent

While having Spark in production running big SQL strings is possible, the DataFrame API's syntax has advantages over raw SQL strings:
- compile time safe syntax (not types)
- easier to navigate the code (using the IDE to jump to definitions)

The process of translating SQL scripts to their DataFrame equivalent can be repetitive, tedious and error prone hence the need for automation

## Getting started

```bash
sbt 'run path/to/sql_script.sql'
```

## Support matrix

### SELECT

| ANSI SQL        | supported |
| --------------- | --------- |
| column name     | [X]       |
| column rename   | [X]       |
| type cast       | [ ]       |
| literal         | [ ]       |
| custom function | [ ]       |
| joins           | [ ]       |
| nested select   | [ ]       |

## Example

```sql
select *
from dw.table;
```

becomes

```
spark  
    .read
    // TODO: add source for dw.table
    .select(
        col("*")
    )
```