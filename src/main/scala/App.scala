
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

import java.nio.file.{Files, Path, Paths}
import scala.io.Source
import scala.sys.process._
import scala.util.Using

object App {

  sealed trait Style

  object Style {
    case object LIGHT extends Style
    case object DARK extends Style

    val values: Set[Style] = Set(DARK, LIGHT)
  }

  private def buildHTMLDocument(content: String, style: Style): String = {
    import Style._

    val styleInclude = style match {
      case LIGHT => "https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      case DARK => "http://netdna.bootstrapcdn.com/bootswatch/2.1.1/cyborg/bootstrap.min.css"
    }

    s"""<!doctype html>
       |<html>
       |  <head>
       |  <meta charset='utf-8'>
       |    <title>Stefan Lourens - Resume</title>
       |	  <link href="$styleInclude" rel="stylesheet">}
       |	</head>
       |	<body>
       |	  <div style="width: 780px; margin:0 auto;">
       |		  $content
       |	  </div>
       |	</body>
       |</html>
       |""".stripMargin
  }

  def main(args: Array[String]): Unit = {
    val resumeDirectory: Path = Paths.get("resume")

    Using(Source.fromFile(resumeDirectory.resolve("resume.md").toFile)) { resume =>
      val parser = Parser.builder().build()
      val renderer = HtmlRenderer.builder().build()
      val htmlContent = renderer.render {
        parser.parseReader(resume.reader())
      }

      Files.write(resumeDirectory.resolve("index.html"), buildHTMLDocument(htmlContent, Style.LIGHT).getBytes())
      Files.write(resumeDirectory.resolve("dark.html"), buildHTMLDocument(htmlContent, Style.DARK).getBytes())

      s"wkhtmltopdf ${resumeDirectory.resolve("index.html")} ${resumeDirectory.resolve("resume.pdf")}".!
    }
  }

}
