(ns raster-to-svg.core
  (:import [javax.imageio ImageIO]
           [java.awt.image BufferedImage])
  (:require [clojure.java.io]))

(defn get-pixels-for-block [raster block-x block-y block-size]
  (for [x (range (* block-x block-size) (* (inc block-x) block-size))
        y (range (* block-y block-size) (* (inc block-y) block-size))]
    (.getPixel raster x y nil)))

(defn process-block [raster block-x block-y block-size]
  (get-pixels-for-block raster block-x, block-y, block-size))

(defn process-image []
  (let [block-size 8
        filename "resources/candle.png"
        imagefile (clojure.java.io/as-file filename)
        raster (.getData (. ImageIO read imagefile))
        width-in-blocks (/ (.getWidth raster) block-size)
        height-in-blocks (/ (.getHeight raster) block-size)           
        block-coords (for [x (range width-in-blocks) y (range height-in-blocks)] [x y])]

    (map (fn [[x y]] (process-block raster x y block-size)) block-coords)))

(defn -main [& args]
  (println (process-image)))
