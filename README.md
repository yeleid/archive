# archive
An archive app based on CDH

#### 1. Prerequisites

**enable hbase replication feature**

To take advantage of MOB, you have to use HFile version 3 (The MOB feature is transparent to the client).

```xml
HBase Service Advanced Configuration Snippet (Safety Valve)
<property>
  <name>hfile.format.version</name>
  <value>3</value>
</property>
```

![hbase_replication](docs/img/hbase-replication.png)

#### 2. Archive System Setup

**clone code repository**

    git clone https://github.com/yeleid/archive

**compile and package**

    mvn clean package
    
**run archive service**

    bin/archive.sh
    
#### 3. References

*solrctrl reference*

    http://www.cloudera.com/content/www/en-us/documentation/enterprise/latest/topics/search_solrctl_ref.html
    
*extractHBaseCells Morphline command*
    
    http://www.cloudera.com/content/www/en-us/documentation/enterprise/latest/topics/search_hbase_batch_indexer.html
    
*using Lily HBase NRT Indexer service*

    http://www.cloudera.com/content/www/en-us/documentation/enterprise/latest/topics/search_use_hbase_indexer_service.html
    
*indexer configuration*

    https://github.com/NGDATA/hbase-indexer/wiki/Indexer-configuration