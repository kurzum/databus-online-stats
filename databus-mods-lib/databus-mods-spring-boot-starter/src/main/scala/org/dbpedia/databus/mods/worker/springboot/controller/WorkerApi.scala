package org.dbpedia.databus.mods.worker.springboot.controller

import org.dbpedia.databus.dataid.Part
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod}

import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

@Controller
abstract class WorkerApi {

  /**
   * todo multiple APIs for one application
   */
  private val log = LoggerFactory.getLogger(classOf[WorkerApi])

  @RequestMapping(
    value = Array("{publisher}/{group}/{artifact}/{version}/{file}/activity"),
    method = Array(RequestMethod.GET, RequestMethod.POST))
  def activity(
    @PathVariable publisher: String,
    @PathVariable group: String,
    @PathVariable artifact: String,
    @PathVariable version: String,
    @PathVariable file: String,
    request: HttpServletRequest, response: HttpServletResponse
  ): Unit = {

    // TODO
    val didPartUri = s"https://databus.dbpedia.org/$publisher/$group/$artifact/$version/$file"

    val didPart = Part.apply(didPartUri)
    try {
      handleRequest(didPart, request, response)
    } catch {
      case e: Exception =>
        response.setStatus(500)
        val os = response.getOutputStream
        val pw = new PrintWriter(os, true, StandardCharsets.UTF_8)
        e.printStackTrace(pw)
        pw.close()
        os.close()
    }
  }

  def handleRequest(didPart: Part, request: HttpServletRequest, response: HttpServletResponse): Unit
}
