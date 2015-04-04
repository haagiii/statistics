(ns statistics.core)
(use '(incanter core charts io datasets stats))
(view (function-plot sin -4 4))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def data (read-dataset "resources/data.csv" :header true))
(def month ($ :month data))
(def A ($ :A data))
(def B ($ :B data))
(def C ($ :C data))

(mean ($ :A data)) ;平均
(median ($ :B data)) ;中央値
(variance ($ :C data)) ;分散

(view (line-chart month C))


;matrixという行列の形式でファイルを読み込み
(def reg-matrix (to-matrix (read-dataset "resources/reg.csv" :header true))) 
;selを使ってcols(列)が0番目の列を取得し、それをAと定義します
(def A (sel reg-matrix :cols 0))
;(linear-model目的変数 説明変数)で線形回帰をしてくれます。(range 1 6)は1〜5までの数値のリストを出してくれます。つまり、reg-matrixの1〜5列目までを説明変数として利用しますという意味です
;lmと打てばモデルの詳細な中身が得られますが、今回は回帰係数と決定係数だけ見ます
(def lm (linear-model A (sel reg-matrix :cols (range 1 6))))
(str "回帰係数:" (:coefs lm) ", 自由度調整済み決定係数:" (:adj-r-square lm))
>"回帰係数:(105.84756925102556 1.536568888859847 0.0038050803303892877 1.3632247569223068 -2.45697750029467 0.35739630831540126), 自由度調整済み決定係数:0.9932540338906779"
;また、回帰係数が欲しいだけなら、簡易版のsimple-regressionもあります。
(simple-regression A (sel reg-matrix :cols (range 1 6)))
