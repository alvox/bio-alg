(ns bio-alg.skews)

(defn find-skews [^String text]
  (let [skews (make-array Long/TYPE (inc (.length text)))]
    (loop [i 0]
      (if (= i (.length text))
        skews
        (let [c (.charAt text i)]
          (cond
            (= c \C)
              (aset ^longs skews (inc i) (dec (aget ^longs skews i)))
            (= c \G)
              (aset ^longs skews (inc i) (inc (aget ^longs skews i)))
            :default
              (aset ^longs skews (inc i) (aget ^longs skews i)))
          (recur (inc i)))))))

(defn find-min [arr]
  (areduce ^longs arr i min-elem 0
           (if (< (aget ^longs arr i) min-elem)
             (aget ^longs arr i)
             min-elem)))

(defn find-min-skews [text]
  (let [skews (find-skews text)
        min-elem (find-min skews)]
    (loop [coll [] idx 0]
      (if (= idx (alength ^longs skews))
        coll
        (if (= (aget ^longs skews idx) min-elem)
          (recur (conj coll idx) (inc idx))
          (recur coll (inc idx)))))))
