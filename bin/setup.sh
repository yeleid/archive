#!/usr/bin/env bash

ZOOKEEPER="172.31.10.195:2181"

# create hbase table
echo "create hbase table..."
hbase shell bin/hbase/create.rb
echo

# create solr collection
echo "create solr collection..."
SOLR_COLLECTION="archive"
SOLR_DIR="/tmp/archive_configs"
SHARDS=1
REPLICAS=1
SOLR="http://172.31.10.196:8983/solr"
ZK="$ZOOKEEPER/solr"

rm -rf $SOLR_DIR
solrctl instancedir --generate $SOLR_DIR
cp bin/solr/schema.xml $SOLR_DIR/conf/

solrctl --solr $SOLR --zk $ZK instancedir --create $SOLR_COLLECTION $SOLR_DIR
solrctl --solr $SOLR --zk $ZK collection --create $SOLR_COLLECTION -s $SHARDS -r $REPLICAS

#register indexer
echo "register indexer..."
NAME="archive"
INDEX_CONF="bin/solr/indexer-config.xml"
hbase-indexer add-indexer --name $NAME --indexer-conf $INDEX_CONF --connection-param solr.zk=$ZK --connection-param solr.collection=$SOLR_COLLECTION --zookeeper $ZOOKEEPER
hbase-indexer list-indexers --zookeeper $ZOOKEEPER