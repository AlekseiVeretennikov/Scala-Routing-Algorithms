error id: 681ADBCE6DADFDD3A9CDADA229A03939
file:///c:/Users/1/AppData/Local/Programs/Microsoft%20VS%20Code/LAB/noL1/Main.scala
### java.lang.AssertionError: assertion failed: (List(),0)

occurred in the presentation compiler.



action parameters:
offset: 9
uri: file:///c:/Users/1/AppData/Local/Programs/Microsoft%20VS%20Code/LAB/noL1/Main.scala
text:
```scala
object Ma@@

```


presentation compiler configuration:
Scala version: 2.12.20
Classpath:
<WORKSPACE>\.bloop\nol1\bloop-bsp-clients-classes\classes-Metals-2tCZwCEUTwCt-fwZDa_xdw== [exists ], <HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.11.0\semanticdb-javac-0.11.0.jar [exists ], <HOME>\.sbt\boot\scala-2.12.20\lib\scala-library.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalafx\scalafx_2.12\16.0.0-R24\scalafx_2.12-16.0.0-R24.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-base\17.0.2\javafx-base-17.0.2-win.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-controls\17.0.2\javafx-controls-17.0.2-win.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-graphics\17.0.2\javafx-graphics-17.0.2-win.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-media\17.0.2\javafx-media-17.0.2-win.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-swing\17.0.2\javafx-swing-17.0.2-win.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-web\17.0.2\javafx-web-17.0.2-win.jar [exists ], <HOME>\.sbt\boot\scala-2.12.20\lib\scala-reflect.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-graphics\17.0.2\javafx-graphics-17.0.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-base\17.0.2\javafx-base-17.0.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-controls\17.0.2\javafx-controls-17.0.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\openjfx\javafx-media\17.0.2\javafx-media-17.0.2.jar [exists ]
Options:
-Yrangepos -Xplugin-require:semanticdb




#### Error stacktrace:

```
scala.tools.nsc.classpath.FileBasedCache.getOrCreate(ZipAndJarFileLookupFactory.scala:284)
	scala.tools.nsc.plugins.Plugins.findPluginClassLoader(Plugins.scala:109)
	scala.tools.nsc.plugins.Plugins.findPluginClassLoader$(Plugins.scala:72)
	scala.tools.nsc.Global.findPluginClassLoader(Global.scala:44)
	scala.tools.nsc.plugins.Plugins.$anonfun$loadRoughPluginsList$6(Plugins.scala:47)
	scala.tools.nsc.plugins.Plugin$.$anonfun$loadAllFrom$1(Plugin.scala:148)
	scala.collection.immutable.List.map(List.scala:293)
	scala.tools.nsc.plugins.Plugins.loadRoughPluginsList(Plugins.scala:47)
	scala.tools.nsc.plugins.Plugins.loadRoughPluginsList$(Plugins.scala:40)
	scala.tools.nsc.Global.loadRoughPluginsList(Global.scala:44)
	scala.tools.nsc.plugins.Plugins.roughPluginsList(Plugins.scala:113)
	scala.tools.nsc.plugins.Plugins.roughPluginsList$(Plugins.scala:113)
	scala.tools.nsc.Global.roughPluginsList$lzycompute(Global.scala:44)
	scala.tools.nsc.Global.roughPluginsList(Global.scala:44)
	scala.tools.nsc.plugins.Plugins.loadPlugins(Plugins.scala:149)
	scala.tools.nsc.plugins.Plugins.loadPlugins$(Plugins.scala:119)
	scala.tools.nsc.Global.loadPlugins(Global.scala:44)
	scala.tools.nsc.plugins.Plugins.plugins(Plugins.scala:165)
	scala.tools.nsc.plugins.Plugins.plugins$(Plugins.scala:165)
	scala.tools.nsc.Global.plugins$lzycompute(Global.scala:44)
	scala.tools.nsc.Global.plugins(Global.scala:44)
	scala.tools.nsc.plugins.Plugins.computePluginPhases(Plugins.scala:176)
	scala.tools.nsc.plugins.Plugins.computePluginPhases$(Plugins.scala:175)
	scala.tools.nsc.Global.computePluginPhases(Global.scala:44)
	scala.tools.nsc.Global.computePhaseDescriptors(Global.scala:733)
	scala.tools.nsc.Global.phaseDescriptors$lzycompute(Global.scala:738)
	scala.tools.nsc.Global.phaseDescriptors(Global.scala:738)
	scala.tools.nsc.Global$Run.<init>(Global.scala:1247)
	scala.tools.nsc.interactive.Global$TyperRun.<init>(Global.scala:1323)
	scala.tools.nsc.interactive.Global.newTyperRun(Global.scala:1346)
	scala.tools.nsc.interactive.Global.<init>(Global.scala:294)
	scala.meta.internal.pc.MetalsGlobal.<init>(MetalsGlobal.scala:49)
	scala.meta.internal.pc.ScalaPresentationCompiler.newCompiler(ScalaPresentationCompiler.scala:627)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$compilerAccess$1(ScalaPresentationCompiler.scala:147)
	scala.meta.internal.pc.CompilerAccess.loadCompiler(CompilerAccess.scala:40)
	scala.meta.internal.pc.CompilerAccess.retryWithCleanCompiler(CompilerAccess.scala:182)
	scala.meta.internal.pc.CompilerAccess.$anonfun$withSharedCompiler$1(CompilerAccess.scala:155)
	scala.Option.map(Option.scala:230)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:154)
	scala.meta.internal.pc.CompilerAccess.$anonfun$withNonInterruptableCompiler$1(CompilerAccess.scala:132)
	scala.meta.internal.pc.CompilerAccess.$anonfun$onCompilerJobQueue$1(CompilerAccess.scala:209)
	scala.meta.internal.pc.CompilerJobQueue$Job.run(CompilerJobQueue.scala:152)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1583)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: (List(),0)