(ns raster-to-svg.core
  (:import [javax.imageio ImageIO]
           [java.awt.image BufferedImage])
  (:require [clojure.java.io]))

(defn average [numbers]
  (/ (apply + numbers) (count numbers)))

(defn process-block [raster block-size [x y]]
  (average (.getPixels raster x y block-size block-size nil)))

(defn process-image []
  (let [block-size 8
        filename "resources/candle.png"
        imagefile (clojure.java.io/as-file filename)
        raster (.getData (. ImageIO read imagefile))
        block-coords (for [x (range 0 (.getWidth raster) block-size)
                           y (range 0 (.getHeight raster) block-size)] [x y])]
    (->> block-coords
         (map (partial process-block raster block-size))
         (zipmap block-coords))))

(defn -main [& args]
  (println (process-image)))
