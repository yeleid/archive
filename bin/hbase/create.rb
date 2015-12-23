create 'archive', {NAME => 'd', IS_MOB => true, MOB_THRESHOLD => 1024000}, {NAME => 'm', REPLICATION_SCOPE => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}
describe 'archive'
exit