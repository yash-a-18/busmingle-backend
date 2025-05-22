import sbt._

object Dependencies {


  /**
    * Unless you have some very, very specific and unusual requirements, this is the optimal 
    * ExecutionContext implementation for use in any Scala.js project. If you're using ExecutionContext 
    * and not using this project, you likely have some serious bugs and/or performance issues waiting to be discovered.
    */
  val zio =  Def.setting {
    Seq("dev.zio" %% "zio" % DependencyVersions.zio,
    "dev.zio" %% "zio-http" % DependencyVersions.zioHttp,
    "dev.zio" %% "zio-json" % DependencyVersions.zioJson,
    "dev.zio" %% "zio-redis" % DependencyVersions.zioRedis
    )
  }

  val quill = Def.setting {
    Seq(
      "io.getquill" %% "quill-jdbc-zio" % DependencyVersions.quill,
      "org.postgresql" % "postgresql" % DependencyVersions.postgres
    )
  }

  val jwt = Def.setting {
    Seq(
      "com.github.jwt-scala" %% "jwt-core" % DependencyVersions.jwtCore
    )
  }

  val tapir = Def.setting {
    Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % DependencyVersions.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % DependencyVersions.tapir,
      "com.softwaremill.sttp.client3" %% "zio" % DependencyVersions.sttpClient3,
      "com.softwaremill.sttp.client4" %% "zio" % DependencyVersions.sttpClient4,
      "com.softwaremill.sttp.client4" %% "zio-json" % DependencyVersions.sttpClient4,
      "com.softwaremill.sttp.client4" %% "core" % DependencyVersions.sttpClient4,
      "com.softwaremill.sttp.client3" %% "circe" % DependencyVersions.sttpClient3
      


    )
  }

}
