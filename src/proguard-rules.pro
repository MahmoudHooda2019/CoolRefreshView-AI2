# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.aemo.pollrefresh.PollRefresh {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/aemo/pollrefresh/repack'
-flattenpackagehierarchy
-dontpreverify
