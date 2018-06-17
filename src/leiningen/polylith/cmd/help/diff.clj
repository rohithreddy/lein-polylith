(ns leiningen.polylith.cmd.help.diff)

(defn help []
  (println "  List all files and directories that has been changed in the workspace")
  (println "  since a specific point in time.")
  (println)
  (println "  lein polylith diff [ARG] [FLAG]")
  (println "    ARG = (omitted) -> Since last successful build, stored in bookmark")
  (println "                       :last-successful-build in WS-ROOT/.polylith/time.edn. or")
  (println "                       :last-successful-build in WS-ROOT/.polylith/git.edn if")
  (println "                       you have CI variable set to something on the machine.")
  (println "          timestamp -> Since the given timestamp (milliseconds since 1970).")
  (println "          git-hash  -> Since the given git hash if the CI variable is set.")
  (println "          bookmark  -> Since the timestamp for the given bookmark in WS-ROOT/.polylith/time.edn or")
  (println "                       since the git hash for the given bookmark in WS-ROOT/.polylith/git.edn if CI")
  (println "                       variable set.")
  (println)
  (println "    FLAG = +        -> Show time information.")
  (println "                       (the + sign may occur in any order in the argument list).")
  (println)
  (println "   'lein polylith diff 0' can be used to list all files in the workspace.")
  (println)
  (println "  example:")
  (println "    lein polylith diff")
  (println "    lein polylith diff +")
  (println "    lein polylith diff + 1523649477000")
  (println "    lein polylith diff + 7d7fd132412aad0f8d3019edfccd1e9d92a5a8ae")
  (println "    lein polylith diff 1523649477000")
  (println "    lein polylith diff 7d7fd132412aad0f8d3019edfccd1e9d92a5a8ae")
  (println "    lein polylith diff 1523649477000 +")
  (println "    lein polylith diff mybookmark"))
