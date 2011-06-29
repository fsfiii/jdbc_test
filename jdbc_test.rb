#!/usr/bin/env jruby
# quick test of jdbc under jruby for comparison to java

require 'java'

db_url  = 'jdbc:postgresql://localhost/fsfiii'
db_user = 'reader'
db_pass = 'r32der'
sql = 'select * from test_big_table limit 1000000'

Java::org.postgresql.Driver

conn = java.sql.DriverManager.get_connection db_url, db_user, db_pass
conn.set_auto_commit false
stmt = conn.create_statement
stmt.fetch_size = 50

begin_ts = Time.now.to_i

rslt = stmt.execute_query sql
meta = rslt.meta_data
cols = meta.column_count

n = 0
while rslt.next do
  1.upto(cols) do |i|
    s = rslt.get_string i
  end
  n += 1
end

elapsed = Time.now.to_i - begin_ts
rate = '%0.02f' % (n / elapsed.to_f)
STDERR.puts "#{n} rows processed in #{elapsed} seconds (#{rate} recs/s)"
