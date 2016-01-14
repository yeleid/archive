#!/usr/bin/env bash

ZOOKEEPER="172.31.10.195:2181"

#delete indexer
echo "delete indexer..."
NAME="archive"
hbase-indexer delete-indexer --name $NAME --zookeeper $ZOOKEEPER

# delete solr collection
echo "delete solr collection..."
SOLR_COLLECTION="archive"
SOLR="http://172.31.10.196:8983/solr"
ZK="$ZOOKEEPER/solr"
solrctl --solr $SOLR --zk $ZK collection --delete $SOLR_COLLECTION
solrctl --solr $SOLR --zk $ZK instancedir --delete $SOLR_COLLECTION

# delete hbase table
echo "delete hbase table..."
hbase shell bin/hbase/drop.rb
echo



