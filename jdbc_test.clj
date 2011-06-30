; ugliest. clojure. code. evar.

(use 'clojure.contrib.sql)
(import [java.util Calendar TimeZone Date])

(let [db_host "localhost"
      db_port 5432
      db_name "fsfiii"
      db_user "reader"
      db_pass "r32der"]

  (def db {:classname "org.postgresql.Driver"
           :subprotocol "postgresql"
           :subname (str "//" db_host ":" db_port "/" db_name)
           :user db_user
           :password db_pass})

  
  (def begin_ts (. (Calendar/getInstance) getTimeInMillis))

  (with-connection db
    (with-query-results rs ["select * from test_big_table limit 1000000"]
;     (dorun (println (let [recs (reduce (fn [n s] (+ n (count s))) 0 rs)]
     (dorun (println (let [recs (reduce (fn [n s] (inc n)) 0 rs)]
       (str recs " rows processed in "
       (/ (- (. (Calendar/getInstance) getTimeInMillis) begin_ts) 1000.0)
       " seconds ("
       (/ recs (/
         (- (. (Calendar/getInstance) getTimeInMillis) begin_ts) 1000.0
       ))
       " recs/s)"
       )))))))

;      (dorun (map #(println (str
;                            (:product_id %)  "^A"
;                            (str (:date %))  "^A"
;                            (str (:price %)) "^A"
;                            (:rank %)
;                            )) rs)))))
