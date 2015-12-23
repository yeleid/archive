# archive
An archive app based on CDH

#### 1. Prerequisites

**enable hbase replication feature**

To take advantage of MOB, you have to use HFile version 3 (The MOB feature is transparent to the client).

*HBase Service Advanced Configuration Snippet (Safety Valve)*
```xml
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
    
    