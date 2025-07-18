lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(UniversalPlugin)
  // .enablePlugins(PlayNettyServer).disablePlugins(PlayPekkoHttpServer) // uncomment to use the Netty backend
  .settings(
    name := """api-server""",
    organization := "com.example",
    version := "0.1.0",
    crossScalaVersions := Seq("2.13.16", "3.3.6"),
    scalaVersion := crossScalaVersions.value.head,
    // ファイル変更検知できないためポーリング
    PlayKeys.fileWatchService := play.dev.filewatch.FileWatchService.polling(500),
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      evolutions,
      "org.postgresql" % "postgresql" % "42.7.7",
      "io.getquill" %% "quill-jdbc" % "4.8.5",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test,
    ),
    scalacOptions ++= Seq("-feature", "-Werror"),
  )
