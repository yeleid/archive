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

**deploy key-value store indexer and configure mophlines**

![lily-indexer](docs/img/lily-indexer.png)

![morphline](docs/img/morphline.png)

For more details, go see bin/solr/morphline.conf.

#### 2. Archive System Setup

**clone code repository**

    git clone https://github.com/yeleid/archive

**compile and package**

    mvn clean package
    
**setup environment**

This step includes hbase table creation, solr collection creation and lily indexer registration.

    bin/setup.sh
    
**run archive service**

    bin/archive.sh
    
    then you should be able to see the log as follows:
    16/01/14 04:20:10 INFO solutions.ArchiveServer: Start Archive Server.
    
    
#### 3. Demonstration

**check the service is running**

    bin/info.sh
    
    then you should be able to see the log as follows:
    Archive System 0.4 @yeleid
    
**upload a file (< 10MB) that to be archived**

    bin/upload.sh
    
    by default, this file will be associated with id '123'. if you change ID="123" to ID="new" in script bin/upload.sh, archive service will return an uuid for this new file.
    
**update metadata for an archived file**

    bin/update.sh
    
**query metadata given file id**

    bin/retrieve.sh
    
    then you should be able to see the log like below:
    {"category":null,"author":"alex","filename":"test.txt"}
    
**download the archived file given file id**

    bin/download.sh
    
    by default, the downloaded file will be put into directory '/tmp/staging/'.
    
#### 4. References

*solrctrl reference*

    http://www.cloudera.com/content/www/en-us/documentation/enterprise/latest/topics/search_solrctl_ref.html
    
*extractHBaseCells Morphline command*
    
    http://www.cloudera.com/content/www/en-us/documentation/enterprise/latest/topics/search_hbase_batch_indexer.html
    
*using Lily HBase NRT Indexer service*

    http://www.cloudera.com/content/www/en-us/documentation/enterprise/latest/topics/search_use_hbase_indexer_service.html
    
*indexer configuration*

    https://github.com/NGDATA/hbase-indexer/wiki/Indexer-configuration
    
    