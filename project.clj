(defproject bio-alg "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.taoensso/timbre "3.3.1-1cd4b70"]
                 [prismatic/hiphip "0.2.0"]]
  :main ^:skip-aot bio-alg.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :jvm-opts ^:replace [])
